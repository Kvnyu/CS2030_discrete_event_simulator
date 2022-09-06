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
            ArriveEvent arriveEvent = new ArriveEvent(customer);
            System.out.println(arriveEvent);

            ServerEvent serverEvent = server.handleCustomer(customer);
            server = serverEvent.getServer();
            System.out.println(serverEvent.getEvent());
        }

        sc.close();
    }
}
