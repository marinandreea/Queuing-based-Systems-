package businessLogic;

import model.Server;
import model.Task;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class for objects of type Scheduler. It sends the task to Servers according to the established strategy
 */
public class Scheduler {

    private ArrayList<Server> servers;

    /**
     * @param maxNoServers
     * constructor for class Scheduler
     * creates a new server and adds it to the serves ArrayList
     */
    public Scheduler(int maxNoServers) {

        servers = new ArrayList<Server>();
        for(int i = 0;i < maxNoServers;i++){
            Server s = new Server();
            servers.add(s);
        }
    }

    /**
     * @param maxNoServers
     * @param t
     * strategy method for sending tasks to servers
     * it finds the server with the minimum waitingPeriod and adds the task to that server
     */
    public void minWaitingTimeStrategy(int maxNoServers, Task t){

        int minWaitingTimeServer = servers.get(0).getWaitingPeriod().get() ;
        int poz = 0;
        for(int i = 0;i < maxNoServers;i++){

            if(servers.get(i).getWaitingPeriod().get() < minWaitingTimeServer){
                      minWaitingTimeServer = servers.get(i).getWaitingPeriod().get();
                      poz = i;
            }
        }
        servers.get(poz).getTasks().add(t);
        servers.get(poz).getWaitingPeriod().addAndGet(t.getServiceTime());

    }

    /**
     * @return the list of servers
     */
    public ArrayList<Server> getServers() {
        return servers;
    }

}
