class Diesel extends Combustivel {
    public Diesel(double precoLitro) {
        super("Diesel", precoLitro);
    }

    @Override
    public double calcularValorTotal(double litros) {
        return litros * getPrecoLitro();
    }

    @Override
    public double calcularImposto(double litros) {
        return calcularValorTotal(litros) * 0.15;
    }
}