package GUIControllers;

import Model.Account.Seller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class GUICenter {
   private static GUICenter instance;
//    private ArrayList<Scene> seenPages;
//    private Scene currentMenu;

    private ArrayList<Page> seenPages;
    private Page currentMenu;
    private Scene currentScene;

    private Page landing;
    private Seller sellerToAddCompany;
    private GUICenter()
    {
        seenPages = new ArrayList<>();
    }
    public static GUICenter getInstance() {
        if(instance == null)
        {
            instance = new GUICenter();
        }
        return instance;
    }

    public Seller getSellerToAddCompany() {
        return sellerToAddCompany;
    }

    public void setSellerToAddCompany(Seller sellerToAddCompany) {
        this.sellerToAddCompany = sellerToAddCompany;
    }

    public void setLanding(Page landing) {
        this.landing = landing;
    }

    public Page getLanding() {
        return landing;
    }

    public void setCurrentScene(Scene currentScene) {
        this.currentScene = currentScene;
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    //    public ArrayList<Scene> getSeenPages() {
//        return seenPages;
//    }
//    public void addSeenPage(Scene scene){
//        seenPages.add(scene);
//    }
//    public void removeFromScenePage(int index){seenPages.remove(index);}
//
//    public Scene getCurrentMenu() {
//        return currentMenu;
//    }
//
//    public void setCurrentMenu(Scene currentMenu) {
//        this.currentMenu = currentMenu;
//    }
    public ArrayList<Page> getSeenPages() {
        return seenPages;
    }
    public void addSeenPage(Page page){
        seenPages.add(page);
    }
    public void removeFromScenePage(int index){seenPages.remove(index);}

    public Page getCurrentMenu() {
        return currentMenu;
    }

    public void setCurrentMenu(Page currentMenu) {
        this.currentMenu = currentMenu;
    }

    public Scene getSceneFromPage(Page page) throws IOException {
        Scene result = null;
        Parent template = null;
        if(page.equals(Page.MAIN)){
            template = FXMLLoader.load(getClass().getResource("/fxml/MainPage.fxml"));
        }
        else if(page.equals(Page.MAKEMANAGERFIRST)){
            template = FXMLLoader.load(getClass().getResource("/fxml/MakeManagerFirst.fxml"));
        }
        else if(page.equals(Page.SIGNIN)){
            template = FXMLLoader.load(getClass().getResource("/fxml/SignInPage.fxml"));
        }
        else if(page.equals(Page.SIGNUP)){
            template = FXMLLoader.load(getClass().getResource("/fxml/SignUpPage.fxml"));
        }
        else if(page.equals(Page.MANAGER)){
            template = FXMLLoader.load(getClass().getResource("/fxml/ManagerAccount/ManagerAccount.fxml"));
        }
        else if(page.equals(Page.SELLER)){
            template = FXMLLoader.load(getClass().getResource("/fxml/SellerAccount/SellerAccount.fxml"));
        }
        else if(page.equals(Page.CUSTOMER)){
            template = FXMLLoader.load(getClass().getResource("/fxml/CustomerAccount/CustomerAccount.fxml"));
        }
        else if(page.equals(Page.CREATECOMPANY)){
            template = FXMLLoader.load(getClass().getResource("/fxml/CreateCompany.fxml"));
        }
        else if(page.equals(Page.MANAGEUSERS)){
            template = FXMLLoader.load(getClass().getResource("/fxml/ManagerAccount/ManageUsers.fxml"));
        }
        else if(page.equals(Page.MANAGEREQUESTS)){
            template = FXMLLoader.load(getClass().getResource("/fxml/ManagerAccount/ManageRequests.fxml"));
        }
        else if(page.equals(Page.MANAGEPRODUCTS)){
            template = FXMLLoader.load(getClass().getResource("/fxml/ManagerAccount/ManageProducts.fxml"));
        }
        else if(page.equals(Page.MANAGEDISCOUNTCODES)){
            template = FXMLLoader.load(getClass().getResource("/fxml/ManagerAccount/ManageDiscountCodes.fxml"));
        }
        else if(page.equals(Page.MANAGECATEGORIES)){
            template = FXMLLoader.load(getClass().getResource("/fxml/ManagerAccount/ManageCategories.fxml"));
        }
        else if(page.equals(Page.EDITFIELDSMANAGER)){
            template = FXMLLoader.load(getClass().getResource("/fxml/ManagerAccount/EditFieldManager.fxml"));
        }
        else if(page.equals(Page.VIEWUSER)){
            template = FXMLLoader.load(getClass().getResource("/fxml/ManagerAccount/ViewUsername.fxml"));
        }
        else if(page.equals(Page.CREATEMANAGER)){
            template = FXMLLoader.load(getClass().getResource("/fxml/ManagerAccount/CreateManager.fxml"));
        }
        else if(page.equals(Page.EDITDISCOUNTCODE)){
            template = FXMLLoader.load(getClass().getResource("/fxml/ManagerAccount/EditDiscountCode.fxml"));
        }
        else if(page.equals(Page.VIEWDISCOUNTCODE)){
            template = FXMLLoader.load(getClass().getResource("/fxml/ManagerAccount/ViewDiscountCode.fxml"));
        }
        else if(page.equals(Page.CREATEDISCOUNTCODE)){
            template = FXMLLoader.load(getClass().getResource("/fxml/ManagerAccount/CreateDiscountCode.fxml"));
        }
        else if(page.equals(Page.VIEWREQUEST)){
            template = FXMLLoader.load(getClass().getResource("/fxml/ManagerAccount/ViewRequest.fxml"));
        }
        else if(page.equals(Page.CREATECATEGORY)){
            template = FXMLLoader.load(getClass().getResource("/fxml/ManagerAccount/CreateCategory.fxml"));
        }
        else if(page.equals(Page.EDITCATEGORY)){
            template = FXMLLoader.load(getClass().getResource("/fxml/ManagerAccount/EditCategory.fxml"));
        }
        else if(page.equals(Page.SALESHISTORY)){
            template = FXMLLoader.load(getClass().getResource("/fxml/SellerAccount/SalesHistory.fxml"));
        }
        else if(page.equals(Page.SELLEROFFS)){
            template = FXMLLoader.load(getClass().getResource("/fxml/SellerAccount/OFFs.fxml"));
        }
        else if(page.equals(Page.SELLERPRODUCTS)){
            template = FXMLLoader.load(getClass().getResource("/fxml/SellerAccount/Products.fxml"));
        }
        else if(page.equals(Page.SELLERCATEGORIIES)){
            template = FXMLLoader.load(getClass().getResource("/fxml/SellerAccount/Categories.fxml"));
        }
        else if(page.equals(Page.EDITFIELDSELLER)){
            template = FXMLLoader.load(getClass().getResource("/fxml/SellerAccount/EditFieldSeller.fxml"));
        }
        else if(page.equals(Page.VIEWOFF)){
            template = FXMLLoader.load(getClass().getResource("/fxml/SellerAccount/ViewOFF.fxml"));
        }
        else if(page.equals(Page.EDITOFF)){
            template = FXMLLoader.load(getClass().getResource("/fxml/SellerAccount/EditOFF.fxml"));
        }
        else if(page.equals(Page.CREATEOFF)){
            template = FXMLLoader.load(getClass().getResource("/fxml/SellerAccount/CreateOFF.fxml"));
        }
        else if(page.equals(Page.VIEWPRODUCT)){
            template = FXMLLoader.load(getClass().getResource("/fxml/SellerAccount/ViewProduct.fxml"));
        }
        else if(page.equals(Page.VIEWBUYERSPRODUCT)){
            template = FXMLLoader.load(getClass().getResource("/fxml/SellerAccount/ViewBuyersProduct.fxml"));
        }
        else if(page.equals(Page.EDITPRODUCT)){
            template = FXMLLoader.load(getClass().getResource("/fxml/SellerAccount/EditProduct.fxml"));
        }
        else if(page.equals(Page.CREATEPRODUCT)){
            template = FXMLLoader.load(getClass().getResource("/fxml/SellerAccount/CreateProduct.fxml"));
        }
//        result =  new Scene(template,currentMenu.getWidth(),currentMenu.getHeight());
        result =  new Scene(template,currentScene.getWidth(),currentScene.getHeight());
        return result;
    }
}
