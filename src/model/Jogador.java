package model;

public class Jogador extends Entidade{
	private String tipoTiro;
	private int pontos;
	private int pontosDiminuidos;
	private int qtVida;

	public Jogador(int x, int y){
		//coordenada que centralizara o jogador na tela
		setX(x/2-83/2);
		
		//coordenada para deixar o jogador visível,
		//se nao subtrair o jogador ficará em baixo da tela, invisível
		setY(y-88);
		
		setLargura(83);
		setAltura(88);
		setTipoTiro(TipoMaterial.getTipos().get(0));
		
		//define a quantidade de vida do jogador
		qtVida = 100;
	}

	public String getTipoTiro() {
		return tipoTiro;
	}

	public void setTipoTiro(String tipoTiro){
		//pega os tipos de tiros
		for(int i=0; i<TipoMaterial.getTipos().size(); i++){
			//verifica qual foi o tipo de tiro escolhido
			if(tipoTiro.equals(TipoMaterial.getTipos().get(i))){
				//definie o tipo de tiro
				setImg("jogador-"+tipoTiro+".png");
				this.tipoTiro   = tipoTiro;
				
				//para o laco de repeticao
				break;
			}
		}
	}

	public int getPontos() {
		return pontos;
	}

	public void setPontos(int pontos) {
		//atualiza a pontuação do jogador
		if(this.pontos+pontos >= 0){			
			this.pontos += pontos;
		}
		
		//caso os pontos estejam sendo retirados,
		//salva a quantidade de pontos diminuidos
		if(pontos < 0){
			this.pontosDiminuidos += pontos;
		}
	}

	public int getQtVida() {
		return qtVida;
	}

	public void setQtVida(int qtVida) {
		this.qtVida += qtVida;
	}

	public int getPontosDiminuidos() {
		return pontosDiminuidos;
	}

	public void setPontosDiminuidos(int pontosDiminuidos) {
		this.pontosDiminuidos = pontosDiminuidos;
	}
}
