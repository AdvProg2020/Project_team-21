package GUIControllers.ManagerAccount;

import Controller.ControlManager;
import GUIControllers.Error;
import GUIControllers.GraphicFather;
import GUIControllers.Page;
import Model.DiscountCode;
import Model.Product;
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

public class ManageDiscountCodes extends GraphicFather implements Initializable {
    public Label AlertLabel;
    public TableView<DiscountCode> listDiscountCodes;

    public TableColumn<DiscountCode,String> id = new TableColumn<>("DiscountID");
    public Label alertLabel;
    public TextField codeToEdit;
    public TextField codeToRemove;
    public TextField codeToView;

    ObservableList<DiscountCode> getDiscountCode(){
        ObservableList<DiscountCode> result =  FXCollections.observableArrayList();
        for (String s : DiscountCode.getAllDiscountCodes().keySet()) {
            result.add(DiscountCode.getAllDiscountCodes().get(s));
        }
        return result;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listDiscountCodes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        id.setCellValueFactory(new PropertyValueFactory<>("discountId"));
        listDiscountCodes.setItems(getDiscountCode());
        listDiscountCodes.getColumns().add(id);
    }

    public void viewDiscountCode(MouseEvent mouseEvent) {
        if(!DiscountCode.getAllDiscountCodes().containsKey(codeToView.getText()))
            showError(alertLabel,"This code doesn't exist!", Error.NEGATIVE);
        else{
            ControlManager.getInstance().setDiscountCodeToView(codeToView.getText());
            goToNextPage(Page.VIEWDISCOUNTCODE,mouseEvent);
        }
    }

    public void RemoveDiscountCode(MouseEvent mouseEvent) {
        try {
            ControlManager.getInstance().removeDiscountCode(codeToRemove.getText());
            showError(alertLabel,"Code " + codeToRemove.getText()+" has been successfully deleted!",Error.POSITIVE);
        } catch (Exception e) {
            showError(alertLabel,e.getMessage(),Error.NEGATIVE);
        }
    }

    public void createDiscountCode(MouseEvent mouseEvent) {
        goToNextPage(Page.CREATEDISCOUNTCODE,mouseEvent);
    }

    public void EditDiscountCode(MouseEvent mouseEvent) {
        if(DiscountCode.getAllDiscountCodes().containsKey(codeToEdit.getText())){
            ControlManager.getInstance().setDiscountCodeToEdit(codeToEdit.getText());
            goToNextPage(Page.EDITDISCOUNTCODE,mouseEvent);
        }else{
            showError(alertLabel,"This code doesn't exist!",Error.NEGATIVE);
        }
    }
}
