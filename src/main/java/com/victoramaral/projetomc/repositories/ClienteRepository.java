package com.victoramaral.projetomc.repositories;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.victoramaral.projetomc.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

	Optional<Cliente> findDistinctById(Integer id);
	
	@Transactional
	Cliente findByEmail (String email);



	
}
