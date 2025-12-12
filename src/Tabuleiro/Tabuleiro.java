package src.Tabuleiro;

import src.Casa.*;
import src.Interface.Main;
import src.Jogador.Jogador;

import java.util.*;

public class Tabuleiro {
    private static Tabuleiro instance; // Instância única (Singleton)
    private final int TOTAL_CASAS;
    private final List<Casa> casas = new ArrayList<>();
    private int turnoAtual = 1;

    private Tabuleiro(int numCasas) {
        this.TOTAL_CASAS = numCasas;
        inicializarCasas();
        distribuirCasasEspeciais();
    }

    // Método de acesso estático (Singleton)
    public static Tabuleiro getInstance(int numCasas) {
        if (instance == null) {
            instance = new Tabuleiro(numCasas);
        }
        return instance;
    }

    // Método para resetar o Singleton (útil para testes, mas não essencial no jogo)
    public static void resetInstance() {
        instance = null;
    }

    private void inicializarCasas() {
        // 1. Cria todas como CasaSimples
        for (int i = 0; i < TOTAL_CASAS; i++) {
            casas.add(new Normal(i));
        }
    }
    private void distribuirCasasEspeciais() {
        // Exemplo: Colocar sorte em 10%, 30% e 70% do caminho
        definirEspecial(0.10, "Sorte");
        definirEspecial(0.30, "Sorte");
        definirEspecial(0.70, "Sorte");

        // Prisão em 25% e 85%
        definirEspecial(0.25, "Prisao");
        definirEspecial(0.85, "Prisao");

        // Outras regras
        definirEspecial(0.50, "Surpresa");
        definirEspecial(0.65, "Azar");
        definirEspecial(0.90, "Reversa");
        definirEspecial(0.40, "JogaDeNovo");
    }
    private void definirEspecial(double porcentagem, String tipo) {
        int index = (int) (TOTAL_CASAS * porcentagem) - 1;
        if (index > 0 && index < TOTAL_CASAS - 1) { // Não altera a primeira nem a última casa
            int numeroCasa = index + 1;
            switch (tipo) {
                case "Sorte" -> casas.set(index, new Sorte(numeroCasa));
                case "Prisao" -> casas.set(index, new Prisao(numeroCasa));
                case "Azar" -> casas.set(index, new Azar(numeroCasa));
                case "Surpresa" -> casas.set(index, new Surpresa(numeroCasa));
                case "Reversa" -> casas.set(index, new Reversa(numeroCasa));
                case "JogaDeNovo" -> casas.set(index, new JogaDeNovo(numeroCasa));
            }
        }
    }
    public void aplicarEfeitoCasa(Jogador jogador, ArrayList<Jogador> jogadores, Scanner read, Random random) {
        Casa casaAtual = getCasa(jogador.getPosicao());
        casaAtual.aplicarRegra(jogador, jogadores, this, read, random);
    }

    public int getTurnoAtual() { return turnoAtual; }
    public void mudarTurno() { this.turnoAtual += 1; }
    public int getTotalCasas() { return TOTAL_CASAS; }

    public Casa getCasa(int posicao) {
        if (posicao <= 0) return casas.get(0);
        if (posicao > TOTAL_CASAS) return casas.get(TOTAL_CASAS - 1);
        return casas.get(posicao - 1);
    }

    public Jogador verificarVitorioso(Jogador jogador) {
        if (jogador.getPosicao() >= TOTAL_CASAS) {
            System.out.println("\nTEMOS UM VENCEDOR!!!\n\n");
            System.out.format("Jogador(a): %s venceu!, ele chegou na marca de %d casas.\n", jogador.getNome(), TOTAL_CASAS);
            return jogador;
        }
        return null;
    }

    public void relatorioGeral(List<Jogador> jogadores) {
        System.out.format("\n======= Status Geral (Turno %d) =======\n", getTurnoAtual());
        for (Jogador j : jogadores) {
            String status = j.getAtivo() ? "ATIVO" : "BLOQUEADO";
            System.out.format("Jogador: %s %s%s (Tipo: %s)\nPosição: %d/%d\nStatus: %s (Bloqueios restantes: %d)\n\n",
                    j.getCor(), j.getNome(), Main.ANSI_RESET,
                    j.getTipo().getDescricao(), j.getPosicao(), TOTAL_CASAS, status, j.getTurnosBloqueados());
        }
        System.out.format("======================================\n\n");
    }

    public void verificarCasa(Jogador jogador, ArrayList<Jogador> jogadores, Scanner read, Random random) {
        if (jogador.getPosicao() > 0 && jogador.getPosicao() <= TOTAL_CASAS) {
            Casa casaAtual = getCasa(jogador.getPosicao());
            casaAtual.aplicarRegra(jogador, jogadores, this, read, random);
        }
    }
}