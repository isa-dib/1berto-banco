import java.util.UUID;
import java.time.LocalDate;

public abstract class Cliente {

    private String nome;
    private final String id;
    private final LocalDate dataCadastro;
    
    public Cliente() {
        this.id = UUID.randomUUID().toString();
        this.dataCadastro = LocalDate.now();
    }

    public Cliente(String nome) {
        this();
        this.nome = nome;
    }

    public Cliente(String id, String nome, LocalDate dataCadastro) {
        this.id = id;
        this.nome = nome;
        this.dataCadastro = dataCadastro;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public String toString() {
        return this.id + ";" + this.nome + ";" + this.dataCadastro;
    }

    public boolean equals(Object obj) {
        return obj != null
            && (obj instanceof Cliente)
            && ((Cliente) obj).id.equals(this.id);
    }

    public int hashCode() {
        return id.hashCode();
    }
}