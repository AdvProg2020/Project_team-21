package Server.Model.BankPrime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BankClientHandler implements Runnable{
    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;

    public BankClientHandler(Socket socket) throws IOException {
        this.socket = socket;

        dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
    }

    private void handler() throws IOException{
        while (true){
            String in = dis.readUTF();
            String[] splitted = in.split("\\s");

        }
    }

    @Override
    public void run() {

    }


    public void start() {
    }

    private void getBalance(String[] splitInputs) {
        if (!LaunchBank.expiredTokens.contains(splitInputs[1])) {
            if (LaunchBank.tokens.containsKey(splitInputs[1])) {
                BankAccount bankAccount = BankAccount.getBankAccountWithUsername(LaunchBank.tokens.get(splitInputs[1]));
                try {
                    dos.writeUTF(String.valueOf(bankAccount.getBalance()));
                    dos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    dos.writeUTF("Token is invalid");
                    dos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                dos.writeUTF("Token expired");
                dos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void pay(String[] splitInputs) {
        int receiptId = Integer.parseInt(splitInputs[1]);
        Receipt receipt = Receipt.getReceiptById(receiptId);
        if (receipt == null) {
            try {
                dos.writeUTF("Invalid receipt id");
                dos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (receipt.isPaid()) {
            try {
                dos.writeUTF("Receipt is paid before");
                dos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (receipt.getType().equals("move")) {
            if (BankAccount.getAccountWithId(receipt.getSourceId()) == null || BankAccount.getAccountWithId(receipt.getDestinationId()) == null) {
                try {
                    dos.writeUTF("Invalid account id");
                    dos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (BankAccount.getAccountWithId(receipt.getSourceId()).getBalance() < receipt.getMoney()) {
                try {
                    dos.writeUTF("Source account does not have enough money");
                    dos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                BankAccount source = BankAccount.getAccountWithId(receipt.getSourceId());
                BankAccount destination = BankAccount.getAccountWithId(receipt.getDestinationId());
                source.setBalance(source.getBalance() - receipt.getMoney());
                destination.setBalance(destination.getBalance() + receipt.getMoney());
                receipt.setPaid(true);
                try {
                    dos.writeUTF("Done successfully");
                    dos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (receipt.getType().equals("deposit")) {
            if (BankAccount.getAccountWithId(receipt.getDestinationId()) == null) {
                try {
                    dos.writeUTF("Invalid account id");
                    dos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                BankAccount destination = BankAccount.getAccountWithId(receipt.getDestinationId());
                destination.setBalance(destination.getBalance() + receipt.getMoney());
                receipt.setPaid(true);
                try {
                    dos.writeUTF("Done successfully");
                    dos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (receipt.getType().equals("withdraw")) {
            if (BankAccount.getAccountWithId(receipt.getSourceId()) == null) {
                try {
                    dos.writeUTF("Invalid account id");
                    dos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (BankAccount.getAccountWithId(receipt.getSourceId()).getBalance() < receipt.getMoney()) {
                try {
                    dos.writeUTF("Source account does not have enough money");
                    dos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                BankAccount source = BankAccount.getAccountWithId(receipt.getSourceId());
                source.setBalance(source.getBalance() - receipt.getMoney());
                receipt.setPaid(true);
                try {
                    dos.writeUTF("Done successfully");
                    dos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void getTransactions(String[] splitInputs) {
        String token = splitInputs[1];
        String type = splitInputs[2];
        if (!LaunchBank.expiredTokens.contains(token)) {
            if (LaunchBank.tokens.containsKey(splitInputs[1])) {
                Gson gson = new GsonBuilder().serializeNulls().create();
                BankAccount bankAccount = BankAccount.getBankAccountWithUsername(LaunchBank.tokens.get(token));
                if (type.equals("+")) {
                    ArrayList<Receipt> receiptsResult = new ArrayList<>();
                    for (Receipt receipt : Receipt.getAllReceipts()) {
                        if (receipt.getDestinationId() == bankAccount.getAccountId()) {
                            receiptsResult.add(receipt);
                        }
                    }
                    String data = gson.toJson(receiptsResult);
                    try {
                        dos.writeUTF(data);
                        dos.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (type.equals("-")) {
                    ArrayList<Receipt> receiptsResult = new ArrayList<>();
                    for (Receipt receipt : Receipt.getAllReceipts()) {
                        if (receipt.getDestinationId() == bankAccount.getAccountId()) {
                            receiptsResult.add(receipt);
                        }
                    }
                    String data = gson.toJson(receiptsResult);
                    try {
                        dos.writeUTF(data);
                        dos.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else if (type.equals("*")) {
                    ArrayList<Receipt> receiptsResult = new ArrayList<>();
                    for (Receipt receipt : Receipt.getAllReceipts()) {
                        if (receipt.getSourceId() == bankAccount.getAccountId() || receipt.getDestinationId() == bankAccount.getAccountId()) {
                            receiptsResult.add(receipt);
                        }
                    }
                    String data = gson.toJson(receiptsResult);
                    try {
                        dos.writeUTF(data);
                        dos.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    for (Receipt i : Receipt.getAllReceipts()) {
                        if (i.getId() == Integer.parseInt(splitInputs[2])) {
                            String data = gson.toJson(i);
                            try {
                                dos.writeUTF(data);
                                dos.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return;
                        }
                    }
                    try {
                        dos.writeUTF("Token is invalid");
                        dos.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            try {
                dos.writeUTF("Token expired");
                dos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String checkInput(String input) {
        String regex = "create_receipt (\\S+) (\\S+) (\\S+) (-?\\d+) (-?\\d+)( \\S+)?";
        String[] splitInputs = input.split("\\s");
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if (!matcher.find()) {
            return "Invalid parameters passed";
        } else {
            try {
                double test = Double.parseDouble(splitInputs[3]);
            } catch (NumberFormatException e) {
                return "Invalid money";
            }
            if (splitInputs[2].equals("deposit") || splitInputs[2].equals("withdraw") || splitInputs[2].equals("move")) {
                return "ok";
            } else {
                return "Invalid receipt type";
            }
        }
    }

    private void createReceipt(String input) {
        String[] splitInputs = input.split("\\s");
        String check = checkInput(input);
        if (check.equals("ok")) {
            if (!LaunchBank.expiredTokens.contains(splitInputs[1])) {
                if (LaunchBank.tokens.containsKey(splitInputs[1])) {
                    String type = splitInputs[2];
                    double money = Double.parseDouble(splitInputs[3]);
                    int sourceId = Integer.parseInt(splitInputs[4]);
                    int destinationId = Integer.parseInt(splitInputs[5]);
                    String description = null;
                    if (splitInputs.length > 6) {
                        description = splitInputs[6];
                    }
                    if (type.equals("deposit") && sourceId != -1) {
                        try {
                            dos.writeUTF("Source account id is invalid");
                            dos.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (type.equals("withdraw") && destinationId != -1) {
                        try {
                            dos.writeUTF("Destination account id is invalid");
                            dos.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (type.equals("withdraw") && sourceId != BankAccount.getBankAccountWithUsername(LaunchBank.tokens.get(splitInputs[1])).getAccountId()) {
                        try {
                            dos.writeUTF("Token is invalid");
                            dos.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (type.equals("move") && sourceId != BankAccount.getBankAccountWithUsername(LaunchBank.tokens.get(splitInputs[1])).getAccountId()) {
                        try {
                            dos.writeUTF("Token is invalid");
                            dos.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (type.equals("move") && sourceId == destinationId) {
                        try {
                            dos.writeUTF("Equal source and destination account");
                            dos.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (type.equals("move") && (sourceId == -1 || destinationId == -1)) {
                        try {
                            dos.writeUTF("Invalid account id");
                            dos.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Receipt receipt = new Receipt(type, money, sourceId, destinationId, description);
                        try {
                            dos.writeUTF(String.valueOf(receipt.getId()));
                            dos.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    try {
                        dos.writeUTF("Token is invalid");
                        dos.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                try {
                    dos.writeUTF("Token expired");
                    dos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void getToken(String[] splitInputs) {
        if (BankAccount.isThereAccountWithThisUsername(splitInputs[1])) {
            BankAccount bankAccount = BankAccount.getBankAccountWithUsername(splitInputs[1]);
            if (splitInputs[2].equals(bankAccount.getPassword())) {
                if (LaunchBank.tokens.containsValue(splitInputs[1])) {
                    try {
                        String token = null;
                        for (String i : LaunchBank.tokens.keySet()) {
                            if (LaunchBank.tokens.get(i).equals(splitInputs[1])) {
                                token = i;
                            }
                        }
                        dos.writeUTF(token);
                        dos.flush();
                        return;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                String token = generateToken();
                LaunchBank.tokens.put(token, bankAccount.getUsername());
                class TokenTimer extends TimerTask {
                    private String token;

                    public TokenTimer(String token) {
                        this.token = token;
                    }

                    @Override
                    public void run() {
                        LaunchBank.expiredTokens.add(token);
                        LaunchBank.tokens.remove(token);
                    }
                }
                TimerTask timerTask = new TokenTimer(token);
                Timer timer = new Timer();
                timer.schedule(timerTask, 3600000);
                try {
                    dos.writeUTF(token);
                    dos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    dos.writeUTF("Invalid username or password");
                    dos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                dos.writeUTF("Invalid username or password");
                dos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String generateToken() {
        final String ALPHA_NUMERIC_STRING = "NAKAPPROJECT";
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            stringBuilder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return stringBuilder.toString();
    }

    private void createAccount(String[] splitInputs) {
        if (splitInputs[4].equals(splitInputs[5])) {
            if (!(BankAccount.isThereAccountWithThisUsername(splitInputs[3]))) {
                BankAccount bankAccount = new BankAccount(splitInputs[1], splitInputs[2], splitInputs[3], splitInputs[4]);
                try {
                    dos.writeUTF(String.valueOf(bankAccount.getAccountId()));
                    dos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    dos.writeUTF("Username is not available");
                    dos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                dos.writeUTF("Passwords do not match");
                dos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
