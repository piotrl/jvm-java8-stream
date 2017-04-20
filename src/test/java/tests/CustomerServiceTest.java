package tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import services.CustomerService;
import services.CustomerServiceInterface;
import entities.Customer;

public class CustomerServiceTests {

	@Test
	public void testFindByName() {
		CustomerServiceInterface cs = new CustomerService(DataProducer.getTestData(10));
		
		List<Customer> res = cs.findByName("Customer: 1");
		
		assertNotNull("Result can't be null", res);
		assertEquals(1, res.size());
		
	}

}
