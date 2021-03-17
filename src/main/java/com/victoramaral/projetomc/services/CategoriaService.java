package com.victoramaral.projetomc.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.victoramaral.projetomc.DTO.CategoriaDTO;
import com.victoramaral.projetomc.domain.Categoria;
import com.victoramaral.projetomc.repositories.CategoriaRepository;
import com.victoramaral.projetomc.services.exceptions.DataIntegrityException;
import com.victoramaral.projetomc.services.exceptions.ObjectNotFoundException;



@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;
	
	//@Autowired
	//private EntityManager em;
	
	@Transactional
	public Categoria Buscar(Integer id) {
		
		/* CriteriaBuilder cb = em.getCriteriaBuilder();
		 CriteriaQuery<Categoria> cq = cb.createQuery(Categoria.class);
		 Root<Categoria> categoria = cq.from(Categoria.class);
		 
		 List<Predicate> predicates = new ArrayList<Predicate>();
		 
		 predicates.add(cb.equal(categoria.<Integer>get("id"), id));
		 
		 cq.distinct(true);
		 categoria.fetch("produtos");
		 
		 cq.select(categoria).where(predicates.toArray(new Predicate[] {}));
		 
		 TypedQuery<Categoria> tquery = em.createQuery(cq);
		 //tquery.setMaxResults(1);
		 
		 
		 return tquery.getSingleResult();
		
		 	*/	 
		Optional<Categoria> obj = repo.findDistinctById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
		} 

	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}

	public Categoria update(Categoria obj) {
		Categoria newObj = Buscar(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id) {
		Buscar(id);
		try {
		repo.deleteById(id);
		}catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir uma categoria que possui produtos");
			
		}
		
	}

	public List<Categoria> findALL() {
		
		return repo.findAll();
	}
	public Page<Categoria> buscarPage(Integer page, Integer linesPerPage, String orderBy,String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),
				orderBy);
		return repo.findAll(pageRequest);
	}
	public Categoria fromDTO(CategoriaDTO objDTO) {
		return new Categoria(objDTO.getId(), objDTO.getNome());
		
	}
	private void updateData(Categoria newOBJ, Categoria obj) {
		newOBJ.setNome(obj.getNome());
		
	}

}
