package com.budbreak.pan.service.pan.shiro;//package com.whut.pan.config.shiro;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
//import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//
//import java.util.concurrent.Executors;
//
//@Configuration
//public class WebConfiguration extends WebMvcConfigurationSupport {
//
//	@Override
//	public void addCorsMappings(CorsRegistry registry) {
//		registry.addMapping("/**")
//				.allowedHeaders("*")
//				.allowedMethods("*")
//				.allowedOrigins("*");
//	}
//
//	@Override
//	protected void configureAsyncSupport(AsyncSupportConfigurer configurer) {
//		configurer.setTaskExecutor(new ConcurrentTaskExecutor(Executors.newFixedThreadPool(3)));
//		configurer.setDefaultTimeout(30000);
//	}
//}
