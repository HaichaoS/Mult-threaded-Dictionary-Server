package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Haichao Song
 * Description:
 */
public class ClientGUI {

    private JFrame frame;
    private JTextField text;
    private Client client;
    private String word, mean;

    public JFrame getFrame() {
        return frame;
    }

    public ClientGUI (Client client) {
        this.client = client;
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


        JButton search = new JButton("SEARCH");
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                word = text.getText();
                if (isValid("Search", word, "")) {
                    String[] request = client.search(word);
                    int state = Integer.parseInt(request[0]);
                    if (state == 0 ) {
                        JOptionPane.showMessageDialog(frame, "Word Not Exist.", "Warning",
                                JOptionPane.WARNING_MESSAGE);
                    } else if (state == 2) {
                        JOptionPane.showMessageDialog(frame, "Connection Fail.", "Warning",
                                JOptionPane.WARNING_MESSAGE);
                    } else {
                        meaningPane.setText(request[1]);
                    }
                }
            }
        });

        JButton add = new JButton("ADD");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                word = text.getText();
                mean = meaningPane.getText();
                if (isValid("Add", word, mean)) {
                    String[] request = client.add(word, mean);
                    int confirm = JOptionPane.showConfirmDialog(frame,  "Confirm to Add a new word?",
                            "Confirm Window", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {

                        int state = Integer.parseInt(request[0]);
                        if ( state == 0 ) {
                            JOptionPane.showMessageDialog(frame, "Word Exist.", "Warning",
                                    JOptionPane.WARNING_MESSAGE);
                        } else if (state == 2) {
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

        JButton remove = new JButton("REMOVE");
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                word = text.getText();
                if (isValid("Remove", word, "")) {
                    String[] request = client.remove(word);
                    int confirm = JOptionPane.showConfirmDialog(frame,  "Confirm to Remove the word?",
                            "Confirm Window", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {

                        int state = Integer.parseInt(request[0]);

                        if (state == 0 ) {
                            JOptionPane.showMessageDialog(frame, "Word Not Exist.", "Warning",
                                    JOptionPane.WARNING_MESSAGE);
                        } else if (state == 2) {
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

        JButton clear = new JButton("CLEAR");
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(frame,  "Confirm to Clear the text?",
                        "Confirm Window", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    text.setText("");
                    meaningPane.setText("");
                }
            }
        });

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
