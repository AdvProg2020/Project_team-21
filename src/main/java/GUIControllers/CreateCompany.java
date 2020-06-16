package GUIControllers;

import Model.Company;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class CreateCompany extends GraphicFather {
    public TextField companyAddress;
    public TextField companyName;
    public Label alertLabel;

    public void submit(MouseEvent mouseEvent) {
        Company company;
        if(Company.getAllCompanies().containsKey(companyName.getText()))
        {
            company = Company.getAllCompanies().get(companyName.getText());
        }
        else
        {
            company = new Company(companyName.getText(),companyAddress.getText());
        }
        GUICenter.getInstance().getSellerToAddCompany().setCompany(company);
        goToNextPage(GUICenter.getInstance().getLanding(),mouseEvent);
    }

}
