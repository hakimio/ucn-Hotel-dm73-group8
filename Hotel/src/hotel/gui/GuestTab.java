package hotel.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import hotel.controller.GuestCtrl;
import hotel.controller.ExpenseCtrl;
import hotel.core.Guest;
import hotel.core.Expense;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class GuestTab extends JPanel
{
    private JTable guestTable;
    private JTable expenseTable;
    private GuestCtrl guestCtrl;
    private ExpenseCtrl expenseCtrl;
    private JComponent[] expenseInputs;
    private JLabel[] expenseLabels;
    private final GuestTab guestTab = this;
    
    public GuestTab()
    {
        if (GUI.selectedHotel != null)
        {
            String hotelName = GUI.selectedHotel.getName();
            expenseCtrl = new ExpenseCtrl(hotelName);
            guestCtrl = new GuestCtrl(GUI.selectedHotel.getName());
        }
        else
        {
            guestCtrl = null;
            expenseCtrl = null;
        }
        
        expenseLabels = new JLabel[]{new JLabel("Name"), new JLabel("Price")};
        expenseInputs = new JComponent[2];
        expenseInputs[0] = new JTextField();
        SpinnerModel priceSpinner = new SpinnerNumberModel(0.1, 0.1, 999, 0.01);
        expenseInputs[1] = new JSpinner(priceSpinner);
        
        String[] guestColumns = new String[] {"#", "Name"};
        guestTable = GUI.createTable(guestColumns);
        updateGuestTable();
        
        String[] expenseColumns = new String[] {"#", "Name", "Price"};
        expenseTable = GUI.createTable(expenseColumns);
        
        guestTable.getSelectionModel().addListSelectionListener(
        new ListSelectionListener() 
        {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                if (expenseCtrl == null)
                    return;
                
                int selected;
                if (guestTable.getSelectedRowCount() > 0)
                    selected = guestTable.getSelectedRow();
                else
                    return;
                
                updateExpenseTable(selected);
            }
        });
        
        JScrollPane guestScrollPane = new JScrollPane(guestTable);
        JScrollPane expenseScrollPane = new JScrollPane(expenseTable);
        
        JButton addGuest = new JButton("Add");
        addGuest.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (GUI.selectedHotel == null)
                    GUI.showError("Hotel must be selected.", "Error", guestTab);
                else
                    addGuestCB();
            }
        });
        JButton editGuest = new JButton("Edit");
        editGuest.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (GUI.selectedHotel == null)
                    GUI.showError("Hotel must be selected.", "Error", guestTab);
                else if (guestTable.getSelectedRowCount() < 1)
                    GUI.showError("Please select some guest.", "Error", 
                            guestTab);
                else 
                    editGuestCB(guestTable.getSelectedRow());
            }
        });
        
        JButton removeGuest = new JButton("Remove");
        removeGuest.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (GUI.selectedHotel == null)
                    GUI.showError("Hotel must be selected.", "Error", guestTab);
                else if (guestTable.getSelectedRowCount() < 1)
                    GUI.showError("Please select some guest.", "Error", 
                            guestTab);
                else 
                    removeGuestCB(guestTable.getSelectedRow());
            }
        });
        
        JToolBar guestToolBar = new JToolBar();
        guestToolBar.add(addGuest);
        guestToolBar.add(editGuest);
        guestToolBar.add(removeGuest);
        
        JButton addExpense = new JButton("Add");
        addExpense.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (GUI.selectedHotel == null)
                    GUI.showError("Hotel must be selected.", "Error", guestTab);
                else if (guestTable.getSelectedRowCount() < 1)
                    GUI.showError("Please select some guest.", "Error", 
                            guestTab);
                else 
                    addExpenseCB(guestTable.getSelectedRow());
            }
        });
        
        JButton editExpense = new JButton("Edit");
        editExpense.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (GUI.selectedHotel == null)
                    GUI.showError("Hotel must be selected.", "Error", guestTab);
                else if (guestTable.getSelectedRowCount() < 1)
                    GUI.showError("Please select some guest.", "Error", 
                            guestTab);
                else if (expenseTable.getSelectedRowCount() < 1)
                    GUI.showError("Please select some expense", "Error", 
                            guestTab);
                else 
                    editExpenseCB(expenseTable.getSelectedRow(), 
                        guestTable.getSelectedRow());
            }
        });
        JButton removeExpense = new JButton("Remove");
        removeExpense.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (GUI.selectedHotel == null)
                    GUI.showError("Hotel must be selected.", "Error", guestTab);
                else if (guestTable.getSelectedRowCount() < 1)
                    GUI.showError("Please select some guest.", "Error", 
                            guestTab);
                else if (guestTable.getSelectedRowCount() < 1)
                    GUI.showError("Please select some expense", "Error", 
                            guestTab);
                else 
                    removeExpenseCB(expenseTable.getSelectedRow(),
                            guestTable.getSelectedRow());
            }
        });
        
        JToolBar expenseToolBar = new JToolBar();
        expenseToolBar.add(addExpense);
        expenseToolBar.add(editExpense);
        expenseToolBar.add(removeExpense);
        
        JPanel guestPanel = new JPanel();
        guestPanel.setLayout(new BorderLayout());
        guestPanel.add(guestToolBar, BorderLayout.PAGE_START);
        guestPanel.add(guestScrollPane);
        
        JPanel expensePanel = new JPanel();
        expensePanel.setLayout(new BorderLayout());
        expensePanel.add(expenseToolBar, BorderLayout.PAGE_START);
        expensePanel.add(expenseScrollPane);
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, 
                guestPanel, expensePanel);
        splitPane.setDividerLocation(200);
        
        this.add(splitPane);
        this.setVisible(true);
    }

    private void updateGuestTable()
    {
        DefaultTableModel model = (DefaultTableModel) guestTable.getModel();
        while(model.getRowCount() > 0)
            model.removeRow(0);
        
        if (guestCtrl == null)
            return;
        
        for(int i = 0; i < guestCtrl.getGuestCount(); i++)
        {
            Guest guest = guestCtrl.getGuestById(i);
            Object[] roomData = {i+1, guest.getName()};
            model.addRow(roomData);
        }
    }
    
    private void updateExpenseTable(int selectedGuest)
    {
        Guest guest = guestCtrl.getGuestById(selectedGuest);
        Expense[] expenses = expenseCtrl.getExpensesByGuest(guest.getName());
        
        DefaultTableModel model = (DefaultTableModel) expenseTable.getModel();
        while(model.getRowCount() > 0)
            model.removeRow(0);
        
        for (int i = 0; i < expenses.length; i++)
        {
            Expense expense = expenses[i];
            Object[] expenseData = {i+1, expense.getName(), expense.getPrice()};
            model.addRow(expenseData);
        }
    }
    
    private void addGuestCB()
    {
        
    }
    
    private void editGuestCB(int id)
    {
        
    }
    
    private void removeGuestCB(int id)
    {
        
    }
    
    private void addExpenseCB(int guestId)
    {
        
    }
    
    private void editExpenseCB(int expenseId, int guestId)
    {
        
    }
    
    private void removeExpenseCB(int expenseId, int guestId)
    {
        
    }
}
