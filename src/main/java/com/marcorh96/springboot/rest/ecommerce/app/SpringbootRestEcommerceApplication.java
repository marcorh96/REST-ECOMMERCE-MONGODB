package com.marcorh96.springboot.rest.ecommerce.app;

import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.marcorh96.springboot.rest.ecommerce.app.models.dao.AddressRepository;
import com.marcorh96.springboot.rest.ecommerce.app.models.dao.CategoryRepository;
import com.marcorh96.springboot.rest.ecommerce.app.models.dao.ManufacturerRepository;
import com.marcorh96.springboot.rest.ecommerce.app.models.dao.PersonRepository;
import com.marcorh96.springboot.rest.ecommerce.app.models.dao.ShippingAddressRepository;
import com.marcorh96.springboot.rest.ecommerce.app.models.document.Address;
import com.marcorh96.springboot.rest.ecommerce.app.models.document.Category;
import com.marcorh96.springboot.rest.ecommerce.app.models.document.ShippingAddress;
import com.marcorh96.springboot.rest.ecommerce.app.models.document.Order;
import com.marcorh96.springboot.rest.ecommerce.app.models.document.OrderItem;
import com.marcorh96.springboot.rest.ecommerce.app.models.document.Product;
import com.marcorh96.springboot.rest.ecommerce.app.models.document.Role;
import com.marcorh96.springboot.rest.ecommerce.app.models.document.User;
import com.marcorh96.springboot.rest.ecommerce.app.models.document.Person;
import com.marcorh96.springboot.rest.ecommerce.app.models.document.Manufacturer;

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
	private CategoryRepository categoryRepository;
	@Autowired
	private ShippingAddressRepository shippingAddressRepository;
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private ManufacturerRepository manufacturerRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootRestEcommerceApplication.class, args);
	}

	@Bean
	CommandLineRunner runner() {
		return args -> {

			/*
			 * mongoTemplate.dropCollection("orders");
			 * mongoTemplate.dropCollection("products");
			 * mongoTemplate.dropCollection("users");
			 */

			Person person = new Person("Carlos", "Cantu", new Date(96, 0, 22), "8123456787");
			Person person2 = new Person("Christopher", "Lopez", new Date(95, 9, 12), "8119085673");
			Person person3 = new Person("Manuu", "Herrera", new Date(98, 11, 12), "8118080460");
			Person person4 = new Person("Marco", "Hernandez", new Date(96, 8, 20), "8197654360");
			personRepository.saveAll(Arrays.asList(person, person2, person3, person4));

			Address address = new Address("Pedregal", "Monterrey", "Nuevo León", "Mexico", "55096");
			Address address2 = new Address("San pedro", "Guadalupe", "Nuevo León", "Mexico", "55096");
			Address address3 = new Address("San Agustin", "General Escobedo", "Nuevo León", "Mexico", "55096");
			addressRepository.saveAll(Arrays.asList(address, address2, address3));

			Category category = new Category("Smartphone", "New");
			categoryRepository.save(category);

			Manufacturer manufacturer = new Manufacturer("apple", "iphone", "Apple California", "AT&T Mexico");

			Manufacturer manufacturer2 = new Manufacturer("samsung", "Samsung Galaxy", "Samsung", "Telcel Mexico");
			manufacturerRepository.saveAll(Arrays.asList(manufacturer, manufacturer2));
			Product product1 = new Product("IPhone 14 Pro Max", "El nuevo iphone 14 pro max", category,
					Arrays.asList("Procesador A16", "6.5 Pulgadas", "256gb", "6gb RAM"), 25000.00, 200,
					"Space Gray", manufacturer, "iphone14-pro-max.jpg");
			Product product2 = new Product("IPhone 13 Pro Max", "El viejo iphone 13 pro max", category,
					Arrays.asList("Procesador A15", "6.5 Pulgadas", "256gb", "5gb RAM"), 20000.00, 50,
					"Black Space", manufacturer, "iphone13-pro-max.jpg");
			Product product3 = new Product("IPhone 12 Pro Max", "El nuevo iphone 12 pro max", category,
					Arrays.asList("Procesador A14", "6.25 Pulgadas", "128gb", "4gb RAM"), 12500.00, 100,
					"Red", manufacturer, "iphone12-pro-max.jpg");
			Product product4 = new Product("IPhone 11 Pro Max", "El nuevo iphone 11 pro max", category,
					Arrays.asList("Procesador A13", "6 Pulgadas", "64gb", "4gb RAM"), 10000.00, 10,
					"Dark blue", manufacturer, "iphone11-pro-max.jpg");
			Product product5 = new Product("Samsung s23 Plus Ultra", "El nuevo samsung s23 plus ultra", category,
					Arrays.asList("Procesador SnapDragon 865", "7 Pulgadas", "512gb", "12gb RAM"),
					25000.00, 200, "Purple", manufacturer2, "samsung-s23-ultra.png");
			Product product6 = new Product("Samsung s22 Plus Ultra", "El nuevo samsung s22 plus ultra", category,
					Arrays.asList("Procesador SnapDragon 855", "6.5 Pulgadas", "256gb", "8gb RAM"),
					20000.00, 150, "White", manufacturer2, "samsung-s22-ultra.png");
			Product product7 = new Product("Samsung s21 Plus Ultra", "El nuevo samsung s21 plus ultra", category,
					Arrays.asList("Procesador SnapDragon 845", "6.48 Pulgadas", "128gb", "6gb RAM"),
					12500.00, 100, "Black", manufacturer2, "samsung-s21-ultra.png");
			Product product8 = new Product("Samsung s20 Plus Ultra", "El nuevo samsung s20 plus ultra", category,
					Arrays.asList("Procesador SnapDragon 825", "6.2 Pulgadas", "64gb", "4gb RAM"),
					10000.00, 10, "Dark blue", manufacturer2, "samsung-s20-ultra.png");
			Product product9 = new Product("Samsung s20 Plus Ultra", "El nuevo samsung s20 plus ultra", category,
					Arrays.asList("Procesador SnapDragon 825", "6.2 Pulgadas", "64gb", "4gb RAM"),
					10000.00, 10, "Dark blue", manufacturer2, "samsung-s21-ultra.png");

			ShippingAddress shippingAddress = new ShippingAddress("La colmena", "Iztapalapas", "CdMX", "Mexico",
					102030);
			ShippingAddress shippingAddress2 = new ShippingAddress("La colmena", "Iztapalapas", "CdMX", "Mexico",
					102030);
			ShippingAddress shippingAddress3 = new ShippingAddress("La Juanja", "Iztapalapas", "CdMX", "Mexico", 10202);
			shippingAddressRepository.saveAll(Arrays.asList(shippingAddress, shippingAddress2, shippingAddress3));

			User user1 = new User(person, "carlos@gmail.com",
					"$2a$10$Tmz.mt/RjhkJlPAIjoYZ.egr//PVmYuen8EqdiyNZcqAxlEPo7lCq",
					address, Role.ROLE_USER);

			User user2 = new User(person2, "christopher@gmail.com",
					"$2a$10$eD2Nl9dEmphhiLGv4xy3NuhFObs3zXPMVUM4S9AGHTDMmAWhxlasW",
					address2, Role.ROLE_USER);

			User user3 = new User(person3, "mannuuu@gmail.com",
					"$2a$10$eD2Nl9dEmphhiLGv4xy3NuhFObs3zXPMVUM4S9AGHTDMmAWhxlasW",
					address3, Role.ROLE_ADMIN);

			User user4 = new User(person4, "marco@gmail.com",
					"$2a$10$eD2Nl9dEmphhiLGv4xy3NuhFObs3zXPMVUM4S9AGHTDMmAWhxlasW",
					address3, Role.ROLE_ADMIN);

			Order order = new Order(
					user1,
					Arrays.asList(new OrderItem(product1, 1)),
					"Pending", shippingAddress);
			Order order2 = new Order(
					user2,
					Arrays.asList(new OrderItem(product1, 1),
							new OrderItem(product3, 5)),
					"Completed", shippingAddress2);

			Order order3 = new Order(
					user3,
					Arrays.asList(new OrderItem(product2, 2),
							new OrderItem(product1, 1)),
					"In Hold", shippingAddress3);

			productService.saveAll(
					Arrays.asList(product1, product2, product3, product4, product5, product6, product7, product8,
							product9, product1, product2, product3, product4, product5, product6, product7, product8,
							product9, product1, product2, product3, product4, product5, product6, product7, product8,
							product9, product1, product2, product3, product4, product5, product6, product7, product8,
							product9, product1, product2, product3, product4, product5, product6, product7, product8,
							product9, product1, product2, product3, product4, product5, product6, product7, product8,
							product9, product1, product2, product3, product4, product5, product6, product7, product8,
							product9, product1, product2, product3, product4, product5, product6, product7, product8,
							product9));
			userService.saveAll(Arrays.asList(user1, user2, user3, user4));
			orderService.saveAll(Arrays.asList(order, order2, order3));

		};
	}

}
