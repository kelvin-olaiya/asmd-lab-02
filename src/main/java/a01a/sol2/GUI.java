package a01a.sol2;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    final Map<JButton, Position> cells = new HashMap<>();
    private final Logic logic;
    
    public GUI(final Logic logic) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.logic = logic;
        this.setSize(70*logic.getSize(), 70*logic.getSize());
        JPanel panel = new JPanel(new GridLayout(logic.getSize(),logic.getSize()));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
            var jb = (JButton)e.getSource();
            this.logic.hit(this.cells.get(jb));
            for (var entry: this.cells.entrySet()){
                entry.getKey().setText(
                    this.logic
                        .getMark(entry.getValue())
                        .map(String::valueOf)
                        .orElse(" "));
            }
            if (this.logic.isOver()){
                System.exit(0);
            }
        };
                
        for (int i=0; i<logic.getSize(); i++){
            for (int j=0; j<logic.getSize(); j++){
            	final JButton jb = new JButton();
                this.cells.put(jb, new Position(j,i));
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }
    
}
