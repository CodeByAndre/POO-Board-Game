/* Module and purpose: Game rules: turns, movement, bounce at the end, cell effect and winner.
 * Student name/number and implemented contributions: <André Ferreira> - movement and effects; <André Ferreira> - turns and end of game
 * Collaborator/reviewer and contribution: cross review between both students
 * AI assistance: Claude, initial skeleton - see AI_USE.md
 */
package model;

import java.util.ArrayList;

public class Jogo {
    private final Tabuleiro tabuleiro;
    private final ArrayList<Jogador> jogadores;
    private int atual;
    private boolean terminou;
    private Jogador vencedor;

    public Jogo() {
        this("Jogador 1", "Jogador 2");
    }

    // Sobrecarga: permite escolher os nomes dos jogadores.
    public Jogo(String nome1, String nome2) {
        this.tabuleiro = new Tabuleiro();
        this.jogadores = new ArrayList<>();
        this.jogadores.add(new Jogador(nome1));
        this.jogadores.add(new Jogador(nome2));
        this.atual = 0;
        this.terminou = false;
        this.vencedor = null;
    }

    // O valor vem de qualquer fonte (aleatória ou manual). Valor inválido não altera o jogo.
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

        int destino = alvo;
        String efeito = null;
        Celula celula = tabuleiro.getCelula(alvo);

        if (alvo != ultima && celula.deslocamento() != 0) {
            destino = alvo + celula.deslocamento();
            efeito = celula.getNome();
        }

        if (destino > ultima) {
            destino = ultima;
        }

        if (destino < 1) {
            destino = 1;
        }

        jogador.moverPara(destino);

        if (destino == ultima) {
            terminou = true;
            vencedor = jogador;
        } else {
            atual = (atual + 1) % jogadores.size();
        }

        return new ResultadoJogada(jogador.getNome(), valor, origem, destino, ressalto, efeito);
    }

    // Sobrecarga: obtém o valor diretamente de uma fonte de dado.
    public ResultadoJogada jogar(Dado dado) {
        return jogar(dado.lancar());
    }

    public Jogador getJogadorAtual() {
        return jogadores.get(atual);
    }

    public boolean isTerminado() {
        return terminou;
    }

    // null enquanto o jogo não terminar.
    public Jogador getVencedor() {
        return vencedor;
    }

    // Representação do modelo, por exemplo "(22, 24)".
    public String getPosicoes() {
        return "(" + jogadores.get(0).getPosicao() + ", " + jogadores.get(1).getPosicao() + ")";
    }
}
