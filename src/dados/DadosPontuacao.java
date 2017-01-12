package dados;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class DadosPontuacao {	
	//diretorio e pasta que ira guardar a pontuacao do jogo
	private String dirPontuacao 	= "trashAttack";
	private String arquivoPontuacao = "dados_pontuacao.txt";
	
	//lista com os nomes dos jogadore e seus pontos, unidos
	private ArrayList<String> txtPontos;
	
	//lista com os pontos e nomes dos jogadores separados
	private ArrayList<ArrayList<String>> listRankPontos;
	
	public DadosPontuacao(){
		//cria pasta que irá guardar a pontuação, caso não exista
		criaDirDadosPontos();
		
		//pega os dados antigos
		pegaDados();
		
		//ordena os dados
		ordena();
	}
	
	private void pegaDados(){
		//ler as pontuações no arquivo de texto
		//no máximo 100 linhas
		LerDados lerDados = new LerDados();
		lerDados.lerArquivo(dirPontuacao+"/"+arquivoPontuacao, 100);
		
		//if(lerDados.lerArquivo(dirPontuacao+"/"+arquivoPontuacao, 100)){
			//retorna as pontuações dos jogadores
			txtPontos = lerDados.getVetorDados();
		//}
		/*else{
			//caso retorne false, significa que não conseguiu ler o arquivo
			//por que ele não existe!
			txtPontos = new ArrayList<String>();
		}*/
		
		//remove linhas em branco
		txtPontos.remove("");
		txtPontos.remove(" ");
	}
	
	private void salvaPontuacao(ArrayList<String> dados){		
		//salva o novo rank de pontuação no arquivo de texto
		SalvaDados salvarDados = new SalvaDados();
		salvarDados.salvaDados(dirPontuacao+"/"+arquivoPontuacao,dados);
	}
	
	public void substitueMenorPonto(String dados){	
		//a lista está em ordem crescente
		//remove a primeira pontuação
		//caso exista mais de 100 jogadores no rank
		if(txtPontos.size() >= 100){
			//pega a menor pontuação do rank
			//transforma de volta em uma linha do arquivo de pontuação
			String menorPt = listRankPontos.get(0).get(1)+"¬¬"+listRankPontos.get(0).get(0);
			
			txtPontos.remove(menorPt);
		}
		
		//adiciona a nova pontuação a lista
		txtPontos.add(dados);
		
		//salva a nova pontuação
		salvaPontuacao(txtPontos);
	}
	
	public void ordena(){
		//ordena o rank
		separaPontosNomes();
		ordenaRank();
	}
	
	private void separaPontosNomes(){
		//cria o array que guardará os dados das pontuações
		listRankPontos = new ArrayList<ArrayList<String>>();
				 
		//pega toda as linhas do arquivo com a pontuação e os nomes dos jogadores
		for(int i=0; i<txtPontos.size(); i++){
			//separa o nome do jogador de seu ponto
			String[] splited = txtPontos.get(i).split("¬¬");			
			
			//cria um arraylist para guardar o nome do jogador e sua pontuação
			ArrayList<String> tmp = new ArrayList<String>();
			tmp.add(splited[splited.length-1]);
			tmp.add(splited[0]);
			
			//adiciona a coleção de listas
			listRankPontos.add(tmp);
		}
	}
	
	private void ordenaRank(){
		//método para comparar a pontuação dos jogadores
		Comparator<ArrayList<String>> comparador = new Comparator<ArrayList<String>>() {
	        @Override
	        public int compare(ArrayList<String> o1, ArrayList<String> o2) {
	            //compara os pontos de dois jogadores
	        	return Integer.compare(Integer.parseInt(o1.get(0)), Integer.parseInt(o2.get(0)));
	        }
	    };
	    
	    //ordena o rank de pontos
	    Collections.sort(listRankPontos, comparador);
	}
	
	private void criaDirDadosPontos(){
		//Endereco para a pasta que ira guardar as pontuações
		File dir = new File(dirPontuacao);
		
		//Caso a pasta especificada não exista
		if (!dir.exists()) {
			//Cria o diretorio que ira armazenar as imagens criadas
			if(!dir.mkdirs()) {
				System.out.println("Falha ao criar o diretorio: "+dirPontuacao);
			}
		}
	}

	public ArrayList<String> getTxtPontos() {
		return txtPontos;
	}

	public ArrayList<ArrayList<String>> getListRankPontos() {
		return listRankPontos;
	}
}
