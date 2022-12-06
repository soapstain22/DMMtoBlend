import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FakeCompiler {

    File f;
    public void compile() throws FileNotFoundException {
        System.out.println("Compiling...");
        f = new File(Main.DMEPath);
        Scanner s = new Scanner(f);
        s.useDelimiter("#include ");
        s.next();
        s.next();
        while (s.hasNext()){
            String ew = s.next();
            ew = ew.replaceAll("\r|\n|\"","");
            System.out.printf ("%-15s "+ew+ "\n", "compile");
            if (ew.endsWith(".dm")){
                loadFile(ew);
            }
        }
    }
    public void loadFile(String in) throws FileNotFoundException {
        in = in.replaceAll("\r|\n|\"", "");
        in = in.replaceAll("END_INCLUDE", "");
        in = Main.gameRoot + "\\" + (in);
        GameObj reference = new GameObj("temp");
        File f = new File(in);
        Scanner s = new Scanner(f);
        boolean usable = false;
        while (s.hasNext()) {
            String h;
            String k = s.next();
            if (k.split("//").length > 0) {
                h = k.split("//")[0];
            } else
                h = k;
            s.useDelimiter("\n");
            if (dearGod(h) == 1){
                GameObj peenis = DirectoryHelper.searchTree(h);
                if(peenis != null){
                    //System.out.printf ("%-15s "+h + "\n", "usable");
                    reference = peenis;
                    usable = true;

                }else {
                    usable= false;
                }

                //ok so this is where the thing that sets the data in the reference, like dir = h if h has dir potential.
            }
            if (dearGod(h) == 2){
                usable= false;
            }
            if (usable && isUsableData(h)){
                //System.out.printf ("%-15s "+h + "\n", "eatparams");
                reference.eatParams(h);
            }
        }
    }
    private int dearGod(String h){
        h=h.replaceAll("\\(",")");
        if (!h.matches("\t.*")) {
            if (!h.startsWith("#")) {
                if (!h.startsWith("//")) {
                    if (!h.startsWith("  *")) {
                        if (h.startsWith("/")) {
                            if (!h.startsWith("/*")) {
                                h = h.replaceAll(" ", "");
                                if (h.contains(")") | h.contains("/proc/")) {
                                    return 2;
                                }
                                return 1;
                            }
                        }
                    }
                }
            }
        }
        return 0;
    }
    private boolean isUsableData(String h) {
        //System.out.printf ("%-15s "+h+ "\n", "isdata");
        h = h.replaceAll("\t", "");
        if (h.startsWith("name =")) {
            return true;
        }
        if (h.startsWith("dir =")) {
            return true;
        }
        if (h.startsWith("icon =")) {
            return true;
        }
        if (h.startsWith("icon_state =")) {
            return true;
        }
        return false;
    }
}
