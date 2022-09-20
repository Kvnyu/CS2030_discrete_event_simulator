void findBestBooking(Request request, List<Driver> driversList){
    ImList<Driver> drivers = new ImList<Driver>().addAll(driversList);
    Iterator<Driver> iter = drivers.iterator();
    ImList<Booking> bookings = new ImList<Booking>();
    while(iter.hasNext()){
        Driver driver = iter.next();
        for (Service service : driver.getServices()){
            Driver newDriver = new Driver(driver.getLicensePlate(), driver.getPassengerWaitingTime(),
                new ImList<Service>().add(service), driver.getClassName());
            Booking booking = new Booking(newDriver, request);
            bookings = bookings.add(booking);
        }
    }
    ImList<Booking> sortedBookings = bookings.sort(new BookingComparator());
    Iterator<Booking> sortedBookingIter = sortedBookings.iterator();
    while (sortedBookingIter.hasNext()){
        System.out.println(sortedBookingIter.next());
    }
}
