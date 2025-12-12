package src.Casa;

import src.Jogador.Jogador;
import src.Tabuleiro.Tabuleiro;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Prisao extends Casa{
    public Prisao(int numero){
        super(numero);
    }

    public String getNome() {
        return "Casa de Prisão";
    }

    @Override
    public void aplicarRegra(Jogador jogador, ArrayList<Jogador> todosJogadores, Tabuleiro tabuleiro, Scanner read, Random rand) {
        exibirMensagemCaiu(jogador);
        jogador.setTurnosBloqueados(1);
        System.out.println("\nPERDE A VEZ! Bloqueado por 1 turno.");
    }
}
