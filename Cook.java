import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
public class Cook {
    final String fileName;
    final String[] states;
    final String[] dirs;
    final String[] frames;
    final BufferedImage[][] cache;
    final int length;
    final int width;
    ArrayList<BufferedImage> line;
    Cook(String file,String[] sta,String[] dir, String[] frame,BufferedImage[][] cac,int x, int y){
        fileName = file;
        frames = frame;
        states = sta;
        dirs = dir;
        cache = cac;
        width = x;
        length = y;
    }
    void bake() throws IOException {
        line = new ArrayList<>();
        for (int y = 0; y < cache[0].length; y++) {
            for (int j = 0; j < cache.length; j++) {
                line.add(cache[j][y]);
            }
        }
        String[] fn = poop(states,dirs,frames);
        for (int i = 0; i < fn.length; i++) {
            File outputFile;
            BufferedImage w = line.get(i);
            String work = fileName+"/"+ fn[i] +".png";
            outputFile = new File(work);
            ImageIO.write(w, "png", outputFile);
        }
    }
    String[] poop(String[] sta,String[] dir, String[] frame){
        String[] finished;
        ArrayList<String> list = new ArrayList<>();
        int x = 0;
        for (int i = 0; i < sta.length; i++) {
            for (int j = 0; j < Integer.parseInt(dir[i]); j++) {
                for (int f = 0; f < Integer.parseInt(frame[i]); f++) {
                    x+=1;
                    String hi = sta[i] +" dir"+ j +" frame"+ f;
                    list.add(hi);
                }
            }
        }
        finished = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            finished[i] = list.get(i);
            //System.out.println(list.get(i));
        }
        return finished;
    }
}