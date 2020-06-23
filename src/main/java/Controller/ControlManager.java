package Controller;

import Model.Account.Account;
import Model.Account.Customer;
import Model.Category;
import Model.DiscountCode;
import Model.Account.Manager;
import Model.Product;
import Model.Request.Request;
import View.ManagerProfileUIs.ManageCategories.ManagerEditCategoryUI;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class ControlManager {

    Manager user = (Manager)(Control.getInstance().getUser());
    private String userToView;
    private String discountCodeToView;
    private String discountCodeToEdit;
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
    public void createAccount(String username, String password, String firstName, String lastName, String email, String phoneNumber,String photo)
    {
        Manager manager = new Manager(username,firstName,lastName,email,phoneNumber,password,photo);
    }

    public void setDiscountCodeToEdit(String discountCodeToEdit) {
        this.discountCodeToEdit = discountCodeToEdit;
    }

    public String getDiscountCodeToEdit() {
        return discountCodeToEdit;
    }

    public void setUserToView(String userToView) {
        this.userToView = userToView;
    }

    public String getUserToView() {
        return userToView;
    }

    public void setDiscountCodeToView(String discountCodeToView) {
        this.discountCodeToView = discountCodeToView;
    }

    public String getDiscountCodeToView() {
        return discountCodeToView;
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
        Product.removeProduct(Product.getAllProducts().get(id));
    }
    private int makeInt(String s)
    {
        return Integer.parseInt(s);
    }
    private double makeDouble(String s)
    {
        return Double.parseDouble(s);
    }
    public void createDiscountCode(String discountID , String startDate, String endDate, String percentage, String maxDiscount, String maxNumber, HashMap<String, Customer> codeOwners) throws Exception
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
        new DiscountCode(discountID,startDateDate,endDateDate,makeDouble(percentage),makeDouble(maxDiscount),makeInt(maxNumber),codeOwners);
    }
    public boolean editDiscountCodeValidField(String field)
    {
        if(field.matches("(?i)start\\s*time|end\\s*time|percentage|discount\\*percentage|max\\s*discount|max\\s*for\\s*each|add\\s*owner|remove\\s*owner"))
        {
            return true;
        }
        return false;
    }
    public void editDiscountCode(String field,String newValue, String id) throws Exception
    {
        DiscountCode discountCode = DiscountCode.getAllDiscountCodes().get(id);
        if(field.equalsIgnoreCase("Start date"))
        {
            if(!newValue.matches("\\d{4}\\s+\\d{1,2}\\s+\\d{1,2}\\s+\\d{1,2}\\s+\\d{1,2}"))
            {
                throw new Exception("Your start date format isn't allowed!");
            }
            else
            {
                String[] startDateParsed = newValue.split("\\s+");
                discountCode.setStartTime(LocalDateTime.of(makeInt(startDateParsed[0]),makeInt(startDateParsed[1]),makeInt(startDateParsed[2]),makeInt(startDateParsed[3]),makeInt(startDateParsed[4])));
            }
        }
        else if(field.equalsIgnoreCase("End date"))
        {
            if(!newValue.matches("\\d{4}\\s+\\d{1,2}\\s+\\d{1,2}\\s+\\d{1,2}\\s+\\d{1,2}"))
            {
                throw new Exception("Your end date format isn't allowed!");
            }
            else
            {
                String[] endDateParsed = newValue.split("\\s+");
                discountCode.setEndTime(LocalDateTime.of(makeInt(endDateParsed[0]),makeInt(endDateParsed[1]),makeInt(endDateParsed[2]),makeInt(endDateParsed[3]),makeInt(endDateParsed[4])));
            }
        }
        else if(field.equalsIgnoreCase("Percentage"))
        {
            if(!newValue.matches("\\d+.?(\\d+)?"))
            {
                throw new Exception("Your percentage should be a double!");
            }
            else
            {
                discountCode.setDiscountPercentage(makeDouble(newValue));
            }
        }
        else if(field.equalsIgnoreCase("Max amount"))
        {
            if(!newValue.matches("\\d+.?(\\d+)?"))
            {
                throw new Exception("Your maximum amount of discount should be a double!");
            }
            else
            {
                discountCode.setMaxDiscountAmount(makeDouble(newValue));
            }
        }
        else if(field.equalsIgnoreCase("Max times"))
        {
            if(!newValue.matches("\\d+"))
            {
                throw new Exception("Your maximum amount of number of usage should be an Integer!");
            }
            else
            {
                discountCode.setDiscountNumberForEachUser(makeInt(newValue));
            }
        }
        else if(field.equalsIgnoreCase("add owner"))
        {
            String username = newValue;
            if(!Account.getAllAccounts().containsKey(username))
            {
                throw new Exception("This username doesn't exist!");
            }
            if(!(Account.getAllAccounts().get(username) instanceof Customer))
            {
                throw new Exception("This user is not a customer.");
            }
            if(discountCode.getDiscountOwners().containsKey(username))
            {
                throw new Exception("This user already has this code.");
            }
            discountCode.addDiscountOwner((Customer) Account.getAllAccounts().get(username));
        }
        else if(field.equalsIgnoreCase("remove owner"))
        {
            String username = newValue;
            if(!Account.getAllAccounts().containsKey(username))
            {
                throw new Exception("This username doesn't exist!");
            }
            if(!(Account.getAllAccounts().get(username) instanceof Customer))
            {
                throw new Exception("This user is not a customer.");
            }
            if(!discountCode.getDiscountOwners().containsKey(username))
            {
                throw new Exception("This user does not have this code");
            }
            discountCode.removeDiscountOwner((Customer) Account.getAllAccounts().get(username));
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
            DiscountCode discountCode = DiscountCode.getAllDiscountCodes().get(id);
            for (String s : discountCode.getDiscountOwners().keySet())
            {
                Customer customer = discountCode.getDiscountOwners().get(s);
                customer.removeDiscountCode(discountCode);
            }
            DiscountCode.removeDiscountCode(id);
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
    public void changeCategoryName(String newName) throws Exception
    {
        if(ControlManager.getInstance().checkCategoryExistance(newName))
        {
            throw new Exception("This category already exists!");
        }
        for (Category category : Category.getAllCategories()) {
            if(category.getName().equalsIgnoreCase(ManagerEditCategoryUI.getInstance().getCategoryName()))
            {
                category.setName(newName);
            }
        }
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
    public boolean checkCategoryExistance(String name)
    {
        for (Category category : Category.getAllCategories()) {
            if(category.getName().equalsIgnoreCase(name))
                return true;
        }
        return false;
    }
}
