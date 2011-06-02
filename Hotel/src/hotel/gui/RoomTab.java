package hotel.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import net.sf.nachocalendar.CalendarFactory;
import net.sf.nachocalendar.components.DateField;

import hotel.controller.RoomCtrl;
import hotel.controller.BookingCtrl;
import hotel.controller.GuestCtrl;
import hotel.core.Guest;
import hotel.core.Room;
import hotel.core.Booking;
import java.util.Date;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class RoomTab extends JPanel
{
    private JTable roomTable;
    private JTable bookingTable;
    private RoomCtrl roomCtrl;
    private BookingCtrl bookingCtrl;
    private JComponent[] roomInputs;
    private JLabel[] roomLabels;
    private JComponent[] bookingInputs;
    private JLabel[] bookingLabels;
    private GuestCtrl guestCtrl;
    private final RoomTab roomTab = this;
    
    public RoomTab()
    {
        if (GUI.selectedHotel != null)
        {
            String hotelName = GUI.selectedHotel.getName();
            roomCtrl = new RoomCtrl(hotelName);
            bookingCtrl = new BookingCtrl(hotelName);
        }
        else
        {
            roomCtrl = null;
            bookingCtrl = null;
        }
        
        roomLabels = new JLabel[]{new JLabel("Room Nr"), new JLabel("Size"), 
            new JLabel("Price"), new JLabel("Nr of bedrooms")};
        roomInputs = new JSpinner[4];
        for (int i = 0; i < roomInputs.length; i++)
            roomInputs[i] = new JSpinner(new SpinnerNumberModel(1, 1, 999, 1));
        bookingLabels = new JLabel[]{new JLabel("Guest"), 
            new JLabel("Arrival Date"), new JLabel("Leaving Date"), 
            new JLabel("Discount")};
        bookingInputs = new JComponent[4];
        if (GUI.selectedHotel != null)
            guestCtrl = new GuestCtrl(GUI.selectedHotel.getName());
        
        String[] roomColumns = new String[] {"#", "Nr", "Size", "Sq Meter cost", 
            "bedrooms"};
        roomTable = GUI.createTable(roomColumns);
        updateRoomTable();
        
        String[] bookingColumns = new String[] {"#", "id", "Guest Name", 
            "Arrival Date", "Leaving Date", "Discount"};
        bookingTable = GUI.createTable(bookingColumns);
        TableColumn columnToRemove = bookingTable.getColumnModel().getColumn(1); 
        bookingTable.getColumnModel().removeColumn(columnToRemove);
        
        roomTable.getSelectionModel().addListSelectionListener(
        new ListSelectionListener() 
        {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                if (bookingCtrl == null)
                    return;
                
                int selected;
                if (roomTable.getSelectedRowCount() > 0)
                    selected = roomTable.getSelectedRow();
                else
                    return;
                
                updateBookingTable(selected);
            }
        });
        
        JScrollPane roomScrollPane = new JScrollPane(roomTable);
        JScrollPane bookingScrollPane = new JScrollPane(bookingTable);
        
        JButton AddRoom = new JButton("Add");
        AddRoom.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (GUI.selectedHotel == null)
                    GUI.showError("Hotel must be selected.", "Error", roomTab);
                else
                    addRoomCB();
            }
        });
        JButton editRoom = new JButton("Edit");
        editRoom.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (GUI.selectedHotel == null)
                    GUI.showError("Hotel must be selected.", "Error", roomTab);
                else if (roomTable.getSelectedRowCount() < 1)
                    GUI.showError("Please select some room.", "Error", roomTab);
                else 
                    editRoomCB(roomTable.getSelectedRow());
            }
        });
        
        JButton removeRoom = new JButton("Remove");
        removeRoom.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (GUI.selectedHotel == null)
                    GUI.showError("Hotel must be selected.", "Error", roomTab);
                else if (roomTable.getSelectedRowCount() < 1)
                    GUI.showError("Please select some room.", "Error", roomTab);
                else 
                    removeRoomCB(roomTable.getSelectedRow());
            }
        });
        
        JToolBar roomToolBar = new JToolBar();
        roomToolBar.add(AddRoom);
        roomToolBar.add(editRoom);
        roomToolBar.add(removeRoom);
        
        JButton addBooking = new JButton("Add");
        addBooking.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (GUI.selectedHotel == null)
                    GUI.showError("Hotel must be selected.", "Error", roomTab);
                else if (roomTable.getSelectedRowCount() < 1)
                    GUI.showError("Please select some room.", "Error", roomTab);
                else 
                    addBookingCB(roomTable.getSelectedRow());
            }
        });
        
        JButton editBooking = new JButton("Edit");
        editBooking.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (GUI.selectedHotel == null)
                    GUI.showError("Hotel must be selected.", "Error", roomTab);
                else if (roomTable.getSelectedRowCount() < 1)
                    GUI.showError("Please select some room.", "Error", roomTab);
                else if (bookingTable.getSelectedRowCount() < 1)
                    GUI.showError("Please select some booking", "Error", 
                            roomTab);
                else 
                    editBookingCB((Integer) bookingTable.
                        getValueAt(bookingTable.getSelectedRow(), 1), 
                        roomTable.getSelectedRow());
            }
        });
        JButton removeBooking = new JButton("Remove");
        removeBooking.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (GUI.selectedHotel == null)
                    GUI.showError("Hotel must be selected.", "Error", roomTab);
                else if (roomTable.getSelectedRowCount() < 1)
                    GUI.showError("Please select some room.", "Error", roomTab);
                else if (bookingTable.getSelectedRowCount() < 1)
                    GUI.showError("Please select some booking", "Error", 
                            roomTab);
                else 
                    removeBookingCB((Integer) bookingTable.
                            getValueAt(bookingTable.getSelectedRow(), 1),
                            roomTable.getSelectedRow());
            }
        });
        
        JToolBar bookingToolBar = new JToolBar();
        bookingToolBar.add(addBooking);
        bookingToolBar.add(editBooking);
        bookingToolBar.add(removeBooking);
        
        JPanel roomPanel = new JPanel();
        roomPanel.setLayout(new BorderLayout());
        roomPanel.add(roomToolBar, BorderLayout.PAGE_START);
        roomPanel.add(roomScrollPane);
        
        JPanel bookingPanel = new JPanel();
        bookingPanel.setLayout(new BorderLayout());
        bookingPanel.add(bookingPanel, BorderLayout.PAGE_START);
        bookingPanel.add(bookingScrollPane);
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, 
                roomPanel, bookingPanel);
        splitPane.setDividerLocation(200);
        
        this.add(splitPane);
        this.setVisible(true);
    }
    
    private void addBookingCB(final int roomId)
    {
        if (guestCtrl == null)
            guestCtrl = new GuestCtrl(GUI.selectedHotel.getName());
        if (bookingCtrl == null)
            bookingCtrl = new BookingCtrl(GUI.selectedHotel.getName());
        
        if (guestCtrl.getGuestCount() == 0)
        {
            GUI.showError("No guests were added.", "Error", this);
            return;
        }
        
        String[] guestNames = new String[guestCtrl.getGuestCount()];
        for (int i = 0; i < guestNames.length; i++)
            guestNames[i] = guestCtrl.getGuestById(i).getName();
        bookingInputs[0] = new JComboBox(guestNames);
        ((JComboBox)bookingInputs[0]).setSelectedIndex(0);
        bookingInputs[1] = CalendarFactory.createDateField();
        ((DateField)bookingInputs[1]).setValue(new Date());
        bookingInputs[2] = CalendarFactory.createDateField();
        ((DateField)bookingInputs[2]).setValue(new Date());
        bookingInputs[3] = new JSpinner(new SpinnerNumberModel(0, 0, 999, 1));
        final JDialog addDialog = GUI.dialog("Add Booking", bookingLabels, 
                bookingInputs);
        JPanel myPanel = (JPanel)addDialog.getContentPane().getComponent(0);
        
        final JComboBox guestNamesCB = ((JComboBox)myPanel.getComponent(1));
        final DateField arrivalDateField = ((DateField)myPanel.getComponent(3));
        final DateField leavingDateField = ((DateField)myPanel.getComponent(5));
        final JSpinner discount = (JSpinner)myPanel.getComponent(7);
        
        JButton okButton = ((JButton)myPanel.getComponent(8));
        okButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                String guestName = (String)guestNamesCB.getSelectedItem();
                Date arrivalDate = (Date)arrivalDateField.getValue();
                Date leavingDate = (Date)leavingDateField.getValue();
                int discountI = (Integer)discount.getValue();
                try
                {
                    Room room = roomCtrl.getRoomById(roomId);
                    Guest guest = guestCtrl.getGuestByName(guestName);
                    bookingCtrl.addBooking(room, guest, arrivalDate, 
                            leavingDate, discountI);
                }
                catch (Exception exc)
                {
                    GUI.showError(exc.getMessage(), "Error", roomTab);
                    return;
                }
                addDialog.setVisible(false);
                updateRoomTable();
                updateBookingTable(roomId);
            }
        });
        addDialog.setVisible(true);
    }
    
    private void editBookingCB(final int bookingId, final int roomId)
    {
        if (guestCtrl == null)
            guestCtrl = new GuestCtrl(GUI.selectedHotel.getName());
        if (bookingCtrl == null)
            bookingCtrl = new BookingCtrl(GUI.selectedHotel.getName());
        
        Booking booking = bookingCtrl.getBookingByBookingId(bookingId);
        
        String[] guestNames = new String[guestCtrl.getGuestCount()];
        for (int i = 0; i < guestNames.length; i++)
            guestNames[i] = guestCtrl.getGuestById(i).getName();
        bookingInputs[0] = new JComboBox(guestNames);
        ((JComboBox)bookingInputs[0]).setSelectedItem(booking.getGuest().
                getName());
        bookingInputs[1] = CalendarFactory.createDateField();
        ((DateField)bookingInputs[1]).setValue(booking.getArrivalDate());
        bookingInputs[2] = CalendarFactory.createDateField();
        ((DateField)bookingInputs[2]).setValue(booking.getLeavingDate());
        SpinnerModel discountModel = new SpinnerNumberModel(booking.
                getDiscount(), 0, 999, 1);
        bookingInputs[3] = new JSpinner(discountModel);
        final JDialog editDialog = GUI.dialog("Add Booking", bookingLabels, 
                bookingInputs);
        JPanel myPanel = (JPanel)editDialog.getContentPane().getComponent(0);
        
        final JComboBox guestNamesCB = ((JComboBox)myPanel.getComponent(1));
        final DateField arrivalDateField = ((DateField)myPanel.getComponent(3));
        final DateField leavingDateField = ((DateField)myPanel.getComponent(5));
        final JSpinner discount = (JSpinner)myPanel.getComponent(7);
        
        JButton okButton = ((JButton)myPanel.getComponent(8));
        okButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                String guestName = (String)guestNamesCB.getSelectedItem();
                Date arrivalDate = (Date)arrivalDateField.getValue();
                Date leavingDate = (Date)leavingDateField.getValue();
                int discountI = (Integer)discount.getValue();
                try
                {
                    Room room = roomCtrl.getRoomById(roomId);
                    Guest guest = guestCtrl.getGuestByName(guestName);
                    bookingCtrl.editBooking(bookingId, room, guest, arrivalDate, 
                            leavingDate, discountI);
                }
                catch (Exception exc)
                {
                    GUI.showError(exc.getMessage(), "Error", roomTab);
                    return;
                }
                editDialog.setVisible(false);
                updateRoomTable();
                updateBookingTable(roomId);
            }
        });
        editDialog.setVisible(true);
    }
    
    private void removeBookingCB(int bookingId, int selectedRoom)
    {
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to remove the booking?", "Removal",
                JOptionPane.YES_NO_OPTION);

        if (choice == 0)
        {
            try
            {
                bookingCtrl.removeBooking(bookingId);
            }
            catch (Exception e)
            {
                GUI.showError(e.getMessage(), "Error", this);
                return;
            }
            updateRoomTable();
            updateBookingTable(selectedRoom);
        }
    }
    
    private void addRoomCB()
    {
        if (roomCtrl == null)
            roomCtrl = new RoomCtrl(GUI.selectedHotel.getName());
        final JDialog addDialog = GUI.dialog("Add", roomLabels, roomInputs);
        JPanel myPanel = (JPanel)addDialog.getContentPane().getComponent(0);
        
        final JSpinner roomNr = ((JSpinner)myPanel.getComponent(1));
        final JSpinner size = ((JSpinner)myPanel.getComponent(3));
        final JSpinner price = ((JSpinner)myPanel.getComponent(5));
        final JSpinner bedrooms = (JSpinner)myPanel.getComponent(7);
        
        JButton okButton = ((JButton)myPanel.getComponent(8));
        okButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    int roomNrI = (Integer)roomNr.getValue();
                    if (roomCtrl.getRoomByRoomNr(roomNrI) != null)
                    {
                        GUI.showError("Room with specified room nr already "
                                + "exists", "Error", roomTab);
                        return;
                    }
                    int sizeI = (Integer) size.getValue();
                    int priceI = (Integer) price.getValue();
                    int bedrooomsI = (Integer) bedrooms.getValue();
                    roomCtrl.addRoom(roomNrI, priceI, sizeI, bedrooomsI);
                }
                catch (Exception exc)
                {
                    GUI.showError(exc.getMessage(), "Error", roomTab);
                    return;
                }
                
                addDialog.setVisible(false);
                updateRoomTable();
            }
        });
        
        addDialog.setVisible(true);
    }
    
    private void removeRoomCB(int id)
    {
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to remove the room?", "Removal",
                JOptionPane.YES_NO_OPTION);

        if (choice == 0)
        {
            try
            {
                Room room = roomCtrl.getRoomById(id);
                roomCtrl.removeRoom(room.getRoomNr());
            }
            catch (Exception e)
            {
                GUI.showError(e.getMessage(), "Error", this);
                return;
            }
            updateRoomTable();
        }
    }
    
    private void editRoomCB(final int id)
    {
        if (roomCtrl == null)
            roomCtrl = new RoomCtrl(GUI.selectedHotel.getName());
        final JDialog editDialog = GUI.dialog("Edit", roomLabels, roomInputs);
        JPanel myPanel = (JPanel)editDialog.getContentPane().getComponent(0);
        
        final Room room = roomCtrl.getRoomById(id);
        final JSpinner roomNr = ((JSpinner)myPanel.getComponent(1));
        roomNr.setValue(room.getRoomNr());
        final JSpinner size = ((JSpinner)myPanel.getComponent(3));
        size.setValue(room.getSqMeters());
        final JSpinner price = ((JSpinner)myPanel.getComponent(5));
        price.setValue(room.getMeterCost());
        final JSpinner bedrooms = (JSpinner)myPanel.getComponent(7);
        bedrooms.setValue(room.getNrOfBedrooms());

        JButton okButton = ((JButton)myPanel.getComponent(8));
        okButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    int roomNrI = (Integer)roomNr.getValue();
                    if (roomNrI != room.getRoomNr() &&
                            roomCtrl.getRoomByRoomNr(roomNrI) != null)
                    {
                        GUI.showError("Another room with specified room nr "
                                + "already exists", "Error", roomTab);
                        return;
                    }
                    int sizeI = (Integer) size.getValue();
                    int priceI = (Integer) price.getValue();
                    int bedrooomsI = (Integer) bedrooms.getValue();
                    roomCtrl.editRoom(id, roomNrI, priceI, sizeI, bedrooomsI);
                }
                catch (Exception exc)
                {
                    GUI.showError(exc.getMessage(), "Error", roomTab);
                    return;
                }
                
                editDialog.setVisible(false);
                updateRoomTable();
            }
        });
        
        editDialog.setVisible(true);
    }
    
    private void updateRoomTable()
    {   
        DefaultTableModel model = (DefaultTableModel) roomTable.getModel();
        while(model.getRowCount() > 0)
            model.removeRow(0);
        
        if (roomCtrl == null)
            return;
        
        for(int i = 0; i < roomCtrl.getRoomCount(); i++)
        {
            Room room = roomCtrl.getRoomById(i);
            Object[] roomData = {i, room.getRoomNr(), room.getSqMeters(),
                room.getCost(), room.getNrOfBedrooms()};
            model.addRow(roomData);
        }
    }
    
    private void updateBookingTable(int selectedRoom)
    {
        Room room = roomCtrl.getRoomById(selectedRoom);
        Booking[] bookings = bookingCtrl.getBookingsByRoom(room);
        
        DefaultTableModel model = (DefaultTableModel) bookingTable.getModel();
        while(model.getRowCount() > 0)
            model.removeRow(0);
        
        for (int i = 0; i < bookings.length; i++)
        {
            Booking booking = bookings[i];
            Object[] bookingData = {i, booking.getId(), 
                booking.getGuest().getName(), booking.getArrivalDate(), 
                booking.getLeavingDate(), booking.getDiscount()};
            model.addRow(bookingData);
        }
    }
}
