class PagerPreack extends Term {
    private final ImList<String> completedHandshakes;

    PagerPreack(String name) {
        super(name);
        this.completedHandshakes = new ImList<String>();
    }

    PagerPreack(String name, String transmitterName, ImList<String> completedHandshakes) {
        super(name, transmitterName);
        this.completedHandshakes = completedHandshakes;
    }

    ConnectedHost ack() {
        System.out.println("ack" + this.completedHandshakes);
        return new ConnectedHost(this.getTransmitterName(), this.getName(), true, this.completedHandshakes);
    }
}
