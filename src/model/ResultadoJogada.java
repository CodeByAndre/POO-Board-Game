/* Module and purpose: Result of one turn (roll, movement, chained effects), read by the console.
 * Student name/number and implemented contributions: Diogo Marques (30016433) - original implementation; André Ferreira (30017457) - list of chained effects
 * Collaborator/reviewer and contribution: cross review between both students
 * AI assistance: Claude, initial skeleton - see AI_USE.md
 */
package model;

import java.util.ArrayList;

public class ResultadoJogada {
    private final String jogador;
    private final int valor;
    private final int origem;
    private final int destino;
    private final boolean ressalto;
    private final ArrayList<String> efeitos;

    public ResultadoJogada(String jogador, int valor, int origem, int destino,
                           boolean ressalto, ArrayList<String> efeitos) {
        this.jogador = jogador;
        this.valor = valor;
        this.origem = origem;
        this.destino = destino;
        this.ressalto = ressalto;
        this.efeitos = new ArrayList<>(efeitos);
    }

    public String getJogador() {
        return jogador;
    }

    public int getValor() {
        return valor;
    }

    public int getOrigem() {
        return origem;
    }

    public int getDestino() {
        return destino;
    }

    public boolean isRessalto() {
        return ressalto;
    }

    // Todos os efeitos aplicados, por ordem (vazia se a casa não tinha efeito).
    public ArrayList<String> getEfeitos() {
        return new ArrayList<>(efeitos);
    }

    // Primeiro efeito aplicado, ou null se não houve nenhum.
    public String getEfeito() {
        if (efeitos.isEmpty()) {
            return null;
        }

        return efeitos.get(0);
    }
}
