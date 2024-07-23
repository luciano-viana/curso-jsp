package servlets;

import java.io.IOException;

import dao.DAOLoginRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;

//O chamado Controller são as servlets ou ServletLoginController
@WebServlet(urlPatterns = {"/principal/ServletLogin","/ServletLogin"} )/*Mapemaento de URL que vem da tela*/
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private DAOLoginRepository daoLoginRepository = new DAOLoginRepository();

	public ServletLogin() {

	}

	// Recebe os dados pela url em parâmetros
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	// Recebe os dados enviados por um formulário
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Irá receber os parâmetros de login e senha da tela
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		String url = request.getParameter("url");
		
		try {

				// Validar se login e senha foram informados
				if (login != null && !login.isEmpty() && senha != null && !senha.isEmpty()) {
		
					// Objetivo irá receber os dados do login e senha
					ModelLogin modelLogin = new ModelLogin();
					modelLogin.setLogin(login);
					modelLogin.setSenha(senha);
					
					if(daoLoginRepository.validarAutenticacao(modelLogin)) {//Simulando um login
						
						request.getSession().setAttribute("usuario", modelLogin.getLogin());
						
						//Validação da url
						if(url == null | url.equals("null")) {
							url = "principal/principal.jsp";
						}
						
						RequestDispatcher redirecionar = request.getRequestDispatcher(url);
						redirecionar.forward(request, response);
						
					}else {
						RequestDispatcher redirecionar = request.getRequestDispatcher("/index.jsp");
						request.setAttribute("msg", "Informe o login e senha corretamente!");
						redirecionar.forward(request, response);
					}
					
				}else {
					RequestDispatcher redirecionar = request.getRequestDispatcher("index.jsp");//direcionar para a tela principal
					request.setAttribute("msg", "Informe o login e senha corretamente!");//informar mensagem para o usuário
					redirecionar.forward(request, response);//dá o comando de redirecionamento e volta para tela mostrando a mensagem
				}
		
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
