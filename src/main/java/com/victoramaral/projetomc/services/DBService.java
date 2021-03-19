package com.victoramaral.projetomc.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.victoramaral.projetomc.domain.Categoria;
import com.victoramaral.projetomc.domain.Cidade;
import com.victoramaral.projetomc.domain.Cliente;
import com.victoramaral.projetomc.domain.Endereco;
import com.victoramaral.projetomc.domain.Estado;
import com.victoramaral.projetomc.domain.ItemPedido;
import com.victoramaral.projetomc.domain.Pagamento;
import com.victoramaral.projetomc.domain.PagamentoComBoleto;
import com.victoramaral.projetomc.domain.PagamentoComCartão;
import com.victoramaral.projetomc.domain.Pedido;
import com.victoramaral.projetomc.domain.Produto;
import com.victoramaral.projetomc.domain.enums.EstadoPagamento;
import com.victoramaral.projetomc.domain.enums.Perfil;
import com.victoramaral.projetomc.domain.enums.TipoCliente;
import com.victoramaral.projetomc.repositories.CategoriaRepository;
import com.victoramaral.projetomc.repositories.CidadeRepository;
import com.victoramaral.projetomc.repositories.ClienteRepository;
import com.victoramaral.projetomc.repositories.EnderecoRepository;
import com.victoramaral.projetomc.repositories.EstadoRepository;
import com.victoramaral.projetomc.repositories.ItemPedidoRepository;
import com.victoramaral.projetomc.repositories.PagamentoRepository;
import com.victoramaral.projetomc.repositories.PedidoRepository;
import com.victoramaral.projetomc.repositories.ProdutoRepository;

@Service
public class DBService {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	public void instantiateTestDatabase() throws ParseException {
		Categoria cat1 = new Categoria(null, "informatica");
		Categoria cat2 = new Categoria(null, "Escritorio");
		Categoria cat3 = new Categoria(null, "Cama Mesa e Banho");
		Categoria cat4 = new Categoria(null, "Eletronicos");
		Categoria cat5 = new Categoria(null, "Jardinagem");
		Categoria cat6 = new Categoria(null, "Perfumaria");
		Categoria cat7 = new Categoria(null, "Cosmeticos");
		
		
		
		Produto p1 = new Produto(null, "Computador",2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.0);
		Produto p4 = new Produto(null, "Mesa de escritorio",3000.00);
		Produto p5 = new Produto(null, "Toalha", 500.00);
		Produto p6 = new Produto(null, "Colcha", 10.0);
		Produto p7 = new Produto(null, "TVtruecolor", 830.0);//tentar pesquisa esse ID
		Produto p8 = new Produto(null, "Roçadeira", 50.0);
		Produto p9 = new Produto(null, "Abajour", 70.0);
		Produto p10 = new Produto(null, "Pendente", 20.0);
		Produto p11 = new Produto(null, "Shampoo", 120.0);
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2,p4));
		cat3.getProdutos().addAll(Arrays.asList(p5,p6));
		cat4.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat5.getProdutos().addAll(Arrays.asList(p8,p7));
		cat6.getProdutos().addAll(Arrays.asList(p9,p10));
		cat7.getProdutos().addAll(Arrays.asList(p11));
		
		p1.getCategorias().addAll(Arrays.asList(cat1,cat4));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2,cat4));
		p3.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p4.getCategorias().addAll(Arrays.asList(cat2));
		p5.getCategorias().addAll(Arrays.asList(cat3));
		p6.getCategorias().addAll(Arrays.asList(cat3));
		p7.getCategorias().addAll(Arrays.asList(cat5));
		p8.getCategorias().addAll(Arrays.asList(cat5));
		p9.getCategorias().addAll(Arrays.asList(cat6));
		p10.getCategorias().addAll(Arrays.asList(cat6));
		p11.getCategorias().addAll(Arrays.asList(cat7));
		
		
	
		categoriaRepository.saveAll(Arrays.asList(cat1,cat2,cat3,cat4,cat5,cat6,cat7));
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11));
				
		Estado est1 = new Estado(null,"Minas gerais");
		Estado est2 = new Estado(null,"São Paulo");
		
		Cidade c1 = new Cidade(null, "Umberlandia",est1);
		Cidade c2 = new Cidade(null, "São Paulo",est2);
		Cidade c3 = new Cidade(null, "Campinas",est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		estadoRepository.saveAll(Arrays.asList(est1,est2));
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));
		
		Cliente cli1 = new Cliente(null,"Maria Silva","tito.victor@hotmail.com", "30848935004",TipoCliente.PESSOAFISICA,pe.encode("123") );
		Cliente cli2 = new Cliente(null,"Ana Costa","victor.amarallv@gmail.com", "11612798063",TipoCliente.PESSOAFISICA,pe.encode("123") );
		cli2.addPerfil(Perfil.ADMIN);
		
		cli1.getTelefones().addAll(Arrays.asList("2546879", "554567"));
		cli2.getTelefones().addAll(Arrays.asList("4343444", "6565666"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "3884462", cli1, c1);
		Endereco e2 = new Endereco(null, "Av. Matoo", "105", "Sala800", "Centro", "5446788", cli1, c2);
		Endereco e3 = new Endereco(null, "Av. Epitacio Pessoa", "305", "APTO100", "Centro", "33321", cli2, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
		cli2.getEnderecos().addAll(Arrays.asList(e3));
		
		clienteRepository.saveAll(Arrays.asList(cli1,cli2));
		enderecoRepository.saveAll(Arrays.asList(e1,e2,e3));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 09:32"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartão(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PEDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1,ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1,pagto2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.0);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1,ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1,ip2,ip3));
		
	}	
}
