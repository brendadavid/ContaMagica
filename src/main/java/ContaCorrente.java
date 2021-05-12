public class ContaCorrente {
    private String numeroConta;
    private String nomeCorrentista;
    private double saldo = 0;
    private CategoriaEnum categoria = CategoriaEnum.SILVER;

    public ContaCorrente(String nro, String nome) {
        this.numeroConta = nro;
        this.nomeCorrentista = nome;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public String getNomeCorrentista() {
        return nomeCorrentista;
    }

    public double getSaldo() {
        return saldo;
    }

    public CategoriaEnum getCategoria() {
        return this.categoria;
    }

    public boolean deposito(double valor) {
        if (valor <= 0) {
            System.out.println("Valor inválido");
            return false;
        } else {
            if (categoria.equals(CategoriaEnum.SILVER)) {
                this.saldo += valor;
                if (getSaldo() >= 50000) {
                    categoria = CategoriaEnum.GOLD;
                }
            } else if (categoria.equals(CategoriaEnum.GOLD)) {
                this.saldo += valor * 1.01;
                if (getSaldo() >= 200000) {
                    categoria = CategoriaEnum.PLATINUM;
                }
            } else if (getSaldo() >= 200000) {
                this.saldo += valor * 1.025;
            }
            return true;
        }
    }

    public boolean retirada(double valor) {
        if (valor > getSaldo()) {
            System.out.println("Valor requisitado indisponível");
            return false;
        } else {
            this.saldo -= valor;

            if (getSaldo() < 25000 && categoria.equals(CategoriaEnum.GOLD)) {
                categoria = CategoriaEnum.SILVER;

            } else if (getSaldo() < 100000 && categoria.equals(CategoriaEnum.PLATINUM)) {

                this.categoria = CategoriaEnum.GOLD;
            }

            return true;
        }
    }

}


