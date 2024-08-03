package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
		
		String sql = "INSERT INTO model_login(login, senha, nome, email) VALUES (?, ?, ?, ?);";
		PreparedStatement preparedSql = connection.prepareStatement(sql);
		
		preparedSql.setString(1, objeto.getLogin());
		preparedSql.setString(2, objeto.getSenha());
		preparedSql.setString(3, objeto.getNome());
		preparedSql.setString(4, objeto.getEmail());
		
		preparedSql.execute();
		connection.commit();
		
		return this.consultaUsuario(objeto.getLogin());
	}
	
	//Método para consultar usuário
	public ModelLogin consultaUsuario(String login) throws Exception {
		
		//Objeto
		ModelLogin modelLogin = new ModelLogin();
		
		//Preparado o SQL para consulta no BD
		String sql = "select * from model_login where upper(login) = upper('"+login+"')";
		
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
	
	public boolean validarLogin(String login) throws Exception{
		String sql = "select count(1) > 0 as existe from model_login where upper(login) = upper('"+login+"');";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultado =  statement.executeQuery();
		
		resultado.next();//Para ele entrar nos resultados do sql
		return resultado.getBoolean("existe");
	}
	
}

















