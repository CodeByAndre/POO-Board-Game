/* Module and purpose: Minimal check runner for the automated tests: prints initial state, input, expected and obtained value, and OK/FALHOU.
 * Student name/number and implemented contributions: André Ferreira (30017457) - implementation
 * Collaborator/reviewer and contribution: Diogo Marques (30016433) - review
 * AI assistance: Claude, initial skeleton - see AI_USE.md
 */
package tests;

public class Verificador {
    private int total;
    private int falhas;

    public Verificador() {
        this.total = 0;
        this.falhas = 0;
    }

    // Compara o valor esperado com o obtido e regista o resultado.
    public void caso(String nome, String inicial, String entrada, Object esperado, Object obtido) {
        total++;
        boolean ok = (esperado == null) ? obtido == null : esperado.equals(obtido);

        if (!ok) {
            falhas++;
        }

        System.out.println((ok ? "[OK]     " : "[FALHOU] ") + nome);
        System.out.println("         inicial: " + inicial + " | entrada: " + entrada);
        System.out.println("         esperado: " + esperado + " | obtido: " + obtido);
    }

    // Verifica que a ação lança uma exceção do tipo indicado.
    public void excecao(String nome, String inicial, String entrada,
                        Class<? extends Exception> tipo, Runnable acao) {
        String obtido;

        try {
            acao.run();
            obtido = "nenhuma exceção";
        } catch (Exception e) {
            obtido = e.getClass().getSimpleName();
        }

        caso(nome, inicial, entrada, tipo.getSimpleName(), obtido);
    }

    public void secao(String titulo) {
        System.out.println();
        System.out.println("--- " + titulo + " ---");
    }

    public int getTotal() {
        return total;
    }

    public int getFalhas() {
        return falhas;
    }

    // Imprime o resumo e devolve true se não houve falhas.
    public boolean resumo() {
        System.out.println();
        System.out.println("Resultado: " + (total - falhas) + "/" + total + " verificações passaram"
                + (falhas == 0 ? "." : " (" + falhas + " falharam)."));
        return falhas == 0;
    }
}
