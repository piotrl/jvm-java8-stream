package tests;

import java.util.ArrayList;
import java.util.List;

import entities.Customer;
import entities.Product;

/**
 * This is just a proposal for test data generator. You can build your own solution!
 */
public class DataProducer {
	public static List<Customer> getTestData(int count) {
		List<Customer> result = new ArrayList<>();

		for (int i = 0; i < count; i++) {
			Customer c = new Customer(i, "Customer: " + i);
			for (int j = count - i; j > 1; j--) {
				c.addProduct(buildProduct(j));
			}
			result.add(c);
		}
		return result;

	}

	public static Product buildProduct(int id) {
		return new Product(id, "Product: " + id, (double) id * 0.1);
	}
}
