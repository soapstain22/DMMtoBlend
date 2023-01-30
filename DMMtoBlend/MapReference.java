package DMMtoBlend;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class MapReference {
    //"XU" = (
    ///obj/machinery/atmospherics/components/tank/air,
    ///obj/machinery/light/directional/north,
    ///turf/open/floor/plating,
    ///area/station/engineering/atmos)
    MapReference(String path) {
        Pattern m = Pattern.compile("\".*?\" = \\(.*?\\)");
        String[] s = m.split(path);
        for (int i = 0; i < s.length; i++) {
            //
        }
    }
}
