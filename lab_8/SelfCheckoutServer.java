import java.util.function.Supplier;

class SelfCheckoutServer extends AbstractServer {
    //
    private final ImList<Server> servers;
    private final int firstServerNumber;

    SelfCheckoutServer(int numOfSelfCheckoutServers, int serverNumber, int maxQSize, Supplier<Double> restTimes) {
        this(numOfSelfCheckoutServers, serverNumber, maxQSize, restTimes, 0.0);
    }

    SelfCheckoutServer(int numOfSelfCheckoutServers, int serverNumber, int maxQSize, Supplier<Double> restTimes,
            double nextAvailableAt) {
        this(numOfSelfCheckoutServers, serverNumber, maxQSize, restTimes, 0, nextAvailableAt);
    }

    SelfCheckoutServer(int numOfSelfCheckoutServers, int serverNumber, int maxQSize, Supplier<Double> restTimes,
            int totalCustomersServed, double nextAvailableAt) {
        this(numOfSelfCheckoutServers, serverNumber, maxQSize, restTimes,
                totalCustomersServed, 0.0, nextAvailableAt);
    }

    SelfCheckoutServer(int numOfSelfCheckoutServers, int serverNumber, int maxQSize, Supplier<Double> restTimes,
            int totalCustomersServed,
            double totalCustomerWaitTime, double nextAvailableAt) {
        this(numOfSelfCheckoutServers, serverNumber, maxQSize, restTimes, totalCustomersServed,
                0.0, true, nextAvailableAt);
    }

    SelfCheckoutServer(int numOfSelfCheckoutServers, int serverNumber, int maxQSize, Supplier<Double> restTimes,
            int totalCustomersServed, double totalCustomerWaitTime,
            boolean isAvailable, double nextAvailableAt) {
        this(numOfSelfCheckoutServers, serverNumber, maxQSize, restTimes, totalCustomersServed,
                totalCustomerWaitTime, isAvailable, nextAvailableAt,
                new ImList<Customer>());
    }

    SelfCheckoutServer(int numOfSelfCheckoutServers, int serverNumber, int maxQSize, Supplier<Double> restTimes,
            int totalCustomersServed, double totalCustomerWaitTime,
            boolean isAvailable, double nextAvailableAt,
            ImList<Customer> customers, ImList<Server> servers) {
        super(false, serverNumber, maxQSize, restTimes, totalCustomersServed, totalCustomerWaitTime, isAvailable,
                nextAvailableAt,
                customers);
        this.firstServerNumber = serverNumber;
        this.servers = servers;
    }

    SelfCheckoutServer(int numOfSelfCheckoutServers, int serverNumber, int maxQSize, Supplier<Double> restTimes,
            int totalCustomersServed, double totalCustomerWaitTime,
            boolean isAvailable, double nextAvailableAt,
            ImList<Customer> customers) {
        super(false, serverNumber, maxQSize, restTimes, totalCustomersServed, totalCustomerWaitTime, isAvailable,
                nextAvailableAt,
                customers);
        this.firstServerNumber = serverNumber;
        ImList<Server> tempServers = new ImList<Server>();
        for (int i = 0; i < numOfSelfCheckoutServers; i++) {
            tempServers = tempServers.add(new Server(true, serverNumber + i, 1, restTimes));
        }
        this.servers = tempServers;
        System.out.println(this.servers.size());
    }

    int getNumOfSelfCheckoutServers() {
        return this.servers.size();
    }

    boolean isAvailableAt(double eventTime) {
        for (Server server : this.servers) {
            if (server.isAvailableAt(eventTime)) {
                return true;
            }
        }
        return false;
    }

    @Override
    double getNextAvailableAt() {
        double earliestAvailableAt = this.servers.get(0).getNextAvailableAt();
        for (Server server : this.servers) {
            System.out.println(server.nextAvailableAt());
            earliestAvailableAt = Math.min(server.getNextAvailableAt(), earliestAvailableAt);
        }
        return earliestAvailableAt;
    }

    boolean hasSpaceInQueueAt(double eventTime) {
        if (this.getNextAvailableAt() <= eventTime) {
            return this.getMaxQSize() - this.getQueueSize() > 0;
        } else {
            return this.getMaxQSize() - this.getQueueSize() + 1 > 0;
        }
    }

    Server getNextAvailableSelfCheckoutServer() {
        Server earliestAvailableServer = this.servers.get(0);
        for (Server currentServer : this.servers) {
            if (currentServer.getNextAvailableAt() < earliestAvailableServer.getNextAvailableAt()) {
                earliestAvailableServer = currentServer;
            }
        }
        return earliestAvailableServer;
    }

    ImList<Server> updateServer(Server server) {
        return this.servers.set(server.getServerNumber() - this.firstServerNumber, server);
    }

    SelfCheckoutServer startServing(Customer customer, int serverNumber, double serviceTime,
            boolean serveFromQueue, double eventTime) {
        Server newServer = this.servers.get(serverNumber - 1).startServing(customer, serverNumber, serviceTime,
                serveFromQueue, eventTime);
        ImList<Server> newServers = updateServer(newServer);

        return new SelfCheckoutServer(this.getNumOfSelfCheckoutServers(), this.getServerNumber(), this.getMaxQSize(),
                this.getRestTimes(),
                this.getTotalCustomersServed(), this.getTotalCustomerWaitTime(),
                false, serviceTime, this.getCustomers(), newServers);
    }

    SelfCheckoutServer addCustomerToQueue(Customer customer) {
        ImList<Customer> customers = this.getCustomers().add(customer);
        return new SelfCheckoutServer(this.getNumOfSelfCheckoutServers(), this.getServerNumber(), this.getMaxQSize(),
                this.getRestTimes(),
                this.getTotalCustomersServed(), this.getTotalCustomerWaitTime(),
                this.isAvailable(), this.getNextAvailableAt(), customers, this.servers);
    }

    Pair<AbstractServer, Double> finishServing() {
        return new Pair<AbstractServer, Double>(
                this,
                0.0);
    }

    Customer getNextCustomerInQueue() {
        return this.getCustomers().get(0);
    }

    SelfCheckoutServer returnFromRest(int serverNumber) {
        Server newServer = this.servers.get(serverNumber - 1).returnFromRest(serverNumber);
        ImList<Server> newServers = this.updateServer(newServer);
        return new SelfCheckoutServer(this.getNumOfSelfCheckoutServers(), this.getServerNumber(), this.getMaxQSize(),
                this.getRestTimes(),
                this.getTotalCustomersServed(),
                this.getTotalCustomerWaitTime(), true,
                this.getNextAvailableAt(), this.getCustomers(), newServers);
    }

    @Override
    AbstractServer getAvailableServer() {
        return this.getNextAvailableSelfCheckoutServer();
    }

    @Override
    String getServerName(int serverNumber) {
        return String.format("self-check %s", serverNumber);
    }

    @Override
    public String toString() {
        return String.format("%d: self checkout server %s", this.getServerNumber(), this.getCustomers().toString());
    }
}
