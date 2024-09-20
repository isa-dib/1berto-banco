import java.util.UUID;

public abstract class Conta {
    private final String id;
    private int numero;
    protected double saldo;
    private Cliente cliente;

    public Conta() {
        this.id = UUID.randomUUID().toString();
        this.saldo = 0;
    }

    public Conta(Cliente cliente, int numero) {
        this();
        this.cliente = cliente;
        this.numero = numero;
    }

    public Conta(String id, int numero, double saldo, Cliente cliente) {
        this.id = id;
        this.numero = numero;
        this.saldo = saldo;
        this.cliente = cliente;
    }

    public String getId() {
        return id;
    }

    public double getSaldo() {
        return saldo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public int getNumero() {
        return numero;
    }

    public String toString(){
        return this.id + ";"+ this.numero + ";" + this.saldo + ";" + this.cliente;
    }

    public abstract void sacar(double valor);
    public abstract void depositar(double valor);

    public int hashCode() {
        return id.hashCode();
    }
}
