abstract class Combustivel implements Tributavel {
    private String nome;
    private double precoLitro;

    public Combustivel(String nome, double precoLitro) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do combustível não pode ser vazio.");
        }
        if (precoLitro <= 0) {
            throw new IllegalArgumentException("Preço do litro deve ser maior que zero.");
        }
        this.nome = nome;
        this.precoLitro = precoLitro;
    }

    public abstract double calcularValorTotal(double litros);

    public String getNome() {
        return nome;
    }

    public double getPrecoLitro() {
        return precoLitro;
    }

    public void setPrecoLitro(double precoLitro) {
        if (precoLitro > 0) {
            this.precoLitro = precoLitro;
        }
    }
}