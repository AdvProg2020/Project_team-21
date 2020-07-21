package Client.GUIControllers.ManagerAccount;

import Client.ClientCenter;
import Client.GUIControllers.Error;
import Client.GUIControllers.GraphicFather;
import Client.GUIControllers.Page;
import Client.Model.Category;
import Client.ServerRequest;
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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManageCategories extends GraphicFather implements Initializable {
    public TextField categoryToRemove;
    public TableView<Category> listCategories;
    public TableColumn<Category,String> name = new TableColumn<>("Name");
    public Label alertLabel;
    public TextField categoryToEdit;
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

    public void removeCategory(MouseEvent mouseEvent) {
        String categoryName = categoryToRemove.getText();
        if(categoryName.equals("AllProducts")){
            showError(alertLabel,"You can't remove this category",Error.NEGATIVE);
        }else{
            ClientCenter.getInstance().sendReqToServer(ServerRequest.POSTREMOVECATEGORY,categoryName);
            String response = ClientCenter.getInstance().readMessageFromServer();
            if(response.startsWith(ServerRequest.DONE.toString())){
                showError(alertLabel,"Category "+categoryToRemove.getText() + " has been deleted successfully!",Error.POSITIVE);
            }else{
                showError(alertLabel,"This category doesn't exist!", Error.NEGATIVE);
            }
        }
    }

    public void addCategory(MouseEvent mouseEvent) {
        goToNextPage(Page.CREATECATEGORY,mouseEvent);
    }

    public void editCategory(MouseEvent mouseEvent) {
        if(categoryToEdit.getText().equals("AllProducts")){
            showError(alertLabel,"You can't edit this category",Error.NEGATIVE);
        }else{
            ClientCenter.getInstance().sendReqToServer(ServerRequest.GETCATEGORYEXISTS,categoryToEdit.getText());
            String response = ClientCenter.getInstance().readMessageFromServer();
            if(response.startsWith(ServerRequest.DONE.toString())){
                ClientCenter.getInstance().setCategoryToEdit(categoryToEdit.getText());
                goToNextPage(Page.EDITCATEGORY,mouseEvent);
            }else {
                showError(alertLabel,"This category doesn't exist!",Error.NEGATIVE);
            }
        }
    }

    public void selection(MouseEvent mouseEvent) {
        Category selectedItem = listCategories.getSelectionModel().getSelectedItem();
        categoryToEdit.setText(selectedItem.getName());
        categoryToRemove.setText(selectedItem.getName());
    }
}
