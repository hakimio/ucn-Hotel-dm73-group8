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
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class GuestTab extends JPanel
{
    private JTable guestTable;
    private JTable expenseTable;
    private GuestCtrl guestCtrl;
    private ExpenseCtrl expenseCtrl;
    private JComponent[] expenseInputs;
    private JLabel[] expenseLabels;
    private final GuestTab guestTab = this;
    //private GUI gui;
    
    public GuestTab()
    {
        this.setLayout(new GridLayout(1, 0));
        expenseCtrl = null;
        if (GUI.getHotel() != null)
        {
            String hotelName = GUI.getHotel().getName();
            if (guestCtrl == null)
                guestCtrl = new GuestCtrl(hotelName);
            else
                guestCtrl.setHotelName(hotelName);
        }
        else
        {
            guestCtrl = null;
        }
        
        expenseLabels = new JLabel[]{new JLabel("Name"), new JLabel("Price")};
        expenseInputs = new JComponent[2];
        expenseInputs[0] = new JTextField();
        SpinnerModel priceSpinner = new SpinnerNumberModel(0.1, 0.1, 999, 0.01);
        expenseInputs[1] = new JSpinner(priceSpinner);
        
        String[] guestColumns = new String[] {"#", "Name"};
        guestTable = GUI.createTable(guestColumns);
        guestTable.getColumnModel().getColumn(0).setMaxWidth(30);
        updateGuestTable();
        final TableRowSorter<TableModel> guestSorter = new TableRowSorter<TableModel>();
        guestSorter.setModel(guestTable.getModel());
        guestTable.setRowSorter(guestSorter);
        
        String[] expenseColumns = new String[] {"#", "Name", "Price"};
        expenseTable = GUI.createTable(expenseColumns);
        expenseTable.getColumnModel().getColumn(0).setMaxWidth(30);
        guestTable.getSelectionModel().addListSelectionListener(
        new ListSelectionListener() 
        {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                if (e.getValueIsAdjusting())
                    return;
                
                if (guestTable.getSelectedRowCount() < 1 && 
                        guestTable.getRowCount() > 0)
                    guestTable.setRowSelectionInterval(0, 0);
                
                int selected;
                if (guestTable.getSelectedRowCount() > 0)
                    selected = guestTable.getSelectedRow();
                else
                    return;

                updateExpenseTable((Integer)guestTable.
                        getValueAt(selected, 0) - 1);
            }
        });
        
        final TableRowSorter<TableModel> expenseSorter = new TableRowSorter<TableModel>();
        expenseSorter.setModel(expenseTable.getModel());
        expenseTable.setRowSorter(expenseSorter);
        
        JScrollPane guestScrollPane = new JScrollPane(guestTable);
        JScrollPane expenseScrollPane = new JScrollPane(expenseTable);
        
        JButton addGuest = new JButton("Add");
        addGuest.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (GUI.getHotel() == null)
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
                if (GUI.getHotel() == null)
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
                if (GUI.getHotel() == null)
                    GUI.showError("Hotel must be selected.", "Error", guestTab);
                else if (guestTable.getSelectedRowCount() < 1)
                    GUI.showError("Please select some guest.", "Error", 
                            guestTab);
                else 
                    removeGuestCB(guestTable.getSelectedRow());
            }
        });
        
        JToolBar guestToolBar = new JToolBar();
        guestToolBar.setFloatable(false);
        guestToolBar.add(addGuest);
        guestToolBar.add(editGuest);
        guestToolBar.add(removeGuest);
        
        final JTextField filterField = new JTextField();
        filterField.addKeyListener(new KeyListener() {

            @Override public void keyTyped(KeyEvent e) {}
            @Override public void keyPressed(KeyEvent e) {}
            
            @Override 
            public void keyReleased(KeyEvent e) 
            {
                String text = filterField.getText();
                if (text.isEmpty())
                    guestSorter.setRowFilter(null);
                else
                    guestSorter.setRowFilter(RowFilter.regexFilter(text, 1));
            }
        });

        guestToolBar.addSeparator();
        guestToolBar.add(new JLabel("Filter   "));
        guestToolBar.addSeparator();
        guestToolBar.add(filterField);
        guestToolBar.addSeparator();
        
        JButton addExpense = new JButton("Add");
        addExpense.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (GUI.getHotel() == null)
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
                if (GUI.getHotel() == null)
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
                if (GUI.getHotel() == null)
                    GUI.showError("Hotel must be selected.", "Error", guestTab);
                else if (guestTable.getSelectedRowCount() < 1)
                    GUI.showError("Please select some guest.", "Error", 
                            guestTab);
                else if (expenseTable.getSelectedRowCount() < 1)
                    GUI.showError("Please select some expense", "Error", 
                            guestTab);
                else 
                    removeExpenseCB(expenseTable.getSelectedRow(),
                            guestTable.getSelectedRow());
            }
        });
        
        JToolBar expenseToolBar = new JToolBar();
        expenseToolBar.setFloatable(false);
        expenseToolBar.add(new JLabel("Expenses   "));
        expenseToolBar.add(addExpense);
        expenseToolBar.add(editExpense);
        expenseToolBar.add(removeExpense);
        
        String[] fieldNames = new String[] {"Expense Name", "Price"};
        final JComboBox expensefilterBox = new JComboBox(fieldNames);
        final JTextField expensefilterField = new JTextField();
        expensefilterField.addKeyListener(new KeyListener() {

            @Override public void keyTyped(KeyEvent e) {}
            @Override public void keyPressed(KeyEvent e) {}
            
            @Override 
            public void keyReleased(KeyEvent e) 
            {
                String text = expensefilterField.getText();
                int i = expensefilterBox.getSelectedIndex() + 1;
                if (text.isEmpty())
                    expenseSorter.setRowFilter(null);
                else
                    expenseSorter.setRowFilter(RowFilter.regexFilter(text, i));
            }
        });

        expenseToolBar.addSeparator();
        expenseToolBar.add(new JLabel("Filter by   "));
        expenseToolBar.add(expensefilterBox);
        expenseToolBar.addSeparator();
        expenseToolBar.add(expensefilterField);
        expenseToolBar.addSeparator();
        
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
    
    public void update()
    {
        if (GUI.getHotel() != null && guestCtrl == null)
            guestCtrl = new GuestCtrl(GUI.getHotel().getName());
        else if(GUI.getHotel() != null)
            guestCtrl.setHotelName(GUI.getHotel().getName());
        else
            guestCtrl = null;

        updateGuestTable();
        
        if (guestTable.getSelectedRowCount() < 1 && 
                guestTable.getRowCount() > 0)
            guestTable.setRowSelectionInterval(0, 0);
    }

    private void updateGuestTable()
    {
        DefaultTableModel model = (DefaultTableModel) guestTable.getModel();
        while(model.getRowCount() > 0)
            model.removeRow(0);
        
        if (guestCtrl == null || guestCtrl.getGuestCount() == 0)
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
        DefaultTableModel model = (DefaultTableModel) expenseTable.getModel();
        while(model.getRowCount() > 0)
            model.removeRow(0);
        if (guestCtrl == null)
            return;
        Guest guest = guestCtrl.getGuestById(selectedGuest);
        if (guest == null)
            return;
        
        Expense[] expenses = ExpenseCtrl.getExpensesByGuest(guest.getName());
        
        for (int i = 0; i < expenses.length; i++)
        {
            Expense expense = expenses[i];
            Object[] expenseData = {i+1, expense.getName(), expense.getPrice()};
            model.addRow(expenseData);
        }
    }
    
    private void addGuestCB()
    {
        if (guestCtrl == null)
            guestCtrl = new GuestCtrl(GUI.getHotel().getName());

        String guestName = JOptionPane.showInputDialog(guestTab, "Name");

        if (guestName != null)
        {
            try
            {
                guestCtrl.addGuest(guestName);
            }
            catch (Exception e)
            {
                GUI.showError(e.getMessage(), "Error", this);
                return;
            }
            updateGuestTable();
            GUI.updateBookings();
            GUI.updateRooms();
        }
    }
    
    private void editGuestCB(int id)
    {
        if (guestCtrl == null)
            guestCtrl = new GuestCtrl(GUI.getHotel().getName());

        Guest guest = guestCtrl.getGuestById(id);
        String guestName = JOptionPane.showInputDialog(guestTab, "Name", 
                guest.getName());

        if (guestName != null)
        {
            try
            {
                guestCtrl.editGuest(id, guestName);
            }
            catch (Exception e)
            {
                GUI.showError(e.getMessage(), "Error", this);
                return;
            }
            updateGuestTable();
            guestTable.setRowSelectionInterval(id, id);
            GUI.updateBookings();
            GUI.updateRooms();
        }
    }
    
    private void removeGuestCB(int id)
    {
        String guestName = guestCtrl.getGuestById(id).getName();
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to remove \""+ guestName +"\" ?",
                "Removal",
                JOptionPane.YES_NO_OPTION);

        if (choice == 0)
        {
            try
            {
                guestCtrl.removeGuest(id);
            }
            catch (Exception e)
            {
                GUI.showError(e.getMessage(), "Error", this);
            }
            
            updateGuestTable();
            GUI.updateBookings();
            GUI.updateRooms();
        }
    }
    
    private void addExpenseCB(final int guestId)
    {
        if (guestCtrl == null)
            guestCtrl = new GuestCtrl(GUI.getHotel().getName());
        String guestName = guestCtrl.getGuestById(guestId).getName();
        if (expenseCtrl == null)
            expenseCtrl = new ExpenseCtrl(guestName);
        else
            expenseCtrl.setGuestName(guestName);
        
        final JDialog addDialog = GUI.dialog("Add Expense", expenseLabels, 
                expenseInputs);
        JPanel myPanel = (JPanel)addDialog.getContentPane().getComponent(0);
        
        final JTextField expenseName = ((JTextField)myPanel.getComponent(1));
        final JSpinner price = ((JSpinner)myPanel.getComponent(3));
        
        JButton okButton = ((JButton)myPanel.getComponent(4));
        okButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                String expenseNameS = expenseName.getText();
                double priceI = (Double)price.getValue();
                try
                {
                    expenseCtrl.addExpense(expenseNameS, priceI);
                }
                catch (Exception exc)
                {
                    GUI.showError(exc.getMessage(), "Error", guestTab);
                    return;
                }
                addDialog.setVisible(false);
                updateGuestTable();
                guestTable.setRowSelectionInterval(guestId, guestId);
                updateExpenseTable(guestId);
            }
        });
        addDialog.setVisible(true);
    }
    
    private void editExpenseCB(final int expenseId, final int guestId)
    {
        if (guestCtrl == null)
            guestCtrl = new GuestCtrl(GUI.getHotel().getName());
        String guestName = guestCtrl.getGuestById(guestId).getName();
        if (expenseCtrl == null)
            expenseCtrl = new ExpenseCtrl(guestName);
        else
            expenseCtrl.setGuestName(guestName);
        
        Expense expense = expenseCtrl.getExpenseById(expenseId);
        
        final JDialog editDialog = GUI.dialog("Add Expense", expenseLabels, 
                expenseInputs);
        JPanel myPanel = (JPanel)editDialog.getContentPane().getComponent(0);
        
        final JTextField expenseName = ((JTextField)myPanel.getComponent(1));
        expenseName.setText(expense.getName());
        final JSpinner price = ((JSpinner)myPanel.getComponent(3));
        price.setValue(expense.getPrice());
        
        JButton okButton = ((JButton)myPanel.getComponent(4));
        okButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                String expenseNameS = expenseName.getText();
                double priceI = (Double)price.getValue();
                try
                {
                    expenseCtrl.editExpense(expenseId, expenseNameS, priceI);
                }
                catch (Exception exc)
                {
                    GUI.showError(exc.getMessage(), "Error", guestTab);
                    return;
                }
                editDialog.setVisible(false);
                updateGuestTable();
                guestTable.setRowSelectionInterval(guestId, guestId);
                updateExpenseTable(guestId);
            }
        });
        editDialog.setVisible(true);
    }
    
    private void removeExpenseCB(int expenseId, int guestId)
    {
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to remove selected expense?", "Removal",
                JOptionPane.YES_NO_OPTION);
        
        String guestName = guestCtrl.getGuestById(guestId).getName();
        if (expenseCtrl == null)
            expenseCtrl = new ExpenseCtrl(guestName);
        else
            expenseCtrl.setGuestName(guestName);
        
        if (choice == 0)
        {
            try
            {
                expenseCtrl.removeExpense(expenseId);
            }
            catch (Exception e)
            {
                GUI.showError(e.getMessage(), "Error", this);
                return;
            }
            updateGuestTable();
            guestTable.setRowSelectionInterval(guestId, guestId);
            updateExpenseTable(guestId);
        }
    }
}
