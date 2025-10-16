package jogoTabuleiro;

import java.util.Random;

public class JogadorAzarado extends Jogador {

	public JogadorAzarado(String nome, String cor, int posicao) {
		super(nome, cor, posicao);
		this.ativo = true;
		this.tipo = "Jogador azarado";
	}
	
	public int mudarPosicao(Jogador j, Random geradorNum) {
		int dado1, dado2;
		int quantAndar;
		
		dado1 = geradorNum.nextInt(6) + 1;
		dado2 = geradorNum.nextInt(6) + 1;
		
		quantAndar = dado1 + dado2;
		
		if (quantAndar > 6) {
			return mudarPosicao(j, geradorNum);
		}
		
		if (j.ativo == true) {
			if (dado1 == dado2) {
				j.setPosicao(j.getPosicao() + quantAndar);
				System.out.format("\nJogador: %s(%s) tirou %d, %d, e andou %d\n", j.getCor(), j.getNome(), dado1, dado2, dado1 + dado2);
				System.out.format("\nJogador: %s pegou dados iguais e ganhou o direito de jogar mais uma vez os dados\n", j.getNome());
				return j.mudarPosicao(j, geradorNum);
			}
			
			j.setPosicao(j.getPosicao() + quantAndar);
			
			System.out.format("\nJogador: %s(%s) tirou %d, %d e andou %d\n", j.getCor(), j.getNome(), dado1, dado2, dado1 + dado2);
		}
		
		return 0;
	}	
	
	public String getClasse(){
		return this.tipo;
	}
}
