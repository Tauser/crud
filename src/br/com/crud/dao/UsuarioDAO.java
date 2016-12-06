/**
 * 
 * Crud Usuario
 * @author Tauser Carneiro
 *
 */
package br.com.crud.dao;

import br.com.crud.model.Usuario;

import litebase.LitebaseConnection;
import litebase.PreparedStatement;
import litebase.ResultSet;
import totalcross.sql.Connection;

public class UsuarioDAO {
		
	private LitebaseConnection connection = LitebaseConnection.getInstance("APPI");
	private PreparedStatement prepStmtInsert;
	private PreparedStatement prepStmtUpdate;
	private PreparedStatement prepStmtDelete;
	private PreparedStatement prepStmtSelect;
	private PreparedStatement prepStmtId;

	public UsuarioDAO() {
		
		if(!connection.exists("usuario")){
			connection.execute("create table usuario "
					+ "(id int primary key not null,"
					+ "nome char(100) not null,"
					+ "idade int not null)");
		}
	}
	
	public void insert(Usuario usuario){
		
		prepStmtInsert = connection.prepareStatement(" insert into usuario values (?, ?, ?) ");
		
		usuario.setId(nextId());
		
		prepStmtInsert.clearParameters();
		prepStmtInsert.setInt(0, usuario.getId());
		prepStmtInsert.setString(1, usuario.getNome());
		prepStmtInsert.setInt(2, usuario.getIdade());
		
		prepStmtInsert.executeUpdate();
	}
	
	public void update(Usuario usuario){
		
		prepStmtUpdate = connection.prepareStatement(" update usuario set nome = ?, idade = ? where id = ? ");
		
		prepStmtUpdate.clearParameters();
		prepStmtUpdate.setString(0, usuario.getNome());
		prepStmtUpdate.setInt(1, usuario.getIdade());
		prepStmtUpdate.setInt(2, usuario.getId());
		
		
		prepStmtUpdate.executeUpdate();
	}
	
	public void delete(Usuario usuario){
		
		prepStmtDelete = connection.prepareStatement(" delete usuario where id = ? ");
		
		prepStmtDelete.clearParameters();
		prepStmtDelete.setInt(0, usuario.getId());
		
		prepStmtDelete.executeUpdate();
	}
	
	public String [] [] listAll(){
		
		String [] [] res = null;
	
		prepStmtSelect = connection.prepareStatement(" select * from usuario ");
		
		ResultSet result = prepStmtSelect.executeQuery();
		
		if(result.next()){
			result.first();
			res = result.getStrings();
		}
		
		result.close();
		
		return res;
	}
	
	private int nextId(){
		
		prepStmtId = connection.prepareStatement(" select max(id) as vId from usuario ");
		
		int res = 1;
		
		ResultSet result = prepStmtId.executeQuery();
		result.beforeFirst();
		
		while(result.next()){
			res = result.getInt("vId")+1;
		}
		result.close();
		
		return res;
	}
}
