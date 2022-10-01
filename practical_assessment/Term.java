abstract class Term {
    private final String name;
    private final String transmitterName;

    Term(String name) {
        this.name = name;
        this.transmitterName = "";
    }

    Term(String name, String transmitterName) {
        this.name = name;
        this.transmitterName = transmitterName;
    }

    protected String getTransmitterName() {
        return this.transmitterName;
    }

    protected String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Term) {
            Term newOther = (Term) other;
            return newOther.name == this.name;
        }
        return false;
    }

    @Override
    public String toString() {
        if (this.transmitterName != "") {
            return String.format("%s >--snd--> %s >--rcv--> %s", this.name, this.transmitterName, this.name);
        }
        return this.name;
    }
}
