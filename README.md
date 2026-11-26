# Metro de Lisboa - jogo de tabuleiro (POO, UAL 2026-2027)

Jogo de percurso para dois jogadores, em Java, na consola. Tabuleiro de 30 estações com casas que fazem avançar ou recuar, efeitos encadeados, ressalto no terminal e limite de 50 rondas.

**Equipa:** André Ferreira (30017457) e Diogo Marques (30016433)

## Requisitos

Java 25 (versão configurada no IntelliJ). O código também compila com Java 21.

## Estrutura

    src/model/     regras do jogo (Jogo, Tabuleiro, Celula e subclasses, Jogador, Dado e implementações, ResultadoJogada)
    src/console/   interface de consola (Main) - não contém regras
    src/tests/     verificações automáticas (Verificador, EfeitosChecks, JogoChecks)
    docs/          UML, divisão de tarefas, mockup e tabela de representação
    rules.md       regras e mapa de efeitos
    exemplo_consola.txt   exemplo de uma partida na consola

## Compilar

    javac -encoding UTF-8 -d out $(find src -name "*.java")

## Correr

    java -cp out console.Main

No início escolhem-se os nomes e o modo de lançamento:

- `1` - dado aleatório (Enter para lançar)
- `2` - valor introduzido manualmente (1 a 6; valores inválidos são rejeitados sem alterar o jogo)

Durante a partida: `r` reinicia, `q` sai. Ver `exemplo_consola.txt`.

## Testes

    java -cp out tests.JogoChecks

Corre todas as verificações (dados, efeitos, encadeamento, movimento, ressalto, casas partilhadas, input inválido, fim do jogo, limite de 50 rondas, reinício e partidas completas). Para cada caso mostra o estado inicial, a entrada, o valor esperado, o valor obtido e `[OK]` ou `[FALHOU]`. No fim mostra o total, por exemplo:

    Resultado: 78/78 verificações passaram.

Só os dados e efeitos:

    java -cp out tests.EfeitosChecks

Os testes usam `DadoPredefinido` (sequências fixas de lançamentos) e `DadoAleatorio` com semente, por isso são repetíveis.

## Documentação

- Regras e mapa de efeitos: `rules.md`
- Exemplo de consola: `exemplo_consola.txt`
- UML: `docs/UML.md`
- Divisão de tarefas: `docs/TAREFAS.md`
- Mockup artístico: `docs/mockup.png`
- Tabela de representação (conceito / consola / modelo / mockup): `docs/REPRESENTACAO.md`
- Registo de utilização de IA: `AI_USE.md`
