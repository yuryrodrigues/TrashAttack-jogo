package control;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import model.Jogador;
import model.Lixo;
import model.TipoMaterial;
import model.Tiro;
import view.Jogo;

public class JogoCtrl {
	private Jogo obJogo;
	private InputCtrl inputCtrl;
	
	//quantidade de fileiras de inimigos por fase
	private int qtFilaInimigoFase 	= 20;
	//quantidade maxima e mínima de inimigos em uma fila
	private int qtInimigoFila		= 1;
	private int qtInimigoFilaMin	= 1;
	//velocidade inimigos
	//quanto maior, mais lentos
	private int velocidadeInimigo 	= 5;
	//espaco entre uma fileira de inimigo e outra, em pixel
	private int espacoVertFilaIni 	= 130;
	
	//contador para deixar um espaço de pixel entre uma fila de inimigo e outra
	private int contePixelLimpo 	= espacoVertFilaIni;
	
	//quantidade de inimigos ja adicionados
	private int qtFilaInimigoAdd	= 0;
		
	//contador da marcha dos inimigos
	//conta quantos pixel o jogo andou
	private int contVelInimigo 	= 0;	
	
	//coordenada y da fila de inimigos
	private int yFilaInimigo 	= 0;
	
	//em qual fase o jogo está
	private int faseJogo		= 1;
	
	//numero da ultima fase do jogo
	private int faseFinalJogo	= 5;
	
	//quantidade maxima de inimigos na fileira
	private int qtInimigoFilaMax;
	
	//espaço horizontal entre os inimigos
	private int espacoEntreInim = 5;
	
	//qt de fila adicionada, para intercalar, uma fila maior outr menor
	private int qtFilaAdd;
	
	
	//coordenadas x possiveis para os inimigos
	private ArrayList<Integer> coordenadaInimigo = new ArrayList<Integer>();
	
	public JogoCtrl(Jogo jogo){
		//salva a referencia para a janela do jogo
		obJogo = jogo;
		
		//defien a quantidade máxima de inimigos por fileira
		geraPosicaoInimigo();
	}
	
	public void novoJogo(){	
		//reseta as configuraçõe das fases
		resetaConfigFase();

		//reseta as configurações
		resetaConfigJogo();
		
		/* TEM QUE ESTAR POR ÚLTIMO! SENAO DA BUG NO LOOP! */
		//indica para a janela do jogo que vai iniciar um novo jogo
		obJogo.novoJogo();
	}
	
	private void novaFaseJogo(){		
		//verifica se o num da fase atual é maior do que o qt de inimigos max por fileira
		//se for maior, permance a configuração da fase anterior
		if(faseJogo <= qtInimigoFilaMax){
			//aumenta a quantidade de inimigos for fileira
			//de acordo com a fase atual, 2º fase = 2 inimigos; 3º fase = 3 inimigos;
			qtInimigoFila 		= faseJogo;		
			qtInimigoFilaMin 	= faseJogo;
		}
		
		//diminui a velocidade do jogo
		//quanto maior a quantidade de inimigos, menor a velocidade deles
		if(faseJogo == 3){
			velocidadeInimigo += 1.5;
		}
		else if(faseJogo == 4){
			velocidadeInimigo += 1.0;
		}
		else if(faseJogo == 5){
			velocidadeInimigo += 0.5;
		}
		
		//reseta as configuraçõe das fases
		resetaConfigFase();
		
		//inicia uma nova fase
		obJogo.novaFase();
		
		//indica que iniciou uma nova fase
		obJogo.setNovaFase(true);
		
		//pausa o jogo para exibir a mensagem
		obJogo.setPause(true);
	}
	
	private void resetaConfigFase(){
		//contador para deixar um espaço de pixel entre uma fila de inimigo e outra
		contePixelLimpo = espacoVertFilaIni;
		
		//quantidade de inimigos ja adicionados, na fase
		qtFilaInimigoAdd= 0;
			
		//contador da marcha dos inimigos
		//conta quantos pixel os inimigos andaram
		contVelInimigo 	= 0;	
	
		//coordenada y da fila de inimigos
		yFilaInimigo 	= 0;
	}
	
	private void resetaConfigJogo(){
		//quantidade maxima e mínima de inimigos em uma fila
		qtInimigoFila		= 1;
		qtInimigoFilaMin	= 1;
		
		//velocidade inimigos
		velocidadeInimigo 	= 5;
		
		//em qual fase o jogo está
		faseJogo			= 1;
	}
	
	public void setInputCtrl(InputCtrl inputCtrl){
		//referencia para o objeto que gerencia o inputo do mouse e teclado
		this.inputCtrl = inputCtrl;
	}
	
	public void atualizar(){		
		//atualiza as entidades do jogo
		atualizaTiros();		
		
		//move os inimigos na velocidade especificada
		if(contVelInimigo == velocidadeInimigo){
			//atualiza os inimigos
			atualizaInimigos();
			
			//adiciona novos inimigos na tela
			adicionaInimigos();
			
			//reseta o contador de pixel andados do jgo
			contVelInimigo = 0;
		}
		
		//inidica que ja andou um pixel
		contVelInimigo++;
		
		//verifica se houve colisoes
		verificaColisoes();
		
		//verifica se o jogador pressionou alguma tecla
		verificaTeclas();
		
		//verifica se o jogador perdeu o jogo
		verificaGameOver();
		
		//verifica se o jogador matou todos os inimigos
		//e inicia uma nova fase
		verificaNovaFase();
		
		//se o jogador não ganhou o jogo
		verificaJogoGanho();
		
        // Em seguida chamamos yeald na nossa Thread para permitir a outras
        // partes do sistema processarem.
        Thread.yield();
	}
	
	public void verificaTeclas(){
		HashMap<Integer, Boolean> tecla = inputCtrl.getTeclaPressionada();

		//somente movimenta o jogo se estiver despausado
		if(!obJogo.isPause()){
	        if(tecla.get(KeyEvent.VK_LEFT) != null){
	        	atualizaJogador("esq");
	        }
	        if(tecla.get(KeyEvent.VK_RIGHT) != null){
	        	atualizaJogador("dir");
	        }
	        if(tecla.get(KeyEvent.VK_SPACE) != null){
	        	atiraMissel();
	        	
	        	//remove a tecla "liberada" do map
	        	//senão, se o inimigo estiver próximo ao jogador
	        	//o comando de atirar será executado repetidas vezes
	        	//e caso o inimigo seja de tipo diferente, 
	        	//o jogo tentará atirar diversas vezes e nunca conseguirá,
	        	//pois o tirá estará sendo removido logo de seguida!
	    		tecla.remove(KeyEvent.VK_SPACE);
	        }
	        if(tecla.get(KeyEvent.VK_A) != null){
	            atualizaTipoTiro("A");
	        }
	        if(tecla.get(KeyEvent.VK_S) != null){
	            atualizaTipoTiro("S");
	        }
	        if(tecla.get(KeyEvent.VK_D) != null){
	            atualizaTipoTiro("D");
	        }
	        if(tecla.get(KeyEvent.VK_F) != null){
	            atualizaTipoTiro("F");
	        }
	        if(tecla.get(KeyEvent.VK_G) != null){
	            atualizaTipoTiro("G");
	        }  
		}
		
		//pausa por alguns segundos, para não mostrar os objetos "teletransportando" :)
        /*try {
            Thread.sleep(3);
        } catch (InterruptedException ex) {
            throw new RuntimeException("Não foi possível utilizar a Thread para pequenas pausas no jogo.");
        }*/
	}
	
	public void verificaTeclasCtrlJanela(){
		HashMap<Integer, Boolean> tecla = inputCtrl.getTeclaPressionada();
		
		//verifica se pausou/despausou o jogo
		if(tecla.get(KeyEvent.VK_P) != null){
            pausaJogo();
            
            //remove todas as teclas pressionadas
            tecla.clear();
		}
		
		//se escolheu fechar a janela
        if(tecla.get(KeyEvent.VK_ESCAPE) != null){
            obJogo.desativaJogo();
            obJogo.finalizar();
			System.exit(0);
        }
		
		//na tela de game over ou jogo ganho		
		if(obJogo.isGameOver() || obJogo.isJogoGanho()){
			//caso tecle enter
			if(tecla.get(KeyEvent.VK_ENTER) != null){
				//inicia um novo jogo
				novoJogo();
	        }
		}
    }
	
	private int qtMaxInimigoFila(Lixo inimigo){		
		/*//divide a largura da tela pela largura dos inimigos
		//e um espaço de 5pixel entre eles
		qtInimigoFilaMax = obJogo.getLargura() / (inimigo.getLargura()+espacoEntreInim);
		
		//pega o resto da divisao
		//os pixel a mais na tela, onde não se pode colocar o inimigo
		return obJogo.getLargura() % (inimigo.getLargura()+espacoEntreInim);*/
		
		qtInimigoFilaMax = 5;
		
		return obJogo.getLargura() - (inimigo.getLargura()+espacoEntreInim) * qtInimigoFilaMax;
	}
	
	private void geraPosicaoInimigo(){		
		//cria um inimigo
		Lixo inimigo = new Lixo();
				
		//gera a quantidade maxima de inimigos na tela 
		//retorna a qt de pixels que sobrou
		int restoPixel 	= qtMaxInimigoFila(inimigo);
		
		//divide o resto dos pixel entre os inimigos da fila
		int espacoExtra = restoPixel/qtInimigoFilaMax;
		
		//os pixel extras que sobraram no espaço entre os inimigos
		int espacoExtraResto = espacoExtra % qtInimigoFilaMax;
		int j 	= 0;
		int posicao, espacoEsq;

		//gera a posical x dos inimigos na tela
		for(int i=0; i<qtInimigoFilaMax; i++){
			//se sobrou pixel extras dos extras :)
			if(espacoExtraResto > 0){
				espacoExtraResto--;
				
				//adiciona mais um pixel extra ao inimigo
				j = 1;
			}
			else{
				j = 0;
			}
			
			//senão, gera sua posição na fileira
			posicao		= i*(inimigo.getLargura()+espacoEntreInim)+(espacoExtra*i)+j;
			//os inimigos tem que ganhar um espaço extra do lado esquerdo,
			//para não encostarem na borda da tela
			espacoEsq 	= espacoExtra/2;
			coordenadaInimigo.add(posicao+espacoEsq);
		}
	}
	
	public void pausaJogo(){
		//se o jogo estiver pausado
		if(obJogo.isPause()){
			//despausa
			obJogo.setPause(false);
		}
		else{
			//se não estiver, pausa
			obJogo.setPause(true);
		}
	}
	
	private void verificaGameOver(){
		//verifica se as vidas do jogador se esgotaram
		if(obJogo.getObJogador().getQtVida() <= 0){
			//informa que o jogador perdeu o jogo
			obJogo.setGameOver(true);
		}
	}
	
	private void verificaNovaFase(){
		//verifica se adicionou todos os inimigos da fase
		if(qtFilaInimigoAdd == qtFilaInimigoFase){
			//verifica se o jogador já matou todos os inimigos
			if(obJogo.getObInimigos().size() == 0){
				//aumenta o nível da fase
				faseJogo++;
				
				//inicia uma nova fase
				novaFaseJogo();
			}
		}
	}
	
	private void verificaJogoGanho(){
		//verifica se o jogador ganhou o jogo
		if(faseJogo == faseFinalJogo+1 && obJogo.getObInimigos().size() == 0){
			//informa para a view que ganho o jogo
			obJogo.setJogoGanho(true);

			//pausa o jogo para exibir a mensagem
			obJogo.setPause(true);
		}
	}
	
	private void atualizaJogador(String direcao){
		//pega a referencia para o jogador
		Jogador j = obJogo.getObJogador();
		
		//verifica se atingiu a borda da tela
		
		if(j.getX() > 0 && direcao.equals("esq")){			
			j.setX(j.getX()-1);
		}
		
		if(j.getX() < obJogo.getLargura()-j.getLargura() && direcao.equals("dir")){
			j.setX(j.getX()+1);
		}
		
	}
	
	private void atualizaTipoTiro(String sentido){
		//pega os tipos de tiros
		ArrayList<String> tiposTiro = TipoMaterial.getTipos();
		
		switch(sentido){
			case "A":
				obJogo.getObJogador().setTipoTiro(tiposTiro.get(0));
				break;
			case "S":
				obJogo.getObJogador().setTipoTiro(tiposTiro.get(1));
				break;
			case "D":
				obJogo.getObJogador().setTipoTiro(tiposTiro.get(2));
				break;
			case "F":
				obJogo.getObJogador().setTipoTiro(tiposTiro.get(3));
				break;
			case "G":
				obJogo.getObJogador().setTipoTiro(tiposTiro.get(4));
				break;
		}
	}
	
	private void atualizaTiros() {
		//pega a referencia para a lista de tiros
		ArrayList<Tiro> obT = obJogo.getObTiros();
		
		for(int i=0; i < obT.size(); i++){
			//pega o tiro atual
			Tiro t = obT.get(i);	
			
			//verifica se ultrapassou o topo da tela
			if(t.getY() < 0){
				//remove o tiro da tela
				obT.remove(i);
			}
			else{
				//atualiza a posicao do tiro na tela
				t.setY(t.getY()-1);
			}
		}
	}
	
	private void atiraMissel(){
		if(verificaSeAtira()){
			//adiciona um missel a lista de misseis atirados
			obJogo.getObTiros().add(new Tiro(obJogo.getObJogador().getX()+obJogo.getObJogador().getLargura()-Tiro.imgLargura, obJogo.getAltura()-obJogo.getObJogador().getAltura(), obJogo.getObJogador().getTipoTiro()));
		}
	}
	
	private boolean verificaSeAtira(){
		//somente atira se existir espaço antes do missel anterior: --->   ---->   ---->

		//verifica se ja atirou alguma vez
		if(obJogo.getObTiros().size() == 0){
			return true;
		}
		else{
			//pega o ultimo tiro
			Tiro ultimoTiro = (Tiro) obJogo.getObTiros().get(obJogo.getObTiros().size()-1);

			//verifica se existe espaço na tela suficiente para outro tiro
			if(ultimoTiro.getY() < obJogo.getAltura()-ultimoTiro.getAltura()-obJogo.getObJogador().getAltura()){
				return true;
			}
			else{
				return false;
			}
		}
	}
	
	private void adicionaInimigos(){
		//verifica se ja atingiu a quantidade de inimigos da fase
		if(qtFilaInimigoAdd < qtFilaInimigoFase){
			//se e a primeira fila de inimigos, ou
			//se ja deixou um espaco de 10 pixel entre uma fila e outra
			if(contePixelLimpo == espacoVertFilaIni){
				geraFilaInimigos();
				
				//informa que adicionou um inimigo
				qtFilaInimigoAdd++;
				
				//reinicia o contador de espaço entre inimigos
				contePixelLimpo = 0;
			}
			
			contePixelLimpo++;
		}
	}
	
	private void geraFilaInimigos(){
		Random rand;	
		ArrayList coordJaUsada = new ArrayList();
		
		//pega a quantidade de inimigos desta fila horizontal
		//no máximo 10 inimigo e no mínimo 3
		rand = new Random();			
		int qtInimFila = rand.nextInt((qtInimigoFila - qtInimigoFilaMin) + 1) + qtInimigoFilaMin;
		
		//verifica se ja adiciou uma linha anterior,
		//para intercalar, uma fila com maior qt de inimigo e outra menor
		int espacoExtra;
		int j = 0;
		if(qtFilaAdd == 0){			
			//define que o espaço entre elese será o padrão
			espacoExtra = 0;
			
			//indica que ja("vai") adicionar uma fileira
			qtFilaAdd++;
		}
		else{
			//se ja adiciou uma fila antes, remove um inimigo desta
			//quando a fila atual tiver a quantidade limite de inimigos
			//pois estes inimigos ficarão entre os espaços da fila anterior
			//e caso nao faça isto, o inimigo cairá para fora da tela		
			if(qtInimigoFila == qtInimigoFilaMax){
				j = 1;
			}
			
			//indica que os inimigos terão um espaço esta de mais metade do espaço padrão
			//para jogá-los no meio da fileira anterior
			espacoExtra	= coordenadaInimigo.get(1)/2;
			
			//indica que ja("vai") adicionar uma fileira
			qtFilaAdd = 0;
		}
		
		//Adiciona os inimigos na fila		
		for(int i = j; i<qtInimFila; i++){
			//pega um numero aleatorio
			rand 		= new Random();			
			int numAlea = rand.nextInt((TipoMaterial.getTipos().size()-1) + 1) + 0;
			
			//adiciona um inimigo na lista, aleatoriamente
			obJogo.getObInimigos().add(new Lixo(TipoMaterial.getTipos().get(numAlea)));
			
			//gera uma posicao aleatoria na horizontal
			//um inimigo nao pode esta na mesma posicao de outro
			rand = new Random();			
			int xInimigo;
			
			do{
				if(qtFilaAdd == 0){
					//pega a posicao x do inimigo
					xInimigo = rand.nextInt(((coordenadaInimigo.size()-2) - 0) + 1) + 0;
					//-2 indica que o inimigo desta fileira não pode estar na última posição
					//senão ele fica fora da tela,
					//pois esta fileira estará no meio da fileira anterior
				}
				else{
					//pega a posicao x do inimigo
					xInimigo = rand.nextInt(((coordenadaInimigo.size()-1) - 0) + 1) + 0;
				}
			}
			while(coordJaUsada.contains(coordenadaInimigo.get(xInimigo)));
			
			//pega uma posicao X "semi-aleatoria" para o inimigo
			obJogo.getObInimigos().get(obJogo.getObInimigos().size()-1).setX(coordenadaInimigo.get(xInimigo)+espacoExtra);
			
			//adiciona ele na posicao Y, a xx pixel de distancia da fileira abaixo dele
			obJogo.getObInimigos().get(obJogo.getObInimigos().size()-1).setY(yFilaInimigo);
			
			//adiciona a coordenada do inimigo a lista de coordenada ja utilizadas
			coordJaUsada.add(coordenadaInimigo.get(xInimigo));
		}
	}
	
	private void atualizaInimigos(){
		//pega os inimigos
		for(int j=0; j < obJogo.getObInimigos().size(); j++){
			Lixo l = obJogo.getObInimigos().get(j);
			
			//verifica se ja atingiu o final da tela
			//ou seja se ultrapassou o chao
			if(obJogo.getAltura() > l.getY()){
				//atualiza sua posicao
				l.setY(l.getY()+1);
			}
			else{
				//retira a vida do jogador
				obJogo.getObJogador().setQtVida(-l.getQtVidaRetirada());
				
				//diminui a pontuação do jogador
				obJogo.getObJogador().setPontos(-l.getPontos());
				
				//indica que o jogador perdeu pontos
				obJogo.setExibeMenosPontos(true);
				
				//remove o inimigo da tela
				obJogo.getObInimigos().remove(j);				
			}
		}
	}
	
	private void verificaColisoes(){
		Rectangle formaTiro;
		Rectangle formaInimigo;
		Tiro tiro;
		Lixo ini;
		
		//pega os tiros
		for(int i=0; i < obJogo.getObTiros().size(); i++){
			tiro = obJogo.getObTiros().get(i);
			formaTiro = tiro.getForma();
			
			//pega os inimigos
			for(int j=0; j < obJogo.getObInimigos().size(); j++){
				ini = obJogo.getObInimigos().get(j);
				formaInimigo = ini.getForma();
				
				//verifica se se encontra na posicao de algum inimigo
				if(formaTiro.intersects(formaInimigo)){					
					//verifica se o tiro e o inimigo sao do mesmo tipo
					if(tiro.getTipo().equals(ini.getTipo())){
						//aumenta os pontos do jogador
						obJogo.getObJogador().setPontos(ini.getPontos());
						
						//remove o inimigo do jogo
						obJogo.getObInimigos().remove(j);
					}
					else{
						//System.out.println(obJogo.getObJogador().getPontosDiminuidos());
						//diminui a pontuação do jogador
						obJogo.getObJogador().setPontos(-ini.getPontos());
						
						//indica que o jogador perdeu pontos
						obJogo.setExibeMenosPontos(true);
					}
					//remove o o tiro do jogo
					obJogo.getObTiros().remove(i);
					
					//passa para o proximo tiro
					break;
				}
			}
		}			
	}

	public int getFaseJogo() {
		return faseJogo;
	}

	public int getFaseFinalJogo() {
		return faseFinalJogo;
	}

	public void setFaseFinalJogo(int faseFinalJogo) {
		this.faseFinalJogo = faseFinalJogo;
	}
	
	public void resetarPontosDiminuidosJogador(){
		obJogo.getObJogador().setPontosDiminuidos(0);
	}
}
