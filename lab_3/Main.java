import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();
        Server server = new Server(name);
        int customerCount = 0;
        PQ<Event> queue = new PQ<Event>(new EventComparator());

        while (sc.hasNextDouble()) {
            customerCount++;
            double arrivalTime = sc.nextDouble();
            double serviceTime = sc.nextDouble();

            Customer customer = new Customer(arrivalTime, serviceTime, customerCount);
            queue = queue.add(new ArriveEvent(customer, server));
        }
        while (!queue.isEmpty()) {
            Pair<Event, PQ<Event>> pair = queue.poll();
            Event event = pair.first();
            queue = pair.second();

            System.out.println(event);
            if (event.hasNextEvent) {
                NonTerminalEvent nonTerminalEvent = (NonTerminalEvent) event;
                queue = queue.add(nonTerminalEvent.getNextEvent());
            }
        }

        sc.close();
    }
}
