package services;

import entities.Customer;
import entities.Product;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class CustomerService implements CustomerServiceInterface {

    private List<Customer> customers;

    public CustomerService(List<Customer> customers) {
        this.customers = customers;
    }

    @Override
    public List<Customer> findByName(String name) {
        return customers.stream()
                .filter(customer -> customer.getName().equals(name))
                .collect(toList());
    }

    @Override
    public List<Customer> findByField(String fieldName, Object value) {
        return customers.stream()
                .filter(c -> isEqualByField(fieldName, value, c))
                .collect(toList());
    }

    @Override
    public List<Customer> customersWhoBoughtMoreThan(int number) {
        return customers.stream()
                .filter(c -> c.getBoughtProducts().size() > number)
                .collect(toList());
    }

    @Override
    public List<Customer> customersWhoSpentMoreThan(double price) {
        return customers.stream()
                .filter(c -> c.getBoughtProducts().stream()
                        .map(Product::getPrice)
                        .reduce(0.0, Double::sum) > price
                )
                .collect(toList());
    }

    @Override
    public List<Customer> customersWithNoOrders() {
        return customers.stream()
                .filter(c -> c.getBoughtProducts().isEmpty())
                .collect(toList());
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
        return customers.stream()
                .map(Customer::getBoughtProducts)
                .anyMatch(c -> c.contains(p));
    }

    @Override
    public List<Product> mostPopularProduct() {
        List<Product> listOfAllProducts = customers.stream()
                .map(Customer::getBoughtProducts)
                .reduce(new ArrayList<>(), (curr, acc) -> {
                    acc.addAll(curr);
                    return acc;
                });

        Map<Product, Long> collect = listOfAllProducts.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        Optional<Map.Entry<Product, Long>> max = collect.entrySet()
                .stream()
                .max(Comparator.comparingLong(Map.Entry::getValue));

        return collect.entrySet()
                .stream()
                .filter(e -> Objects.equals(e.getValue(), max.get().getValue()))
                .map(Map.Entry::getKey)
                .collect(toList());
    }

    @Override
    public int countBuys(Product p) {
        return customers.stream()
                .map(Customer::getBoughtProducts)
                .map(pList -> pList.stream()
                        .filter(p2 -> p2.equals(p))
                        .collect(toList())
                        .size())
                .reduce(0, Integer::sum);
    }

    @Override
    public int countCustomersWhoBought(Product p) {
        List<List<Product>> customersWithProducts = customers.stream()
                .map(Customer::getBoughtProducts)
                .filter(pList -> pList.contains(p))
                .collect(toList());
        return customersWithProducts.size();
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
