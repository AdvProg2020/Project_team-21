package Model;

import Controller.ControlManager;
import View.ManagerProfileUIs.ManageCategories.ManagerManageCategoriesUI;
import org.junit.Test;
import org.junit.Assert;

import java.util.ArrayList;

public class AccountTest {

    String expected = new String();
    String real = new String();
    @Test
    public void addToCartTest()
    {
        ArrayList<Product> products = new ArrayList<>();
        try
        {
            ControlManager.getInstance().addCategory("category1",products);
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        try
        {
            ControlManager.getInstance().addCategory("category2",products);
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        try
        {
            ControlManager.getInstance().addCategory("category3",products);
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        String actual = ManagerManageCategoriesUI.getInstance().categoriesTest();
        String expected = "category1"+"category2"+"category3";
        Assert.assertEquals(expected,actual);
    }

}

