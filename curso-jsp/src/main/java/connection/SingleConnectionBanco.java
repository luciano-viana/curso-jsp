package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnectionBanco {
	
	//String de conexão do banco de dados
	private static String banco = "jdbc:postgresql://localhost:5433/curso-jsp?autoReconnect=true";
	private static String user = "postgres";
	private static String senha = "admin";
	private static Connection connection = null; //Objeto de conexão
	
	//Método para retornar conexão existente 
	public static Connection getConnection() {
		return connection;
	}
	
	
	//De qualquer lugar que chamar a classe "SingleConnectionBanco" direto sempre vai obter uma conexão
	static {
		conectar();
	}
	
	//Construtor
	public SingleConnectionBanco() {//quando tiver uma instância vai conectar
		conectar();
	}
	
	//Método
	private static void conectar() {
		
		try {
			
			if(connection == null) {
				Class.forName("org.postgresql.Driver");//Carrega o driver de conexão do banco
				connection = DriverManager.getConnection(banco, user, senha);
				connection.setAutoCommit(false);//false para não efetuar alterações no banco sem o nosso comando
			}
			
		} catch (Exception e) {
			e.printStackTrace();//Mostrar qualquer erro no momento de conectar
		}
	}

}
