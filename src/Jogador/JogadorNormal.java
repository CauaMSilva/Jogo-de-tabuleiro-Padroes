package src.Jogador;

import src.Abstracao.TipoJogador;

public class JogadorNormal extends Jogador{
    public JogadorNormal(String nome, String cor, int posicao) {
        super(nome, cor, posicao, TipoJogador.NORMAL);
    }
    @Override protected boolean aplicarRegraEspecialDados(int[] dados, int passos) {
        return true;
    }
}
