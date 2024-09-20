import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import javax.swing.JOptionPane;


public class Banco{
    //verificar duplicidade de cpf e cnpj (um cliente não pode ter duas contas do mesmo tipo)

    private final static String banco = "Exemplo Bank";
    private int numero = 0;
    public String getBanco() {
        return banco;
    }

    private static Map<String, Cliente> clientes = new HashMap<>();
    private static Map<String, PessoaFisica> clientesPF = new HashMap<>();
    private static Map<String, PessoaJuridica> clientesPJ = new HashMap<>();

    private static Map<String, Conta> contas = new HashMap<>();
    private static Map<String, Conta> contasPoupanca = new HashMap<>();
    private static Map<String, Conta> contasCorrente = new HashMap<>();
    private static Map<String, Conta> contasInvestimento = new HashMap<>();

    public void adicionarCliente(Cliente cliente) {
        clientes.put(cliente.getId(), cliente);
        if (cliente instanceof PessoaFisica pf) clientesPF.put(pf.getCpf(), pf);
        if (cliente instanceof PessoaJuridica pj) clientesPJ.put(pj.getCnpj(), pj);
    }
  
    public void adicionarConta(Conta conta) {
        contas.put(conta.getId(), conta);
        if (conta instanceof ContaPoupanca cp) contasPoupanca.put(cp.getId(), cp);
        if (conta instanceof ContaCorrente cc) contasCorrente.put(cc.getId(), cc);
        if (conta instanceof ContaInvestimento ci) contasInvestimento.put(ci.getId(), ci);
        this.numero++;
    }

    public void listarClientes() {
        ArrayList<Cliente> listaClientes = new ArrayList<>(Banco.clientes.values());
        StringBuilder aux = new StringBuilder();
        listaClientes.forEach(cliente -> aux.append(cliente.getNome()).append(", ").append(cliente.getClass()).append(", ").append(cliente.getDataCadastro()).append("\n"));
        JOptionPane.showMessageDialog(null,aux,"Listagem de Clientes", JOptionPane.DEFAULT_OPTION);
    }

    public void listarContas() {
        ArrayList<Conta> listaContas = new ArrayList<>(Banco.contas.values());
        StringBuilder aux = new StringBuilder();
        listaContas.forEach(conta -> aux.append("Conta ").append(conta.getNumero()).append(", saldo R$").append(conta.getSaldo()).append(", ").append(conta.getClass()).append("\n"));
        JOptionPane.showMessageDialog(null,aux,"Listagem de Contas", JOptionPane.DEFAULT_OPTION);
    }

    public Cliente buscarCliente(String id) {
        return clientes.get(id);
    }

    public PessoaFisica buscarClientePF(String cpf) {
        return clientesPF.get(cpf);
    }

    public PessoaJuridica buscarClientePJ(String cnpj) {
        return clientesPJ.get(cnpj);
    }

    public void removerCliente() {
        Cliente cliente = selecionarCliente();
        if (cliente instanceof PessoaFisica pf) clientesPF.remove(pf.getCpf());
        if (cliente instanceof PessoaJuridica pf) clientesPJ.remove(pf.getCnpj());

        contas.values().removeIf(conta -> conta.getCliente().equals(cliente));
        clientes.remove(cliente.getId());
    }

    public void removerConta() {
        Conta conta = selecionarConta();
        if(conta instanceof ContaPoupanca cp) contasPoupanca.remove(cp.getId());
        if(conta instanceof ContaCorrente cc) contasCorrente.remove(cc.getId());
        if(conta instanceof ContaInvestimento ci) contasInvestimento.remove(ci.getId());
        contas.remove(conta.getId());
        JOptionPane.showMessageDialog(null, "Conta removida com sucesso!", "Confirmação", JOptionPane.DEFAULT_OPTION);
    }

    public boolean verificarDuplicidadeCliente(Cliente cliente){
        if (cliente instanceof PessoaFisica pf) {
            if (clientesPF.containsKey(pf.getCpf())) {
                JOptionPane.showMessageDialog(null, "Cliente já cadastrado!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return true;
            }
        } else if (cliente instanceof PessoaJuridica pj) {
            if (clientesPJ.containsKey(pj.getCnpj())) {
                JOptionPane.showMessageDialog(null, "Cliente já cadastrado!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return true;
            }
        }
        return false;
    }

    public boolean verificarDuplicidadeConta(Conta conta){
        if (conta instanceof ContaPoupanca cp) {
            if (contasPoupanca.containsKey(cp.getId())) {
                JOptionPane.showMessageDialog(null, "Conta já cadastrada!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return true;
            }
        } else if (conta instanceof ContaCorrente cc) {
            if (contasCorrente.containsKey(cc.getId())) {
                JOptionPane.showMessageDialog(null, "Conta já cadastrada!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return true;
            }
        } else if (conta instanceof ContaInvestimento ci) {
            if (contasInvestimento.containsKey(ci.getId())) {
                JOptionPane.showMessageDialog(null, "Conta já cadastrada!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return true;
            }
        }
        return false;
    }

    private Cliente selecionarCliente(){
        String tipoPessoa = JOptionPane.showInputDialog("Digite o tipo do cliente (F|J)");
        boolean isFisica = tipoPessoa.trim().equalsIgnoreCase("f");
        if(isFisica){
            String cpf = JOptionPane.showInputDialog("Digite o CPF do cliente: ");
            Cliente cliente =  buscarClientePF(cpf);
            if(cliente == null)return null;
            return cliente;
        }else{
            String cnpj = JOptionPane.showInputDialog("Digite o CNPJ do cliente: ");
            Cliente cliente =  buscarClientePJ(cnpj);
            if(cliente == null)return null;
            return cliente;
        }
    }

    private Conta selecionarConta(){
        ArrayList<Conta> listaContas = new ArrayList<>(Banco.contas.values());
        String id = JOptionPane.showInputDialog("Digite o número da conta: ");
        int numero = Integer.parseInt(id);
        for (int i = 0; i < listaContas.size(); i++) {
            if(listaContas.get(i).getNumero() == numero){
                Conta conta = listaContas.get(i);
                return conta;
            }
        }
        JOptionPane.showMessageDialog(null, "Conta não encontrada!", "Aviso", JOptionPane.WARNING_MESSAGE);
        return null;
    }

    public void cadastrarCliente(){
        String nome = JOptionPane.showInputDialog("Digite o nome do cliente: ");
        String tipo = JOptionPane.showInputDialog("Digite o tipo do cliente (F|J)");
        boolean isFisica = tipo.trim().equalsIgnoreCase("f");
        if (isFisica) {
            String cpf = JOptionPane.showInputDialog("Digite o CPF do cliente: ");
            Cliente c = new PessoaFisica(nome, cpf);
            if(verificarDuplicidadeCliente(c)== false){
                adicionarCliente(c);
                JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!!", "Confirmação", JOptionPane.DEFAULT_OPTION);
            } 
        } else {
            String cnpj = JOptionPane.showInputDialog("Digite o CNPJ do cliente: ");
            Cliente c = new PessoaJuridica(nome, cnpj);
            if(verificarDuplicidadeCliente(c)== false){
                adicionarCliente(c);
                JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!!", "Confirmação", JOptionPane.DEFAULT_OPTION);
            }
        }
    }

    public void cadastrarConta(){
        Cliente cliente = selecionarCliente();
        int numero = this.numero;
        String tipo = JOptionPane.showInputDialog("Digite o tipo da conta: \n( P | C | I )");
        boolean isPoupanca = tipo.trim().equalsIgnoreCase("p");
        boolean isCorrente = tipo.trim().equalsIgnoreCase("c");
        boolean isInvestimento = tipo.trim().equalsIgnoreCase("i");
        if (isPoupanca) {
            Conta c = new ContaPoupanca(cliente, numero);
            if(verificarDuplicidadeConta(c)== false){
                adicionarConta(c);
                JOptionPane.showMessageDialog(null, "Conta cadastrada com sucesso!!\nNúmero da conta: "+ c.getNumero(), "Confirmação", JOptionPane.DEFAULT_OPTION);
            }
        } else if (isCorrente) {
            double limite = Double.parseDouble(JOptionPane.showInputDialog("Digite o limite da conta corrente: "));
            Conta c = new ContaCorrente(cliente, numero, limite);
            if(verificarDuplicidadeConta(c)== false){
                adicionarConta(c);
                JOptionPane.showMessageDialog(null, "Conta cadastrada com sucesso!!\nNúmero da conta: " + c.getNumero(), "Confirmação", JOptionPane.DEFAULT_OPTION);
            }
        } else if (isInvestimento) {
            Conta c = new ContaInvestimento(cliente, numero);
            if(verificarDuplicidadeConta(c)== false){
                adicionarConta(c);
                JOptionPane.showMessageDialog(null, "Conta cadastrada com sucesso!!\nNúmero da conta: " +c.getNumero(), "Confirmação", JOptionPane.DEFAULT_OPTION);
            }
        }
    }

    public void realizarDeposito(){
        Conta conta = selecionarConta();
        if (conta == null) {
            JOptionPane.showMessageDialog(null, "Conta não encontrada!", "Aviso", JOptionPane.WARNING_MESSAGE);
        } else if (conta.getClass() == ContaPoupanca.class) {
            double valor = Double.parseDouble(JOptionPane.showInputDialog("Digite o valor do depósito: "));
            conta.depositar(valor);
            JOptionPane.showMessageDialog(null, "Depósito realizado com sucesso!", "Confirmação", JOptionPane.DEFAULT_OPTION);
        } else if (conta.getClass() == ContaCorrente.class) {
            double valor = Double.parseDouble(JOptionPane.showInputDialog("Digite o valor do depósito: "));
            conta.depositar(valor);
            JOptionPane.showMessageDialog(null, "Depósito realizado com sucesso!", "Confirmação", JOptionPane.DEFAULT_OPTION);
        } else if (conta.getClass() == ContaInvestimento.class) {
            double valor = Double.parseDouble(JOptionPane.showInputDialog("Digite o valor do depósito: "));
            conta.depositar(valor);
            JOptionPane.showMessageDialog(null, "Depósito realizado com sucesso!", "Confirmação", JOptionPane.DEFAULT_OPTION);
        }
    }

    public void realizarSaque(){
        Conta conta = selecionarConta();
        if (conta == null) {
            JOptionPane.showMessageDialog(null, "Conta não encontrada!", "Aviso", JOptionPane.WARNING_MESSAGE);
        } else if (conta.getClass() == ContaPoupanca.class) {
            double valor = Double.parseDouble(JOptionPane.showInputDialog("Digite o valor do saque: "));
            conta.sacar(valor);
        } else if (conta.getClass() == ContaCorrente.class) {
            double valor = Double.parseDouble(JOptionPane.showInputDialog("Digite o valor do saque: "));
            conta.sacar(valor);
        } else if (conta.getClass() == ContaInvestimento.class) {
            double valor = Double.parseDouble(JOptionPane.showInputDialog("Digite o valor do saque: "));
            conta.sacar(valor);
        }
    }

    public void alterarLimite(){
        Conta conta = selecionarConta();
        if (conta.getClass() == ContaCorrente.class) {
            double limite = Double.parseDouble(JOptionPane.showInputDialog("Digite o novo limite da conta corrente: "));
            ((ContaCorrente) conta).setLimite(limite);
            JOptionPane.showMessageDialog(null, "Limite alterado com sucesso!", "Confirmação", JOptionPane.DEFAULT_OPTION);
        }else if(conta.getClass() == ContaInvestimento.class||conta.getClass() == ContaPoupanca.class){
            JOptionPane.showMessageDialog(null, "Tipo de conta inválido!", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void aplicarInvestimento(){
        Conta conta = selecionarConta();
        if (conta.getClass() == ContaInvestimento.class) {
            ((ContaInvestimento) conta).aplicar();
            JOptionPane.showMessageDialog(null, "Investimento aplicado com sucesso!", "Confirmação", JOptionPane.DEFAULT_OPTION);
        }else if(conta.getClass() == ContaPoupanca.class){
            ((ContaPoupanca) conta).aplicar();
            JOptionPane.showMessageDialog(null, "Investimento aplicado com sucesso!", "Confirmação", JOptionPane.DEFAULT_OPTION);
        }else if (conta.getClass() == ContaCorrente.class){
            JOptionPane.showMessageDialog(null, "Tipo de conta inválido!", "Aviso", JOptionPane.WARNING_MESSAGE);
            
        }
    }

    public void inicializarClientes(){
        String diretorioAtual = new File("").getAbsolutePath();
        String caminhoArquivoClientesPF = diretorioAtual + File.separator + "clientesPF.txt";
        String caminhoArquivoClientesPJ = diretorioAtual + File.separator + "clientesPJ.txt";
        
        if (new File(caminhoArquivoClientesPF).exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivoClientesPF))) {
                String linha;
                while ((linha = br.readLine()) != null) {
                    linha = linha.replace("\n", ";");
                    String[] dados = linha.split(";");
                    Cliente cliente = new PessoaFisica(dados[0], dados[1],LocalDate.parse((dados[2])), dados[3]);
                    adicionarCliente(cliente);
                }
            } catch (IOException e) {
                System.out.println("Erro ao carregar dados do arquivo de usuários: " + e.getMessage());
            }
        }
        if (new File(caminhoArquivoClientesPJ).exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivoClientesPJ))) {
                String linha;
                while ((linha = br.readLine()) != null) {
                    linha = linha.replace("\n", ";");
                    String[] dados = linha.split(";");
                    Cliente cliente = new PessoaJuridica(dados[0], dados[1],LocalDate.parse((dados[2])), dados[3]);
                    adicionarCliente(cliente);
                }
            } catch (IOException e) {
                System.out.println("Erro ao carregar dados do arquivo de usuários: " + e.getMessage());
            }
        }
        
    }

    public void inicializarContas(){
        String diretorioAtual = new File("").getAbsolutePath();
        String caminhoArquivoContasPoupanca = diretorioAtual + File.separator + "contasPoupanca.txt";
        String caminhoArquivoContasCorrente = diretorioAtual + File.separator + "contasCorrente.txt";
        String caminhoArquivoContasInvestimento = diretorioAtual + File.separator + "contasInvestimento.txt";

        
        if (new File(caminhoArquivoContasPoupanca).exists()){
            try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivoContasPoupanca))) {
                String linha;
                while ((linha = br.readLine()) != null) {
                    linha = linha.replace("\n", ";");
                    String[] dados = linha.split(";");
                    Conta conta = new ContaPoupanca(dados[0],Integer.parseInt(dados[1]) ,Double.parseDouble(dados[2]), buscarCliente(dados[3]));
                    adicionarConta(conta);
                }
            } catch (IOException e) {
                System.out.println("Erro ao carregar dados do arquivo de contas: " + e.getMessage());
            }
        }
        if(new File(caminhoArquivoContasCorrente).exists()){
            try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivoContasCorrente))) {
                String linha;
                while ((linha = br.readLine()) != null) {
                    linha = linha.replace("\n", ";");
                    String[] dados = linha.split(";");
                    Conta conta = new ContaCorrente(dados[0], Integer.parseInt(dados[1]) ,Double.parseDouble(dados[2]), buscarCliente(dados[3]), Double.parseDouble(dados[7]));
                    adicionarConta(conta);
                }
            } catch (IOException e) {
                System.out.println("Erro ao carregar dados do arquivo de contas: " + e.getMessage());
            }
        }
        if(new File(caminhoArquivoContasInvestimento).exists()){
            try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivoContasInvestimento))) {
                String linha;
                while ((linha = br.readLine()) != null) {
                    linha = linha.replace("\n", ";");
                    String[] dados = linha.split(";");
                    Conta conta = new ContaInvestimento(dados[0], Integer.parseInt(dados[1]) ,Double.parseDouble(dados[2]), buscarCliente(dados[3]), Integer.parseInt(dados[7]));
                    adicionarConta(conta);
                }
            } catch (IOException e) {
                System.out.println("Erro ao carregar dados do arquivo de contas: " + e.getMessage());
            }
        }
    }
    
    //eu sei que ficou muito longo, mas eu não tinha visto como usar tipos genéricos ainda
    public void finalizar() throws IOException{
        String nome = "clientes";
        FileWriter arquivo = new FileWriter(nome + ".txt");
        for (Cliente c : clientes.values()) {
            arquivo.write(c + "\n");
        }
        arquivo.close();

        nome = "clientesPF";
        arquivo = new FileWriter(nome + ".txt");
        for (PessoaFisica c : clientesPF.values()) {
            arquivo.write(c + "\n");
        }
        arquivo.close();

        nome = "clientesPJ";
        arquivo = new FileWriter(nome + ".txt");
        for (PessoaJuridica c : clientesPJ.values()) {
            arquivo.write(c + "\n");
        }
        arquivo.close();

        nome = "contas";
        arquivo = new FileWriter(nome + ".txt");
        for (Conta c : contas.values()) {
            arquivo.write(c + "\n");
        }
        arquivo.close();

        nome = "contasPoupanca";
        arquivo = new FileWriter(nome + ".txt");
        for (Conta c : contasPoupanca.values()) {
            arquivo.write(c + "\n");
        }
        arquivo.close();

        nome = "contasCorrente";
        arquivo = new FileWriter(nome + ".txt");
        for (Conta c : contasCorrente.values()) {
            arquivo.write(c + "\n");
        }
        arquivo.close();

        nome = "contasInvestimento";
        arquivo = new FileWriter(nome + ".txt");
        for (Conta c : contasInvestimento.values()) {
            arquivo.write(c + "\n");
        }
        arquivo.close();

        JOptionPane.showMessageDialog(null, "Programa finalizado com sucesso!\nBye bye!!", "Confirmação", JOptionPane.DEFAULT_OPTION);
    }
}