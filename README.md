# Sistema de Gestão de Posto de Combustíveis (AutoPosto)
# Equipe:

* **Autores:** KEVIN DE OLIVEIRA LACERDA e EMANUEL ALEXANDRINO CARTACHO 
* **ADS - Programação Orientada a Objetoss, UNIFIP Patos-PB.**

## 1. Descrição do Sistema

O sistema é uma aplicação de gerenciamento operacional voltada para o auxílio administrativo e comercial de um posto de combustíveis, automatizando o controle de estoque, vendas, cadastro de clientes e frentistas, cálculo de valores e tributos por tipo de combustível, emissão de cupom fiscal e geração de relatórios gerenciais.

### Principais Funcionalidades

* **Gestão de Cadastros:** cadastro, listagem, consulta, atualização e desativação lógica de clientes e bombas de combustível, respeitando vínculos com vendas em aberto.

* **Controle de Estoque e Bombas:** gerenciamento de tanques por tipo de combustível, verificação de capacidade máxima, baixa automática na venda e controle de disponibilidade das bombas.

* **Fluxo de Atendimento e Venda:** abertura e conclusão de transações vinculando cliente, frentista, bomba e volume abastecido, com limite de até 3 atendimentos simultâneos.

* **Cálculo Polimórfico de Valores e Tributos:** cálculo do valor total e incidência de impostos específicos para **Gasolina** (25%), **Etanol** (20%) e **Diesel** (15%).

* **Nota Fiscal e Relatórios:** emissão automática de nota fiscal na conclusão da venda e exibição de dois resumos gerais (vendas concluídas/faturamento e desempenho por frentista/estoque dos tanques).

## 2. Estrutura do Projeto e Conceitos de POO Aplicados

Todas as classes residem diretamente no diretório `src`:

* **`Tributavel.java` (Interface):** define o contrato `calcularImposto(double litros)`.

* **`Combustivel.java` (Classe Abstrata):** implementa `Tributavel`, centraliza atributos comuns (`nome`, `precoLitro`) e declara o método abstrato `calcularValorTotal(double litros)`.

* **`Gasolina.java`, `Etanol.java`, `Diesel.java` (Herança e Polimorfismo):** subclasses de `Combustivel` que sobrescrevem (`@Override`) os cálculos de valor e alíquota de imposto.

* **`StatusVenda.java` e `StatusBomba.java` (Enums):** controlam os estados das vendas e das bombas.

* **`Cliente.java`:** armazena dados cadastrais, saldo e situação ativa/inativa, com validação para débito de saldo.

* **`Frentista.java`:** representa os funcionários habilitados a realizar atendimentos.

* **`TanqueArmazenamento.java`:** controla a capacidade máxima, o estoque atual, o reabastecimento e a baixa de litros.

* **`BombaCombustivel.java`:** vincula um bico/bomba a um combustível, controlando sua disponibilidade e ativação.

* **`TransacaoVenda.java`:** relaciona cliente, frentista, bomba, volume, valor total, imposto e status, além de imprimir o cupom fiscal.

* **`CentralVendas.java`:** classe coordenadora que utiliza coleções `ArrayList` e aplica as regras de negócio do sistema.

* **`Main.java`:** ponto de entrada do console responsável por popular os dados iniciais, ler consultas via `Scanner` e executar o roteiro de demonstração.

## 3. Tecnologias Utilizadas

* **Linguagem:** Java (JDK 26)

* **IDE:** IntelliJ IDEA

* **Modelagem:** Diagrama de Classes UML (Mermaid)

## 4. Instruções para Execução

1. Baixe os arquivos do repositório para o seu computador.

2. Abra o **IntelliJ**, clique em **File > Open** e selecione a pasta do projeto.

3. Certifique-se de que um **JDK** válido está configurado em **File > Project Structure > Project**.

4. Abra o arquivo `src/Main.java`.

5. Clique no botão **Run** (ícone de play verde) ao lado do método `main`.

6. Interaja pelo console quando solicitado para consultar a disponibilidade das bombas.

## 5. Roteiro de Uso (Fluxo da Execução)

Ao executar a classe `Main`, o sistema realiza automaticamente a carga inicial (3 combustíveis, 3 tanques de 10.000 L com 5.000 L iniciais, 3 bombas, 2 frentistas e 5 clientes) e segue a sequência abaixo:

 1. **Consulta Inicial de Bomba:** solicita via teclado (`Scanner`) o número de uma bomba (`1`, `2` ou `3`) e informa se está disponível.

 2. **Teste 1 — Início de Venda Válida:** inicia a Venda #1 (cliente Ana, 20 L na Bomba 1) e confirma que a bomba passa para indisponível (`OCUPADO`).

 3. **Teste 2 — Bloqueio de Bomba Ocupada:** tenta iniciar a Venda #2 (cliente Airton) na mesma Bomba 1 já ocupada e bloqueia a operação.

 4. **Teste 3 — Bloqueio por Saldo Insuficiente:** tenta iniciar a Venda #3 (cliente Kevin, com saldo insuficiente) e impede o início do atendimento.

 5. **Teste 4 — Conclusão de Venda e Nota Fiscal:** conclui a Venda #1, debita o saldo do cliente, dá baixa no tanque, imprime o cupom fiscal e libera a Bomba 1.

 6. **Teste 5 — Reutilização da Bomba Liberada:** realiza e conclui a Venda #4 (cliente Camylla) na Bomba 1 após sua liberação.

 7. **Teste 6 — Limite de Atendimentos Simultâneos:** abre 3 vendas simultâneas (Vendas #5, #6 e #7) e bloqueia a quarta tentativa (Venda #8) por atingir o limite máximo de 3 vagas.

 8. **Teste 7 — Proteção de Vínculos na Desativação:** tenta desativar a Bomba 1 e a cliente Ana enquanto possuem vendas em aberto, bloqueando ambas as ações até a conclusão das vendas.

 9. **Teste 8 — Atualização, Desativação e Relatórios:** atualiza o saldo de um cliente, desativa um cliente sem pendências, lista clientes e bombas e exibe os resumos gerais de vendas concluídas, frentistas e estoque atual dos tanques.

10. **Consulta Final de Bomba:** solicita novamente via teclado o número de uma bomba para verificar sua disponibilidade final.

## 6. Regras de Negócio Implementadas

* Uma venda só pode ser iniciada e concluída se houver estoque suficiente no tanque.

* Um atendimento só pode ser iniciado se houver vaga dentro do limite de bicos/atendimentos simultâneos.

* Uma venda não pode iniciar em uma bomba indisponível ou desativada.

* Uma venda não pode ser iniciada ou concluída se o cliente não tiver saldo suficiente ou estiver inativo.

* Um cliente ou bomba não pode ser desativado (nem ter status alterado manualmente) enquanto possuir venda em aberto.

* Uma venda não pode ser concluída se a transação for inválida ou não estiver com status aberto.

* Um tanque não pode ser abastecido acima de sua capacidade máxima nem com quantidade menor ou igual a zero.

* Uma venda não pode ser iniciada ou concluída se o frentista responsável não estiver cadastrado na equipe do posto.

* Uma venda concluída não pode ser iniciada ou concluída novamente, e deve liberar a bomba para disponivel.

## 7. Entrega, Contribuições e Material da Apresentação

* **Diagrama UML:** disponível na pasta de documentação/imagens do repositório (`diagrama-uml.png`).

* **Contribuição Kevin:** definição do escopo e das regras de negócio, modelagem das entidades e relacionamentos (Diagrama UML), implementação da lógica principal de vendas, controle de estoque, validadores na `CentralVendas`, estruturação dos testes na `Main` e elaboração do `README.md`.

* **Contribuição Emanuel:** apoio na definição das regras de negócio, implementação das classes de domínio (hierarquia de combustíveis, tributação, clientes, frentistas, tanques e bombas), revisão das validações de atendimento e elaboração do material de apresentação.

* **Material da apresentação:** slides/roteiro em PDF disponíveis no repositório (`apresentacao-autoposto.pdf`) e demonstração prática do sistema em execução no IntelliJ IDEA.
