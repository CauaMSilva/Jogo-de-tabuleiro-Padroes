package src.Casa;

import src.Abstracao.TipoJogador;
import src.Jogador.Jogador;
import src.Tabuleiro.Tabuleiro;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Azar extends Casa{
    public Azar(int numero){
        super(numero);
    }
    public String getNome() {
        return "Casa Azar";
    }
    public void aplicarRegra(Jogador jogador, ArrayList<Jogador> todosJogadores, Tabuleiro tabuleiro, Scanner read, Random rand) {
        exibirMensagemCaiu(jogador);
        if(jogador.getTipo() == TipoJogador.SORTUDO){
            System.out.println("\nQue Sorte ! Jogadores Sortudos não afetados pela Casa de Azar.");
        } else {
            jogador.mover(-3);
            System.out.println("Punição de Azar ! Você voltou 3 casas.");
        }
    }
}
