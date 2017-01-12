package view;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import control.GUICtrl;

public class GUI extends JFrame {
	private int larguraJanela 	= 800;
	private int alturaJanela	= 600;
	private String tituloJogo	= "TrashAttack";
	
	//controlador do jogo
	private GUICtrl guiCtrl;
	
	//objeto do jogo
	private Jogo painelJogo;
	
	//barra de menu
	private JMenuBar barraMenu;	
	private JMenuBar jMenuBar;
	private JMenu jMenuPontuacao;
	private JMenu jMenuAjuda;
	private JMenu jMenuJogo;
	private JMenuItem jMenuExibirAjuda;
	private JMenuItem jMenuSobreJogo;
	private JMenuItem jMenuRecordes;
	private JMenuItem jMenuNovoJogo;
	private JMenuItem jMenuSair;
	
	public GUI(){
		//inicia o controlador da janela
		guiCtrl = new GUICtrl(this);
				
		//define as configuracoes da janela		
		setTitle(tituloJogo);	
		setSize(larguraJanela,alturaJanela);
		
		//centraliza o jogo na tela
		setLocationRelativeTo(null);
		
		//impede o usuario de redimensionar a tela
		setResizable(false);
		
		//define a operacao que executara quando o usuario fechar a tela
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//cria a barra de menus
		barraMenu();
		
		//adiciona a barra de menus
		setJMenuBar(jMenuBar);
				
		//adiciona o jogo na janela	
		painelJogo = new Jogo(this);
		add(painelJogo);
		
		//redimensiona a janela com base nos componentes dela, ou seja, o menu e o jogo
		pack();
		
		//exibe a janela do jogo
		setVisible(true);
		
		//inicia o jogo
		painelJogo.run();
	}
	
	private void barraMenu(){		
		jMenuBar 		= new JMenuBar();
		jMenuJogo 		= new JMenu();
		jMenuPontuacao 	= new JMenu();
		jMenuAjuda 		= new JMenu();
		jMenuSobreJogo 	= new JMenuItem();
		jMenuExibirAjuda= new JMenuItem();
		jMenuRecordes 	= new JMenuItem();
		jMenuNovoJogo 	= new JMenuItem();
		jMenuSair 		= new JMenuItem();		
		
		jMenuBar.add(jMenuJogo);
		jMenuBar.add(jMenuPontuacao);
		jMenuBar.add(jMenuAjuda);
		
		jMenuJogo.setText("Jogo");
		jMenuJogo.add(jMenuNovoJogo);
		jMenuJogo.add(jMenuSair);
		
		jMenuPontuacao.setText("Pontuação");
		jMenuPontuacao.add(jMenuRecordes);
		
		jMenuAjuda.setText("Ajuda");
		jMenuAjuda.add(jMenuExibirAjuda);
		jMenuAjuda.add(jMenuSobreJogo);
		
		
		jMenuSobreJogo.setText("Sobre o Jogo");
		jMenuSobreJogo.addActionListener(guiCtrl);
		
		jMenuExibirAjuda.setText("Exibir Ajuda");
		jMenuExibirAjuda.addActionListener(guiCtrl);
		
		jMenuSair.setText("Sair");
		jMenuSair.addActionListener(guiCtrl);		
		
		jMenuNovoJogo.setText("Novo Jogo");
		jMenuNovoJogo.addActionListener(guiCtrl);		
		
		jMenuRecordes.setText("Ranking   ");
		jMenuRecordes.addActionListener(guiCtrl);			
		
		//adiciona a fonte especificado ao jogo				
		jMenuBar.setFont(Fonte.getFonteTexto());
	}

	public int getBarraMenuAltura(){
		return barraMenu.getHeight();
	}
	
	public int getAlturaJanela(){
		return alturaJanela;
	}

	public int getLarguraJanela() {
		return larguraJanela;
	}

	public void setLarguraJanela(int larguraJanela) {
		this.larguraJanela = larguraJanela;
	}

	public JMenuBar getjMenuBar() {
		return jMenuBar;
	}

	public JMenu getjMenuPontuacao() {
		return jMenuPontuacao;
	}

	public JMenu getjMenuAjuda() {
		return jMenuAjuda;
	}

	public JMenu getjMenuJogo() {
		return jMenuJogo;
	}

	public JMenuItem getjMenuExibirAjuda() {
		return jMenuExibirAjuda;
	}

	public JMenuItem getjMenuSobreJogo() {
		return jMenuSobreJogo;
	}

	public JMenuItem getjMenuRecordes() {
		return jMenuRecordes;
	}

	public JMenuItem getjMenuNovoJogo() {
		return jMenuNovoJogo;
	}

	public JMenuItem getjMenuSair() {
		return jMenuSair;
	}

	public Jogo getPainelJogo() {
		return painelJogo;
	}
}
