package main

import (
	"log"
	"math/rand"
	"sync"
	"sync/atomic"
	"time"
)

// A little utility that simulates performing a task for a random duration.
// For example, calling do(10, "Remy", "is cooking") will compute a random
// number of milliseconds between 5000 and 10000, log "Remy is cooking",
// and sleep the current goroutine for that much time.

func do(seconds int, action ...any) {
	log.Println(action...)
	randomMillis := 500*seconds + rand.Intn(500*seconds)
	time.Sleep(time.Duration(randomMillis) * time.Millisecond)
}

type Order struct {
	id         uint64
	customer   string
	reply      chan *Order
	preparedBy string
}

var nextId atomic.Uint64
var Waiter = make(chan *Order, 3)

func Cook(name string, done chan struct{}) {
	log.Println(name, "starting work")
	for {
		select {
		case order := <-Waiter:
			order.preparedBy = name
			do(10, name, "cooking order", order.id, "for", order.customer)
			order.reply <- order
		case <-done:
			return
		}
	}
}

func Customer(name string, waitGroup *sync.WaitGroup) {
	defer waitGroup.Done()

	for mealsEaten := 0; mealsEaten < 5; {
		order := &Order{id: nextId.Add(1), customer: name, reply: make(chan *Order, 1)}
		log.Println(name, "placed order", order.id)
		select {
		case Waiter <- order:
			meal := <-order.reply
			do(2, name, "eating cooked order", meal.id, "prepared by", meal.preparedBy)
			mealsEaten++
		case <-time.After(7 * time.Second):
			do(5, name, "waiting too long, abandoning order", order.id)
		}
	}
	log.Println(name, "going home")
}

func main() {
	customers := [10]string{"Ani", "Bai", "Cat", "Dao", "Eve", "Fay", "Gus", "Hua", "Iza", "Jai"}

	done := make(chan struct{})

	go Cook("Remy", done)
	go Cook("Colette", done)
	go Cook("Linguini", done)

	var waitGroup sync.WaitGroup
	for _, customer := range customers {
		waitGroup.Add(1)
		go Customer(customer, &waitGroup)
	}
	waitGroup.Wait()

	close(done)
	log.Println("Restaurant Closing")
}
