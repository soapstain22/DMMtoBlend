package DMMtoBlend;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TileHolder {
    private static HashMap<String, GameObj[]> dict;
    static public GameObj mapRefToObject(String s) {
        String[] a = s.split("\n");
        GameObj g = null;
        Pattern p = Pattern.compile("(\\/.*)[,){]|name = \\\"(.*?)\\\"|dir = (.)|icon_state = \\\"(.*?)\\\"");
        for (int i = 0; i < a.length; i++) {
            Matcher m = p.matcher(a[i]);
            if (g == null)
            g = DirectoryHelper.searchTree(m.group("reference")).clone();
            String state = m.group("state");
            String name = m.group("name");
            String dir = m.group("dir");
            g.setDir(dir);
            g.setIcon_state(state);
        }
        //(\/.*)[,){]|name = \"(.*?)\"|dir = (.)|icon_state = \"(.*?)\"
        // only feed it strings that come from /.*?,|/.*\)
        // for example
        // /obj/machinery/door/airlock/research{
        //	req_access_txt = "8"
        //	name = "Toxins Launch Site";
        //	icon_state = "weed"
        //	dir = 2
        //	req_access_txt = "8"
        //	},

        // name = \"(.*?)\"|dir = (.)|icon_state = \"(.*?)\"
        return g;
    }
}
