package com.rizerio.springproject.msclientes.infra.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.rizerio.springproject.msclientes.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	Optional<Cliente> findByCpf(String cpf);
	
	Cliente findByNome(@Param("nome") String nome);
	
	@Query("SELECT c FROM Cliente c WHERE c.idade = ?1")
	Cliente findByIdade(Integer idade);
}
