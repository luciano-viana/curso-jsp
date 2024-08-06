package servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;

import java.io.IOException;

import dao.DAOUsuarioRepository;

@WebServlet("/ServletUsuarioController")/*Mapemaento de URL que vem da tela*/
public class ServletUsuarioController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();
       
    public ServletUsuarioController() {
    	
    }
    
    //Deletar e consultar utilizar o Get
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			String acao = request.getParameter("acao");
			
			if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {
				
				String idUser = request.getParameter("id");
				
				daoUsuarioRepository.deletarUser(idUser);
				
				request.setAttribute("msg", "Ecluido com sucesso!");
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarajax")) {
					
					String idUser = request.getParameter("id");//Pegou o parâmetro
					
					daoUsuarioRepository.deletarUser(idUser);//Excluiu/Deletou
					
					response.getWriter().write("Ecluído com sucesso!");//Resposta
					
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjax")) {
				
				String nomeBusca = request.getParameter("nomeBusca");//Pegou o parâmetro
				System.out.println(nomeBusca);
				
				//daoUsuarioRepository.deletarUser(idUser);//Excluiu/Deletou
				
				//response.getWriter().write("Ecluído com sucesso!");//Resposta
			}
			
			else {
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			}
				
			
		
			
		}catch (Exception e) {
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");//direcionar para a tela erro
			request.setAttribute("msg", e.getMessage());//informar mensagem para o usuário
			redirecionar.forward(request, response);//dá o comando de redirecionamento e volta para tela mostrando a mensagem
		}
	}
	
	//Atualizar e Gravar utiliza Post
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
		String msg = "Operação realizada com sucesso!";
		
		//Pegar parâmetros
		String id = request.getParameter("id");
		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		
		//Iniciar um objeto
		ModelLogin modelLogin = new ModelLogin();
		modelLogin.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
		modelLogin.setNome(nome);
		modelLogin.setEmail(email);
		modelLogin.setLogin(login);
		modelLogin.setSenha(senha);
		
		if(daoUsuarioRepository.validarLogin(modelLogin.getLogin()) && modelLogin.getId() == null) {
			msg = "Já existe usuário com o mesmo login, informe outro login!";
		}else {
			if(modelLogin.isNovo()) {
				msg = "Gravado com sucesso!";
			}else {
				msg = "Atualizado com sucesso!";
			}
			
			modelLogin = daoUsuarioRepository.gravarUsuario(modelLogin);
		}
		
		request.setAttribute("msg", msg);
		request.setAttribute("modolLogin", modelLogin);
		//Redirecionar para não ter tela em branco
		request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
		
		}catch (Exception e) {
			e.printStackTrace();
			
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");//direcionar para a tela erro
			request.setAttribute("msg", e.getMessage());//informar mensagem para o usuário
			redirecionar.forward(request, response);//dá o comando de redirecionamento e volta para tela mostrando a mensagem
		}
		
	}

}
