package com.builders.register.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.builders.register.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long>{

	Page<Client> findByNameContainingIgnoreCase(String name, Pageable pageable);
	
}
