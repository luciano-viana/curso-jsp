package servlets;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import dao.DAOUsuarioRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;

@WebServlet("/SertvletTelefone")
public class SertvletTelefone extends ServletGenericUtil implements Serializable {

	private static final long serialVersionUID = 1L;

	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();

	public SertvletTelefone() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			String iduser = request.getParameter("iduser");

			if (iduser != null && iduser.isEmpty()) {

				ModelLogin modelLogin = daoUsuarioRepository.consultaUsuarioID(Long.parseLong(iduser));

				request.setAttribute("usuario", modelLogin);
				request.getRequestDispatcher("principal/telefone.jsp").forward(request, response);

			} else {
				List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
				request.setAttribute("modolLogins", modelLogins);
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));

				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
