class Transmitter extends Host {
    Transmitter(String name) {
        super(name);
    }

    Transmitter(String name, String pagerName) {
        super(name, pagerName);
    }

    Transmitter(String name, String pagerName, Boolean connectionEstablished) {
        super(name, pagerName, connectionEstablished);
    }

    Transmitter(String name, String pagerName, Boolean connectionEstablished, ImList<String> connections) {
        super(name, pagerName, connectionEstablished, connections);
    }
}
