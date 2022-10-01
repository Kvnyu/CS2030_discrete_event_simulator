class ConnectedHost extends Host {
    ConnectedHost(String name) {
        super(name);
    }

    ConnectedHost(String name, String pagerName) {
        super(name, pagerName);
    }

    ConnectedHost(String name, String pagerName, Boolean connectionEstablished) {
        super(name, pagerName, connectionEstablished);
    }

    ConnectedHost(String name, String pagerName, Boolean connectionEstablished, ImList<String> connections) {
        super(name, pagerName, connectionEstablished, connections);
    }
}
