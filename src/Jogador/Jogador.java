package src.Jogador;
import src.Abstracao.TipoJogador;
import src.Interface.Main;
import src.Tabuleiro.Dado;

public abstract class Jogador {
	protected String nome;
	protected String cor;
	protected int posicao;
	protected TipoJogador tipo;
	protected boolean ativo = true;
	private int turnosBloqueados = 0;
    private boolean jogarDeNovo = false;
    private int totalDeJogadas = 0;
	
	public Jogador(String nome, String cor, int posicao, TipoJogador tipo) {
		this.nome = nome;
		this.cor = cor;
		this.posicao = posicao;
		this.tipo = tipo;
	}

	public String getNome() {
		return nome;
	}

	public String getCor() {
		return cor;
	}

	public int getPosicao() {
		return posicao;
	}
	
	public boolean getAtivo() {
		return ativo;
	}
	
	public int getTurnosBloqueados() {
		return turnosBloqueados;
	}
	
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	public void setPosicao(int posicao) {
		this.posicao = posicao;
	}
	
	public void setTurnosBloqueados(int turnosBloqueados) {
		this.turnosBloqueados = turnosBloqueados;
	}

    public boolean getJogarDeNovo() {
        return jogarDeNovo;
    }
    public void setJogarDeNovo(boolean jogarDeNovo) {
        this.jogarDeNovo = jogarDeNovo;
    }
    public int getTotalDeJogadas() {
        return totalDeJogadas;
    }
    public void setTotalDeJogadas(int totalDeJogadas) {
        this.totalDeJogadas = totalDeJogadas;
    }
    protected abstract boolean aplicarRegraEspecialDados(int[] dados, int passos);

    public int mover(int passos){
        this.posicao += passos;
        if (this.posicao < 1){
            this.posicao = 1;
        }
        return this.posicao;
    }
    public int mudarPosicao() {
        if (!this.ativo){ return 0; }

        int passos = 0;
        int passosAcumulados = 0;
        boolean rolagemValida = false;

        // =======================================================
        // 1. Estágio de Rolagem e Validação (JOGA ATÉ ACHAR UM NÚMERO VÁLIDO)
        // =======================================================
        do {
            // Rola os dados
            int[] dados = Dado.rolarDados();
            passos = Dado.calcularPassos(dados);

            // Aplica a regra especial (Sortudo/Azarado)
            rolagemValida = aplicarRegraEspecialDados(dados, passos);

            if (!rolagemValida) {
                // A mensagem de rolagem inválida já foi exibida por Sortudo/Azarado
                System.out.println("⚠️ Rolagem inválida pela regra especial. Tentando novamente...");

                continue;
            }

            // Se a rolagem é válida, avança o movimento.
            mover(passos);
            passosAcumulados += passos;
            this.totalDeJogadas++; // Conta a jogada válida

            System.out.format("\nJogador: %s %s%s tirou %d, %d, e andou %d (Nova Posição %d).\n",
                    this.getCor(), this.getNome(), Main.ANSI_RESET, dados[0], dados[1], passos, this.getPosicao());

            if (Dado.verificarDadosIguais(dados)) {
                System.out.format("-> Jogador: %s pegou dados iguais e ganhou o direito de jogar mais uma vez!\n", this.nome);
                this.setJogarDeNovo(true);

                // Se ganhou o direito de jogar de novo, entra em um loop secundário
                while (this.getJogarDeNovo()) {
                    this.setJogarDeNovo(false); // Reseta para controlar a próxima iteração
                    System.out.println("-> Jogador: Realizando jogada extra!");

                    // Rola novamente para a jogada extra
                    dados = Dado.rolarDados();
                    passos = Dado.calcularPassos(dados);

                    if (!aplicarRegraEspecialDados(dados, passos)) {
                        System.out.println("❌ Jogada extra cancelada por rolagem inválida.");
                        break; // Sai do loop de jogadas extras
                    }

                    mover(passos);
                    passosAcumulados += passos;
                    this.totalDeJogadas++;

                    System.out.format("Jogador: %s %s%s tirou %d, %d, e andou %d (Nova Posição %d).\n",
                            this.getCor(), this.getNome(), Main.ANSI_RESET, dados[0], dados[1], passos, this.getPosicao());

                    // Verifica se a jogada extra deu novos dados iguais
                    if (Dado.verificarDadosIguais(dados)) {
                        System.out.format("-> Jogador: %s pegou dados iguais NOVAMENTE e ganhou direito a outra jogada!\n", this.nome);
                        this.setJogarDeNovo(true);
                    }
                }
            }

        } while (!rolagemValida); // O loop principal garante que o jogador andou pelo menos uma vez com uma rolagem válida

        return passosAcumulados;
    }

    public boolean verificarNome(String nome) {
        return this.nome.equalsIgnoreCase(nome);
    }

    public void decrementarBloqueio() {
        if (this.turnosBloqueados > 0) {
            this.turnosBloqueados--;
            if (this.turnosBloqueados == 0) {
                this.ativo = true;
            }
        }
    }
    public TipoJogador getTipo() {
        return tipo;
    }

    public void verificarAtivo() {
        if (this.turnosBloqueados > 0) {
            this.ativo = false;
            System.out.format("\nJogador: %s %s%s (%s) está BLOQUEADO por %d rodada(s).\n",
                    this.cor, this.nome, Main.ANSI_RESET, this.tipo.getDescricao(), this.turnosBloqueados);
        } else {
            this.ativo = true;
        }
    }
}
