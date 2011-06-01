package hotel.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import hotel.controller.RoomCtrl;
import hotel.controller.BookingCtrl;
import hotel.core.Room;
import hotel.core.Booking;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class RoomTab extends JPanel
{
    private JTable roomTable;
    private JTable bookingTable;
    private RoomCtrl roomCtrl;
    private BookingCtrl bookingCtrl;
    
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
        
        String[] roomColumns = new String[] {"#", "Nr", "Size", "Price", 
            "bedrooms"};
        roomTable = GUI.createTable(roomColumns);
        updateRoomTable();
        
        String[] bookingColumns = new String[] {"#", "id", "Guest Name", 
            "Arrival Date", "Leaving Date", "Discount"};
        bookingTable = GUI.createTable(bookingColumns);
        bookingTable.getColumnModel().getColumn(1).setResizable(false);
        bookingTable.getColumnModel().getColumn(1).setMinWidth(0);
        bookingTable.getColumnModel().getColumn(1).setWidth(0);
        
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
                Room room = roomCtrl.getRoomById(selected);
                updateBookingTable(bookingCtrl.getBookingsByRoom(room));
            }
        });
        
        JScrollPane roomScrollPane = new JScrollPane(roomTable);
        JScrollPane bookingScrollPane = new JScrollPane(bookingTable);
        
        JButton AddRoom = new JButton("Add");
        final RoomTab roomTab = this;
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
                            getValueAt(bookingTable.getSelectedRow(), 1));
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
                            getValueAt(bookingTable.getSelectedRow(), 1));
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
    
    private void addBookingCB(int roomId)
    {
        
    }
    
    private void editBookingCB(int bookingId)
    {
        
    }
    
    private void removeBookingCB(int bookingId)
    {
        
    }
    
    private void addRoomCB()
    {
        
    }
    
    private void removeRoomCB(int id)
    {
        
    }
    
    private void editRoomCB(int id)
    {
        
    }
    
    private void updateRoomTable()
    {
        if (roomCtrl == null)
            return;
        
        DefaultTableModel model = (DefaultTableModel) roomTable.getModel();
        while(model.getRowCount() > 0)
            model.removeRow(0);
        
        for(int i = 0; i < roomCtrl.getRoomCount(); i++)
        {
            Room room = roomCtrl.getRoomById(i);
            Object[] roomData = {i, room.getRoomNr(), room.getSqMeters(),
                room.getCost(), room.getNrOfBedrooms()};
            model.addRow(roomData);
        }
    }
    
    private void updateBookingTable(Booking[] bookings)
    {
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
