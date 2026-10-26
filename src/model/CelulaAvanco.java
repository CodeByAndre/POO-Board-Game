/* Module and purpose: Cell that moves the pawn forward.
 * Student name/number and implemented contributions: <Aluno 2> - implementation
 * Collaborator/reviewer and contribution: <Aluno 1> - review
 * AI assistance: Claude, initial skeleton - see AI_USE.md
 */
package model;

public class CelulaAvanco extends Celula {
    private final int casas;

    public CelulaAvanco(int numero, String nome, int casas) {
        super(numero, nome);

        if (casas < 1) {
            throw new IllegalArgumentException("Casas a avançar têm de ser positivas");
        }

        this.casas = casas;
    }

    @Override
    public int deslocamento() {
        return casas;
    }
}
