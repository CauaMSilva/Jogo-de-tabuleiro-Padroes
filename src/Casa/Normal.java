package src.Casa;

import src.Jogador.Jogador;
import src.Tabuleiro.Tabuleiro;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Normal extends Casa {
    public Normal(int numero){
        super(numero);
    }
    public String getNome() {
        return "Casa Normal";
    }

    @Override
    public void aplicarRegra(Jogador jogador, ArrayList<Jogador> todosJogadores, Tabuleiro tabuleiro, Scanner read, Random rand) {
        //nenhuma regra especial
    }
}
