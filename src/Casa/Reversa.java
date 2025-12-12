package src.Casa;

import src.Interface.Main;
import src.Jogador.Jogador;
import src.Tabuleiro.Tabuleiro;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Reversa extends Casa{
    public Reversa(int numero){
        super(numero);
    }
    public String getNome() {
        return "Casa de Reversa";
    }

    @Override
    public void aplicarRegra(Jogador jogador, ArrayList<Jogador> todosJogadores, Tabuleiro tabuleiro, Scanner read, Random rand) {
        Jogador maisAtrasado = null;
        for (Jogador jog : todosJogadores) {
            if (maisAtrasado == null || jog.getPosicao() < maisAtrasado.getPosicao()) {
                maisAtrasado = jog;
            }
        }

        if (jogador.getPosicao() > maisAtrasado.getPosicao()) {
            int tempPosicao = jogador.getPosicao();
            jogador.setPosicao(maisAtrasado.getPosicao());
            maisAtrasado.setPosicao(tempPosicao);
            System.out.format("TROCA DE POSIÇÃO! Você trocou de lugar com %s(%s).\n", maisAtrasado.getCor() + maisAtrasado.getNome() + Main.ANSI_RESET, maisAtrasado.getCor());
        } else {
            System.out.println("Você já é o jogador mais atrasado. Nenhuma troca realizada.");
        }
    }
}
