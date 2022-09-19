abstract class Driver {
    private final String licensePlate;
    private final int passengerWaitingTime;
    private final ImList<Service> services;

    Driver(String licensePlate, int passengerWaitingTime, ImList<Service> services) {
        this.licensePlate = licensePlate;
        this.passengerWaitingTime = passengerWaitingTime;
        this.services = services;
    }

    protected final ImList<Service> getServices() {
        return this.services;
    }

    protected String getStringRepresentation(String className) {
        return String.format("%s (%d mins away) %s",
                this.licensePlate, this.passengerWaitingTime, className);
    }

    protected final int getPassengerWaitingTime() {
        return this.passengerWaitingTime;
    }
}
