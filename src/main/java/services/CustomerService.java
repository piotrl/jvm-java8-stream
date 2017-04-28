package services;

import java.util.List;
import java.util.stream.Collectors;

import entities.Customer;
import entities.Product;

public class CustomerService implements CustomerServiceInterface {

    private List<Customer> customers;

    public CustomerService(List<Customer> customers) {
        this.customers = customers;
    }

    @Override
    public List<Customer> findByName(String name) {
        return customers.stream()
                .filter(customer -> customer.getName().equals(name))
                .collect(Collectors.toList());
    }

    @Override
    public List<Customer> findByField(String fieldName, Object value) {
        return customers.stream()
                .filter(c -> isEqualByField(fieldName, value, c))
                .collect(Collectors.toList());
    }

    @Override
    public List<Customer> customersWhoBoughtMoreThan(int number) {
        return customers.stream()
                .filter(c -> c.getBoughtProducts().size() > number)
                .collect(Collectors.toList());
    }

    @Override
    public List<Customer> customersWhoSpentMoreThan(double price) {
        return customers.stream()
                .filter(c -> c.getBoughtProducts().stream()
                        .map(Product::getPrice)
                        .reduce(0.0, Double::sum) > price
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<Customer> customersWithNoOrders() {
        return customers.stream()
                .filter(c -> c.getBoughtProducts().isEmpty())
                .collect(Collectors.toList());
    }

    @Override
    public void addProductToAllCustomers(Product p) {
        customers.forEach(c -> c.getBoughtProducts().add(p));
    }

    @Override
    public double avgOrders(boolean includeEmpty) {
        Integer sumOfAllOrders = customers.stream()
                .map(customer -> customer.getBoughtProducts().size())
                .reduce(0, Integer::sum);
        return 1.0 * sumOfAllOrders / customers.size();
    }

    @Override
    public boolean wasProductBought(Product p) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<Product> mostPopularProduct() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int countBuys(Product p) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int countCustomersWhoBought(Product p) {
        // TODO Auto-generated method stub
        return 0;
    }

    private boolean isEqualByField(String fieldName, Object value, Customer c) {
        try {
            return Customer.class.getDeclaredField(fieldName).get(c).equals(value);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return false;
    }


}
