package de.ketrwu.portfolio;

import de.ketrwu.portfolio.config.ApplicationConfiguration;
import de.ketrwu.portfolio.service.CaptchaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Slf4j
@SpringBootApplication
@EnableAutoConfiguration
@Configuration
@EnableScheduling
@ComponentScan("de.ketrwu.portfolio")
public class PortfolioApplication {

	@Autowired
	private CaptchaService captchaService;

	@Autowired
	private ApplicationConfiguration applicationConfiguration;

	public static void main(String[] args) {
		PortfolioApplication portfolioApplication = new PortfolioApplication();
		SpringApplication.run(PortfolioApplication.class, args)
				.getAutowireCapableBeanFactory()
				.autowireBean(portfolioApplication);
		portfolioApplication.init();
	}

	public void init() {
		captchaService.loadFonts();
	}

	@EventListener(ApplicationReadyEvent.class)
	public void registerCloudProxy() {
		if (applicationConfiguration.getCloud() != null && applicationConfiguration.getCloud().getUrl() != null) {
			ResponseEntity<Void> response = new RestTemplate().postForEntity(
					URI.create(applicationConfiguration.getCloud().getUrl()),
					applicationConfiguration.getCloud(),
					Void.class
			);
			if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
				log.info("Registered as Cloud Proxy Client!");
			} else {
				log.error("Failed to register as Cloud Proxy Client!");
			}
		}
	}

}
