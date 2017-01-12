package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import dados.DadosPontuacao;
import view.SalvaPontuacao;

public class SalvaPontuacaoCtrl implements KeyListener, MouseListener, WindowListener, ActionListener {
	//referencia para a janela que sera controlada
	private SalvaPontuacao obJanela;
	
	//objeto que manipula os dados das pontuações salvos
	private DadosPontuacao dadosPontuacao;
	
	//lista com os nomes dos jogadore e seus pontos, separados
	private ArrayList<ArrayList<String>> listRankPontos;
	
	//para verificar se é a primeira "teclada" do usuario
	private boolean primeiroInput = true;
	
	
	public SalvaPontuacaoCtrl(SalvaPontuacao obJanela){
		this.obJanela 	= obJanela;
		dadosPontuacao 	= new DadosPontuacao();
	}
	
	public boolean verificaSeSalva(int pontosJogador){
		//ler as pontuações no arquivo de texto
		if(listRankPontos == null){
			//pega a lista com o rank
			listRankPontos	= dadosPontuacao.getListRankPontos();
		}
		
		//inicializa
		String tmpMnPt = null;
		
		//caso exista alguma pontuação no rank
		if(listRankPontos.size() != 0){
			//pega a pontuação do menor jogador do rank
			//a lista está em ordem crescente
			tmpMnPt = listRankPontos.get(0).get(0);
		}
		
		int menorPtRank;
		
		//verifica se existe a menor pontuação
		//e se o valor não está nulo, vazio ou com espaço
		if(tmpMnPt != null && !tmpMnPt.isEmpty() && !tmpMnPt.equals("") && !tmpMnPt.equals(" ") && tmpMnPt.length() >= 1){
			menorPtRank = Integer.parseInt(listRankPontos.get(0).get(0));
		}
		else{
			menorPtRank = 0;
		}
		
		//verifica se a pontuação do jogador atual é maior do que a do 100º
		if(listRankPontos.size() >= 100 && pontosJogador <= menorPtRank){
			//se ele não estiver no top100
			//exibe a informação que ele está fora do top100
			obJanela.mostrarMensagem("Você ficou fora do Top-100 :(", "Jogue mais uma vez");
			
			return false;
		}
		else{
			return true;
		}
	}
	
	private void salvaPontuacao(){
		//verifica se o usuario digitou o seu nome
		if(!obJanela.getTxtNome().getText().equals("") && !primeiroInput){			
			//cria o texto que sera gravado no arquivo
			String dado = obJanela.getTxtNome().getText()+"¬¬"+obJanela.getPontosJogador();
			
			//salva a nova pontuação no arquivo de texto
			dadosPontuacao.substitueMenorPonto(dado);
			
			//indica que os pontos foram salvos
			obJanela.setPontosSalvos(true);
			
			//fecha a janela de salvamento de pontuação
			JOptionPane.getRootFrame().dispose();
		}
		else{
			obJanela.mostrarMensagem("Digite o seu nome.","");
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		//remove o texto de informação quando o usuário digitar a tecla de BackSpace
		if(e.getSource() == obJanela.getTxtNome() && e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
			//verifica se é a primeira tecla teclada
			if(primeiroInput){
				obJanela.getTxtNome().setText("");;
				
				primeiroInput = false; 
			}
		}
		
		//salva a pontuação do usuário
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			//verifica se é a primeira tecla teclada
			if(!primeiroInput){
				salvaPontuacao();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//remove o texto de informação quando o usuário digitar alguma letra
		if(e.getSource() == obJanela.getTxtNome()){
			//verifica se é a primeira tecla teclada
			if(primeiroInput){
				obJanela.getTxtNome().setText("");;
				
				primeiroInput = false; 
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//remove o texto de informação quando o usuário digitar alguma letra
		if(e.getButton() == MouseEvent.BUTTON1 && e.getSource() == obJanela.getTxtNome()){
			//verifica se é o primeiro click do mouse
			if(primeiroInput){
				obJanela.getTxtNome().setText("");;
				
				primeiroInput = false; 
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
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override	
	public void windowOpened( WindowEvent e ){
        obJanela.getTxtNome().requestFocus();
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == obJanela.getBtnSalvar()){
			salvaPontuacao();
		}
	}
}
