package DMMtoBlend;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Pattern;

public class MapReference {
    static Map<String, GameObj[]> fart;
    //"XU" = (
    ///obj/machinery/atmospherics/components/tank/air,
    ///obj/machinery/light/directional/north,
    ///turf/open/floor/plating,
    ///area/station/engineering/atmos)
    MapReference(String path) {
        if (fart == null){
            fart = new HashMap<>();
        }
        Pattern m = Pattern.compile("\".*?\" = \\(.*?\\)");
        String[] s = m.split(path);
        for (int i = 0; i < s.length; i++) {
            String address = s[0];
            Stack<GameObj> gob = new Stack<>();
            String[] poop = s[i].split("\n");
            for (int j = 0; j < poop.length; j++) {
                GameObj hi = DirectoryHelper.searchTree(poop[j].replaceAll("[,)]",""));
                if (hi != null || hi != DirectoryHelper.root){
                    gob.add(hi);
                }
            }
            GameObj[] e = new GameObj[gob.size()];
            for (int go = 0; go < gob.size(); go++) {
                e[go] = gob.get(go);
            }
            fart.put(address,e);
        }
    }
}
