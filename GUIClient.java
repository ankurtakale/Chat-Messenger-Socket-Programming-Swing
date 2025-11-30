import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

class MarvellousLogin implements ActionListener
{   
    JFrame fobj;
    JButton sendButton;
    JTextField inputField;
    JTextArea chatArea;

    Socket sobj;
    PrintStream pobj;
    BufferedReader bobj1;

    public MarvellousLogin(String title, int width, int height, Socket sobj) throws Exception
    {
        this.sobj = sobj;

        pobj = new PrintStream(sobj.getOutputStream());
        bobj1 = new BufferedReader(new InputStreamReader(sobj.getInputStream()));

        fobj = new JFrame(title);
        fobj.getContentPane();
   

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(chatArea);
        scroll.setBounds(20, 20, 350, 180);
         
        inputField = new JTextField();
        inputField.setBounds(20, 220, 220, 30);

        sendButton = new JButton("SEND");
        sendButton.setBounds(260, 220, 100, 30);
        sendButton.addActionListener(this);

        fobj.add(scroll);
        fobj.add(inputField);
        fobj.add(sendButton);

        fobj.setLayout(null);
        fobj.setSize(width, height);
        fobj.setVisible(true);
        fobj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent aobj)
    {
        try
        {
            String str1 = inputField.getText();

            if(str1.equals("end"))
            {
                chatArea.append("Connection Stop\n");
                sobj.close();
                return;
            }
            pobj.println(str1);
            chatArea.append("Enter message :" + str1 + "\n");

          
            String str2 = bobj1.readLine();

            chatArea.append("Server says : " + str2 + "\n");

            inputField.setText("");
        }
        catch(Exception e)
        {
            chatArea.append("Server disconnected.\n");
        }
    }
}


class GUIClient
{
   public static void main(String A[]) throws Exception
   {
        Socket sobj = new Socket("localhost", 5100);
        System.out.println("Client connected to server successfully");

        new MarvellousLogin("Client Window", 400, 320, sobj);
   }
}