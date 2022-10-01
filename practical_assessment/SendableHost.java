abstract class SendableHost extends Host {
    SendableHost(String name) {
        super(name, "", false);
    }

    SendableHost(String name, String pagerName) {
        super(name, pagerName, false);
    }

    SendableHost(String name, String pagerName, Boolean connectionEstablished) {
        super(name, pagerName, connectionEstablished, new ImList<String>());
    }

    SendableHost(String name, String pagerName, Boolean connectionEstablished,
            ImList<String> additionalConnections) {
        super(name, pagerName, connectionEstablished, "", additionalConnections);
    }

    SendableHost(String name, String pagerName, Boolean connectionEstablished, String connectedPager,
            ImList<String> additionalConnections) {
        super(name, pagerName, connectionEstablished, connectedPager, additionalConnections);
    }
}
