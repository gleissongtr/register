package com.builders.register.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.builders.register.model.Client;
import com.builders.register.service.ClientService;

@RestController
@RequestMapping("/clients")
public class ClientController extends BaseController {
	
	@Autowired
    ClientService service;

	/**
	 * Busca todos com parametros de paginação não obrigatórios
	 * @param page
	 * @param size
	 * @param direction
	 * @param orderBy
	 * @return
	 */
	@GetMapping
    public Page<Client> findAll(
    		@RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size,
            @RequestParam(value = "direction", required = false, defaultValue = "asc") String direction,
            @RequestParam(value = "orderBy", required = false, defaultValue = "name") String[] orderBy ) {
        return service.findAll(PageRequest.of(page, size, Sort.Direction.fromString(direction), orderBy));
    }
	
	/**
	 * Busca todos com parametros de paginação  não obrigatórios
	 * e termo de pesquisa obrigatório para name
	 * @param findTerm
	 * @param page
	 * @param size
	 * @param direction
	 * @param orderBy
	 * @return
	 */
    @GetMapping("/find")
    public Page<Client> find(
            @RequestParam(value = "findTerm", required = false) String findTerm,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size,
            @RequestParam(value = "direction", required = false, defaultValue = "asc") String direction,
            @RequestParam(value = "orderBy", required = false, defaultValue = "name") String[] orderBy ) {
        return service.findByNameContainingIgnoreCase(findTerm,
        		PageRequest.of(page, size, Sort.Direction.fromString(direction), orderBy));
    }
    
    /**
     * Busca por exemplo de objeto com parametros de paginação não obrigatórios,
     * poderia receber o exemplo pronto via @Post mas para manter o padrão http mantive o @Get e query params
     * @param name
     * @param age
     * @param page
     * @param size
     * @param direction
     * @param orderBy
     * @return
     */
    @GetMapping("/findExample")
    public Page<Client> findByExample(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "age", required = false) Integer age,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size,
            @RequestParam(value = "direction", required = false, defaultValue = "asc") String direction,
            @RequestParam(value = "orderBy", required = false, defaultValue = "name") String[] orderBy ) {
        return service.findByExample(
        		Client.builder().name(name).age(age).build(),
        		PageRequest.of(page, size, Sort.Direction.fromString(direction), orderBy));
    }
    
    /**
     * Busca Client pelo id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Client> findById(@PathVariable("id") Long id ) {
        return ResponseEntity.ok(service.findById(id));
    }

    /**
     * Cria um Client
     * retorna 201 e a URI do recurso no header, obedecendo um dos 3 fatores do Level 3 do Richardson Maturity Model 
     * @param client
     * @return
     */
    @PostMapping
    public ResponseEntity<Client> create(@RequestBody Client client) {
    	Client clientCreated = service.create(client);
    	//
    	return ResponseEntity.created(tratarResourceURI(clientCreated.getId())).build();
    }
    /**
     * Atualiza Client
     * retorna vazio para evitar trafegar com dados que o client já possui
     * @param client
     * @return
     */
    @PutMapping
    public ResponseEntity<Void> update(@RequestBody Client client) {
    	service.update(client);
    	return ResponseEntity.ok().build();
    }
    /**
     * Exclui Client
     * @param id
     * @return
     * @throws Exception
     */
    @DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
    	service.delete(id);
		return ResponseEntity.ok().build();
	}
}
