import java.util.Map;

public class MapData {
    String rawdefines = "";
    String[] defines;
    String rawmap = "";
    String[] maps;
    static Map<String, GameObj[]> dict;

    public MapData(String mapPath) {
    }

    void processDefine(String s){
        String coord = s.split("\"")[1];

    }
}
