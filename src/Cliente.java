class Cliente {
    private String nome;
    private String cpf;
    private double saldo;
    private boolean ativo;

    public Cliente(String nome, String cpf, double saldo) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do cliente não pode ser vazio.");
        }
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF do cliente não pode ser vazio.");
        }
        if (saldo < 0) {
            throw new IllegalArgumentException("Saldo inicial do cliente não pode ser negativo.");
        }
        this.nome = nome;
        this.cpf = cpf;
        this.saldo = saldo;
        this.ativo = true;
    }

    public boolean debitarSaldo(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor para débito deve ser maior que zero.");
        }
        if (!this.ativo) {
            throw new IllegalArgumentException("Cliente " + this.nome + " está inativo.");
        }
        if (this.saldo < valor) {
            throw new IllegalArgumentException("Cliente " + this.nome + " não possui saldo suficiente.");
        }
        this.saldo -= valor;
        return true;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome != null && !nome.trim().isEmpty()) {
            this.nome = nome;
        }
    }

    public String getCpf() {
        return cpf;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        if (saldo >= 0) {
            this.saldo = saldo;
        }
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}