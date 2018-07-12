package Lesson4.MainTask;

import javax.swing.*;
import java.awt.*;

public class PersonEditWindow extends JDialog {
    Person person;
    private JTextField textFieldName;
    private JTextField textFieldPatronymic;
    private JTextField textFieldSurname;

    public PersonEditWindow(Person person) {
        this.person = person;
        setTitle("Person edit window");
        setModal(true);
        setBounds(300, 300, 400, 150);
        setDefaultCloseOperation( WindowConstants.HIDE_ON_CLOSE);

        JPanel centralPanel = new JPanel();
        JPanel bottomPanel = new JPanel();
        add(centralPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        centralPanel.setLayout(new GridLayout(3, 2));
        bottomPanel.setLayout(new FlowLayout());

        textFieldName = new JTextField();
        textFieldPatronymic = new JTextField();
        textFieldSurname = new JTextField();
        setPerson();

        centralPanel.add(new JLabel("Name"));
        centralPanel.add(textFieldName);
        centralPanel.add(new JLabel("Patronymic"));
        centralPanel.add(textFieldPatronymic);
        centralPanel.add(new JLabel("Surname"));
        centralPanel.add(textFieldSurname);

        JButton buttonSave = new JButton("Save");
        buttonSave.addActionListener(e -> {
            saveData();
        });
        bottomPanel.add(buttonSave);

        setResizable(false);
        setVisible(true);
    }

    private void setPerson(){
        if(person ==null) return;
        textFieldName.setText(person.getName());
        textFieldPatronymic.setText(person.getPatronymic());
        textFieldSurname.setText(person.getSurname());
    }

    public void saveData() {
        person = new Person(textFieldName.getText(), textFieldPatronymic.getText(), textFieldSurname.getText());
        setVisible(false);
    }
}
