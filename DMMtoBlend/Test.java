package DMMtoBlend;
import java.io.IOException;
public class Test {
    public static void main(String[] args) throws IOException {
        Main.gameRoot = "C:\\Users\\Max\\Documents\\GS13 2\\Gas-Station-13-Deluxe";
        Main.DMEPath = "C:\\Users\\Max\\Documents\\GS13 2\\Gas-Station-13-Deluxe\\tgstation.dme";
        Main.mapPath = "C:\\Users\\Max\\Documents\\GS13 2\\Gas-Station-13-Deluxe\\_maps\\map_files\\MetaStation\\MetaStation.dmm";
        Main.exportRoot = "C:\\Users\\Max\\Documents\\3d\\renderSS13\\javaDMM\\delta\\EXPORT";
        Main.generateImages = true;
        Main.main(args);
    }
}