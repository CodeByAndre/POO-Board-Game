# UML inicial - Metro de Lisboa

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
        +getTamanho() int
        +getCelula(numero int) Celula
    }

    class Jogador {
        -nome String
        -posicao int
        +getNome() String
        +getPosicao() int
        +moverPara(novaPosicao int) boolean
    }

    class ResultadoJogada {
        -jogador String
        -valor int
        -origem int
        -destino int
        -ressalto boolean
        -efeito String
    }

    class Jogo {
        -atual int
        -terminou boolean
        +Jogo()
        +Jogo(nome1 String, nome2 String)
        +jogar(valor int) ResultadoJogada
        +jogar(dado Dado) ResultadoJogada
        +isTerminado() boolean
        +getVencedor() Jogador
        +getPosicoes() String
    }

    class Main {
        +main(args String[])
    }

    Dado <|.. DadoAleatorio
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
```

## Notas

- `Dado` é uma interface: o jogo aceita qualquer fonte de valores (aleatória agora; manual e com valores predefinidos nos testes, em novembro).
- `Celula` é abstrata e `deslocamento()` é redefinido em cada subclasse (herança e polimorfismo).
- `Jogo` contém as regras e não escreve nada no ecrã. Toda a interação com o utilizador está em `Main` (consola separada das regras).
- Sobrecargas: `DadoAleatorio()` / `DadoAleatorio(long)`, `Jogo()` / `Jogo(String, String)` e `jogar(int)` / `jogar(Dado)`.
