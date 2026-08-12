package lk.ijse.cmjd113.FoodOrder;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class FoodOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodOrderApplication.class, args);
	}

	/**
	 * ModelMapper bean — used by MappingDtoEntity for simple flat-object mapping.
	 * Complex entity graphs are mapped manually to avoid circular references.
	 */

	@Bean
	public ModelMapper mapper() {
		ModelMapper mapper = new ModelMapper();
		// Skip null fields to avoid overwriting existing data on partial updates
		mapper.getConfiguration().setSkipNullEnabled(true);
		return mapper;
	}
}
