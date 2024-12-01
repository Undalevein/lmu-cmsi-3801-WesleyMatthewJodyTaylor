package main

import (
	"log"
	"math/rand"
	"sync/atomic"
	"time"
	"os"
	"container/list"
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
		var order = <- Waiter
		do(10, name, "cooking order", order.id, "for", order.customer);
		order.preparedBy = name
		order.reply.complete(order)
		break
	}
}

/*
public static void cook(String name) {
		log(name, "starting work");
		while (true) {
			try {
				// We want to get the order with a blocking call
				var order = waiter.take();
				doAction(10, name, "cooking order", order.id, "for", order.customer);
				order.preparedBy = name;
				// Completing the future sends the cooked order back to the customer
				order.reply.complete(order);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				break;
			}
		}
	}
*/

func customer(name string) {
	mealsEaten := 0
	for mealsEaten < 5 {
		var order = Order(name)
		log.Println(name, "placed order", order.id)
		if (Waiter.offer(order, 7, time.Second)) {
			var meal = order.reply.get()
			do(2, name, "eating cooked order", meal.id)
			mealsEaten++
		} else {
			do(5, name, "waiting too long, abandoning order", order.id)
		}
		log.Println(name, "going home")
	}
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
	do(10, "Remy", "is cooking")
	var customers = ["Ani", "Bai", "Cat", "Dao", "Eve", "Fay", "Gus", "Hua", "Iza", "Jai"]
	// ADD SOME STUFF HERE

    log.Println("Restaurant Closing")
    // SHUT DOWN EXECUTOR...
}

/*

	public static void main(String[] args) throws InterruptedException {
		var customers = List.of(
			"Ani", "Bai", "Cat", "Dao", "Eve", "Fay", "Gus", "Hua", "Iza", "Jai");

		// All threads will be virtual threads
		ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
		executor.execute(() -> cook("Remy"));
		executor.execute(() -> cook("Colette"));
		executor.execute(() -> cook("Linguini"));

		// Need a latch in order to wait for all customers to finish
		CountDownLatch latch = new CountDownLatch(customers.size());
		for (String customer : customers) {
			executor.execute(() -> customer(customer, latch));
		}
		latch.await();

		// Cleanup nicely
		log("Restaurant closing");
		executor.shutdown();
	}
*/