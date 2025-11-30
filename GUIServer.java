import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

class MarvellousServer implements ActionListener
{
    JFrame fobj;
    JButton sendButton;
    JTextField sendField;
    JTextArea chatArea;

    Socket sobj;
    PrintStream pobj;
    BufferedReader bobj1;

    String str1 = null, str2 = null;

    public MarvellousServer(String title, int width, int height, Socket sobj) throws Exception
    {
        this.sobj = sobj;

        pobj = new PrintStream(sobj.getOutputStream());
        bobj1 = new BufferedReader(new InputStreamReader(sobj.getInputStream()));

        fobj = new JFrame(title);
        fobj.getContentPane();   

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(chatArea);
        scroll.setBounds(20, 20, 360, 180);

        sendField = new JTextField();
        sendField.setBounds(20, 220, 250, 30);

        sendButton = new JButton("SEND");
        sendButton.setBounds(280, 220, 100, 30);
        sendButton.addActionListener(this);

        fobj.add(scroll);
        fobj.add(sendField);
        fobj.add(sendButton);

        fobj.setLayout(null);
        fobj.setSize(width, height);
        fobj.setVisible(true);
        fobj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        startClientListener();
    }

    public void startClientListener()
    {
        Thread t = new Thread(() -> {
            try
            {
                while ((str1 = bobj1.readLine()) != null)
                {
                    chatArea.append("Client says: " + str1 + "\n");
                }
            }
            catch (Exception eobj)
            {
                chatArea.append("Client disconnected.\n");
            }
        });
          
        t.start();
      
    }
    public void actionPerformed(ActionEvent aobj)
    {
        try
        {
            str2 = sendField.getText();
            pobj.println(str2);

            chatArea.append("Enter message:" + str2 + "\n");
            sendField.setText("");
        }
        catch (Exception eobj)
        {
        
        }
    }
}

class GUIServer
{
    public static void main(String A[]) throws Exception
    {
        ServerSocket ssobj = new ServerSocket(5100);
        System.out.println("Server is waiting at port 5100");

        Socket sobj = ssobj.accept();
        System.out.println("Client request accepted successfully");

        new MarvellousServer("Server Window", 420, 320, sobj);
    }
} 