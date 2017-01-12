package model;

public class Tiro extends Entidade{
	private String tipo;
	public static int imgLargura = 45;
	
	public Tiro(int x, int y){
		setX(x);
		setY(y);
		setLargura(45);
		setAltura(70);
		setImg("lixeira-"+TipoMaterial.getTipos().get(0)+".png");
	}
	
	public Tiro(int x, int y, String tipo){
		this(x, y);
		setTipo(tipo);
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo){
		//pega os tipos de tiros
		for(int i=0; i<TipoMaterial.getTipos().size(); i++){
			//verifica qual foi o tipo de tiro escolhido
			if(tipo.equals(TipoMaterial.getTipos().get(i))){
				//definie o tipo de tiro
				setImg("lixeira-"+tipo+".png");
				this.tipo = tipo;
				
				//para o laco de repeticao
				break;
			}
		}
	}
}