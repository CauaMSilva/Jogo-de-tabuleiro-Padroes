# Jogo de Tabuleiro em Java (Refatorado seguindo Padrões)

Projeto de **jogo de tabuleiro desenvolvido em Java**, refatorado com foco em **padrões de projeto**, **boas práticas de POO** e **arquitetura limpa**.  
O sistema implementa toda a lógica do jogo, incluindo tipos de jogadores, regras do tabuleiro e punições aplicadas ao cair em casas específicas.

## Processo de Refatoração
Este projeto passou por uma refatoração estrutural com os seguintes objetivos:
- Aplicar padrões de projeto adequados ao domínio do jogo
- Melhorar a organização e legibilidade do código
- Reduzir acoplamento e aumentar coesão
- Facilitar manutenção, testes e evolução do sistema
- Separar claramente responsabilidades

## Padrões e Princípios Aplicados
- **Strategy**: comportamento variável dos tipos de jogadores
- **Factory**: criação de jogadores de forma centralizada
- **Single Responsibility Principle (SRP)**
- **Open/Closed Principle (OCP)**
- **Encapsulamento e abstração**

## Funcionalidades
- Sistema de turnos padronizado
- Movimentação de jogadores no tabuleiro
- Diferentes tipos de jogadores com regras próprias
- Casas especiais com lógica encapsulada
- Aplicação automática de punições
- Controle do estado do jogo
- Validações de jogadas e regras

## Tipos de Jogadores
Os jogadores são modelados por abstrações, permitindo:
- Reutilização de comportamentos comuns
- Clareza na aplicação das regras do jogo

## Tabuleiro e Regras
O tabuleiro é composto por casas que encapsulam suas próprias regras.  
Cada tipo de casa é responsável por executar seu efeito, promovendo:
- Baixo acoplamento
- Fácil adição de novas regras
- Código mais legível e organizado

## Tecnologias Utilizadas
- Java
- Programação Orientada a Objetos
- Padrões de Projeto

## ▶️ Como Executar
1. Clone o repositório:
```bash
git clone https://github.com/CauaMSilva/Jogo-de-tabuleiro-Padroes
```
2. Abra o projeto em uma IDE Java

3. Execute a classe Main.java

4. Siga as instruções exibidas no terminal
