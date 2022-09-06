class ServerEvent {
    private final Server server;
    private final Event event;

    ServerEvent(Server server, Event event) {
        this.server = server;
        this.event = event;
    }

    Server getServer() {
        return this.server;
    }

    Event getEvent() {
        return this.event;
    }
}
