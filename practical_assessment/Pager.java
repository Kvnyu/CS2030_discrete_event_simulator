class Pager extends Term {
    Pager(String name) {
        super(name);
    }

    Pager(String name, String transmitterName) {
        super(name, transmitterName);
    }

    ReceivedTransmitter snd(SendableHost host) {
        if (host.connectionEstablished()) {
            return new ReceivedTransmitter(host.getName(), this.getName(), false, host.getPagerName(),
                    host.getConnections());
        }
        return new ReceivedTransmitter(host.getName(), this.getName());
    }
}
