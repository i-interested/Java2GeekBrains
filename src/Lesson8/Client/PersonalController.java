package Lesson8.Client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.DataOutputStream;
import java.io.IOException;

public class PersonalController {
    @FXML
    Button btn;

    @FXML
    TextField msgField;

    public void btnClick() {
        DataOutputStream out = ((MiniStage) btn.getScene().getWindow()).out;
        String nickTo = ((MiniStage) btn.getScene().getWindow()).nickTo;
        try {
            out.writeUTF("/w " + nickTo + " " + msgField.getText());
            msgField.clear();
            msgField.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
