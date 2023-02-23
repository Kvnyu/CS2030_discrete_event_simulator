import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();
        Server server = new Server(name);
        int customerCount = 0;

        while (sc.hasNextDouble()) {
            customerCount++;
            double arrivalTime = sc.nextDouble();
            double serviceTime = sc.nextDouble();

            Customer customer = new Customer(arrivalTime, serviceTime, customerCount);
            ArriveEvent arriveEvent = new ArriveEvent(customer, server);
            System.out.println(arriveEvent);
            Event nextEvent = arriveEvent.getNextEvent();
            System.out.println(nextEvent);
            server = nextEvent.getServer();
        }

        sc.close();
    }
}
