package br.edu.uniesp.assistencia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication junta tres anotacoes:
//   @Configuration        -> a classe pode definir beans
//   @EnableAutoConfiguration -> o Boot configura sozinho o que achar no classpath (Tomcat, JPA, Flyway...)
//   @ComponentScan        -> varre ESTE pacote e os subpacotes procurando @RestController, @Service,
//                            @Repository e afins. Por isso toda classe do projeto precisa estar
//                            abaixo de br.edu.uniesp.assistencia para ser encontrada.
@SpringBootApplication
public class AssistenciaUniespApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssistenciaUniespApplication.class, args);
	}

}
