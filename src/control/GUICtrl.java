package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import view.Ajuda;
import view.GUI;
import view.RankPontuacao;
import view.SobreJogo;

public class GUICtrl implements ActionListener {
	//referencia para a janela do jogo
	private GUI obJanela;
	
	public GUICtrl(GUI obGUI){
		this.obJanela = obGUI;
	}
	
	private void exibeRankPontos(){
		//cria a caixa de dialógo para mostrar o rank da pontuação do jogo
		JScrollPane panelRankPontos = new RankPontuacao();
		
		//mostra a caixa de dialógo
		JOptionPane.showOptionDialog(null, 
				panelRankPontos, 
		        "Top 100 jogadores", 
		        JOptionPane.NO_OPTION, 
		        JOptionPane.PLAIN_MESSAGE, 
		        null, 
		        new String[]{},
		        "default");
		
		//despausa o jogo
		obJanela.getPainelJogo().getJogoCtrl().pausaJogo();
	}
	
	private void exibeSobreJogo(){
		//cria a caixa de dialógo para mostrar a tela de sobre jogo
		JPanel panelSobreJogo = new SobreJogo();
		
		//mostra a caixa de dialógo
		JOptionPane.showOptionDialog(null, 
				panelSobreJogo, 
		        "Sobre", 
		        JOptionPane.NO_OPTION, 
		        JOptionPane.PLAIN_MESSAGE, 
		        null, 
		        new String[]{},
		        "default");
		
		//despausa o jogo
		obJanela.getPainelJogo().getJogoCtrl().pausaJogo();
	}
	
	private void exibeAjuda(){
		//cria a caixa de dialógo para mostrar a tela de sobre jogo
		JPanel panelExibeAjuda = new Ajuda();
		
		//mostra a caixa de dialógo
		JOptionPane.showOptionDialog(null, 
				panelExibeAjuda, 
		        "Ajuda", 
		        JOptionPane.NO_OPTION, 
		        JOptionPane.PLAIN_MESSAGE, 
		        null, 
		        new String[]{},
		        "default");
		
		//despausa o jogo
		obJanela.getPainelJogo().getJogoCtrl().pausaJogo();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == obJanela.getjMenuSair()){
			//fecha a janela
			obJanela.dispose();
			obJanela.getPainelJogo().desativaJogo();
			obJanela.getPainelJogo().finalizar();
			System.exit(0);
		}
		else if(e.getSource() == obJanela.getjMenuNovoJogo()){
			//inicia um novo jogo
			obJanela.getPainelJogo().getJogoCtrl().novoJogo();
		}
		else if(e.getSource() == obJanela.getjMenuRecordes()){
			//verifica se o jogo esta pausado
			if(!obJanela.getPainelJogo().isPause()){
				//se não estiver, pausa o jogo
				obJanela.getPainelJogo().getJogoCtrl().pausaJogo();
			}
			
			//inicia o rank de pontuação do jogo
			exibeRankPontos();
		}
		else if(e.getSource() == obJanela.getjMenuSobreJogo()){
			//verifica se o jogo esta pausado
			if(!obJanela.getPainelJogo().isPause()){
				//se não estiver, pausa o jogo
				obJanela.getPainelJogo().getJogoCtrl().pausaJogo();
			}
			
			//exibe a tela de Sobre o Jogo
			exibeSobreJogo();
		}
		else if(e.getSource() == obJanela.getjMenuExibirAjuda()){
			//verifica se o jogo esta pausado
			if(!obJanela.getPainelJogo().isPause()){
				//se não estiver, pausa o jogo
				obJanela.getPainelJogo().getJogoCtrl().pausaJogo();
			}
			
			//exibe a tela de Sobre o Jogo
			exibeAjuda();
		}
	}
}
