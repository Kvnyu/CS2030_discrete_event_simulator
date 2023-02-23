class Request {
    private final int distance;
    private final int numberOfPassengers;
    private final int time;

    Request(int distance, int numberOfPassengers, int time) {
        this.distance = distance;
        this.numberOfPassengers = numberOfPassengers;
        this.time = time;
    }

    @Override
    public String toString() {
        return String.format("%dkm for %dpax @ %dhrs",
                this.distance, this.numberOfPassengers, this.time);
    }

    int computeFare(Service service) {
        return service.computeFare(this.distance, this.numberOfPassengers, this.time);
    }
}
