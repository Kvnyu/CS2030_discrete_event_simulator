class Simulator {
    private final int numOfServers;
    private final int qmax;
    private final ImList<Pair<Double, Double>> inputTimes;

    Simulator(int numOfServers, int qmax, ImList<Pair<Double, Double>> inputTimes) {
        this.numOfServers = numOfServers;
        this.qmax = qmax;
        this.inputTimes = inputTimes;
    }

    String simulate() {
        int customerCount = 1;
        PQ<Event> queue = new PQ<Event>(new EventComparator());
        ServerBalancer serverBalancer = new ServerBalancer(this.numOfServers, this.qmax);

        for (Pair<Double, Double> customerPair : this.inputTimes) {

            double arrivalTime = customerPair.first();
            double serviceTime = customerPair.second();

            Customer customer = new Customer(arrivalTime, serviceTime, customerCount);
            queue = queue.add(new ArriveEvent(customer));
            customerCount += 1;
        }

        while (!queue.isEmpty()) {
            Pair<Event, PQ<Event>> pair = queue.poll();

            Event event = pair.first();
            queue = pair.second();

            // TODO: Change the class signature of getNextEvent to require servers list
            Pair<Event, ServerBalancer> eventServers = event.getNextEvent(serverBalancer);
            Event nextEvent = eventServers.first();
            serverBalancer = eventServers.second();
            // TODO: Create a new Terminal event attribute on Event, and TerminalEvent class
            // that all terminal events return as "getNextEvent"

            if (!nextEvent.isTerminalEvent()) {
                queue = queue.add(nextEvent);
            }

            if (!event.isTerminalEvent()) {
                System.out.println(event);
            }
        }
        return "";
    }
}
