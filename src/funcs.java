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
import org.jivesoftware.smackx.filetransfer.FileTransferListener;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.jivesoftware.smackx.filetransfer.OutgoingFileTransfer;
import org.jivesoftware.smackx.filetransfer.IncomingFileTransfer;
import org.jivesoftware.smackx.filetransfer.FileTransferRequest;
import org.jivesoftware.smackx.packet.StreamInitiation;
import java.io.File;


// Esta clase implementa todas las funcionalidades de los protocolos, con la liberia SMACK para JAVA
public class funcs {

// Se logea con una cuenta existente en alumchat.fun utilizando el protocolo XEP-0077
    public static void log_in(String user, String pass, XMPPConnection con)
    {
        try{
            
            con.connect();
            con.login(user,pass);
            if(con.isAuthenticated()){ // Valida si se logro conectar con el servidor con las credenciales correctas
            System.out.println(user+" te has conectado a la sesion\n");}
            else{
                System.out.println("Creedenciales incorrectas\n");// sino vuelva a ingresar unas credenciales existentes dentro del servidor
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }  
    }

// Crea una comunicacion 1-1 entre un contacto deseado 
    public static void chat_someone(XMPPConnection con, String[] chated, Object[] nodes, String user, String algoritmo, String emisor,String pass){
        namesJson jsonf= new namesJson();
        if(emisor.equals("y")&& algoritmo.equals("1")){
        try{
            int cont = 0;
            while(cont<nodes.length){
            Chat chat= con.getChatManager().createChat(chated[cont], new MessageListener() {// se crea un listener para poder recibir los mensajes en tiempo real
                            
                
                @Override
                public void processMessage(Chat chat, Message message) {
                    System.out.println("paquete recibido");
                }
            });
            /*System.out.println("Esta chateando con "+ chated+" si desea salir escriba '666'\n");
            Scanner reader = new Scanner(System.in);*/
            
            if(!(user+"@alumchat.fun").equals(chated[cont])){
                chat.sendMessage("flooding " + "," + user+"@alumchat.fun" + "," + chated[cont] + "," + "0,0," + "listado,paquete");
                System.out.println("flooding" + "," + user +"@alumchat.fun"+ "," + chated[cont] + "," + "0,0," + "listado,paquete");
                System.out.println("Solo prints no se envio nada");
            }
            //con.disconnect();
            cont++;
       } }catch(Exception e){
            e.printStackTrace();
        }
    }

    else if(emisor.equals("n") && algoritmo.equals("1")){
        msg saveMSG = new msg();
        System.out.println("Ingrese el nodo a conectarse");
        Scanner scan = new Scanner(System.in);
        String connectTo=scan.nextLine();
        connectTo = connectTo+"alumchat.fun";
        con.disconnect();
        log_in(user, pass, con);
        int cont = 0;
        while (cont<chated.length){
        Chat chat= con.getChatManager().createChat(chated[cont], new MessageListener() {// se crea un listener para poder recibir los mensajes en tiempo real
                            
                
            @Override
            public void processMessage(Chat chat, Message message) {
                String from = "";
                String jumps = "";
                int jumpsINT = 0;
                String dist = "0";
                String haveBeen = "";
                String msg = "";
                System.out.println("paquete recibido");

                String[] reciMsg = message.getBody().split(",");
                from = user;
                jumpsINT = Integer.parseInt(reciMsg[2]) + 1;
                jumps = Integer.toString(jumpsINT);
                haveBeen = reciMsg[3]+";"+user;
                msg = reciMsg[4];
                saveMSG.setMSG(from, jumps, dist, haveBeen, msg);
                
            }
        });

        if(saveMSG.hasData == true){
            try{
            chat.sendMessage(saveMSG.getPre()+","+chated[cont]+","+saveMSG.getPost());
            }catch(Exception e){
                e.printStackTrace();
            }
            cont++;
        }

    }
           // chat.sendMessage(user+","+chated[]);
    }
    }

    public static void special_chat(XMPPConnection con, String chated,String msg){
        try{

            Chat chat= con.getChatManager().createChat(chated, new MessageListener() {// se crea un listener para poder recibir los mensajes en tiempo real
                            
                        
                        
     
    
                @Override
                public void processMessage(Chat chat, Message message) {
                    
                    System.out.println("Received message from "+chated +": " + message.getBody());
                }
            });
            System.out.println("Se envio el mensaje:  "+msg+"\n");
            Scanner reader = new Scanner(System.in);
               
            while(con.isConnected()){// mientras se mantenga conectado hara lo siguiente
                   
                while(!reader.nextLine().equals("666")){
                    chat.sendMessage(msg);}
                break;
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
