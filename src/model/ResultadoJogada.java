/* Module and purpose: Result of one turn (roll, movement, effect), read by the console.
 * Student name/number and implemented contributions: <Diogo Marques> - implementation
 * Collaborator/reviewer and contribution: <Diogo Marques> - review
 * AI assistance: Claude, initial skeleton - see AI_USE.md
 */
package model;

public class ResultadoJogada {
    private final String jogador;
    private final int valor;
    private final int origem;
    private final int destino;
    private final boolean ressalto;
    private final String efeito;

    public ResultadoJogada(String jogador, int valor, int origem, int destino,
                           boolean ressalto, String efeito) {
        this.jogador = jogador;
        this.valor = valor;
        this.origem = origem;
        this.destino = destino;
        this.ressalto = ressalto;
        this.efeito = efeito;
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

    // Nome da casa especial, ou null se a casa não tinha efeito.
    public String getEfeito() {
        return efeito;
    }
}
