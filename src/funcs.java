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
/*
 * Nombre: Rene Ventura
 * Carnet: 19554
 * Proyecto: Implementacion de protocolos de XMPP a un chat ubicado en el servidor de XMPP alumchat.fun
 * Fecha de creacion: 12 de agosto del 2022, Guatemala, Guatemala
 * Fecha de ultima modificacion: 15 de agosto del 2022, Guatemala, Guatemala
 * 
 */


// Esta clase implementa todas las funcionalidades de los protocolos, con la liberia SMACK para JAVA
public class funcs {

// Crea una cuenta en alumchat.fun utilizando el protocolo XEP-0077
    public static void create_account(String user, String pass,XMPPConnection con ){
        try{
           
            con.connect();
            AccountManager accountManager= new AccountManager(con);// para la creacion de la cuenta no es necesario agregar la extension @alumcha.fun
            accountManager.createAccount(user, pass);
            con.login(user,pass);
            System.out.println("Usuario Creado");
        }
        catch(Exception e){
            e.printStackTrace();
        }     
        

    }

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

// Se desconecta de una cuenta existente en alumchat.fun utilizando el protocolo XEP-0077
    public static void log_out(XMPPConnection con){
        try{
           
            con.disconnect();
            System.out.println("Se ha desconectado de la sesion\n");
        }
        catch(Exception e){

            e.printStackTrace();
        }
    }

// Se elimina  una cuenta existente en alumchat.fun utilizando el protocolo XEP-0077
    public static void delete_account(String user, String pass, XMPPConnection con){
        try{
            AccountManager accountManager= new AccountManager(con);
            accountManager.deleteAccount();
            System.out.println("Se ha eliminado el usuario "+user+" Con password: "+pass);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

// Muestra la lista de contactos agregados junto con su disponibilidad
    public static void show_users(XMPPConnection con){
        try{
           
           
            Roster roster = con.getRoster();
            Collection<RosterEntry> entries = roster.getEntries();
            Presence presence;
            for(RosterEntry entry : entries) {
            presence = roster.getPresence(entry.getUser());
            System.out.println("Username: "+entry.getUser());
            System.out.println("Status: "+   presence.getType().name());
           
            
            }

        }

        catch(Exception e){
            e.printStackTrace();
        }     
    }

//Muestra la descripcion de un contacto especifico
    public  void getUser_info(String contact, XMPPConnection con){
        try{
        Roster roster = con.getRoster();
        
        System.out.println("ID: "+ contact);
        System.out.println("Status: "+ roster.getPresence(contact));
        }
        catch(Exception e ){
            e.printStackTrace();
        }

    }

//Agrega un contacto a la lista de contactos
    public static void add_friend(XMPPConnection con, String usuario){
        try{

            Roster roster= con.getRoster();
            if (!roster.contains(usuario)) {
                roster.createEntry(usuario,"p",null);
                System.out.println("Eres amigo de "+ usuario);
            } else {
                System.out.println("Contacto ya existente en la libreta de direcciones");
            } 
        }
        catch(Exception e ){
        e.printStackTrace();
        }

    }

//Define la disponibilidad del usuario entre available y unavailable
    public static void set_presence(XMPPConnection con){
        try{
            Scanner red= new Scanner(System.in);
            System.out.println("Escoga su estado\n1. available\n 2. unavailable");
            String pres=red.nextLine(); 
            if(Integer.valueOf(pres)==1){
            Presence presence= new Presence(Presence.Type.available);
            con.sendPacket(presence);
            System.out.println("Estado cambiado");
        }
            else if( Integer.valueOf(pres)==2){
                Presence presence= new Presence(Presence.Type.unavailable);
                con.sendPacket(presence); 
                System.out.println("Estado cambiado");
            }
            else{
                System.out.println("no es valida esa opcion\n");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

// Crea una comunicacion 1-1 entre un contacto deseado 
    public static void chat_someone(XMPPConnection con, String chated){
        try{

            Chat chat= con.getChatManager().createChat(chated, new MessageListener() {// se crea un listener para poder recibir los mensajes en tiempo real
                            
                        
                        
     
    
                @Override
                public void processMessage(Chat chat, Message message) {
                    
                    System.out.println("Received message from "+chated +": " + message.getBody());
                }
            });
            System.out.println("Esta chateando con "+ chated+" si desea salir escriba '666'\n");
            Scanner reader = new Scanner(System.in);
               
            while(con.isConnected()){// mientras se mantenga conectado hara lo siguiente
                   
                while(!reader.nextLine().equals("666")){
                    chat.sendMessage(reader.nextLine());}
                break;
            }

        }catch(Exception e){
            e.printStackTrace();
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

//Se une a un MultiUserChat utilizando el protocolo XEP-0045
    public static void group_chat(XMPPConnection con, String room){

        try{
            MultiUserChat muc= new MultiUserChat(con, room+"@conference.alumchat.fun");// se conecta a un MUC existente con la extension @conference.alumchat.fun
            muc.join(con.getUser());   //Se une al MUC si el MUC tiene libre el join de cualquier usuario
            


            
            muc.addMessageListener(new PacketListener() {//se crea un listener para los mensajes del MUC
             public void processPacket(Packet packet){
                Message message= (Message) packet;
                System.out.println(muc.getRoom()+": "+ message.getBody());
             }   
            });
            System.out.println("para salir del MUC escribe '666'");
            Scanner reader = new Scanner(System.in);
   
            while(con.isConnected()){
                
                while(!reader.nextLine().equals("666")){//mientras se mantenga conectado hara lo siguiente
                    
                   muc.sendMessage(reader.nextLine());}// envia un mensaje al MUC
                break;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

//envia archivos
    public static void send_file(XMPPConnection con, String filename, String username){
        try{
       
            FileTransferManager manager = new FileTransferManager(con);
            OutgoingFileTransfer transfer= manager.createOutgoingFileTransfer(username);
            File file= new File(filename);
            transfer.sendFile(file, "archivo");
            while(!transfer.isDone()){
            System.out.println(transfer.getStatus());
            System.out.println(transfer.getProgress());}
            //Thread.sleep(1000L);
            System.out.println("Success?");
            StreamInitiation si = new StreamInitiation();
            FileTransferRequest request= new FileTransferRequest(manager, si);
         
            while(con.isConnected()){
            IncomingFileTransfer transfer2= request.accept();
            File file2= new File(transfer2.getFileName());
            transfer2.recieveFile(file2);
            System.out.println(transfer2.getFileName());
        }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
