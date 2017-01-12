package dados;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SalvaDados {
	private File arquivo;	
	private ArrayList<String> dadosArray;
	private boolean preservaDados;
	
	//Salva os dados em um arquivo de texto
	public void salvaDados(String endArquivo, ArrayList<String> dadosArray){
		//dados que serão escritos no arquivo
		this.dadosArray	= dadosArray;
		
		//Especifica o local do arquivo onde sera gravado os dados
		this.arquivo 	= new File(endArquivo);
		
		//indica que deseja apagar todos os dados do arquivo
		preservaDados 	= false;
		
		//Salva os dados em um arquivo
		salvaDadosArquivo();
	}
	
	//Salva os dados em um arquivo de texto e deleta o arquivo antigo
	public void salvaDados(String endArquivo, ArrayList<String> dadosArray, boolean preserva){
		//dados que serão escritos no arquivo
		this.dadosArray	= dadosArray;
		
		//Especifica o local do arquivo onde sera gravado os dados
		this.arquivo 	= new File(endArquivo);
		
		//verifica se deseja apagar o arquivo anterior
		if(preserva){
			//indica que deseja manter os dados antigos no arquivo
			preservaDados = true;
		}
		
		//Salva os dados em um arquivo
		salvaDadosArquivo();
	}
	
	//Salva os dados em um arquivo de texto
	private void salvaDadosArquivo(){
		try{
	        //Abri o arquivo para escrita, caso não exista será criado
			//true = adiciona linhas no final do arquivo
			FileWriter fileWriter 			= new FileWriter(arquivo, preservaDados);
	
	        //Objeto para manipular e escrever no arquivo utilizando o buffere
			BufferedWriter bufferedWriter 	= new BufferedWriter(fileWriter);
			
	        //Salva os dados no arquivo de texto
			for(int i=0; i<dadosArray.size(); i++){
				//Escreve ó número atual no arquivo
				bufferedWriter.write(dadosArray.get(i));

				//Cria uma nova linha
				bufferedWriter.newLine();
			}
	
	        //Fecha o arquivo
	        bufferedWriter.close();
		} 
		catch (IOException e) {
			throw new RuntimeException("Falha no salvamento do arquivo: "+e);
		}
	}
}
