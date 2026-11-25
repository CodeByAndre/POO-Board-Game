/* Module and purpose: Dado whose value is chosen by the user; rejects values outside 1..6.
 * Student name/number and implemented contributions: André Ferreira (30017457) - implementation
 * Collaborator/reviewer and contribution: Diogo Marques (30016433) - review
 * AI assistance: Claude, initial skeleton - see AI_USE.md
 */
package model;

public class DadoManual implements Dado {
    private int valor;
    private boolean definido;

    public DadoManual() {
        this.valor = 0;
        this.definido = false;
    }

    // Valor inválido devolve false e não altera o estado.
    public boolean definir(int valor) {
        if (valor < DadoAleatorio.MINIMO || valor > DadoAleatorio.MAXIMO) {
            return false;
        }

        this.valor = valor;
        this.definido = true;
        return true;
    }

    @Override
    public int lancar() {
        if (!definido) {
            throw new IllegalStateException("Nenhum valor definido");
        }

        definido = false;
        return valor;
    }
}
