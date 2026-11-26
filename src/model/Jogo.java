/* Module and purpose: Game rules: turns, movement, bounce, chained effects, 50-round limit, tie-break and restart.
 * Student name/number and implemented contributions: André Ferreira (30017457) - movement, effects and chaining; Diogo Marques (30016433) - turns, end of game, round limit and restart
 * Collaborator/reviewer and contribution: cross review between both students
 * AI assistance: Claude, initial skeleton - see AI_USE.md
 */
package model;

import java.util.ArrayList;

public class Jogo {
    public static final int MAX_RONDAS = 50;
    public static final int MAX_ENCADEAMENTO = 5;

    private final Tabuleiro tabuleiro;
    private final ArrayList<Jogador> jogadores;
    private int atual;
    private int rondas;
    private boolean terminou;
    private Jogador vencedor;

    public Jogo() {
        this("Jogador 1", "Jogador 2");
    }

    // Sobrecarga: permite escolher os nomes dos jogadores.
    public Jogo(String nome1, String nome2) {
        this(new Tabuleiro(), nome1, nome2);
    }

    // Sobrecarga: permite usar outro tabuleiro (usado nos testes).
    public Jogo(Tabuleiro tabuleiro, String nome1, String nome2) {
        if (tabuleiro == null) {
            throw new IllegalArgumentException("Tabuleiro é obrigatório");
        }

        this.tabuleiro = tabuleiro;
        this.jogadores = new ArrayList<>();
        this.jogadores.add(new Jogador(nome1));
        this.jogadores.add(new Jogador(nome2));
        this.atual = 0;
        this.rondas = 0;
        this.terminou = false;
        this.vencedor = null;
    }

    // O valor vem de qualquer fonte (aleatória, manual ou predefinida). Valor inválido não altera o jogo.
    public ResultadoJogada jogar(int valor) {
        if (terminou) {
            throw new IllegalStateException("O jogo já terminou");
        }

        if (valor < DadoAleatorio.MINIMO || valor > DadoAleatorio.MAXIMO) {
            throw new IllegalArgumentException("Valor do dado inválido: " + valor);
        }

        Jogador jogador = jogadores.get(atual);
        int origem = jogador.getPosicao();
        int ultima = tabuleiro.getTamanho();

        int alvo = origem + valor;
        boolean ressalto = false;

        if (alvo > ultima) {
            alvo = ultima - (alvo - ultima);
            ressalto = true;
        }

        // Efeitos em cadeia: aplica-se o efeito da casa onde se cai, e assim sucessivamente.
        ArrayList<String> efeitos = new ArrayList<>();
        int posicao = alvo;
        int passos = 0;

        while (posicao != ultima && passos < MAX_ENCADEAMENTO) {
            Celula celula = tabuleiro.getCelula(posicao);

            if (celula.deslocamento() == 0) {
                break;
            }

            int seguinte = posicao + celula.deslocamento();

            if (seguinte > ultima) {
                seguinte = ultima;
            }

            if (seguinte < 1) {
                seguinte = 1;
            }

            efeitos.add(celula.getNome() + ": casa " + posicao + " -> " + seguinte);
            posicao = seguinte;
            passos++;
        }

        jogador.moverPara(posicao);

        if (posicao == ultima) {
            terminou = true;
            vencedor = jogador;
        } else {
            avancarTurno();
        }

        return new ResultadoJogada(jogador.getNome(), valor, origem, posicao, ressalto, efeitos);
    }

    // Sobrecarga: obtém o valor diretamente de uma fonte de dado.
    public ResultadoJogada jogar(Dado dado) {
        return jogar(dado.lancar());
    }

    // Passa a vez; ao completar uma ronda verifica o limite de rondas.
    private void avancarTurno() {
        atual = (atual + 1) % jogadores.size();

        if (atual == 0) {
            rondas++;

            if (rondas >= MAX_RONDAS) {
                terminou = true;
                vencedor = maisAvancado();
            }
        }
    }

    // Jogador com a posição maior; null se estiverem empatados.
    private Jogador maisAvancado() {
        Jogador a = jogadores.get(0);
        Jogador b = jogadores.get(1);

        if (a.getPosicao() > b.getPosicao()) {
            return a;
        }

        if (b.getPosicao() > a.getPosicao()) {
            return b;
        }

        return null;
    }

    public void reiniciar() {
        for (Jogador j : jogadores) {
            j.reiniciar();
        }

        atual = 0;
        rondas = 0;
        terminou = false;
        vencedor = null;
    }

    public Jogador getJogadorAtual() {
        return jogadores.get(atual);
    }

    public boolean isTerminado() {
        return terminou;
    }

    // null enquanto o jogo não terminar, ou se terminar empatado.
    public Jogador getVencedor() {
        return vencedor;
    }

    public boolean isEmpate() {
        return terminou && vencedor == null;
    }

    // Número da ronda em curso (começa em 1).
    public int getRondaAtual() {
        return rondas + 1;
    }

    // Representação do modelo, por exemplo "(22, 24)".
    public String getPosicoes() {
        return "(" + jogadores.get(0).getPosicao() + ", " + jogadores.get(1).getPosicao() + ")";
    }
}
