package Controller;

import Model.*;
import Model.Account.Customer;
import Model.Account.Seller;
import Model.Log.BuyLog;
import Model.Log.SellLog;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class ControlCustomer {
    private static ControlCustomer instance;
    private ControlCustomer()
    {

    }
    public static ControlCustomer getInstance()
    {
        if(instance == null)
            instance = new ControlCustomer();
        return instance;
    }
    public void createAccount(String username, String password, String firstName, String lastName, String email, String phoneNumber,String photo)
    {
       Customer customer = new Customer(username,firstName,lastName,email,phoneNumber,password,photo);
    }
    private boolean checkProductExistance(String productID)
    {
        if(Product.getAllProducts().containsKey(productID))
            return true;
        return false;
    }
    public void increaseOrDecreaseProductInCart(String productID,Customer customer,boolean increase) throws Exception
    {
        if(!checkProductExistance(productID))
        {
            throw new Exception("This product doesn't exist!");
        }
        Product product = Product.getAllProducts().get(productID);
        if(!customer.getShoppingCart().getProductsQuantity().containsKey(product))
        {
            throw new Exception("You don't have this product");
        }
        if(increase)
        {
            customer.getShoppingCart().increaseQuantity(product);
        }
        else
        {
            customer.getShoppingCart().decreaseQuantity(product);
        }
    }
    private double calculateTotalPrice(ArrayList<Product> products,double totalOff)
    {
        double price = 0;
        for (Product product : products) {
            price+=product.getPrice();
        }
        return price - totalOff;
    }
    private double calculateTotalOffCart(HashMap<Seller,ArrayList<Product>> productsSellers)
    {
        double totalOffMoneyForCart = 0;
        for (Seller seller : productsSellers.keySet())
        {
            ArrayList<Product> products = productsSellers.get(seller);
            for (Product product : products)
            {
                for (Off off : seller.getAllOffs())
                {
                    if(off.getProductsList().contains(product))
                    {
                        totalOffMoneyForCart += product.getPrice()*(off.getOffAmount()/100);
                        break;
                    }
                }
            }
          }
        return totalOffMoneyForCart;
    }
    private boolean canPay(double totalPrice,double discount,Customer customer)
    {
        if(customer.getBalance() >= totalPrice-discount)
            return true;
        return false;
    }
    public String purchase(Customer customer, String receiverName, String receiverAddress
            , String receiverPhoneNo, String receiverPostalCode,String discountCode) throws Exception
    {
        String logID = Control.getInstance().randomString(10);
        ShoppingCart cart = customer.getShoppingCart();
        if(!customer.getDiscountCodes().containsKey(discountCode) && !discountCode.equalsIgnoreCase("next"))
        {
            throw new Exception("You don't have this discount code!");
        }
        if(!receiverPhoneNo.matches("\\d+"))
        {
            throw new Exception("Your phone number format is wrong!");
        }
        if(!receiverPostalCode.matches("\\d+"))
        {
            throw new Exception("Your postal code format is wrong!");
        }
        HashMap<Seller, ArrayList<Product>> productsSellers = new HashMap<>();
        for (Product product : cart.getProductSeller().keySet())
        {
            Seller seller = cart.getProductSeller().get(product);
            if(productsSellers.containsKey(seller))
            {
                productsSellers.get(seller).add(product);
            }
            else
            {
                ArrayList <Product> products = new ArrayList<>();
                products.add(product);
                productsSellers.put(seller,products);
            }
        }

        ArrayList<Product> cartProducts = new ArrayList<>();
        ArrayList<String> cartSellers = new ArrayList<>();
        for (Product product : cart.getProductsQuantity().keySet())
        {
            cartProducts.add(product);
        }
        for (Seller seller : productsSellers.keySet())
        {
            cartSellers.add(seller.getUsername());
        }
        DiscountCode discount = DiscountCode.getAllDiscountCodes().get(discountCode);
        double price = cart.getPrice() - calculateTotalOffCart(productsSellers);
        double totalDiscountAmount = price*(discount.getDiscountPercentage()/100);
        if(totalDiscountAmount > discount.getMaxDiscountAmount())
        {
            totalDiscountAmount = discount.getMaxDiscountAmount();
        }
        if(!canPay(price,totalDiscountAmount,customer))
        {
            throw new Exception("You don't have enough money.");
        }
        new BuyLog(logID,LocalDateTime.now(),totalDiscountAmount,price-totalDiscountAmount,cartProducts,cartSellers.get(0),
                customer.getUsername(),receiverName,receiverAddress,receiverPhoneNo,receiverPostalCode,cartSellers);
        customer.addDiscountUse(discount);
        customer.setBalance(customer.getBalance()-(price-totalDiscountAmount));
        for (Product product : cartProducts) {
            product.addBuyer(customer);
        }

        double totalOffMoneyPerSeller;
        for (Seller seller : productsSellers.keySet())
        {
            totalOffMoneyPerSeller = 0;
            ArrayList<Product> products = productsSellers.get(seller);
            for (Product product : products)
            {
                for (Off off : seller.getAllOffs())
                {
                    if(off.getProductsList().contains(product))
                    {
                        totalOffMoneyPerSeller += product.getPrice()*(off.getOffAmount()/100);
                        break;
                    }
                }
            }
            new SellLog(Control.getInstance().randomString(10),LocalDateTime.now(),totalOffMoneyPerSeller,calculateTotalPrice(products,totalOffMoneyPerSeller),
                    products,seller.getUsername(),customer.getUsername(),receiverName,receiverAddress,receiverPhoneNo,receiverPostalCode);
            seller.setCredit(seller.getCredit() + calculateTotalPrice(products,totalOffMoneyPerSeller));
        }
        cart.clearShoppingCart();
        return logID;
    }

    public boolean checkCustomerGotOrder(String orderID,Customer customer)
    {
        for (BuyLog log : customer.getBuyLogs()) {
            if(log.getLogId().equals(orderID))
                return true;
        }
        return false;
    }

    public void rateProduct(String productID, String score,Customer customer) throws Exception
    {
        boolean hasProduct = false;
        for (BuyLog buyLog : customer.getBuyLogs())
        {
            for (Product product : buyLog.getAllProducts())
            {
                if(product.getProductId().equals(productID))
                {
                    hasProduct = true;
                    break;
                }
            }
            if(hasProduct)
                break;
        }
        if(!hasProduct)
        {
            throw new Exception("You haven't purchased this product!");
        }
        if(!score.matches("[1-5]"))
        {
            throw new Exception("Your score format is wrong. It should be number between 1 and 5.");
        }
        Product product = Product.getAllProducts().get(productID);
        product.addScore(new Score(customer,product,Integer.parseInt(score)));
    }

}
