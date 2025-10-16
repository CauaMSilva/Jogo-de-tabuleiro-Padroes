package jogoTabuleiro;
import java.util.*;

public class Main {

	public static void main(String[] args) {
		Scanner read = new Scanner(System.in);
		Random rand = new Random();
		ArrayList<Jogador> jogadores = new ArrayList<Jogador>();
		Tabuleiro tabuleiro = new Tabuleiro();
		Debug debug = new Debug(true);
		Jogo jogo = new Jogo();
		
		
		jogo.cadastrarJogadores(read, jogadores, rand);
		if (jogo.tiposSuficientes(jogadores)) {
			jogo.partida(jogadores, tabuleiro, read, rand, debug);
		}
		
		
	}
}

