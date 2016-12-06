/**
 * 
 * Crud Usuario
 * @author Tauser Carneiro
 *
 */
package br.com.crud.ui;

import br.com.crud.business.Constants;
import br.com.crud.dao.UsuarioDAO;
import br.com.crud.model.Usuario;

import totalcross.sys.Convert;
import totalcross.sys.InvalidNumberException;
import totalcross.ui.Button;
import totalcross.ui.Container;
import totalcross.ui.Control;
import totalcross.ui.Grid;
import totalcross.ui.dialog.MessageBox;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;

public class UsuarioForm extends Container{
	private static final int 	INSERT_BTN_ID  	= 	100;
	private static final int	UPDATE_BTN_ID	= 	101;
	private static final int	DELETE_BTN_ID	=	102;
	private final UsuarioDAO usuarioDao;
	private Grid 				grid;
	
	public UsuarioForm(){
		usuarioDao = new UsuarioDAO();
	}
	
	public void initUI(){
		Button insertButton = new Button("Novo");
		Button updateButton = new Button("Editar");
		Button deleteButton = new Button("Excluir");
		
		insertButton.appId = INSERT_BTN_ID;
		updateButton.appId = UPDATE_BTN_ID;
		deleteButton.appId = DELETE_BTN_ID;
		
		add(deleteButton, RIGHT - 2, TOP + 2, PREFERRED + 5, PREFERRED + 5);
		add(updateButton, BEFORE - 2, SAME, SAME, SAME);
		add(insertButton, BEFORE - 2, SAME, SAME, SAME);
		
		grid = new Grid(new String[] {"ID", "Nome", "Idade"}, 
				new int[] { -10, -70, -10}, 
				new int [] {CENTER, LEFT, CENTER},
				false);
		
		add(grid, LEFT, AFTER + 2, FILL - 4, FILL - 4 );
		
		grid.setItems(usuarioDao.listAll());
		
	}
	
	public void onEvent(Event event){
		
		switch (event.type) {
		case ControlEvent.PRESSED:
			switch (((Control) event.target).appId) {
				case INSERT_BTN_ID:
					new UsuarioEditForm(usuarioDao, new Usuario(), Constants.INSERT).popupNonBlocking();
					break;
	
				case UPDATE_BTN_ID:
					if(isSelected()){
						new UsuarioEditForm(usuarioDao, getSelected(), Constants.UPDATE).popupNonBlocking();
					}
					break;
				
				case DELETE_BTN_ID:
					if(isSelected()){
						usuarioDao.delete(getSelected());
						grid.setItems(usuarioDao.listAll());
						grid.setSelectedIndex(-1);
					}
					break;
				}
				break;

		case ControlEvent.WINDOW_CLOSED:
			if(event.target instanceof UsuarioEditForm){
				grid.setItems(usuarioDao.listAll());
				grid.setSelectedIndex(-1);
			}
			break;
		}
	}

	private Usuario getSelected() {
		Usuario usuario = new Usuario();
		String[] col = grid.getSelectedItem();
		try {
			usuario.setId(Convert.toInt(col[0]));
			usuario.setNome(col[1]);
			usuario.setIdade(Convert.toInt(col[2]));
		} catch (InvalidNumberException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return usuario;
	}

	private boolean isSelected() {
		boolean selectedItem = grid.getSelectedIndex() >= 0;
		if(!selectedItem){
			new MessageBox("Error", "Selecione um Item!").popup();
		}
		return selectedItem;
	}
}
