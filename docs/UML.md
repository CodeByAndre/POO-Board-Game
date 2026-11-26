# UML - Metro de Lisboa (marco de novembro)

```mermaid
classDiagram
    class Dado {
        <<interface>>
        +lancar() int
    }

    class DadoAleatorio {
        +MINIMO int
        +MAXIMO int
        -random Random
        +DadoAleatorio()
        +DadoAleatorio(semente long)
        +lancar() int
    }

    class DadoManual {
        -valor int
        -definido boolean
        +definir(valor int) boolean
        +lancar() int
    }

    class DadoPredefinido {
        -valores int[]
        -indice int
        +DadoPredefinido(valores int[])
        +lancar() int
    }

    class Celula {
        <<abstract>>
        -numero int
        -nome String
        #Celula(numero int, nome String)
        +getNumero() int
        +getNome() String
        +deslocamento()* int
    }

    class CelulaNormal {
        +deslocamento() int
    }

    class CelulaAvanco {
        -casas int
        +deslocamento() int
    }

    class CelulaRecuo {
        -casas int
        +deslocamento() int
    }

    class Tabuleiro {
        +TAMANHO int
        -celulas ArrayList~Celula~
        +Tabuleiro()
        +Tabuleiro(casas ArrayList~Celula~)
        +getTamanho() int
        +getCelula(numero int) Celula
    }

    class Jogador {
        -nome String
        -posicao int
        +getNome() String
        +getPosicao() int
        +moverPara(novaPosicao int) boolean
        +reiniciar()
    }

    class ResultadoJogada {
        -jogador String
        -valor int
        -origem int
        -destino int
        -ressalto boolean
        -efeitos ArrayList~String~
        +getEfeitos() ArrayList~String~
        +getEfeito() String
    }

    class Jogo {
        +MAX_RONDAS int
        +MAX_ENCADEAMENTO int
        -atual int
        -rondas int
        -terminou boolean
        -vencedor Jogador
        +Jogo()
        +Jogo(nome1 String, nome2 String)
        +Jogo(tabuleiro Tabuleiro, nome1 String, nome2 String)
        +jogar(valor int) ResultadoJogada
        +jogar(dado Dado) ResultadoJogada
        +reiniciar()
        +getJogadorAtual() Jogador
        +isTerminado() boolean
        +isEmpate() boolean
        +getVencedor() Jogador
        +getRondaAtual() int
        +getPosicoes() String
    }

    class Main {
        +main(args String[])
    }

    class Verificador {
        -total int
        -falhas int
        +caso(nome, inicial, entrada, esperado, obtido)
        +excecao(nome, inicial, entrada, tipo, acao)
        +resumo() boolean
    }

    class EfeitosChecks {
        +main(args String[])
        +correr(v Verificador)
    }

    class JogoChecks {
        +main(args String[])
    }

    Dado <|.. DadoAleatorio
    Dado <|.. DadoManual
    Dado <|.. DadoPredefinido
    Celula <|-- CelulaNormal
    Celula <|-- CelulaAvanco
    Celula <|-- CelulaRecuo
    Tabuleiro "1" *-- "30" Celula
    Jogo "1" *-- "1" Tabuleiro
    Jogo "1" *-- "2" Jogador
    Jogo ..> Dado : usa
    Jogo ..> ResultadoJogada : devolve
    Main ..> Jogo : usa
    Main ..> Dado : usa
    EfeitosChecks ..> Verificador : usa
    JogoChecks ..> Verificador : usa
    JogoChecks ..> EfeitosChecks : corre
    JogoChecks ..> Jogo : testa
    EfeitosChecks ..> DadoPredefinido : usa
```

## Notas

- `Dado` é uma interface: o jogo aceita qualquer fonte de valores (aleatória agora; manual e com valores predefinidos nos testes, em novembro).
- `Celula` é abstrata e `deslocamento()` é redefinido em cada subclasse (herança e polimorfismo).
- `Jogo` contém as regras e não escreve nada no ecrã. Toda a interação com o utilizador está em `Main` (consola separada das regras).
- Sobrecargas: `DadoAleatorio()` / `DadoAleatorio(long)`, `Tabuleiro()` / `Tabuleiro(ArrayList<Celula>)`, `Jogo()` / `Jogo(String, String)` / `Jogo(Tabuleiro, String, String)` e `jogar(int)` / `jogar(Dado)`.
- `DadoManual` valida o valor (1 a 6) em `definir`; um valor inválido devolve `false` e não altera nada. `DadoPredefinido` devolve uma sequência fixa e é usado nos testes.
- O tabuleiro com casas à escolha só é usado nos testes, para provar que o encadeamento pára ao fim de 5 passos (o mapa real não tem ciclos).
- `Tabuleiro "1" *-- "30" Celula` refere-se ao tabuleiro do jogo; o de teste pode ter outro tamanho.
