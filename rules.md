# Metro de Lisboa - Regras

Dois jogadores percorrem uma linha de 30 estações, da 1 (origem) à 30 (terminal).

## Mapa de efeitos

| Casa | Nome             | Efeito     | Destino |
|-----:|------------------|------------|--------:|
| 4    | Comboio direto   | Avançar 3  | 7       |
| 9    | Avaria na linha  | Recuar 4   | 5       |
| 12   | Correspondência  | Avançar 5  | 17      |
| 16   | Greve            | Recuar 3   | 13      |
| 22   | Linha expresso   | Avançar 4  | 26      |
| 26   | Falha de energia | Recuar 5   | 21      |

As restantes casas são normais.

## Regras

- Ambos começam na casa 1. O jogador 1 joga primeiro e alternam.
- Dado de 1 a 6, manual (validado) ou aleatório. Os dois modos usam a mesma lógica.
- Fim exato: se posição + dado > 30, o peão ressalta (30 - excedente).
- Casas partilhadas: os dois peões podem ocupar a mesma casa, sem efeito extra.
- Encadeamento: os efeitos aplicam-se em cadeia, no máximo 5 passos (ex.: 22 -> 26 -> 21).
- Vitória: quem chegar primeiro à casa 30.
- Terminação: máximo de 50 rondas. Se ninguém chegar, ganha o mais avançado. Posições iguais = empate.
- Input inválido não altera o estado do jogo.

## Modelo

`(22, 24)` = peão do jogador 1 na casa 22 e peão do jogador 2 na casa 24.
