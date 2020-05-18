package View.ManagerProfileUIs.ManageDiscountCodes;

import Controller.ControlManager;
import Controller.Sort;
import Model.DiscountCode;
import View.ConsoleView;
import View.UI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ManagerViewDiscountCodeUI extends UI {
    private static ManagerViewDiscountCodeUI instance;
    private SortDiscountCodesType sortDiscountCodesType;
    private ManagerViewDiscountCodeUI()
    {

    }

    public static ManagerViewDiscountCodeUI getInstance()
    {
        if(instance == null)
            instance = new ManagerViewDiscountCodeUI();
        return instance;
    }
    String code = "";

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public void run() {
        if(ControlManager.getInstance().checkDiscountCodeExistance(code))
        {
            System.out.println("Percentage: " + DiscountCode.getAllDiscountCodes().get(code).getDiscountPercentage());
            System.out.println("Start time: " + DiscountCode.getAllDiscountCodes().get(code).getStartTime());
            System.out.println("End time: " + DiscountCode.getAllDiscountCodes().get(code).getEndTime());
            System.out.println("Maximum discount amount: " + DiscountCode.getAllDiscountCodes().get(code).getMaxDiscountAmount());
            System.out.println("Maximum for each user: " + DiscountCode.getAllDiscountCodes().get(code).getDiscountNumberForEachUser());
            ConsoleView.getInstance().goToNextPage(ConsoleView.getInstance().getLastMenu());
        }
        else
        {
            ConsoleView.getInstance().errorInput("Code doesn't exist!" , ConsoleView.getInstance().getLastMenu());
        }
    }

    @Override
    public void help() {

    }

    public void setSortDiscountCodesType(SortDiscountCodesType sortDiscountCodesType) {
        this.sortDiscountCodesType = sortDiscountCodesType;
    }

    private SortDiscountCodesType getSortDiscountCodesType() {
        return sortDiscountCodesType;
    }

    @Override
    public void sort() {
        if(getSortDiscountCodesType()==SortDiscountCodesType.DISCOUNT_ID){
            Map<String, DiscountCode> discountCodes = Sort.sortDiscountCodeHashMap(DiscountCode.getAllDiscountCodes());
            for (String s : discountCodes.keySet()) {
                System.out.println(s);
            }
        } else if(getSortDiscountCodesType()==SortDiscountCodesType.DISCOUNT_PERCENTAGE){
            ArrayList<DiscountCode> discountCodes = Sort.sortDiscountCodesByDiscountPercentage(new ArrayList<>(DiscountCode.getAllDiscountCodes().values()));
            for (DiscountCode discountCode : discountCodes) {
                System.out.println(discountCode.getDiscountId() + ": " + discountCode.getDiscountPercentage());
            }
        }
    }
}
