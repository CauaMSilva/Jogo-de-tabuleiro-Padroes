package src.Casa;

import src.Abstracao.TipoJogador;
import src.Jogador.Jogador;
import src.Tabuleiro.Tabuleiro;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Sorte extends Casa{
    public Sorte(int numero){
        super(numero);
    }
    public String getNome() {
        return "Casa da Sorte";
    }

    @Override
    public void aplicarRegra(Jogador jogador, ArrayList<Jogador> todosJogadores, Tabuleiro tabuleiro, Scanner read, Random rand) {
        if(jogador.getTipo() == TipoJogador.AZARADO){
            System.out.format("Que Azar ! Jogadores Azarados não podem aproveitar o bônus da Sorte.");
        }else {
            jogador.mover(3);
            System.out.println("Bônus de Sorte! Avança 3 casas.");
        }
    }
}
