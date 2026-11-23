import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CentralVendas central = new CentralVendas();

        Combustivel gasolina = new Gasolina(6.20);
        Combustivel etanol = new Etanol(4.30);
        Combustivel diesel = new Diesel(5.90);

        central.cadastrarCombustivel(gasolina);
        central.cadastrarCombustivel(etanol);
        central.cadastrarCombustivel(diesel);

        central.adicionarTanque(new TanqueArmazenamento(gasolina, 10000.0, 5000.0));
        central.adicionarTanque(new TanqueArmazenamento(etanol, 10000.0, 5000.0));
        central.adicionarTanque(new TanqueArmazenamento(diesel, 10000.0, 5000.0));

        BombaCombustivel bomba1 = new BombaCombustivel(1, gasolina);
        BombaCombustivel bomba2 = new BombaCombustivel(2, etanol);
        BombaCombustivel bomba3 = new BombaCombustivel(3, diesel);

        central.adicionarBomba(bomba1);
        central.adicionarBomba(bomba2);
        central.adicionarBomba(bomba3);

        Frentista frentista1 = new Frentista("Murilo", "111.111.111-11", 101);
        Frentista frentista2 = new Frentista("Marcos", "222.222.222-22", 102);
        central.cadastrarFuncionario(frentista1);
        central.cadastrarFuncionario(frentista2);

        Cliente cliente1 = new Cliente("Ana", "333.333.333-01", 400.00);
        Cliente cliente2 = new Cliente("Kevin", "333.333.333-02", 50.00);
        Cliente cliente3 = new Cliente("Airton", "333.333.333-03", 300.00);
        Cliente cliente4 = new Cliente("Camylla", "333.333.333-04", 250.00);
        Cliente cliente5 = new Cliente("Vitoria", "333.333.333-05", 500.00);

        central.cadastrarCliente(cliente1);
        central.cadastrarCliente(cliente2);
        central.cadastrarCliente(cliente3);
        central.cadastrarCliente(cliente4);
        central.cadastrarCliente(cliente5);

        System.out.println("=== CONSULTA INICIAL DE DISPONIBILIDADE DAS BOMBAS ===");
        System.out.print("Digite o número da bomba que deseja verificar (1, 2 ou 3): ");
        int numBombaInicial = scanner.nextInt();
        System.out.println("Bomba " + numBombaInicial + " está disponível? " + (central.isBombaDisponivel(numBombaInicial) ? "Sim" : "Não"));

        System.out.println("\n=== TESTE 1: INICIAR VENDA NA BOMBA 1 (ANA) ===");
        TransacaoVenda venda1 = new TransacaoVenda(1, cliente1, frentista1, bomba1, 20.0);
        central.iniciarAtendimento(venda1);
        System.out.println("Bomba " + bomba1.getNumeroBomba() + " está disponível? " + (central.isBombaDisponivel(1) ? "Sim" : "Não"));

        System.out.println("\n=== TESTE 2: TENTAR OCUPAR A BOMBA 1 JA OCUPADA (AIRTON) ===");
        try {
            TransacaoVenda venda2 = new TransacaoVenda(2, cliente3, frentista2, bomba1, 10.0);
            central.iniciarAtendimento(venda2);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro bloqueado: " + e.getMessage());
        }

        System.out.println("\n=== TESTE 3: TENTAR INICIAR VENDA COM SALDO INSUFICIENTE (KEVIN NA BOMBA 2) ===");
        try {
            TransacaoVenda venda3 = new TransacaoVenda(3, cliente2, frentista2, bomba2, 20.0);
            central.iniciarAtendimento(venda3);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro bloqueado: " + e.getMessage());
        }

        System.out.println("\n=== TESTE 4: CONCLUIR VENDA #1 E LIBERAR BOMBA 1 ===");
        central.concluirVenda(1);
        System.out.println("Bomba " + bomba1.getNumeroBomba() + " está disponível? " + (central.isBombaDisponivel(1) ? "Sim" : "Não"));

        System.out.println("\n=== TESTE 5: ENTRAR NOVAMENTE NA MESMA BOMBA 1 APOS CONCLUSAO (CAMYLLA) ===");
        TransacaoVenda venda4 = new TransacaoVenda(4, cliente4, frentista1, bomba1, 15.0);
        if (central.iniciarAtendimento(venda4)) {
            central.concluirVenda(4);
        }

        System.out.println("\n=== TESTE 6: TESTE DE LIMITE DE 3 ATENDIMENTOS SIMULTANEOS ===");
        TransacaoVenda venda5 = new TransacaoVenda(5, cliente1, frentista1, bomba1, 5.0);
        TransacaoVenda venda6 = new TransacaoVenda(6, cliente3, frentista2, bomba2, 5.0);
        TransacaoVenda venda7 = new TransacaoVenda(7, cliente4, frentista1, bomba3, 5.0);
        TransacaoVenda venda8 = new TransacaoVenda(8, cliente5, frentista2, bomba1, 5.0);

        central.iniciarAtendimento(venda5);
        central.iniciarAtendimento(venda6);
        central.iniciarAtendimento(venda7);
        try {
            central.iniciarAtendimento(venda8);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro bloqueado: " + e.getMessage());
        }

        System.out.println("\n=== TESTE 7: TENTAR DESATIVAR BOMBA E CLIENTE COM VENDA EM ABERTO ===");
        try {
            central.desativarBomba(1);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro bloqueado: " + e.getMessage());
        }
        try {
            central.desativarCliente("333.333.333-01");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro bloqueado: " + e.getMessage());
        }

        central.concluirVenda(5);
        central.concluirVenda(6);
        central.concluirVenda(7);

        System.out.println("\n=== TESTE 8: LISTAR, CONSULTAR, ATUALIZAR E DESATIVAR APOS CONCLUSAO ===");
        central.atualizarCliente("333.333.333-02", "Kevin", 200.00);
        central.desativarCliente("333.333.333-05");
        central.listarClientes();
        central.listarBombas();

        central.exibirResumoVendasConcluidas();
        central.exibirResumoFrentistasEEstoque();

        System.out.println("=== CONSULTA FINAL DE DISPONIBILIDADE DAS BOMBAS ===");
        System.out.print("Digite o número da bomba que deseja verificar (1, 2 ou 3): ");
        int numBombaFinal = scanner.nextInt();
        System.out.println("Bomba " + numBombaFinal + " está disponível? " + (central.isBombaDisponivel(numBombaFinal) ? "Sim" : "Não"));

    }
}