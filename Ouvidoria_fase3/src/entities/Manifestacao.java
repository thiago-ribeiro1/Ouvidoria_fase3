package entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Manifestacao {

	private String texto;
	private String tipo;
	private String nome;
	private String cpf;
	private String email;

	public Manifestacao(String texto, String tipo, String nome, String cpf, String email) {
		super();
		this.texto = texto;
		this.tipo = tipo;
		this.nome = nome;
		this.cpf = cpf;
		this.email = email;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Manifestacao [texto=" + texto + ", tipo=" + tipo + ", nome=" + nome + ", cpf=" + cpf + ", email="
				+ email + "]";
	}

	public void adicionarManifestacao(Connection c) {

		Scanner sc = new Scanner(System.in);
		System.out.println();
		System.out.println("Registre sua manifestação: ");
		this.texto = sc.nextLine();

		System.out.println("Digite seu nome: ");
		this.nome = sc.nextLine();
		System.out.println("Digite seu CPF: ");
		this.cpf = sc.nextLine();
		System.out.println("Digite seu email: ");
		this.email = sc.nextLine();

		System.out.println();
		System.out.println("Selecione o tipo da manifestação: ");
		System.out.println();
		System.out.println("1) Reclamação");
		System.out.println("2) Sugestão");
		System.out.println("3) Elogio");
		System.out.println();

		int manifestacaoTipo = 0;

		while (manifestacaoTipo < 1 || manifestacaoTipo > 3) {
			System.out.println("Digite o número da opção: ");
			manifestacaoTipo = sc.nextInt();
			sc.nextLine();

			if (manifestacaoTipo < 1 || manifestacaoTipo > 3) {
				System.out.println("Opção inválida, tente novamente \n");
			}
		}

		Manifestacao m = new Manifestacao(texto, tipo, nome, cpf, email);

		switch (manifestacaoTipo) {
		case 1:
			m = new Reclamacao(texto, tipo, nome, cpf, email);
			m.setTipo("Reclamação");
			break;
		case 2:
			m = new Sugestao(texto, tipo, nome, cpf, email);
			m.setTipo("Sugestão");
			break;
		case 3:
			m = new Elogio(texto, tipo, nome, cpf, email);
			m.setTipo("Elogio");
			break;
		}

		this.tipo = m.getTipo();

		String sql = "insert into manifestacoes (texto, tipo, nome, cpf, email) values ('" + this.texto + "','"
				+ this.tipo + "','" + this.nome + "','" + this.cpf + "','" + this.email + "')";
		try {
			Statement st = c.createStatement();
			st.executeUpdate(sql);
			System.out.println();
			System.out.printf(
					"Manifestação armazenada no banco de dados com sucesso! %nManifestação: (%s) %nTipo: %s %n", texto,
					tipo);
			System.out.println();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void listarManifestacoes(Connection c) {
		String sql = "select * from manifestacoes";
		try {
			Statement st = c.createStatement();
			ResultSet resultado = st.executeQuery(sql);

			if (!resultado.next()) {
				System.out.println();
				System.out.println("Não existem manifestações cadastradas \n");
			} else {
				System.out.println("Lista de manifestações:  \n");

				do {
					System.out.printf("ID %d) Manifestação: %s %n Tipo: %S %n Nome: %s %n CPF: %s %n Email: %s %n",
							resultado.getInt("ID"), resultado.getString("Texto"), resultado.getString("Tipo"),
							resultado.getString("Nome"), resultado.getString("CPF"), resultado.getString("Email"));
					System.out.println();
				} while (resultado.next());
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public static void RemoverManifestacao(Connection c, int id) {

		String sql;
		if (id == 0) { // apaga tudo
			sql = "truncate manifestacoes";
			try {
				Statement st = c.createStatement();
				st.executeUpdate(sql);
				System.out.println("Todas as manifestações foram apagadas \n");
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else { // apaga por id
			sql = "delete from manifestacoes where id = " + id;
			try {
				Statement st = c.createStatement();
				int rowsAffected = st.executeUpdate(sql);

				if (rowsAffected > 0) {
					System.out.println("Manifestação apagada com sucesso \nID: " + id + "\n");
				} else {
					System.out.println();
					System.out.println("Nenhuma manifestação encontrada com a ID: " + id + "\n");
				}

				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static Manifestacao pesquisarManifestacaoPorId(Connection c, int id) {
		String sql = "select * from manifestacoes where id = " + id;
		try {
			Statement st = c.createStatement();
			ResultSet resultado = st.executeQuery(sql);
			if (resultado.next()) {
				String texto = resultado.getString("Texto");
				String tipo = resultado.getString("Tipo");
				String nome = resultado.getString("Nome");
				String cpf = resultado.getString("CPF");
				String email = resultado.getString("Email");
				Manifestacao manifestacao = new Manifestacao(texto, tipo, nome, cpf, email);
				manifestacao.setId(id);
				System.out.printf("Manifestação ID: %d %nDescrição: %s %n", id, texto);
				System.out.println();
				System.out.printf("ID %d) Manifestação: %s %nTipo: %s %nNome: %s %nCPF: %s %nEmail: %s %n%n", id, texto,
						tipo, nome, cpf, email);
				return manifestacao;
			} else {
				System.out.println();
				System.out.println("Nenhuma manifestação encontrada com essa ID \n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void setId(int id) {

	}
}
