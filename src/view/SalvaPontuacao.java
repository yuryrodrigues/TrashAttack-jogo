package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import control.SalvaPontuacaoCtrl;

public class SalvaPontuacao extends JPanel {
	//controlador da janela
	private SalvaPontuacaoCtrl salvaPonCtrl;
	
	//pontos do jogador
	private int pontosJogador;
	
	//indica se os pontos foram salvos
	private boolean pontosSalvos;
	
	//fonte utilizada no texto
	private Font fonteTxt;
	
	//elementos do painel
	private JTextField txtNome;
	private JLabel txtPontos;
	private JButton btnSalvar;
	
	public SalvaPontuacao(){
		//controlador da janela
		salvaPonCtrl = new SalvaPontuacaoCtrl(this);
		
		//pega a fonte do jogo
		fonteTxt = Fonte.getFonteTexto();
	}
	
	public JPanel painel(int pontosJogador){
		//guarda os pontos do jogador
		this.pontosJogador = pontosJogador;
		
		//indica que ainda não salvou os pontos
		pontosSalvos = false;
		
		//configurações do painel
		setLayout(null);
		setSize(300,190);
		setPreferredSize(new Dimension(300, 190));		
			
		//adiciona os elementos da janela
		JLabel lblNome = new JLabel();
		lblNome.setFont(fonteTxt.deriveFont(Font.BOLD, 14));
		lblNome.setText("Digite seu nome: ");		
		lblNome.setBounds(15, 0, 200, 30);
		lblNome.setVisible(true);
		lblNome.setEnabled(true);
		
		txtNome = new JTextField();
		txtNome.setFont(fonteTxt.deriveFont(Font.PLAIN, 15));
		//define a quantidade de caracteres maxima que pode ser digitada
		txtNome.setDocument(new JTextFieldLimite(25));
		txtNome.setText("no máximo 25 letras");
		txtNome.setBounds(15, 30, 270, 30);	
		txtNome.setVisible(true);
		txtNome.setEnabled(true);
		//adiciona os controladores dos eventos deste campo de texto
		txtNome.addKeyListener(salvaPonCtrl);
		txtNome.addMouseListener(salvaPonCtrl);
		
		JLabel lblPontos = new JLabel();
		lblPontos.setFont(fonteTxt.deriveFont(Font.BOLD, 14));
		lblPontos.setText("Seus pontos: ");		
		lblPontos.setBounds(15, 70, 200, 30);
		lblPontos.setVisible(true);
		lblPontos.setEnabled(true);
		
		txtPontos = new JLabel();
		txtPontos.setFont(fonteTxt.deriveFont(Font.BOLD, 15));
		txtPontos.setForeground(Color.DARK_GRAY);
		txtPontos.setText(" "+pontosJogador);		
		txtPontos.setBounds(15, 100, 270, 30);
		txtPontos.setBackground(new Color(0, 0, 0, .05f));
		txtPontos.setOpaque(true);
		txtPontos.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
		txtPontos.setVisible(true);
		txtPontos.setEnabled(true);		
		
		btnSalvar = new JButton();
		btnSalvar.setFont(fonteTxt.deriveFont(Font.BOLD, 15));
		btnSalvar.setText("Salvar pontuação");		
		btnSalvar.setBounds(50, 152, 200, 30);
		btnSalvar.addActionListener(salvaPonCtrl);
		btnSalvar.setVisible(true);
		btnSalvar.setEnabled(true);
		
		add(lblNome);
		add(txtNome);
		add(lblPontos);
		add(txtPontos);
		add(btnSalvar);
		
		revalidate();
		
		return this;
	}
	
	public boolean verificaSeSalva(int pontosJogador){
		return salvaPonCtrl.verificaSeSalva(pontosJogador);
	}
	
	public void mostrarMensagem(String msg, String titulo){
		//cria o label que conterá o texto
		JLabel lblMsg = new JLabel();
		lblMsg.setFont(fonteTxt.deriveFont(Font.BOLD, 14));
		lblMsg.setText(msg);
		lblMsg.setHorizontalAlignment(SwingConstants.CENTER);
		
		//mostra a mensagem
		JOptionPane.showMessageDialog(null,lblMsg,titulo,JOptionPane.PLAIN_MESSAGE);
	}

	public JTextField getTxtNome() {
		return txtNome;
	}

	public JButton getBtnSalvar() {
		return btnSalvar;
	}

	public int getPontosJogador() {
		return pontosJogador;
	}

	public boolean isPontosSalvos() {
		return pontosSalvos;
	}

	public void setPontosSalvos(boolean pontosSalvos) {
		this.pontosSalvos = pontosSalvos;
	}
}
