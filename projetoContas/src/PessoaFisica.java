import java.time.LocalDate;

public class PessoaFisica extends Cliente {
    private final String cpf;

    public PessoaFisica(String nome, String cpf) {
        super(nome);
        this.cpf = cpf;
    }

    public PessoaFisica(String id, String nome, LocalDate dataCadastro, String cpf) {
        super(id, nome, dataCadastro);
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public String toString(){
        return super.toString() + ";" + this.cpf;
    }
    
    public boolean equals(Object obj) {
        return super.equals(obj) && (obj instanceof PessoaFisica);
    }
}
