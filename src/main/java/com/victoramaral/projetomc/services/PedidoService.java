package com.victoramaral.projetomc.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.victoramaral.projetomc.domain.Cliente;
import com.victoramaral.projetomc.domain.ItemPedido;
import com.victoramaral.projetomc.domain.PagamentoComBoleto;
import com.victoramaral.projetomc.domain.Pedido;
import com.victoramaral.projetomc.domain.enums.EstadoPagamento;
import com.victoramaral.projetomc.repositories.ItemPedidoRepository;
import com.victoramaral.projetomc.repositories.PagamentoRepository;
import com.victoramaral.projetomc.repositories.PedidoRepository;
import com.victoramaral.projetomc.security.UserSS;
import com.victoramaral.projetomc.services.exceptions.AuthorizationException;




@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private EntityManager em;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ClienteService clienteService;

	@Autowired
	private EmailService emailService;
	
	@Transactional
	public Pedido Buscar(Integer id) {
		/*Optional<Pedido> obj = repo.findDistinctById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		 "Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
		*/
		
		 CriteriaBuilder cb = em.getCriteriaBuilder();
		 CriteriaQuery<Pedido> cq = cb.createQuery(Pedido.class);
		 Root<Pedido> pedido = cq.from(Pedido.class);
		 
		 List<Predicate> predicates = new ArrayList<Predicate>();
		 
		 predicates.add(cb.equal(pedido.<Integer>get("id"), id));
		 
		 cq.distinct(true);
		 pedido.fetch("cliente");
		 
		 cq.select(pedido).where(predicates.toArray(new Predicate[] {}));
		 
		 TypedQuery<Pedido> tquery = em.createQuery(cq);
		 //tquery.setMaxResults(1);
		 
		 
		 return tquery.getSingleResult();
		} 
		@Transactional
		public Pedido insert(Pedido obj) {
			obj.setId(null);
			obj.setInstante(new Date());
			obj.setCliente(clienteService.Buscar(obj.getCliente().getId()));
			obj.getPagamento().setEstado(EstadoPagamento.PEDENTE);
			obj.getPagamento().setPedido(obj);
			if (obj.getPagamento() instanceof PagamentoComBoleto) {
				PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
				boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
			}
			obj = repo.save(obj);
			pagamentoRepository.save(obj.getPagamento());
			for (ItemPedido ip : obj.getItens()) {
				ip.setDesconto(0.0);
				ip.setProduto(produtoService.Buscar(ip.getProduto().getId()));
				ip.setPreco(ip.getProduto().getPreco());
				ip.setPedido(obj);
			}
			itemPedidoRepository.saveAll(obj.getItens());
			emailService.sendOrderConfirmationHtmlEmail(obj);
			System.out.println(obj);
			return obj;
		}
		public Page<Pedido> buscarPage(Integer page, Integer linesPerPage, String orderBy,String direction){
			UserSS user = UserService.authenticated();
			if (user == null) {
				throw new AuthorizationException("Acesso negado");
			}
			PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),
					orderBy);
			Cliente cliente =  clienteService.Buscar(user.getId());
			return repo.findByCliente(cliente ,pageRequest);
		}



}
