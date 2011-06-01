package hotel.utils;

import java.util.HashSet;
import java.util.Random;
import java.util.Arrays;

/**
 *
 * @author tomas
 */
public class IdGenerator
{
    private HashSet<Integer> ids;
    private Random rnd;

    public IdGenerator()
    {
        ids = new HashSet<Integer>();
        rnd = new Random();
    }

    public IdGenerator(Integer[] ids)
    {
        this();
        this.ids.addAll(Arrays.asList(ids));
    }

    public int getNextId()
    {
        int nrOfGeneratedIds = ids.size();
        int randomInt = -1;

        while (ids.size() < nrOfGeneratedIds + 1)
        {
            randomInt = rnd.nextInt(9999) + 1;
            ids.add(randomInt);
        }

        return randomInt;
    }
    
    public boolean addId(int id)
    {
        return ids.add(id);
    }
    
    public boolean removeId(int id)
    {
        if (ids.contains(id))
        {
            ids.remove(id);
            return true;
        }
        else
            return false;
    }

    public boolean setSavedIds(Integer[] ids)
    {
        HashSet<Integer> temp = new HashSet<Integer>();

        if (temp.addAll(Arrays.asList(ids)))
            this.ids = temp;
        else
            return false;

        return true;
    }

    public Integer[] getSavedIdIs()
    {
        return ids.toArray(new Integer[ids.size()]);
    }
}
