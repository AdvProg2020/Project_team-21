package View;

import Model.DiscountCode;

public class ManagerViewDiscountCodesUI extends UI {
    private static ManagerViewDiscountCodesUI instance;
    private ManagerViewDiscountCodesUI()
    {

    }

    public static ManagerViewDiscountCodesUI getInstance()
    {
        if(instance == null)
            instance = new ManagerViewDiscountCodesUI();
        return instance;
    }
    @Override
    public void run()
    {
        int i = 1;
        for (String s : DiscountCode.getAllDiscountCodes().keySet())
        {
            System.out.println(i+"." + s + "   Percentage: "+DiscountCode.getAllDiscountCodes().get(s).getDiscountPercentage());
            i++;
        }
    }

    @Override
    public void help()
    {
        System.out.println("To view details of a discount code : view discount code [code]");
        System.out.println("To edit a field in a discount code : edit discount code [code]");
        System.out.println("To remove a discount code : remove discount code [code]");
        System.out.println("To sort by alphabet : sort by start date");
        System.out.println("To sort by alphabet : sort by end date");
        System.out.println("To sort by percentage : sort by percentage");
    }
}
