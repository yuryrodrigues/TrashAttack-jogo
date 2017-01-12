package control;

import view.Jogo;

public class RodarJogoCtrl {	
	//referencia para o controlador da janela do jogo
	private Jogo janelaJogo;
	//referencia para o controlador da janela do jogo
	private JogoCtrl jogoCtrl;
	
	public RodarJogoCtrl(Jogo janelaJogo, JogoCtrl jogoCtrl){
		//referencia para a janela do jogo
		this.janelaJogo = janelaJogo;
		
		//inicializa o controlador da janela do jogo
		this.jogoCtrl 	= jogoCtrl;
	}
	
	public void iniciarLoop(){	
		//próxima atualização, em segundos
        double proximaAtualizacao	= (double)System.nanoTime() / 1000000000.0;
        //lag, tempo máximo de atualização do jogo
        double tempoMaxAtualizacao	= 0.5;
        //frames já pulados
        int framesPulados 			= 1;
        //frames máximos que podem ser pulados, em computadores lentos
        int framesMaxParaPular		= 5;
        //quantidade de atualizações máximas por segundo
		double tempoAtualiPorSeg	= 1.0/255.0;
		
		//roda o jogo indefinidamente, ate receber a indicacao que deve finalizar
		while(janelaJogo.isAtivo()){
			//verifica se teclou alguma tecla de ação da janela
			//como pausar o jogo e fechar a janela
			jogoCtrl.verificaTeclasCtrlJanela();
			
			//somente procegue se o jogo estiver "jogável"
			if(!janelaJogo.isPause() && !janelaJogo.isGameOver() && !janelaJogo.isJogoGanho()){
				//pega o tempo atual, em segundos
	            double currTime = (double)System.nanoTime() / 1000000000.0;
	            
	            //se demorou mais do que "lag" para atualizar
	            if((currTime - proximaAtualizacao) > tempoMaxAtualizacao){
	            	//indica que o tempo da próxima atualização(delta)
	            	//deve ser somado ao tempo atual
	            	proximaAtualizacao = currTime;
	            }
	            
	            //se o jogo atualizou na velocidade especificado, ou
	            //se demorou demais para atualizar
	            if(currTime >= proximaAtualizacao)
	            {
	                //aumenta o tempo para a próxima atualização
	                proximaAtualizacao += tempoAtualiPorSeg;
	                
	                //atualiza o estado do jogo
	                jogoCtrl.atualizar();
	                
	                //se ainda tiver tempo para a próxima atualização, ou
	                //se já pulou a quantidade de frames máximas
	                if((currTime < proximaAtualizacao) || (framesPulados > framesMaxParaPular))
	                {
	                	//exibe o frame do jogo atualizado
	                    janelaJogo.repaint();
	                    
	                    //reseta os frames pulados
	                    framesPulados = 1;
	                }
	                else
	                {
	                	//indica que pulou um frame do jogo
	                    framesPulados++;
	                }
	            }
	            //se atualizou rápido demais
	            else
	            {
	                //calcula o tempo que deve dormir
	                int sleepTime = (int)(1000.0 * (proximaAtualizacao - currTime));

	                if(sleepTime > 0)
	                {
	                    //coloca o jogo para dormir até o tempo da próxima atualização
	                    try
	                    {
	                        Thread.sleep(sleepTime);
	                    }
	                    catch(InterruptedException e){}
	                }
	            }
			}
			else{
				janelaJogo.repaint();
			}
		}
		
		janelaJogo.desativaJogo();;
	}
}
