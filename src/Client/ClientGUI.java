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
        frame.setMinimumSize(new Dimension(450, 300));
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        text =  new JTextField();
        text.setColumns(10);

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

        JLabel meaning = new JLabel("Meaning:");
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
                                .addComponent(add, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                                .addGap(10)
                                .addComponent(remove, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                                .addGap(5))
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGap(5)
                                .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE))
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
                                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(add, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(remove, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                                .addGap(8))
        );
        frame.getContentPane().setLayout(groupLayout);
    }

}
