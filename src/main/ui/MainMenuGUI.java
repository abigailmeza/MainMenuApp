package ui;

import model.Game;
import model.GameList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

// Citations: ListDemoProject, https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html
//            CPSC 210 SimpleDrawingPlayer
public class MainMenuGUI extends JFrame implements ListSelectionListener {

    private GameList savedGames;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/gamelist.json";

    private JButton deleteButton;
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
        setMinimumSize(new Dimension(300, 300));
        createListPanel();
        createButtons();
        createSearchPanel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: instantiates necessary fields
    public void initializeFields() {
        savedGames = new GameList("My saved games");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        listModel = new DefaultListModel();
        list = new JList(listModel);
    }


    // MODIFIES: this
    // EFFECTS: creates search bar at top of frame
    private void createSearchPanel() {
        JLabel search = new JLabel("Name:");
        JPanel searchPanel = new JPanel();

        searchPanel.add(search);
        searchPanel.add(gameName);
        add(searchPanel, BorderLayout.PAGE_START);
    }

    // MODIFIES: this
    // EFFECTS: creates scroll panel of saved games
    private void createListPanel() {
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(4);
        listScrollPane = new JScrollPane(list);
    }

    // MODIFIES: this
    // EFFECTS: declares and instantiates all the buttons
    private void createButtons() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 2));

        createCreateButton();
        createDeleteButton();
        createLoadButton();
        createSaveButton();

        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.PAGE_END);
    }

    // MODIFIES: this
    // EFFECTS: creates load button
    public void createLoadButton() {
        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(e -> loadGameList());
        buttonPanel.add(loadButton);
    }

    // MODIFIES: this
    // EFFECTS: creates save button
    public void createSaveButton() {
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveGameList());
        buttonPanel.add(saveButton);

    }

    // MODIFIES: this
    // EFFECTS: creates create button
    public void createCreateButton() {
        JButton createButton = new JButton("Create");
        createButton.setActionCommand("Create");
        gameName = new JTextField(8);

        CreateActionListener createListener = new CreateActionListener(createButton, gameName, list, listModel);
        savedGames.addGame(new Game(gameName.getText(), "Easy"));
        createButton.addActionListener(createListener);
        createButton.setEnabled(false);
        gameName.addActionListener(createListener);
        gameName.getDocument().addDocumentListener(createListener);

        if (!(listModel.size() == 0)) {
            listModel.getElementAt(list.getSelectedIndex());
        } else {
            buttonPanel.add(createButton);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates delete button
    public void createDeleteButton() {
        deleteButton = new JButton("Delete");
        deleteButton.setActionCommand("Delete");

        deleteButton.addActionListener(e -> deleteAction());
        deleteButton.setEnabled(false);
        buttonPanel.add(deleteButton);
    }

    // MODIFIES: this
    // EFFECTS: deletes current game selected
    public void deleteAction() {
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
    private void loadGameList() {
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
    private void saveGameList() {
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
    // EFFECTS: if no selection is possible, disables delete button
    //          if selection is possible, enables delete button
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            if (list.getSelectedIndex() == -1) {
                deleteButton.setEnabled(false);
            } else {
                deleteButton.setEnabled(true);
            }
        }
    }

    // EFFECTS: creates the main menu GUI
    public static void main(String[] args) {
        new MainMenuGUI();
    }

}
