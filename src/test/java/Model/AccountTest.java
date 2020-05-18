package Model;

import Controller.Control;
import Model.*;
import Model.Account.Customer;
import Model.Account.Seller;
import org.junit.Test;
import org.junit.Assert;

public class AccountTest {

    String expected = new String();
    String real = new String();
    @Test
    public void addToCartTest()
    {
        try {
            Control.getInstance().addToCart(new Product("342354634","soup",new Company("apple","california"),
                    20,new Category("soups",null),new Seller("opo","jkasdj","asdf","asdf",
                    "32423","asdf",new Company("asdf","dfh"))),"awfsd","34");
        }catch (Exception e)
        {

        }

    }

}

