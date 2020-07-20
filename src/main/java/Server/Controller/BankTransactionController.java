package Server.Controller;

//import Server.Model.Account.Manager;
//
//import java.io.*;
//import java.net.Socket;
//
//public class BankTransactionController {
//    private static BankTransactionController bankTransactionController;
//    private String mainBankId = "";
//    private Socket socket;
//    private DataInputStream dis;
//    private DataOutputStream dos;
//
//    private BankTransactionController() {
//        try {
//            socket = new Socket("localHost", 8080);
//            dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
//            dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
//            setMainBankId();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static BankTransactionController getInstance() {
//        if (bankTransactionController == null)
//            bankTransactionController = new BankTransactionController();
//        return bankTransactionController;
//    }
//
//    public String getMainBankId() {
//        return mainBankId;
//    }
//
//    public void setMainBankId() {
//        Manager manager = (Manager) (Control.getInstance().getUser());
//        if (!manager.getBankAccountId().equals("")) {
//            mainBankId = manager.getBankAccountId();
//        } else if (manager.getBankAccountId().equals("")) {
//            mainBankId = addAccountToBank(manager.getFirstName() , manager.getLastName() , manager.getUsername()
//                    , manager.getPassword() ,manager.getPassword());
//            manager.setBankAccountId(mainBankId);
//        }
//    }
//
//    public String addAccountToBank(String firstName, String lastName, String username, String password, String repeatPassword) {
//        String toBeSend = "create_account " + firstName + " " + lastName + " " + username + " " + password + " " + repeatPassword;
//        String received = "";
//        try {
//            dos.writeUTF(toBeSend);
//            dos.flush();
//            received = dis.readUTF();
//            return received;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public String getBankToken(String username, String password) {
//        String toBeSend = "get_token " + username + " " + password;
//        String received = "";
//        try {
//            dos.writeUTF(toBeSend);
//            dos.flush();
//            received = dis.readUTF();
//            return received;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public String getReceiptID(String token, String type, String money, String sourceId, String desId, String description) {
//        StringBuilder sb = new StringBuilder("create_receipt " + token + " " + type + " " + money + " " + sourceId + " " + desId);
//        if (!description.equals("")) sb.append(description);
//        String toBeSend = sb.toString();
//        String received = "";
//        try {
//            dos.writeUTF(toBeSend);
//            dos.flush();
//            received = dis.readUTF();
//            return received;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public String getTransaction(String type, String token) {
//        String toBeSend = "get_transactions " + token + " " + type;
//        String received = "";
//        try {
//            dos.writeUTF(toBeSend);
//            dos.flush();
//            received = dis.readUTF();
//            return received;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public String payReceipt(String receiptId) {
//        String toBeSend = "pay " + receiptId;
//        String received = "";
//        try {
//            dos.writeUTF(toBeSend);
//            dos.flush();
//            received = dis.readUTF();
//            return received;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public String getBalance(String token) {
//        String toBeSend = "get_balance " + token;
//        String received = "";
//        try {
//            dos.writeUTF(toBeSend);
//            dos.flush();
//            received = dis.readUTF();
//            return received;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public String exitBank() {
//        String toBeSend = "exit";
//        String received = "";
//        try {
//            dos.writeUTF(toBeSend);
//            dos.flush();
//            received = dis.readUTF();
//            return received;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//}
