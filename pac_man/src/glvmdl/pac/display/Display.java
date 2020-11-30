package glvmdl.pac.display;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Display {
    
    private JFrame frame;
    private Canvas canvas;
    
    private final String title;
    private final int width;
    private final int height;
    private final JLabel label;
    
    public Display(String title, int width, int height, int worldId, int lives){
        this.title = title;
        this.width = width;
        this.height = height;
        this.label = new JLabel();
        label.setText("World:" + worldId  + " Lives:" + lives + " Points:0");
        createDisplay();
    }
    
    private void createDisplay(){
        frame = new JFrame(title);
        frame.setLayout(new BorderLayout());
        frame.setSize(width,height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.add(label, BorderLayout.NORTH);
        
        label.setSize(width, 50);
        label.setVisible(true);
        
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height-50));
        canvas.setMaximumSize(new Dimension(width, height-50));
        canvas.setMinimumSize(new Dimension(width, height-50));
        canvas.setFocusable(false);
        
        frame.add(canvas, BorderLayout.SOUTH);
        
        frame.pack();
    }
    
    public Canvas getCanvas() {
        return canvas;
    }
    
    public JFrame getFrame(){
        return frame;
    }
    
    public String getLabelText(){
        return this.label.getText();
    }
    
    public void setLabelText(String text){
        this.label.setText(text);
    }
}