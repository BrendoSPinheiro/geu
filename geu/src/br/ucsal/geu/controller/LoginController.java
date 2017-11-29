package br.ucsal.geu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.security.MD5Encoder;

import sun.security.provider.MD5;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("login.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String usuario = request.getParameter("usuario");
			String senha = request.getParameter("senha");
			String md5 = MD5Encoder.encode(senha.getBytes());
		
			System.out.println(senha);
			
			//criar o dao
			if(usuario.equalsIgnoreCase(senha)) {
				System.out.println("LOGOU");
				request.getSession().setAttribute("usuario", usuario);
				request.getRequestDispatcher("index.jsp").forward(request, response);
			}else {
				System.out.println("NÃO LOGOU");
				response.sendRedirect("/geu/login");
			}
			
			
	}

}
