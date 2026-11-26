/* Module and purpose: Console: player names, choice of dice mode (random or manual), validated manual input, restart and quit. Contains no game rules.
 * Student name/number and implemented contributions: Diogo Marques (30016433) - console loop, mode choice, manual input validation, restart
 * Collaborator/reviewer and contribution: André Ferreira (30017457) - review
 * AI assistance: Claude, initial skeleton and review - see AI_USE.md
 */
package console;

import java.util.Scanner;

import model.Dado;
import model.DadoAleatorio;
import model.DadoManual;
import model.Jogo;
import model.ResultadoJogada;
import model.Tabuleiro;

public class Main {
    private static final int MODO_ALEATORIO = 1;
    private static final int MODO_MANUAL = 2;

    private static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);

        System.out.println("=== Metro de Lisboa ===");

        String nome1 = pedirNome("Nome do jogador 1", "Jogador 1");
        String nome2 = pedirNome("Nome do jogador 2", "Jogador 2");

        if (nome1 == null || nome2 == null) {
            return;
        }

        Jogo jogo = new Jogo(nome1, nome2);

        boolean continuar = true;

        while (continuar) {
            int modo = pedirModo();

            if (modo == 0) {
                return;
            }

            boolean saiu = jogarPartida(jogo, modo);

            if (saiu) {
                System.out.println("Jogo terminado.");
                return;
            }

            mostrarFim(jogo);
            continuar = perguntarSimNao("Jogar outra vez? (s/n): ");

            if (continuar) {
                jogo.reiniciar();
                System.out.println("Nova partida.");
            }
        }

        System.out.println("Até à próxima!");
    }

    // Devolve true se o utilizador saiu a meio da partida.
    private static boolean jogarPartida(Jogo jogo, int modo) {
        Dado aleatorio = new DadoAleatorio();
        DadoManual manual = new DadoManual();

        while (!jogo.isTerminado()) {
            System.out.println();
            System.out.println("Ronda " + jogo.getRondaAtual() + "/" + Jogo.MAX_RONDAS
                    + " | Posições " + jogo.getPosicoes()
                    + " | Vez de " + jogo.getJogadorAtual().getNome());

            if (modo == MODO_ALEATORIO) {
                System.out.print("Enter para lançar, r para reiniciar, q para sair: ");
            } else {
                System.out.print("Valor do dado (1-6), r para reiniciar, q para sair: ");
            }

            String linha = lerLinha();

            if (linha == null || linha.equalsIgnoreCase("q")) {
                return true;
            }

            if (linha.equalsIgnoreCase("r")) {
                jogo.reiniciar();
                System.out.println("Partida reiniciada. Todos voltam à casa 1.");
                continue;
            }

            if (modo == MODO_ALEATORIO) {
                if (!linha.isEmpty()) {
                    System.out.println("Input inválido. Carregue Enter, r ou q.");
                    continue;
                }

                mostrar(jogo.jogar(aleatorio));
            } else {
                // A validação é feita pelo DadoManual: valor inválido não altera o jogo.
                if (!manual.definir(converter(linha))) {
                    System.out.println("Valor inválido: tem de ser um número inteiro entre "
                            + DadoAleatorio.MINIMO + " e " + DadoAleatorio.MAXIMO + ".");
                    continue;
                }

                mostrar(jogo.jogar(manual));
            }
        }

        return false;
    }

    private static String pedirNome(String pergunta, String predefinido) {
        System.out.print(pergunta + " (Enter = " + predefinido + "): ");
        String linha = lerLinha();

        if (linha == null) {
            return null;
        }

        if (linha.isEmpty()) {
            return predefinido;
        }

        return linha;
    }

    // Devolve 1 (aleatório), 2 (manual) ou 0 se a entrada acabou.
    private static int pedirModo() {
        while (true) {
            System.out.println();
            System.out.println("Modo de lançamento:");
            System.out.println("  1 - Dado aleatório");
            System.out.println("  2 - Valor introduzido manualmente");
            System.out.print("Escolha (1/2): ");

            String linha = lerLinha();

            if (linha == null) {
                return 0;
            }

            int opcao = converter(linha);

            if (opcao == MODO_ALEATORIO || opcao == MODO_MANUAL) {
                return opcao;
            }

            System.out.println("Opção inválida.");
        }
    }

    private static boolean perguntarSimNao(String pergunta) {
        while (true) {
            System.out.print(pergunta);
            String linha = lerLinha();

            if (linha == null || linha.equalsIgnoreCase("n")) {
                return false;
            }

            if (linha.equalsIgnoreCase("s")) {
                return true;
            }

            System.out.println("Responda s ou n.");
        }
    }

    // null quando já não há mais entrada (ex.: fim de ficheiro).
    private static String lerLinha() {
        if (!scanner.hasNextLine()) {
            return null;
        }

        return scanner.nextLine().trim();
    }

    // Converte texto em inteiro; devolve -1 se não for um número.
    private static int converter(String texto) {
        try {
            return Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void mostrar(ResultadoJogada r) {
        System.out.println(r.getJogador() + " lançou " + r.getValor()
                + ": sai da casa " + r.getOrigem());

        if (r.isRessalto()) {
            System.out.println("  Passou do terminal e voltou atrás");
        }

        for (String efeito : r.getEfeitos()) {
            System.out.println("  Efeito: " + efeito);
        }

        System.out.println("  Fica na casa " + r.getDestino());
    }

    private static void mostrarFim(Jogo jogo) {
        System.out.println();
        System.out.println("Posições finais " + jogo.getPosicoes());

        if (jogo.isEmpate()) {
            System.out.println("Limite de " + Jogo.MAX_RONDAS + " rondas atingido: empate.");
        } else if (jogo.getVencedor().getPosicao() < Tabuleiro.TAMANHO) {
            System.out.println("Limite de " + Jogo.MAX_RONDAS + " rondas atingido.");
            System.out.println("Vencedor (mais avançado): " + jogo.getVencedor().getNome());
        } else {
            System.out.println("Vencedor: " + jogo.getVencedor().getNome());
        }
    }
}
