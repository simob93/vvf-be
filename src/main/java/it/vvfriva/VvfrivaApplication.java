package it.vvfriva;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class VvfrivaApplication extends SpringBootServletInitializer {
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(VvfrivaApplication.class);
    }

	public static void main(String[] args) {
				
		SpringApplication.run(VvfrivaApplication.class, args);
		
	}
	
	@PostConstruct
    public void init(){
//		
//		Date date1 = new Date();
//		System.out.println(date1);
//		
//      // Setting Spring Boot SetTimeZone
//      TimeZone.setDefault(TimeZone.getTimeZone("UTC")); 
      
//      
//      Date date2 = new Date();
//		System.out.println(date2);
    }


}

