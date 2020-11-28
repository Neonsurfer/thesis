package glvmdl.pac.highscore;

import static glvmdl.pac.highscore.Highscore.getCurrentHighscores;
import static glvmdl.pac.highscore.Highscore.highscores;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;


@SuppressWarnings("serial")
public class DisplayHighscore extends AbstractPanel{
    private JFrame frame;
    private String title;
    private JPanel container;
    private final String columns [] = {"Name", "Score","World" ,"Date"};
    private JTable table;
    private JTableHeader tableHeader;
    private static DefaultTableModel tableModel;
    private JScrollPane tableScroll;
    private JLabel panelTitle;
    
    public DisplayHighscore(){
        frame = new JFrame(title);
        frame.setSize(600,400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        setShowPanelProperties();
        panelTitle = new JLabel("Highscores", JLabel.LEFT);
        panelTitle.setFont(new Font("Serif", Font.BOLD, 22));
        
        container = new JPanel();
        container.setPreferredSize(new Dimension(200, 100));
        container.add(panelTitle);
        
        frame.add(container);
        
        tableModel = new DefaultTableModel(columns, 0);
        setTableProperties(tableModel);
        
        populateTable(highscores);
        table = new JTable(tableModel);
        frame.add(table);
    }
    
    @Override
    void setShowPanelProperties() {
        this.setVisible(false);
        this.setBackground(Color.WHITE);
        this.setSize(690,550);
        this.setPreferredSize(new Dimension(690,550));
        this.setLocation(0,20);
    }
    
    public static void populateTable(List<Highscore> highscores){
        if(tableModel.getRowCount() != 0){
            eraseTable();
        }
        getCurrentHighscores();
        for(Highscore highscore : highscores){
            Object name = highscore.getName();
            Object score = highscore.getScore();
            Object worldNum = highscore.getWorldNum();
            Object date = highscore.getDate();
            
            Object [] data = {name, score, worldNum, date};
            tableModel.addRow(data);
        }
    }
    
    private static void eraseTable(){
        tableModel.setRowCount(0);
    }
    
    
    private void setTableProperties(final DefaultTableModel tableModel){
        table = new JTable(tableModel);
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? Color.WHITE : Color.LIGHT_GRAY);
                return this;
            }
        });
        
        table.setPreferredScrollableViewportSize(new Dimension(600,300));
        table.setFillsViewportHeight(true);
        table.setVisible(true);
        

        tableHeader = table.getTableHeader();
        tableHeader.setFont(new Font("Serif", Font.BOLD, 12));
        tableHeader.setBackground(Color.DARK_GRAY);
        tableHeader.setForeground(Color.WHITE);
        add(tableHeader);
        tableScroll = new JScrollPane(table);
        tableScroll.setVisible(true);
        add(tableScroll);
        add(table);
    }
}
