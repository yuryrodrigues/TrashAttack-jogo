package view;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Ajuda extends JPanel {
	public Ajuda(){
		//imagens do jogo
		Icon logomarca = new ImageIcon(getClass().getResource("/recursos/gui/logomarca.png"));
		Icon dica1 = new ImageIcon(getClass().getResource("/recursos/gui/dica1.png"));
		Icon dica2 = new ImageIcon(getClass().getResource("/recursos/gui/dica2.png"));
		Icon dica3 = new ImageIcon(getClass().getResource("/recursos/gui/dica3.png"));
		Icon dica4 = new ImageIcon(getClass().getResource("/recursos/gui/dica4.png"));
		Icon dica5 = new ImageIcon(getClass().getResource("/recursos/gui/dica5.png"));
		
		//pega a fonte do jogo
		Font fonteTxt = Fonte.getFonteTexto();		

	    //configurações do painel
		setSize(453, 526);
		setPreferredSize(new Dimension(453, 533));		
		setLayout(null);

	    //define a borda do painel
	    setBorder(new EmptyBorder(0, 0, 0, 0));
	    
	    //conteúdo do painel
	    JLabel img_logo = new JLabel(logomarca);
		img_logo.setBounds((455/2-263/2), 10, logomarca.getIconWidth(), logomarca.getIconHeight());
		
		JLabel msg1 = new JLabel();
		msg1.setText("O TrashAttack é um jogo de agilidade muito simples.");
		msg1.setFont(fonteTxt.deriveFont(Font.PLAIN, 14));
		msg1.setBounds(15, 94, getWidth(), 25);
		
		JLabel msg11 = new JLabel();
		msg11.setText("O objetivo é recolher os lixos antes deles caírem no chão.");
		msg11.setFont(fonteTxt.deriveFont(Font.PLAIN, 14));
		msg11.setBounds(15, 111, getWidth(), 25);
		
		JLabel msg2 = new JLabel();
		msg2.setText("Como Jogar");
		msg2.setFont(fonteTxt.deriveFont(Font.BOLD, 14));
		msg2.setBounds(15, 143, getWidth(), 15);
		
		JLabel msgDica1 = new JLabel();
		msgDica1.setText("Utilize as teclas A,S,D,F,G para trocar o tipo da lixeira.");
		msgDica1.setFont(fonteTxt.deriveFont(Font.PLAIN, 14));
		msgDica1.setBounds(15, 158, getWidth(), 15);
		
		JLabel imagem1 = new JLabel(dica1);
		imagem1.setBounds(15, 188, dica1.getIconWidth(), dica1.getIconHeight());
		
		JLabel msgDica2 = new JLabel();
		msgDica2.setText("Utilize as setas esquerda e direita para movimentar o jogador e o");
		msgDica2.setFont(fonteTxt.deriveFont(Font.PLAIN, 14));
		msgDica2.setBounds(15, 349, 453, 26);
		
		JLabel msgDica21 = new JLabel();
		msgDica21.setText("Espaço para atirar.");
		msgDica21.setFont(fonteTxt.deriveFont(Font.PLAIN, 14));
		msgDica21.setBounds(15, 367, 453, 26);
		
		JLabel imagem2  = new JLabel(dica2);
		imagem2.setBounds(15, 400, dica2.getIconWidth(), dica2.getIconHeight());
		
		JLabel imagem5  = new JLabel(dica5);
		imagem5.setBounds(161, 400, dica5.getIconWidth(), dica5.getIconHeight());
		
		JLabel msgDica3 = new JLabel();
		msgDica3.setText("Utilize a tecla P para pausar o jogo e a tecla ESC para fechar a janela.");
		msgDica3.setFont(fonteTxt.deriveFont(Font.PLAIN, 14));
		msgDica3.setBounds(15, 451, getWidth(), 15);
		
		JLabel imagem3  = new JLabel(dica3);
		imagem3.setBounds(15, 480, dica3.getIconWidth(), dica3.getIconHeight());
		
		JLabel imagem4  = new JLabel(dica4);
		imagem4.setBounds(97, 480, dica4.getIconWidth(), dica4.getIconHeight());
		
		//adicioan o conteúdo ao painel		
		add(msg1);
		add(msg11);
		add(img_logo);
		add(msg2);
		add(msgDica1);
		add(imagem1);
		add(msgDica2);
		add(msgDica21);
		add(imagem2);
		add(imagem5);
		add(msgDica3);
		add(imagem3);
		add(imagem4);
	    
	    //exibe o jpanel
	    setVisible(true);
	}
}