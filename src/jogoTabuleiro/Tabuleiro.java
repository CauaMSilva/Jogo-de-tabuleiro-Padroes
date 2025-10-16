package jogoTabuleiro;
import java.util.*;

public class Tabuleiro {
	private int totalCasas = 40;
	private String[] casas = new String[40];
	private int turnoAtual = 1;
	
	public Tabuleiro() {
		for (int i = 0; i < totalCasas; i++) {
			casas[i] = "casa normal";
			if (i + 1 == 5 || i + 1 == 15 || i + 1 == 30)
				casas[i] = "casa da sorte";
			else if (i + 1 == 10 || i + 1 == 25 || i + 1 == 38) 
				casas[i] = "casa stop";
			else if (i + 1 == 13) 
				casas[i] = "casa surpresa";
			else if (i + 1 == 17 || i + 1 == 27) 
				casas[i] = "casa do trapaceiro";
			else if (i + 1 == 20 || i + 1 == 35) 
				casas[i] = "casa coringa";
		}
	}
	
	public String[] getCasas() {
		return casas;
	}
	
	public String getCasa(int i) {
		return casas[i];
	}
	public int getTurnoAtual() {
		return turnoAtual;
	}
	public void mudarTurno() {
		this.turnoAtual += 1;
	}
	
    //Sessão de controle de jogo:
	
	public int verificarVitorioso(Jogador j) {
		if (j.getPosicao() > 39) {
			System.out.println("\nTEMOS UM VENCEDOR!!!\n\n");
			System.out.format("Jogador(a): %s venceuu!\n", j.getNome());
			return 1;
		}
		return 0;
	}
	
	public void relatorioGeral(ArrayList<Jogador> jogadores, Tabuleiro t) {
		System.out.format("\n=======Status geral=======\n\n");
		for (int i = 0; i < jogadores.size(); i++) {
			System.out.format("Jogador: %s\nCor: %s\nTipo de jogador: %s\nPosição: %d\n\n", 
					jogadores.get(i).getNome(), jogadores.get(i).getCor(), jogadores.get(i).getClasse(), jogadores.get(i).getPosicao());
		}
		System.out.format("Turno: %d", t.getTurnoAtual());
		System.out.format("\n=========================\n\n");
	}
	
	// Sessão de verificação de casas e casas especiais:
	
	public void casaDaSorte(Jogador j) {
		j.setPosicao(j.getPosicao() + 3);
	}
	
	public void casaStop(Jogador j) {
		j.setTurnosBloqueados(1);
		j.setPosicao(j.getPosicao() + 1);
	}
	
	public void casaTrapaceiro(Jogador j, ArrayList<Jogador> jogadores, Tabuleiro t, Scanner read) {
		System.out.println("Casa do trapaceiro\n\n"
				+ "Instruções: Sempre que um jogador cai na casa do trapaceiro ele tem direito de escolher um jogador para voltar ao inicio do jogo\n");
		
		int existe = 0;
		
		while (existe == 0) {
			System.out.format("Escreva o nome do jogador que você deseja mover para o inicio: ");
			t.relatorioGeral(jogadores, t);
			String nomeRemover = read.nextLine();
			for (int i = 0; i < jogadores.size(); i++) {
				if (jogadores.get(i).existeJogador(nomeRemover)) {
					Jogador jogadorRetornado = jogadores.get(i);
					jogadorRetornado.setPosicao(0);
					System.out.format("Jogador(a) %s movido com sucesso\n", jogadorRetornado.getNome());
					existe++;
					break;
				}
			}
			if (existe == 0) {
				System.out.println("Jogador não encontrado");
			}
		}
	}
	
	public int casaCoringa(Jogador j, ArrayList<Jogador> jogadores) {
		int menorLugar = 50;
		int auxiliar = j.getPosicao();
		Jogador ultimo = null;
		for (int i = 0; i < jogadores.size(); i++) {
			if (jogadores.get(i).getPosicao() < menorLugar) {
				menorLugar = jogadores.get(i).getPosicao();
				ultimo = jogadores.get(i);
			}
		}
		if (j.getPosicao() != menorLugar) {
			j.setPosicao(ultimo.getPosicao());
			ultimo.setPosicao(auxiliar);
			System.out.format("\nJogador: %s(%s) trocou de lugar com %s(%s)\n", j.getCor(), j.getNome(), ultimo.getCor(), ultimo.getNome());
			return 0;
		}
		return 0;
	}

	public int casaSurpresa(Jogador j, ArrayList<Jogador> jogadores, Random r) {
		int tipoRecebido = r.nextInt(3);
		String[] cartas = {"jogador normal", "jogador azarado", "jogador sortudo"};
		String nome = j.getNome();
		String cor = j.getCor();
		int posicao = j.getPosicao();

		if (cartas[tipoRecebido] == j.getClasse()){
			return casaSurpresa(j, jogadores, r);
		}

		for (int i = 0; i < jogadores.size(); i++){
			if (jogadores.get(i).getNome() == j.getNome()){
				jogadores.remove(i);
			}
		}

		if (cartas[tipoRecebido].equals("jogador normal")) jogadores.add(new Jogador(nome, cor, posicao));
		if (cartas[tipoRecebido].equals("jogador azarado")) jogadores.add(new JogadorAzarado(nome, cor, posicao));
		if (cartas[tipoRecebido].equals("jogador sortudo")) jogadores.add(new JogadorSortudo(nome, cor, posicao));

		return 0;
	}
	
	public void verificarCasa(Jogador j, ArrayList<Jogador> jogadores,Tabuleiro t, Scanner read, Random r){
		if (j.getPosicao() > 0 && j.getPosicao() < 41) {
			if (casas[j.getPosicao()-1].equals("casa da sorte")) {
				System.out.format("\nJogador: %s(%s) caiu na casa da sorte e andou mais 3 casas\n", j.getCor(), j.getNome());
				casaDaSorte(j);
			}
			else if (casas[j.getPosicao()-1].equals("casa stop")) {
				System.out.format("\nJogador: %s(%s) caiu na casa da stop\n", j.getCor(), j.getNome());
				casaStop(j);
			}
			else if (casas[j.getPosicao()-1].equals("casa do trapaceiro")) {
				System.out.format("\nJogador: %s(%s) caiu na casa do trapceiro\n", j.getCor(), j.getNome());
				casaTrapaceiro(j, jogadores, t, read);
			}
			else if(casas[j.getPosicao()-1].equals("casa coringa")) {
				System.out.format("\nJogador: %s(%s) caiu na casa da coringa\n", j.getCor(), j.getNome());
				casaCoringa(j, jogadores);
			}
			else if(casas[j.getPosicao()-1].equals("casa surpresa")) {
				System.out.format("\nJogador: %s(%s) caiu na casa da surpresa\n", j.getCor(), j.getNome());
				casaSurpresa(j, jogadores, r);
			
			}
		}
	}
}
