import java.util.function.Supplier;

class ServerBalancer {
    private final int numOfServers;
    private final int numOfSelfCheckoutServers;
    private final int qmax;
    private final ImList<AbstractServer> servers;
    private final Supplier<Double> restTimes;

    ServerBalancer(int numOfServers, int numOfSelfCheckoutServers, int qmax, Supplier<Double> restTimes) {
        this.numOfServers = numOfServers;
        this.numOfSelfCheckoutServers = numOfSelfCheckoutServers;
        this.qmax = qmax;
        this.restTimes = restTimes;

        ImList<AbstractServer> servers = new ImList<AbstractServer>();

        for (int i = 0; i < this.numOfServers; i++) {
            servers = servers.add(new Server(i + 1, this.qmax, this.restTimes));
        }

        servers = servers.add(new SelfCheckoutServer(numOfServers + 1, this.qmax, this.restTimes));

        this.servers = servers;
    }

    ServerBalancer(int numOfServers, int numOfSelfCheckoutServers, int qmax, Supplier<Double> restTimes,
            ImList<AbstractServer> servers) {
        this.numOfServers = numOfServers;
        this.numOfSelfCheckoutServers = numOfSelfCheckoutServers;
        this.qmax = qmax;
        this.restTimes = restTimes;
        this.servers = servers;
    }

    boolean isThereAServerAvailableAt(double eventTime) {
        for (AbstractServer server : this.servers) {
            if (server.isAvailableAt(eventTime)) {
                return true;
            }
        }
        return false;
    }

    AbstractServer getAvailableServerAt(double eventTime) {
        AbstractServer availableServer = this.servers.get(0);
        for (AbstractServer server : this.servers) {
            if (server.isAvailableAt(eventTime)) {
                return server;
            }
        }
        return availableServer;
    }

    boolean isThereServerWithSpaceInQueueAt(double eventTime) {
        for (AbstractServer server : this.servers) {
            if (server.hasSpaceInQueueAt(eventTime)) {
                return true;
            }
        }
        return false;
    }

    AbstractServer getServerWithSpaceInQueueAt(double eventTime) {
        AbstractServer serverWithSpaceInQueue = this.servers.get(0);
        for (AbstractServer server : this.servers) {
            // System.out.print(server.getServerNumber());
            // System.out.print(" ");
            // System.out.println(server.getCustomers());
            if (server.hasSpaceInQueueAt(eventTime)) {
                return server;
            }
        }
        return serverWithSpaceInQueue;
    }

    AbstractServer getServer(int serverNumber) {
        return this.servers.get(serverNumber - 1);
    }

    ServerBalancer updateServer(AbstractServer server) {
        ImList<AbstractServer> newServers = this.servers.set(server.getServerIndexNumber(), server);
        return updateListOfServers(newServers);
    }

    ServerBalancer updateListOfServers(ImList<AbstractServer> servers) {
        return new ServerBalancer(this.numOfServers, this.numOfSelfCheckoutServers, this.qmax, this.restTimes, servers);
    }

    double getTotalCustomerWaitTime() {
        double customerWaitTime = 0.0;
        for (AbstractServer server : this.servers) {
            customerWaitTime += server.getTotalCustomerWaitTime();
        }
        return customerWaitTime;
    }

    int getTotalCustomersServed() {
        int totalCustomersServed = 0;
        for (AbstractServer server : this.servers) {
            totalCustomersServed += server.getTotalCustomersServed();
        }
        return totalCustomersServed;
    }
}
