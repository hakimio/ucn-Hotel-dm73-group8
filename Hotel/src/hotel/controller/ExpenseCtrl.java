package hotel.controller;

import java.util.ArrayList;
import hotel.core.Expense;
import hotel.DB.ExpenseDB;

public class ExpenseCtrl
{
    private String guestName, hotelName;
    private ArrayList<Expense> expenses;
    private ExpenseDB expenseDB;
    
    public ExpenseCtrl(String guestName, String hotelName)
    {
        this.guestName = guestName;
        this.hotelName = hotelName;
        expenseDB = new ExpenseDB();
        expenses = expenseDB.getExpensesByGuest(guestName, hotelName);
    }
    
    public String getGuestName()
    {
        return guestName;
    }
    
    public void setGuestName(String guestName)
    {
        this.guestName = guestName;
        expenses = expenseDB.getExpensesByGuest(guestName, hotelName);
    }

    public String getHotelName()
    {
        return hotelName;
    }

    public void setHotelName(String hotelName)
    {
        this.hotelName = hotelName;
        expenses = expenseDB.getExpensesByGuest(guestName, hotelName);
    }
    
    public Expense getExpenseById(int id)
    {
        return expenses.get(id);
    }
    
    public static Expense[] getExpensesByGuest(String name, String hotelName)
    {
        ExpenseDB expenseDB = new ExpenseDB();
        ArrayList<Expense> expenses = expenseDB.getExpensesByGuest(name, 
                hotelName);
        return expenses.toArray(new Expense[expenses.size()]);
    }
    
    public void removeExpense(int id)
    {
        Expense expense = expenses.get(id);
        expenseDB.delete(expense.getName(), guestName, hotelName);
        expenses.remove(id);
    }
    
    public void addExpense(String name, double price)
    {
        if (getExpenseByName(name) != null)
            throw new IllegalStateException("Expense with the specified name "
                    + "was already added.");
        Expense expense = new Expense(name, price);
        expenses.add(expense);
        expenseDB.insertExpense(expense, guestName, hotelName);
    }
    
    public void editExpense(int id, String name, double price)
    {
        Expense oldExpense = expenses.get(id);
        if (!name.equals(oldExpense.getName()) 
                && getExpenseByName(name) != null)
            throw new IllegalStateException("Another expense with the specified"
                    + " name was already added.");
        Expense newExpense = new Expense(name, price);
        expenseDB.updateExpense(newExpense, oldExpense.getName(), guestName, 
                hotelName);
        expenses.set(id, newExpense);
    }
    
    public Expense getExpenseByName(String name)
    {
        return expenseDB.getExpense(name, guestName, hotelName);
    }
    
    public int getExpenseCount()
    {
        return expenses.size();
    }
    
    public double getTotalExpenses()
    {
        double totalExpenses = 0;
        for(int i = 0; i < expenses.size(); i++)
            totalExpenses += expenses.get(i).getPrice();
        
        return totalExpenses;
    }
}
