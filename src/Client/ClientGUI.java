package Client;

import com.inet.jortho.FileUserDictionary;
import com.inet.jortho.SpellChecker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Haichao Song
 * Description: the GUI allows users to search, add and remove words in the dictionary and
 * shows different Message dialogs according to operations and failures.
 */
public class ClientGUI {

    private JFrame frame;
    private JTextField text;
    private RequestHandler requestHandler;
    private String word, mean, syn;
    private final int OPERATION_FAIL = 0;
    private final int CONNECTION_FAIL = 2;

    public JFrame getFrame() {
        return frame;
    }

    public ClientGUI (RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
        create();
    }

    public void create() {

        frame = new JFrame();
        frame.setMinimumSize(new Dimension(450, 300));
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        text =  new JTextField();
        text.setColumns(10);
        JLabel meaning = new JLabel("Meaning:");
        JScrollPane scrollPane = new JScrollPane();
        JLabel target = new JLabel("Word:");
        JTextArea meaningPane = new JTextArea();
        JLabel synonym = new JLabel("Synonym:");
        JTextArea synonymPane = new JTextArea();

        //FILE LOCATION OF DICTIONARY
        String userDictionaryPath =  "/Dictionary/";

        //SET DICTIONARY PROVIDER FROM DICTIONARY PATH
        SpellChecker.setUserDictionaryProvider(new FileUserDictionary(userDictionaryPath));

        //REGISTER DICTIONARY
        SpellChecker.registerDictionaries(getClass().getResource(userDictionaryPath), "en");

        SpellChecker.register(text);
        SpellChecker.register(meaningPane);
        SpellChecker.register(synonymPane);

        // Search button and its listener
        JButton search = new JButton("SEARCH");
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                word = text.getText();
                if (isValid("Search", word, "")) {
                    String[] request = requestHandler.search(word);
                    int state = Integer.parseInt(request[0]);
                    if (state == OPERATION_FAIL ) {
                        JOptionPane.showMessageDialog(frame, "Word Not Exist.", "Warning",
                                JOptionPane.WARNING_MESSAGE);
                    } else if (state == CONNECTION_FAIL) {
                        JOptionPane.showMessageDialog(frame, "Connection Fail.", "Warning",
                                JOptionPane.WARNING_MESSAGE);
                    } else {
                        meaningPane.setText(request[1]);
                        synonymPane.setText(request[2]);
                    }
                }
            }
        });

        // Add button and its listener
        JButton add = new JButton("ADD");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                word = text.getText();
                mean = meaningPane.getText();
                syn = synonymPane.getText();
                if (isValid("Add", word, mean)) {
                    int confirm = JOptionPane.showConfirmDialog(frame,  "Confirm to Add a new word?",
                            "Confirm Window", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        String[] request = requestHandler.add(word, mean, syn);
                        int state = Integer.parseInt(request[0]);
                        if ( state == OPERATION_FAIL ) {
                            JOptionPane.showMessageDialog(frame, "Word Exist.", "Warning",
                                    JOptionPane.WARNING_MESSAGE);
                        } else if (state == CONNECTION_FAIL) {
                            JOptionPane.showMessageDialog(frame, "Connection Fail.", "Warning",
                                    JOptionPane.WARNING_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(frame, "Add Success.", "Tips",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            }
        });

        // Remove button and its listener
        JButton remove = new JButton("REMOVE");
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                word = text.getText();
                if (isValid("Remove", word, "")) {

                    int confirm = JOptionPane.showConfirmDialog(frame,  "Confirm to Remove the word?",
                            "Confirm Window", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        String[] request = requestHandler.remove(word);
                        int state = Integer.parseInt(request[0]);

                        if (state == OPERATION_FAIL ) {
                            JOptionPane.showMessageDialog(frame, "Word Not Exist.", "Warning",
                                    JOptionPane.WARNING_MESSAGE);
                        } else if (state == CONNECTION_FAIL) {
                            JOptionPane.showMessageDialog(frame, "Connection Fail.", "Warning",
                                    JOptionPane.WARNING_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(frame, "Remove Success.", "Tips",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }

            }
        });

        // Clear button and its listener
        JButton clear = new JButton("CLEAR");
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(frame,  "Confirm to Clear the text?",
                        "Confirm Window", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    text.setText("");
                    meaningPane.setText("");
                    synonymPane.setText("");
                }
            }
        });

        // Render the GUI
        scrollPane.setViewportView(meaningPane);
        meaningPane.setLineWrap(true);
        GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGap(5)
                                .addComponent(target, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGap(5)
                                .addComponent(text, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                                .addGap(5)
                                .addComponent(search, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGap(5)
                                .addComponent(meaning, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE))
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGap(5)
                                .addComponent(synonym, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE))
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGap(5)
                                .addComponent(add, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                                .addGap(5)
                                .addComponent(remove, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                                .addGap(5)
                                .addComponent(clear, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                                .addGap(5))
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGap(5)
                                .addComponent(synonymPane, GroupLayout.PREFERRED_SIZE, 420, Short.MAX_VALUE)
                                .addGap(5))
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGap(5)
                                .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
                                .addGap(5))

        );

        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGap(5)
                                .addComponent(target, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(text, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(search, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                                .addGap(5)
                                .addComponent(meaning, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
                                .addGap(5)
                                .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                                .addGap(5)
                                .addComponent(synonym, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
                                .addGap(5)
                                .addComponent(synonymPane, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                                .addGap(5)
                                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(add, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(remove, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(clear, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                                .addGap(8))
        );
        frame.getContentPane().setLayout(groupLayout);
    }

    /* Check if user entered the word or meaning in operation */
    public Boolean isValid(String command, String word, String meaning) {
        if (word == "") {
            JOptionPane.showMessageDialog(frame, "Please Enter a word.", "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (command == "Add" && meaning == "") {
            JOptionPane.showMessageDialog(frame, "Please Enter the meaning.", "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

}
