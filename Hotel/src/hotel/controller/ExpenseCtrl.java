package hotel.controller;

import java.util.ArrayList;
import hotel.core.Expense;
import hotel.DB.ExpenseDB;

public class ExpenseCtrl
{
    private String guestName;
    private ArrayList<Expense> expenses;
    private ExpenseDB expenseDB;
    
    public ExpenseCtrl(String guestName)
    {
        this.guestName = guestName;
        expenseDB = new ExpenseDB();
        expenses = expenseDB.getExpensesByGuest(guestName);
    }
    
    public String getGuestName()
    {
        return guestName;
    }
    
    public void setGuestName(String guestName)
    {
        this.guestName = guestName;
        expenses = expenseDB.getExpensesByGuest(guestName);
    }
    
    public Expense getExpenseById(int id)
    {
        return expenses.get(id);
    }
    
    public Expense[] getExpensesByGuest(String name)
    {
        ArrayList<Expense> localExpenses = expenseDB.getExpensesByGuest(name);
        return localExpenses.toArray(new Expense[localExpenses.size()]);
    }
    
    public void removeExpense(String name)
    {
        expenses.remove(expenseDB.getExpense(name,guestName));
        expenseDB.delete(name, guestName);
    }
    
    public void addExpense(String name, double price)
    {
        Expense expense = new Expense(name, price);
        expenses.add(expense);
        expenseDB.insertExpense(expense, guestName);
    }
    
    public void editExpense(int id, String name, double price)
    {
        Expense oldExpense = expenses.get(id);
        Expense newExpense = new Expense(name, price);
        expenseDB.updateExpense(newExpense, oldExpense.getName(), guestName);
        expenses.set(id, newExpense);
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
