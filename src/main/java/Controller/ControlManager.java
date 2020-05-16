package Controller;

import Model.Account.Account;
import Model.Category;
import Model.DiscountCode;
import Model.Account.Manager;
import Model.Product;
import Model.Request.Request;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ControlManager {

    Manager user = (Manager)(Control.getInstance().getUser());
    private static ControlManager instance;
    private ControlManager()
    {

    }
    public static ControlManager getInstance()
    {
        if(instance == null)
            instance = new ControlManager();
        return instance;
    }
    public void createAccount(String username, String password, String firstName, String lastName, String email, String phoneNumber)
    {
        new Manager(username,firstName,lastName,email,phoneNumber,password);
    }

    public void viewUsername(String username) throws Exception
    {
        if(!Account.getAllAccounts().keySet().contains(username))
        {
            throw new Exception("This username doesn't exist!");
        }
    }
    public void removeProduct(String id) throws Exception
    {
        if(!Product.getAllProducts().containsKey(id))
            throw new Exception("This product doesn't exist!");
        Product.getAllProducts().remove(id);
    }
    private int makeInt(String s)
    {
        return Integer.parseInt(s);
    }
    private double makeDouble(String s)
    {
        return Double.parseDouble(s);
    }
    public void createDiscountCode(String discountID , String startDate, String endDate,String percentage,String maxDiscount, String maxNumber) throws Exception
    {
        //Exceptions
        if(DiscountCode.getAllDiscountCodes().containsKey(discountID))
        {
            throw new Exception("This id was already selected");
        }
        if(!startDate.matches("\\d{4}\\s+\\d{1,2}\\s+\\d{1,2}\\s+\\d{1,2}\\s+\\d{1,2}"))
        {
            throw new Exception("Your start date format isn't allowed!");
        }
        if(!endDate.matches("\\d{4}\\s+\\d{1,2}\\s+\\d{1,2}\\s+\\d{1,2}\\s+\\d{1,2}"))
        {
            throw new Exception("Your end date format isn't allowed!");
        }
        if(!percentage.matches("\\d+.?(\\d+)?"))
        {
            throw new Exception("Your percentage should be a double!");
        }
        if(!maxDiscount.matches("\\d+.?(\\d+)?"))
        {
            throw new Exception("Your maximum amount of discount should be a double!");
        }
        if(!maxNumber.matches("\\d+"))
        {
            throw new Exception("Your maximum amount of number of usage should be an Integer!");
        }
        String[] startDateParsed = startDate.split("\\s+");
        String[] endDateParsed = endDate.split("\\s+");
        LocalDateTime startDateDate = LocalDateTime.of(makeInt(startDateParsed[0]),makeInt(startDateParsed[1]),makeInt(startDateParsed[2]),makeInt(startDateParsed[3]),makeInt(startDateParsed[4]));
        LocalDateTime endDateDate = LocalDateTime.of(makeInt(endDateParsed[0]),makeInt(endDateParsed[1]),makeInt(endDateParsed[2]),makeInt(endDateParsed[3]),makeInt(endDateParsed[4]));
        new DiscountCode(discountID,startDateDate,endDateDate,makeDouble(percentage),makeDouble(maxDiscount),makeInt(maxNumber));
    }
    public boolean editDiscountCodeValidField(String field)
    {
        if(field.matches("(?i)start\\s*time|end\\s*time|percentage|discount\\*percentage|max\\s*discount|max\\s*for\\s*each"))
        {
            return true;
        }
        return false;
    }
    public void editDiscountCode(String field,String newValue, String id) throws Exception
    {
        if(field.equals("Start date"))
        {
            if(!newValue.matches("\\d{4}\\s+\\d{1,2}\\s+\\d{1,2}\\s+\\d{1,2}\\s+\\d{1,2}"))
            {
                throw new Exception("Your start date format isn't allowed!");
            }
            else
            {
                String[] startDateParsed = newValue.split("\\s+");
                DiscountCode.getAllDiscountCodes().get(id).setStartTime(LocalDateTime.of(makeInt(startDateParsed[0]),makeInt(startDateParsed[1]),makeInt(startDateParsed[2]),makeInt(startDateParsed[3]),makeInt(startDateParsed[4])));
            }
        }
        else if(field.equals("End date"))
        {
            if(!newValue.matches("\\d{4}\\s+\\d{1,2}\\s+\\d{1,2}\\s+\\d{1,2}\\s+\\d{1,2}"))
            {
                throw new Exception("Your end date format isn't allowed!");
            }
            else
            {
                String[] endDateParsed = newValue.split("\\s+");
                DiscountCode.getAllDiscountCodes().get(id).setEndTime(LocalDateTime.of(makeInt(endDateParsed[0]),makeInt(endDateParsed[1]),makeInt(endDateParsed[2]),makeInt(endDateParsed[3]),makeInt(endDateParsed[4])));
            }
        }
        else if(field.equals("Percentage"))
        {
            if(!newValue.matches("\\d+.?(\\d+)?"))
            {
                throw new Exception("Your percentage should be a double!");
            }
            else
            {
                DiscountCode.getAllDiscountCodes().get(id).setDiscountPercentage(makeDouble(newValue));
            }
        }
        else if(field.equals("Max amount"))
        {
            if(!newValue.matches("\\d+.?(\\d+)?"))
            {
                throw new Exception("Your maximum amount of discount should be a double!");
            }
            else
            {
                DiscountCode.getAllDiscountCodes().get(id).setMaxDiscountAmount(makeDouble(newValue));
            }
        }
        else if(field.equals("Max times"))
        {
            if(!newValue.matches("\\d+"))
            {
                throw new Exception("Your maximum amount of number of usage should be an Integer!");
            }
            else
            {
                DiscountCode.getAllDiscountCodes().get(id).setDiscountNumberForEachUser(makeInt(newValue));
            }
        }
    }
    public void removeDiscountCode(String id) throws Exception
    {
        if(!DiscountCode.getAllDiscountCodes().containsKey(id))
        {
            throw new Exception("This id doesn't exist!");
        }
        else
        {
            DiscountCode.getAllDiscountCodes().remove(id);
        }
    }
    public void addCategory(String name, ArrayList<Product> products) throws Exception
    {
        for (Category category : Category.getAllCategories()) {
            if(category.getName().equalsIgnoreCase(name))
                throw new Exception("This category already exists!");
        }
        new Category(name, products);
    }
    public boolean checkDiscountCodeExistance(String id)
    {
        if(DiscountCode.getAllDiscountCodes().containsKey(id))
            return true;
        return false;
    }
    public boolean checkRequestIdExistance(String id)
    {
        if(Request.getAllRequests().containsKey(id))
        {
            return true;
        }
        return false;
    }
}
