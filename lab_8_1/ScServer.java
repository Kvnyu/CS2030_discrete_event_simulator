import java.util.function.Supplier;

class ScServer extends Server {
    protected final ImList<Server> scServers;

    ScServer(int numOfScServers, int serverNumber, int maxQSize, Supplier<Double> restTimes) {
        this(numOfScServers, serverNumber, maxQSize, restTimes, 0.0);
    }

    ScServer(int numOfScServers, int serverNumber, int maxQSize, Supplier<Double> restTimes, double nextAvailableAt) {
        this(numOfScServers, serverNumber, maxQSize, restTimes, 0, nextAvailableAt);
    }

    ScServer(int numOfScServers, int serverNumber, int maxQSize, Supplier<Double> restTimes,
            int totalCustomersServed, double nextAvailableAt) {
        this(numOfScServers, serverNumber, maxQSize, restTimes,
                totalCustomersServed, 0.0, nextAvailableAt);
    }

    ScServer(int numOfScServers, int serverNumber, int maxQSize, Supplier<Double> restTimes, int totalCustomersServed,
            double totalCustomerWaitTime, double nextAvailableAt) {
        this(numOfScServers, serverNumber, maxQSize, restTimes, totalCustomersServed,
                0.0, true, nextAvailableAt);
    }

    ScServer(int numOfScServers, int serverNumber, int maxQSize, Supplier<Double> restTimes,
            int totalCustomersServed, double totalCustomerWaitTime,
            boolean isAvailable, double nextAvailableAt) {
        this(numOfScServers, serverNumber, maxQSize, restTimes, totalCustomersServed,
                totalCustomerWaitTime, isAvailable, nextAvailableAt,
                new ImList<Customer>());
    }

    ScServer(int numOfScServers, int serverNumber, int maxQSize, Supplier<Double> restTimes,
            int totalCustomersServed, double totalCustomerWaitTime,
            boolean isAvailable, double nextAvailableAt,
            ImList<Customer> customers) {
        super(serverNumber, maxQSize, restTimes, totalCustomersServed, totalCustomerWaitTime, isAvailable,
                nextAvailableAt, customers, false);
        ImList<Server> tempServers = new ImList<Server>();
        for (int i = 1; i < numOfScServers + 1; i++) {
            tempServers = tempServers.add(Server.of(serverNumber + i, 1, restTimes, true));
        }
        this.scServers = tempServers;
    }

    ScServer(int numOfScServers, int serverNumber, int maxQSize, Supplier<Double> restTimes,
            int totalCustomersServed, double totalCustomerWaitTime,
            boolean isAvailable, double nextAvailableAt,
            ImList<Customer> customers, ImList<Server> scServers) {
        super(serverNumber, maxQSize, restTimes, totalCustomersServed, totalCustomerWaitTime, isAvailable,
                nextAvailableAt, customers, false);
        this.scServers = scServers;
    }

    @Override
    boolean isAvailableAt(double eventTime) {
        for (Server server : this.scServers) {
            if (server.isAvailableAt(eventTime)) {
                return true;
            }
        }
        return false;
    }

    @Override
    Server updateServer(Server server) {
        ImList<Server> newScServers = this.scServers.set(server.getServerNumber() - this.getServerNumber() - 1, server);
        return new ScServer(this.scServers.size(), this.getServerNumber(), this.getMaxQSize(), this.getRestTimes(),
                this.getTotalCustomersServed(), this.getTotalCustomerWaitTime(), this.isAvailable(),
                this.getNextAvailableAt(), this.getCustomers(), newScServers);
    }

    @Override
    Server getIfAvailable(double eventTime) {
        Server returnServer = this.scServers.get(0);
        for (Server server : this.scServers) {
            if (server.isAvailableAt(eventTime)) {
                return server;
            }
        }
        return returnServer;
    }

    @Override
    Server get(int serverNumber) {
        return this.scServers.get(serverNumber - 3);
    }

    @Override
    Pair<Server, Double> finishServing() {
        return new Pair<Server, Double>(this, 0.0);
    }

    @Override
    Server returnFromRest() {
        ImList<Customer> newCustomers = this.getCustomers().remove(0);
        int newTotalCustomersServed = this.getTotalCustomersServed() + 1;

        return new Server(this.getServerNumber(), this.getMaxQSize(), this.getRestTimes(),
                newTotalCustomersServed,
                this.getTotalCustomerWaitTime(), true,
                this.getNextAvailableAt(), newCustomers, this.getIsSc());
    }

    @Override
    public String toString() {
        return String.format("self-check %d", this.getServerNumber());
    }
}