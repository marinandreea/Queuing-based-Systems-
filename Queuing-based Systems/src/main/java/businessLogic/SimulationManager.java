package businessLogic;

import model.Task;
import view.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class that contains methods for generating randomly the clients
 * Contains the simulation loop
 */
public class SimulationManager implements Runnable {

    private View view;
    private Scheduler scheduler;
    private ArrayList<Task> generatedTasks;
    Thread simM;

    private int minArrival;
    private int maxArrival;
    private int simulationTime;
    private int minService;
    private int maxService;
    private int nrQueues;
    private int nrClients;

    private int totalSTime = 0;
    private int totalClientsInQueues;
    private int max = 0;
    private int time = 0;
    private int waitingT = 0;
    private BufferedWriter writer;
    int q = 0;


    /**
     * @throws IOException
     * constructor which initializes the view, creates the thread for simulation,
     * creates and opens the file for output and calls initialize() method
     */
    public SimulationManager() throws IOException {

        view = new View();
        simM = new Thread(this);
        File file = new File("results.txt");
        file.createNewFile();
        writer = new BufferedWriter(new FileWriter(file.getName()));
        initialize();


    }

    /**
     * @param arrMin
     * @param arrMax
     * @param serMin
     * @param serMax
     * @param nrQ
     * @param nrC
     * @param simTime
     * method for setting the data received form GUI
     */
    public void setData(int arrMin, int arrMax, int serMin, int serMax, int nrQ, int nrC, int simTime){

        this.minArrival = arrMin;
        this.maxArrival = arrMax;
        this.minService = serMin;
        this.maxService = serMax;
        this.nrQueues = nrQ;
        this.nrClients = nrC;
        this.simulationTime = simTime;
    }

    /**
     * @return true if the data is valid
     * takes the data from GUI and if it is valid it calls the setData method
     */
    public boolean getInputData(){

        String minArr = view.getMinArrivalTimeTxtField().getText();
        String maxArr = view.getMaxArrivalTimeTxtField().getText();
        String minSer = view.getMinServiceTimeTxtField().getText();
        String maxSer = view.getMaxServiceTimeTxtField().getText();
        String nrQu = view.getNrQueuesTxtField().getText();
        String nrCl = view.getNrClientsTxtField().getText();
        String simTime = view.getSimulTimeTxtField().getText();

        int arrMin, arrMax, serMin, serMax, nrQ, nrC, simTim;
        try{
            arrMin = Integer.parseInt(minArr);
            arrMax = Integer.parseInt(maxArr);
            serMin = Integer.parseInt(minSer);
            serMax = Integer.parseInt(maxSer);
            nrQ = Integer.parseInt(nrQu);
            nrC = Integer.parseInt(nrCl);
            simTim = Integer.parseInt(simTime);

            if(arrMin > 0 && arrMax > 0 && serMin > 0 && serMax > 0 && nrQ > 0 && nrC > 0 && simTim > 0){

                if(arrMin < arrMax && serMin < serMax){
                    setData(arrMin,arrMax,serMin,serMax,nrQ,nrC,simTim);
                    return true;
                }else{
                    JOptionPane.showMessageDialog(null, "Invalid input data!");
                    return false;
                }
            }else{
                JOptionPane.showMessageDialog(null, "Invalid input data!");
                return false;
            }
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid input data!");
            return false;
        }
    }

    /**
     * method for initializing the start simulation button
     * it also creates and starts threads for each queue
     * calls the method for generating randomly the clients
     * starts the thread for simulation
     */
    public void initialize() {

        view.addStartButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(getInputData() == true){

                    scheduler = new Scheduler(nrQueues);
                    for(int i = 0;i < nrQueues;i++){
                        Thread t = new Thread(scheduler.getServers().get(i));
                        t.start();
                    }
                    generatedTasks = generateNRandomTasks(nrClients,minArrival,maxArrival,minService,maxService);
                    simM.start();
                }
            }
        });
    }


    /**
     * @param nrTasks
     * @param minArr
     * @param maxArr
     * @param minSer
     * @param maxSer
     * @return an ArrayList containing the randomly generated tasks
     */
    private ArrayList<Task> generateNRandomTasks(int nrTasks, int minArr, int maxArr, int minSer, int maxSer){

        ArrayList<Task> randomGeneratedTasks = new ArrayList<Task>();
        for(int i = 1; i <= nrTasks;i++){
            int arrival = 0;
            int service = 0;
            Random a = new Random();
            Random s = new Random();
            arrival = a.nextInt(maxArr - minArr) + minArr;
            service = s.nextInt(maxSer - minSer) + minSer;
            Task t = new Task(arrival,service,i);
            randomGeneratedTasks.add(t);
        }
        Collections.sort(randomGeneratedTasks);
        return randomGeneratedTasks;
    }


    /**
     * @param arrayTask
     * @return total service time of a queue
     */
    public int totalServiceTime(ArrayList<Task> arrayTask){

        arrayTask.forEach((t)->{
            totalSTime+=t.getServiceTime();
        });
        return totalSTime;
    }

    /**
     *
     * @param currentTime
     * the methods displays the output to the GUI and also writes it in the results.txt file
     */
    public void print( int currentTime){
        try{
            //BufferedWriter writer = new BufferedWriter(new FileWriter("results.txt"));
            StringBuilder s = new StringBuilder();
            int finalCurrentTime = currentTime;
            //s.append("\n");
            s.append("Time: "+ currentTime);
            s.append("\n");
            s.append("Waiting: ");
            if(generatedTasks.isEmpty()){
                s.append("There are no waiting clients");
            }

            generatedTasks.forEach(task ->{
                s.append("("+task.getClientId()+","+task.getArrivalTime()+","+task.getServiceTime()+") ");
            });
            s.append("\n");
            AtomicInteger nrQ= new AtomicInteger(1);
            scheduler.getServers().forEach(server -> {
                if(finalCurrentTime == 0) {
                    s.append("Queue " + nrQ + ": " + "closed"+"\n");
                    nrQ.getAndIncrement();
                }else{
                    s.append("Queue " + nrQ + ": " + server.toString()+"\n");
                    nrQ.getAndIncrement();
                }
            });

            writer.write(s.toString());
            writer.write("\n");
            view.updateLogs(s.toString());
            //writer.close();
        }catch(Exception e){}

    }


    /**
     * iterates generatedTasks list and picks tasks that have the arrival time equal to the current time
     * sends the tasks to queue by calling the minWaitingTimeStrategy method from the Scheduler
     * deletes the tasks from the list
     * updates the UI frame
     * increments currentTime
     * waits an interval of 1 second
     */
    @Override
    public void run() {

        int currentTime = 0;
        int totalS = totalServiceTime(generatedTasks);
        while(currentTime < simulationTime) {

            totalClientsInQueues = 0;


            ArrayList<Task> toRemove = new ArrayList<Task>();
            int finalCurrentTime = currentTime;
            Iterator<Task> ts = generatedTasks.iterator();
            while (ts.hasNext()) {
                Task k = ts.next();
                if (k.getArrivalTime() == finalCurrentTime) {
                    scheduler.minWaitingTimeStrategy(nrQueues, k);
                    toRemove.add(k);
                }
            }
            scheduler.getServers().forEach(server -> {
                        totalClientsInQueues += server.getTasks().size();

                    }
            );
            if (totalClientsInQueues > max) {
                max = totalClientsInQueues;
                time = currentTime;
            }
            generatedTasks.removeAll(toRemove);
            print(currentTime);
            q = 0;
            AtomicInteger ok = new AtomicInteger();
            scheduler.getServers().forEach(server -> {
                       if(server.getTasks().isEmpty()){
                          q++;
                       }
                    }
            );
            if(q == nrQueues){
                ok.set(1);
            }
            if(generatedTasks.isEmpty() && ok.get() == 1){
                currentTime = simulationTime;
            }
            currentTime++;
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                break;
            }
        }

        double nr = (double)totalS/(double)nrClients;
        String s = String.valueOf(nr);
        //time=time-1;
        view.updateLogs("Average service time: "+ s);
        view.updateLogs("Peak hour: " + time + " with " + max + " clients");
        try {
            writer.write("Average service time: "+ s+"\n");
            writer.write("Peak hour: " + time + " with " + max + " clients"+"\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * @param args
     * @throws IOException
     * launches the application
     */
    public static void main(String[] args) throws IOException {

        SimulationManager gen = new SimulationManager();
    }


}
