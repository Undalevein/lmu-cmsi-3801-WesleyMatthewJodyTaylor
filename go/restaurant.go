package main

import (
	"log"
	"math/rand"
	"sync/atomic"
	"time"
	"runtime"
)

// A little utility that simulates performing a task for a random duration.
// For example, calling do(10, "Remy", "is cooking") will compute a random
// number of milliseconds between 5000 and 10000, log "Remy is cooking",
// and sleep the current goroutine for that much time.

func do(seconds int, action ...any) {
	log.Println(action...)
	randomMillis := 500 * seconds + rand.Intn(500 * seconds)
	time.Sleep(time.Duration(randomMillis) * time.Millisecond)
}

// Implement the rest of the simulation here. You may need to add more imports
// above.

type Order struct {
	nextId atomic.Uint32
	id int64
	customer string
	reply chan *Order
	preparedBy string
}

var Waiter = make(chan *Order, 3)

func cook(name string) {
	log.Println(name, "starting work");
	for {
		order := <- Waiter
		order.preparedBy = name
		do(10, name, "cooking order", order.id, "for", order.customer);
		order.reply <- (order)
	}
}

func customer(name string) {
	for mealsEaten := 0; mealsEaten < 5;  {
		order := Order{name}
		log.Println(name, "placed order", order.id)
		if (Waiter <- (order, 7, time.Second)) {
			meal := order.reply.get()
			do(2, name, "eating cooked order", meal.id)
			mealsEaten++
		} else {
			do(5, name, "waiting too long, abandoning order", order.id)
		}
	}
	log.Println(name, "going home")
}

/*
 public static void customer(String name, CountDownLatch latch) {
		try {
			for (var mealsEaten = 0; mealsEaten < 5; ) {
				var order = new Order(name);
				log(name, "placed order", order.id);

				try {
					// Wait 7 seconds for the waiter to take the order
					if (waiter.offer(order, 7, TimeUnit.SECONDS)) {
						// Wait indefinitely for the meal to be cooked
						var meal = order.reply.get();
						// Eat for up to 2 seconds
						doAction(2, name, "eating cooked order", meal.id,
								"prepared by", meal.preparedBy);
						mealsEaten++;
					} else {
						doAction(5, name, "waiting too long, abandoning order", order.id);
					}
				} catch (InterruptedException | ExecutionException e) {
					Thread.currentThread().interrupt();
					break;
				}
			}
			log(name, "going home");
		} finally {
			latch.countDown();
		}
	}
*/

func main(){
	customers := [10]string{"Ani", "Bai", "Cat", "Dao", "Eve", "Fay", "Gus", "Hua", "Iza", "Jai"}

	go cook("Remy")
	go cook("Colette")
	go cook("Linguini")

	waitGroup := sync.WaitGroup
	waitGroup.add(len(customers))
	for _, customer := range customers {
		go func() {
			defer waitGroup.Done()
			customer(customer)
		}()
	}
	waitGroup.Wait()

	log.Println("Restaurant Closing")
	runtime.Goexit()
}