import java.io.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;
public class  namesJson 
{
    String[] name, id,nodes;
    
    public String[] getID() {
        JSONParser parser = new JSONParser();
        String[] arr= null;
        
        try {
           Object obj = parser.parse(new FileReader("names-demo.json"));
           JSONObject jsonObject = (JSONObject)obj;
           String a= jsonObject.get("config").toString();
           a= a.substring(1,a.length()-1);
           ArrayList<String> arrayList = new ArrayList<String>();
           
           
           arr= a.split(",");
           for (int i = 0; i< arr.length; i++)
           {  
               arrayList.add(arr[i]);  
           }  
           
           id= new String[arrayList.size()];
           for (int i =0; i< arrayList.size();i++){
                
                id[i]=arrayList.get(i).split(":")[0];
           }
           for(int ia=0;ia<id.length;ia++){
            id[ia]= id[ia].substring(1,id[ia].length()-1);
        }
           
        } catch(Exception e) {
           e.printStackTrace();
        }
        return id;
     }

     public String[] getName() {
        JSONParser parser = new JSONParser();
        String[] arr= null;
        
        try {
           Object obj = parser.parse(new FileReader("names-demo.json"));
           JSONObject jsonObject = (JSONObject)obj;
           String a= jsonObject.get("config").toString();
           a= a.substring(1,a.length()-1);
           ArrayList<String> arrayList = new ArrayList<String>();
           
           
           arr= a.split(",");
           for (int i = 0; i< arr.length; i++)
           {  
               arrayList.add(arr[i]);  
           }  
           name= new String[arrayList.size()];
           
           for (int i =0; i< arrayList.size();i++){
                name[i]=arrayList.get(i).split(":")[1];
                
           }
           for(int ia=0;ia<arrayList.size();ia++){
            name[ia]= name[ia].substring(1,name[ia].length()-1);
        }
           
        } catch(Exception e) {
           e.printStackTrace();
        }
        return name;
     }
     public Object[][] getNodes(){
        String[] ids= {"A","B","C","D","E","F","G"};
        String[] temp= new String[ids.length];
        Object[][] nodes=new Object[ids.length][ids.length];
        JSONParser parser = new JSONParser();
        JSONObject a = null;
        try{
        Object obj = parser.parse(new FileReader("topo-demo.json"));
        JSONObject jsonObject = (JSONObject)obj;
        JSONArray f= new JSONArray();
        
        a= (JSONObject) jsonObject.get("config");
        for(int i=0;i<ids.length;i++){
            
            f= (JSONArray)a.get(ids[i]);
            temp[i]=f.toString();
        }
        for(int i=0; i<temp.length;i++){
            temp[i]= temp[i].substring(1,temp[i].length()-1);
        }
        for(int i=0; i<temp.length;i++){
            nodes[i]=temp[i].split(",");
        }
         
        System.out.println();
        }catch(Exception e){
            e.printStackTrace();
        }
        return nodes;
     }

     public String getSingleNode(String key){
        JSONParser parser = new JSONParser();
        JSONObject a = null;
        key = key.replace("\"", "");
        try{
        Object obj = parser.parse(new FileReader("topo-demo.json"));
        JSONObject jsonObject = (JSONObject)obj;
        a= (JSONObject) jsonObject.get("config");
        
        }catch(Exception e){
            e.printStackTrace();
        }
        return a.get(key).toString();
     }

 }
