package jogoTabuleiro;
import java.util.*;

public class Debug {
    private boolean debugAtivo;

    public Debug(boolean debugAtivo){
        this.debugAtivo = debugAtivo;
    }

    public boolean getDebugAtivo() {
        return debugAtivo;
    }

    public void setDebugAtivo(boolean debugAtivo) {
        this.debugAtivo = debugAtivo;
    }

    public void forcarCasa(Jogador j, Scanner r){
        int posicao = 0;
        
        System.out.format("\n\nPara qual casa você deseja levar o jogador: %s(%s): ", j.getCor(), j.getNome());
        posicao = r.nextInt();
        r.nextLine();

        j.setPosicao(posicao);
        
        System.out.format("\nJogador: %s(%s) movido para a casa %d com sucesso\n", j.getCor(), j.getNome(), posicao);

    }
}