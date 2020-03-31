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

    }

}
