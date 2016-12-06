/**
 * 
 * Crud Usuario
 * @author Tauser Carneiro
 *
 */
package br.com.crud;

import br.com.crud.ui.UsuarioForm;

import totalcross.sys.Settings;
import totalcross.ui.MainWindow;
import totalcross.ui.UIColors;
import totalcross.ui.gfx.Color;

public class Main extends MainWindow {
	public Main(){
		super("TAUSER CARNEIRO", VERTICAL_GRADIENT);
	}
	
	public void initUI(){
		UIColors.controlsBack = Color.BRIGHT;
		setBackColor(Color.BRIGHT);
		setUIStyle(Settings.Android);
		swap(new UsuarioForm());
	}
}
