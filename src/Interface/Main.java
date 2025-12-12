package src.Interface;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import src.Controles.Debug;
import src.Controles.Jogo;
import src.Jogador.Jogador;
import src.Tabuleiro.Tabuleiro;

public class Main {

    public static final String ANSI_RESET = "\u001B[0m";

    public static void main(String[] args) {
        Scanner read = new Scanner(System.in);
        Random rand = new Random();

        int numCasa;
        do {
            System.out.println("Digite o número de casas do Tabuleiro (mínimo 20 casas): ");
            numCasa = read.nextInt();
        } while (numCasa < 20);

        int numJogadores;
        do {
            System.out.println("Digite o número de jogadores( Mín: 2  e Max: 6): ");
            numJogadores = read.nextInt();
        } while (numJogadores < 2 || numJogadores > 6);
        read.nextLine();

        Jogo jogo  = new Jogo(read, rand);

        jogo.configTabuleiro(numCasa);
        jogo.config(numJogadores);
        jogo.printTabuleiro();
        jogo.start();

        read.close();

    }
}