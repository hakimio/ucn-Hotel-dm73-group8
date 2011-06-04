package hotel.controller;

import java.util.ArrayList;
import hotel.core.Worker;
import hotel.DB.WorkerDB;
import hotel.utils.DateUtils;
import java.util.Date;

public class WorkerCtrl
{
    private ArrayList<Worker> workers;
    private WorkerDB workerDB;
    private String hotelName;
    
    public WorkerCtrl(String hotelName)
    {
        this.hotelName = hotelName;
        workerDB = new WorkerDB();
        workers = workerDB.getWorkersByHotel(hotelName);
    }
    
    public String getHotelName()
    {
        return hotelName;
    }
    
    public void setHotelName(String hotelName)
    {
        this.hotelName = hotelName;
        workers = workerDB.getWorkersByHotel(hotelName);
    }
    
    public Worker getWorkerById(int id)
    {
        return workers.get(id);
    }
    
    public void removeWorker(String name)
    {
        workers.remove(workerDB.getWorker(name, hotelName));
        workerDB.delete(name, hotelName);
    }
    
    public void addWorker(String name, Date birthDate, Date startedWorking, 
            int income, String position)
    {
        if (startedWorking.before(DateUtils.getToday()))
            throw  new IllegalStateException("Date at which worker starts "
                    + "working can not be in the past.");
        Worker worker = new Worker(name, birthDate, startedWorking, income, 
                position);
        workers.add(worker);
        workerDB.insertWorker(worker, hotelName);
    }
    
    public void editWorker(int id, String name, Date birthDate, 
            Date startedWorking, int income, String position)
    {
        Worker oldWorker = workers.get(id);
        Worker newWorker = new Worker(name, birthDate, startedWorking, income, 
                position);
        workerDB.updateWorker(newWorker, oldWorker.getName(), hotelName);
        workers.set(id, newWorker);
    }
    
    public int getWorkerCount()
    {
        return workers.size();
    }
}
