class ServerBalancer {
    private final int numOfServers;
    private final int qmax;
    private final ImList<Server> servers;

    ServerBalancer(int numOfServers, int qmax) {
        this.numOfServers = numOfServers;
        this.qmax = qmax;

        ImList<Server> servers = new ImList<Server>();

        for (int i = 0; i < this.numOfServers; i++) {
            servers = servers.add(new Server(Integer.toString(i + 1), this.qmax));
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

    Pair<Event, ServerBalancer> serve(Customer customer) {
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
        ServerBalancer newServerBalancer = new ServerBalancer(this.numOfServers, this.qmax, newServers);
        return new Pair<Event, ServerBalancer>(new ServeEvent(customer, newServer, customer.getArrivalTime()),
                newServerBalancer);
    }

    Pair<Event, ServerBalancer> serve(Customer customer, Server server) {
        Server newServer = server.serveFromQueue(customer);
        ImList<Server> newServers = this.servers.set(this.servers.indexOf(server), newServer);
        ServerBalancer newServerBalancer = new ServerBalancer(this.numOfServers, this.qmax, newServers);
        return new Pair<Event, ServerBalancer>(
                new ServeEvent(customer, newServer, newServer.getAvailableTime()),
                newServerBalancer);
    }

    Pair<Event, ServerBalancer> addToQueue(Customer customer) {
        int serverWithQueueSpaceIndex = 0;
        Server serverWithQueueSpace = this.servers.get(serverWithQueueSpaceIndex);
        for (int i = 0; i < this.servers.size(); i++) {
            if (this.servers.get(i).hasSpaceInQueue()) {
                serverWithQueueSpaceIndex = i;
                serverWithQueueSpace = this.servers.get(i);
            }
        }
        Server newServer = serverWithQueueSpace.addCustomerToQueue(customer.getServiceTime());
        ImList<Server> newServers = this.servers.set(serverWithQueueSpaceIndex, newServer);
        ServerBalancer newServerBalancer = new ServerBalancer(this.numOfServers, this.qmax, newServers);
        return new Pair<Event, ServerBalancer>(
                new WaitEvent(customer, newServer, serverWithQueueSpace.getAvailableTime()), newServerBalancer);
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

    ServerBalancer decrementServerQueue(Server server) {
        ServerBalancer serverBalancer = this;
        if (server.getQSize() > 0) {
            Server newServer = server.removeCustomerFromQueue();
            int newServerIndex = this.servers.indexOf(server);
            ImList<Server> newServers = this.servers.set(newServerIndex, newServer);
            serverBalancer = new ServerBalancer(this.numOfServers, this.qmax, newServers);
        }
        return serverBalancer;
    }

}
