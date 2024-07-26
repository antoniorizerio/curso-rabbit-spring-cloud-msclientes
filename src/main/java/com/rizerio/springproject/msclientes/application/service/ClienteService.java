package com.rizerio.springproject.msclientes.application.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.rizerio.springproject.msclientes.domain.Cliente;
import com.rizerio.springproject.msclientes.infra.repository.ClienteRepository;

@Service
public class ClienteService {

	private final ClienteRepository repository;
	
	public ClienteService(ClienteRepository repository) {
		this.repository = repository;
	}
	
	@Transactional
	public Cliente save(Cliente cliente) {
		return repository.save(cliente);
	}
	
	public Optional<Cliente> getByCpf(String cpf) {
		return repository.findByCpf(cpf);
	}
}
