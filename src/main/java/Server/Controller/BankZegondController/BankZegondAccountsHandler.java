package Server.Controller.BankZegondController;

import Server.Controller.Control;
import Server.Model.Account.Account;
import Server.Model.Account.Customer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BankZegondAccountsHandler {
    private Socket clientSocket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private ServerSocket serverSocket;
    private Account user;

    public BankZegondAccountsHandler(Socket clientSocket, DataOutputStream dataOutputStream, DataInputStream dataInputStream, ServerSocket serverSocket, Account user) {
        this.clientSocket = clientSocket;
        this.dataOutputStream = dataOutputStream;
        this.dataInputStream = dataInputStream;
        this.serverSocket = serverSocket;
        this.user = user;
    }

    public void bankAccountsControllerHandler(RequestForBankServer requestForServer) throws IOException {
        if (requestForServer.getFunction().equals("createBankAccount")) {
            createAccountHandler(requestForServer);
        }
    }

    private void createAccountHandler(RequestForBankServer requestForServer) throws IOException {
        String response = Control.getInstance().getBankAccountsController().createBankAccount(requestForServer.getInputs().get(0), requestForServer.getInputs().get(1),
                requestForServer.getInputs().get(2), requestForServer.getInputs().get(3), requestForServer.getInputs().get(4));
        if (!response.startsWith("password") && !response.startsWith("username")) {
            //Account user; //TODO = Shop.getInstance().findUser(requestForServer.getInputs().get(2));
            if (user instanceof Customer)
                ((Customer) user).setBankAccountId(response);
            if (user == null) {
                //TODO Shop.getInstance().findRegisteringSellerRequestForServer(requestForServer.getInputs().get(2)).setBankAccountId(response);
            }
        }
        String answer = Control.getInstance().getBankTransactionsController().depositFirstAfterCreating(requestForServer.getInputs().get(2), requestForServer.getInputs().get(3), "10000000");
        dataOutputStream.writeUTF(response);
        dataOutputStream.flush();
    }

}
