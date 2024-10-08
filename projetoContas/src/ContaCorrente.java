import static javax.swing.JOptionPane.*;

public class ContaCorrente extends Conta{
    private double limite = 0;

    public ContaCorrente(Cliente cliente, int numero) {
        super(cliente, numero);
    }

    public ContaCorrente(Cliente cliente, int numero, double limite) {
        super(cliente, numero);
        this.limite = limite;
    }

    public ContaCorrente(String id, int numero, double saldo, Cliente cliente, double limite) {
        super(id, numero, saldo, cliente);
        this.limite = limite;
    }

    public String toString(){
        return super.toString() + ";" + this.limite;
    }

    public double getLimite() {
        return limite;
    }

    public void setLimite(double valor) {
        if (valor < 0) {
            System.err.println("O limite não pode ser negativo");
            return;
        }
        this.limite = valor;
    }
    

    public void sacar(double valor) {
        if (valor > 0 && valor <= (limite + saldo)) {
            this.saldo -= valor;
            showMessageDialog(null, "Saque realizado com sucesso!", "Confirmação", DEFAULT_OPTION);
        } else {
            showMessageDialog(null, "Saldo insuficiente", "Erro", ERROR_MESSAGE);
        }
    }

    public void depositar(double valor) {
        if (valor <= 0) return;
        this.saldo += valor;
    }
}
