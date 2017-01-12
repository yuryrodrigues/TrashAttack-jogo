package model;

public class Lixo extends Entidade {
	private String tipo;
	private int pontos;
	private int qtVidaRetirada;
	
	public Lixo(){
		setLargura(40);
		setAltura(40);
		pontos 			= 10;
		qtVidaRetirada 	= 10;
	}
	
	public Lixo(String tipo){
		this();
		setTipo(tipo);
	}

	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {		
		//pega os tipos de tiros
		for(int i=0; i<TipoMaterial.getTipos().size(); i++){
			//verifica qual foi o tipo de tiro escolhido
			if(tipo.equals(TipoMaterial.getTipos().get(i))){
				//definie o tipo de tiro
				setImg(tipo+".png");
				this.tipo = tipo;
				
				//para o laco de repeticao
				break;
			}
		}
	}
	
	public int getPontos() {
		return pontos;
	}
	
	public void setPontos(int pontos) {
		this.pontos = pontos;
	}

	public int getQtVidaRetirada() {
		return qtVidaRetirada;
	}

	public void setQtVidaRetirada(int qtVidaRetirada) {
		this.qtVidaRetirada = qtVidaRetirada;
	}
}
