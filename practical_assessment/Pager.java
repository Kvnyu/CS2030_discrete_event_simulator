class Pager extends Term {
    Pager(String name) {
        super(name);
    }

    Pager(String name, String transmitterName) {
        super(name, transmitterName);
    }

    ReceivedTransmitter snd(Host host) {
        if (host.connectionEstablished()) {
            return new ReceivedTransmitter(host.getName(), this.getName(), true, host.getConnections());
        }
        return new ReceivedTransmitter(host.getName(), this.getName());
    }
}
