class BombaCombustivel {
    private int numeroBomba;
    private Combustivel combustivel;
    private StatusBomba status;
    private boolean ativa;

    public BombaCombustivel(int numeroBomba, Combustivel combustivel) {
        if (numeroBomba <= 0) {
            throw new IllegalArgumentException("Número da bomba deve ser maior que zero.");
        }
        if (combustivel == null) {
            throw new IllegalArgumentException("Combustível da bomba não pode ser nulo.");
        }
        this.numeroBomba = numeroBomba;
        this.combustivel = combustivel;
        this.status = StatusBomba.DISPONIVEL;
        this.ativa = true;
    }

    public boolean isDisponivel() {
        return this.ativa && this.status == StatusBomba.DISPONIVEL;
    }

    public int getNumeroBomba() {
        return numeroBomba;
    }

    public Combustivel getCombustivel() {
        return combustivel;
    }

    public StatusBomba getStatus() {
        return status;
    }

    public void setStatus(StatusBomba status) {
        if (status != null) {
            this.status = status;
        }
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }
}