package ui;

import model.Game;
import model.GameList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

// Citations: ListDemoProject, https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html
//            CPSC 210 SimpleDrawingPlayer
public class MainMenuGUI extends JFrame implements ActionListener, ListSelectionListener {

    private Font font;
    private GameList savedGames;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/gamelist.json";

    private JButton deleteButton;
    private JButton playButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton createButton;

    private JScrollPane listScrollPane;
    private JPanel buttonPanel;
    private JTextField gameName;
    private JList list;
    private DefaultListModel listModel;

    public MainMenuGUI() {
        super("Main Menu");
        initializeFields();
        initializeGraphics();
    }

    // MODIFIES: this
    // EFFECTS: initializes JFrame window, creates search bar, list panel and buttons
    private void initializeGraphics() {
        setMinimumSize(new Dimension(600, 600));
        createListPanel();
        createButtons();
        createSearchPanel();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: instantiates necessary fields
    public void initializeFields() {
        font = new Font(Font.SANS_SERIF, Font.PLAIN, 25);
        savedGames = new GameList("My saved games");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        listModel = new DefaultListModel();
        list = new JList(listModel);
    }

    // MODIFIES: this
    // EFFECTS: creates search bar at top of frame
    private void createSearchPanel() {
        JLabel label = new JLabel("Name:");
        label.setFont(font);

        JPanel searchPanel = new JPanel();
        searchPanel.add(label);
        searchPanel.add(gameName);
        searchPanel.add(createButton);
        searchPanel.add(Box.createHorizontalStrut(5));
        add(searchPanel, BorderLayout.PAGE_START);
    }

    // MODIFIES: this
    // EFFECTS: creates create button
    public void createCreateButton() {
        createButton = new JButton("Create");
        gameName = new JTextField(8);

        createButton.setFont(font);
        gameName.setFont(font);

        createButton.setActionCommand("Create");
        CreateListener createListener = new CreateListener(createButton);

        createButton.addActionListener(createListener);
        savedGames.addGame(new Game(gameName.getText(), "Easy"));
        createButton.addActionListener(createListener);
        createButton.setEnabled(false);
        gameName.addActionListener(createListener);
        gameName.getDocument().addDocumentListener(createListener);
    }

    // CITATION: ListDemoProject, https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html
    class CreateListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public CreateListener(JButton button) {
            this.button = button;
        }

        public void actionPerformed(ActionEvent e) {
            String name = gameName.getText();

            if (name.equals("") || listModel.contains(name)) {
                Toolkit.getDefaultToolkit().beep();
                gameName.requestFocusInWindow();
                gameName.selectAll();
                return;
            }

            int index = list.getSelectedIndex();
            if (index == -1) {
                index = 0;
            } else {
                index++;
            }
            listModel.insertElementAt(gameName.getText(), index);
            gameName.requestFocusInWindow();
            gameName.setText("");
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

    // MODIFIES: this
    // EFFECTS: creates play button
    public void createPlayButton() {
        playButton = new JButton("Play");
        playButton.setFont(font);
        playButton.setActionCommand("Play");
        playButton.addActionListener(this);
        playButton.setEnabled(false);
        buttonPanel.add(playButton);
    }

    // MODIFIES: this
    // EFFECTS: creates scroll panel of saved games
    private void createListPanel() {
        list.setFont(font);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(4);
        listScrollPane = new JScrollPane(list);
        listScrollPane.setFont(font);
    }

    // MODIFIES: this
    // EFFECTS: declares and instantiates all the buttons
    private void createButtons() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 2));

        createCreateButton();
        createDeleteButton();
        createPlayButton();
        createLoadButton();
        createSaveButton();

        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.PAGE_END);
    }


    // MODIFIES: this
    // EFFECTS: creates delete button
    public void createDeleteButton() {
        deleteButton = new JButton("Delete");
        deleteButton.setFont(font);
        deleteButton.setActionCommand("Delete");
        deleteButton.addActionListener(this);
        deleteButton.setEnabled(false);
        buttonPanel.add(deleteButton);
    }

    // MODIFIES: this
    // EFFECTS: creates load button
    public void createLoadButton() {
        loadButton = new JButton("Load");
        loadButton.setFont(font);
        loadButton.addActionListener(this);
        buttonPanel.add(loadButton);
    }

    // MODIFIES: this
    // EFFECTS: creates save button
    public void createSaveButton() {
        saveButton = new JButton("Save");
        saveButton.setFont(font);
        saveButton.addActionListener(this);
        buttonPanel.add(saveButton);
    }

    // MODIFIES: this
    // EFFECTS: deletes current game selected
    public void deleteGame() {
        int index = list.getSelectedIndex();
        listModel.remove(index);

        int size = listModel.getSize();

        if (size == 0) {
            deleteButton.setEnabled(false);
        } else {
            if (index == listModel.getSize()) {
                index--;
            }
            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
        }
    }

    // MODIFIES: savedGames
    // EFFECTS: loads saved games from file
    private void loadGames() {
        try {
            savedGames = jsonReader.read();
            for (String s : savedGames.viewGameNames()) {
                listModel.addElement(s);
            }
            System.out.println("Loaded " + savedGames.getGameListName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // MODIFIES: savedGames
    // EFFECTS: saves savedGames to file
    private void saveGames() {
        try {
            GameList newSavedGames = new GameList("saved games");
            for (int i = 0; i < listModel.size(); i++) {
                String currentGameName = listModel.getElementAt(i).toString();
                if (!savedGames.hasGameWithName(currentGameName)) {
                    newSavedGames.addGame(new Game(currentGameName, "Easy"));
                } else {
                    newSavedGames.addGame(savedGames.getGameWithName(currentGameName));
                }
            }
            savedGames = newSavedGames;
            jsonWriter.open();
            jsonWriter.write(savedGames);
            jsonWriter.close();
            System.out.println("Saved " + savedGames.getGameListName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: if there are no saved games in list, disables play button,
    //          otherwise, launches selected game
    public void launchGame() {
        if (listModel.getSize() == 0) {
            playButton.setEnabled(false);
        } else {
            String name = list.getSelectedValue().toString();
            dispose();
            new PlayGameWindow(name);
        }
    }

    // MODIFIES: this
    // EFFECTS: if no selection is possible, disables delete button
    //          if selection is possible, enables delete button
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            if (list.getSelectedIndex() == -1) {
                deleteButton.setEnabled(false);
                playButton.setEnabled(false);
            } else {
                deleteButton.setEnabled(true);
                playButton.setEnabled(true);
            }
        }
    }

    // EFFECTS: performs action based on button pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == deleteButton) {
            deleteGame();
        } else if (e.getSource() == saveButton) {
            saveGames();
        } else if (e.getSource() == loadButton) {
            loadGames();
        } else if (e.getSource() == playButton) {
            launchGame();
        }
    }

    // EFFECTS: creates the main menu GUI
    public static void main(String[] args) {
        new MainMenuGUI();
    }
}
