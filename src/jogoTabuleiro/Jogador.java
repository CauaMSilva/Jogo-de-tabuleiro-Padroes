package jogoTabuleiro;
import java.util.*;

public class Jogador {
	protected String nome;
	protected String cor;
	protected int posicao;
	protected String tipo;
	protected boolean ativo = true;
	private int turnosBloqueados = 0;
	
	public Jogador(String nome, String cor) {
		this.nome = nome;
		this.cor = cor;
		this.posicao = 0;
		this.tipo = "Jogador normal";
		this.ativo = true;
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
	
	public String getClasse() {
		return this.tipo;
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
	
	public int mudarPosicao(Jogador j, Random geradorNum) {
		int dado1, dado2;
		int quantAndar;
		
		dado1 = geradorNum.nextInt(6) + 1;
		dado2 = geradorNum.nextInt(6) + 1;
		
		quantAndar = dado1 + dado2;
		
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
	
	public void relatorioJogador(Jogador j) {
		System.out.format("=======status de %s=======\n", j.getNome());
		System.out.format("cor: %s\nposição atual: %d\n", j.getCor(), j.getPosicao());
	}
	
	public void verificarAtivo(Jogador j) {
		if (j.getTurnosBloqueados() > 0) {
			j.setAtivo(false);
			System.out.format("%s(%s) teve o turno bloqueado", j.getCor(), j.getNome());
			j.setTurnosBloqueados(j.getTurnosBloqueados() - 1);
		}
		else {
			j.setAtivo(true);
		}
	}
	public boolean existeJogador(String nome) {
		if (this.nome.toLowerCase().equals(nome.toLowerCase())) {
			return true;
		}
		return false;
	}
	
	
}
