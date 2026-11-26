/* Module and purpose: Automated checks for the game: normal movement, turns, bounce at the limits, shared cells, invalid input, end of game, 50-round limit, restart and a complete game. Also runs EfeitosChecks.
 * Student name/number and implemented contributions: Diogo Marques (30016433) - implementation
 * Collaborator/reviewer and contribution: André Ferreira (30017457) - review
 * AI assistance: Claude, help choosing the predefined roll sequences - see AI_USE.md
 */
package tests;

import model.DadoAleatorio;
import model.Jogo;
import model.ResultadoJogada;
import model.Tabuleiro;

public class JogoChecks {

    // Sequência que leva o jogador A até à casa 28 (B fica na 8); A joga a seguir.
    private static final int[] A_ATE_28 = {3, 1, 5, 1, 6, 1, 5, 1};

    public static void main(String[] args) {
        Verificador v = new Verificador();

        EfeitosChecks.correr(v);
        movimento(v);
        limites(v);
        casasPartilhadas(v);
        inputInvalido(v);
        terminacao(v);
        limiteRondas(v);
        reinicio(v);
        jogoCompleto(v);

        if (!v.resumo()) {
            System.exit(1);
        }
    }

    // Junta a sequência base com mais valores no fim.
    private static int[] com(int[] base, int... extra) {
        int[] r = new int[base.length + extra.length];

        for (int i = 0; i < base.length; i++) {
            r[i] = base[i];
        }

        for (int i = 0; i < extra.length; i++) {
            r[base.length + i] = extra[i];
        }

        return r;
    }

    // Sequência com n repetições do par (a, b).
    private static int[] pares(int n, int a, int b) {
        int[] r = new int[2 * n];

        for (int i = 0; i < n; i++) {
            r[2 * i] = a;
            r[2 * i + 1] = b;
        }

        return r;
    }

    private static void movimento(Verificador v) {
        v.secao("Movimento normal e turnos");

        Jogo jogo = new Jogo("A", "B");
        v.caso("Ambos começam na casa 1", "novo jogo", "-", "(1, 1)", jogo.getPosicoes());
        v.caso("Começa o jogador A", "novo jogo", "-", "A", jogo.getJogadorAtual().getNome());

        ResultadoJogada r = jogo.jogar(2);
        v.caso("Avança o valor do dado", "(1, 1)", "A lança 2", 3, r.getDestino());
        v.caso("Resultado guarda a origem", "(1, 1)", "A lança 2", 1, r.getOrigem());
        v.caso("Passa a vez a B", "(3, 1)", "-", "B", jogo.getJogadorAtual().getNome());

        jogo.jogar(5);
        v.caso("B move-se sem mexer em A", "(3, 1)", "B lança 5", "(3, 6)", jogo.getPosicoes());
        v.caso("Volta a vez de A", "(3, 6)", "-", "A", jogo.getJogadorAtual().getNome());
        v.caso("Ronda 2 depois de ambos jogarem", "(3, 6)", "-", 2, jogo.getRondaAtual());
    }

    private static void limites(Verificador v) {
        v.secao("Limites (ressalto no terminal)");

        Jogo jogo = new Jogo("A", "B");
        ResultadoJogada r = EfeitosChecks.jogarTudo(jogo, com(A_ATE_28, 4));
        v.caso("Ressalto: 28 + 4 volta para 28", "A na casa 28", "A lança 4", 28, r.getDestino());
        v.caso("Ressalto fica registado", "A na casa 28", "A lança 4", true, r.isRessalto());
        v.caso("Ressalto não termina o jogo", "A na casa 28", "A lança 4", false, jogo.isTerminado());

        jogo = new Jogo("A", "B");
        r = EfeitosChecks.jogarTudo(jogo, com(A_ATE_28, 6));
        v.caso("Ressalto para casa especial: 28 + 6 -> 26 -> 21", "A na casa 28", "A lança 6",
                21, r.getDestino());
        v.caso("Efeito aplicado depois do ressalto", "A na casa 28", "A lança 6",
                "Falha de energia: casa 26 -> 21", r.getEfeito());

        jogo = new Jogo("A", "B");
        r = EfeitosChecks.jogarTudo(jogo, com(A_ATE_28, 1));
        v.caso("Movimento até à casa 29 sem ressalto", "A na casa 28", "A lança 1", false, r.isRessalto());
    }

    private static void casasPartilhadas(Verificador v) {
        v.secao("Casas partilhadas");

        Jogo jogo = new Jogo("A", "B");
        EfeitosChecks.jogarTudo(jogo, new int[]{2, 2});
        v.caso("Dois peões na mesma casa normal", "(1, 1)", "A 2, B 2", "(3, 3)", jogo.getPosicoes());

        EfeitosChecks.jogarTudo(jogo, new int[]{1, 1});
        v.caso("Dois peões na mesma casa após efeito", "(3, 3)", "A 1, B 1", "(7, 7)", jogo.getPosicoes());

        jogo.jogar(3);
        v.caso("Mover um não mexe no outro", "(7, 7)", "A lança 3", "(10, 7)", jogo.getPosicoes());
    }

    private static void inputInvalido(Verificador v) {
        v.secao("Input inválido");

        Jogo jogo = new Jogo("A", "B");
        jogo.jogar(2);

        v.excecao("Valor 0 é rejeitado", "(3, 1), vez de B", "jogar(0)",
                IllegalArgumentException.class, () -> jogo.jogar(0));
        v.excecao("Valor 7 é rejeitado", "(3, 1), vez de B", "jogar(7)",
                IllegalArgumentException.class, () -> jogo.jogar(7));
        v.caso("Posições não mudam", "(3, 1)", "jogar(0), jogar(7)", "(3, 1)", jogo.getPosicoes());
        v.caso("Vez não muda", "vez de B", "jogar(0), jogar(7)", "B", jogo.getJogadorAtual().getNome());
        v.caso("Ronda não muda", "ronda 1", "jogar(0), jogar(7)", 1, jogo.getRondaAtual());
    }

    private static void terminacao(Verificador v) {
        v.secao("Fim do jogo");

        Jogo jogo = new Jogo("A", "B");
        ResultadoJogada r = EfeitosChecks.jogarTudo(jogo, com(A_ATE_28, 2));
        v.caso("Chegar exatamente à 30", "A na casa 28", "A lança 2", Tabuleiro.TAMANHO, r.getDestino());
        v.caso("Jogo termina", "A na casa 30", "-", true, jogo.isTerminado());
        v.caso("Vencedor é A", "A na casa 30", "-", "A", jogo.getVencedor().getNome());
        v.caso("Não é empate", "A na casa 30", "-", false, jogo.isEmpate());
        v.excecao("Não se joga depois do fim", "jogo terminado", "jogar(3)",
                IllegalStateException.class, () -> jogo.jogar(3));

        Jogo novo = new Jogo("A", "B");
        v.caso("Sem vencedor antes do fim", "novo jogo", "-", null, novo.getVencedor());
    }

    private static void limiteRondas(Verificador v) {
        v.secao("Limite de " + Jogo.MAX_RONDAS + " rondas");

        // Só 1s: os dois peões andam em ciclo 5 -> 6 -> 7 -> 8 -> 9 -> 5 e nunca chegam à 30.
        Jogo jogo = new Jogo("A", "B");
        EfeitosChecks.jogarTudo(jogo, pares(Jogo.MAX_RONDAS - 1, 1, 1));
        jogo.jogar(1);
        v.caso("Ainda não termina a meio da ronda 50", "99 jogadas", "-", false, jogo.isTerminado());

        jogo.jogar(1);
        v.caso("Termina ao fim da ronda 50", "100 jogadas de 1", "-", true, jogo.isTerminado());
        v.caso("Posições iguais", "100 jogadas de 1", "-", "(6, 6)", jogo.getPosicoes());
        v.caso("Empate", "(6, 6)", "-", true, jogo.isEmpate());
        v.caso("Sem vencedor em empate", "(6, 6)", "-", null, jogo.getVencedor());

        // A lança sempre 1, B sempre 2: desempate pelo mais avançado.
        jogo = new Jogo("A", "B");
        EfeitosChecks.jogarTudo(jogo, pares(Jogo.MAX_RONDAS, 1, 2));
        v.caso("Desempate: termina ao fim da ronda 50", "A=1s, B=2s", "100 jogadas", true, jogo.isTerminado());
        v.caso("Posições no fim", "A=1s, B=2s", "100 jogadas", "(6, 5)", jogo.getPosicoes());
        v.caso("Ganha o mais avançado", "(6, 5)", "-", "A", jogo.getVencedor().getNome());
    }

    private static void reinicio(Verificador v) {
        v.secao("Reinício");

        Jogo jogo = new Jogo("A", "B");
        EfeitosChecks.jogarTudo(jogo, new int[]{3, 5, 2});
        jogo.reiniciar();
        v.caso("Posições voltam à casa 1", "(5, 6)", "reiniciar()", "(1, 1)", jogo.getPosicoes());
        v.caso("Volta a ser a vez de A", "vez de B", "reiniciar()", "A", jogo.getJogadorAtual().getNome());
        v.caso("Volta à ronda 1", "ronda 2", "reiniciar()", 1, jogo.getRondaAtual());

        jogo = new Jogo("A", "B");
        EfeitosChecks.jogarTudo(jogo, com(A_ATE_28, 2));
        jogo.reiniciar();
        v.caso("Jogo terminado volta a estar ativo", "A ganhou", "reiniciar()", false, jogo.isTerminado());
        v.caso("Vencedor é apagado", "A ganhou", "reiniciar()", null, jogo.getVencedor());

        ResultadoJogada r = jogo.jogar(3);
        v.caso("Depois de reiniciar as regras são as mesmas", "(1, 1)", "A lança 3", 7, r.getDestino());

        jogo = new Jogo("A", "B");
        EfeitosChecks.jogarTudo(jogo, pares(Jogo.MAX_RONDAS, 1, 1));
        jogo.reiniciar();
        v.caso("Contagem de rondas recomeça após empate", "empate na ronda 50", "reiniciar()",
                false, jogo.isEmpate());
    }

    private static void jogoCompleto(Verificador v) {
        v.secao("Jogo completo");

        // Partida com valores predefinidos, do início ao fim.
        Jogo jogo = new Jogo("Ana", "Rui");
        int[] valores = {3, 1, 5, 1, 6, 1, 5, 1, 2};
        ResultadoJogada r = EfeitosChecks.jogarTudo(jogo, valores);
        v.caso("Partida predefinida termina", "(1, 1)", "3,1,5,1,6,1,5,1,2", true, jogo.isTerminado());
        v.caso("Posições finais", "(1, 1)", "3,1,5,1,6,1,5,1,2", "(30, 8)", jogo.getPosicoes());
        v.caso("Vencedor", "(1, 1)", "3,1,5,1,6,1,5,1,2", "Ana", jogo.getVencedor().getNome());
        v.caso("Última jogada", "(28, 8)", "Ana lança 2", "Ana", r.getJogador());

        // Partida aleatória com semente fixa: tem de terminar dentro do limite de jogadas.
        jogo = new Jogo();
        DadoAleatorio dado = new DadoAleatorio(42);
        int jogadas = 0;
        boolean posicoesValidas = true;

        while (!jogo.isTerminado() && jogadas <= 2 * Jogo.MAX_RONDAS) {
            r = jogo.jogar(dado);
            jogadas++;

            if (r.getDestino() < 1 || r.getDestino() > Tabuleiro.TAMANHO) {
                posicoesValidas = false;
            }
        }

        v.caso("Partida aleatória (semente 42) termina", "(1, 1)", "DadoAleatorio(42)",
                true, jogo.isTerminado());
        v.caso("No máximo " + (2 * Jogo.MAX_RONDAS) + " jogadas", "(1, 1)", "DadoAleatorio(42)",
                true, jogadas <= 2 * Jogo.MAX_RONDAS);
        v.caso("Posições sempre entre 1 e 30", "(1, 1)", "DadoAleatorio(42)", true, posicoesValidas);
    }
}
