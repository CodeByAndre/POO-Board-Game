# Tabela de representação - Metro de Lisboa

Relação entre o conceito do jogo, a forma como aparece na consola, a parte do modelo (Java) que o implementa e o mockup artístico (`docs/mockup.png`).

| Conceito do jogo | Consola | Modelo (Java) | Mockup artístico |
| --- | --- | --- | --- |
| Tabuleiro de 30 estações | Números de casa 1 a 30 nas mensagens | `Tabuleiro` com `ArrayList<Celula>` (`TAMANHO = 30`) | Linha de metro em serpentina com 30 estações |
| Partida (casa 1) | `Posições (1, 1)` no início | `Jogador.POSICAO_INICIAL = 1` | Bloco escuro "PARTIDA" com os dois peões ao lado |
| Terminal (casa 30) | `Vencedor: <nome>` | `Jogo.jogar` termina quando a posição é `getTamanho()` | Bloco escuro "TERMINAL" |
| Estação normal | Só `Fica na casa N` | `CelulaNormal`, `deslocamento() = 0` | Círculo branco com contorno escuro |
| Estação que faz avançar (4, 12, 22) | `Efeito: Comboio direto: casa 4 -> 7` | `CelulaAvanco`, `deslocamento() > 0` | Círculo verde com etiqueta "+3 → casa 7" |
| Estação que faz recuar (9, 16, 26) | `Efeito: Greve: casa 16 -> 13` | `CelulaRecuo`, `deslocamento() < 0` | Círculo vermelho com etiqueta "−3 → casa 13" |
| Efeitos encadeados (máx. 5) | Uma linha `Efeito:` por cada passo | Ciclo em `Jogo.jogar`, `MAX_ENCADEAMENTO = 5`, lista em `ResultadoJogada.getEfeitos()` | Etiqueta da 22 aponta para a 26, que também é especial |
| Ressalto no terminal | `Passou do terminal e voltou atrás` | `Jogo.jogar`: `alvo = ultima - (alvo - ultima)`; `ResultadoJogada.isRessalto()` | Regra escrita no rodapé |
| Dois jogadores / peões | `Vez de <nome>` e `Posições (a, b)` | Dois `Jogador` em `Jogo`, `getJogadorAtual()`, `getPosicoes()` | Peões J1 (roxo) e J2 (laranja) |
| Casas partilhadas | Dois números iguais, ex. `(7, 7)` | Cada `Jogador` guarda a sua posição; não há bloqueio | Os peões podem ficar lado a lado na mesma estação |
| Dado | `<nome> lançou N` | Interface `Dado`: `DadoAleatorio`, `DadoManual`, `DadoPredefinido` | Não representado (ação do jogador) |
| Modo de lançamento | Menu `1 - Dado aleatório / 2 - Valor introduzido manualmente` | `Main` escolhe a implementação de `Dado`; as regras são sempre as de `Jogo` | Não representado |
| Limite de 50 rondas | `Ronda N/50`; no fim `Limite de 50 rondas atingido` | `Jogo.MAX_RONDAS`, `getRondaAtual()`, `isEmpate()` | Regra escrita no rodapé |
| Reiniciar | `r` durante a partida; `Jogar outra vez? (s/n)` no fim | `Jogo.reiniciar()` e `Jogador.reiniciar()` | Peões voltam à PARTIDA |

## Comparação entre o mockup e o tabuleiro implementado

- O mockup usa exatamente o mapa de `Tabuleiro`: as mesmas casas especiais (4, 9, 12, 16, 22, 26), os mesmos nomes e os mesmos deslocamentos.
- As cores das linhas (azul, amarelo, verde, vermelho) são só decoração inspirada no metro e não têm significado nas regras; na consola não existem.
- No mockup o percurso é em serpentina para caber numa folha. No modelo o tabuleiro é uma lista linear de 1 a 30.
- O mockup mostra o destino de cada efeito ("→ casa 7"). Na consola o mesmo aparece só quando o efeito acontece (`casa 4 -> 7`).
- O encadeamento 22 → 26 → 21 está no mockup porque a etiqueta da 22 aponta para uma casa vermelha. Na consola aparecem duas linhas `Efeito:`.
- Nomes dos jogadores, ronda atual e modo do dado só existem na consola, porque dependem da partida.
