import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.util.Scanner;
import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smackx.GroupChatInvitation;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.jivesoftware.smackx.filetransfer.OutgoingFileTransfer;
import java.io.File;
import java.util.Arrays;



public class Main {
    public static void main(String[] args) throws Exception {
        funcs chat= new funcs();
        String receptor = "undefined";
        namesJson jsonf= new namesJson();
        String[] id= jsonf.getID();
        String[] name= jsonf.getName();
        Object[][] nodes= jsonf.getNodes();
        XMPPConnection con = new XMPPConnection("alumchat.fun");
        Scanner scan = new Scanner(System.in);    
        try{
            System.out.println("Ingrese su usuario: ");
            String user= scan.nextLine();
            System.out.println("Ingrese su password: ");
            String pass= scan.nextLine();
            chat.log_in(user, pass, con);

            System.out.println("Elija el algoritmo: \n1) Flooding\n2) DVR\n3) LSR");
            String algoritmo= scan.nextLine();

            System.out.println("Es emisor(y/n)");
            String emisor = scan.nextLine();
            
            if(emisor.equals("y")&&algoritmo.equals("3")){

                System.out.println("Indique receptor: blabla@alumchat.fun");
                receptor= scan.nextLine();
            }
            System.out.println("Elija su nodo (Ingresa el numero): ");
            int o=0;
            for(int i=0; i<id.length;i++){
                o= i+1;
                System.out.println("No."+ o + " Nodo: "+id[i].toString()+" user "+name[i].toString());
            } 
            int option=Integer.parseInt(scan.nextLine());
            
            chat.chat_someone(con, name, nodes[option-1], user, algoritmo, emisor,pass, receptor);
            
        }
        catch(Exception e){
            e.printStackTrace();
        }     
    
            
        
        
    }
}