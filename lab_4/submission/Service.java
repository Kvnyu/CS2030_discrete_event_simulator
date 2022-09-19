abstract class Service {
    private final int farePerKm;
    private final int surchargeAmount;
    private final int surchargeStartTime;
    private final int surchargeEndTime;
    private final int bookingFee;

    Service(int farePerKm,
            int surchargeAmount,
            int surchargeStartTime,
            int surchargeEndTime,
            int bookingFee) {
        this.farePerKm = farePerKm;
        this.surchargeAmount = surchargeAmount;
        this.surchargeStartTime = surchargeStartTime;
        this.surchargeEndTime = surchargeEndTime;
        this.bookingFee = bookingFee;
    }

    int computeFare(int distance, int numberOfPassengers, int timeOfService) {
        if (timeOfService >= this.surchargeStartTime && timeOfService <= this.surchargeEndTime) {
            return this.surchargeAmount + this.bookingFee + this.farePerKm * distance;
        }
        return this.bookingFee + farePerKm * distance;
    }
}
