import static javax.swing.JOptionPane.*;

public class ContaInvestimento extends Conta implements Rendimento{
    private int quantidadeDepositos = 0;
    private static double taxa = 0.02;

    public ContaInvestimento(Cliente cliente, int numero) {
        super(cliente, numero);
    }

    public ContaInvestimento(String id, int numero, double saldo, Cliente cliente, int quantidadeDepositos) {
        super(id, numero, saldo, cliente);
        this.quantidadeDepositos = quantidadeDepositos;
    }

    public int getQuantidadeDepositos() {
        return quantidadeDepositos;
    }

    public String toString(){
        return super.toString() + ";" + this.quantidadeDepositos;
    }

    public void sacar(double valor) {
        if (valor > 0 && valor <= saldo && (quantidadeDepositos%3) == 0) {
            this.saldo -= valor;
            showMessageDialog(null, "Saque realizado com sucesso!", "Confirmação", DEFAULT_OPTION);
        }else{
            showMessageDialog(null, "Não é possível realizar saque", "Erro", ERROR_MESSAGE);
        }
    }
    public void depositar(double valor) {
        if (valor <= 0) return;
        this.saldo += valor;   
        this.quantidadeDepositos++;
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
