package control;

import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;

import view.Jogo;

public class InputCtrl implements MouseMotionListener, MouseListener, KeyListener {
	//map que irá guardar os eventos das teclas pressionadas
	private HashMap<Integer, Boolean> teclaPressionada;
	
	//referencia para a janela do jogo
	private Jogo janelaJogo;
	
	//referencia para o controlador do jogo
	private JogoCtrl jogoCtrl;
	
	public InputCtrl(Jogo janelaJogo, JogoCtrl jogoCtrl){
		//inicializa o map que guardar os eventos das teclas
		teclaPressionada 	= new HashMap<Integer, Boolean>();
		
		//referencia para a janela do jogo
		this.janelaJogo 	= janelaJogo;
		
		//referencia para o controlador do jogo
		this.jogoCtrl 		= jogoCtrl;
	}
	
	public HashMap<Integer, Boolean> getTeclaPressionada() {
		return teclaPressionada;
	}
	
	//metodos da classe KeyListener que devem ser implementados
	@Override
	public void keyPressed(KeyEvent tecla) {
		//adiciona a tecla pressionada ao map
		teclaPressionada.put(tecla.getKeyCode(), true);
	}
	@Override
	public void keyReleased(KeyEvent tecla) {
		//remove a tecla "liberada" do map
		teclaPressionada.remove(tecla.getKeyCode());
	}
	@Override
	public void keyTyped(KeyEvent tecla) {		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//se o jogo estiver finalizado
		if(janelaJogo.isGameOver() || janelaJogo.isJogoGanho()){
			//verifica se clicou no botão de "novo jogo"
			if((e.getButton() == 1) && janelaJogo.getFrmRtgNovoJogo().contains(e.getX(), e.getY())){				
				//inicia um novo jogo
				jogoCtrl.novoJogo();
			}
			
			//verifica se clicou no botão de "novo jogo"
			if((e.getButton() == 1) && janelaJogo.getFrmRtgSalvarPontos().contains(e.getX(), e.getY())){				
				//abre a tela de novo jogo
				//new SalvaPontuacao(janelaJogo.getObJogador().getPontos());
				janelaJogo.salvarPontuacao();
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e){
		//se o jogo estiver finalizado
		if(janelaJogo.isGameOver() || janelaJogo.isJogoGanho()){
			//verifica o botão de "novo jogo"
			if(janelaJogo.getFrmRtgNovoJogo().contains(e.getX(), e.getY())){
				//muda o ponteiro do cursor para o ponteiro de click
				janelaJogo.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			//verifica o botão de "salvar pontuação"
			else if(janelaJogo.getFrmRtgSalvarPontos().contains(e.getX(), e.getY())){
				//muda o ponteiro do cursor para o ponteiro de click
				janelaJogo.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			else{
				//muda o ponteiro para o padrão
				janelaJogo.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		}
	}
}
