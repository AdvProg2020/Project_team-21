package View;

import Controller.ControlManager;
import Model.DiscountCode;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.Scanner;

public class ManagerCreateDiscountCodeUI extends UI {
    private static ManagerCreateDiscountCodeUI instance;
    private ManagerCreateDiscountCodeUI()
    {

    }

    public static ManagerCreateDiscountCodeUI getInstance() {
        if (instance == null)
            instance = new ManagerCreateDiscountCodeUI();
        return instance;
    }
    private String randomString(int n)
    {

        // length is bounded by 256 Character
        byte[] array = new byte[256];
        new Random().nextBytes(array);

        String randomString
                = new String(array, Charset.forName("UTF-8"));

        // Create a StringBuffer to store the result
        StringBuffer r = new StringBuffer();

        // remove all spacial char
        String  AlphaNumericString
                = randomString
                .replaceAll("[^A-Za-z0-9]", "");

        // Append first 20 alphanumeric characters
        // from the generated random String into the result
        for (int k = 0; k < AlphaNumericString.length(); k++) {

            if (Character.isLetter(AlphaNumericString.charAt(k))
                    && (n > 0)
                    || Character.isDigit(AlphaNumericString.charAt(k))
                    && (n > 0)) {

                r.append(AlphaNumericString.charAt(k));
                n--;
            }
        }
        // return the resultant string
        return r.toString();
    }
    @Override
    public void run()
    {
        Scanner scanner = ConsoleView.getScanner();
        String discountID = randomString(10);
        System.out.println("Enter start date in [year month day hour minute]: ");
        String startDate = scanner.nextLine();
        System.out.println("Enter end date in [year month day hour minute]: ");
        String endDate = scanner.nextLine();
        System.out.println("Enter percentage discount: ");
        String percentage =scanner.nextLine();
        System.out.println("Enter maximum amount of discount you want to give: ");
        String maxDiscount =scanner.nextLine();
        System.out.println("Enter allowed number of times a user can use this code: ");
        String maxNumber = scanner.nextLine();
        try
        {
            ControlManager.getInstance().createDiscountCode(discountID,startDate,endDate,percentage,maxDiscount,maxNumber);
            System.out.println("Discount code with " + discountID + "has been successfully made!");
            ConsoleView.getInstance().goToNextPage(ConsoleView.getInstance().getLastMenu());
        }
        catch (Exception e)
        {
            ConsoleView.getInstance().errorInput(e.getMessage(),ConsoleView.getInstance().getLastMenu());
        }
    }

    @Override
    public void help() {
    }
}
