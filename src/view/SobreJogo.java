package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class SobreJogo extends JPanel {
	public SobreJogo(){
		//logo do jogo
		Icon logomarca 	= new ImageIcon(getClass().getResource("/recursos/gui/logomarca.png"));
		
		//pega a fonte do jogo
		Font fonteTxt = Fonte.getFonteTexto();		

	    //configurações do painel
	    setSize(290, 280);
	    setPreferredSize(new Dimension(290, 308));
	    
	    //define a borda do painel
	    setBorder(new EmptyBorder(0, 0, 0, 0));
	    
	    //conteúdo do painel
	    JLabel img_logo = new JLabel(logomarca);
	    img_logo.setBounds(new Rectangle(142, 154, 2, 99));
		
		JLabel msg = new JLabel();
		msg.setBounds(new Rectangle(20, 29, 396, 341));
		msg.setFont(fonteTxt.deriveFont(Font.PLAIN, 14));
		msg.setText("<html> "
				+ "<br>Aprenda sobre coleta seletiva jogando. <br><br> "
				+ "<b>Versão:</b> 1.3 <br/><br/>"
				+ "<b>Desenvolvedores:</b> <br> Danilo Santana - C3228B1 <br> Diogo R. Lopes - C173398 "
				+ "<br>Ricardo Souto - C13CH5 <br> Wanderson Martins - C202754 "
				+ "<br>Yury Rodrigues - C203360<br>" + " </html>");
		
		//adicioan o conteúdo ao painel
		add(img_logo);
		add(msg);
	    
	    //exibe o jpanel
	    setVisible(true);
	}
}