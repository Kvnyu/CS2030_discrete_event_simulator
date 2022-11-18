class ServerRestEvent extends AssignedEvent {
    ServerRestEvent(String serverName, Customer customer, int serverNumber, double eventTime) {
        super(serverName, customer, serverNumber, false, HIGH_PRIORITY, eventTime, false);
    }

    @Override
    Pair<Event, ServerBalancer> getNextEvent(ServerBalancer serverBalancer) {
        Server server = serverBalancer.getServer(serverNumber);
        // System.out.println(server.getCustomers());
        server = server.returnFromRest();
        Event event = new TerminalEvent(this.customer);
        serverBalancer = serverBalancer.updateServer(server);
        return new Pair<Event, ServerBalancer>(event, serverBalancer);
    }
}
