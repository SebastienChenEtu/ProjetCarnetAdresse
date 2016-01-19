package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import modele.DAO;
import modele.Database;


@Configuration
@ComponentScan(value = "modele")
public class SpringConfig {
	
	@Bean
	public DAO getDAO(){
		Database dt = new Database("Database.db");
		dt.connexion();
		DAO dao = new DAO(dt);
		return dao;
	}
	
	
}
