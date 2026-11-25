/* Module and purpose: Dado that returns a fixed sequence of values; used by the automated checks.
 * Student name/number and implemented contributions: André Ferreira (30017457) - implementation
 * Collaborator/reviewer and contribution: Diogo Marques (30016433) - review
 * AI assistance: Claude, initial skeleton - see AI_USE.md
 */
package model;

public class DadoPredefinido implements Dado {
    private final int[] valores;
    private int indice;

    public DadoPredefinido(int[] valores) {
        if (valores == null || valores.length == 0) {
            throw new IllegalArgumentException("É preciso pelo menos um valor");
        }

        for (int v : valores) {
            if (v < DadoAleatorio.MINIMO || v > DadoAleatorio.MAXIMO) {
                throw new IllegalArgumentException("Valor inválido: " + v);
            }
        }

        this.valores = valores.clone();
        this.indice = 0;
    }

    @Override
    public int lancar() {
        if (indice >= valores.length) {
            throw new IllegalStateException("Sem mais valores predefinidos");
        }

        int v = valores[indice];
        indice++;
        return v;
    }
}
