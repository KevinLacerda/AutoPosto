class TransacaoVenda {
    private int idTransacao;
    private Cliente cliente;
    private Frentista funcionario;
    private BombaCombustivel bomba;
    private double litros;
    private double valorTotal;
    private double imposto;
    private StatusVenda status;
    private boolean valida;

    public TransacaoVenda(int idTransacao, Cliente cliente, Frentista funcionario, BombaCombustivel bomba, double litros) {
        if (idTransacao <= 0) {
            throw new IllegalArgumentException("ID da transação deve ser maior que zero.");
        }
        if (bomba == null || bomba.getCombustivel() == null) {
            throw new IllegalArgumentException("Bomba ou combustível da transação não pode ser nulo.");
        }
        if (litros <= 0) {
            throw new IllegalArgumentException("Quantidade de litros deve ser maior que zero.");
        }

        this.idTransacao = idTransacao;
        this.cliente = cliente;
        this.funcionario = funcionario;
        this.bomba = bomba;
        this.litros = litros;
        this.status = StatusVenda.ABERTA;
        this.valida = true;

        Combustivel comb = bomba.getCombustivel();
        this.valorTotal = comb.calcularValorTotal(litros);
        this.imposto = comb.calcularImposto(litros);
    }

    public boolean isValida() {
        return valida;
    }

    public void imprimirNotaFiscal() {
        if (!valida) {
            throw new IllegalArgumentException("Não é possível imprimir nota fiscal de transação inválida.");
        }
        System.out.println("\n========================================");
        System.out.println("          AUTOPOSTO IPIRANGA            ");
        System.out.println("========================================");
        System.out.println("Cupom Fiscal / Transação #" + idTransacao);
        System.out.println("Status da Venda: " + status);
        System.out.println("Frentista Responsável: " + (funcionario != null ? funcionario.getNome() : "N/A"));

        if (cliente != null) {
            System.out.println("Cliente: " + cliente.getNome() + " (CPF: " + cliente.getCpf() + ")");
        } else {
            System.out.println("Cliente: Consumidor não identificado (Avulso)");
        }

        System.out.println("Bomba Utilizada: " + bomba.getNumeroBomba());
        System.out.println("Combustível Escolhido: " + bomba.getCombustivel().getNome());
        System.out.printf("Preço do Litro: R$ %.2f%n", bomba.getCombustivel().getPrecoLitro());
        System.out.printf("Quantidade Abastecida: %.2f L%n", litros);
        System.out.printf("Valor Total a Pagar: R$ %.2f%n", valorTotal);
        System.out.printf("Tributos Incidentes (Imposto): R$ %.2f%n", imposto);
        System.out.println("========================================\n");
    }

    public int getIdTransacao() {
        return idTransacao;
    }

    public StatusVenda getStatus() {
        return status;
    }

    public void setStatus(StatusVenda status) {
        if (status != null) {
            this.status = status;
        }
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Frentista getFuncionario() {
        return funcionario;
    }

    public BombaCombustivel getBomba() {
        return bomba;
    }

    public double getLitros() {
        return litros;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public double getImposto() {
        return imposto;
    }
}