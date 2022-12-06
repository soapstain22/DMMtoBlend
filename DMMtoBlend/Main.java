package DMMtoBlend;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
public class Main {
    public static String gameRoot = "C:\\Users\\Max\\Desktop\\GS13 Revamp\\GS13-main";
    public static String DMEPath = "C:\\Users\\Max\\Desktop\\GS13 Revamp\\GS13-main\\tgstation.dme";
    public static String mapPath = "C:\\Users\\Max\\Desktop\\GS13 Revamp\\GS13-main\\_maps\\map_files\\Deltastation\\DeltaStation2.dmm";
    public static String exportRoot = "C:\\Users\\Max\\Documents\\3d\\renderSS13\\javaDMM\\jabba\\icon";
    public static void main(String[] args) throws IOException {
        DirectoryHelper.initDirectory(exportRoot);
        try {
            loadFile(mapPath);
            //System.out.printf ("%-15s "+mapPath+ "\n", "loaded");
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 1; i < DirectoryHelper.validPaths.size()-1; i++) {
            String s = DirectoryHelper.validPaths.get(i);
            s = s.replaceAll("\\\\","/");
            GameObj gameObj = DirectoryHelper.searchTree(s);
            DirectoryHelper.objGen(gameObj);

        }
    }
    static void loadFile(String file) throws IOException {
        //System.out.printf ("%-15s "+file+ "\n", "loading");
        File f = new File(file);
        Scanner s = new Scanner(f);
        s.useDelimiter("\\(1,1,1\\) = \\{\"");
        String defined = s.next();
        String map = s.next();
        String[] working = defined.split("\n");
        String cd = "";
        TileHolder t = new TileHolder(".");
        for (int i = 0; i < working.length; i++) {
         //   System.out.printf ("%-15s "+working[i]+ "\n", "cleaning");
            working[i] = working[i].replaceAll("\\\\","/");
            // FIXME: 12/2/2022 what te shit

            if (working[i].matches("(\\/.*?.[,)])")){
                working[i] = working[i].replaceAll(",","");
                working[i] = working[i].replaceAll("\\)","");
                t.add(new GameObj(working[i]));
                DirectoryHelper.initDirectory(working[i]);
                //System.out.println(working[i]);
                DirectoryHelper.generateObjNodeTree(working[i]);
            }
            if (working[i].matches("\"...\" = \\(")){
                cd = working[i].split("\"")[1];
                t.tileAdd();
                t = new TileHolder(working[i]);
            }
        }
        FakeCompiler c = new FakeCompiler();
        c.compile();
    }
}
