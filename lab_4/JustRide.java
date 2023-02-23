class JustRide extends Service {
    private static final int FARE_PER_KM = 22;
    private static final int SURCHARGE_AMOUNT = 500;
    private static final int SURCHARGE_START_TIME = 600;
    private static final int SURCHARGE_END_TIME = 900;
    private static final int BOOKING_FEE = 0;

    JustRide() {
        super(FARE_PER_KM, SURCHARGE_AMOUNT, SURCHARGE_START_TIME,
                SURCHARGE_END_TIME, BOOKING_FEE);
    }

    @Override
    public String toString() {
        return "JustRide";
    }
}
