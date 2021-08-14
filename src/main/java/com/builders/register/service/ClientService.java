package com.builders.register.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.builders.register.exceptions.InvalidSearchFilterException;
import com.builders.register.exceptions.ObjectNotFoundException;
import com.builders.register.model.Client;
import com.builders.register.repository.ClientRepository;

@Service
public class ClientService {
	
	@Autowired
    ClientRepository repository;

	/**
	 * 
	 * @param pagingController
	 * @return
	 */
    public Page<Client> findAll(PageRequest pagingController) {
    	return repository.findAll(pagingController);
    }
    
    /**
     * 
     * @param findTerm
     * @param pagingController
     * @return
     */
    public Page<Client> findByNameContainingIgnoreCase(String findTerm, PageRequest pagingController) {
    	Optional.ofNullable(findTerm)
    		.orElseThrow(() -> new InvalidSearchFilterException("O termo de pesquisa não pode ser null."));
        return repository.findByNameContainingIgnoreCase(findTerm, pagingController);
    }
    
    /**
     * Deve ser montado de acordo com a necessidade negocial, são muitas as possibilidades de combinações de busca
     * @param clientExample
     * @param pagingController
     * @return
     */
    public Page<Client> findByExample(Client clientExample, PageRequest pagingController) {
    	ExampleMatcher customMatcher = ExampleMatcher.matchingAll()
    		      .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
    		      .withMatcher("age", ExampleMatcher.GenericPropertyMatchers.exact());
    	return repository.findAll(Example.of(clientExample, customMatcher), pagingController);
    }
    
    /**
     * 
     * @param id
     * @return
     */
    public Client findById(Long id) {
        Optional<Client> client = repository.findById(id);
		client.orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado."));
        return client.get();
    }
    
    /**
     * 
     * @param id
     */
    public void delete(Long id) {
    	repository.findById(id)
			.orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado."));
        repository.deleteById(id);
    }

    /**
     * 
     * @param client
     * @return
     */
	public Client create(Client client) {
        return repository.save(client);
    }

	/**
	 * 
	 * @param client
	 */
	public void update(Client client) {
		Optional.ofNullable(client.getId())
			.orElseThrow(() -> new InvalidSearchFilterException("O Id do cliente não pode ser null."));
		Optional<Client> clientFind = repository.findById(client.getId());
		clientFind.orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado."));
		
		repository.save(client);
	}
}
