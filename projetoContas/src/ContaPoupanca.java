import static javax.swing.JOptionPane.*;

public class ContaPoupanca extends Conta implements Rendimento{
    private static double taxa = 0.005;
    
    public ContaPoupanca(Cliente cliente, int numero) {
        super(cliente, numero);
    }

    public ContaPoupanca(String id, int numero, double saldo, Cliente cliente) {
        super(id, numero, saldo, cliente);
    }

    public void sacar(double valor) {
        if (valor > 0 && valor <= saldo) {
            this.saldo -= valor;
            showMessageDialog(null, "Saque realizado com sucesso!", "Confirmação", DEFAULT_OPTION);
        }else {
            showMessageDialog(null, "Saldo insuficiente", "Erro", ERROR_MESSAGE);
        }   
    }

    public void depositar(double valor) {
        if (valor <= 0) return;
        this.saldo += valor;
    }

    public double getTaxa() {
        return taxa;
    }

    public void setTaxa(double valor) {
        if (valor < 0) {
            showMessageDialog(null, "A taxa não pode ser negativa", "Erro", ERROR_MESSAGE);
            return;
        }
        taxa = valor;
    }

    public void aplicar() {
        this.saldo += saldo * taxa;
    }
}