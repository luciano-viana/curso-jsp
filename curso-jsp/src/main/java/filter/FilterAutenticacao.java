package filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = {"/principal/*"})//Filter intercepta todas as requisições que vierem do projeto ou mapeamento
public class FilterAutenticacao implements Filter {

	public FilterAutenticacao() {
	}

	//Método destroy encerra os processos quando o servidor é parado "exemplo matar uma conexão com o banco de dados"
	//O que iria desbruir se o projeto morrer ou o servidor parar? Mataria os processos de conexão com o banco
	public void destroy() {
	}

	//Intercepta todas as requesições e as respostas no sistema
	//Tudo que fizer no sistema vai passar pelo doFiltro
	//Exemplo: Validação de autentica, Dar commit e rolbak de transações do banco, Validar e fazer redirecionamento de páginas
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		
		String usuariologado = (String) session.getAttribute("usuario");
		
		String urlParaAutenticar = req.getServletPath();//url que está sendo acessada
		
		//Validar se está logado, se não redireciona para a tela de login
		if(usuariologado == null || (usuariologado != null && usuariologado.isEmpty()) && 
				!urlParaAutenticar.contains("/principal/ServletLogin")) {//Não está logado
			RequestDispatcher redireciona = request.getRequestDispatcher("/index.jsp?url="+urlParaAutenticar);
			request.setAttribute("msg", "por favor realize o login!");
			redireciona.forward(request, response);
			return;//Para a execução e redirecionar para o login do sistema
			
		}else {
			chain.doFilter(request, response);
		}
		
	}
	
	//Inicia os processos e recursos quando o servidor sobe o projeto "exemplo: iniciar a conexão com o banco"
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
