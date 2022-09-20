Driver x = new NormalCab("X", 0);
Driver y = new NormalCab("Y", 5);
Driver z = new NormalCab("Z", 1000);

Request firstRequest = new Request(1, 2, 1);
Request secondRequest = new Request(9, 2, 559);
Request thirdRequest = new Request(27, 3, 600);

Booking bookingOne = new Booking(x, firstRequest);
Booking bookingTwo = new Booking(y, firstRequest);
Booking bookingThree = new Booking(z, firstRequest);

Booking bookingFour = new Booking(x, secondRequest);
Booking bookingFive = new Booking(y, secondRequest);
Booking bookingSix = new Booking(z, secondRequest);

Booking bookingSeven = new Booking(x, thirdRequest);
Booking bookingEight = new Booking(y, thirdRequest);
Booking bookingNine = new Booking(z, thirdRequest);