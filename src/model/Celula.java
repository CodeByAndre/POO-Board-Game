/* Module and purpose: Abstract board cell; subclasses define the effect via deslocamento().
 * Student name/number and implemented contributions: <Aluno 1> - class design
 * Collaborator/reviewer and contribution: <Aluno 2> - review
 * AI assistance: Claude, initial skeleton - see AI_USE.md
 */
package model;

public abstract class Celula {
    private final int numero;
    private final String nome;

    protected Celula(int numero, String nome) {
        if (numero < 1) {
            throw new IllegalArgumentException("Número de casa inválido");
        }

        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }

        this.numero = numero;
        this.nome = nome.trim();
    }

    public int getNumero() {
        return numero;
    }

    public String getNome() {
        return nome;
    }

    // 0 = sem efeito, positivo = avançar, negativo = recuar.
    public abstract int deslocamento();
}
