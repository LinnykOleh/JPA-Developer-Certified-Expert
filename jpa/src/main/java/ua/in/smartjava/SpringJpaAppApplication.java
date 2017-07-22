package ua.in.smartjava;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.persistence.EntityManagerFactory;

//TODO add profile for local mysql
@SpringBootApplication
public class SpringJpaAppApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(SpringJpaAppApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringJpaAppApplication.class, args);
	}

//	@Bean
//	CommandLineRunner commandLineRunner(EntityManagerFactory entityManagerFactory) {
//		return args -> {
//			LOGGER.info("Creating the entity manager.");
//			EntityManager entityManager = entityManagerFactory.createEntityManager();
//			Employee employee = new Employee(1);
//			employee.setName("testName");
//			LOGGER.info("Persisting the entity");
//			entityManager.persist(employee);
//		};
//	}

	@Bean
	public javax.validation.Validator localValidatorFactoryBean() {
		return new LocalValidatorFactoryBean();
	}
}
