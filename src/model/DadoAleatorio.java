/* Module and purpose: Dado that returns random values between 1 and 6.
 * Student name/number and implemented contributions: <Aluno 1> - implementation
 * Collaborator/reviewer and contribution: <Aluno 2> - review
 * AI assistance: Claude, initial skeleton - see AI_USE.md
 */
package model;

import java.util.Random;

public class DadoAleatorio implements Dado {
    public static final int MINIMO = 1;
    public static final int MAXIMO = 6;

    private final Random random;

    public DadoAleatorio() {
        this.random = new Random();
    }

    // Sobrecarga: com semente, a sequência é repetível (usado nos testes).
    public DadoAleatorio(long semente) {
        this.random = new Random(semente);
    }

    @Override
    public int lancar() {
        return random.nextInt(MAXIMO - MINIMO + 1) + MINIMO;
    }
}
