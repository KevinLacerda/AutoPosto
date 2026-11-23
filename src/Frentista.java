class Frentista {
    private String nome;
    private String cpf;
    private int codigoCracha;

    public Frentista(String nome, String cpf, int codigoCracha) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do frentista não pode ser vazio.");
        }
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF do frentista não pode ser vazio.");
        }
        if (codigoCracha <= 0) {
            throw new IllegalArgumentException("Código do crachá deve ser maior que zero.");
        }
        this.nome = nome;
        this.cpf = cpf;
        this.codigoCracha = codigoCracha;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public int getCodigoCracha() {
        return codigoCracha;
    }
}