package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnectionBanco;
import model.ModelLogin;
import model.ModelTelefone;

public class DAOUsuarioRepository {
	
	private Connection connection;
	
	//construtor
	public DAOUsuarioRepository() {
		connection = SingleConnectionBanco.getConnection();
	}
	
	//Método grava e retorna
	public ModelLogin gravarUsuario(ModelLogin objeto, Long userLogado) throws Exception {
		
		if(objeto.isNovo()) {//Grava um novo usuário
		
		String sql = "INSERT INTO model_login(login, senha, nome, email, usuario_id, perfil, sexo, cep, logradouro, bairro, localidade, uf, numero, datanascimento, rendamensal) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		PreparedStatement preparedSql = connection.prepareStatement(sql);
		
		preparedSql.setString(1, objeto.getLogin());
		preparedSql.setString(2, objeto.getSenha());
		preparedSql.setString(3, objeto.getNome());
		preparedSql.setString(4, objeto.getEmail());
		preparedSql.setLong(5, userLogado);
		preparedSql.setString(6, objeto.getPerfil());
		preparedSql.setString(7, objeto.getSexo());
		
		//set endereço
		preparedSql.setString(8, objeto.getCep());
		preparedSql.setString(9, objeto.getLogradouro());
		preparedSql.setString(10, objeto.getBairro());
		preparedSql.setString(11, objeto.getLocalidade());
		preparedSql.setString(12, objeto.getUf());
		preparedSql.setString(13, objeto.getNumero());
		preparedSql.setDate(14, objeto.getDataNascimento());
		preparedSql.setDouble(15, objeto.getRendamensal());
		
		preparedSql.execute();
		
		connection.commit();
		
			if(objeto.getFotouser() != null && !objeto.getFotouser().isEmpty()) {
				sql = "update model_login set fotouser =?, extensafotouser=? where login =?";
				                                           
				
				preparedSql = connection.prepareStatement(sql);
				
				preparedSql.setString(1, objeto.getFotouser());
				preparedSql.setString(2, objeto.getExtensafotouser());
				preparedSql.setString(3, objeto.getLogin());
				
				preparedSql.execute();
				
				connection.commit();
			}
		
		}else {
			String sql = "UPDATE model_login SET login=?, senha=?, nome=?, email=?, perfil=?, sexo=?, cep=?, logradouro=?, bairro=?, localidade=?, uf=?, numero=?, datanascimento=?, rendamensal=? where id = "+objeto.getId()+";";
			
			PreparedStatement prepareSql = connection.prepareStatement(sql);
			
			prepareSql.setString(1, objeto.getLogin());
			prepareSql.setString(2, objeto.getSenha());
			prepareSql.setString(3, objeto.getNome());
			prepareSql.setString(4, objeto.getEmail());
			prepareSql.setString(5, objeto.getPerfil());
			prepareSql.setString(6, objeto.getSexo());
			
			//set endereço
			prepareSql.setString(7, objeto.getCep());
			prepareSql.setString(8, objeto.getLogradouro());
			prepareSql.setString(9, objeto.getBairro());
			prepareSql.setString(10, objeto.getLocalidade());
			prepareSql.setString(11, objeto.getUf());
			prepareSql.setString(12, objeto.getNumero());
			prepareSql.setDate(13, objeto.getDataNascimento());
			prepareSql.setDouble(14, objeto.getRendamensal());
			
			prepareSql.executeUpdate();
			
			connection.commit();
			
					if(objeto.getFotouser() != null && !objeto.getFotouser().isEmpty()) {
						sql = "update model_login set fotouser =?, extensafotouser=? where id =?";
						
						prepareSql = connection.prepareStatement(sql);
						
						prepareSql.setString(1, objeto.getFotouser());
						prepareSql.setString(2, objeto.getExtensafotouser());
						prepareSql.setLong(3, objeto.getId());
						
						prepareSql.execute();
						
						connection.commit();
					}
			
		}
		
		return this.consultaUsuario(objeto.getLogin(), userLogado);
	}
	
//---------------------------------------------------------------------------------------------------------------------------------------------------------
	
	//Método para buscar usuário por total de página	
public int consultaUsuarioListTotalPaginacao(String nome,Long userLogado) throws Exception{
		
		String sql = "select count(1) as total from model_login where upper(nome) like upper(?) and useradmin is false and usuario_id = ? ";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%" + nome + "%");
		statement.setLong(2, userLogado);
		
		ResultSet resultado = statement.executeQuery();
		
		resultado.next();
		
		Double cadastros = resultado.getDouble("total");
		
		Double porpagina = 5.0;
		
		Double pagina = cadastros / porpagina;
		
		Double resto = pagina % 2;
		
		if(resto > 0) {
			pagina ++;
		}
		
		return pagina.intValue();
		
	}


//---------------------------------------------------------------------------------------------------------------------------------------------------------
//Método para buscar usuário por nome com OffSet
	public List<ModelLogin> consultaUsuarioListOffSet(String nome,Long userLogado, int offset) throws Exception{
		
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		String sql = "select * from model_login where upper(nome) like upper(?) and useradmin is false and usuario_id = ? order by id desc offset "+offset+" limit 5" ;
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%" + nome + "%");
		statement.setLong(2, userLogado);
		
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()) {//Percorrer as linhas de resultado do SQL
			
			ModelLogin modelLogin = new ModelLogin();
			
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			//modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			 
			retorno.add(modelLogin);
		}
		
		return retorno;
	}
	
//---------------------------------------------------------------------------------------------------------------------------------------------------------
	
	//Método para buscar usuário por nome
	public List<ModelLogin> consultaUsuarioList(String nome,Long userLogado) throws Exception{
		
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		String sql = "select * from model_login where upper(nome) like upper(?) and useradmin is false and usuario_id = ? order by id desc limit 5" ;
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%" + nome + "%");
		statement.setLong(2, userLogado);
		
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()) {//Percorrer as linhas de resultado do SQL
			
			ModelLogin modelLogin = new ModelLogin();
			
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			//modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			 
			retorno.add(modelLogin);
		}
		
		return retorno;
	}
	
//---------------------------------------------------------------------------------------------------------------------------------------------------------
	
		//Método para buscar usuário
		public List<ModelLogin> consultaUsuarioListPaginada(Long userLogado, Integer offset) throws Exception{
			
			List<ModelLogin> retorno = new ArrayList<ModelLogin>();
			//alterei aqui, estava somente = order by nome
			String sql = "select * from model_login where useradmin is false and usuario_id = " + userLogado + " order by id desc offset " +offset+ " limit 5;"; 
			
			PreparedStatement statement = connection.prepareStatement(sql);
			
			ResultSet resultado = statement.executeQuery();
			
			while(resultado.next()) {//Percorrer as linhas de resultado do SQL
				
				ModelLogin modelLogin = new ModelLogin();
				
				modelLogin.setEmail(resultado.getString("email"));
				modelLogin.setId(resultado.getLong("id"));
				modelLogin.setLogin(resultado.getString("login"));
				modelLogin.setNome(resultado.getString("nome"));
				//modelLogin.setSenha(resultado.getString("senha"));
				modelLogin.setPerfil(resultado.getString("perfil"));
				modelLogin.setSexo(resultado.getString("sexo"));
				
				retorno.add(modelLogin);
			}
			
			return retorno;
		}
		
//-------------------------------------------------------------------------------------------------------------------------------------------------
	public int totalPagina(Long userLogado)throws Exception {
		String sql = "select count(1) as total from model_login where usuario_id = " + userLogado;
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultado = statement.executeQuery();
		
		resultado.next();
		
		Double cadastros = resultado.getDouble("total");
		
		Double porpagina = 5.0;
		
		Double pagina = cadastros / porpagina;
		
		Double resto = pagina % 2;
		
		if(resto > 0) {
			pagina ++;
		}
		
		return pagina.intValue();
	}
		
	
//---------------------------------------------------------------------------------------------------------------------------------------------------------
	
		//Método para buscar usuário para a tela de Relatório
		public List<ModelLogin> consultaUsuarioListRel(Long userLogado) throws Exception{
			
			List<ModelLogin> retorno = new ArrayList<ModelLogin>();
			
			String sql = "select * from model_login where useradmin is false and usuario_id = " + userLogado + " order by id desc;" ;
			PreparedStatement statement = connection.prepareStatement(sql);
			
			ResultSet resultado = statement.executeQuery();
			
			while(resultado.next()) {//Percorrer as linhas de resultado do SQL
				
				ModelLogin modelLogin = new ModelLogin();
				
				modelLogin.setEmail(resultado.getString("email"));
				modelLogin.setId(resultado.getLong("id"));
				modelLogin.setLogin(resultado.getString("login"));
				modelLogin.setNome(resultado.getString("nome"));
				//modelLogin.setSenha(resultado.getString("senha"));
				modelLogin.setPerfil(resultado.getString("perfil"));
				modelLogin.setSexo(resultado.getString("sexo"));
				
				modelLogin.setTelefones(this.listFone(modelLogin.getId()));
				
				retorno.add(modelLogin);
			}
			
			
			return retorno;
		}
		
//---------------------------------------------------------------------------------------------------------------------------------------------------------

		//Método para buscar usuário para a tela de Relatório por data inicial e data final
		public List<ModelLogin> consultaUsuarioListRel(Long userLogado, String dataInicial, String dataFinal) throws Exception{
			
			List<ModelLogin> retorno = new ArrayList<ModelLogin>();
			
			String sql = "select * from model_login where useradmin is false and usuario_id = " + userLogado +
					"and datanascimento >= ? and datanascimento <= ? order by id desc;" ;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setDate(1, Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataInicial))));
			statement.setDate(2, Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataFinal))));
			
			ResultSet resultado = statement.executeQuery();
			
			while(resultado.next()) {//Percorrer as linhas de resultado do SQL
				
				ModelLogin modelLogin = new ModelLogin();
				
				modelLogin.setEmail(resultado.getString("email"));
				modelLogin.setId(resultado.getLong("id"));
				modelLogin.setLogin(resultado.getString("login"));
				modelLogin.setNome(resultado.getString("nome"));
				//modelLogin.setSenha(resultado.getString("senha"));
				modelLogin.setPerfil(resultado.getString("perfil"));
				modelLogin.setSexo(resultado.getString("sexo"));
				
				modelLogin.setTelefones(this.listFone(modelLogin.getId()));
				
				retorno.add(modelLogin);
			}
			
			
			return retorno;
		}
		
//---------------------------------------------------------------------------------------------------------------------------------------------------------
	
		
	//Método para buscar usuário
	public List<ModelLogin> consultaUsuarioList(Long userLogado) throws Exception{
		
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		String sql = "select * from model_login where useradmin is false and usuario_id = " + userLogado + " order by id desc limit 5;" ;
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()) {//Percorrer as linhas de resultado do SQL
			
			ModelLogin modelLogin = new ModelLogin();
			
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			//modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			
			retorno.add(modelLogin);
		}
		
		
		return retorno;
	}
	
//---------------------------------------------------------------------------------------------------------------------------------------------------------
		
	    //Método para consulta usuário logado
		public ModelLogin consultaUsuarioLogado(String login) throws Exception {
		
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
			 modelLogin.setUseradmin(resultado.getBoolean("useradmin"));
			 modelLogin.setPerfil(resultado.getString("perfil"));
			 modelLogin.setSexo(resultado.getString("sexo"));
			 modelLogin.setFotouser(resultado.getString("fotouser"));
			 modelLogin.setCep(resultado.getString("cep"));
			 modelLogin.setLogradouro(resultado.getString("logradouro"));
			 modelLogin.setBairro(resultado.getString("bairro"));
			 modelLogin.setLocalidade(resultado.getString("localidade"));
			 modelLogin.setUf(resultado.getString("uf"));
			 modelLogin.setNumero(resultado.getString("numero"));
			 modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
			 modelLogin.setRendamensal(resultado.getDouble("rendamensal"));
		 }
		 
		return modelLogin;
	}
	
//---------------------------------------------------------------------------------------------------------------------------------------------------------	
	
	//Método consultar usuário pelo login
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
			 modelLogin.setPerfil(resultado.getString("perfil"));
			 modelLogin.setSexo(resultado.getString("sexo"));
			 modelLogin.setFotouser(resultado.getString("fotouser"));
			 modelLogin.setCep(resultado.getString("cep"));
			 modelLogin.setLogradouro(resultado.getString("logradouro"));
			 modelLogin.setBairro(resultado.getString("bairro"));
			 modelLogin.setLocalidade(resultado.getString("localidade"));
			 modelLogin.setUf(resultado.getString("uf"));
			 modelLogin.setNumero(resultado.getString("numero"));
			 modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
			 modelLogin.setRendamensal(resultado.getDouble("rendamensal"));
		 }
		 
		return modelLogin;
	}

//---------------------------------------------------------------------------------------------------------------------------------------------------------
	//Método para consultar usuário
	public ModelLogin consultaUsuario(String login, Long userLogado) throws Exception {
		
		//Objeto
		ModelLogin modelLogin = new ModelLogin();
		
		//Preparado o SQL para consulta no BD
		String sql = "select * from model_login where upper(login) = upper('"+login+"') and useradmin is false and usuario_id = " + userLogado;
		
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
			 modelLogin.setPerfil(resultado.getString("perfil"));
			 modelLogin.setSexo(resultado.getString("sexo"));
			 modelLogin.setFotouser(resultado.getString("fotouser"));
			 modelLogin.setCep(resultado.getString("cep"));
			 modelLogin.setLogradouro(resultado.getString("logradouro"));
			 modelLogin.setBairro(resultado.getString("bairro"));
			 modelLogin.setLocalidade(resultado.getString("localidade"));
			 modelLogin.setUf(resultado.getString("uf"));
			 modelLogin.setNumero(resultado.getString("numero"));
			 modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
			 modelLogin.setRendamensal(resultado.getDouble("rendamensal"));
		 }
		 
		return modelLogin;
	}
	
	//aqui
	
//---------------------------------------------------------------------------------------------------------------------------------------------------------	
//Método para consultar usuário por id será utilizado para carregar os dados no DAOTelefoneRepository
			public ModelLogin consultaUsuarioID(Long id) throws Exception {
				
				//Objeto
				ModelLogin modelLogin = new ModelLogin();
				
				//Preparado o SQL para consulta no BD
				String sql = "select * from model_login where id = ? and useradmin is false";
				
				//Setado os parâmetros
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setLong(1, id);
				
				//Executado o sql
				ResultSet resultado =  statement.executeQuery();
				 
				while (resultado.next()) {//Se tem resultado
					
					 modelLogin.setId(resultado.getLong("id"));
					 modelLogin.setNome(resultado.getString("nome"));
					 modelLogin.setEmail(resultado.getString("email"));
					 modelLogin.setLogin(resultado.getString("login"));
					 modelLogin.setSenha(resultado.getString("senha"));
					 modelLogin.setPerfil(resultado.getString("perfil"));
					 modelLogin.setSexo(resultado.getString("sexo"));
					 modelLogin.setFotouser(resultado.getString("fotouser"));
					 modelLogin.setExtensafotouser(resultado.getString("extensafotouser"));
					 modelLogin.setCep(resultado.getString("cep"));
					 modelLogin.setLogradouro(resultado.getString("logradouro"));
					 modelLogin.setBairro(resultado.getString("bairro"));
					 modelLogin.setLocalidade(resultado.getString("localidade"));
					 modelLogin.setUf(resultado.getString("uf"));
					 modelLogin.setNumero(resultado.getString("numero"));
					 modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
					 modelLogin.setRendamensal(resultado.getDouble("rendamensal"));
				 }
				 
				return modelLogin;
			}
	
	
//---------------------------------------------------------------------------------------------------------------------------------------------------------	
	
	//Método para consultar usuário por id
		public ModelLogin consultaUsuarioID(String id, Long userLogado) throws Exception {
			
			//Objeto
			ModelLogin modelLogin = new ModelLogin();
			
			//Preparado o SQL para consulta no BD
			String sql = "select * from model_login where id = ? and useradmin is false and usuario_id = ?";
			
			//Setado os parâmetros
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, Long.parseLong(id));
			statement.setLong(2, userLogado);
			
			//Executado o sql
			ResultSet resultado =  statement.executeQuery();
			 
			while (resultado.next()) {//Se tem resultado
				
				 modelLogin.setId(resultado.getLong("id"));
				 modelLogin.setNome(resultado.getString("nome"));
				 modelLogin.setEmail(resultado.getString("email"));
				 modelLogin.setLogin(resultado.getString("login"));
				 modelLogin.setSenha(resultado.getString("senha"));
				 modelLogin.setPerfil(resultado.getString("perfil"));
				 modelLogin.setSexo(resultado.getString("sexo"));
				 modelLogin.setFotouser(resultado.getString("fotouser"));
				 modelLogin.setExtensafotouser(resultado.getString("extensafotouser"));
				 modelLogin.setCep(resultado.getString("cep"));
				 modelLogin.setLogradouro(resultado.getString("logradouro"));
				 modelLogin.setBairro(resultado.getString("bairro"));
				 modelLogin.setLocalidade(resultado.getString("localidade"));
				 modelLogin.setUf(resultado.getString("uf"));
				 modelLogin.setNumero(resultado.getString("numero"));
				 modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
				 modelLogin.setRendamensal(resultado.getDouble("rendamensal"));
			 }
			 
			return modelLogin;
		}
	
//---------------------------------------------------------------------------------------------------------------------------------------------------------
	
	public boolean validarLogin(String login) throws Exception{
		String sql = "select count(1) > 0 as existe from model_login where upper(login) = upper('"+login+"');";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultado =  statement.executeQuery();
		
		resultado.next();//Para ele entrar nos resultados do sql
		return resultado.getBoolean("existe");
	}
	
//---------------------------------------------------------------------------------------------------------------------------------------------------------
	
	public void deletarUser(String idUser) throws Exception {
		String sql = "DELETE FROM model_login WHERE id = ? and useradmin is false "; //Montou SQL
		PreparedStatement prepareSql = connection.prepareStatement(sql);//Preparou SQL
		prepareSql.setLong(1, Long.parseLong(idUser)); //Setou o parâmetro
		prepareSql.executeUpdate(); // Executou
		
		connection.commit();
	}
	
//---------------------------------------------------------------------------------------------------------------------------------------------------------	
	//Método carregar lista de telefones dos usuários
		public List<ModelTelefone> listFone(Long idUserPai) throws Exception{
			
			List<ModelTelefone> retorno = new ArrayList<ModelTelefone>();
			
			String sql = "select * from telefone where usuario_pai_id = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setLong(1, idUserPai);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				
				ModelTelefone modelTelefone = new ModelTelefone();
				
				modelTelefone.setId(rs.getLong("id"));
				modelTelefone.setNumero(rs.getString("numero"));
				modelTelefone.setUsuario_cad_id(this.consultaUsuarioID(rs.getLong("Usuario_cad_id")));
				modelTelefone.setUsuario_pai_id(this.consultaUsuarioID(rs.getLong("Usuario_pai_id")));
				
				retorno.add(modelTelefone);
			}
			
			return retorno;
		}
		
}
