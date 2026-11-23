class Gasolina extends Combustivel {
    public Gasolina(double precoLitro) {
        super("Gasolina", precoLitro);
    }

    @Override
    public double calcularValorTotal(double litros) {
        return litros * getPrecoLitro();
    }

    @Override
    public double calcularImposto(double litros) {
        return calcularValorTotal(litros) * 0.25;
    }
}