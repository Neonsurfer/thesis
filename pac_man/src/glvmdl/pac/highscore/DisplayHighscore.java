package glvmdl.pac.highscore;

import static glvmdl.pac.highscore.Highscore.getCurrentHighscores;
import static glvmdl.pac.highscore.Highscore.highscores;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;


@SuppressWarnings("serial")
public final class DisplayHighscore extends AbstractPanel{
    private final JFrame frame;
    private final String title = "Highscores";
    //private static JPanel container;
    private static final String columns [] = {"Name", "Score","World" ,"Date"};
    private static JTable table;
    private static DefaultTableModel tableModel;
    private static JScrollPane tableScroll;
    //private final JLabel panelTitle;
    
    public DisplayHighscore(){
        frame = new JFrame(title);
        frame.setSize(600,400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        tableModel = new DefaultTableModel(columns, 10){
            @Override
            public boolean isCellEditable(int rowIndex, int mColIndex){
                return false;
            }
        };
        
        setTableProperties(tableModel);
        populateTable(highscores);    

        frame.add(tableScroll);
        
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
        table.setColumnSelectionAllowed(false);
        table.setRowSelectionAllowed(false);
        table.setFocusable(false);

        tableScroll = new JScrollPane(table);
        tableScroll.setVisible(true);
        tableScroll.setViewportView(table);

    }
}
