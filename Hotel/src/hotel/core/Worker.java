package hotel.core;

import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class Worker extends Person
{
    private String position;
    private Date birthDate, startedWorking;
    private int income;
    
    public Worker(String name, Date birthDate, Date startedWorking, int income,
            String position)
    {
        super(name);
        GregorianCalendar birth = new GregorianCalendar();
        birth.setTime(birthDate);
        GregorianCalendar retirement = new GregorianCalendar();
        GregorianCalendar youngster = new GregorianCalendar();

        retirement.set(Calendar.YEAR,
                retirement.get(Calendar.YEAR) - 70);
        youngster.set(Calendar.YEAR, youngster.get(Calendar.YEAR) - 14);

        if (birth.compareTo(retirement) <= 0)
            throw new IllegalArgumentException("Employee too old to " +
                    "work.");
        if (birth.compareTo(youngster) > 0)
            throw new IllegalArgumentException("Employee too young to " +
                    "work.");
        
        if (position.isEmpty())
            throw new IllegalArgumentException("Position can not be " +
                    "empty");
        
        this.birthDate = birthDate;
        this.startedWorking = startedWorking;
        this.position = position;
        this.income = income;
    }

    public void setIncome(int income)
    {
        this.income = income;
    }
    
    public void setBirthDate(Date birthDate)
    {
        GregorianCalendar birth = new GregorianCalendar();
        birth.setTime(birthDate);
        GregorianCalendar retirement = new GregorianCalendar();
        GregorianCalendar youngster = new GregorianCalendar();

        retirement.set(Calendar.YEAR,
                retirement.get(Calendar.YEAR) - 70);
        youngster.set(Calendar.YEAR, youngster.get(Calendar.YEAR) - 14);

        if (birth.compareTo(retirement) <= 0)
            throw new IllegalArgumentException("Employee too old to " +
                    "work.");
        if (birth.compareTo(youngster) > 0)
            throw new IllegalArgumentException("Employee too young to " +
                    "work.");
        this.birthDate = birthDate;
    }
    
    public void setStartedWorking(Date startedWorking)
    {
        this.startedWorking = startedWorking;
    }
    
    public void setPosition(String position)
    {
        if (position.isEmpty())
            throw new IllegalArgumentException("Position can not be " +
                    "empty");
        this.position = position;
    }

    public int getIncome()
    {
        return income;
    }

    public Date getBirthDate()
    {
        return birthDate;
    }

    public String getPosition()
    {
        return position;
    }

    public Date getStartedWorking()
    {
        return startedWorking;
    }
}
