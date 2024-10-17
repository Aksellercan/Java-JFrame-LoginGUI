import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;

public class Main {

     static class SimpleFrame extends JFrame {

         JTextField textField;
         JTextField textField1;
         JButton button;
         JTextArea textArea;
         JLabel label;
         JLabel label1;

     public SimpleFrame() {
            setSize(600, 400);
            setTitle("Simple Login Window");
            setLocation(new Point(400, 400));
            setLayout(new GridLayout(7, 2, 10, 10));

            textField = new JTextField(20);
            textField1 = new JTextField(20);
            button = new JButton("OK");
            JLabel label = new JLabel("Name:");
            JLabel label1 = new JLabel("Password:");

            textArea = new JTextArea(5,20);
            textArea.setEditable(false);

            button.addActionListener(new ButtonAction(this));

            add(label);
            add(textField);
            add(label1);
            add(textField1);
            add(button);
            add(textArea);

            setLocationRelativeTo(null);
            setVisible(true);
        }
    }
    static class ButtonAction implements ActionListener {
         SimpleFrame frame;

         public ButtonAction(SimpleFrame frame) {
             this.frame = frame;
         }
        @Override
        public void actionPerformed(ActionEvent e) {
            String user = frame.textField.getText().trim();
            String pass = frame.textField1.getText().trim();

            frame.textArea.setText(null);
            if(user.isEmpty() && pass.isEmpty()) {
                Fileclass.showErrorDialog("Please enter a valid username and password");
                return;
            }
            if (Fileclass.userExists(user)) {
                if (Fileclass.readfromfile(user, pass)) {
                    frame.textArea.append("Welcome " + user + "\n");
                } else {
                    frame.textArea.append("Incorrect login for user " + user + "\n");
                }
            } else {
                Fileclass.addtofile(user, pass);
                frame.textArea.append("User named " + user + " added to file" + "\n");
            }
         }
    }
    static class Fileclass {
        private static String filepath ="C:\\Users\\Aksell\\IdeaProjects\\multiquestionsv2\\src\\filereader.txt";

         static void addtofile(String user, String pass){
            try(FileWriter mywriter = new FileWriter(filepath,true)) {
                mywriter.write(user + "," + pass + "\n");
            } catch (IOException e) {
                 System.out.println("An error occurred.");
                 e.printStackTrace();
             }
        }
         static boolean readfromfile(String user, String pass) {
            try(BufferedReader br = new BufferedReader(new FileReader(filepath))){
                String line;
                while((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                        if (user.equals(values[0]) && pass.equals(values[1])) {
                            return true;
                        }
                }
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            return false;
        }
         static boolean userExists(String user) {
            try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
                String line;
                while ((line = br.readLine()) != null) {
                        String[] values = line.split(",");
                        if (values.length > 0 && user.equals(values[0])) {
                            return true;
                        }
                }
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            return false;
        }
        public static void showErrorDialog(String message) {
            JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
        }
     }
            public static void main(String[] args) {
                EventQueue.invokeLater(() -> {
                    var frame = new Main.SimpleFrame();
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setVisible(true);
                });
            }
        }