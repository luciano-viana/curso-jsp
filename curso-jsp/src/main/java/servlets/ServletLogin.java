package servlets;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;

//O chamado Controller são as servlets ou ServletLoginController
//@WebServlet("/ServletLogin")/*Mapemaento de URL que vem da tela*/
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ServletLogin() {

	}

	// Recebe os dados pela url em parâmetros
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	// Recebe os dados enviados por um formulário
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//Irá receber os parâmetros de login e senha da tela
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		
		//Objetivo irá receber os dados do login e senha
		ModelLogin modelLogin = new ModelLogin();
		modelLogin.setLogin(login);
		modelLogin.setSenha(senha);
		
	}

}
