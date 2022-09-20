class Booking implements Comparable<Booking> {
    private final Driver driver;
    private final Request request;
    private final Service serviceWithLowestFare;

    public Booking(Driver driver, Request request) {
        this.driver = driver;
        this.request = request;
        this.serviceWithLowestFare = getServiceWithLowestFare(request, driver);
    }

    @Override
    public int compareTo(Booking other) {
        int thisLowestFare = this.getFare();
        int otherLowestFare = other.getFare();

        if (thisLowestFare == otherLowestFare) {
            return this.driver.getPassengerWaitingTime()
                    - other.getDriver().getPassengerWaitingTime();
        }
        return thisLowestFare < otherLowestFare ? -1 : 1;
    }

    protected Service getServiceWithLowestFare() {
        return this.serviceWithLowestFare;
    }

    private Service getServiceWithLowestFare(Request request, Driver driver) {
        Service currentBestService = driver.getServices().get(0);
        int currentBestFare = request.computeFare(currentBestService);
        for (Service service : driver.getServices()) {
            int fare = request.computeFare(service);
            if (fare < currentBestFare) {
                currentBestFare = fare;
                currentBestService = service;
            }
        }
        return currentBestService;
    }

    protected Driver getDriver() {
        return this.driver;
    }

    protected Request getRequest() {
        return this.request;
    }

    protected int getFare() {
        return this.request.computeFare(this.serviceWithLowestFare);
    }

    @Override
    public String toString() {
        return String.format("$%.2f using %s (%s)",
                (float) this.request.computeFare(serviceWithLowestFare) / 100, this.driver,
                serviceWithLowestFare);

    }
}
