package model;

/**
 * Class for objects of type Task
 */
public class Task implements Comparable<Task>{

    private int arrivalTime;
    private int serviceTime;
    private int clientID;

    /**
     * @param arrivalTime
     * @param serviceTime
     * @param clientID
     */
    public Task(int arrivalTime, int serviceTime,int clientID)
    {
        this.arrivalTime=arrivalTime;
        this.serviceTime=serviceTime;
        this.clientID=clientID;
    }

    /**
     * @return arrival time
     */
    public int getArrivalTime() {
        return arrivalTime;
    }

    /**
     * @return service time
     */
    public int getServiceTime() {
        return serviceTime;
    }

    /**
     * @param serviceTime
     */
    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    /**
     * @return the id of the client
     */
    public int getClientId()
    {
        return this.clientID;
    }

    /**
     * @param o of type Task
     * @return 1 if the list is sorted
     */
    @Override
    public int compareTo(Task o) {
        if(o.getArrivalTime() > this.getArrivalTime())
                    return -1;
        else if(o.getArrivalTime() == this.getArrivalTime())
                    return 0;
        else
                    return 1;

    }

}

