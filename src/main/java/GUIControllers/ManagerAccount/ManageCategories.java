package GUIControllers.ManagerAccount;

import Controller.ControlManager;
import GUIControllers.Error;
import GUIControllers.GraphicFather;
import GUIControllers.Page;
import Model.Category;
import Model.Request.Request;
import View.ConsoleView;
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
    public TableColumn<Category,String> name = new TableColumn<>("Name");
    public Label alertLabel;
    public TextField categoryToEdit;

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
        String categoryName = categoryToRemove.getText();
        if(!ControlManager.getInstance().checkCategoryExistance(categoryName))
        {
            showError(alertLabel,"This category doesn't exist!", Error.NEGATIVE);
        }
        else
        {
            for (Category category : Category.getAllCategories()) {
                if(category.getName().equalsIgnoreCase(categoryName))
                    Category.removeCategory(category);
            }
        }
    }

    public void addCategory(MouseEvent mouseEvent) {
        goToNextPage(Page.CREATECATEGORY,mouseEvent);
    }

    public void editCategory(MouseEvent mouseEvent) {
        if(ControlManager.getInstance().checkCategoryExistance(categoryToEdit.getText())){
            ControlManager.getInstance().setCategoryToEdit(categoryToEdit.getText());
            goToNextPage(Page.EDITCATEGORY,mouseEvent);
        }else{
            showError(alertLabel,"This category doesn't exist!",Error.NEGATIVE);
        }

    }
}
