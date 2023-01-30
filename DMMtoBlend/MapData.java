package DMMtoBlend;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

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
