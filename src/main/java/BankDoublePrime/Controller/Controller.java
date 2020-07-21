package BankDoublePrime.Controller;

import BankDoublePrime.Model.Account;
import BankDoublePrime.Model.Token;
import BankDoublePrime.View.BankServer;

public class Controller {
    public static String giveTokenToAccount(String username) {
        Account account = AccountController.getAccountWithUserName(username);
        Token tokenPrime = null;
        for (Token token : BankServer.onlineUsers.keySet()) {
            if(BankServer.onlineUsers.get(token).equals(account)){
                tokenPrime = token;
            }
        }
        if(tokenPrime != null){
            BankServer.onlineUsers.remove(tokenPrime , account);
        }
        Token token = new Token(account);
        BankServer.onlineUsers.put(token, account);
        return token.getTokenId();
    }

    public static String generateToken(){
        return RandomString.getAlphaNumericString(20);
    }

}

class RandomString {

    // function to generate a random string of length n
    static String getAlphaNumericString(int n) {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }
}
