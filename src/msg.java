
public class msg {
    public String pre;
    public String post;
    public String retBeen;
    public boolean hasData = false;


    public void setMSG(String from, String jumps, String dist, String haveBeen, String msg){
        pre = from;
        post = jumps + "," + dist + "," + haveBeen + "," + msg;
        hasData = true;
    }

    public String getPre(){
        return pre;
    }

    public String getPost(){
        return post;
    }

}
