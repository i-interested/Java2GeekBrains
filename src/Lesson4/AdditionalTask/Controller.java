package Lesson4.AdditionalTask;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Controller {

    @FXML
    TextArea textArea;

    @FXML
    TextField textField;

    @FXML
    MenuItem exitMenuItem;

    public void sendMsg() {
        String mail = "src/Lesson4/AdditionalTask/sounds/mail.wav";
        Media hit = new Media(new File(mail).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.play();
        textArea.appendText(textField.getText() + "\n");
        textField.clear();
        textField.requestFocus();
    }

    public void exit(){
        System.exit(0);
    }

    public void open() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        chooser.getExtensionFilters().add(extFilter);
        File file = chooser.showOpenDialog(new Stage());
        if(file.exists()) {
            try {
                textArea.setText(String.valueOf(Files.readAllLines(Paths.get(file.getPath()))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void save() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Save File");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        chooser.getExtensionFilters().add(extFilter);
        File file = chooser.showSaveDialog(new Stage());
        try (PrintWriter out = new PrintWriter(file.getPath())) {
            out.println(textArea.getText());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}