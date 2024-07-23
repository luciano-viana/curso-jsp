package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.SingleConnectionBanco;
import model.ModelLogin;

public class DAOLoginRepository {
	
	//Objeto de conexão
	private Connection connection;
	
	//Construtor
	public DAOLoginRepository() {
	 connection = SingleConnectionBanco.getConnection();
	}
	
	//Método para validar o login
	public boolean validarAutenticacao(ModelLogin modeLogin) throws Exception {
		
		//SQL de consulta
		String sql = "select * from model_login where upper(login) = upper(?) and upper(senha) = upper(?)";
		
		PreparedStatement statement = connection.prepareStatement(sql);//Objeto para preparar o sql
		
		statement.setString(1, modeLogin.getLogin());
		statement.setString(2, modeLogin.getSenha());
		
		ResultSet resultSet = statement.executeQuery();//Objeto de resultado
		
		if(resultSet.next()) {
			return true;//autenticado
		} 
			return false;//nao autenticado
	}

}
