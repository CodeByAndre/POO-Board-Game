/* Module and purpose: Player with a name and the pawn position on the board.
 * AI assistance: Claude, initial skeleton - see AI_USE.md
 */
package model;

public class Jogador {
    public static final int POSICAO_INICIAL = 1;

    private final String nome;
    private int posicao;

    public Jogador(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }

        this.nome = nome.trim();
        this.posicao = POSICAO_INICIAL;
    }

    public String getNome() {
        return nome;
    }

    public int getPosicao() {
        return posicao;
    }

    public boolean moverPara(int novaPosicao) {
        if (novaPosicao < POSICAO_INICIAL) {
            return false;
        }

        this.posicao = novaPosicao;
        return true;
    }
}
