package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import control.InputCtrl;
import control.JogoCtrl;
import control.RodarJogoCtrl;
import model.Jogador;
import model.Lixo;
import model.TipoMaterial;
import model.Tiro;

//extende da classe WindowListener, para escutar os eventos da janela, como o click no botão de fechar
public class Jogo extends JPanel {	
	//referencia para a janela do jogo
	private JFrame janelaJogo;
	
	//se o jogo esta ativo ou nao
	private boolean ativo;
	
	//se o jogo esta pausado ou nao
	private boolean pause;
	
	//se o jogo estiver finalizado
	private boolean gameOver;
	
	//se o jogador ganhou o jogo
	private boolean jogoGanho;
	
	//se o jogo passou para a próxima fase
	private boolean novaFase;
	
	//indica se o jogador já salvou seus pontos
	private boolean pontosSalvos;
	
	//objeto responsavel por controlar o loop do jogo
	//e acionar o controlador da janela, JogoCtrl
	private RodarJogoCtrl rodarJogoCtrl;
	
	//referencia para o controlador da janela do jogo
	private JogoCtrl jogoCtrl;
	
	//objeto que vai gerencia o input do teclado e mouse
	private InputCtrl inputCtrl;
	
	//imagem de fundo do jogo
	private String imgFundoJogo = "cenario.jpg";
	
	//guarda os objetos do jogo
	private Jogador obJogador;
	private ArrayList<Lixo> obInimigos;
	private ArrayList<Tiro> obTiros;
	
	//objetos para guardar as imagens do jogo
	private HashMap<String, BufferedImage> imgFundoTela;
	private HashMap<String, BufferedImage> imgJogador;
	private HashMap<String, BufferedImage> imgInimigos;
	private HashMap<String, BufferedImage> imgTiros;
	
	//objetos da tela
	private Rectangle frmRtgNovoJogo;
	private Rectangle frmRtgSalvarPontos;
	
	//tempo inicial de quando exibiu uma mensagem na tela
	//por exemplo, nova fase
	private double tempoInicioMsgJogo = 0;
	
	//controles da exibição de diminuição da pontuação do jogaodr
	private boolean exibeMenosPontos;
	private double tempoInicioHUDJogo = 0;

	public Jogo(JFrame janelaJogo){
		//guarda a referencia para a janela do jogo
		this.janelaJogo = janelaJogo;
		
		//define a altura do painel do jogo
		//precisa dos dois sets senão não redimensiona a janela corretamente
		Dimension tamanhoPainel = new Dimension(janelaJogo.getWidth(), janelaJogo.getHeight()-janelaJogo.getJMenuBar().getHeight()-27);
		setSize(tamanhoPainel);
		setPreferredSize(tamanhoPainel);

		//configuração do JPanel
		setFocusable(true);
		
		//para acessar o video, atraves do buffer
		//cria o buffer strategy com 2 buffers
		setDoubleBuffered(true);
		
		//indica que o programa do jogo vai desenhar os objetos, 
		//ignorando o evento de desenho do sistema
		setIgnoreRepaint(true);		
		
		//inicia o controlador do jogo
		jogoCtrl 	= new JogoCtrl(this);
		
		//define que esta classe escutará os eventos do teclado e mouse
		inputCtrl 	= new InputCtrl(this, jogoCtrl);
		addKeyListener(inputCtrl);		
		addMouseListener(inputCtrl);
		addMouseMotionListener(inputCtrl);
		
		//adiciona o controlador de input ao controlador do jogo
		jogoCtrl.setInputCtrl(inputCtrl);
				
		//inicia um novo jogo
		novoJogo();

		//inicializa as configuracoes do jogo
		carregaJogo();
	}
	
	public void run(){
		//inicia o loop do jogo
		rodarJogoCtrl = new RodarJogoCtrl(this, jogoCtrl);
		rodarJogoCtrl.iniciarLoop();
	}
	
	public void novoJogo(){

		//instancia as entidades do jogo
		obJogador 	= new Jogador(getLargura(), getAltura());
		obInimigos 	= new ArrayList<>();
		obTiros 	= new ArrayList<>();
		
		/* TEM QUE ESTAR POR ÚLTIMO! SENAO DA BUG NO LOOP! */
		//informa que iniciou o jogo
		ativo 			= true;
		pause 			= false;
		gameOver		= false;
		jogoGanho		= false;
		novaFase		= false;
		pontosSalvos	= false;
		exibeMenosPontos= false;
	}
	
	public void novaFase(){
		//reseta as configurações da fase
		obInimigos 	= new ArrayList<>();
		obTiros 	= new ArrayList<>();
	}
	
	private void carregaJogo(){
		//objeto para guardar as imagens do fundo da tela
		imgFundoTela 	= new HashMap<String, BufferedImage>();
		
		//carrega os fundos de todas as fases
		for(int i=1; i<=jogoCtrl.getFaseFinalJogo(); i++){
			//a key será a fase do jogo
			imgFundoTela.put(Integer.toString(i), carregaImg("cenario/cenario-"+i+".png"));
		}
				
		//pega os tipos de materiais
		ArrayList<String> tiposMateriais = TipoMaterial.getTipos();
		
		//objeto para guardar as imagens do jogador
		imgJogador 	= new HashMap<String, BufferedImage>();
		Jogador j;
		
		//objeto para guardar as imagens dos tipos de tiro
		imgTiros 	= new HashMap<String, BufferedImage>();
		Tiro t;
		
		//objeto para guardar as imagens dos tipos de inimigos
		imgInimigos = new HashMap<String, BufferedImage>();
		Lixo l;
		
		for(int i=0; i < tiposMateriais.size(); i++){
			//cria um jogador com uma lixeira do tipo de material selecionado
			j = new Jogador(getLargura(), getAltura());
			j.setTipoTiro(tiposMateriais.get(i));
			
			//carrega a imagem do tipo de lixeira selecionada
			imgJogador.put(j.getImg(), carregaImg(j.getImg()));
			
			
			//cria um tiro do tipo de material selecionado
			t = new Tiro(0,0, tiposMateriais.get(i));
			
			//carrega a imagem do tipo de tiro selecionado
			imgTiros.put(t.getImg(), carregaImg(t.getImg()));
			
			
			//cria um inimigo do tipo de material selecionado
			l = new Lixo(tiposMateriais.get(i));
			
			//carrega a imagem do tipo de inimigo selecionado
			imgInimigos.put(l.getImg(), carregaImg(l.getImg()));
		}
	}
	
	private BufferedImage carregaImg(String img){
		//Pega as imagens que serao utilizadas no jogo
		URL urlImg = Jogo.class.getResource("/recursos/"+img);
		
        try{
        	//url da imagem do jogador	        
	        if(urlImg == null){
	            throw new RuntimeException("A imagem "+img+" não foi encontrada.");
	        }
	        else{
	        	//pega a imagem do jogador e guarda no buffer
	            return ImageIO.read(urlImg);
	        }
        } 
        catch (IOException e) {
        	throw new RuntimeException("Nao foi possível ler a imagem:"+img+" -- "+e);
        }
	}
		
	public void paint(Graphics g) {
		//a cada rodada precisamos de uma "tela" para desenhar os objetos
		//para isto é obtido o um graphics do bufferStrategy
        Graphics2D tela = (Graphics2D) g;
        
        //define a cor que deverá preencher a janela
        tela.setColor(Color.black);
        //pinta a tela do jogo. Para isto desenha um retangulo do tamanho da janela
        tela.fillRect(0, 0, getLargura(), getAltura());

        //desenha o plano de fundo do jogo
        
        //se estiver em uma fase após a fase final        
        if(jogoCtrl.getFaseJogo() > jogoCtrl.getFaseFinalJogo()){
        	//mostra o desenho da fase anterior
        	tela.drawImage(imgFundoTela.get(Integer.toString(jogoCtrl.getFaseJogo()-1)), 0, 0, null);
        }
        else{
        	//senão, mostra a imagem da fase atual
        	tela.drawImage(imgFundoTela.get(Integer.toString(jogoCtrl.getFaseJogo())), 0, 0, null);
        }
                
        /******* desenha o jogo *******/
        
        desenhaEntidade(tela);
        desenhaHUD(tela);
        desenhaMensagens(tela);
        
        /******************************/
        
        //libera o objeto graphics
        tela.dispose();
	}
	
	private void desenhaEntidade(Graphics2D tela){
		//desenha o jogador
		tela.drawImage(imgJogador.get(obJogador.getImg()), obJogador.getX(), obJogador.getY(), null);

		//desenha os inimigos
		Lixo ini;
		
		for(int j=0; j < obInimigos.size(); j++){
			ini = obInimigos.get(j);
			tela.drawImage(imgInimigos.get(ini.getImg()), ini.getX(), ini.getY(), null);
		}
		
		//desenha os misseis atirados
		Tiro tiro;
		
		for(int i=0; i < obTiros.size(); i++){
			//pega o tiro atual
			tiro = obTiros.get(i);
			
			tela.drawImage(imgTiros.get(tiro.getImg()), tiro.getX(), tiro.getY(), null);
		}		
	}
	
	private void desenhaHUD(Graphics2D tela){
		//define a cor que deverá preencher a janela, co transparência
        tela.setColor(new Color(0, 0, 0, .50f));
        
        //pinta a tela do jogo. Para isto desenha um retangulo do tamanho da janela
        tela.fillRoundRect(0, 0, 255, 80, 5, 5);
        
		//define a qualidade da fonte
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    rh.put(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);

	    tela.setRenderingHints(rh);
	    
	    //cor da fonte do texto
		tela.setColor(Color.WHITE);
		
		//define a fonte do texto
		tela.setFont(Fonte.getFonteTexto().deriveFont(Font.BOLD, 16));
		
		//escreve o texto
		String txt = "Pontos: "+obJogador.getPontos();
		tela.drawString(txt, 15, 25);
		
		//verifica se o jogador perdeu pontos
		if(exibeMenosPontos){
			//verifica se ainda não mostrou a mensagem
			if(tempoInicioHUDJogo == 0){
				//pega o tempo em segundos
				tempoInicioHUDJogo = (double)System.nanoTime()/1000000000.0;
			}
			
			//tempo que a mensagem ficou exibida na tela
			double tempoHUDExibida = (double)System.nanoTime()/1000000000.0 - tempoInicioHUDJogo;
			
			if(tempoHUDExibida < 2){
				//mostra a quantidade de pontos diminuídos do jogador
				tela.setColor(Color.RED);
				
				//pega somente a quantidade em valores positivos(Math.abs)
				tela.drawString("-"+Math.abs(obJogador.getPontosDiminuidos()), tela.getFontMetrics().stringWidth(txt)+30, 25);	
			}
			else{
				//reseta o tempo inicial
				tempoInicioHUDJogo = 0;
				
				//indica para não exibir a diminuição da pontuação novamente
				exibeMenosPontos = false;				
				
				//indica que diminuiu a pontuação do jogador
				jogoCtrl.resetarPontosDiminuidosJogador();
			}
		}
		
		tela.setColor(Color.WHITE);
		tela.drawString("Nível: "+jogoCtrl.getFaseJogo(), 15, 45);	
		tela.drawString("Capacidade do bueiro: ", 15, 65);
		
		//cor da quantidade de vida
		if(obJogador.getQtVida() <= 50){
			tela.setColor(Color.RED);
		}
		else{
			tela.setColor(Color.GREEN);
		}
		
		tela.drawString(+obJogador.getQtVida()+"%", 200, 65);
	}
	
	private void desenhaMensagens(Graphics2D tela){
		//escurece o fundo da tela
		if(gameOver || novaFase || pause || jogoGanho){
			//define a cor que deverá preencher a janela, co transparência
	        tela.setColor(new Color(0, 0, 0, .80f));
	        
	        //pinta a tela do jogo. Para isto desenha um retangulo do tamanho da janela
	        tela.fillRect(0, 0, getLargura(), getAltura());
		}
		
		//mostra a mensagem de jogo ganho
		//mostra a mensagem de game over
		if(gameOver || jogoGanho){
			//define a fonte do texto
			tela.setColor(Color.WHITE);
			tela.setFont(Fonte.getFonteTexto().deriveFont(Font.BOLD, 30));
			
			//define se ganhou ou perdeu o jogo
			if(gameOver){
				tela.drawString("Você perdeu ;(", getLargura()/2-120, getAltura()/2-30-40);
			}
			else if(jogoGanho){
				tela.drawString("Você ganhou o jogo! o/", getLargura()/2-160, getAltura()/2-30-40);
			}
			
			
			tela.drawString("Sua pontuação: "+obJogador.getPontos(), getLargura()/2-160, getAltura()/2-16);
			
			//desenha o fundo do botão "novo jogo"
	        tela.setColor(new Color(255, 255, 255, 255));
	        //pinta a tela do jogo. Para isto desenha um retangulo do tamanho da janela
	        tela.fillRoundRect(getLargura()/2-205, getAltura()/2+8+16, 200, 50, 5, 5);
	        
	        //escreve o texto do botão
	        tela.setColor(Color.BLACK);
			tela.setFont(Fonte.getFonteTexto().deriveFont(Font.BOLD, 30));
			
			String txt = "Novo jogo";
			tela.drawString(txt, (getLargura()/2-207)+(200/2)-(tela.getFontMetrics().stringWidth(txt)/2), getAltura()/2+60);
			
			//forma do retangulo do botao
			frmRtgNovoJogo = new Rectangle(getLargura()/2-205, getAltura()/2+8+16, 200, 50);
			
			
			//desenha o fundo do botão "salvar pontuação"
	        tela.setColor(new Color(255, 255, 255, 255));
	        //pinta a tela do jogo. Para isto desenha um retangulo do tamanho da janela
	        tela.fillRoundRect(getLargura()/2+05, getAltura()/2+8+16, 230, 50, 5, 5);
	        
	        //verifica se o jogador já salvou seus pontos
	        if(!pontosSalvos){	        
	        	//escreve o texto do botão
	        	tela.setColor(Color.BLACK);
	        	
	        	//forma do retangulo do botao
				frmRtgSalvarPontos = new Rectangle(getLargura()/2+05, getAltura()/2+8+16, 230, 50);
	        }
	        else{
	        	//desabilita o botão
	        	//caso o jogador já tenha salvo seus pontos
	        	tela.setColor(Color.GRAY);
	        	frmRtgSalvarPontos = new Rectangle(-10, -10, 0, 0);
	        }
	        
	        //define a fonte do texto
			tela.setFont(Fonte.getFonteTexto().deriveFont(Font.BOLD, 30));
			
			//escreve o texto na tela
			txt = "Salvar pontos";
			tela.drawString(txt, (getLargura()/2+05)+(230/2)-(tela.getFontMetrics().stringWidth(txt)/2), getAltura()/2+60);
			
		}
		//mostra a mensagem de nova fase
		else if(novaFase){
			//verifica se ainda não mostrou a mensagem
			if(tempoInicioMsgJogo == 0){
				//pega o tempo em segundos
				tempoInicioMsgJogo = (double)System.nanoTime()/1000000000.0;
			}
			
			//tempo que a mensagem ficou exibida na tela
			double tempoMsgExibida = (double)System.nanoTime()/1000000000.0 - tempoInicioMsgJogo;
			
			if(tempoMsgExibida < 2){			
				//define a fonte do texto		        
				tela.setColor(Color.WHITE);
				tela.setFont(Fonte.getFonteTexto().deriveFont(Font.BOLD, 30));
				
				String txt = "Proxímo nível!";
				
				//exibe o texto centralizado na tela			
				tela.drawString(txt, getLargura()/2-(tela.getFontMetrics().stringWidth(txt))/2, getAltura()/2-21);
			}
			else{
				//reseta o tempo inicial
				tempoInicioMsgJogo = 0;
				
				//indica que já iniciou uma nova fase
				novaFase	= false;
				
				//remove a pausa do jogo
				pause 		= false;
			}
		}
		//mostra a mensagem de jogo pausado
		else if(pause){
			//define a fonte do texto	        
			tela.setColor(Color.WHITE);
			tela.setFont(Fonte.getFonteTexto().deriveFont(Font.BOLD, 30));
			
			String txt = "pausado";
			
			//exibe o texto centralizado na tela			
			tela.drawString(txt, getLargura()/2-(tela.getFontMetrics().stringWidth(txt))/2, getAltura()/2-21);
		}
	}
	
	public void salvarPontuacao(){		
		//cria a caixa de dialógo para o usuário digitar o seu nome
		SalvaPontuacao salvaPontos 	= new SalvaPontuacao();
		
		//verifica se o jogador esta no top-100
		if(salvaPontos.verificaSeSalva(obJogador.getPontos())){
			//cria o painel de salvamento de pontos
			JPanel panelSalvaPontos 	= salvaPontos.painel(obJogador.getPontos());
			
			//mostra a caixa de dialógo
			JOptionPane.showOptionDialog(null, 
					panelSalvaPontos, 
			        "Salvar pontuação", 
			        JOptionPane.NO_OPTION, 
			        JOptionPane.PLAIN_MESSAGE, 
			        null, 
			        new String[]{},
			        "default");
			
			//indica que os pontos foram salvo ou não
			pontosSalvos = salvaPontos.isPontosSalvos();
		}
		else{
			//desabilita o botão de salvamento de pontos
			pontosSalvos = true;
		}
	}

	public void finalizar(){
        //fecha a janela do jogo
        janelaJogo.dispose();
	}	
	
	public void desativaJogo(){
		//indica que o jogo deve terminar
		ativo = false;
	}
	
	
	
	//gets e sets
	public int getLargura() {
        return this.getWidth();
    }

    public int getAltura() {
    	return this.getHeight();
    }
    
	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}	

	public boolean isPause() {
		return pause;
	}

	public void setPause(boolean pause) {
		this.pause = pause;
	}
	
	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
	
	public ArrayList<Lixo> getObInimigos() {
		return obInimigos;
	}

	public ArrayList<Tiro> getObTiros() {
		return obTiros;
	}
	
	public Jogador getObJogador() {
		return obJogador;
	}

	public Rectangle getFrmRtgNovoJogo() {
		return frmRtgNovoJogo;
	}

	public Rectangle getFrmRtgSalvarPontos() {
		return frmRtgSalvarPontos;
	}

	public JogoCtrl getJogoCtrl() {
		return jogoCtrl;
	}

	public boolean isNovaFase() {
		return novaFase;
	}

	public void setNovaFase(boolean novaFase) {
		this.novaFase = novaFase;
	}

	public boolean isJogoGanho() {
		return jogoGanho;
	}

	public void setJogoGanho(boolean jogoGanho) {
		this.jogoGanho = jogoGanho;
	}

	public boolean isExibeMenosPontos() {
		return exibeMenosPontos;
	}

	public void setExibeMenosPontos(boolean exibeMenosPontos) {
		this.exibeMenosPontos = exibeMenosPontos;
	}
}
