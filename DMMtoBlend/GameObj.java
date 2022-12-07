package DMMtoBlend;
import java.util.ArrayList;
public class GameObj {
    GameObj parent = null;

    ArrayList<GameObj> children = new ArrayList<>();
    private String icon = null;
    private String icon_state = null;
    private String path = "";
    private int dir = 0;

    public String getPath() {
        return path;
    }

    TileHolder belongsTo;
    GameObj(String path, int dir) {
        this.dir = dir;
        this.path = path; }
    GameObj(String path) {
        if (path.equals("root")){
            DirectoryHelper.root = this;
            this.path = "root";
        }
        this.path = path;
    }

    public GameObj getChildren(int i) {
        return children.get(i);
    }
    public void addChildren(GameObj g) {
        g.parent = this;
        if (!this.hasChild(g)){
            children.add(g);
        }

    }
    public boolean hasParent() {
        return parent != null;
    }
    public boolean hasChild() {
        return children.size() != 0;
    }
    public boolean hasChild(GameObj g) {
        for (GameObj child : this.children) {
            if (child.path.equals(g.path)) {
                return true;
            }
        }
        return false;
    }public GameObj containsParent(String s) {
        for (GameObj child : children) {
            if (child.path.equals(s)) {
                return child;
            }
        }
        return null;
    }
    public String getName(){
        String[] nam = path.split("/");
        return nam[nam.length-1];
    }

    public void setIcon(String icon) {
        //System.out.printf ("%-15s "+icon+ "\n", "set");
        this.icon = icon;
    }
    public void setDensity(String icon) {
        this.icon = icon;
    }
    public void setDir(int dir) {
        this.dir = dir;
    }
    public void setDir(String dir) {
        switch (dir){
            case "NORTH": this.dir = 0;
                break;
        }
    }

    public void setIcon_state(String icon_state) {
        icon_state = icon_state.replaceAll("\"","");
        this.icon_state = icon_state;
    }

    public String getIcon() {
        if (icon == null){
            if (hasParent()){
                return this.parent.getIcon();

            }
        }
        return icon;
    }

    public String getIcon_state() {
        return icon_state;
    }

    public void eatParams(String s){
        String[] poop = s.split("=");
        String var = poop[0].replaceAll("\t| ","");
        String data = poop[1].replaceAll("'|\"'","").substring(1);
        switch (var){
            case "icon": this.setIcon(data);
            break;
            case "dir": this.setDir(dir);
            break;
            case "icon_state": this.setIcon_state(data);
            break;
        }
    }
}