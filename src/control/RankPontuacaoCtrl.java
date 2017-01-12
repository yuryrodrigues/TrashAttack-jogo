package control;

import java.util.ArrayList;
import java.util.Vector;

import dados.DadosPontuacao;

public class RankPontuacaoCtrl {	
	//objeto que manipula os dados das pontuações salvos
	private DadosPontuacao dadosPontuacao;
	
	//lista com os pontos e nomes dos jogadores separados
	private ArrayList<ArrayList<String>> listRankPontos;
	
	public RankPontuacaoCtrl(){
		//instancia a classe que vai manipular o dados de pontuação
		dadosPontuacao 	= new DadosPontuacao();		

		//cria a lista que vai guardar os dados do rank
		listRankPontos = new ArrayList<ArrayList<String>>();
	}
	
	public Vector<Vector> getObRankPontos(){		
		//pega os dados do rank
		listRankPontos = dadosPontuacao.getListRankPontos();
		
		//vetor com as linhas que serão exibidas na tabela
		Vector<Vector> linhas = new Vector<Vector>();
		
		int j = 1;
		//passa a lista de HashMap para Object
		for(int i=listRankPontos.size()-1; i>=0; i--){
			//cria uma nova linha
			Vector<String> novaLinha = new Vector<String>();
			
			//adiciona os valores das celulas da linha
			novaLinha.addElement(j+" ");
			novaLinha.addElement(" "+listRankPontos.get(i).get(0));
			novaLinha.addElement(" "+listRankPontos.get(i).get(1));	    
		   
			//adiciona a linha ao vetor de linhas
			linhas.addElement(novaLinha);
			
			//indica a próxima no posição do rank
			j++;
		}
		
		return linhas;
	}
}
