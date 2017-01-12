package model;

import java.util.ArrayList;

public abstract class TipoMaterial {
	private static ArrayList<String> tipos = new ArrayList<String>(){{
	    add("metal");
		add("organico");
		add("papel");
		add("plastico");
		add("vidro");
	}};

	public static ArrayList<String> getTipos() {
		return tipos;
	}	
}
