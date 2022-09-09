import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collection;
import java.util.Scanner;
import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
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
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;


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

// Crea una comunicacion peer-peer entre un contacto deseado 
    public static void chat_someone(XMPPConnection con, String[] chated, Object[] nodes, String user, String algoritmo, String emisor,String pass, String receptor){
        namesJson jsonf= new namesJson();
        String nameFile = "names-demo.json";
        JSONParser parser = new JSONParser();
        /*Identifico index de la matriz de nodos para obtener los vecinos*/
        String[] names_  = jsonf.getName(); /*Llamo nombres*/
        Object[][] nodeMatrix  = jsonf.getNodes();/*Llamo matriz de nodos*/
        Object[] nearby = {};
        for(int i =0; i<names_.length;i++){
            if(names_[i].equals(user+"@alumchat.fun")){ /*Toma el index del usuario recurrente*/
                nearby = nodeMatrix[i]; /*Almacena los vecinos por su nombre de nodo "A", "B", etc*/
            }
        }
    if(emisor.equals("y")&& algoritmo.equals("1")){
        try{
            int cont = 0;
            String [] temp= new String[nodes.length];
            namesJson jsonnn= new namesJson();
            String[] ids= jsonnn.getID();
            for(int i=0; i<nodes.length;i++)
            {
                nodes[i]= nodes[i].toString().replace("\"", "");
            }
            System.out.println(Arrays.deepToString(nodes));
            for(int i=0; i<chated.length;i++){
                for(int j=0; j<nodes.length;j++){
                if(ids[i].contains(nodes[j].toString())){
                    temp[j]=chated[i].toString();
                }}
            }
            System.out.println(Arrays.deepToString(temp));
        
            while(cont<nodes.length){
            Chat chat= con.getChatManager().createChat(temp[cont], new MessageListener() {// se crea un listener para poder recibir los mensajes en tiempo real
                            
                
                @Override
                public void processMessage(Chat chat, Message message) {
                    System.out.println("paquete recibido");
                }
            });
            /*System.out.println("Esta chateando con "+ chated+" si desea salir escriba '666'\n");
            Scanner reader = new Scanner(System.in);*/
            
            if(!(user+"@alumchat.fun").equals(chated[cont])){
                chat.sendMessage("flooding " + "," + user+"@alumchat.fun" + "," + temp[cont] + "," + "0,0," + "listado,paquete");
                System.out.println("flooding" + "," + user +"@alumchat.fun"+ "," + temp[cont] + "," + "0,0," + "listado,paquete");
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
        String [] temp= new String[chated.length];
        connectTo = connectTo+"@alumchat.fun";
        temp[0]=connectTo;
        for(int i =1; i<chated.length;i++){
            if(chated[i]!=connectTo){
                temp[i]=chated[i].toString();
                
            }
        }
        
       // con.disconnect();
       //log_in(user, pass, con);
        int cont = 0;
        while (cont<temp.length){
            if(!temp[cont].contains(user)){
                //System.out.println(temp[cont]);;
            Chat chat2= con.getChatManager().createChat(temp[cont], new MessageListener() {// se crea un listener para poder recibir los mensajes en tiempo real
                            
                
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
                
                from = reciMsg[1];
                jumpsINT = Integer.parseInt(reciMsg[3]) + 1;
                jumps = Integer.toString(jumpsINT);
                haveBeen += reciMsg[5]+";"+user+";";
                msg = reciMsg[6];
                saveMSG.setMSG(from, jumps, dist, haveBeen, msg);
                System.out.println(saveMSG.getPost());
                
            }
        });

        if(saveMSG.hasData == true){
            try{
            if(!saveMSG.getBin().contains(temp[cont]) && !temp[cont].contains(saveMSG.getPre()) && temp[cont]!= user+"@alumchat.fun" ){    
                chat2.sendMessage(saveMSG.getPre()+","+temp[cont]+","+saveMSG.getPost());
                System.out.println(temp[cont]);
            }
          
            }catch(Exception e){
                e.printStackTrace();
            }
            cont++;
        }
    }
    else{
        cont++;
    }
    }
           // chat.sendMessage(user+","+chated[]);
    }
 if(emisor.equals("y")&& algoritmo.equals("2")){
    try{
        int cont = 0;
        dvp dvpr= new dvp();
        String [] temp= new String[nodes.length];
        namesJson jsonnn= new namesJson();
        String[] ids= jsonnn.getID();
        String vp;
        for(int i=0; i<nodes.length;i++)
        {
            nodes[i]= nodes[i].toString().replace("\"", "");
        }
        System.out.println(Arrays.deepToString(nodes));
        for(int i=0; i<chated.length;i++){
            for(int j=0; j<nodes.length;j++){
            if(ids[i].contains(nodes[j].toString())){
                temp[j]=chated[i].toString();
            }}
        }
        System.out.println(Arrays.deepToString(temp));
    
        while(cont<nodes.length){
        Chat chat= con.getChatManager().createChat(temp[cont], new MessageListener() {// se crea un listener para poder recibir los mensajes en tiempo real
                        
            
            @Override
            public void processMessage(Chat chat, Message message) {
                System.out.println("paquete recibido");
            }
        });
        /*System.out.println("Esta chateando con "+ chated+" si desea salir escriba '666'\n");
        Scanner reader = new Scanner(System.in);*/
        
        if(!(user+"@alumchat.fun").equals(chated[cont])){
            System.out.println(temp.toString());
            vp= dvpr.bellmeameesta(temp, temp.length+1, temp.length, 1, user);
            chat.sendMessage("dvp" + "|" + user+"@alumchat.fun" + "|" + temp[cont] + "|" +vp + "|"+Arrays.deepToString(nodes)+"|"+"paquete");
            System.out.println("dvp" + "," + user +"@alumchat.fun"+ "," + temp[cont] + "," + "0,0," + "listado,paquete");
            System.out.println("Solo prints no se envio nada");
        }
        //con.disconnect();
        cont++;
   } }catch(Exception e){
        e.printStackTrace();
    }
}

else if(emisor.equals("n") && algoritmo.equals("2")){
    msg saveMSG = new msg();
   
    System.out.println("Ingrese el nodo a conectarse");
    Scanner scan = new Scanner(System.in);
    String connectTo=scan.nextLine();
    String [] temp= new String[chated.length];
    connectTo = connectTo+"@alumchat.fun";
    temp[0]=connectTo;
    for(int i =1; i<chated.length;i++){
        if(chated[i]!=connectTo){
            temp[i]=chated[i].toString();
            
        }
    }
    
   // con.disconnect();
   //log_in(user, pass, con);
    int cont = 0;
    while (cont<temp.length){
        if(!temp[cont].contains(user)){
            //System.out.println(temp[cont]);;
        Chat chat2= con.getChatManager().createChat(temp[cont], new MessageListener() {// se crea un listener para poder recibir los mensajes en tiempo real
                        
            
        @Override
        public void processMessage(Chat chat, Message message) {
            String from = "";
            String jumps = "";
            int jumpsINT = 0;
            String dist = "";
            String haveBeen = "";
            String msg = "";
            System.out.println("paquete recibido");

            String[] reciMsg = message.getBody().split("|");
            
            from = reciMsg[1];
            jumpsINT = Integer.parseInt(reciMsg[2]) + 1;
            jumps = Integer.toString(jumpsINT);
            haveBeen += reciMsg[4]+";"+user+";";
            msg = reciMsg[5];
            dist= reciMsg[3];
            saveMSG.setMSG(from, jumps, dist, haveBeen, msg);
            System.out.println(saveMSG.getPost());
            
        }
    });

    if(saveMSG.hasData == true){
        try{
        if(!saveMSG.getBin().contains(temp[cont]) && !temp[cont].contains(saveMSG.getPre()) && temp[cont]!= user+"@alumchat.fun" ){    
            chat2.sendMessage(saveMSG.getPre()+","+temp[cont]+","+saveMSG.getPost());
            System.out.println(temp[cont]);
        }
      
        }catch(Exception e){
            e.printStackTrace();
        }
        cont++;
    }
}
else{
    cont++;
}
}
       // chat.sendMessage(user+","+chated[]);
}
       
    if(emisor.equals("y") && algoritmo.equals("3")){
        msg saveMSG = new msg();
                try{
                    int cont = 0;
                    while (cont<nearby.length){ /*Recorre los vecinos obtenidos*/
                        /*Llama nuevamente el file de nombres por problematica de flexibilidad*/
                        Object obj = parser.parse(new FileReader(nameFile));
                        JSONObject jsonObject = (JSONObject)obj;
                        jsonObject= (JSONObject) jsonObject.get("config"); /*Accede a config*/
                        String a = nearby[cont].toString(); 
                        a = a.substring(1,a.length()-1);
                        Object finalUser = jsonObject.get(a); /*Accede al usuario @alumchat de cada vecino*/
                        String neighbor = finalUser.toString();
                        
                        
                        
                        /*Realiza conexion con cada vecino segun su @alumchat*/
                        
                        Chat chat= con.getChatManager().createChat(neighbor, new MessageListener() {// se crea un listener para poder recibir los mensajes en tiempo real
                                        
                            
                            @Override
                            public void processMessage(Chat chat, Message message) {
                                System.out.println("paquete recibido");
                            }
                        });
                        /*System.out.println("Esta chateando con "+ chated+" si desea salir escriba '666'\n");
                        Scanner reader = new Scanner(System.in);*/
                        
                       /*Routing Protocol - Emisor - Nodo destino inmediato - 
                       Saltos - Distancia - Nodos en los que ha estado - Mensaje -  Receptor Final*/
                       
                        chat.sendMessage("Link State Routing " + "," + user+ "," + neighbor + "," + "0,0," + user + ",RoutingExploration" + "," + receptor);
                        System.out.println("Link State Routing" + "," + user + "," + neighbor+ "," + "0,0," + user + ",RoutingExploration" + "," + receptor);
                        
                        cont++;
                        
                    }
                    cont = 0;
                    while (cont<1){ /*Recorre los vecinos obtenidos*/
                       
                        /*Realiza conexion con cada vecino segun su @alumchat*/
                        Chat chatToReceptor= con.getChatManager().createChat(receptor, new MessageListener() {// se crea un listener para poder recibir los mensajes en tiempo real

                        @Override
                        public void processMessage(Chat chat, Message message) {
                            String from = "";
                            String receptor = "";
                            String jumps = "";
                            int jumpsINT = 0;
                            String dist = "";
                            String haveBeen = "";
                            String msg = "";
                            
                            /*Routing Protocol - Emisor - Nodo destino inmediato - 
                            Saltos - Distancia - Nodos en los que ha estado - Mensaje -  Receptor Final*/
                            String[] reciMsg = message.getBody().split(",");
                            
                            from = reciMsg[1];
                            jumpsINT = Integer.parseInt(reciMsg[3]) + 1;
                            jumps = Integer.toString(jumpsINT);
                            dist = jumps;
                            haveBeen += reciMsg[5]+";"+user+";";
                            msg = reciMsg[6];
                            receptor = reciMsg[7];
                            saveMSG.setReceptor(receptor);
                            saveMSG.setMSG(from, jumps, dist, haveBeen, msg);
                            System.out.println("paquete recibido desde destino final :D");                             
                            System.out.println(saveMSG.getPost());
                                            
                        }
                        });
                            /*System.out.println("Esta chateando con "+ chated+" si desea salir escriba '666'\n");
                            Scanner reader = new Scanner(System.in);*/
                            
                           /*Routing Protocol - Emisor - Nodo destino inmediato - 
                           Saltos - Distancia - Nodos en los que ha estado - Mensaje -  Receptor Final*/
                        if(saveMSG.hasData == true){
                            chatToReceptor.sendMessage("Link State Routing: Final Message" +","+saveMSG.getPre()+","+receptor+","+saveMSG.getPost()+receptor);
                            System.out.println("paquete recibido en destino receptor --> "+saveMSG.getPost());
                            
                            cont++; 
                        }
                            //con.disconnect();   
                            
                    }   
                }catch(Exception e){
                    e.printStackTrace();
                }


    } 
    else if(emisor.equals("n") && algoritmo.equals("3")){
        msg saveMSG = new msg();

                try{
                    int cont = 0;
                    System.out.println(nearby.length);
                    while (cont<nearby.length){
                        Object obj = parser.parse(new FileReader(nameFile));
                        JSONObject jsonObject = (JSONObject)obj;
                        jsonObject= (JSONObject) jsonObject.get("config"); /*Accede a config*/
                        String a = nearby[cont].toString(); 
                        a = a.substring(1,a.length()-1);
                        Object finalUser = jsonObject.get(a); /*Accede al usuario @alumchat de cada vecino*/
                        String neighbor = finalUser.toString();

                        /*Realiza conexion con cada vecino segun su @alumchat*/
                        Chat chat2= con.getChatManager().createChat(neighbor, new MessageListener() {// se crea un listener para poder recibir los mensajes en tiempo real
                        
                            
                        @Override
                        public void processMessage(Chat chat, Message message) {
                            String from = "";
                            String receptor = "";
                            String jumps = "";
                            int jumpsINT = 0;
                            String dist = "";
                            String haveBeen = "";
                            String msg = "";
                            System.out.println("paquete recibido");
                            /*Routing Protocol - Emisor - Nodo destino inmediato - 
                            Saltos - Distancia - Nodos en los que ha estado - Mensaje -  Receptor Final*/
                            String[] reciMsg = message.getBody().split(",");
                            
                            from = reciMsg[1];
                            jumpsINT = Integer.parseInt(reciMsg[3]) + 1;
                            jumps = Integer.toString(jumpsINT);
                            dist = jumps;
                            haveBeen += reciMsg[5]+";"+user+";";
                            msg = reciMsg[6];
                            receptor = reciMsg[7];
                            saveMSG.setReceptor(receptor);
                            saveMSG.setMSG(from, jumps, dist, haveBeen, msg);
                            System.out.println(saveMSG.getPost()+receptor);
                                         
                        }
                        });
                        
                        if(saveMSG.getReceptor().equals(user+"@alumchat.fun")){ /*Identifica la llegada del routing discoverer,
                            
                            se espera que identifique el shortest path y lo envie al nodo emisor*/
                            
                            try{
                                Chat chatToSender= con.getChatManager().createChat((saveMSG.getPre()+"@alumchat.fun"), new MessageListener() {
                                    @Override
                                    public void processMessage(Chat chat, Message message) {
                                        System.out.println("paquete enviado");
                                    }
                                });
                                chatToSender.sendMessage("Link State Routing: Ruta Certificada" +","+saveMSG.getPre()+","+neighbor+","+saveMSG.getPost()+receptor);
                                System.out.println("paquete recibido en destino emisor --> "+saveMSG.getPost());
                                break;
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                        else if(saveMSG.hasData == true){
                            try{
                                System.out.println(user);
                                
                                /*Routing Protocol - Emisor - Nodo destino inmediato - 
                                Saltos - Distancia - Nodos en los que ha estado - Mensaje -  Receptor Final*/
                                neighbor = neighbor.split("@")[0];
                                if(!saveMSG.getBin().contains(neighbor) && neighbor!= (saveMSG.getPre()+"@alumchat.fun") && neighbor!= (user+"@alumchat.fun")){   
                                    chat2.sendMessage("Link State Routing" +","+saveMSG.getPre()+","+neighbor+","+saveMSG.getPost()+","+saveMSG.getReceptor());
                                    System.out.println("Link State Routing" +","+saveMSG.getPre()+","+neighbor+","+saveMSG.getPost()+","+saveMSG.getReceptor());
                                }
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                            cont++;
                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
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
