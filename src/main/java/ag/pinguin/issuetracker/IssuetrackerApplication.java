package ag.pinguin.issuetracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class IssuetrackerApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(IssuetrackerApplication.class, args);
	}
	//External Tomcat config
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(IssuetrackerApplication.class);
	}
}
