package application;

import java.sql.Connection;
import java.util.Scanner;

import db.DB;
import entities.Manifestacao;

public class Program {

	public static void main(String[] args) {
		Connection c = DB.getConnection();
		Scanner sc = new Scanner(System.in);

		int opcao = 0;
		while (opcao != 5) {
			System.out.println("============= MENU =============");
			System.out.println();
			System.out.println("1) Inserir manifestação");
			System.out.println("2) Listar manifestações");
			System.out.println("3) Remover manifestação por ID");
			System.out.println("4) Pesquisar manifestação por ID");
			System.out.println("5) Sair");
			System.out.println();
			System.out.println("=========== OUVIDORIA ==========");
			System.out.println();
			System.out.println("Digite a sua opção: ");
			opcao = sc.nextInt();

			switch (opcao) {
			case 1:
				Manifestacao manifestacoes = new Manifestacao("texto", "tipo", "nome", "cpf", "email");
				manifestacoes.adicionarManifestacao(c);
				break;
			case 2:
				Manifestacao.listarManifestacoes(c);
				break;
			case 3:
				System.out.println("Digite a ID da manifestação que você quer remover, ou digite 0 para apagar todas as manifestações: ");
				int remover = sc.nextInt();
				Manifestacao.RemoverManifestacao(c, remover);
				break;
			case 4:
				System.out.println("Digite a id da manifestação que você quer pesquisar: ");
				int pesquisarPorID = sc.nextInt();
				Manifestacao.pesquisarManifestacaoPorId(c, pesquisarPorID);
				break;
			case 5:	
				System.out.println();
				System.out.println("Até logo. Obrigado pelo feedback!");
				System.out.println();
				break;
			default:
				System.out.println();
				System.out.println("Opção inválida, tente novamente \n");
				break;
			}
		}
		sc.close();
	}

}
