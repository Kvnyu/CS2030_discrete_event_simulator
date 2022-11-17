import java.util.function.Supplier;

class ServerBalancer {
    private final int numOfServers;
    private final int numOfSelfCheckoutServers;
    private final int qmax;
    private final ImList<Server> servers;
    private final Supplier<Double> restTimes;

    ServerBalancer(int numOfServers, int numOfSelfCheckoutServers, int qmax, Supplier<Double> restTimes) {
        this.numOfServers = numOfServers;
        this.numOfSelfCheckoutServers = numOfSelfCheckoutServers;
        this.qmax = qmax;
        this.restTimes = restTimes;

        ImList<Server> servers = new ImList<Server>();

        for (int i = 0; i < this.numOfServers; i++) {
            servers = servers.add(new Server(i + 1, this.qmax, this.restTimes));
        }

        if (numOfSelfCheckoutServers > 0) {
            servers = servers
                    .add(new ScServer(numOfSelfCheckoutServers, numOfServers + 1, this.qmax, this.restTimes));
        }

        this.servers = servers;
    }

    ServerBalancer(int numOfServers, int numOfSelfCheckoutServers, int qmax, Supplier<Double> restTimes,
            ImList<Server> servers) {
        this.numOfServers = numOfServers;
        this.numOfSelfCheckoutServers = numOfSelfCheckoutServers;
        this.qmax = qmax;
        this.restTimes = restTimes;
        this.servers = servers;
    }

    boolean isThereAServerAvailableAt(double eventTime) {
        for (Server server : this.servers) {
            if (server.isAvailableAt(eventTime)) {
                return true;
            }
        }
        return false;
    }

    Server getAvailableServerAt(double eventTime) {
        Server availableServer = this.servers.get(0);
        for (Server server : this.servers) {
            if (server.isAvailableAt(eventTime)) {
                return server.getIfAvailable(eventTime);
            }
        }
        return availableServer;
    }

    boolean isThereServerWithSpaceInQueueAt(double eventTime) {
        for (Server server : this.servers) {
            if (server.hasSpaceInQueueAt(eventTime)) {
                return true;
            }
        }
        return false;
    }

    Server getServerWithSpaceInQueueAt(double eventTime) {
        Server serverWithSpaceInQueue = this.servers.get(0);
        for (Server server : this.servers) {
            if (server.hasSpaceInQueueAt(eventTime)) {
                return server;
            }
        }
        return serverWithSpaceInQueue;
    }

    Server getServer(int serverNumber) {
        // servers ScServer [servers]
        if (serverNumber > this.numOfServers + 1) {
            return this.servers.get(this.numOfServers).get(serverNumber);
        }
        return this.servers.get(serverNumber - 1);
    }

    ServerBalancer updateServer(Server server) {
        if (server.getServerNumber() > this.numOfServers + 1) {
            Server newServer = this.servers.get(this.numOfServers).updateServer(server);
            ImList<Server> newServers = this.servers.set(this.numOfServers, newServer);
            return updateListOfServers(newServers);
        }
        ImList<Server> newServers = this.servers.set(server.getServerIndexNumber(), server);
        return updateListOfServers(newServers);
    }

    ServerBalancer updateListOfServers(ImList<Server> servers) {
        return new ServerBalancer(this.numOfServers, this.numOfSelfCheckoutServers, this.qmax, this.restTimes, servers);
    }

    double getTotalCustomerWaitTime() {
        double customerWaitTime = 0.0;
        for (Server server : this.servers) {
            customerWaitTime += server.getTotalCustomerWaitTime();
        }
        return customerWaitTime;
    }

    int getTotalCustomersServed() {
        int totalCustomersServed = 0;
        for (Server server : this.servers) {
            totalCustomersServed += server.getTotalCustomersServed();
        }
        return totalCustomersServed;
    }
}
