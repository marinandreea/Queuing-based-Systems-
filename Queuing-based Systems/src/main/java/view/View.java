package view;


import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Class for the graphical user interface
 */
public class View extends JFrame {
    private JLabel nrClientsLabel;
    private JTextField nrClientsTxtField;
    private JLabel nrQueuesLabel;
    private JTextField nrQueuesTxtField;
    private JTextField simulTimeTxtField;
    private JLabel simulTimeLabel;
    private JLabel minArrivalTimeLabel;
    private JLabel maxArrivalTimeLabel;
    private JTextField minArrivalTimeTxtField;
    private JTextField maxArrivalTimeTxtField;
    private JLabel minServiceTimeLabel;
    private JLabel maxServiceTimeLabel;
    private JTextField minServiceTimeTxtField;
    private JTextField maxServiceTimeTxtField;
    private JButton startButton;
    private JTextArea logTxtArea;
    private JScrollPane logScrollPane;


    /**
     * constructor that sets the title, size, default close option and visibility for the frame
     * initializes the labels, test fields and start button
     * sets their bounds and adds them to the frame
     */
    public View() {

        this.setTitle("Queues Management");
        this.setSize(900, 900);
        this.setLayout((LayoutManager)null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

        //construct components
        nrClientsLabel = new JLabel ("Nr clients:");
        nrClientsTxtField = new JTextField (5);
        nrQueuesLabel = new JLabel ("Nr queues:");
        nrQueuesTxtField = new JTextField (5);
        simulTimeTxtField = new JTextField (5);
        simulTimeLabel = new JLabel ("Simulation time:");
        minArrivalTimeLabel = new JLabel ("Min Arrival Time:");
        maxArrivalTimeLabel = new JLabel ("Max Arrival Time:");
        minArrivalTimeTxtField = new JTextField (5);
        maxArrivalTimeTxtField = new JTextField (5);
        minServiceTimeLabel = new JLabel ("Min Service Time:");
        maxServiceTimeLabel = new JLabel ("Max Service Time:");
        minServiceTimeTxtField = new JTextField (5);
        maxServiceTimeTxtField = new JTextField (5);
        startButton = new JButton ("Start Simulation");
        logTxtArea = new JTextArea ();
        logScrollPane = new JScrollPane();


        //adjust size and set layout
        setPreferredSize (new Dimension (500, 500));
        //setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        setLayout (null);

        //add components
        add (nrClientsLabel);
        add (nrClientsTxtField);
        add (nrQueuesLabel);
        add (nrQueuesTxtField);
        add (simulTimeTxtField);
        add (simulTimeLabel);
        add (minArrivalTimeLabel);
        add (maxArrivalTimeLabel);
        add (minArrivalTimeTxtField);
        add (maxArrivalTimeTxtField);
        add (minServiceTimeLabel);
        add (maxServiceTimeLabel);
        add (minServiceTimeTxtField);
        add (maxServiceTimeTxtField);
        add (startButton);
        add (logScrollPane);


        //set component bounds (only needed by Absolute Positioning)
        nrClientsLabel.setBounds (40, 45, 70, 30);
        nrClientsTxtField.setBounds (110, 50, 45, 25);
        nrQueuesLabel.setBounds (40, 90, 70, 30);
        nrQueuesTxtField.setBounds (110, 95, 45, 25);
        simulTimeTxtField.setBounds (135, 135, 100, 25);
        simulTimeLabel.setBounds (40, 135, 100, 25);
        minArrivalTimeLabel.setBounds (280, 50, 105, 25);
        maxArrivalTimeLabel.setBounds (280, 80, 100, 25);
        minArrivalTimeTxtField.setBounds (390, 50, 100, 25);
        maxArrivalTimeTxtField.setBounds (390, 80, 100, 25);
        minServiceTimeLabel.setBounds (545, 50, 110, 25);
        maxServiceTimeLabel.setBounds (545, 80, 110, 25);
        minServiceTimeTxtField.setBounds (665, 50, 100, 25);
        maxServiceTimeTxtField.setBounds (665, 80, 100, 25);
        startButton.setBounds (320, 130, 135, 25);
        logScrollPane.setBounds(80, 250, 760, 390);
        logScrollPane.setViewportView(logTxtArea);
        logTxtArea.setText(" ");


    }

    /**
     * @param updateText
     * method used to update the text area in the user interface
     */
    public void updateLogs(String updateText){
        logTxtArea.append(updateText+"\n");
        JScrollBar myScrollBar = logScrollPane.getVerticalScrollBar();
        myScrollBar.setValue(myScrollBar.getMinimum());
    }


    /**
     * @return the value introduced by the user for number of clients as a string
     */
    public JTextField getNrClientsTxtField() {
        return nrClientsTxtField;
    }

    /**
     * @return the value introduced by the user for number of queues as a string
     */
    public JTextField getNrQueuesTxtField() {
        return nrQueuesTxtField;
    }

    /**
     * @return the value introduced by the user for simulation time as a string
     */
    public JTextField getSimulTimeTxtField() {
        return simulTimeTxtField;
    }

    /**
     * @return the value introduced by the user for minimum arrival time as a string
     */
    public JTextField getMinArrivalTimeTxtField() {
        return minArrivalTimeTxtField;
    }

    /**
     * @return the value introduced by the user for maximum arrival time as a string
     */
    public JTextField getMaxArrivalTimeTxtField() {
        return maxArrivalTimeTxtField;
    }

    /**
     * @return the value introduced by the user for minimum service time as a string
     */
    public JTextField getMinServiceTimeTxtField() {
        return minServiceTimeTxtField;
    }

    /**
     * @return the value introduced by the user for maximum service time as a string
     */
    public JTextField getMaxServiceTimeTxtField() {
        return maxServiceTimeTxtField;
    }

    /**
     * @param actionListener for start simulation button
     */
    public void addStartButtonActionListener(final ActionListener actionListener){
        startButton.addActionListener(actionListener);
    }

}

