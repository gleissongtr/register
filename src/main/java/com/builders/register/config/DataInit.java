package com.builders.register.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.builders.register.model.Client;
import com.builders.register.repository.ClientRepository;

@Component
public class DataInit implements ApplicationListener<ContextRefreshedEvent>{

	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationListener.class);
	
	@Autowired
	ClientRepository clientRepository;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		LOGGER.info("CARREGANDO DADOS DE TESTE...");
		clientRepository.save(Client.builder().name("Mary").age(31).build());
		clientRepository.save(Client.builder().name("Ann").age(30).build());
		clientRepository.save(Client.builder().name("Marilyn").age(33).build());
		clientRepository.save(Client.builder().name("John").age(34).build());
		clientRepository.save(Client.builder().name("Jane").age(33).build());
	}
	
}
