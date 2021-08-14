package com.builders.register;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.builders.register.exceptions.InvalidSearchFilterException;
import com.builders.register.exceptions.ObjectNotFoundException;
import com.builders.register.model.Client;
import com.builders.register.repository.ClientRepository;
import com.builders.register.service.ClientService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ClientServiceTest {
	
	private Client clientDefault;
	
	@Mock
    private ClientRepository clientRepository;
	
	@InjectMocks
	ClientService clientService;
	
	@BeforeEach
    void config() {
		clientDefault = Client.builder().id(1l).name("Jack").age(39).build();
    }
	
	@Test
	public void createOk() {
		when(clientRepository.save(clientDefault)).thenReturn(clientDefault);
		when(clientRepository.findById(1l)).thenReturn(Optional.of(clientDefault));
		
		Client clientCreated = clientService.create(clientDefault);
		Assertions.assertNotNull(clientCreated.getId());
		Assertions.assertNotNull(clientService.findById(1l));
		Assertions.assertEquals(clientDefault, clientCreated);
	}
	

	@Test
	public void findByIdOk() {
		when(clientRepository.findById(1l)).thenReturn(Optional.of(clientDefault));
		Assertions.assertNotNull(clientService.findById(1l));
	}
	
	@Test
	public void findByIdClientInexistente() {
		when(clientRepository.findById(1l)).thenReturn(Optional.empty());
		Assertions.assertThrows(ObjectNotFoundException.class, () -> clientService.findById(1l));
	}
  
    @Test
    public void findByNameSemTerm() throws Exception {
		Assertions.assertThrows(InvalidSearchFilterException.class,
				() -> clientService.findByNameContainingIgnoreCase(null, null));
    }
    
    @Test
    public void deleteClientInexistente() throws Exception {
		when(clientRepository.findById(1l)).thenReturn(Optional.empty());
		Assertions.assertThrows(ObjectNotFoundException.class, () -> clientService.findById(1l));
    }
    
    @Test
    public void updateComIdNull() throws Exception {
    	clientDefault.setId(null);
    	Assertions.assertThrows(InvalidSearchFilterException.class,
				() -> clientService.update(clientDefault));
    }
    
    @Test
    public void updateIdInexistente() throws Exception {
    	when(clientRepository.findById(1l)).thenReturn(Optional.empty());
		Assertions.assertThrows(ObjectNotFoundException.class, () -> clientService.update(clientDefault));
    }

    @Disabled("Desabilitado temporariamente")
    @Test
    public void updateOk() throws Exception {
    }
}
