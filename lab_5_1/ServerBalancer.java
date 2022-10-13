class ServerBalancer {
    private final int numOfServers;
    private final int qmax;
    private final ImList<Server> servers;

    ServerBalancer(int numOfServers, int qmax) {
        this.numOfServers = numOfServers;
        this.qmax = qmax;

        ImList<Server> servers = new ImList<Server>();

        for (int i = 0; i < this.numOfServers; i++) {
            servers = servers.add(new Server(i + 1, this.qmax));
        }

        this.servers = servers;
    }

    ServerBalancer(int numOfServers, int qmax, ImList<Server> servers) {
        this.numOfServers = numOfServers;
        this.qmax = qmax;
        this.servers = servers;
    }

    boolean isThereAServerAvailable() {
        for (Server server : this.servers) {
            if (server.isAvailable()) {
                return true;
            }
        }
        return false;
    }

    Server getAvailableServer() {
        Server availableServer = this.servers.get(0);
        for (Server server : this.servers) {
            if (server.isAvailable()) {
                return server;
            }
        }
        return availableServer;
    }

    boolean isThereServerWithSpaceInQueue() {
        for (Server server : this.servers) {
            if (server.hasSpaceInQueue()) {
                return true;
            }
        }
        return false;
    }

    Server getServerWithSpaceInQueue() {
        Server serverWithSpaceInQueue = this.servers.get(0);
        for (Server server : this.servers) {
            if (server.hasSpaceInQueue()) {
                return server;
            }
        }
        return serverWithSpaceInQueue;
    }

    Server getServer(int serverNumber) {
        return this.servers.get(serverNumber - 1);
    }

    ServerBalancer updateServer(Server server) {
        ImList<Server> newServers = this.servers.set(server.getServerIndexNumber(), server);
        return updateListOfServers(newServers);
    }

    ServerBalancer updateListOfServers(ImList<Server> servers) {
        return new ServerBalancer(this.numOfServers, this.qmax, servers);
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
