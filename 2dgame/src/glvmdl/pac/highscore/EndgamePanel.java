package glvmdl.pac.highscore;



import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EndgamePanel {
    private JFrame frame;
    private String title;
    private JLabel panelTitle;
    private JTextField textField;
    private String name;
    
    public EndgamePanel(){
        frame = new JFrame(title);
        frame.setSize(200,100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        panelTitle = new JLabel("New Highscore!", JLabel.LEFT);
        panelTitle.setFont(new Font("Serif", Font.BOLD, 22));
        
        textField = new JTextField("Enter your name");
        JButton btn = new JButton("Enter!");
        btn.addActionListener((ActionEvent e) -> {
            textField.getText();
        });
        
        frame.add(textField);
        frame.add(btn);
    }

    public String getName() {
        return name;
    }
    
    
    

}
