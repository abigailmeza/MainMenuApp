package ui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// CITATION: ListDemoProject, https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html
class CreateActionListener implements ActionListener, DocumentListener {
    private JTextField gameName;
    private JList list;
    private DefaultListModel listModel;
    private JButton button;

    private boolean alreadyEnabled = false;


    public CreateActionListener(JButton button, JTextField gameName, JList list, DefaultListModel listModel) {
        this.button = button;
        this.gameName = gameName;
        this.list = list;
        this.listModel = listModel;
    }

    // MODIFIES: this
    // EFFECTS: creates a new game with given GameName if game name doesn't already exist
    @Override
    public void actionPerformed(ActionEvent e) {
        String name = gameName.getText();

        if (name.equals("") || listModel.contains(name)) {
            Toolkit.getDefaultToolkit().beep();
            gameName.requestFocusInWindow();
            gameName.selectAll();
            return;
        }

        int index = list.getSelectedIndex(); //get selected index
        if (index == -1) { //no selection, so insert at beginning
            index = 0;
        } else {           //add after the selected item
            index++;
        }

        listModel.insertElementAt(gameName.getText(), index);
        //If we just wanted to add to the end, we'd do this:
        //listModel.addElement(employeeName.getText());

        //Reset the text field.
        gameName.requestFocusInWindow();
        gameName.setText("");

        //Select the new item and make it visible.
        list.setSelectedIndex(index);
        list.ensureIndexIsVisible(index);
    }

    // MODIFIES: this
    // EFFECTS: if button is not already enabled, then enables it
    public void insertUpdate(DocumentEvent e) {
        if (!alreadyEnabled) {
            button.setEnabled(true);
        }
    }

    // MODIFIES: this
    // EFFECTS: checks empty text field case and updates button accordingly
    public void removeUpdate(DocumentEvent e) {
        handleEmptyTextField(e);
    }

    // MODIFIES: this
    // EFFECTS: if not given an empty text field and if button is not already enabled,
    //          enables the button
    public void changedUpdate(DocumentEvent e) {
        if (!handleEmptyTextField(e)) {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: returns true if given an empty text field and disables button
    //          otherwise, returns false
    private boolean handleEmptyTextField(DocumentEvent e) {
        if (e.getDocument().getLength() <= 0) {
            button.setEnabled(false);
            alreadyEnabled = false;
            return true;
        }
        return false;
    }
}