package com.builders.register;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.builders.register.model.Client;
import com.builders.register.repository.ClientRepository;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@SpringBootTest
@AutoConfigureMockMvc
class ClientControllerTest {
	
	private Client clientDefault;
	
	@Autowired
    private MockMvc mvc;

    @MockBean
    private ClientRepository clientRepository;

    @Autowired
    private JacksonTester<Client> jsonClient;

    @BeforeEach
    private void config() {
    	clientDefault = Client.builder().id(1l).name("Jack").age(39).build();
    }
    
    @Test
    public void findByIdOk() throws Exception {
    	
        given(clientRepository.findById(1l)).willReturn(Optional.of(clientDefault));

        MockHttpServletResponse response = mvc.perform(
            get("/clients/1").accept(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
        		jsonClient.write(clientDefault).getJson()
        );
    }
    
    @Test
    public void findByIdClientInexistente() throws Exception {
        given(clientRepository.findById(1l)).willReturn(Optional.empty());

        MockHttpServletResponse response = mvc.perform(
            get("/clients/1").accept(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
    
    @Test
    public void findByNameSemTerm() throws Exception {
    	MockHttpServletResponse response = mvc.perform(
    			get("/clients/find").accept(MediaType.APPLICATION_JSON))
    			.andReturn().getResponse();
    	
    	assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
    
    @Test
    public void deleteOk() throws Exception {
    	given(clientRepository.findById(1l)).willReturn(Optional.of(clientDefault));
    	MockHttpServletResponse response = mvc.perform(
    			delete("/clients/1").accept(MediaType.APPLICATION_JSON))
    			.andReturn().getResponse();
    	
    	assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
    
    @Test
    public void deleteClientInexistente() throws Exception {
    	given(clientRepository.findById(1l)).willReturn(Optional.empty());
    	MockHttpServletResponse response = mvc.perform(
    			delete("/clients/1").accept(MediaType.APPLICATION_JSON))
    			.andReturn().getResponse();
    	
    	assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
    
    @Test
    public void updateComIdNull() throws Exception {
    	clientDefault.setId(null);
    	MockHttpServletResponse response = mvc.perform(
    			put("/clients")
    			.content(jsonClient.write(clientDefault).getJson())
    			.contentType(MediaType.APPLICATION_JSON))
    			.andReturn().getResponse();
    	
    	assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
    
    @Test
    public void updateIdInexistente() throws Exception {
    	given(clientRepository.findById(1l)).willReturn(Optional.empty());
    	MockHttpServletResponse response = mvc.perform(
    			put("/clients")
    			.content(jsonClient.write(clientDefault).getJson())
    			.contentType(MediaType.APPLICATION_JSON))
    			.andReturn().getResponse();
    	
    	assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
    
    @Test
    public void updateOk() throws Exception {
    	given(clientRepository.findById(1l)).willReturn(Optional.of(clientDefault));
    	MockHttpServletResponse response = mvc.perform(
    			put("/clients")
    			.content(jsonClient.write(clientDefault).getJson())
    			.contentType(MediaType.APPLICATION_JSON))
    			.andReturn().getResponse();
    	
    	assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
}
