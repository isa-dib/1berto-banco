import java.io.IOException;
import javax.swing.JOptionPane;

public class App {
    private final static Banco banco = new Banco();
    public static void main(String[] args) throws IOException {
        //cadastrarcliente e ja acresenta na lista de clientes
        //cadastrarconta e ja acresenta na lista de contas
        //cadastrar usando cast cliente e conta
        //verificar duplicidade de cpf e cnpj 
        //um cliente não pode ter duas contas do mesmo tipo

        banco.inicializarClientes();
        banco.inicializarContas();
        

        String inputi = "";
        while (true) {
            inputi = JOptionPane.showInputDialog("Bem-vindo ao " + banco.getBanco()+ "\n" + exibirMenu());
            int opcao = Integer.parseInt(inputi);

            if(opcao == 1){
                banco.cadastrarCliente();
            }else if(opcao == 2){
                banco.cadastrarConta();
            }else if(opcao == 3){
                banco.realizarDeposito();
            }else if(opcao == 4){
                banco.realizarSaque();
            }else if(opcao == 5){
                banco.listarClientes();
            }else if(opcao == 6){
                banco.listarContas();
            }/*else if(opcao == 7){
                banco.removerConta();
            }else if(opcao == 8){
                banco.removerCliente();
            }*/else if(opcao == 9){
                int resp;
                resp = JOptionPane.showConfirmDialog(null, "Deseja finalizar?");
                if(resp == JOptionPane.YES_OPTION) {
                    break;}
                    else if(resp == JOptionPane.NO_OPTION || resp == JOptionPane.CANCEL_OPTION){
                        JOptionPane.showMessageDialog(null, "Operação cancelada");
                    }
            }
            else{
                JOptionPane.showMessageDialog(null, "Opção inválida", "Erro", JOptionPane.ERROR_MESSAGE);
            }

        }
        banco.finalizar();
    }

    public static String exibirMenu(){
        return "1. Cadastrar Cliente\n2. Cadastrar Conta\n3. Realizar Depósito\n4. Realizar Saque\n5. Listar Clientes\n6. Listar Contas\n7. Remover Conta\n8. Remover Cliente\n9. Alterar Limite\n10. Aplicar Investimento\n11. Sair";
    }
}
