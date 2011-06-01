package hotel.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import hotel.core.Hotel;

public class GUI extends JFrame
{   
    public static Hotel selectedHotel = null;
    private static GUI instance = null;
    
    private GUI()
    {
        super("Western Style");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(640, 480));
        
        setJMenuBar(new MyMenuBar());
        
        JTabbedPane jTabbedPane = new JTabbedPane();
        jTabbedPane.add("Rooms", new RoomTab());
        
        pack();
        setVisible(true);
    }
    
    public static GUI getInstance()
    {
        if (instance == null)
            instance = new GUI();
        
        return instance;
    }
    
    public static JTable createTable(Object[] columnNames)
    {
        JTable table;

        DefaultTableModel model = new DefaultTableModel()
        {
            @Override
            public boolean isCellEditable(int row, int column) 
            {
               return false;
            }
        };

        for (Object columnName: columnNames)
            model.addColumn(columnName);

        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        return table;
    }
    
    public static void showError(String message, String title, Component parent)
    {
        JOptionPane.showMessageDialog(parent, message, title,
            JOptionPane.ERROR_MESSAGE);
    }
    
    public static JDialog dialog(String title, JComponent[] labels, 
            JComponent[] inputs)
    {
        final JDialog dialog = new JDialog();
        dialog.setTitle(title);
        JPanel myPanel = new JPanel(new GridLayout(labels.length+1, 2, 10, 10));
        dialog.getContentPane().add(myPanel);
        myPanel.setBorder(new LineBorder(myPanel.getBackground(), 10));

        for (int i = 0; i < inputs.length; i++)
        {
            labels[i].setSize(120, 20);
            inputs[i].setSize(150,20);
            myPanel.add(labels[i]);
            myPanel.add(inputs[i]);
        }
        JButton okButton = new JButton("Ok");
        myPanel.add(okButton);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dialog.setVisible(false);
            }
        });
        myPanel.add(cancelButton);
        dialog.setResizable(false);
        dialog.pack();

        return dialog;
    }
}
