# Registo de utilização de IA

## 1. Conceção do jogo

- **Ferramenta:** Claude (Anthropic).
- **Objetivo:** escolha do tema, proposta de regras, mapa do tabuleiro, ajuda nas composições das classes.
- **Prompt (resumo):** pedido de ideias de jogo de percurso, depois de regras e mapa para o tema Metro de Lisboa e de estrutura de classes em Java.
- **Output retido:** tema, mapa de efeitos e regras do jogo.
- **Alterações feitas por nós:** Pequenas alterações nas regras do jogo

## 2. Marco de novembro

- **Ferramenta:** Claude (Anthropic).
- **Objetivo:** apoio na implementação das funcionalidades de novembro (consola com escolha do modo e validação do input manual, testes automáticos), na escolha das sequências de lançamentos para os testes, no mockup artístico e na documentação (rules.md, README, tabela de representação).
- **Prompt (resumo):** enviámos o projeto (código e plano de commits) e pedimos os passos seguintes do plano: consola, verificador e checks, mockup, tabela de representação, regras e README.
- **Output retido:** estrutura do `Main` (menu de modo, `r`/`q`, pergunta de nova partida); classe `Verificador`; casos de teste em `EfeitosChecks` e `JogoChecks`; construtores sobrecarregados `Tabuleiro(ArrayList<Celula>)` e `Jogo(Tabuleiro, String, String)` para testar o limite de 5 passos; imagem `docs/mockup.png`; textos de `rules.md`, `README.md` e `docs/REPRESENTACAO.md`.
- **Alterações feitas por nós:** <descrever o que mudaram: mensagens, nomes, casos de teste acrescentados ou retirados, cores do mockup, etc.>
- **Verificação:** compilámos e corremos `java -cp out tests.JogoChecks` (78/78 verificações passaram) e jogámos partidas nos dois modos. Confirmámos à mão os destinos esperados de cada sequência de teste a partir do mapa de efeitos.
