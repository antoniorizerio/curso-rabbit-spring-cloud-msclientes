package com.rizerio.springproject.msclientes.application.controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rizerio.springproject.msclientes.application.controller.exception.StandardError;
import com.rizerio.springproject.msclientes.application.request.ClienteSaveRequest;
import com.rizerio.springproject.msclientes.application.service.ClienteService;
import com.rizerio.springproject.msclientes.domain.Cliente;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/clientes")
public class ClienteResource {
	
	private final ClienteService service;
	
	
	public ClienteResource(ClienteService service) {
		this.service = service;
	}
	

	@GetMapping
	public String status() {
		return "ok";
	}
	
	/**
	 * http://localhost:PORT/clientes?cpf=02282422155
	 * 
	 * @RequestMapping(method = RequestMethod.POST)
	 * 
	 */
	@PostMapping
	public ResponseEntity<Cliente> save(@RequestBody ClienteSaveRequest request) {
		Cliente cliente = request.toModel();
		service.save(cliente);
		
		/*
		 * 
		 * ServletUriComponentsBuilder - UriComponentsBuilder with additional static factory methods 
		 * to create links based on the current HttpServletRequest. Serve para construir URL`s dinamicas.
		 * 
		 * fromCurrentRequest() - Recupero a requisição atual, corrente. Através da URL corrente.
		 * 
		 * query() - passo argumentos para minha URL
		 * buildAndExpand - construo e passo os valores da URL.
		 */
		URI headerLocation = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.query("cpf={cpf}")
				.buildAndExpand(cliente.getCpf())
				.toUri();
		
		URI headerLocation1 = ServletUriComponentsBuilder
				.fromCurrentRequest().build().toUri();
		
		URI headerLocation2 = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.host("Vitoria")
				.buildAndExpand(cliente.getCpf())
				.toUri();
		
		URI headerLocation3 = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{cpf}")
				.buildAndExpand(cliente.getCpf())
				.toUri();
		
		URI headerLocation4 = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.query("cpf={cpf}&nome={nome}")
				.buildAndExpand(cliente.getCpf(), "nome")
				.toUri();
		
		/**
		 * * Create a new builder with a {@linkplain HttpStatus#CREATED CREATED} status
		 * 	 and a location header set to the given URI.
		 */
		
		return ResponseEntity.created(headerLocation).body(cliente);
	}
	
	@GetMapping(params = "cpf")
	public ResponseEntity dadosCliente(@RequestParam("cpf") String cpf) {
		Optional<Cliente> cliente = service.getByCpf(cpf);
		if(cliente.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(cliente);
	}
	
	@GetMapping(value = "/{cpf}")
	public ResponseEntity dadosClientePathVariable(@PathVariable String cpf) {
		Optional<Cliente> cliente = service.getByCpf(cpf);
		if(cliente.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(cliente);
	}
	
	/**
	 * This approach has a major drawback: The @ExceptionHandler annotated method is only active 
	 * for that particular Controller, not globally for the entire application. Of course, 
	 * adding this to every controller makes it not well suited for a general exception handling 
	 * mechanism.
	 */
	@ExceptionHandler({NumberFormatException.class})
	public ResponseEntity<StandardError> objectNotFound(NumberFormatException e, 
			HttpServletRequest request) {
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		
		StandardError standardError = new StandardError(System.currentTimeMillis(), status.value(),
				"Recurso não encontrado", e.getMessage(), request.getRequestURI());
		
		return ResponseEntity.status(status).body(standardError);
	}
}
