package Lesson8.Client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.DataOutputStream;
import java.io.IOException;

public class MiniStage extends Stage {
    String nickTo;
    DataOutputStream out;

    public MiniStage(String nickTo, DataOutputStream out) {
        this.nickTo = nickTo;
        this.out = out;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("personal.fxml"));
            setTitle("personal window to " + nickTo);
            Scene scene = new Scene(root, 300, 400);
            setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
