package GUIControllers.SellerAccount;

import GUIControllers.GraphicFather;
import Model.Category;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class Categories extends GraphicFather implements Initializable {
    public TableView<Category> listCategories;
    public TableColumn<Category,String> name = new TableColumn<>("Name");
    ObservableList<Category> getCategories(){
        ObservableList<Category> result =  FXCollections.observableArrayList();
        for (Category category : Category.getAllCategories()) {
            result.add(category);
        }
        return result;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listCategories.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        listCategories.setItems(getCategories());
        listCategories.getColumns().add(name);
    }
}
