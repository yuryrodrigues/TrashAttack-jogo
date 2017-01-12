package view;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

public class Fonte {
	//nome da fonte utilizada no jogo
	private static String nomeFonteTexto = "Ubuntu-Regular.ttf";
	private static Font fonteTexto;
	
	private static void adicionaFonte(){
		try {
			//ler a fonte
		    InputStream in 	= Fonte.class.getClassLoader().getResourceAsStream("recursos/"+nomeFonteTexto);
			
			//adiciona a fonte ao jogo
			fonteTexto 		= Font.createFont(Font.TRUETYPE_FONT, in);
		} 
		catch (FontFormatException e) {
			throw new RuntimeException("Problema com o formato da fonte:"+nomeFonteTexto+" -- "+e);
		} 
		catch (IOException e) {
			throw new RuntimeException("Nao foi possível ler a fonte:"+nomeFonteTexto+" -- "+e);
		}
	}

	public static Font getFonteTexto() {
		//verifica se a fonte ainda nao foi lida
		if(fonteTexto == null){
			adicionaFonte();
		}
		
		return fonteTexto;
	}
}
