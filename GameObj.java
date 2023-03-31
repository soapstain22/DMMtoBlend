import java.util.ArrayList;

public class GameObj {
    GameObj parent = null;

    ArrayList<GameObj> children = new ArrayList<>();
    private String icon = null;
	private String icon_state = null;
    private String path = "";
    private int dir = 0;
	private float lightPower = 0;
	private boolean density = false;
	private final String modelOverride = null;
	private final int wallPress = 0;
    public String getPath() {
        return path;
    }
    final static int NORTH = 0;
    final static int NORTHEAST = 1;
    final static int EAST = 2;
    final static int SOUTHEAST = 3;
    final static int SOUTH = 4;
    final static int SOUTHWEST = 5;
    final static int WEST = 6;
    final static int NORTHWEST = 7;

    TileHolder belongsTo;
    GameObj(String path, int dir) {
        this.dir = dir;
        this.path = path; }
    public GameObj(String path) {
        if (path.equals("root")){
            DirectoryHelper.root = this;
            this.path = "root";
        }
        this.path = path;
    }

    public boolean isDensity() {
        return density;
    }

    public void setDensity(boolean density) {
        this.density = density;
    }

    private GameObj(){
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
        switch (var) {
            case "icon" -> this.setIcon(data);
            case "dir" -> this.setDir(dir);
            case "icon_state" -> this.setIcon_state(data);
            case "density" -> this.setDensity(data.equals("true"));
        }
    }

    @Override
    public GameObj clone(){
        GameObj g = new GameObj();
        g.dir = this.dir;
        g.parent = this.parent;
        g.children = this.children;
        g.path = this.path;
        g.icon = this.icon;
        g.icon_state = this.icon_state;
        g.lightPower = this.lightPower;
        return g;
    }

    @Override
    public String toString() {
        return "|-" +getIcon() + "|-" + getName()+ "|-" +density+ "|-" +lightPower;
    }
}
