package Client.GUIControllers.SellerAccount;

import Client.ClientCenter;
import Client.GUIControllers.GraphicFather;
import Client.Model.Category;
import Client.ServerRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Categories extends GraphicFather implements Initializable {
    public TableView<Category> listCategories;
    public TableColumn<Category,String> name = new TableColumn<>("Name");
    private ArrayList<Category> allCategories = new ArrayList<>();

    ObservableList<Category> getCategories(){
        ObservableList<Category> result =  FXCollections.observableArrayList();
        result.addAll(allCategories);
        return result;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ClientCenter.getInstance().sendReqToServer(ServerRequest.GETALLCATEGORIES);
        String response = ClientCenter.getInstance().readMessageFromServer();
        if(!response.equalsIgnoreCase("NONE")){
            String[] allCategoriesString = response.split(" - ");
            for (String category : allCategoriesString) {
                allCategories.add(new Category(category));
            }
        }
        listCategories.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        listCategories.setItems(getCategories());
        listCategories.getColumns().add(name);
    }
}
