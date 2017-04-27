package services;

import entities.Customer;
import org.junit.Test;
import tests.DataProducer;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CustomerServiceTest {

	@Test
	public void testFindByName() {
		CustomerServiceInterface cs = new CustomerService(DataProducer.getTestData(10));
		
		List<Customer> res = cs.findByName("Customer: 1");
		
		assertNotNull("Result can't be null", res);
		assertEquals(1, res.size());
	}

	@Test
	public void findByField() throws Exception {
		CustomerServiceInterface cs = new CustomerService(DataProducer.getTestData(10));

		List<Customer> name = cs.findByField("name", "Customer: 1");

		assertNotNull("Result can't be null", name);
		assertEquals(1, name.size());
	}

}
