package DMMtoBlend;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
public class Main {
    //i refuse to change my filepath. rip gs13
    public static String gameRoot = "C:\\Users\\Max\\Desktop\\GS13 Revamp\\GS13-main";
    public static String DMEPath = "C:\\Users\\Max\\Desktop\\GS13 Revamp\\GS13-main\\tgstation.dme";
    public static String mapPath = "C:\\Users\\Max\\Desktop\\GS13 Revamp\\GS13-main\\_maps\\map_files\\Deltastation\\DeltaStation2.dmm";
    public static String exportRoot = "C:\\Users\\Max\\Documents\\3d\\renderSS13\\javaDMM\\jabba\\icon";
    public static boolean generateImages = true;
    public static void main(String[] args) throws IOException {
        //gameRoot = args[0];
        //DMEPath = args[1];
        //mapPath = args[2];
        //exportRoot = args[3];
        //ok so it goes like this
        try {
            loadFile(mapPath);
            //System.out.printf ("%-15s "+mapPath+ "\n", "loaded");
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 1; i < DirectoryHelper.validPaths.size()-1; i++) {
            String s = DirectoryHelper.validPaths.get(i);
            s = s.replaceAll("\\\\","/");

            if (Main.generateImages) {
                GameObj gameObj = DirectoryHelper.searchTree(s);
                DirectoryHelper.objGen(gameObj);
            }
        }
        MapData m = new MapData(mapPath);
        //MapPlacementProcessor p = new MapPlacementProcessor(mapPath);
    }
    static void loadFile(String file) throws IOException {
        //System.out.printf ("%-15s "+file+ "\n", "loading");
        File f = new File(file);
        Scanner s = new Scanner(f);
        s.useDelimiter("\\(1,1,1\\) = \\{\"");
        String defined = s.next();
        String[] working = defined.split("\n");
        String cd = "";
        for (int i = 0; i < working.length; i++) {
         //   System.out.printf ("%-15s "+working[i]+ "\n", "cleaning");
            //working[i] = working[i].replaceAll("\\\\","/");
            // FIXME: 12/2/2022 what te shit
            if (working[i].matches("(\\/.*?.[,)])")){
                working[i] = working[i].replaceAll(",","");
                working[i] = working[i].replaceAll("\\)","");
                DirectoryHelper.initDirectory(working[i]);
                DirectoryHelper.generateObjNodeTree(working[i]);
                //tileholder goes to maprefToObj
            }
        }
        FakeCompiler c = new FakeCompiler();
        c.compile();
        TileHolder map = new TileHolder(mapPath);
    }
}
