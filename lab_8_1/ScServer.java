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
    Server addCustomerToQueue(Customer customer) {
        ImList<Customer> customers = this.getCustomers().add(customer);
        // System.out.println(customers);
        return new ScServer(this.scServers.size(), this.getServerNumber(), this.getMaxQSize(), this.getRestTimes(),
                this.getTotalCustomersServed(), this.getTotalCustomerWaitTime(), this.isAvailable(),
                this.getNextAvailableAt(), customers, this.scServers);
    }

    @Override
    int getTotalCustomersServed() {
        int total = 0;
        for (Server server : this.scServers) {
            total += server.getTotalCustomersServed();
        }
        return total;
    }

    @Override
    double getTotalCustomerWaitTime() {
        double total = 0;
        for (Server server : this.scServers) {
            total += server.getTotalCustomerWaitTime();
        }
        return total;
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

    Server popScQueue() {
        ImList<Customer> newCustomers = this.getCustomers().remove(0);
        return new ScServer(this.scServers.size(), this.getServerNumber(), this.getMaxQSize(), this.getRestTimes(),
                this.getTotalCustomersServed(), this.getTotalCustomerWaitTime(), this.isAvailable(),
                this.getNextAvailableAt(), newCustomers, this.scServers);
    }

    @Override
    Server get(int serverNumber) {
        // minus 1 to convert to index
        // minus 2 since we shifted all ScServers forwards by 1
        return this.scServers.get(serverNumber - this.getServerNumber() - 1);
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
    double getNextAvailableAt() {
        double lowestNextAvailableAt = this.scServers.get(0).getNextAvailableAt();
        for (Server server : this.scServers) {
            lowestNextAvailableAt = Math.min(lowestNextAvailableAt, server.getNextAvailableAt());
        }
        return lowestNextAvailableAt;

    }

    @Override
    public String toString() {
        return String.format("self-check %d", this.getServerNumber());
        // return String.format("outerScServer %s | %s | %s", this.getServerNumber(),
        // this.getCustomers(),
        // this.scServers.toString());
    }

    @Override
    boolean hasSpaceInQueueAt(double eventTime) {
        // System.out.println(String.format("%d %d", this.maxQSize,
        // this.getQueueSize()));
        if (this.getNextAvailableAt() <= eventTime) {
            return this.getMaxQSize() - this.getQueueSize() - 1 > 0;
        } else {
            return this.getMaxQSize() - this.getQueueSize() > 0;
        }
    }
}