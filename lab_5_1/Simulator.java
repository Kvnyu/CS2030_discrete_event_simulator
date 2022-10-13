import java.util.function.Supplier;

class Simulator {
    private final int numOfServers;
    private final int qmax;
    private final ImList<Pair<Double, Supplier<Double>>> inputTimes;

    Simulator(int numOfServers, int qmax, ImList<Pair<Double, Supplier<Double>>> inputTimes) {
        this.numOfServers = numOfServers;
        this.qmax = qmax;
        this.inputTimes = inputTimes;
    }

    String simulate() {
        int customerNumber = 1;
        PQ<Event> queue = new PQ<Event>(new EventComparator());
        ServerBalancer serverBalancer = new ServerBalancer(this.numOfServers, this.qmax);

        for (Pair<Double, Supplier<Double>> customerPair : this.inputTimes) {

            double arrivalTime = customerPair.first();

            Customer customer = new Customer(arrivalTime, customerNumber);
            queue = queue.add(new ArriveEvent(customer, customerPair.second(), customer.getArrivalTime()));
            customerNumber += 1;
        }

        while (!queue.isEmpty()) {
            System.out.println("new iteration------------------");
            Pair<Event, PQ<Event>> pair = queue.poll();
            Event event = pair.first();
            queue = pair.second();

            System.out.println(queue);

            if (!event.isTerminalEvent()) {
                System.out.println(event);
                System.out.println(event.getPriority());
            }

            Pair<Event, ServerBalancer> eventServers = event.getNextEvent(serverBalancer);
            Event nextEvent = eventServers.first();
            serverBalancer = eventServers.second();

            if (!nextEvent.isTerminalEvent()) {
                queue = queue.add(nextEvent);
            }

        }

        int customerCount = customerNumber - 1;
        ImList<Double> returnArray = new ImList<Double>();
        int totalCustomersServed = serverBalancer.getTotalCustomersServed();
        int totalCustomersLeft = customerCount - totalCustomersServed;
        double averageWaitingTime = serverBalancer.getTotalCustomerWaitTime()
                / totalCustomersServed;
        returnArray = returnArray.add(averageWaitingTime);

        return String.format("[%.3f %d %d]", averageWaitingTime,
                totalCustomersServed, totalCustomersLeft);
    }
}
