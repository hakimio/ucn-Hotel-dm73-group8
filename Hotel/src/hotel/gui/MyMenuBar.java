package hotel.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import hotel.core.Hotel;
import hotel.controller.HotelCtrl;
import java.awt.Component;

public class MyMenuBar extends JMenuBar
{
    private HotelCtrl hotelCtrl;
    private JLabel[] labals;
    private JTextField[] inputs;
    
    public MyMenuBar()
    {
        hotelCtrl = new HotelCtrl();
        
        labals = new JLabel[] {new JLabel("Name"), 
            new JLabel("Address")};
        inputs = new JTextField[2];
        for (int i = 0; i < inputs.length; i++)
            inputs[i] = new JTextField();
        
        JMenu hotelMenu = new JMenu("Hotel");
        JMenuItem newMI = new JMenuItem("New...");
        JMenuItem editMI = new JMenuItem("Edit...");
        JMenuItem selectMI = new JMenuItem("Select...");
        JMenuItem removeMI = new JMenuItem("Remove...");
        JMenuItem exitMI = new JMenuItem("Exit");
        
        hotelMenu.add(newMI);
        hotelMenu.add(editMI);
        hotelMenu.add(removeMI);
        hotelMenu.addSeparator();
        hotelMenu.add(selectMI);
        hotelMenu.addSeparator();
        hotelMenu.add(exitMI);
        this.add(hotelMenu);
        
        newMI.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                addHotel();
            }
        });
        
        editMI.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                editHotel();
            }
        });
        
        removeMI.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                removeHotel();
            }
        });
        
        exitMI.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });
        
        selectMI.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                selectHotel();
            }
        });
    }
    
    private void selectHotel()
    {
        if(hotelCtrl.getHotelCount() == 0)
        {
            GUI.showError("No hotels were added.", "Error", this);
            return;
        }
        String[] hotelNames = new String[hotelCtrl.getHotelCount()];
        for (int i = 0; i < hotelNames.length; i++)
            hotelNames[i] = hotelCtrl.getHotel(i).getName();
        
        String hotelToSelect = (String)JOptionPane.showInputDialog(this, 
            "Choose hotel", "Selection", JOptionPane.QUESTION_MESSAGE, 
            null, hotelNames, hotelCtrl.getHotel(0).getName());
        
        try
        {
            GUI.selectedHotel = hotelCtrl.getHotelByName(hotelToSelect);
        }
        catch (Exception e)
        {
            GUI.showError(e.getMessage(), "Error", this);
        }
    }
    
    private void addHotel()
    {
        final JDialog addDialog = GUI.dialog("New Hotel", labals, inputs);
        JPanel myPanel = (JPanel)addDialog.getContentPane().getComponent(0);
        
        final JTextField name = ((JTextField)myPanel.getComponent(1));
        final JTextField address = ((JTextField)myPanel.getComponent(3));
        final MyMenuBar myMenuBar = this;
        
        JButton okButton = ((JButton)myPanel.getComponent(4));
        okButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    hotelCtrl.addHotel(name.getText(), address.getText());
                }
                catch (Exception exc)
                {
                    GUI.showError(exc.getMessage(), "Error", myMenuBar);
                    return;
                }

                addDialog.setVisible(false);
            }
        });

        addDialog.setVisible(true);
    }
    private void editHotel()
    {
        if (GUI.selectedHotel == null)
        {
            GUI.showError("Hotel must be selected", "Error", this);
            return;
        }
        
        final JDialog editDialog = GUI.dialog("Edit Hotel", labals, inputs);
        JPanel myPanel = (JPanel)editDialog.getContentPane().getComponent(0);
        
        final MyMenuBar myMenuBar = this;
        final Hotel hotel = GUI.selectedHotel;
        final JTextField name = ((JTextField)myPanel.getComponent(1));
        name.setText(hotel.getName());
        final JTextField address = ((JTextField)myPanel.getComponent(3));
        address.setText(hotel.getAddress());

        JButton okButton = ((JButton)myPanel.getComponent(4));
        okButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    int id = hotelCtrl.getId(hotel);
                    hotelCtrl.editHotel(id, name.getText(), address.getText());
                }
                catch (Exception exc)
                {
                    GUI.showError(exc.getMessage(), "Error", myMenuBar);
                    return;
                }
                
                editDialog.setVisible(false);
            }
        });

        editDialog.setVisible(true);
    }
    
    private void removeHotel()
    {
        if(hotelCtrl.getHotelCount() == 0)
        {
            GUI.showError("No hotels were added.", "Error", this);
            return;
        }
        String[] hotelNames = new String[hotelCtrl.getHotelCount()];
        for (int i = 0; i < hotelNames.length; i++)
            hotelNames[i] = hotelCtrl.getHotel(i).getName();
        
        String hotelToRemove = (String)JOptionPane.showInputDialog(this, 
            "Choose hotel to remove", "Removal", JOptionPane.QUESTION_MESSAGE, 
            null, hotelNames, hotelCtrl.getHotel(0).getName());
        
        try
        {
            if (GUI.selectedHotel.getName().equals(hotelToRemove))
                GUI.selectedHotel = null;
            hotelCtrl.removeHotel(hotelToRemove);
        }
        catch (Exception e)
        {
            GUI.showError(e.getMessage(), "Error", this);
        }
    }
}
