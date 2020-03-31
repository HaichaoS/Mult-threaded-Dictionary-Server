package Client;

import javax.swing.*;
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
    private String word;

    public JFrame getFrame() {
        return frame;
    }

    public ClientGUI (Client client) {
        this.client = client;
        create();
    }

    public void create() {

        frame = new JFrame();

        text =  new JTextField();

        JButton search = new JButton("SEARCH");
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                word = text.getText();

            }
        });

        JButton add = new JButton("ADD");
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                word = text.getText();

            }
        });

        JButton remove = new JButton("REMOVE");
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                word = text.getText();

            }
        });

        JLabel meaning = new JLabel("The Meaning(s) of the word: ");
        JScrollPane scrollPane = new JScrollPane();
        JLabel target = new JLabel("Word:");
        JTextArea meaningPane = new JTextArea();

        scrollPane.setViewportView(meaningPane);
        meaningPane.setLineWrap(true);
        GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGap(5)
                                .addComponent(target, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))
                        .addComponent(text, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGap(5)
                                .addComponent(meaning, GroupLayout.PREFERRED_SIZE, 244, GroupLayout.PREFERRED_SIZE))
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGap(5)
                                .addComponent(search, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                                .addGap(25)
                                .addComponent(add, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                                .addGap(25)
                                .addComponent(remove, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                                .addGap(5))
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGap(5)
                                .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE)
                                .addContainerGap())
        );

        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGap(5)
                                .addComponent(target, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
                                .addComponent(text, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                .addGap(5)
                                .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                                .addGap(5)
                                .addComponent(meaning, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
                                .addGap(5)
                                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(search, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(add, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(remove, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
                                .addGap(8))
        );
        frame.getContentPane().setLayout(groupLayout);
    }

}
