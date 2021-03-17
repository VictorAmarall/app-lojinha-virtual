package com.victoramaral.projetomc.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.victoramaral.projetomc.domain.Categoria;
import com.victoramaral.projetomc.domain.Produto;
import com.victoramaral.projetomc.repositories.CategoriaRepository;
import com.victoramaral.projetomc.repositories.ProdutoRepository;
import com.victoramaral.projetomc.services.exceptions.ObjectNotFoundException;




@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository repo;
	

	@Autowired
	private CategoriaRepository categoriaRepo;
	
	//@Autowired
	//private EntityManager em;
	@Transactional
	public Produto Buscar(Integer id) {
		 Optional<Produto> obj = repo.findDistinctById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		 "Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
		
		
		 /*CriteriaBuilder cb = em.getCriteriaBuilder();
		 CriteriaQuery<Produto> cq = cb.createQuery(Produto.class);
		 Root<Produto> produto = cq.from(Produto.class);
		 
		 List<Predicate> predicates = new ArrayList<Predicate>();
		 
		 predicates.add(cb.equal(produto.<Integer>get("id"), id));
		 
		 cq.distinct(true);
		 produto.fetch("nome");
		 
		 cq.select(produto).where(predicates.toArray(new Predicate[] {}));
		 
		 TypedQuery<Produto> tquery = em.createQuery(cq);
		 //tquery.setMaxResults(1);
		 
		 
		 return tquery.getSingleResult();*/
		} 
		public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
			PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
			List<Categoria> categorias = categoriaRepo.findAllById(ids);
			return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);	
		}




}
