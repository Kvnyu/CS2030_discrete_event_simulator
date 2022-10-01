abstract class Host {
    private final String name;
    private final String pagerName;
    private final Boolean connectionEstablished;
    private final ImList<String> completedHandshakes;

    Host(String name) {
        this(name, "", false);
    }

    Host(String name, String pagerName) {
        this(name, pagerName, false);
    }

    Host(String name, String pagerName, Boolean connectionEstablished) {
        this(name, pagerName, false, new ImList<String>());
    }

    Host(String name, String pagerName, Boolean connectionEstablished, ImList<String> additionalConnections) {
        this.name = name;
        this.pagerName = pagerName;
        this.connectionEstablished = connectionEstablished;
        this.completedHandshakes = new ImList<String>();
        if (this.connectionEstablished) {
            this.completedHandshakes.add(name);
        }
        for (String host : additionalConnections) {
            this.completedHandshakes.add(host);
        }
    }

    ImList<String> getConnections() {
        return this.completedHandshakes;
    }

    Boolean connectionEstablished() {
        return this.connectionEstablished;
    }

    void broadcast() {
        for (String host : this.completedHandshakes) {
            System.out.println(String.format("%s: beep", host));
        }
    }

    PagerPreack rcv() {
        return new PagerPreack(this.pagerName, this.name);
    }

    protected final String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Host) {
            Host newOther = (Host) other;
            return newOther.name == this.name;
        }
        return other.equals(this);
    }

    @Override
    public String toString() {
        if (this.pagerName != "") {
            if (this.connectionEstablished) {
                return String.format("%s >--snd--> %s >--rcv--> %s >--ack--> %s", this.pagerName, this.name,
                        this.pagerName, this.name);
            }
            return String.format("%s >--snd--> %s", this.pagerName, this.name);
        }
        return this.name;
    }
}
