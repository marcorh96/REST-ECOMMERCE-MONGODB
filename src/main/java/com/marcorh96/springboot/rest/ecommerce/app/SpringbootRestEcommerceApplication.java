package com.marcorh96.springboot.rest.ecommerce.app;

import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.marcorh96.springboot.rest.ecommerce.app.models.document.Address;
import com.marcorh96.springboot.rest.ecommerce.app.models.document.ShippingAddress;
import com.marcorh96.springboot.rest.ecommerce.app.models.document.Order;
import com.marcorh96.springboot.rest.ecommerce.app.models.document.OrderItem;
import com.marcorh96.springboot.rest.ecommerce.app.models.document.Product;
import com.marcorh96.springboot.rest.ecommerce.app.models.document.Role;
import com.marcorh96.springboot.rest.ecommerce.app.models.document.User;
import com.marcorh96.springboot.rest.ecommerce.app.models.document.Person;

import com.marcorh96.springboot.rest.ecommerce.app.models.services.IUserService;
import com.marcorh96.springboot.rest.ecommerce.app.models.services.IProductService;
import com.marcorh96.springboot.rest.ecommerce.app.models.services.IOrderService;

@SpringBootApplication
public class SpringbootRestEcommerceApplication {

	@Autowired
	private IOrderService orderService;
	@Autowired
	private IProductService productService;
	@Autowired
	private IUserService userService;

	@Autowired
	private MongoTemplate mongoTemplate;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootRestEcommerceApplication.class, args);
	}


	@Bean
	CommandLineRunner runner() {
		return args -> {

			/* mongoTemplate.dropCollection("orders");
			mongoTemplate.dropCollection("products");
			mongoTemplate.dropCollection("users"); */

			Person person = new Person("Carlos", "Cantu", new Date(96, 0, 22), "809100820");
			Person person2 = new Person("Christopher", "Lopez", new Date(95, 9, 12), "800559503");
			Person person3 = new Person("Manuu", "Herrera", new Date(98, 11, 12), "005556269");


			Address address = new Address("La colmena", "Iztapalapas", "CdMX", "Mexico", 102030);
			Address address2 = new Address("La colmena", "Iztapalapas", "CdMX", "Mexico", 102030);
			Address address3 = new Address("La Juanja", "Iztapalapas", "CdMX", "Mexico", 10202);

			Product product1 = new Product("IPhone 14 Pro Max", "El nuevo iphone 14 pro max", 25000.00, 200);
			Product product2 = new Product("IPhone 13 Pro Max", "El viejo iphone 13 pro max", 20000.00, 50);
			Product product3 = new Product("IPhone 12 Pro Max", "El nuevo iphone 12 pro max", 12500.00, 100);
			Product product4 = new Product("IPhone 11 Pro Max", "El nuevo iphone 11 pro max", 10000.00, 10);

			ShippingAddress shippingAddress = new ShippingAddress("La colmena", "Iztapalapas", "CdMX", "Mexico", 102030);
			ShippingAddress shippingAddress2 = new ShippingAddress("La colmena", "Iztapalapas", "CdMX", "Mexico", 102030);
			ShippingAddress shippingAddress3 = new ShippingAddress("La Juanja", "Iztapalapas", "CdMX", "Mexico", 10202);

			User user1 = new User(person, "carlitos@gmail.com", "$2a$10$Tmz.mt/RjhkJlPAIjoYZ.egr//PVmYuen8EqdiyNZcqAxlEPo7lCq",
					address, Role.ROLE_USER);

			User user2 = new User(person2, "christo@gmail.com", "$2a$10$eD2Nl9dEmphhiLGv4xy3NuhFObs3zXPMVUM4S9AGHTDMmAWhxlasW",
					address2, Role.ROLE_USER);

			User user3 = new User(person3, "mannuuu1120@gmail.com", "$2a$10$eD2Nl9dEmphhiLGv4xy3NuhFObs3zXPMVUM4S9AGHTDMmAWhxlasW",
					address3, Role.ROLE_ADMIN);

			Order order = new Order(
					user1,
					Arrays.asList(new OrderItem(product1, 1, shippingAddress)),
					"Pending");
			Order order2 = new Order(
					user2,
					Arrays.asList(new OrderItem(product1, 1, shippingAddress2),
							new OrderItem(product3, 5, shippingAddress2)),
					"Completed");

			Order order3 = new Order(
					user3,
					Arrays.asList(new OrderItem(product2, 2, shippingAddress3),
							new OrderItem(product1, 1, shippingAddress3)),
					"In Hold");

			
			productService.saveAll(Arrays.asList(product1, product2, product3, product4));
			userService.saveAll(Arrays.asList(user1, user2, user3));
			orderService.saveAll(Arrays.asList(order, order2, order3));

		};
	}

}
