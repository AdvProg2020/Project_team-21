package Model;

import Model.*;
import org.junit.Test;
import org.junit.Assert;

public class AccountsTest {

    String expected = new String();
    String real = new String();


    @Test
    public void usernameTest(){
        expected="Invalid Username!";
        try {
            new Customer("ss&&" , "" ,"","","","",null,0);
        }
        catch (Exception e){
            real=e.getMessage();
        }
        Assert.assertEquals(expected,real);
    }

    @Test
    public void firstnameTest(){
        expected="Invalid FirstName!";
        try {
            new Customer("Tizi","7869","","","","",null,0);
        }
        catch (Exception e){
            real=e.getMessage();
        }
        Assert.assertEquals(expected,real);
    }

    @Test
    public void weakPasswordTest(){
        expected="Invalid Password!";
        try {
            new Customer("Tizi","Babak","","","","salam chetori?",null,0);
        }
        catch (Exception e){
            real= e.getMessage();
        }
        Assert.assertEquals(expected,real);
    }

    @Test
    public void invalidEmail(){
        expected="Invalid Email!";
        try {
            new Customer("Tizi","Babak","Ghahremani","BabakTizi@nadaram","","1234",null,0);
        }
        catch (Exception e){
            real=e.getMessage();
        }
        Assert.assertEquals(expected,real);
    }

}
