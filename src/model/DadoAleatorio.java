/* Module and purpose: Dado that returns random values between 1 and 6. */
package model;

import java.util.Random;

public class DadoAleatorio implements Dado {
    public static final int MINIMO = 1;
    public static final int MAXIMO = 6;

    private final Random random;

    public DadoAleatorio() {
        this.random = new Random();
    }

    public DadoAleatorio(long semente) {
        this.random = new Random(semente);
    }

    @Override
    public int lancar() {
        return random.nextInt(MAXIMO - MINIMO + 1) + MINIMO;
    }
}
