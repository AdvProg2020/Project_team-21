package Client.GUIControllers.ManagerAccount;

import Client.ClientCenter;
import Client.GUIControllers.Error;
import Client.GUIControllers.GraphicFather;
import Client.ServerRequest;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class CreateCategory extends GraphicFather {

    public TextField name;
    public TextField productsToAdd;
    public Label alertLabel;

    public void done(MouseEvent mouseEvent) {
        if(!name.getText().isEmpty()){
            String allProducts = "NULL";
            if(!productsToAdd.getText().isEmpty()){
                String[] productNames = productsToAdd.getText().split(",");
                int i = 0;
                for (String productName : productNames) {
                    productName = productName.trim();
                    if(i!=0)
                        allProducts += "&";
                    else
                        allProducts = "";
                    allProducts += productName;
                    i++;
                }
            }

            ClientCenter.getInstance().sendReqToServer(ServerRequest.POSTCREATECATEGORY,allProducts + "//" + name.getText());
            String response = ClientCenter.getInstance().readMessageFromServer();
            if(response.startsWith(ServerRequest.DONE.toString())){
                showError(alertLabel,"Category successfully been made without these products cause they don't exist!:" + ClientCenter.getInstance().getMessageFromError(response),Error.POSITIVE);
            }else{
                showError(alertLabel,ClientCenter.getInstance().getMessageFromError(response), Error.NEGATIVE);
            }
        }else{
            showError(alertLabel,"You should give it a name.",Error.NEGATIVE);
        }


//        ArrayList<Product> products = new ArrayList<>();
//        ArrayList<String> productsNotExist = new ArrayList<>();
//        String[] productNames = productsToAdd.getText().split(",");
//        for (String productName : productNames) {
//            productName = productName.trim();
//            if(Product.getAllProducts().containsKey(productName)){
//                products.add(Product.getAllProducts().get(productName));
//            }
//            else {
//                productsNotExist.add(productName);
//            }
//        }

//        try {
//            ControlManager.getInstance().addCategory(name.getText(),products);
//            showError(alertLabel,"Category successfully been made without these products cause they don't exist!:" + productsNotExist,Error.POSITIVE);
//            Product.rewriteFiles();
//        } catch (Exception e) {
//            showError(alertLabel,e.getMessage(), Error.NEGATIVE);
//        }
    }
}
