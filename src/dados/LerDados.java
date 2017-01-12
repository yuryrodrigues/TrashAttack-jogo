package dados;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LerDados {
	//Vetor que vai guardar os dados lidos
	private ArrayList<String> vetorDados = new ArrayList<String>(); 
	private String endArquivo;
	private File arquivo;
	private FileReader fileReader;
	private BufferedReader bufferedReader;
	private String linhaAtual;
	
	public boolean lerArquivo(String endArquivo){		
		//Endereco do arquivo
		this.endArquivo	= endArquivo;
		
		try {
			abreArquivo();
		} catch (FileNotFoundException e1) {
			System.out.println("Arquivo não encontrado: "+e1);
			
			return false;
		}
		
		leArquivo();

		try {
			//Fecha o arquivo
			bufferedReader.close();
		} 
		catch (IOException e) {
			throw new RuntimeException("Erro no fechamento do arquivo: "+e);
		}
		
		return true;
	}
	
	//Especifica a quantidade de linhas que devem ser lidas
	public boolean lerArquivo(String endArquivo, int qtLinhas){
		//qtLinhas = Quantidade de linhas para serem lidas
		
		//Endereco do arquivo
		this.endArquivo	= endArquivo;
		
		try {
			abreArquivo();
		} catch (FileNotFoundException e1) {
			System.out.println("Arquivo não encontrado: "+e1);
			
			return false;
		}
		
		leArquivo(qtLinhas);
	
		try {
			//Fecha o arquivo
			bufferedReader.close();
		} 
		catch (IOException e) {
			throw new RuntimeException("Erro no fechamento do arquivo: "+e);
		}
		
		return true;
	}
	
	//Retorna um vetor com as linhas lidas
	public ArrayList<String> getVetorDados(){
		return vetorDados;
	}
	
	//Abre o arquivo que será lido
	private void abreArquivo() throws FileNotFoundException {
		//Especifica o local do arquivo que será lido
		arquivo 		= new File(endArquivo);

		//Abri o arquivo para leitura
		fileReader 		= new FileReader(arquivo);
		
		//Objeto para manipular o arquivo utilizando o buffere
		bufferedReader 	= new BufferedReader(fileReader);
	}
	
	//Ler todas as linhas do arquivo
	private void leArquivo(){	
		try{
			//Ler linha por linha
			while((linhaAtual = bufferedReader.readLine()) != null) {
				//Adiciona o valor da linha atual para o vetor
				vetorDados.add(linhaAtual); 
			}
		} 
		catch (IOException e) {
			throw new RuntimeException("Erro na leitura do arquivo: "+e);
		}
	}
	
	//Ler o arquivo até a quantidade de linhas especificadas
	private void leArquivo(int qtLinhas){
		//Tem que ler somente a quantidade especificada
		int i = 1;
		
		try {
			//Ler linha por linha
			while((linhaAtual = bufferedReader.readLine()) != null && i <= qtLinhas) {
				//Adiciona o valor da linha atual para o vetor
				vetorDados.add(linhaAtual); 
				
				i++;
			}
		} 
		catch (IOException e) {
			throw new RuntimeException("Erro na leitura do arquivo: "+e);
		}
	}
}
