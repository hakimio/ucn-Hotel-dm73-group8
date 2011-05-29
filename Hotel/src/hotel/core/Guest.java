package hotel.core;

import java.util.ArrayList;

public class Guest extends Person
{
    private ArrayList<Expense> expenses;
    
    public Guest(String name)
    {
        super(name);
        expenses = new ArrayList<Expense>();
    }
    
    public boolean addExpense(Expense expense)
    {
        return expenses.add(expense);
    }
    
    public boolean removeExpense(Expense expense)
    {
        return expenses.remove(expense);
    }
}
