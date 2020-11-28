package glvmdl.pac.highscore;



import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    public static boolean isShowing = true;
    
    public EndgamePanel(int score, int worldNum){

        frame = new JFrame(title);
        
        
        panelTitle = new JLabel("New Highscore!", JLabel.LEFT);
        panelTitle.setFont(new Font("Serif", Font.BOLD, 22));
        
        textField = new JTextField("Name");
        textField.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                textField.setText("");
            }
        });
        JButton btn = new JButton("Enter!");
        
        btn.addActionListener((ActionEvent e) -> {
            Highscore.checkCurrentScore(score, textField.getText(), worldNum);
            Highscore.saveHighScores();
            this.frame.dispose();
            
        });
        textField.setVisible(true);
        
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(panelTitle);
        frame.add(textField);
        frame.add(btn);
        
        frame.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setSize(300,150);
        frame.setVisible(true);
    }

    public String getName() {
        return name;
    }
    
    
    

}
