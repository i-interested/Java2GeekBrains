package Lesson7.Client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("client.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("2k18Chat");
        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.show();
        Controller controller = loader.getController();
        primaryStage.setOnHidden(event -> {
            try {
                if (controller.out != null)
                    controller.out.writeUTF("/end");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
