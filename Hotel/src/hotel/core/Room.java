package hotel.core;

public class Room
{
    private int meterCost, sqMeters, nrOfBedrooms, roomNr;
    private boolean clean;
    
    public Room(int roomNr, int meterCost, int sqMeters, int nrOfBedrooms)
    {
        this.roomNr = roomNr;
        this.meterCost = meterCost;
        this.sqMeters = sqMeters;
        this.nrOfBedrooms = nrOfBedrooms;
        clean = true;
    }

    public void setRoomNr(int roomNr)
    {
        this.roomNr = roomNr;
    }

    public int getRoomNr()
    {
        return roomNr;
    }

    public boolean isClean()
    {
        return clean;
    }

    public int getMeterCost()
    {
        return meterCost;
    }

    public int getNrOfBedrooms()
    {
        return nrOfBedrooms;
    }

    public int getSqMeters()
    {
        return sqMeters;
    }
    
    public int getCost()
    {
        return meterCost * sqMeters;
    }

    public void setClean(boolean clean)
    {
        this.clean = clean;
    }

    public void setMeterCost(int meterCost)
    {
        this.meterCost = meterCost;
    }

    public void setNrOfBedrooms(int nrOfBedrooms)
    {
        this.nrOfBedrooms = nrOfBedrooms;
    }

    public void setSqMeters(int sqMeters)
    {
        this.sqMeters = sqMeters;
    }
}
