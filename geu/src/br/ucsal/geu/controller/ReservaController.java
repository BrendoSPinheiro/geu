package br.ucsal.geu.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.ucsal.geu.dao.EspacoDAO;
import br.ucsal.geu.dao.ReservaDAO;
import br.ucsal.geu.model.Espaco;
import br.ucsal.geu.model.Reserva;

@WebServlet("/reservas")
public class ReservaController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String q = request.getParameter("q");
		if (q != null && q.equals("new")) {
			EspacoDAO dao = new EspacoDAO();
			request.setAttribute("lista", dao.listarLazy());
			request.getRequestDispatcher("reservaform.jsp").forward(request, response);
		}else {
			ReservaDAO dao = new ReservaDAO();
			request.setAttribute("lista", dao.listar());
			request.getRequestDispatcher("reservalist.jsp").forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
		String espacoID = request.getParameter("espaco");
		String titulo = request.getParameter("titulo");
		String descricao = request.getParameter("descricao");
		String justificativa = request.getParameter("justificativa");
		String solicitante = request.getParameter("solicitante");
		String telefone = request.getParameter("telefone");
		String data = request.getParameter("data");
		String inicio = request.getParameter("inicio");
		String fim = request.getParameter("fim");

		
		
		
		Reserva reserva = new Reserva();
		reserva.setTitulo(titulo);
		reserva.setDescricao(descricao);
		reserva.setJustificativa(justificativa);
		reserva.setSolicitante(solicitante);
		reserva.setTelefone(telefone);
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		reserva.setData(LocalDate.parse(data, dateFormat));
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("H:mm");

		reserva.setHoraInicio(LocalTime.parse(inicio, timeFormat));
		reserva.setHoraFim(LocalTime.parse(fim, timeFormat));

		EspacoDAO espacoDAO = new EspacoDAO();
		int id = Integer.parseInt(espacoID);
		Espaco espaco = espacoDAO.getByID(id);
		reserva.setEspaco(espaco);

		ReservaDAO dao = new ReservaDAO();
		
		List<Reserva> reservas = dao.listarLazy();
		
		Boolean existe = false;
		
		for (Reserva r : reservas) {
			if(  (r.getData().equals(reserva.getData()) && r.getEspaco().getId().equals(reserva.getEspaco().getId())) && 
					((reserva.getHoraFim().isAfter(r.getHoraInicio()) && reserva.getHoraFim().isBefore(r.getHoraFim())) || 
					(reserva.getHoraInicio().isAfter(r.getHoraInicio()) && reserva.getHoraInicio().isBefore(r.getHoraFim()))) )
			 {
				existe = true;
			}
		}
		
		if(!existe) {
			dao.inserir(reserva);
		}
		request.setAttribute("lista", dao.listar());
		request.getRequestDispatcher("reservalist.jsp").forward(request, response);

	}

}
