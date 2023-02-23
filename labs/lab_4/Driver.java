class Driver {
    private final String licensePlate;
    private final int passengerWaitingTime;
    private final ImList<Service> services;
    private final String className;

    Driver(String licensePlate, int passengerWaitingTime,
            ImList<Service> services, String className) {
        this.licensePlate = licensePlate;
        this.passengerWaitingTime = passengerWaitingTime;
        this.services = services;
        this.className = className;
    }

    protected final ImList<Service> getServices() {
        return this.services;
    }

    protected String getLicensePlate() {
        return this.licensePlate;
    }

    protected String getClassName() {
        return this.className;
    }

    protected final int getPassengerWaitingTime() {
        return this.passengerWaitingTime;
    }

    @Override
    public String toString() {
        return String.format("%s (%d mins away) %s",
                this.licensePlate, this.passengerWaitingTime, this.className);
    }
}
