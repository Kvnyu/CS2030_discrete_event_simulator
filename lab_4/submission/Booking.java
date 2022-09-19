import java.util.Iterator;

class Booking implements Comparable<Booking> {
    private final Driver driver;
    private final Request request;

    public Booking(Driver driver, Request request) {
        this.driver = driver;
        this.request = request;
    }

    @Override
    public int compareTo(Booking other) {
        Service thisLowestFareService = this.getServiceWithLowestFare(this.request, this.driver);
        Service otherLowestFareService = this.getServiceWithLowestFare(
                other.getRequest(), other.getDriver());
        int thisLowestFare = this.request.computeFare(thisLowestFareService);
        int otherLowestFare = this.request.computeFare(otherLowestFareService);

        if (thisLowestFare == otherLowestFare) {
            return this.driver.getPassengerWaitingTime()
                    - other.getDriver().getPassengerWaitingTime();
        }
        return thisLowestFare < otherLowestFare ? -1 : 1;
    }

    private Service getServiceWithLowestFare(Request request, Driver driver) {
        int minFare = 0;
        Iterator<Service> driverServicesIter = driver.getServices().iterator();
        Service currentBestService = driverServicesIter.next();
        while (driverServicesIter.hasNext()) {
            int currentBestFare = request.computeFare(currentBestService);
            Service service = driverServicesIter.next();
            int fare = request.computeFare(service);
            if (fare < currentBestFare) {
                minFare = fare;
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

    @Override
    public String toString() {
        Service lowestFareService = this.getServiceWithLowestFare(this.request, this.driver);
        return String.format("$%.2f using %s (%s)",
                (float) this.request.computeFare(lowestFareService) / 100, this.driver,
                lowestFareService);

    }
}
