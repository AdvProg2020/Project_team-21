package Client.GUIControllers.ManagerAccount;

import Server.Controller.ControlManager;
import Client.GUIControllers.GraphicFather;
import Server.Model.Account.Seller;
import Server.Model.Off;
import Server.Model.Product;
import Server.Model.Request.OffRequest;
import Server.Model.Request.ProductRequest;
import Server.Model.Request.Request;
import Server.Model.Request.SellerRequest;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewRequest extends GraphicFather implements Initializable {
    public Label info;
    public Label field1Info;
    public Label field2Info;
    public Label field3Info;
    public Label field4Info;
    public Label field5Info;
    public Label field6Info;
    public Label field1;
    public Label field2;
    public Label field3;
    public Label field4;
    public Label field5;
    public Label field6;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            String requestId = ControlManager.getInstance().getRequestToView();
            Request request = Request.getAllRequests().get(requestId);
            if(request instanceof SellerRequest)
            {
                info.setText("A seller with these infos is waiting for you!");
                Seller seller = Request.getRequestedSellers().get(requestId);
                field1.setText("Username");
                field2.setText("Name");
                field3.setText("Email");
                field4.setText("Phone Number");
                field5.setText("Company");
                field6.setText("Password");
                field1Info.setText(seller.getUsername());
                field2Info.setText(seller.getFirstName() + " " + seller.getLastName());
                field3Info.setText(seller.getEmail());
                field4Info.setText(seller.getPhoneNumber());
                field5Info.setText(seller.getCompany().getName());
                field6Info.setText(seller.getPassword());
            }
            else if(request instanceof OffRequest)
            {
                info.setText("An Off with these infos is waiting for you!");
                Off off = Request.getRequestedOffs().get(requestId);
                field1.setText("Request Type");
                field1Info.setText(request.getType() + " " + request.getRequestType());
                field2.setText("OFF ID");
                field2Info.setText(off.getOffId());
                field3.setText("Start Time");
                field3Info.setText(off.getStartTime().toString());
                field4.setText("End Time");
                field4Info.setText(off.getEndTime().toString());
                field5.setText("Off percentage");
                field5Info.setText(Double.toString(off.getOffAmount()));
                field6.setText("");
                field6Info.setText("");
            }
            else if(request instanceof ProductRequest)
            {
                info.setText("A Product with these infos is waiting for you!");
                Product product = Request.getRequestedProducts().get(requestId);
                field1.setText("Request Type" );
                field1Info.setText(request.getType() + " " + request.getRequestType());
                field2.setText("Name");
                field2Info.setText(product.getName());
                field3.setText("ID");
                field3Info.setText(product.getProductId());
                field4.setText("Company Name");
                field4Info.setText(product.getCompany().getName());
                field5.setText("Price");
                field5Info.setText(Double.toString(product.getPrice()));
                field6.setText("Category");
                field6Info.setText(product.getCategory().getName());
            }
    }
}
