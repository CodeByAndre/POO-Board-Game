/* Module and purpose: Console prototype: random rolls, turn feedback and quit. Contains no game rules.
 * Student name/number and implemented contributions: <Aluno 2> - console loop
 * Collaborator/reviewer and contribution: <Aluno 1> - review
 * AI assistance: Claude, initial skeleton - see AI_USE.md
 */
package console;

import java.util.Scanner;

import model.Dado;
import model.DadoAleatorio;
import model.Jogo;
import model.ResultadoJogada;

public class Main {
    public static void main(String[] args) {
        Jogo jogo = new Jogo();
        Dado dado = new DadoAleatorio();
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Metro de Lisboa ===");

        while (!jogo.isTerminado()) {
            System.out.println();
            System.out.println("Posições " + jogo.getPosicoes()
                    + " | Vez de " + jogo.getJogadorAtual().getNome());
            System.out.print("Enter para lançar, q para sair: ");

            if (!scanner.hasNextLine()) {
                return;
            }

            String linha = scanner.nextLine().trim();

            if (linha.equalsIgnoreCase("q")) {
                System.out.println("Jogo terminado.");
                return;
            }

            if (!linha.isEmpty()) {
                System.out.println("Input inválido.");
                continue;
            }

            mostrar(jogo.jogar(dado));
        }

        System.out.println();
        System.out.println("Posições finais " + jogo.getPosicoes());
        System.out.println("Vencedor: " + jogo.getVencedor().getNome());
    }

    private static void mostrar(ResultadoJogada r) {
        System.out.println(r.getJogador() + " lançou " + r.getValor()
                + ": casa " + r.getOrigem());

        if (r.isRessalto()) {
            System.out.println("  Passou do terminal e voltou atrás");
        }

        if (r.getEfeito() != null) {
            System.out.println("  Efeito: " + r.getEfeito());
        }

        System.out.println("  Fica na casa " + r.getDestino());
    }
}
