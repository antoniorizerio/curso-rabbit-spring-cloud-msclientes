package com.rizerio.springproject.msclientes.application.request;

import com.rizerio.springproject.msclientes.domain.Cliente;

import lombok.Data;

@Data
public class ClienteSaveRequest {

	private String cpf;
	private String nome;
	private Integer idade;
	
	public Cliente toModel() {
		return new Cliente(getCpf(), getNome(), getIdade());
	}
}
