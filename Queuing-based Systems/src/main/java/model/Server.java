package model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class for objects of type Server
 */
public class Server implements Runnable{

    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;

    /**
     * Constructor which initializes the BlockingQueue and the AtomicInteger
     */
    public Server(){

        tasks = new LinkedBlockingDeque<>();
        waitingPeriod = new AtomicInteger(0);
    }


    /**
     * @return all tasks contained by a queue as a string
     */
    public String toString(){

        StringBuilder s = new StringBuilder();
        this.getTasks().forEach((tt)->{
            s.append("(").append(tt.getClientId()).append(",").append(tt.getArrivalTime()).append(",").append(tt.getServiceTime()).append(") ");
        });
        if(this.getTasks().isEmpty()){
            return "closed";
        }
        return s.toString();
    }

    /**
     * @return the blocking queue tasks
     */
    public BlockingQueue<Task> getTasks() {
        return tasks;
    }

    /**
     * @return the waitingPeriod of the queue
     */
    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }


    /**
     * contains the thread for a queue
     * it decrements the waitingPeriod and the serviceTime of the task and when the serviceTime is equal to 0 the task is removed from the queue
     */
    @Override
    public void run() {


        while(true){

            try{
                Thread.sleep(1000);
                if(tasks.peek() != null){
                    waitingPeriod.decrementAndGet();
                    tasks.peek().setServiceTime(tasks.peek().getServiceTime()-1);
                    if(tasks.peek().getServiceTime() == 0){
                        tasks.remove(tasks.peek());
                    }
                }

            } catch (InterruptedException e) {
                break;
            }
        }
    }


}
