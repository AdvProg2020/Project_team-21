package Client.GUIControllers.ManagerAccount;

import Server.Controller.ControlManager;
import Client.GUIControllers.Error;
import Client.GUIControllers.GraphicFather;
import Client.GUIControllers.Page;
import Server.Model.Category;
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
        else if(categoryName.equals("All Products")){
            showError(alertLabel,"You can't remove this category",Error.NEGATIVE);
        }
        else
        {
            for (Category category : Category.getAllCategories()) {
                if(category.getName().equalsIgnoreCase(categoryName)) {
                    Category.removeCategory(category);
                    showError(alertLabel,"Category "+categoryToRemove.getText() + " has been deleted successfully!",Error.POSITIVE);
                }
            }
        }
    }

    public void addCategory(MouseEvent mouseEvent) {
        goToNextPage(Page.CREATECATEGORY,mouseEvent);
    }

    public void editCategory(MouseEvent mouseEvent) {
        if(categoryToEdit.getText().equals("All Products")){
            showError(alertLabel,"You can't edit this category",Error.NEGATIVE);
        }
        else if(ControlManager.getInstance().checkCategoryExistance(categoryToEdit.getText())){
            ControlManager.getInstance().setCategoryToEdit(categoryToEdit.getText());
            goToNextPage(Page.EDITCATEGORY,mouseEvent);
        }else{
            showError(alertLabel,"This category doesn't exist!",Error.NEGATIVE);
        }

    }

    public void selection(MouseEvent mouseEvent) {
        Category selectedItem = listCategories.getSelectionModel().getSelectedItem();
        categoryToEdit.setText(selectedItem.getName());
        categoryToRemove.setText(selectedItem.getName());
    }
}
