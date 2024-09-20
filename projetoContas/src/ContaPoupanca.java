import javax.swing.JOptionPane;

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
            JOptionPane.showMessageDialog(null, "Saque realizado com sucesso!", "Confirmação", JOptionPane.DEFAULT_OPTION);
        }else {
            JOptionPane.showMessageDialog(null, "Saldo insuficiente", "Erro", JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(null, "A taxa não pode ser negativa", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        taxa = valor;
    }

    public void aplicar() {
        this.saldo += saldo * taxa;
    }
}