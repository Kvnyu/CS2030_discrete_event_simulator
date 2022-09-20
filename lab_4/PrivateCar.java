class PrivateCar extends Driver {
    PrivateCar(String licensePlate, int waitTime) {
        super(licensePlate, waitTime,
                new ImList<Service>().add(new JustRide()).add(new ShareARide()), "PrivateCar");
    }

}
