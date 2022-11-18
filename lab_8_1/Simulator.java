import java.util.function.Supplier;

class Simulator {
    private final int numOfServers;
    private final int numOfSelfCheckouts;
    private final int qmax;
    private final ImList<Pair<Double, Supplier<Double>>> inputTimes;
    private final Supplier<Double> restTimes;

    Simulator(int numOfServers, int numOfSelfCheckouts, int qmax, ImList<Pair<Double, Supplier<Double>>> inputTimes,
            Supplier<Double> restTimes) {
        this.numOfSelfCheckouts = numOfSelfCheckouts;
        this.numOfServers = numOfServers;
        this.qmax = qmax;
        this.inputTimes = inputTimes;
        this.restTimes = restTimes;
    }

    String simulate() {
        int customerNumber = 1;

        PQ<Event> queue = new PQ<Event>(new EventComparator());
        ServerBalancer serverBalancer = new ServerBalancer(this.numOfServers, this.numOfSelfCheckouts,
                this.qmax, this.restTimes);

        for (Pair<Double, Supplier<Double>> customerPair : this.inputTimes) {

            double arrivalTime = customerPair.first();

            Customer customer = new Customer(arrivalTime, customerNumber);
            queue = queue.add(new ArriveEvent(customer,
                    customerPair.second(), customer.getArrivalTime()));
            customerNumber += 1;
        }

        while (!queue.isEmpty()) {
            // System.out.println("new iteration------------------");
            Pair<Event, PQ<Event>> pair = queue.poll();
            Event event = pair.first();
            queue = pair.second();

            // System.out.println(queue);

            event.maybePrintEvent();

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