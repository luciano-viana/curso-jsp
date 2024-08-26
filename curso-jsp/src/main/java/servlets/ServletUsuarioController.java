package servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.ModelLogin;


import java.io.IOException;
import java.util.List;

import org.apache.tomcat.jakartaee.commons.compress.utils.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.DAOUsuarioRepository;

@MultipartConfig //Anotação utilizada para preparar para upload
@WebServlet(urlPatterns = {"/ServletUsuarioController"})/*Mapemaento de URL que vem da tela*/
public class ServletUsuarioController extends ServletGenericUtil{
	
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
				
				List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
				request.setAttribute("modolLogins", modelLogins);
				
				request.setAttribute("msg", "Ecluido com sucesso!");
				
				//Montar a paginação
				request.setAttribute("totalPagina",daoUsuarioRepository.totalPagina(this.getUserLogado(request)) );
				
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				
				
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarajax")) {
					
					String idUser = request.getParameter("id");//Pegou o parâmetro
					
					daoUsuarioRepository.deletarUser(idUser);//Excluiu/Deletou
					
					response.getWriter().write("Ecluído com sucesso!");//Resposta
					
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjax")) {
				
				String nomeBusca = request.getParameter("nomeBusca");//Pegou o parâmetro
				//System.out.println(nomeBusca);*/
				
				List<ModelLogin> dadosJsonUser = daoUsuarioRepository.consultaUsuarioList(nomeBusca, super.getUserLogado(request));
		
				ObjectMapper mapper = new ObjectMapper();
				
				String json = mapper.writeValueAsString(dadosJsonUser);
				
				response.getWriter().write(json);
				
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarEditar")) {
				
				String id = request.getParameter("id");
				
				ModelLogin modelLogin = daoUsuarioRepository.consultaUsuarioID(id, super.getUserLogado(request));
				
				List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
				request.setAttribute("modolLogins", modelLogins);
				
				request.setAttribute("msg", "Usuário em edição");
				request.setAttribute("modolLogin", modelLogin);
				//Montar a paginação
				request.setAttribute("totalPagina",daoUsuarioRepository.totalPagina(this.getUserLogado(request)) );
				//Redirecionar para não ter tela em branco
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				
			}
			else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listarUser")) {
				
				List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
				
				request.setAttribute("msg", "Usuários carregados");
				request.setAttribute("modolLogins", modelLogins);
				//Montar a paginação
				request.setAttribute("totalPagina",daoUsuarioRepository.totalPagina(this.getUserLogado(request)) );
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			}
			else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("downloadFoto")) {
				
				String idUser = request.getParameter("id");
				
				ModelLogin modelLogin = daoUsuarioRepository.consultaUsuarioID(idUser, super.getUserLogado(request));
				if(modelLogin.getFotouser() != null && !modelLogin.getFotouser().isEmpty()) {
					
					//fazer download da foto do usuário
					response.setHeader("Content-Disposition", "attachment;filename=arquivo." + modelLogin.getExtensafotouser());
					response.getOutputStream().write(new Base64().decode(modelLogin.getFotouser().split("\\,")[1]));
				}
				
			}
			
			else {
				List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
				request.setAttribute("modolLogins", modelLogins);
				//Montar a paginação
				request.setAttribute("totalPagina",daoUsuarioRepository.totalPagina(this.getUserLogado(request)) );
				
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				
			}
				
		}catch (Exception e) {
			e.printStackTrace();
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
		String perfil = request.getParameter("perfil");
		String sexo = request.getParameter("sexo");
		
		String cep = request.getParameter("cep");
		String lougradouro = request.getParameter("lougradouro");
		String bairro = request.getParameter("bairro");
		String localidade = request.getParameter("localidade");
		String uf = request.getParameter("uf");
		String numero = request.getParameter("numero");
		
		//Iniciar um objeto
		ModelLogin modelLogin = new ModelLogin();
		
		modelLogin.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
		modelLogin.setNome(nome);
		modelLogin.setEmail(email);
		modelLogin.setLogin(login);
		modelLogin.setSenha(senha);
		modelLogin.setPerfil(perfil);
		modelLogin.setSexo(sexo);
		
		modelLogin.setCep(cep);
		modelLogin.setLogradouro(lougradouro);
		modelLogin.setBairro(bairro);
		modelLogin.setLocalidade(localidade);
		modelLogin.setUf(uf);
		modelLogin.setNumero(numero);
		
		if(ServletFileUpload.isMultipartContent(request)) {
			Part part = request.getPart("fileFoto");//Pega foto da tela
			
			if(part.getSize() > 0){
				byte[] foto = IOUtils.toByteArray(part.getInputStream());//Converte imagem para byte
				String imagemBase64 = "data:image/" + part.getContentType().split("\\/")[1] + ";base64," +  new Base64().encodeBase64String(foto);
				
				modelLogin.setFotouser(imagemBase64);
				modelLogin.setExtensafotouser(part.getContentType().split("\\/")[1]);
			}
		}
		
		if(daoUsuarioRepository.validarLogin(modelLogin.getLogin()) && modelLogin.getId() == null) {
			msg = "Já existe usuário com o mesmo login, informe outro login!";
		}else {
			if(modelLogin.isNovo()) {
				msg = "Gravado com sucesso!";
			}else {
				msg = "Atualizado com sucesso!";
			}
			
			modelLogin = daoUsuarioRepository.gravarUsuario(modelLogin,super.getUserLogado(request));
		}
		
		List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
		request.setAttribute("modolLogins", modelLogins);
		
		request.setAttribute("msg", msg);
		request.setAttribute("modolLogin", modelLogin);
		//Montar a paginação
		request.setAttribute("totalPagina",daoUsuarioRepository.totalPagina(this.getUserLogado(request)) );
		
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
