import java.util.ArrayList;

class CentralVendas {
    private ArrayList<Combustivel> combustiveis;
    private ArrayList<BombaCombustivel> bombas;
    private ArrayList<Cliente> clientes;
    private ArrayList<TransacaoVenda> vendasAtivas;
    private ArrayList<TransacaoVenda> vendasConcluidas;
    private ArrayList<TanqueArmazenamento> tanques;
    private ArrayList<Frentista> equipeFuncionarios;
    private final int LIMITE_VAGAS = 3;

    public CentralVendas() {
        this.combustiveis = new ArrayList<>();
        this.bombas = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.vendasAtivas = new ArrayList<>();
        this.vendasConcluidas = new ArrayList<>();
        this.tanques = new ArrayList<>();
        this.equipeFuncionarios = new ArrayList<>();
    }

    public void cadastrarCombustivel(Combustivel combustivel) {
        if (combustivel == null) {
            throw new IllegalArgumentException("Combustível não pode ser nulo.");
        }
        combustiveis.add(combustivel);
    }

    public void adicionarTanque(TanqueArmazenamento tanque) {
        if (tanque == null) {
            throw new IllegalArgumentException("Tanque não pode ser nulo.");
        }
        tanques.add(tanque);
    }

    public void cadastrarFuncionario(Frentista funcionario) {
        if (funcionario == null) {
            throw new IllegalArgumentException("Funcionário não pode ser nulo.");
        }
        equipeFuncionarios.add(funcionario);
    }

    public void cadastrarCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo.");
        }
        if (consultarClientePorCpf(cliente.getCpf()) != null) {
            throw new IllegalArgumentException("Já existe cliente cadastrado com o CPF " + cliente.getCpf());
        }
        clientes.add(cliente);
    }

    public void listarClientes() {
        System.out.println("\n=== LISTA DE CLIENTES ===");
        for (Cliente c : clientes) {
            System.out.printf("Nome: %s | CPF: %s | Saldo: R$ %.2f | Situação: %s%n",
                    c.getNome(), c.getCpf(), c.getSaldo(), c.isAtivo() ? "Ativo" : "Inativo");
        }
    }

    public Cliente consultarClientePorCpf(String cpf) {
        if (cpf == null) {
            return null;
        }
        for (Cliente c : clientes) {
            if (c.getCpf().equals(cpf)) {
                return c;
            }
        }
        return null;
    }

    public boolean atualizarCliente(String cpf, String novoNome, double novoSaldo) {
        Cliente cliente = consultarClientePorCpf(cpf);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente com CPF " + cpf + " não encontrado.");
        }
        if (novoNome == null || novoNome.trim().isEmpty()) {
            throw new IllegalArgumentException("Novo nome do cliente não pode ser vazio.");
        }
        if (novoSaldo < 0) {
            throw new IllegalArgumentException("Novo saldo do cliente não pode ser negativo.");
        }
        cliente.setNome(novoNome);
        cliente.setSaldo(novoSaldo);
        System.out.println("Cliente " + cliente.getNome() + " atualizado com sucesso.");
        return true;
    }

    public boolean desativarCliente(String cpf) {
        Cliente cliente = consultarClientePorCpf(cpf);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não encontrado.");
        }
        if (possuiVendaAtivaDoCliente(cpf)) {
            throw new IllegalArgumentException("Não é possível desativar o cliente " + cliente.getNome() + " pois possui venda em aberto.");
        }
        cliente.setAtivo(false);
        System.out.println("Cliente " + cliente.getNome() + " desativado com sucesso.");
        return true;
    }

    public void adicionarBomba(BombaCombustivel bomba) {
        if (bomba == null) {
            throw new IllegalArgumentException("Bomba não pode ser nula.");
        }
        if (buscarBomba(bomba.getNumeroBomba()) != null) {
            throw new IllegalArgumentException("Já existe bomba cadastrada com o número " + bomba.getNumeroBomba());
        }
        bombas.add(bomba);
    }

    public void listarBombas() {
        System.out.println("\n=== LISTA DE BOMBAS ===");
        for (BombaCombustivel b : bombas) {
            System.out.printf("Bomba #%d | Combustível: %s | Status: %s | Situação: %s%n",
                    b.getNumeroBomba(),
                    b.getCombustivel().getNome(),
                    b.getStatus(),
                    b.isAtiva() ? "Ativa" : "Desativada");
        }
    }

    public BombaCombustivel buscarBomba(int numeroBomba) {
        for (BombaCombustivel b : bombas) {
            if (b.getNumeroBomba() == numeroBomba) {
                return b;
            }
        }
        return null;
    }

    public boolean isBombaDisponivel(int numeroBomba) {
        BombaCombustivel bomba = buscarBomba(numeroBomba);
        return bomba != null && bomba.isDisponivel();
    }

    public boolean consultarDisponibilidadeBomba(int numeroBomba) {
        BombaCombustivel bomba = buscarBomba(numeroBomba);
        if (bomba == null) {
            throw new IllegalArgumentException("Bomba #" + numeroBomba + " não encontrada.");
        }
        boolean disponivel = bomba.isDisponivel();
        String estado = !bomba.isAtiva() ? "DESATIVADA" : (disponivel ? "DISPONIVEL" : "OCUPADO");
        System.out.println("Bomba #" + bomba.getNumeroBomba() + " (" + bomba.getCombustivel().getNome() + ") -> " + estado);
        return disponivel;
    }

    public boolean atualizarStatusBomba(int numeroBomba, StatusBomba novoStatus) {
        BombaCombustivel bomba = buscarBomba(numeroBomba);
        if (bomba == null || novoStatus == null) {
            throw new IllegalArgumentException("Bomba ou status inválido.");
        }
        if (possuiVendaAtivaNaBomba(numeroBomba)) {
            throw new IllegalArgumentException("Não é possível alterar manualmente o status da Bomba #" + numeroBomba + " com venda em aberto.");
        }
        bomba.setStatus(novoStatus);
        System.out.println("Status da Bomba #" + numeroBomba + " atualizado para " + novoStatus + ".");
        return true;
    }

    public boolean desativarBomba(int numeroBomba) {
        BombaCombustivel bomba = buscarBomba(numeroBomba);
        if (bomba == null) {
            throw new IllegalArgumentException("Bomba #" + numeroBomba + " não encontrada.");
        }
        if (bomba.getStatus() == StatusBomba.OCUPADO || possuiVendaAtivaNaBomba(numeroBomba)) {
            throw new IllegalArgumentException("Não é possível desativar a Bomba #" + numeroBomba + " enquanto estiver ocupada ou com venda em aberto.");
        }
        bomba.setAtiva(false);
        System.out.println("Bomba #" + numeroBomba + " desativada com sucesso.");
        return true;
    }

    private boolean frentistaCadastrado(Frentista funcionario) {
        if (funcionario == null) {
            return false;
        }
        for (Frentista f : equipeFuncionarios) {
            if (f == funcionario || f.getCodigoCracha() == funcionario.getCodigoCracha()) {
                return true;
            }
        }
        return false;
    }

    private boolean possuiVendaAtivaDoCliente(String cpf) {
        for (TransacaoVenda v : vendasAtivas) {
            if (v.getCliente() != null && cpf.equals(v.getCliente().getCpf())) {
                return true;
            }
        }
        return false;
    }

    private boolean possuiVendaAtivaNaBomba(int numeroBomba) {
        for (TransacaoVenda v : vendasAtivas) {
            if (v.getBomba() != null && v.getBomba().getNumeroBomba() == numeroBomba) {
                return true;
            }
        }
        return false;
    }

    private TransacaoVenda buscarVendaAtiva(int idTransacao) {
        for (TransacaoVenda v : vendasAtivas) {
            if (v.getIdTransacao() == idTransacao) {
                return v;
            }
        }
        return null;
    }

    private TanqueArmazenamento buscarTanquePorCombustivel(Combustivel combustivel) {
        if (combustivel == null) {
            return null;
        }
        for (TanqueArmazenamento t : tanques) {
            Combustivel combTanque = t.getCombustivel();
            if (combTanque != null && (combTanque == combustivel || combTanque.getNome().equals(combustivel.getNome()))) {
                return t;
            }
        }
        return null;
    }

    public boolean iniciarAtendimento(TransacaoVenda venda) {
        if (venda == null || !venda.isValida() || venda.getStatus() != StatusVenda.ABERTA) {
            throw new IllegalArgumentException("Transação inválida para iniciar atendimento.");
        }

        if (vendasAtivas.size() >= LIMITE_VAGAS) {
            throw new IllegalArgumentException("Limite de " + LIMITE_VAGAS + " bicos/atendimentos simultâneos atingido.");
        }

        if (!frentistaCadastrado(venda.getFuncionario())) {
            throw new IllegalArgumentException("Frentista não cadastrado na equipe.");
        }

        if (!venda.getBomba().isDisponivel()) {
            throw new IllegalArgumentException("Bomba " + venda.getBomba().getNumeroBomba() + " não está disponível.");
        }

        if (venda.getCliente() != null && !venda.getCliente().isAtivo()) {
            throw new IllegalArgumentException("Cliente " + venda.getCliente().getNome() + " está inativo.");
        }

        TanqueArmazenamento tanqueCorrespondente = buscarTanquePorCombustivel(venda.getBomba().getCombustivel());
        if (tanqueCorrespondente == null || !tanqueCorrespondente.verificarEstoque(venda.getLitros())) {
            throw new IllegalArgumentException("Estoque insuficiente no tanque para a quantidade solicitada.");
        }

        if (venda.getCliente() != null && venda.getCliente().getSaldo() < venda.getValorTotal()) {
            throw new IllegalArgumentException("Cliente não possui saldo suficiente.");
        }

        venda.getBomba().setStatus(StatusBomba.OCUPADO);
        vendasAtivas.add(venda);
        System.out.println("Atendimento #" + venda.getIdTransacao() + " iniciado com sucesso na Bomba " + venda.getBomba().getNumeroBomba());
        return true;
    }

    public void concluirVenda(int idTransacao) {
        TransacaoVenda vendaEncontrada = buscarVendaAtiva(idTransacao);
        if (vendaEncontrada == null) {
            throw new IllegalArgumentException("Venda não encontrada nos atendimentos ativos.");
        }

        if (!vendaEncontrada.isValida() || vendaEncontrada.getStatus() != StatusVenda.ABERTA) {
            throw new IllegalArgumentException("Venda não está apta para conclusão.");
        }

        if (!frentistaCadastrado(vendaEncontrada.getFuncionario())) {
            throw new IllegalArgumentException("Frentista não cadastrado na equipe.");
        }

        if (vendaEncontrada.getCliente() != null && !vendaEncontrada.getCliente().isAtivo()) {
            throw new IllegalArgumentException("Cliente " + vendaEncontrada.getCliente().getNome() + " está inativo.");
        }

        TanqueArmazenamento tanque = buscarTanquePorCombustivel(vendaEncontrada.getBomba().getCombustivel());
        if (tanque == null || !tanque.verificarEstoque(vendaEncontrada.getLitros())) {
            throw new IllegalArgumentException("Estoque insuficiente para concluir a venda.");
        }

        if (vendaEncontrada.getCliente() != null && vendaEncontrada.getCliente().getSaldo() < vendaEncontrada.getValorTotal()) {
            throw new IllegalArgumentException("Saldo insuficiente do cliente para concluir a venda.");
        }

        tanque.darBaixaEstoque(vendaEncontrada.getLitros());
        if (vendaEncontrada.getCliente() != null) {
            vendaEncontrada.getCliente().debitarSaldo(vendaEncontrada.getValorTotal());
        }

        vendaEncontrada.setStatus(StatusVenda.CONCLUIDA);
        vendaEncontrada.getBomba().setStatus(StatusBomba.DISPONIVEL);
        vendasAtivas.remove(vendaEncontrada);
        vendasConcluidas.add(vendaEncontrada);

        vendaEncontrada.imprimirNotaFiscal();
    }

    public void exibirResumoVendasConcluidas() {
        System.out.println("\n========================================");
        System.out.println("     RESUMO GERAL DE VENDAS CONCLUIDAS  ");
        System.out.println("========================================");
        double totalLitros = 0;
        double faturamentoTotal = 0;
        double impostoTotal = 0;

        for (TransacaoVenda v : vendasConcluidas) {
            String nomeCliente = (v.getCliente() != null) ? v.getCliente().getNome() : "Avulso";
            String nomeFrentista = (v.getFuncionario() != null) ? v.getFuncionario().getNome() : "N/A";
            System.out.printf("Venda #%d | Frentista: %s | Cliente: %s | %s | %.2f L | Total: R$ %.2f | Imposto: R$ %.2f%n",
                    v.getIdTransacao(),
                    nomeFrentista,
                    nomeCliente,
                    v.getBomba().getCombustivel().getNome(),
                    v.getLitros(),
                    v.getValorTotal(),
                    v.getImposto());
            totalLitros += v.getLitros();
            faturamentoTotal += v.getValorTotal();
            impostoTotal += v.getImposto();
        }

        System.out.println("----------------------------------------");
        System.out.println("Total de Vendas Concluídas: " + vendasConcluidas.size());
        System.out.println("Vendas Ainda em Aberto: " + vendasAtivas.size());
        System.out.printf("Volume Total Vendido: %.2f L%n", totalLitros);
        System.out.printf("Faturamento Total: R$ %.2f%n", faturamentoTotal);
        System.out.printf("Total de Impostos: R$ %.2f%n", impostoTotal);
        System.out.println("========================================\n");
    }

    public void exibirResumoFrentistasEEstoque() {
        System.out.println("\n========================================");
        System.out.println("    RESUMO POR FRENTISTA E TANQUES      ");
        System.out.println("========================================");

        for (Frentista f : equipeFuncionarios) {
            int qtdVendas = 0;
            double litrosVendidos = 0;
            double valorVendido = 0;

            for (TransacaoVenda v : vendasConcluidas) {
                if (v.getFuncionario() != null && v.getFuncionario().getCodigoCracha() == f.getCodigoCracha()) {
                    qtdVendas++;
                    litrosVendidos += v.getLitros();
                    valorVendido += v.getValorTotal();
                }
            }
            System.out.printf("Frentista: %s (Crachá %d) | Vendas: %d | Litros: %.2f L | Total: R$ %.2f%n",
                    f.getNome(), f.getCodigoCracha(), qtdVendas, litrosVendidos, valorVendido);
        }

        System.out.println("----------------------------------------");
        for (TanqueArmazenamento t : tanques) {
            System.out.printf("Tanque %-10s | Estoque Atual: %.2f L%n",
                    t.getCombustivel().getNome(), t.getEstoqueAtual());
        }
        System.out.println("========================================\n");
    }

    public ArrayList<Combustivel> getCombustiveis() {
        return combustiveis;
    }
}