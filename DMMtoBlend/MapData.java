package DMMtoBlend;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MapData {
    String pure;
    String rawDefines;

    MapData(String file) throws FileNotFoundException {
        File f = new File(file);
        Scanner s = new Scanner(f);
        s.useDelimiter("//MAP CONVERTED BY dmm2tgm.py THIS HEADER COMMENT PREVENTS RECONVERSION, DO NOT REMOVE\n");
        String pure = s.next();
        rawDefines = pure.split("")[0];
    }
}
