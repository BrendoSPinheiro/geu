package br.ucsal.geu.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import br.ucsal.geu.model.Bloco;
import br.ucsal.geu.model.Espaco;
import br.ucsal.geu.model.Reserva;
import br.ucsal.geu.model.Tipo;
import br.ucsal.util.Conexao;

public class ReservaDAO {


	private Conexao conexao;

	public ReservaDAO() {
		this.conexao = Conexao.getConexao();
	}

	public List<Reserva> listarLazy() {
		Statement stmt;
		List<Reserva> reservas = new ArrayList<>();
		try {
			stmt = conexao.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery("select id,titulo,descricao,justificativa,solicitante,telefone,data,hora_inicio,hora_fim,espaco_id from reservas");
			while(rs.next()) {
				Reserva reserva = new Reserva();
				reserva.setId(rs.getInt("id"));
				
				reserva.setTitulo(rs.getString("titulo"));
				reserva.setDescricao(rs.getString("descricao"));
				reserva.setJustificativa(rs.getString("justificativa"));
				reserva.setSolicitante(rs.getString("solicitante"));
				reserva.setTelefone(rs.getString("telefone"));
				reserva.setData(rs.getDate("data").toLocalDate());
				reserva.setHoraInicio(rs.getTime("hora_inicio").toLocalTime());
				reserva.setHoraFim(rs.getTime("hora_fim").toLocalTime());

				
				Espaco espaco = new Espaco();
				espaco.setId(rs.getInt("espaco_id"));
				reserva.setEspaco(espaco);
				

				
				reservas.add(reserva);
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		return reservas;
	}
	
	
	public List<Reserva> listar() {
		Statement stmt;
		List<Reserva> reservas = new ArrayList<>();
		try {
			stmt = conexao.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery("select reservas.id,titulo,descricao,justificativa,solicitante,telefone,data,hora_inicio,hora_fim,espaco_id,identificacao,andar,bloco_id,nome,letra,latitude,longitude,tipo_id,tipos.nome as nometipo, tipos.descricao from reservas,espacos,blocos,tipos where reservas.espaco_id = espacos.id and espacos.bloco_id = blocos.id and espacos.tipo_id = tipos.id;");
			while(rs.next()) {
				
				Reserva reserva = new Reserva();
				reserva.setId(rs.getInt("id"));
				
				reserva.setTitulo(rs.getString("titulo"));
				reserva.setDescricao(rs.getString("descricao"));
				reserva.setJustificativa(rs.getString("justificativa"));
				reserva.setSolicitante(rs.getString("solicitante"));
				reserva.setTelefone(rs.getString("telefone"));
				reserva.setData(rs.getDate("data").toLocalDate());
				reserva.setHoraInicio(rs.getTime("hora_inicio").toLocalTime());
				reserva.setHoraFim(rs.getTime("hora_fim").toLocalTime());

				
				Espaco espaco = new Espaco();				
				espaco.setId(rs.getInt("espaco_id"));
				espaco.setIdentificacao(rs.getString("identificacao"));
				espaco.setAndar(rs.getString("andar"));
				reserva.setEspaco(espaco);
				Bloco bloco = new Bloco();
				bloco.setId(rs.getInt("bloco_id"));
				bloco.setNome(rs.getString("nome"));
				bloco.setLetra(rs.getString("letra"));
				bloco.setLatitude(rs.getString("latitude"));
				bloco.setLongitude(rs.getString("longitude"));
				Tipo t = new Tipo();
				t.setId(rs.getInt("tipo_id"));
				t.setNome(rs.getString("nometipo"));
				t.setDescricao(rs.getString("descricao"));
				
				
				espaco.setBloco(bloco);
				espaco.setTipo(t);
				
				reservas.add(reserva);
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		return reservas;
	}


	public void inserir(Reserva reserva) {
		try {
			PreparedStatement ps = conexao.getConnection()
					.prepareStatement("insert into Reservas (titulo,descricao,justificativa,solicitante,telefone,data,hora_inicio,hora_fim,espaco_id) values (?,?,?,?,?,?,?,?,?);");
			ps.setString(1, reserva.getTitulo());
			ps.setString(2, reserva.getDescricao());
			ps.setString(3, reserva.getJustificativa());
			ps.setString(4, reserva.getSolicitante());
			ps.setString(5, reserva.getTelefone());
			ps.setDate(6, Date.valueOf(reserva.getData()));
			ps.setTime(7, Time.valueOf(reserva.getHoraInicio()));
			ps.setTime(8, Time.valueOf(reserva.getHoraFim()));
			ps.setInt(9, reserva.getEspaco().getId());
			ps.execute();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
