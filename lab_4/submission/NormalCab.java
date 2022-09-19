class NormalCab extends Driver {
    NormalCab(String licensePlate, int waitTime) {
        super(licensePlate, waitTime,
                new ImList<Service>().add(new JustRide()).add(new TakeACab()));
    }

    @Override
    public String toString() {
        return this.getStringRepresentation("NormalCab");
    }
}
