package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnectionBanco;
import model.ModelLogin;

public class DAOUsuarioRepository {
	
	private Connection connection;
	
	//construtor
	public DAOUsuarioRepository() {
		connection = SingleConnectionBanco.getConnection();
	}
	
	//Método grava e retorna
	public ModelLogin gravarUsuario(ModelLogin objeto) throws Exception {
		
		if(objeto.isNovo()) {//Grava um novo usuário
		
		String sql = "INSERT INTO model_login(login, senha, nome, email) VALUES (?, ?, ?, ?);";
		PreparedStatement preparedSql = connection.prepareStatement(sql);
		
		preparedSql.setString(1, objeto.getLogin());
		preparedSql.setString(2, objeto.getSenha());
		preparedSql.setString(3, objeto.getNome());
		preparedSql.setString(4, objeto.getEmail());
		
		preparedSql.execute();
		connection.commit();
		
		}else {
			String sql = "UPDATE model_login SET login=?, senha=?, nome=?, email=? WHERE id = "+objeto.getId()+";";
			
			PreparedStatement prepareSql = connection.prepareStatement(sql);
			
			prepareSql.setString(1, objeto.getLogin());
			prepareSql.setString(2, objeto.getSenha());
			prepareSql.setString(3, objeto.getNome());
			prepareSql.setString(4, objeto.getEmail());
			
			prepareSql.executeUpdate();
			
			connection.commit();
		}
		
		return this.consultaUsuario(objeto.getLogin());
	}
	
	
	//Método para buscar usuário
	public List<ModelLogin> consultaUsuarioList(String nome) throws Exception{
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		String sql = "select * from model_login where upper(nome) like upper(?) and useradmin is false order by id;";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%" + nome + "%");
		
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()) {//Percorrer as linhas de resultado do SQL
			
			ModelLogin modelLogin = new ModelLogin();
			
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			//modelLogin.setSenha(resultado.getString("senha"));
			
			retorno.add(modelLogin);
		}
		
		return retorno;
	}
	
	
	//Método para buscar usuário
	public List<ModelLogin> consultaUsuarioList() throws Exception{
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		String sql = "select * from model_login where useradmin is false order by id desc;";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()) {//Percorrer as linhas de resultado do SQL
			
			ModelLogin modelLogin = new ModelLogin();
			
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			//modelLogin.setSenha(resultado.getString("senha"));
			
			retorno.add(modelLogin);
		}
		
		
		return retorno;
	}
	
	
	
	//Método para consultar usuário
	public ModelLogin consultaUsuario(String login) throws Exception {
		
		//Objeto
		ModelLogin modelLogin = new ModelLogin();
		
		//Preparado o SQL para consulta no BD
		String sql = "select * from model_login where upper(login) = upper('"+login+"') and useradmin is false";
		
		//Setado os parâmetros
		PreparedStatement statement = connection.prepareStatement(sql);
		
		//Executado o sql
		ResultSet resultado =  statement.executeQuery();
		 
		while (resultado.next()) {//Se tem resultado
			 modelLogin.setId(resultado.getLong("id"));
			 modelLogin.setNome(resultado.getString("nome"));
			 modelLogin.setEmail(resultado.getString("email"));
			 modelLogin.setLogin(resultado.getString("login"));
			 modelLogin.setSenha(resultado.getString("senha"));
		 }
		 
		return modelLogin;
	}
	
	
	//Método para consultar usuário por id
		public ModelLogin consultaUsuarioID(String id) throws Exception {
			
			//Objeto
			ModelLogin modelLogin = new ModelLogin();
			
			//Preparado o SQL para consulta no BD
			String sql = "select * from model_login where id = ? and useradmin is false";
			
			//Setado os parâmetros
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, Long.parseLong(id));
			
			//Executado o sql
			ResultSet resultado =  statement.executeQuery();
			 
			while (resultado.next()) {//Se tem resultado
				 modelLogin.setId(resultado.getLong("id"));
				 modelLogin.setNome(resultado.getString("nome"));
				 modelLogin.setEmail(resultado.getString("email"));
				 modelLogin.setLogin(resultado.getString("login"));
				 modelLogin.setSenha(resultado.getString("senha"));
			 }
			 
			return modelLogin;
		}
	
	
	public boolean validarLogin(String login) throws Exception{
		String sql = "select count(1) > 0 as existe from model_login where upper(login) = upper('"+login+"');";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultado =  statement.executeQuery();
		
		resultado.next();//Para ele entrar nos resultados do sql
		return resultado.getBoolean("existe");
	}
	
	public void deletarUser(String idUser) throws Exception {
		String sql = "DELETE FROM model_login WHERE id = ? and useradmin is false "; //Montou SQL
		PreparedStatement prepareSql = connection.prepareStatement(sql);//Preparou SQL
		prepareSql.setLong(1, Long.parseLong(idUser)); //Setou o parâmetro
		prepareSql.executeUpdate(); // Executou
		
		connection.commit();
	}
		
}
	


















