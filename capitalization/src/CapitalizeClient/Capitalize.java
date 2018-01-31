
package CapitalizeClient;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Group 12
 */
public class Capitalize {

    private BufferedReader in;
    private PrintWriter out;
    private JFrame frame = new JFrame("Capitalizetion Client Program");
    private JTextField dataField = new JTextField(50);
    private JTextArea messageArea = new JTextArea(15, 60);
    

    
    public Capitalize() {

        // Layout GUI
        messageArea.setEditable(false);
        frame.getContentPane().add(dataField, "North");
        frame.getContentPane().add(new JScrollPane(messageArea), "Center");
        messageArea.setToolTipText("YOUR CAPITALIZE LETTERS SHOW IN HEAR");
        dataField.setToolTipText("ENTER TEXT TO GET UPPERCASE LETTERS ");
        // Add Listeners
        dataField.addActionListener(new ActionListener() {
            /**
             * Responds to pressing the enter key 
             */
            public void actionPerformed(ActionEvent e) {
                out.println(dataField.getText());
                   String response;
                try {
                    response = in.readLine();
                    if (response == null || response.equals("")) {
                          System.exit(0);
                      }
                } catch (IOException ex) {
                       response = "Error: " + ex;
                }
                messageArea.append(response + "\n");
                dataField.selectAll();
            }
        });
    }

    
    public void connectToServer() throws IOException {

        // Get the server address from a dialog box.
        String serverAddress ="127.0.0.1";
                /*JOptionPane.showInputDialog(
            frame,
            "Enter IP Address of the Server:",
            "Welcome to the Capitalization Program",
            JOptionPane.QUESTION_MESSAGE);*/

        // Make connection and initialize streams
        Socket socket = new Socket(serverAddress, 1000);
        in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        // Consume the initial welcoming messages from the server
        for (int i = 0; i < 3; i++) {
            messageArea.append(in.readLine() + "\n");
        }
    }

    /**
     * Runs the client application.
     */
    public static void main(String[] args) throws Exception {
        Capitalize client = new Capitalize();
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.pack();
        client.frame.setVisible(true);
        client.connectToServer();
    }
}