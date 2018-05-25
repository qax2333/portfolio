package de.ketrwu.portfolio;

import de.ketrwu.portfolio.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAutoConfiguration
@Configuration
@EnableScheduling
@ComponentScan("de.ketrwu.portfolio")
public class PortfolioApplication {

	@Autowired
	private CaptchaService captchaService;

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

}
