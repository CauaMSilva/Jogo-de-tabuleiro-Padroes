package src.Controles;

import java.util.*;

import src.Abstracao.TipoJogador;
import src.Interface.Main;
import src.Jogador.FabricadeJogador;
import src.Jogador.Jogador;
import src.Tabuleiro.Tabuleiro;

public class Jogo {
    private Tabuleiro tabuleiro;
    private ArrayList<Jogador> jogadores;
    private Scanner read;
    private Random rand;
    private Debug debug;

    public Jogo(Scanner read, Random rand) {
        this.read = read;
        this.rand = rand;
    }

    // Facade: configura o Tabuleiro (Singleton)
    public void configTabuleiro(int numCasas) {
        // Reinicia o Singleton se o tamanho for diferente (garante que só há 1)
        Tabuleiro.resetInstance();
        this.tabuleiro = Tabuleiro.getInstance(numCasas);
        System.out.println("\nTabuleiro configurado com " + numCasas + " casas.");
    }

    // Facade: configura jogadores
    public void config(int numJogadores) {
        configurarDebug();
        this.jogadores = cadastrarJogadores(numJogadores);
        System.out.println("Jogadores configurados.");
    }

    private void configurarDebug() {
        System.out.println("Deseja ativar o modo Debug? (S/N)");
        String resposta = read.nextLine().trim().toUpperCase();
        boolean debugAtivo = resposta.equals("S");
        this.debug = new Debug(debugAtivo);
        System.out.println(debugAtivo ? "Modo Debug ATIVADO." : "Modo Normal ATIVADO.");
    }

    public ArrayList<Jogador> cadastrarJogadores(int quantJogadores) {
        ArrayList<Jogador> tempJogadores = new ArrayList<>();
        boolean diversidadeAntiga;
        String[] cores = {"\u001B[31m", "\u001B[34m", "\u001B[32m", "\u001B[33m", "\u001B[35m", "\u001B[36m"};
        do {
            for (int i = 0; i < quantJogadores; i++) {
                System.out.format("\n--- Cadastro do %dº Jogador ---\n", i + 1);
                System.out.print("Digite seu nome: ");
                String nome = read.nextLine();

                // Usa a cor baseada no índice
                Jogador novoJogador = FabricadeJogador.sortearOTipoECriar(nome, cores[i], 0, rand);
                tempJogadores.add(novoJogador);
                System.out.format("Jogador %s%s%s cadastrado como: %s.\n", novoJogador.getCor(), novoJogador.getNome(), Main.ANSI_RESET, novoJogador.getTipo().getDescricao());
            }

            diversidadeAntiga = verificarDiversidade(tempJogadores);

            if (!diversidadeAntiga) {
                System.out.println("\nRecurso: Tipos sorteados não possuem diversidade mínima (mínimo 2 tipos diferentes). Recadastrando Jogadores...\n");
                // Se precisar de recursão, teria que ser fora deste método, mas para o Facade, simplificamos.
            }
        } while (!diversidadeAntiga);
        return tempJogadores;
    }

    private boolean verificarDiversidade(ArrayList<Jogador> jogadores) {
        Set<TipoJogador> tiposPresentes = new HashSet<>();
        for (Jogador j : jogadores) {
            tiposPresentes.add(j.getTipo());
        }
        return tiposPresentes.size() >= 2;
    }

    // Facade: Imprime status
    public void printTabuleiro() {
        tabuleiro.relatorioGeral(this.jogadores);
    }

    // Facade: Inicia a partida (Lógica principal)
    public void start() {
        Jogador vencedor = null;

        while (vencedor == null) {
            System.out.format("\n======== INÍCIO DO TURNO %d (Total de Casas: %d) ========\n", tabuleiro.getTurnoAtual(), tabuleiro.getTotalCasas());

            // LOOP SOBRE OS JOGADORES
            for (int i = 0; i < jogadores.size(); i++) {
                Jogador jogadorTurnoAtual = jogadores.get(i);
                jogadorTurnoAtual.verificarAtivo();

                // Variável de controle para jogadas extras (será true se dados ou casa derem bônus)
                boolean jogaMaisUmaVez;

                do {
                    jogaMaisUmaVez = false; // Reseta a cada iteração do do-while

                    if (jogadorTurnoAtual.getAtivo()) {
                        System.out.format("\nVEZ DO JOGADOR: %s %s %s (Tipo: %s)\n",
                                jogadorTurnoAtual.getCor(), jogadorTurnoAtual.getNome(), Main.ANSI_RESET,
                                jogadorTurnoAtual.getTipo().getDescricao());

                        // Bloco de Movimento (Debug ou Dados)
                        if (debug.isDebugAtivo()){
                            int posicaoForcada = debug.solicitarPosicao(jogadorTurnoAtual, read, tabuleiro);
                            jogadorTurnoAtual.setPosicao(posicaoForcada);
                            debug.notificarPosicaoForcada(jogadorTurnoAtual, posicaoForcada);
                        } else {
                            System.out.println("(Pressione ENTER para rolar os dados)");
                            read.nextLine();
                            // mudarPosicao agora move e define 'jogarDeNovo' se tirar dados iguais
                            jogadorTurnoAtual.mudarPosicao();
                        }

                        // Aplica efeitos da casa na posição final
                        // CasaJogaDeNovo também define 'jogarDeNovo'
                        tabuleiro.verificarCasa(jogadorTurnoAtual, jogadores, read, rand);

                        // Verifica se o jogador ganhou o direito de jogar de novo (por dados ou casa)
                        jogaMaisUmaVez = jogadorTurnoAtual.getJogarDeNovo();
                        jogadorTurnoAtual.setJogarDeNovo(false); // Consome o direito para não virar loop infinito

                        vencedor = tabuleiro.verificarVitorioso(jogadorTurnoAtual);
                        if (vencedor != null) break;

                    } else {
                        jogadorTurnoAtual.decrementarBloqueio();
                        // Se estiver bloqueado, ele não ganha jogada extra, então sai do do-while
                    }

                    // Continua jogando se tiver ganhado o direito de jogar de novo (jogaMaisUmaVez == true)
                } while (jogaMaisUmaVez && vencedor == null);
                // ===================================================

                if (vencedor != null) break;
            }

            if (vencedor != null) break;

            tabuleiro.relatorioGeral(jogadores);
            tabuleiro.mudarTurno();
        }

        exibirResultadosFinais();
    }

    private void exibirResultadosFinais() {
        System.out.println("\n\n-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        System.out.println("            FIM DO JOGO: RESULTADOS FINAIS ");
        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");

        for (Jogador j : jogadores) {
            System.out.format("-> Jogador: %s | Posição Final: Casa %d | Total de Jogadas: %d\n",
                    j.getNome(), j.getPosicao(), j.getTotalDeJogadas());
        }
    }

}
