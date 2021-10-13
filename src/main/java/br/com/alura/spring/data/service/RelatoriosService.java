package br.com.alura.spring.data.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.FuncionarioProjecao;
import br.com.alura.spring.data.repository.FuncionarioRepository;

@Service
public class RelatoriosService {

	private Boolean system = true;
	
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	private FuncionarioRepository funcionarioRepository;
	
	
	
	public RelatoriosService(FuncionarioRepository funcionarioRepository){
		this.funcionarioRepository = funcionarioRepository;
	}
	

	public void inicial(Scanner scanner) {

		while (system) {
			System.out.println("Qual ação você quer executar?");
			System.out.println("0 - Sair");
			System.out.println("1 - Busca funcionario por nome");
			System.out.println("2 - Busca funcionario por nome, dataContratacao e salario maior");
			System.out.println("3 - Busca funcionario por dataContratacao maior");
			System.out.println("4 - Busca funcionario por Salario");

			int action = scanner.nextInt();

			switch (action) {
			case 1:
				this.buscaFuncionarioPorNome(scanner);
				break;
			case 2:
				this.buscaFuncionarioNomeSalarioMaiorData(scanner);
				break;
			case 3:
				this.buscaFuncionarioDataContratacao(scanner);
				break;
			case 4:
				this.buscafuncionarioSalario();
				break;

			default:
				system = false;
				break;
			}
		}

	}
	
	private void buscaFuncionarioPorNome(Scanner scanner) {
		
		System.out.println("Qual nome deseja pesquisar?");
		String nome = scanner.next();
		
		List<Funcionario> lista = funcionarioRepository.findByNome(nome);
		lista.forEach(System.out::println);
	}

	private void buscaFuncionarioNomeSalarioMaiorData(Scanner scanner) {
		
		System.out.println("Qual nome deseja pesquisar?");
		String nome = scanner.next();
		
		System.out.println("Qual data contratacao deseja pesquisar?");
		String data = scanner.next();
		LocalDate localDate = LocalDate.parse(data, formatter);
		
		System.out.println("Qual salario deseja pesquisar?");
		Double salario = scanner.nextDouble();
		
		List<Funcionario> lista = funcionarioRepository.findNomeDataContratacaoSalarioMaior(nome, salario, localDate);
		lista.forEach(System.out::println);
	}
	
	
	private void buscaFuncionarioDataContratacao(Scanner scanner) {
		
		System.out.println("Qual data contratacao deseja pesquisar?");
		String data = scanner.next();
		LocalDate localDate = LocalDate.parse(data, formatter);
		List<Funcionario> lista = funcionarioRepository.findDataContratacaoMaior(localDate);
		lista.forEach(System.out::println);
	}
	
	
	//usando projecao
	private void buscafuncionarioSalario() {
		
		List<FuncionarioProjecao> lista = funcionarioRepository.findFuncionarioSalario();
		lista.forEach(f -> System.out.println("Funcionario: id: " + f.getId() + " nome:" + f.getNome() + " salario:" + f.getSalario()));
		
	}
	
	
}
