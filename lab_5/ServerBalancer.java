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

    boolean isThereAServerFreeAt(double arrivalTime) {
        for (Server server : this.servers) {
            if (server.isFreeAt(arrivalTime)) {
                return true;
            }
        }
        return false;
    }

    Pair<Server, ServerBalancer> serve(Customer customer) {
        int availableServerIndex = 0;
        Server availableServer = this.servers.get(availableServerIndex);
        for (int i = 0; i < this.servers.size(); i++) {
            if (this.servers.get(i).isFreeAt(customer.getArrivalTime())) {
                availableServer = this.servers.get(i);
                availableServerIndex = i;
                break;
            }
        }

        Server newServer = availableServer.serve(customer);
        ImList<Server> newServers = this.servers.set(availableServerIndex, newServer);
        ServerBalancer newServerBalancer = new ServerBalancer(
                this.numOfServers, this.qmax, newServers);
        return new Pair<Server, ServerBalancer>(
                newServer,
                newServerBalancer);
    }

    Pair<Server, ServerBalancer> serve(Customer customer, Server server) {
        Server currentServer = this.servers.get(server.getServerIndexNumber());
        Server newServer = currentServer.serveFromQueue(customer);
        ImList<Server> newServers = this.servers.set(server.getServerIndexNumber(),
                newServer);
        ServerBalancer newServerBalancer = new ServerBalancer(this.numOfServers,
                this.qmax, newServers);
        return new Pair<Server, ServerBalancer>(
                newServer,
                newServerBalancer);
    }

    Pair<Server, ServerBalancer> addToQueue(Customer customer) {
        int serverWithQueueSpaceIndex = 0;
        Server serverWithQueueSpace = this.servers.get(serverWithQueueSpaceIndex);
        for (int i = 0; i < this.servers.size(); i++) {
            if (this.servers.get(i).hasSpaceInQueue()) {
                serverWithQueueSpaceIndex = i;
                serverWithQueueSpace = this.servers.get(i);
                break;
            }
        }
        Server newServer = serverWithQueueSpace.addCustomerToQueue(customer);
        ImList<Server> newServers = this.servers.set(serverWithQueueSpaceIndex, newServer);
        ServerBalancer newServerBalancer = new ServerBalancer(
                this.numOfServers, this.qmax, newServers);
        return new Pair<Server, ServerBalancer>(newServer,
                newServerBalancer);
    }

    Server getAvailableServer(double arrivalTime) {
        Server availableServer = this.servers.get(0);
        for (Server server : this.servers) {
            if (server.isFreeAt(arrivalTime)) {
                availableServer = server;
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

    ServerBalancer decrementServerQueue(Customer customer, Server server) {
        ServerBalancer serverBalancer = this;
        if (server.getQSize() > 0) {
            Server currentServer = this.servers.get(server.getServerIndexNumber());
            Server newServer = currentServer.removeCustomerFromQueue(customer);
            ImList<Server> newServers = this.servers.set(
                    newServer.getServerIndexNumber(),
                    newServer);
            serverBalancer = new ServerBalancer(this.numOfServers, this.qmax, newServers);
        }
        return serverBalancer;
    }

    ServerBalancer finishServingCustomer(Customer customer, Server server) {
        ServerBalancer serverBalancer = this;
        Server currentServer = this.servers.get(server.getServerIndexNumber());
        if (currentServer.getQSize() > 0) {
            Server newServer = currentServer.finishServingCustomer(customer);
            ImList<Server> newServers = this.servers.set(
                    newServer.getServerIndexNumber(),
                    newServer);
            serverBalancer = new ServerBalancer(this.numOfServers, this.qmax, newServers);
        }
        return serverBalancer;
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
            totalCustomersServed += server.getCustomersServed();
        }
        return totalCustomersServed;
    }

    boolean canExecute(Event event) {
        return event.canExecute(this.servers);
    }

    void checkWhichServersCanExecute() {
        for (Server server : this.servers) {
            System.out.println(String.format(("%d %b"), server.getServerNumber(), server.isAvailable()));
        }
    }

    Server getServerWithIndex(int index) {
        return this.servers.get(index);
    }

    @Override
    public String toString() {
        for (Server server : this.servers) {
            System.out.print(String.format("server %s, in queue: %d | ",
                    server, server.getQSize()));
        }
        return "";
    }
}
