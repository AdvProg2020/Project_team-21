package Server.Controller.BankZegondController;

import Server.Controller.Control;
import Server.Model.Account.Account;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BankZegondTransactionsHandler {
    private Socket clientSocket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private ServerSocket serverSocket;
    private Account user;

    public BankZegondTransactionsHandler(Socket clientSocket, DataOutputStream dataOutputStream, DataInputStream dataInputStream, ServerSocket serverSocket, Account user) {
        this.clientSocket = clientSocket;
        this.dataOutputStream = dataOutputStream;
        this.dataInputStream = dataInputStream;
        this.serverSocket = serverSocket;
        this.user = user;
    }

    public void bankTransactionControllerHandler(RequestForBankServer requestForServer) throws IOException {
        if (requestForServer.getFunction().equals("increaseCustomerCredit")){
            increaseCreditCustomerHandler(requestForServer);
        }else if (requestForServer.getFunction().equals("withdrawMoneySeller")){
            withdrawMoneySellerHandler(requestForServer);
        }else if(requestForServer.getFunction().equals("depositMoneySeller")){
            depositMoneySellerHandler(requestForServer);
        }
    }

    private void increaseCreditCustomerHandler(RequestForBankServer requestForServer) throws IOException {
        String response = Control.getInstance().getBankTransactionsController().increaseCustomerCredit(requestForServer.getInputs().get(0),
                requestForServer.getInputs().get(1), requestForServer.getInputs().get(2));
        dataOutputStream.writeUTF(response);
        dataOutputStream.flush();
    }

    private void withdrawMoneySellerHandler(RequestForBankServer requestForServer) throws IOException {
        String response = Control.getInstance().getBankTransactionsController().withdrawMoneySeller(requestForServer.getInputs().get(0),
                requestForServer.getInputs().get(1), requestForServer.getInputs().get(2));
        dataOutputStream.writeUTF(response);
        dataOutputStream.flush();
    }

    private void depositMoneySellerHandler(RequestForBankServer requestForServer) throws IOException {
        String response = Control.getInstance().getBankTransactionsController().depositMoneySeller(requestForServer.getInputs().get(0),
                requestForServer.getInputs().get(1), requestForServer.getInputs().get(2));
        dataOutputStream.writeUTF(response);
        dataOutputStream.flush();
    }
}
