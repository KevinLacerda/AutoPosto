class TanqueArmazenamento {
    private Combustivel combustivel;
    private double capacidadeMaxima;
    private double estoqueAtual;

    public TanqueArmazenamento(Combustivel combustivel, double capacidadeMaxima, double estoqueAtual) {
        if (combustivel == null) {
            throw new IllegalArgumentException("Combustível do tanque não pode ser nulo.");
        }
        if (capacidadeMaxima <= 0) {
            throw new IllegalArgumentException("Capacidade máxima do tanque deve ser maior que zero.");
        }
        if (estoqueAtual < 0 || estoqueAtual > capacidadeMaxima) {
            throw new IllegalArgumentException("Estoque inicial inválido para a capacidade do tanque.");
        }
        this.combustivel = combustivel;
        this.capacidadeMaxima = capacidadeMaxima;
        this.estoqueAtual = estoqueAtual;
    }

    public boolean verificarEstoque(double litros) {
        return litros > 0 && this.estoqueAtual >= litros;
    }

    public boolean abastecerTanque(double litros) {
        if (litros <= 0) {
            throw new IllegalArgumentException("Quantidade para abastecer o tanque deve ser maior que zero.");
        }
        if ((this.estoqueAtual + litros) > this.capacidadeMaxima) {
            throw new IllegalArgumentException("Abastecimento excede a capacidade máxima do tanque.");
        }
        this.estoqueAtual += litros;
        return true;
    }

    public boolean darBaixaEstoque(double litros) {
        if (litros <= 0) {
            throw new IllegalArgumentException("Quantidade para baixa de estoque deve ser maior que zero.");
        }
        if (!verificarEstoque(litros)) {
            throw new IllegalArgumentException("Estoque insuficiente no tanque para a quantidade solicitada.");
        }
        this.estoqueAtual -= litros;
        return true;
    }

    public Combustivel getCombustivel() {
        return combustivel;
    }

    public double getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public double getEstoqueAtual() {
        return estoqueAtual;
    }
}