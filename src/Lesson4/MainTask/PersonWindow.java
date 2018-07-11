package Lesson4.MainTask;

import javax.swing.*;
import java.awt.*;

public class PersonWindow extends JFrame {

    Person person;
    private JTextField textFieldName;
    private JTextField textFieldPatronymic;
    private JTextField textFieldSurname;

    public PersonWindow() {
        setTitle("Person window");
        setBounds(300, 300, 400, 150);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel centralPanel = new JPanel();
        JPanel bottomPanel = new JPanel();
        add(centralPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        centralPanel.setLayout(new GridLayout(3, 2));
        bottomPanel.setLayout(new FlowLayout());

        textFieldName = new JTextField();
        textFieldName.setEditable(false);

        textFieldPatronymic = new JTextField();
        textFieldPatronymic.setEditable(false);

        textFieldSurname = new JTextField();
        textFieldSurname.setEditable(false);

        centralPanel.add(new JLabel("Name"));
        centralPanel.add(textFieldName);
        centralPanel.add(new JLabel("Patronymic"));
        centralPanel.add(textFieldPatronymic);
        centralPanel.add(new JLabel("Surname"));
        centralPanel.add(textFieldSurname);

        JButton buttonEdit = new JButton("Edit");
        buttonEdit.addActionListener(e -> {
            editData();
        });

        bottomPanel.add(buttonEdit);

        setResizable(false);
        setVisible(true);
    }

    public void editData() {
        PersonEditWindow personEditWindow = new PersonEditWindow(person);
        person = personEditWindow.person;

        if(person == null) return;
        textFieldName.setText(person.getName());
        textFieldPatronymic.setText(person.getPatronymic());
        textFieldSurname.setText(person.getSurname());
    }
}
