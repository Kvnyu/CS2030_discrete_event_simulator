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

            Customer customer = new Customer(arrivalTime, serviceTime);
            if (customerCount == 1){
                server = new Server(name, arrivalTime);
            }
            if (server.isFreeAt(customer.getArrivalTime())){
                server = server.serve(customer);
                System.out.println("customer " + customerCount + " served by " + server);
            }
            else {
                System.out.println("customer " + customerCount + " left");
            }
        }

        sc.close();
    }
}
