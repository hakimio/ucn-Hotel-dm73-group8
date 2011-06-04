package hotel.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import hotel.core.Hotel;
import hotel.controller.HotelCtrl;

public class MyMenuBar extends JMenuBar
{
    private HotelCtrl hotelCtrl;
    private JLabel[] labals;
    private JTextField[] inputs;
    //private GUI gui;
    
    public MyMenuBar()
    {
        hotelCtrl = new HotelCtrl();
        //gui = GUI.getInstance();
        
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
        
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutMI = new JMenuItem("About");
        helpMenu.add(aboutMI);
        this.add(helpMenu);
        final MyMenuBar myMenuBar = this;
        aboutMI.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                JOptionPane.showMessageDialog(myMenuBar, "Created by Tomas "
                        + "Rimkus", "About", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
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
        Hotel[] hotels = hotelCtrl.getHotels();
        if(hotels.length == 0)
        {
            GUI.showError("No hotels were added.", "Error", this);
            return;
        }
        String[] hotelNames = new String[hotels.length];
        for (int i = 0; i < hotelNames.length; i++)
            hotelNames[i] = hotels[i].getName();
        
        String hotelToSelect = (String)JOptionPane.showInputDialog(this, 
            "Choose hotel", "Selection", JOptionPane.QUESTION_MESSAGE, 
            null, hotelNames, hotels[0].getName());
        
        if (hotelToSelect == null)
            return;
        
        try
        {
            Hotel hotel = hotelCtrl.getHotelByName(hotelToSelect);
            GUI.setHotel(hotel);
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
        if (GUI.getHotel() == null)
        {
            GUI.showError("Hotel must be selected", "Error", this);
            return;
        }
        
        final JDialog editDialog = GUI.dialog("Edit Hotel", labals, inputs);
        JPanel myPanel = (JPanel)editDialog.getContentPane().getComponent(0);
        
        final MyMenuBar myMenuBar = this;
        final Hotel hotel = GUI.getHotel();
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
                    Hotel myhotel = hotelCtrl.editHotel(hotel.getName(), 
                            name.getText(), address.getText());
                    GUI.setHotel(myhotel);
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
        Hotel[] hotels = hotelCtrl.getHotels();
        if(hotels.length == 0)
        {
            GUI.showError("No hotels were added.", "Error", this);
            return;
        }
        String[] hotelNames = new String[hotels.length];
        for (int i = 0; i < hotelNames.length; i++)
            hotelNames[i] = hotels[i].getName();
        
        String hotelToRemove = (String)JOptionPane.showInputDialog(this, 
            "Choose hotel to remove", "Removal", JOptionPane.QUESTION_MESSAGE, 
            null, hotelNames, hotels[0].getName());
        if (hotelToRemove == null)
            return;
        
        try
        {
            if (GUI.getHotel().getName().equals(hotelToRemove))
                GUI.setHotel(null);
            hotelCtrl.removeHotel(hotelToRemove);
        }
        catch (Exception e)
        {
            GUI.showError(e.getMessage(), "Error", this);
        }
    }
}
