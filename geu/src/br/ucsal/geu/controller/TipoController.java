package br.ucsal.geu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.ucsal.geu.dao.TipoDAO;
import br.ucsal.geu.model.Tipo;

@WebServlet("/tipos")
public class TipoController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String q = request.getParameter("q");
		
		if (q != null && q.equals("new")) {
			request.getRequestDispatcher("tipoform.jsp").forward(request, response);
			return;
		}
		

		TipoDAO dao = new TipoDAO();

		if (q != null && q.equals("editar")) {
			String id = request.getParameter("id");
			Tipo tipo = dao.getByID(Integer.parseInt(id));
			request.setAttribute("tipo", tipo);
			request.getRequestDispatcher("tipoform.jsp").forward(request, response);
		}

		
		if (q != null && q.equals("excluir")) {
				String id = request.getParameter("id");
				dao.delete(Integer.parseInt(id));
		}
		
		request.setAttribute("lista", dao.listar());
		request.getRequestDispatcher("tipolist.jsp").forward(request, response);
		
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String id = request.getParameter("id");
		String nome = request.getParameter("nome");
		String descricao = request.getParameter("descricao");
		Tipo tipo = new Tipo(nome, descricao);
		TipoDAO dao = new TipoDAO();
		if(id != null && !id.isEmpty()) {
			tipo.setId(Integer.parseInt(id));
			dao.update(tipo);
		}else {
			dao.inserir(tipo);
		}
		

		request.setAttribute("lista", dao.listar());
		request.getRequestDispatcher("tipolist.jsp").forward(request, response);

	}
}
