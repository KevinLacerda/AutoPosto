class Etanol extends Combustivel {
    public Etanol(double precoLitro) {
        super("Etanol", precoLitro);
    }

    @Override
    public double calcularValorTotal(double litros) {
        return litros * getPrecoLitro();
    }

    @Override
    public double calcularImposto(double litros) {
        return calcularValorTotal(litros) * 0.20;
    }
}