class ReceivedTransmitter extends Host {
    ReceivedTransmitter(String name) {
        super(name);
    }

    ReceivedTransmitter(String name, String pagerName) {
        super(name, pagerName);
    }

    ReceivedTransmitter(String name, String pagerName, Boolean connectionEstablished) {
        super(name, pagerName, connectionEstablished);
    }

    ReceivedTransmitter(String name, String pagerName, Boolean connectionEstablished, ImList<String> connections) {
        super(name, pagerName, connectionEstablished, connections);
    }

    ReceivedTransmitter(String name, String pagerName, Boolean connectionEstablished, String connectedPager,
            ImList<String> connections) {
        super(name, pagerName, connectionEstablished, connectedPager, connections);
    }

    PagerPreack rcv() {
        System.out.println(this.getConnections());
        return new PagerPreack(this.getPagerName(), this.getName(), this.getConnections());
    }
}
