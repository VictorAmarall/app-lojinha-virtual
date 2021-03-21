package com.victoramaral.projetomc.repositories;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.victoramaral.projetomc.domain.Cliente;
import com.victoramaral.projetomc.domain.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
	
	@Transactional
	Optional<Pedido> findDistinctById(Integer id);
	@Transactional
	Page <Pedido> findByCliente (Cliente cli, Pageable pageRequest);

	
}
