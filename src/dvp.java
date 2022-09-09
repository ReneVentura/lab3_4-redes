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


public class dvp{
    static int graph[][];
    static int via[][];
    static int rt[][];
    static int v;
    static int e;
    static int MAX_VALUE=999;
    Scanner br = new Scanner(System.in);
    static namesJson jsonf= new namesJson();
    static String[] id= jsonf.getID();
    static String[] names= jsonf.getName();
    static Object[][] nodes= jsonf.getNodes();
    static int vertex= nodes.length; 
public static String bellmeameesta( String[] temp, int v, int e, int cost, String name){
    
  int s=0; 
  int d=0;
  graph = new int[v][v];
  via = new int[v][v];
  rt = new int[v][v];
  for(int i = 0; i < v; i++)
   for(int j = 0; j < v; j++)
   {
    if(i == j)
     graph[i][j] = 0;
    else
     graph[i][j] = 9999;
   }
  int f=0;
  for(int i = 0; i < e; i++)
  {/* */

    f=i+1;
    if(f<e){
    s=i;
    d=i+1;
    }
    else{
      s=i;
      d=e-1;
    }
    

    graph[s][d] = cost;
    graph[d][s] = cost; 
  



  }
  String res;
  
  res=dvr_calc_disp("DVP : ");
  return res;



}
  
  
 
 
 static String dvr_calc_disp(String message)
 {
  String s="";
  System.out.println();
  init_tables(vertex);
  update_tables(vertex);
  System.out.println(message);
  s+=message+"\n";
  s+=print_tables(vertex)+"\n";
  s+=vertex+"\n";
  return s;
 }
 
 static void update_table(int source)
 {
  for(int i = 0; i < v; i++)
  {
   if(graph[source][i] != 9999)
   {
    int dist = graph[source][i];
    for(int j = 0; j < v; j++)
    {
     int inter_dist = rt[i][j];
     if(via[i][j] == source)
      inter_dist = 9999;
     if(dist + inter_dist < rt[source][j])
     {
      rt[source][j] = dist + inter_dist;
      via[source][j] = i;
     }
    }
   }
  }
 }
 
 static void update_tables(int v)
 {
  int k = 0;
  for(int i = 0; i < 4*v; i++)
  {
   update_table(k);
   k++;
   if(k == v)
    k = 0;
  }
 }
 
 static void init_tables(int v)
 {
  for(int i = 0; i < v; i++)
  {
   for(int j = 0; j < v; j++)
   {
    if(i == j)
    {
     rt[i][j] = 0;
     via[i][j] = i;
    }
    else
    {
     rt[i][j] = 9999;
     via[i][j] = 100;
    }
   }
  }
 }
 
 static String print_tables(int v)
 {
  String t="";
  for(int i = 0; i < v; i++)
  {
   for(int j = 0; j < v; j++)
   {
    t+="Dist: " + rt[i][j] + "    ";
   }
   t+="\n";
   System.out.println();
  }
  return t;
 }
 

    public  static void main(String[] args) throws Exception {
        
        

      //  System.out.println(Arrays.deepToString(nodes[0]));  
        //System.out.println(Arrays.deepToString(id));
        //System.out.println(names[2]);
        XMPPConnection con = new XMPPConnection("alumchat.fun");
        String name="oscar123@alumchat.fun"; 
        
        int edges = 0;
        
        for(int i =0; i <  id.length; i++){
          edges+= nodes[i].length;

        }
        System.out.println(edges);
        






    }
}
