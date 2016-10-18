package ua.org.smartjava;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import ua.org.smartjava.domain.Employee;
//TODO add profile for local mysql
@SpringBootApplication
public class SpringJpaAppApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(SpringJpaAppApplication.class);

	@Autowired
	EntityManagerFactory entityManagerFactory;

	public static void main(String[] args) {
		SpringApplication.run(SpringJpaAppApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(EntityManagerFactory entityManagerFactory) {
		return args -> {
			LOGGER.info("Creating the entity manager.");
			EntityManager entityManager = entityManagerFactory.createEntityManager();
			Employee employee = new Employee(1);
			employee.setName("testName");
			LOGGER.info("Persisting the entity");
			entityManager.persist(employee);
		};
	}
}
