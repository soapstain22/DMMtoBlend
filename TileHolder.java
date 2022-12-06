import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class TileHolder {
    static ArrayList<TileHolder> dictionary = new ArrayList<>();
    String position;
    Stack<GameObj> g = new Stack<>();
    void tileAdd(){
        dictionary.add(this);
    }
    void add(GameObj e){
        g.push(e);
    }
    TileHolder(String pos){
        position = pos;
    }
}
