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
        return "";
    }

}
