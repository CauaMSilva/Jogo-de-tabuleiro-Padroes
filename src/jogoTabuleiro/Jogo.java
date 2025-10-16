package jogoTabuleiro;
import java.util.*;

public class Jogo {

	public Jogo() {
		
	}
	public boolean tiposSuficientes(ArrayList<Jogador> jogadores) {
	    boolean temNormal = false;
	    boolean temAzarado = false;
	    boolean temSortudo = false;
	    
	    for (Jogador j : jogadores) {
	        if (j instanceof JogadorAzarado) temAzarado = true;
	        else if (j instanceof JogadorSortudo) temSortudo = true;
	        else temNormal = true;
	    }
	    
	    int tipos = 0;
	    
	    if (temNormal) 
	    	tipos++;
	    if (temAzarado) 
	    	tipos++;
	    if (temSortudo) 
	    	tipos++;
	    
	    if (tipos >= 2) {
	    	return true;
	    }
	    return false;
	}

	public int cadastrarJogadores(Scanner ler, ArrayList<Jogador> jogadores, Random rand) {
		
		System.out.println("Quantos Jogadores irão jogar: ");
		int quantJogadores = ler.nextInt();
		ler.nextLine();
		
		if (quantJogadores > 6 || quantJogadores < 1) { 
			System.out.println("Quantidade invalida");
			return cadastrarJogadores(ler, jogadores, rand);
		}
		String nome;
		String cor;
		int tipoJogador;

		int saidaDoCadastro = 0;

		while (saidaDoCadastro < quantJogadores){
			for (int i = 0; i < quantJogadores; i++) {
	
				System.out.println("Digite seu nome: ");
				nome = ler.nextLine();
				System.out.println("Digite sua cor: ");
				cor = ler.nextLine();
			
				System.out.println("\n");
			
				tipoJogador = rand.nextInt(3);
			
				switch (tipoJogador) {
				case 0: 
					jogadores.add(new Jogador(nome, cor, 0));
					break;
				case 1: 
					jogadores.add(new JogadorAzarado(nome, cor, 0));
					break;
				case 2:
					jogadores.add(new JogadorSortudo(nome, cor, 0));
					break;
				}
				saidaDoCadastro++;
			}

			int condiçãoDeInicio = 0;

			for (int k = 0; k < 1; k++){
				for (int j = 1; j < jogadores.size(); j++){
					if (jogadores.get(k).getClasse() != jogadores.get(j).getClasse()){
						condiçãoDeInicio = 1;
					}
				}
			}

			if (condiçãoDeInicio == 0) {
				jogadores.clear();
				System.out.println("\nInfelizmente os tipos sorteados foram todos iguais cadastre os usuarios novamente: \n\n");
				return cadastrarJogadores(ler, jogadores, rand);
			}
				if (condiçãoDeInicio == 1)
				System.out.println("\nUsuarios cadastrados com sucesso\n\n");
		}
		return 0;
	}
	
	public void partida(ArrayList<Jogador> jogadores, Tabuleiro tabuleiro, Scanner read, Random rand) {
		
		int venceu = 0;
		
		while (venceu == 0) {
			for (int i = 0; i < jogadores.size(); i++) {
				jogadores.get(i).verificarAtivo(jogadores.get(i));
				if (jogadores.get(i).getAtivo()) {
					Jogador jogadorTurnoAtual = jogadores.get(i);
					jogadores.get(i).mudarPosicao(jogadorTurnoAtual, new Random());
					tabuleiro.verificarCasa(jogadorTurnoAtual, jogadores, tabuleiro, read, rand);
					venceu = tabuleiro.verificarVitorioso(jogadores.get(i));
					if (venceu != 0) {
						break;
					
					}
				}
			}

			tabuleiro.relatorioGeral(jogadores, tabuleiro);
			tabuleiro.mudarTurno();
		}
	}
}

