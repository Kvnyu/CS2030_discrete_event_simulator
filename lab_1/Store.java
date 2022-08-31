import java.util.ArrayList;

class Store {
    private final Server server;
    private final ArrayList<Customer> customers; 

    Store(Server server){
        this.customers = new ArrayList<Customer>();
        this.server = server;
    }

    Store(Server server, ArrayList<Customer> customers){
        this.customers = customers;
        this.server = server;
    }

    Store admitCustomer(Customer customer){
        ArrayList<Customer> newCustomers = new ArrayList<Customer>(this.customers);
        newCustomers.add(customer);
        return new Store(this.server, newCustomers);
    }

    void processCustomers(){
        for (int i = 0; i < this.customers.size(); i++){
            Customer customer = this.customers.get(i);
            if (this.server.isFreeAt(customer.getArrivalTime())){
                this.server = this.server.serve(customer);
                System.out.println("\ncustomer " + i + " served by " + server + "\n");
            }
            else {
                System.out.println("\ncustomer " + i + " left\n");
            }
        }
    }

    @Override
    public String toString(){
        return "Server: " + this.server + "\n" + "Number of customers: " + this.customers.size();
    }
}
