package src.Casa;

import src.Abstracao.TipoCasa;
import src.Abstracao.TipoJogador;
import src.Interface.Main;
import src.Jogador.Jogador;
import src.Jogador.FabricadeJogador;
import src.Tabuleiro.Dado;
import src.Tabuleiro.Tabuleiro;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public abstract class Casa {
    private final int numero;

    public Casa(int numero) {
        this.numero = numero;
    }

    public int getNumero() {
        return numero;
    }

    public abstract String getNome();

    public abstract void aplicarRegra(Jogador jogador, ArrayList<Jogador> todosJogadores, Tabuleiro tabuleiro, Scanner read, Random rand);

    protected void exibirMensagemCaiu(Jogador jogador) {
        System.out.format("\n%s caiu na %s(Casa %d)!\n", jogador.getCor() + jogador.getNome() + Main.ANSI_RESET, this.getNome(),this.getNumero());
    }
}
