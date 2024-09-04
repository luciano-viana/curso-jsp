package servlets;

import java.io.Serializable;

import dao.DAOUsuarioRepository;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import model.ModelLogin;

public class ServletGenericUtil extends HttpServlet implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository(); 
	
	//Método que retorna o usuário logado
	public Long getUserLogado(HttpServletRequest request) throws Exception {
		
		HttpSession session = request.getSession();

		String usuariologado = (String) session.getAttribute("usuario");
		
		return daoUsuarioRepository.consultaUsuarioLogado(usuariologado).getId();
	}
	
	//Método que retorna o usuário logado
		public ModelLogin getUserLogadoObjt(HttpServletRequest request) throws Exception {
			
			HttpSession session = request.getSession();

			String usuariologado = (String) session.getAttribute("usuario");
			
			return daoUsuarioRepository.consultaUsuarioLogado(usuariologado);
		}
}
