import java.util.List;
import java.time.LocalDateTime;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public class Restaurant {

    // Java's built-in logging utility is a bit of overkill for this example.
    private static void log(Object... args) {
        var strings = List.of(args).stream().map(Object::toString).toList();
        System.out.println(LocalDateTime.now() + " " + String.join(" ", strings));
    }

    // A little utility that simulates performing a task for a random duration.
    // For example, calling doAction(10, "Remy", "is cooking") will compute a
    // random number of millisecs between 5000 and 10000, log "Remy is cooking",
    // and sleep the current goroutine for that much time.
    private static void doAction(int seconds, Object... action) {
        log(action);
        try {
            var randomMillis = 500 * seconds + 
                    ThreadLocalRandom.current().nextInt(500 * seconds);
            Thread.sleep(randomMillis);
        } catch (InterruptedException e) {
            // Restore interrupted status and continue
            Thread.currentThread().interrupt();
        }
    }

    // An order for a meal is placed by a customer and is taken by a cook.
    // When the meal is finished, the cook will send the finished meal through
    // the reply channel. Each order has a unique id, safely incremented using
    // an atomic counter.
    static class Order {
        static final AtomicLong nextId = new AtomicLong(0);
        final long id;
        final String customer;
        final CompletableFuture<Order> reply;
        String preparedBy;

        Order(String customer) {
            this.id = nextId.incrementAndGet();
            this.customer = customer;
            this.reply = new CompletableFuture<>();
        }
    }

    // The waiter is represented by a channel of orders, which in Java is
    // represented as a BlockingQueue. The waiter will take orders from
    // customers and send them to the cook. The cook will then send the
    // prepared meal back to the waiter. To simulate a waiter being busy,
    // the waiter channel has a buffer capacity of 3 orders.
    private static final BlockingQueue<Order> waiter = new LinkedBlockingQueue<>(3);

    // A cook spends their time fetching orders from the order channel,
    // cooking the requested meal, and sending the meal back through the
    // order's reply channel. The cook function is designed to be run on a
    // Java virtual thread.
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

    // A customer eats five meals and then goes home. Each time they enter the
    // restaurant, they place an order with the waiter. If the waiter is too
    // busy, the customer will wait for 5 seconds before abandoning the order.
    // If the order does get placed, then they will wait as long as necessary
    // for the meal to be cooked and delivered.
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
}