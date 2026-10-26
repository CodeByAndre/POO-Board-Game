/* Module and purpose: Normal cell without effect.
 * Student name/number and implemented contributions: <Aluno 1> - implementation
 * Collaborator/reviewer and contribution: <Aluno 2> - review
 * AI assistance: Claude, initial skeleton - see AI_USE.md
 */
package model;

public class CelulaNormal extends Celula {
    public CelulaNormal(int numero, String nome) {
        super(numero, nome);
    }

    @Override
    public int deslocamento() {
        return 0;
    }
}
