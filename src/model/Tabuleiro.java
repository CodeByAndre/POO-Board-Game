/* Module and purpose: Board with 30 numbered cells (1..30) and the Metro map.
 * AI assistance: Claude, initial skeleton - see AI_USE.md
 */
package model;

import java.util.ArrayList;

public class Tabuleiro {
    public static final int TAMANHO = 30;

    private final ArrayList<Celula> celulas;

    public Tabuleiro() {
        celulas = new ArrayList<>();

        for (int n = 1; n <= TAMANHO; n++) {
            if (n == 4) {
                celulas.add(new CelulaAvanco(n, "Comboio direto", 3));
            } else if (n == 9) {
                celulas.add(new CelulaRecuo(n, "Avaria na linha", 4));
            } else if (n == 12) {
                celulas.add(new CelulaAvanco(n, "Correspondência", 5));
            } else if (n == 16) {
                celulas.add(new CelulaRecuo(n, "Greve", 3));
            } else if (n == 22) {
                celulas.add(new CelulaAvanco(n, "Linha expresso", 4));
            } else if (n == 26) {
                celulas.add(new CelulaRecuo(n, "Falha de energia", 5));
            } else {
                celulas.add(new CelulaNormal(n, "Estação " + n));
            }
        }
    }

    public int getTamanho() {
        return celulas.size();
    }

    public Celula getCelula(int numero) {
        if (numero < 1 || numero > celulas.size()) {
            throw new IllegalArgumentException("Casa inexistente: " + numero);
        }

        return celulas.get(numero - 1);
    }
}
