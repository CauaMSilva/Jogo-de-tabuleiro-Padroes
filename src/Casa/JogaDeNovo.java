package src.Casa;

import src.Jogador.Jogador;
import src.Tabuleiro.Dado;
import src.Tabuleiro.Tabuleiro;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class JogaDeNovo extends Casa{
    public JogaDeNovo(int numero){
        super(numero);
    }
    public String getNome() {
        return "Casa de Jogar de Novo";
    }
    public void aplicarRegra(Jogador jogador, ArrayList<Jogador> todosJogadores, Tabuleiro tabuleiro, Scanner read, Random rand) {
        exibirMensagemCaiu(jogador);
        jogador.setJogarDeNovo(true); // Novo método no Jogador
        System.out.println("JOGADA EXTRA! Você joga o dado mais uma vez neste turno.");
    }
}
