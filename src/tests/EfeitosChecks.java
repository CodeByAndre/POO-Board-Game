/* Module and purpose: Automated checks for the dice (manual and predefined) and for every special cell, including chained effects and the 5-step limit.
 * Student name/number and implemented contributions: André Ferreira (30017457) - implementation
 * Collaborator/reviewer and contribution: Diogo Marques (30016433) - review
 * AI assistance: Claude, help choosing the predefined roll sequences - see AI_USE.md
 */
package tests;

import java.util.ArrayList;

import model.Celula;
import model.CelulaAvanco;
import model.CelulaNormal;
import model.CelulaRecuo;
import model.DadoManual;
import model.DadoPredefinido;
import model.Jogo;
import model.ResultadoJogada;
import model.Tabuleiro;

public class EfeitosChecks {

    public static void main(String[] args) {
        Verificador v = new Verificador();
        correr(v);

        if (!v.resumo()) {
            System.exit(1);
        }
    }

    public static void correr(Verificador v) {
        dados(v);
        efeitos(v);
        encadeamento(v);
    }

    // Joga a sequência toda e devolve o resultado da última jogada.
    static ResultadoJogada jogarTudo(Jogo jogo, int[] valores) {
        DadoPredefinido dado = new DadoPredefinido(valores);
        ResultadoJogada ultimo = null;

        for (int i = 0; i < valores.length; i++) {
            ultimo = jogo.jogar(dado);
        }

        return ultimo;
    }

    private static void dados(Verificador v) {
        v.secao("Dados");

        DadoManual manual = new DadoManual();
        v.caso("Dado manual aceita 1", "sem valor", "definir(1)", true, manual.definir(1));
        v.caso("Dado manual devolve o valor definido", "valor 1", "lancar()", 1, manual.lancar());
        v.caso("Dado manual aceita 6", "sem valor", "definir(6)", true, manual.definir(6));
        v.caso("Dado manual rejeita 0", "valor 6", "definir(0)", false, manual.definir(0));
        v.caso("Dado manual rejeita 7", "valor 6", "definir(7)", false, manual.definir(7));
        v.caso("Valor inválido não altera o valor anterior", "valor 6", "lancar()", 6, manual.lancar());
        v.excecao("Dado manual sem valor não lança", "sem valor", "lancar()",
                IllegalStateException.class, manual::lancar);

        DadoPredefinido pre = new DadoPredefinido(new int[]{3, 1, 6});
        String seq = pre.lancar() + "," + pre.lancar() + "," + pre.lancar();
        v.caso("Dado predefinido segue a sequência", "[3, 1, 6]", "3 x lancar()", "3,1,6", seq);
        v.excecao("Dado predefinido esgotado", "sequência gasta", "lancar()",
                IllegalStateException.class, pre::lancar);
        v.excecao("Dado predefinido rejeita valores fora de 1..6", "-", "new DadoPredefinido([2, 9])",
                IllegalArgumentException.class, () -> new DadoPredefinido(new int[]{2, 9}));
        v.excecao("Dado predefinido rejeita sequência vazia", "-", "new DadoPredefinido([])",
                IllegalArgumentException.class, () -> new DadoPredefinido(new int[]{}));

        Jogo jogo = new Jogo("A", "B");
        DadoManual m = new DadoManual();
        m.definir(9);
        v.excecao("Jogo com dado manual sem valor válido", "(1, 1)", "definir(9), jogar(dado)",
                IllegalStateException.class, () -> jogo.jogar(m));
        v.caso("Estado não muda após input inválido", "(1, 1)", "-", "(1, 1)", jogo.getPosicoes());
    }

    private static void efeitos(Verificador v) {
        v.secao("Casas especiais (um efeito)");

        // Casa 4 - Comboio direto (+3)
        Jogo jogo = new Jogo("A", "B");
        ResultadoJogada r = jogarTudo(jogo, new int[]{3});
        v.caso("Casa 4 avança para 7", "(1, 1)", "A lança 3", 7, r.getDestino());
        v.caso("Casa 4 regista o efeito", "(1, 1)", "A lança 3",
                "Comboio direto: casa 4 -> 7", r.getEfeito());

        // Casa 9 - Avaria na linha (-4)
        jogo = new Jogo("A", "B");
        r = jogarTudo(jogo, new int[]{2, 1, 6});
        v.caso("Casa 9 recua para 5", "A na casa 3", "A lança 6", 5, r.getDestino());
        v.caso("Casa 9 regista o efeito", "A na casa 3", "A lança 6",
                "Avaria na linha: casa 9 -> 5", r.getEfeito());

        // Casa 12 - Correspondência (+5)
        jogo = new Jogo("A", "B");
        r = jogarTudo(jogo, new int[]{5, 1, 6});
        v.caso("Casa 12 avança para 17", "A na casa 6", "A lança 6", 17, r.getDestino());

        // Casa 16 - Greve (-3)
        jogo = new Jogo("A", "B");
        r = jogarTudo(jogo, new int[]{3, 1, 3, 1, 6});
        v.caso("Casa 16 recua para 13", "A na casa 10", "A lança 6", 13, r.getDestino());

        // Casa 26 - Falha de energia (-5)
        jogo = new Jogo("A", "B");
        r = jogarTudo(jogo, new int[]{3, 1, 5, 1, 5, 1, 5});
        v.caso("Casa 26 recua para 21", "A na casa 21", "A lança 5", 21, r.getDestino());
        v.caso("Casa 26 tem um só efeito", "A na casa 21", "A lança 5", 1, r.getEfeitos().size());

        // Casa normal: sem efeito
        jogo = new Jogo("A", "B");
        r = jogarTudo(jogo, new int[]{2});
        v.caso("Casa normal não tem efeito", "(1, 1)", "A lança 2", null, r.getEfeito());
    }

    private static void encadeamento(Verificador v) {
        v.secao("Efeitos encadeados");

        // Casa 22 - Linha expresso (+4) leva à 26 - Falha de energia (-5): 22 -> 26 -> 21.
        Jogo jogo = new Jogo("A", "B");
        ResultadoJogada r = jogarTudo(jogo, new int[]{3, 1, 5, 1, 5});
        v.caso("Casa 22 encadeia com a 26", "A na casa 17", "A lança 5", 21, r.getDestino());
        v.caso("Cadeia 22 -> 26 -> 21 tem 2 efeitos", "A na casa 17", "A lança 5",
                2, r.getEfeitos().size());
        v.caso("Segundo efeito é o da casa 26", "A na casa 17", "A lança 5",
                "Falha de energia: casa 26 -> 21", r.getEfeitos().get(1));

        // Tabuleiro de teste com um ciclo 3 -> 5 -> 3 ...: o encadeamento pára ao fim de 5 passos.
        ArrayList<Celula> casas = new ArrayList<>();
        casas.add(new CelulaNormal(1, "Início"));
        casas.add(new CelulaNormal(2, "Normal"));
        casas.add(new CelulaAvanco(3, "Avança 2", 2));
        casas.add(new CelulaNormal(4, "Normal"));
        casas.add(new CelulaRecuo(5, "Recua 2", 2));

        for (int n = 6; n <= 10; n++) {
            casas.add(new CelulaNormal(n, "Normal"));
        }

        jogo = new Jogo(new Tabuleiro(casas), "A", "B");
        r = jogo.jogar(2);
        v.caso("Ciclo pára ao fim de " + Jogo.MAX_ENCADEAMENTO + " passos",
                "tabuleiro de teste, (1, 1)", "A lança 2", Jogo.MAX_ENCADEAMENTO, r.getEfeitos().size());
        v.caso("Posição final após 5 passos", "tabuleiro de teste, (1, 1)", "A lança 2",
                5, r.getDestino());
        v.caso("Jogo continua após o limite", "tabuleiro de teste", "-", "B",
                jogo.getJogadorAtual().getNome());
    }
}
