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
import totalcross.ui.Control;
import totalcross.ui.Edit;
import totalcross.ui.Label;
import totalcross.ui.Window;
import totalcross.ui.dialog.MessageBox;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;

public class UsuarioEditForm extends Window{
	
	private UsuarioDAO usuarioDao;
	private Edit edId, edNome, edIdade;
	private Button btSalvar, btCancelar;
	
	private static final int 	SAVE_BTN_ID	  =	100;
	private static final int 	CANCEL_BTN_ID =	101;
	
	private String age = "";

	public UsuarioEditForm(UsuarioDAO usuarioDao, Usuario usuario, int editMode) {
		this.usuarioDao = usuarioDao;
		this.appObj = usuario;
		this.appId = editMode;
		
	}
	
	protected void postPopup(){
		super.postPopup();
		initUI();
	}
	
	public void initUI(){
		 
		if (((Usuario) appObj).getIdade() <= 0){
			age = "";
		}else{
			age = Convert.toString(((Usuario) appObj).getIdade());
		}
		
		btSalvar = new Button("Salvar");
		btCancelar = new Button("Cancelar");
		edId = new Edit("999999");
		edNome = new Edit();
		edIdade = new Edit("99999");
		edIdade.setMode(edIdade.NORMAL, true);
		edIdade.setValidChars("0123456789");
		
		btSalvar.appId = SAVE_BTN_ID;
		btCancelar.appId = CANCEL_BTN_ID;
		edId.setEditable(false);
		edId.setText(Convert.toString(((Usuario) appObj).getId()));
		edNome.setText(((Usuario) appObj).getNome());
		edIdade.setText(age);
		
		add(btCancelar, RIGHT - 2, TOP + 2, PREFERRED + 2, PREFERRED + 2 );
		add(btSalvar, BEFORE - 2, SAME, SAME, SAME );
		/*add(new Label("ID"), LEFT + 2, AFTER + 5);
		add(edId, SAME, AFTER + 2);*/
		add(new Label("Nome"), LEFT + 2, AFTER + 5);
		add(edNome, SAME, AFTER + 2 );
		add(new Label("Idade"), LEFT + 2, AFTER + 5);
		add(edIdade, SAME, AFTER + 2 );
		
	}
	
	public void onEvent(Event event){
		
		switch (event.type) {
			case ControlEvent.PRESSED:
				switch (((Control) event.target).appId) {
					case SAVE_BTN_ID:
						if(validateDate()){
							switch (appId) {
								case Constants.INSERT:
									usuarioDao.insert((Usuario) appObj);
									break;
								case Constants.UPDATE:
									usuarioDao.update((Usuario) appObj);
									break;
							}
							unpop();
						}
						break;
					
					case CANCEL_BTN_ID:
						unpop();
						break;
				}
				break;

		}
	}

	private boolean validateDate() {
		
		if(!(this.edNome.getLength() > 0)){
			new MessageBox("ERROR!", "Nome invalido").popup();
			return false;
		}
		
		try {
			if(!(Convert.toInt(this.edIdade.getText()) > 0)){
				new MessageBox("ERROR!", "Idade invalida").popup();
				return false;
			}
		} catch (InvalidNumberException e1) {
			e1.printStackTrace();
		}
		
		try {
			((Usuario) appObj).setId(Convert.toInt(this.edId.getText()));
			((Usuario) appObj).setNome(this.edNome.getText());
			((Usuario) appObj).setIdade(Convert.toInt(this.edIdade.getText()));
		} catch (InvalidNumberException e) {
			e.printStackTrace();
		}
		
		return true;
	}
}
