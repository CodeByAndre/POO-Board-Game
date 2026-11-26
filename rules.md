# Regras - Metro de Lisboa

Jogo de percurso para **2 jogadores**. Cada jogador é um passageiro que tem de chegar ao terminal da linha.

## Tabuleiro

- 30 estações numeradas de 1 (Partida) a 30 (Terminal).
- Os dois peões começam na casa 1.
- Vários peões podem estar na mesma casa (não há bloqueios nem capturas).

## Mapa de efeitos

| Casa | Nome | Tipo | Efeito | Destino |
| --- | --- | --- | --- | --- |
| 4 | Comboio direto | Avanço | +3 | 7 |
| 9 | Avaria na linha | Recuo | -4 | 5 |
| 12 | Correspondência | Avanço | +5 | 17 |
| 16 | Greve | Recuo | -3 | 13 |
| 22 | Linha expresso | Avanço | +4 | 26 (encadeia: 26 -> 21) |
| 26 | Falha de energia | Recuo | -5 | 21 |
| restantes | Estação N | Normal | sem efeito | - |

## Turno

1. O jogador da vez lança o dado (valor de 1 a 6).
   - **Modo aleatório:** o programa gera o valor.
   - **Modo manual:** o utilizador escreve o valor. Valores fora de 1..6 ou texto que não seja um número são rejeitados, aparece uma mensagem e o jogo **não muda** (posições, vez e ronda ficam iguais).
   - Os dois modos usam exatamente as mesmas regras (classe `Jogo`).
2. O peão avança tantas casas como o valor do dado.
3. **Ressalto:** se o movimento passar da casa 30, o peão recua as casas a mais. Exemplo: na casa 28 com um 4 -> 32 -> fica na 28.
4. **Efeitos:** se o peão cair numa casa especial, aplica-se o efeito. Se o destino também for especial, aplica-se o efeito seguinte, e assim sucessivamente (**efeitos encadeados**), até um máximo de **5 passos** por jogada. Um efeito nunca leva o peão para fora do tabuleiro (fica entre 1 e 30).
5. Se não houver vencedor, a vez passa ao outro jogador.

## Fim do jogo

- Ganha quem chegar **exatamente** à casa 30 (diretamente ou depois de um efeito).
- **Limite de 50 rondas:** uma ronda acaba quando os dois jogadores jogaram. Se ao fim da ronda 50 ninguém chegou à 30, ganha o jogador mais avançado. Se estiverem na mesma casa, é **empate**.

## Reiniciar e sair

- Durante a partida: `r` reinicia (os dois peões voltam à casa 1, volta a ser a vez do jogador 1 e a contagem de rondas recomeça); `q` sai.
- No fim da partida o programa pergunta se querem jogar outra vez e volta a pedir o modo de lançamento.
