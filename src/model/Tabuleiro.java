/* Module and purpose: Board with 30 numbered cells (1..30) and the Metro map; overload with custom cells for the checks.
 * Student name/number and implemented contributions: Diogo Marques (30016433) - implementation; André Ferreira (30017457) - custom board constructor
 * Collaborator/reviewer and contribution: André Ferreira (30017457) / Diogo Marques (30016433) - cross review
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

    // Sobrecarga: tabuleiro com casas à escolha (usado nos testes, ex.: limite de encadeamento).
    public Tabuleiro(ArrayList<Celula> casas) {
        if (casas == null || casas.size() < 2) {
            throw new IllegalArgumentException("O tabuleiro precisa de pelo menos 2 casas");
        }

        for (int i = 0; i < casas.size(); i++) {
            if (casas.get(i).getNumero() != i + 1) {
                throw new IllegalArgumentException("As casas têm de estar numeradas de 1 a n, por ordem");
            }
        }

        celulas = new ArrayList<>(casas);
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
