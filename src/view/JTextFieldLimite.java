package view;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class JTextFieldLimite extends PlainDocument{
	//limite de caracteres permitidos em uma JTextField
	private int limite;
	
	public JTextFieldLimite(int limite) {
		super();
		this.limite = limite;
	}
	
	//insere um novo caracter
	public void insertString( int offset, String  str, AttributeSet attr ) throws BadLocationException {
		if(str == null){
			return;
		}
		//verifica se o texto ja ultrapassou o limite
		else if((getLength() + str.length()) <= limite){
			//se não tiver ultrapassado, insere o novo caracter
			super.insertString(offset, str, attr);
		}
	}
}
