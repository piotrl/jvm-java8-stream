package services;

import entities.Customer;
import entities.Product;
import org.junit.Test;
import tests.DataProducer;

import java.util.List;

import static org.junit.Assert.*;

public class CustomerServiceTest {

    @Test
    public void testFindByName() {
        CustomerServiceInterface cs = new CustomerService(DataProducer.getTestData(10));

        List<Customer> res = cs.findByName("Customer: 1");

        assertNotNull("Result can't be null", res);
        assertEquals(1, res.size());
        assertEquals(res.get(0).getName(), "Customer: 1");
    }

    @Test
    public void findByFieldName() throws Exception {
        CustomerServiceInterface cs = new CustomerService(DataProducer.getTestData(10));

        List<Customer> customersWithName = cs.findByField("name", "Customer: 1");

        assertNotNull("Result can't be null", customersWithName);
        assertEquals(1, customersWithName.size());
        assertEquals("Customer: 1", customersWithName.get(0).getName());
    }

    @Test
    public void findByFieldId() throws Exception {
        CustomerServiceInterface cs = new CustomerService(DataProducer.getTestData(10));

        List<Customer> customers = cs.findByField("id", 1);

        assertNotNull("Result can't be null", customers);
        assertEquals(1, customers.size());
        assertEquals(1, customers.get(0).getId());
    }

    @Test
    public void customersWhoBoughtMoreThan3Products() throws Exception {
        CustomerServiceInterface cs = new CustomerService(DataProducer.getTestData(5));

        List<Customer> customers = cs.customersWhoBoughtMoreThan(3);

        assertNotNull("Result can't be null", customers);
        assertEquals(2, customers.size());
    }

    @Test
    public void customersWhoSpentMoreThan1Dolar() throws Exception {
        CustomerServiceInterface cs = new CustomerService(DataProducer.getTestData(5));

        List<Customer> customers = cs.customersWhoSpentMoreThan(1);

        assertNotNull("Result can't be null", customers);
        assertEquals(2, customers.size());
    }

    @Test
    public void customersWithNoOrders() throws Exception {
        CustomerServiceInterface cs = new CustomerService(DataProducer.getTestData(5));

        List<Customer> customers = cs.customersWithNoOrders();

        assertNotNull("Result can't be null", customers);
        assertEquals(1, customers.size());
    }

    @Test
    public void addProductToAllCustomers() throws Exception {
        CustomerServiceInterface cs = new CustomerService(DataProducer.getTestData(5));
        Product fakeProduct = new Product(1234, "Fake product", 1336);

        boolean wasFakeProductBought = cs.wasProductBought(fakeProduct);
        cs.addProductToAllCustomers(fakeProduct);
        boolean wasFakeProductBoughtAfterAdding = cs.wasProductBought(fakeProduct);

        assertFalse(wasFakeProductBought);
        assertTrue(wasFakeProductBoughtAfterAdding);
    }

    @Test
    public void countBuys() throws Exception {
        CustomerServiceInterface cs = new CustomerService(DataProducer.getTestData(5));
        Product fakeProduct = new Product(1234, "Fake product", 1336);

        int buysBeforeAdd = cs.countBuys(fakeProduct);
        cs.addProductToAllCustomers(fakeProduct);
        int buysAfterAdd = cs.countBuys(fakeProduct);

        assertEquals(0, buysBeforeAdd);
        assertEquals(5, buysAfterAdd);
    }
}
