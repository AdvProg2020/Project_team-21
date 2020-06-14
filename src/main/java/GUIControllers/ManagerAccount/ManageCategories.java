package GUIControllers.ManagerAccount;

import GUIControllers.GraphicFather;
import Model.Category;
import Model.Request.Request;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageCategories extends GraphicFather implements Initializable {
    public TextField categoryToRemove;
    public TableView<Category> listCategories;
    public Label AlertLabel;
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

    public void removeCategory(MouseEvent mouseEvent) {
    }

    public void addCategory(MouseEvent mouseEvent) {
    }

    public void editCategory(MouseEvent mouseEvent) {
    }
}
