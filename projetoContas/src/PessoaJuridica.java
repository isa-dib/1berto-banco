import java.time.LocalDate;

public class PessoaJuridica extends Cliente {
    private final String cnpj;

    public PessoaJuridica(String nome, String cnpj) {
        super(nome);
        this.cnpj = cnpj;
    }

    public PessoaJuridica(String id, String nome, LocalDate dataCadastro, String cnpj) {
        super(id, nome, dataCadastro);
        this.cnpj = cnpj;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String toString(){
        return super.toString() + ";" + this.cnpj;
    }

    public boolean equals(Object obj) {
        return super.equals(obj) && (obj instanceof PessoaJuridica);
    }
    
}
