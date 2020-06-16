package Controller;


import Model.*;
import Model.Account.Account;
import Model.Account.Customer;
import Model.Account.Manager;
import Model.Account.Seller;
import Model.DisAndOffStatus;
import Model.Off;
import Model.Filters.*;
import Model.Sorts.ProductsSort;

import java.nio.charset.Charset;
import java.util.*;

public class Control {

    public static ArrayList<Filter> currentFilters = new ArrayList<>();
    public static Category currentCategory = null;
    public static String currentProductSort = "";
    public static String currentUserSort = "";
    public static String currentRequestSort = "";
    Account user = null;
    ShoppingCart signOutCart = new ShoppingCart(null);
    private static Control instance;

    private Control() {
    }

    public ShoppingCart getSignOutCart() {
        return signOutCart;
    }

    public void setSignOutCart(ShoppingCart signOutCart) {
        this.signOutCart = signOutCart;
    }

    public String randomString(int n) {

        // length is bounded by 256 Character
        byte[] array = new byte[256];
        new Random().nextBytes(array);

        String randomString
                = new String(array, Charset.forName("UTF-8"));

        // Create a StringBuffer to store the result
        StringBuffer r = new StringBuffer();

        // remove all spacial char
        String AlphaNumericString
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

    public static Control getInstance() {
        if (instance == null)
            instance = new Control();
        return instance;
    }

    public Account getUser() {
        if (user == null)
            return null;
        return user;
    }

    public void setUser(Account user) {
        this.user = user;
    }

    public void showPopularProducts()
    {
    }

    public void showSales() {

    }

    public void login(String userName, String password) throws notFoundUserOrPass {
        if (!(Account.getAllAccounts().containsKey(userName))){
            throw new notFoundUserOrPass("userName didn't found!");
        } else if (!(Account.getAllAccounts().get(userName).getPassword().equals(password))) {
            throw new notFoundUserOrPass("password didn't match!");
        }
        Account account = Account.getAllAccounts().get(userName);
        setUser(account);
        if(account instanceof Customer)
        {
            signOutCart.setCustomer(account);
            ((Customer) account).setShoppingCart(signOutCart);
        }
    }

    public void createAccount(String type, String username, String password, String firstName, String lastName, String email, String phoneNumber, String verifyPassword, Company company, boolean login, String photo) throws Exception {
        //5 errors
        if (!verifyPassword.equals(password)) {
            throw new Exception("Your password doesn't match");
        }
        if (checkIfCustomer()) {
            throw new Exception("You are a customer and can't make a new user");
        }
        if (!(user instanceof Manager) && type.equalsIgnoreCase("manager") && !Manager.getAllManagers().isEmpty()) {
            throw new Exception("You should be a manager to create a manager account");
        }
        if (!(type.matches("(?i)customer|manager|seller"))) {
            throw new Exception("There is no type of account like that!");
        }
        if (Account.getAllAccounts().containsKey(username)) {
            throw new Exception("This username already exists!");
        }
        if(username.isEmpty()||type.isEmpty()||password.isEmpty()||firstName.isEmpty()||lastName.isEmpty()||email.isEmpty()||phoneNumber.isEmpty()||verifyPassword.isEmpty()){
            throw new Exception("You should fill all of the fields");
        }

        if (type.equalsIgnoreCase("manager")) {
            ControlManager.getInstance().createAccount(username, password, firstName, lastName, email, phoneNumber,photo);
        } else if (type.equalsIgnoreCase("seller")) {
            ControlSeller.getInstance().createAccount(username, password, firstName, lastName, email, phoneNumber, company,photo);
        } else if (type.equalsIgnoreCase("customer")) {
            ControlCustomer.getInstance().createAccount(username, password, firstName, lastName, email, phoneNumber,photo);
        }
        if (login)
            login(username, password);
    }

    public boolean checkIfCustomer() {
        if (Control.getInstance().getUser() != null && Control.getInstance().getUser() instanceof Customer) {
            return true;
        }
        return false;
    }

    public void deleteUser(String username) throws Exception {
        if (!Account.getAllAccounts().keySet().contains(username)) {
            throw new Exception("This username doesn't exist!");
        }
        if (Account.getAllAccounts().get(username).equals(user)) {
            throw new Exception("you can't delete yourself!");
        }
        if (Account.getAllAccounts().get(username) instanceof Manager) {
            Manager.removeManager((Manager) Account.getAllAccounts().get(username));
        } else if (Account.getAllAccounts().get(username) instanceof Seller) {
            Seller.removeSeller((Seller) Account.getAllAccounts().get(username));
        } else if (Account.getAllAccounts().get(username) instanceof Customer) {
            Customer.removeCustomer((Customer) Account.getAllAccounts().get(username));
        }
        Account.getAllAccounts().remove(username);
    }
    public boolean timeCorrectMatch(String time)
    {
        if(time.matches("\\d{4}\\s+\\d{1,2}\\s+\\d{1,2}\\s+\\d{1,2}\\s+\\d{1,2}")) {
            return true;
        }
        return false;
    }
    public boolean doubleCheck(String string)
    {
        if(string.matches("\\d+.?\\d*"))
            return true;
        return false;
    }

    public void sortHashmap(HashMap map) {
        TreeMap<String, Integer> sorted = new TreeMap<>();

        // Copy all data from hashMap into TreeMap
        sorted.putAll(map);

        map.putAll(sorted);
    }

    public void sortHashmapByFeature(HashMap map) {

    }

    public void sortArraylist(ArrayList list) {

    }

    public void logout() {
        setUser(null);
    }

    public class notFoundUserOrPass extends Exception {
        notFoundUserOrPass(String message) {
            super(message + "\ntry again");
        }
    }

    public String showAvailableFilters() {
        String availableFilters = "";
        availableFilters = availableFilters.concat("CompanyName(Brand)\nPrice\nName\nAvailable\nSeller\n");
        if (currentCategory == null) {
            availableFilters = availableFilters.concat("Category\n");
        }
        else {
            for (String specialFeature : currentCategory.getSpecialFeatures()){
                availableFilters=availableFilters.concat(specialFeature+"\n");
            }
        }

        return availableFilters;
    }

    public String createFilter(String filterType, String filterInput) {
        Filter filter = null;
        if (filterType.equalsIgnoreCase("Brand")) {
            filter = new CompanyNameFilter(filterInput, currentCategory == null ? Product.allProductsList : currentCategory.getProductsList());
        } else if (filterType.equalsIgnoreCase("Price")) {
            String[] words = filterInput.split("[\\s-,_]");
            filter = new PriceFilter(Double.parseDouble(words[0]), Double.parseDouble(words[1]), currentCategory == null ? Product.allProductsList : currentCategory.getProductsList());
        } else if (filterType.equalsIgnoreCase("Name")) {
            filter = new ProductNameFilter(filterInput, currentCategory == null ? Product.allProductsList : currentCategory.getProductsList());
        } else if (filterType.equalsIgnoreCase("Seller")) {
            filter = new SellerFilter(filterInput, currentCategory == null ? Product.allProductsList : currentCategory.getProductsList());
        } else if (filterType.equalsIgnoreCase("Available")) {
            filter = new InStockFilter(currentCategory == null ? Product.allProductsList : currentCategory.getProductsList());
        } else if (currentCategory == null && filterType.equalsIgnoreCase("Category")) {
            filter = new CategoryFilter(filterInput, Product.allProductsList);
        }else if (currentCategory != null){
            for (String specialFeature : currentCategory.getSpecialFeatures()){
                if (filterType.equalsIgnoreCase(specialFeature))
                    filter= new FeaturesFilter(specialFeature,filterInput,currentCategory.getProductsList());
            }
        }
        if (filter == null)
            return "Wrong Filter!";
        currentFilters.add(filter);
        return "Filter was created";
    }

    public String createFilterForOffProducts(String filterType, String filterInput) {
        Filter filter = null;
        if (filterType.equalsIgnoreCase("Brand")) {
            filter = new CompanyNameFilter(filterInput, currentCategory == null ? Product.allProductWithOff : currentCategory.getProductsList());
        } else if (filterType.equalsIgnoreCase("Price")) {
            String[] words = filterInput.split("[\\s-,_]");
            filter = new PriceFilter(Double.parseDouble(words[0]), Double.parseDouble(words[1]), currentCategory == null ? Product.allProductWithOff : currentCategory.getProductsList());
        } else if (filterType.equalsIgnoreCase("Name")) {
            filter = new ProductNameFilter(filterInput, currentCategory == null ? Product.allProductWithOff : currentCategory.getProductsList());
        } else if (filterType.equalsIgnoreCase("Seller")) {
            filter = new SellerFilter(filterInput, currentCategory == null ? Product.allProductWithOff : currentCategory.getProductsList());
        } else if (filterType.equalsIgnoreCase("Available")) {
            filter = new InStockFilter(currentCategory == null ? Product.allProductWithOff : currentCategory.getProductsList());
        } else if (currentCategory == null && filterType.equalsIgnoreCase("Category")) {
            filter = new CategoryFilter(filterInput, Product.allProductWithOff);
        }else if (currentCategory != null){
            for (String specialFeature : currentCategory.getSpecialFeatures()){
                if (filterType.equalsIgnoreCase(specialFeature))
                    filter= new FeaturesFilter(specialFeature,filterInput,currentCategory.getProductsList());
            }
        }
        if (filter == null)
            return "Wrong Filter!";
        currentFilters.add(filter);
        return "Filter was created";
    }



    public String showCurrentFilters() {
        return Filter.showCurrentFilters();
    }

    public String disableFilter(String filterType) {
        boolean disabled = false;
        for (Filter currentFilter : currentFilters) {
            if (currentFilter.getName().equals(filterType)) {
                currentFilters.remove(currentFilter);
                disabled = true;
            }
        }
        if (disabled)
            return "Filter removed successfully";
        else
            return "Could not disable filter";

    }

    public String showAvailableSorts (String listType){
        if (listType.equalsIgnoreCase("product"))
            return "Sort by : name A-Z\n Sort by: name Z-A\n Sort by: view\n Sort by: score\nSort by: price ascending\nSort by: price descending";
        else if (listType.equalsIgnoreCase("request"))
            return "Sort by: type\nSort by: Id\n";
        else if (listType.equalsIgnoreCase("user"))
            return "Sort by: first name A-Z\nSort by: last name A-Z\nSort by: first name Z-A\nSort by: last name Z-A\n";
        return "";
    }

    public String makeSort(String sortType, String listType) {
        if ((listType.equalsIgnoreCase("product")) &&
                (sortType.equalsIgnoreCase("name A-Z") ||
                        sortType.equalsIgnoreCase("name Z-A") ||
                        sortType.equalsIgnoreCase("view") ||
                        sortType.equalsIgnoreCase("average score") ||
                        sortType.equalsIgnoreCase("price ascending") ||
                        sortType.equalsIgnoreCase("price descending"))) {
            currentProductSort = sortType;
        } else if ((listType.equalsIgnoreCase("request")) &&
                (sortType.equalsIgnoreCase("type") ||
                        sortType.equalsIgnoreCase("id"))) {
            currentRequestSort = sortType;
        } else if ((listType.equalsIgnoreCase("user")) &&
                (sortType.equalsIgnoreCase("first name A-Z") ||
                        sortType.equalsIgnoreCase("first name Z-A") ||
                        sortType.equalsIgnoreCase("last name A-Z") ||
                        sortType.equalsIgnoreCase("last name Z-A"))) {
            currentUserSort = sortType;
        } else return "wrong sort type!";
        return "sort applied successfully.";
    }

    public String showCurrentProductSort() {
        if (currentProductSort.equals(""))
            return "No sorts selected";
        return currentProductSort;
    }

    public String showCurrentUserSort() {
        if (currentUserSort.equals(""))
            return "No sorts selected";
        return currentUserSort;
    }

    public String showCurrentRequestSort() {
        if (currentRequestSort.equals(""))
            return "No sorts selected";
        return currentRequestSort;
    }

    public String disableSort() {
        currentProductSort = "";
        currentRequestSort = "";
        currentUserSort = "";
        return "Sort disabled";
    }

    public String showFilteredAndSortedProducts() throws Exception {
        String filtered = "";
        ArrayList<Product> sorted = Filter.applyFilter(Product.allProductsList);
        if (currentProductSort.equalsIgnoreCase("name A-Z"))
            sorted.sort(new ProductsSort.productSortByNameAscending());
        else if (currentProductSort.equalsIgnoreCase("name Z-A"))
            sorted.sort(new ProductsSort.productSortByNameDescending());
        else if (currentProductSort.equalsIgnoreCase("average Score"))
            sorted.sort(new ProductsSort.productSortByRate());
        else if (currentProductSort.equalsIgnoreCase("price ascending"))
            sorted.sort(new ProductsSort.productSortByPriceAscending());
        else if (currentProductSort.equalsIgnoreCase("price descending"))
            sorted.sort(new ProductsSort.productSortByPriceDescendingly());
        else if (!currentProductSort.equals(""))
            return "wrong sort input.";
        for (Product product : sorted) {
            filtered = filtered.concat(product.showProductDigest());
            filtered = filtered.concat("\n\n");
        }
        if (filtered.equals(""))
            return showAllProducts();
        return filtered;
    }

    public String showFilteredAndSortedProductsWithOff() throws Exception {
        String filtered = "";
        ArrayList<Product> sorted = Filter.applyFilter(Product.allProductWithOff);
        if (currentProductSort.equalsIgnoreCase("name A-Z"))
            sorted.sort(new ProductsSort.productSortByNameAscending());
        else if (currentProductSort.equalsIgnoreCase("name Z-A"))
            sorted.sort(new ProductsSort.productSortByNameDescending());
        else if (currentProductSort.equalsIgnoreCase("average Score"))
            sorted.sort(new ProductsSort.productSortByRate());
        else if (currentProductSort.equalsIgnoreCase("price ascending"))
            sorted.sort(new ProductsSort.productSortByPriceAscending());
        else if (currentProductSort.equalsIgnoreCase("price descending"))
            sorted.sort(new ProductsSort.productSortByPriceDescendingly());
        else if (!currentProductSort.equals(""))
            return "wrong sort input.";
        for (Product product : sorted) {
            filtered = filtered.concat(product.showProductDigest());
            filtered = filtered.concat("\n\n");
        }
        if (filtered.equals(""))
            return showAllProducts();
        return filtered;
    }

    public String showAllOffProducts(){
        String offProducts="";
        for (Off allOff : Off.allOffsList){
            if (allOff.getDisAndOffStatus().equals(DisAndOffStatus.Expired))
                allOff.removeOff();
            else {
                for (Product product : allOff.getProductsList()){
                    offProducts=offProducts.concat(product.showProductDigest());
                    offProducts = offProducts.concat("\n\n");
                }
            }
        }
        if (offProducts.equals(""))
            return "No products with off to show";
        return offProducts;
    }
    public void addToCart(Product product,String sellerUsername, String quantity) throws Exception
    {
        boolean sellerExists = false;
        Seller seller = null;
        for (Seller seller1 : Seller.getAllSeller())
        {
            if(seller1.getUsername().equals(sellerUsername))
            {
                sellerExists = true;
                seller = seller1;
                break;
            }
        }
        if(!sellerExists)
        {
            throw new Exception("This seller does not exist!");
        }
        if(!quantity.matches("\\d+"))
        {
            throw new Exception("Your quantity format is wrong!");
        }
        if(user == null)
        {
            signOutCart.addProduct(product,Integer.parseInt(quantity),seller);
        }
        else if(!(user instanceof Customer))
        {
            throw new Exception("You should be a customer to buy stuff.");
        }
        else
        {
            ((Customer)user).getShoppingCart().addProduct(product,Integer.parseInt(quantity),seller);
        }
    }

    public String showAllProducts(){
        String allProducts="";
        for (Product allProduct : Product.allProductsList){
            allProducts = allProducts.concat(allProduct.showProductDigest());
            allProducts=allProducts.concat("\n\n");
        }
        if (allProducts.equals("")){
            return "No Product to show!";
        }
        return allProducts;
    }
    public String offOutput(Off off)
    {
        if(off == null)
        {
            return "No off";
        }
        else
            return off.toString();
    }


    public Map<String, Account> sortAllAccounts() {
        Map<String, Account> allAccounts = Account.getAllAccounts();
        /* Sort statement */
        allAccounts = sortByKey(allAccounts);
        return allAccounts;
    }


    public Map<String, Account> sortByKey(Map map) {
        // TreeMap to store values of HashMap
        TreeMap<String, Account> sorted = new TreeMap<>();
        return sorted;
    }



}
