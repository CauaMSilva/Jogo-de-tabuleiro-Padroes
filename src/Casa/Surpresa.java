package src.Casa;

import src.Abstracao.TipoJogador;
import src.Jogador.FabricadeJogador;
import src.Jogador.Jogador;
import src.Tabuleiro.Tabuleiro;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Surpresa extends Casa{
    public Surpresa(int numero){
        super(numero);
    }
    public String getNome() {
        return "Casa da Surpresa";
    }

    @Override
    public void aplicarRegra(Jogador jogador, ArrayList<Jogador> todosJogadores, Tabuleiro tabuleiro, Scanner read, Random rand) {
        exibirMensagemCaiu(jogador);
        TipoJogador tipoAtual = jogador.getTipo();
        TipoJogador novoTipo;

        do {
            novoTipo = TipoJogador.values()[rand.nextInt(TipoJogador.values().length)];
        } while (novoTipo == tipoAtual);

        // 1. Cria a nova instância de jogador (mantendo o estado)
        Jogador novoJogador = FabricadeJogador.criarJogador(jogador.getNome(), jogador.getCor(), jogador.getPosicao(), novoTipo);
        novoJogador.setTurnosBloqueados(jogador.getTurnosBloqueados());
        novoJogador.setAtivo(jogador.getAtivo());

        novoJogador.setJogarDeNovo(jogador.getJogarDeNovo());

        // 2. Substitui na lista
        int indice = todosJogadores.indexOf(jogador);
        if (indice != -1){
            todosJogadores.set(indice, novoJogador);
        }
        System.out.format("MUDANÇA DE CLASSE! Você agora é um %s.\n", novoTipo.getDescricao());
    }
}
