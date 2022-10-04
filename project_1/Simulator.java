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
        int customerCount = 0;
        PQ<Event> queue = new PQ<Event>(new EventComparator());
        ImList<Server> servers = new ImList<Server>();

        for (Pair<Double, Double> customerPair : this.inputTimes) {

            double arrivalTime = customerPair.first();
            double serviceTime = customerPair.second();

            Customer customer = new Customer(arrivalTime, serviceTime, customerCount);
            queue = queue.add(new ArriveEvent(customer));
            customerCount += 1;
        }

        for (int i; i < this.numOfServers; i++) {
            servers.add(new Server(Integer.toString(i), this.qmax));
        }

        while (!queue.isEmpty()) {
            Pair<Event, PQ<Event>> pair = queue.poll();
            Event event = pair.first();

            queue = pair.second();

            // TODO: Change the class signature of getNextEvent to require servers list
            Pair<Event, ImList<Server>> eventServers = event.getNextEvent(servers);
            Event nextEvent = eventServers.first();
            servers = eventServers.second();
            // TODO: Create a new Terminal event attribute on Event, and TerminalEvent class
            // that all terminal events return as "getNextEvent"
            if (!eventServers.second().isTerminalEvent()) {
                queue.add(eventServers.second());
            }

            System.out.println(event);
        }
        return "";
    }
}
