package view;

import java.awt.Dimension;
import java.awt.Font;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

import control.RankPontuacaoCtrl;

public class RankPontuacao extends JScrollPane {	
	//fonte utilizada no texto
	private Font fonteTxt;
	
	public RankPontuacao(){	
		//inicia o controlador da janela
		RankPontuacaoCtrl rankPonCtrl = new RankPontuacaoCtrl();
		
		//pega a fonte do jogo
		fonteTxt = Fonte.getFonteTexto();		

	    //configurações do painel
	    setSize(470, 430);
	    setPreferredSize(new Dimension(470, 430));
	    
	    //define a borda do painel
	    setBorder(new EmptyBorder(0, 0, 0, 0));
	    
		//linhas da tabela, com o rank
	    Vector<Vector> dadosTabela = rankPonCtrl.getObRankPontos();
	    
		//nome das colunas
	    Vector nomesColunas = new Vector<Vector>();
	    nomesColunas.add("Posição");
	    nomesColunas.add("Pontos");
	    nomesColunas.add("Nome");
	    
	    //cria a tabela
	    JTable table = new JTable(dadosTabela, nomesColunas);

	    //adiciona a tabela ao jpanel
	    setViewportView(table);
	    
	    //desabilita a edição da tabela
	    table.setEnabled(false);
	    //desabilita a movimentação das colunas, drag-drop
	    table.getTableHeader().setReorderingAllowed(false);

	    //define o alinhamento do texto da coluna de rank
	    DefaultTableCellRenderer colunaRenderer = new DefaultTableCellRenderer();
	    colunaRenderer.setHorizontalAlignment(SwingConstants.RIGHT);	    
	    table.getColumn("Posição").setCellRenderer(colunaRenderer);
	    
	    //largura das colunas
	    table.getColumn("Posição").setPreferredWidth(1);
	    table.getColumn("Pontos").setPreferredWidth(80);
	    table.getColumn("Nome").setPreferredWidth(200);
	    
	    //altura do cabeçalho da tabela
	    table.getTableHeader().setPreferredSize(new Dimension(1, 25));
	    
	    //altura das linhas da tabela
	    table.setRowHeight(24);

	    //fonte do tabela	    
	    table.setFont(fonteTxt.deriveFont(Font.PLAIN, 14));
	    //fonte do cabeçalho da tabela
	    table.getTableHeader().setFont(fonteTxt.deriveFont(Font.BOLD, 14));
	    
	    //exibe o jpanel
	    setVisible(true);
	}
}
