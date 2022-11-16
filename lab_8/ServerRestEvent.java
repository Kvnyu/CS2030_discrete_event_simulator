class ServerRestEvent extends AssignedEvent {
    ServerRestEvent(Customer customer, int serverNumber, String serverName, double eventTime) {
        super(customer, serverNumber, serverName, false, HIGH_PRIORITY, eventTime, false);
    }

    @Override
    Pair<Event, ServerBalancer> getNextEvent(ServerBalancer serverBalancer) {
        AbstractServer server = serverBalancer.getServer(serverNumber);
        server = server.returnFromRest(serverNumber);
        Event event = new TerminalEvent(this.customer);
        serverBalancer = serverBalancer.updateServer(server);
        return new Pair<Event, ServerBalancer>(event, serverBalancer);
    }
}
