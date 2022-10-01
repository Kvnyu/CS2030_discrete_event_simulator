class PagerPreack extends Term {

    PagerPreack(String name) {
        super(name);
    }

    PagerPreack(String name, String transmitterName) {
        super(name, transmitterName);
    }

    ConnectedHost ack() {
        return new ConnectedHost(this.getTransmitterName(), this.getName(), true);
    }
}
