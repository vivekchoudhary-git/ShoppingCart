package com.vivekSpringBoot.shopping.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	private Environment environment;
	
	 @Bean
	    public TilesConfigurer tilesConfigurer() {
	        TilesConfigurer configurer = new TilesConfigurer();
	        configurer.setDefinitions("/WEB-INF/tiles.xml");
	        configurer.setCheckRefresh(true);
	        return configurer;
	    }

	    @Bean
	    public TilesViewResolver tilesViewResolver() {
	        TilesViewResolver resolver = new TilesViewResolver();
	        resolver.setOrder(0);
	        return resolver;
	    }
	
	    // we are using this method to get images file path (suggested by chatGPT)
	    // usually we dont have to use this method when images are saved inside project and not in external directory.
	    @Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	        registry
	          .addResourceHandler("/resources/**")
	          .addResourceLocations("/resources/");
	        
	        String uploadDir = environment.getProperty("category.upload.path");
	        String productUploadDir = environment.getProperty("product.upload.path");
	        String userImageUploadPath = environment.getProperty("userimage.upload.path");
	        String sellerCertificateUploadPath = environment.getProperty("sellerCertificate.upload.path");
	        
	        // this is to read external directory path where we would store uploaded category images, suggested by chatGPT
	        registry
	          .addResourceHandler("/categoryimage/**")
 //           .addResourceLocations("file:/D:/JAVA/externaldirectory/shoppingcart/categoryimage/");       // this is hard coded
	          .addResourceLocations("file:"+uploadDir+"/");                                                // this is dynamic
	        
	     // this is to read external directory path where we would store uploaded product images, suggested by chatGPT
	        
	        registry
	               .addResourceHandler("/productimage/**")
	               .addResourceLocations("file:"+productUploadDir+"/");
	        
	     // this is to read external directory path where we would store uploaded user profile images, suggested by chatGPT
	     // i would recommend below code to define image location 
	        
	        registry
            .addResourceHandler("/shoppingcart/userimage/**")
	        .addResourceLocations("file:///"+userImageUploadPath+"/");
	        
	        // this is to read external directory path where we would store uploaded seller company registration certificate images, suggested by chatGPT
		     // i would recommend below code to define image location
		       registry
	           .addResourceHandler("/shoppingcart/sellercertificates/**")
		       .addResourceLocations("file:///"+sellerCertificateUploadPath+"/");
		      
	      
	    }
	    
}
