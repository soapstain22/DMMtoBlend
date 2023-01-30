package DMMtoBlend;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class DirectoryHelper {
    static ArrayList<String> validPaths = new ArrayList<>();
    static GameObj root = new GameObj("");
    public static void initDirectory(String p) {
        p = p.replace('/', '\\');
        if (!validPaths.contains(p)) {
            validPaths.add(p);
        }else{ //System.out.printf ("%-15s "+ p + "\n", "invalid");;
        }
    }
    public static String pullFileData(String inFile) throws IOException {
        //Returns only the icon's metadata.
        //System.out.printf ("%-15s "+ inFile + "\n", "pull md");
        File f = new File(inFile);
        ImageInputStream iis = ImageIO.createImageInputStream(f);
       // System.out.printf ("%-15s "+ inFile + "\n", "read");
        Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
        if (readers.hasNext()) {
            ImageReader r = readers.next();
            r.setInput(iis, true);
            IIOMetadata md = r.getImageMetadata(0);
            String[] names = md.getMetadataFormatNames();
            for (int i = 0; i < names.length; i++) {
                Node found = recurs(md.getAsTree(names[i]));
                NamedNodeMap k = found.getAttributes();
                if (k.getLength()>2) {
                    //System.out.printf ("%-15s "+ k.item(2).getNodeValue() + "\n", "data");
                    return k.item(2).getNodeValue();
                }
            }
        }
        return "IDK :(";
    }
    public static Node recurs(Node node) {
        if (node.getNodeName().equals("TextEntry")) {
            return node;
        } else if (node.hasChildNodes()) {
            return recurs(node.getFirstChild());
        } else if (node.getNextSibling() != null) {
            return recurs(node.getNextSibling());
        } else if (node.getParentNode().getNextSibling() != null) {
            return recurs(node.getParentNode().getNextSibling());
        } else return node;
    }
    public static void objGen(GameObj f) throws IOException {
        //System.out.printf ("%-15s "+f.getName()+ "\n", "objgen");
        String icon = pullFileData(Main.gameRoot + "\\" + f.getIcon());
        String out = Main.exportRoot + "/" + f.getIcon();
        String in = Main.gameRoot + "/" + f.getIcon();
        out = out.substring(0,out.length()-4);
        File workFolder = new File(out);
        workFolder.mkdirs();
        File poo = new File(out + "/" + f.getIcon_state()+".png");
        poo.createNewFile();
        BufferedImage b = ImageIO.read(new File(in));
        int high = b.getHeight();
        int wide = b.getWidth();
        int tilew = 32;
        int tileh = 32;
        String findStr = "state";
        int lastIndex = 0;
        int count = 0;
        while(lastIndex != -1){

            lastIndex = icon.indexOf(findStr,lastIndex);

            if(lastIndex != -1){
                count ++;
                lastIndex += findStr.length();
            }
        }
        String[] states = new String[count];
        String[] dirs = new String[count];
        String[] frames = new String[count];
        Pattern pattern = Pattern.compile("\"(.*?)\"");

        Scanner sh = new Scanner(icon);
        sh.useDelimiter("\n");
        int poop = -1;
        while (sh.hasNext()) {
            String work = sh.next();
            if (work.startsWith("\twidth = ")) {
                work = work.replaceAll("\twidth = ", "");
                tilew = Integer.parseInt(work);
            }
            if (work.startsWith("\theight = ")) {
                work = work.replaceAll("\theight = ", "");
                tileh = Integer.parseInt(work);
            }
            if (work.startsWith("state = ")) {
                poop += 1;
                work = work.replaceAll("state = ", "");
                work = work.replaceAll("\"", "");
                states[poop] = work;
            }
            if (work.startsWith("\tdirs = ")) {
                work = work.replaceAll("\tdirs   = ", "");
                dirs[poop] = work;
            }
            if (work.startsWith("\tframes = ")) {
                work = work.replaceAll("\tframes = ", "");
                frames[poop] = work;
            }
        }
        int t = 0;

        int x = wide/tilew;
        int y = high/tileh;
        BufferedImage[][] shit = new BufferedImage[x][y];
        System.out.printf ("%-15s "+icon+ "\n", "init");
        for (int i = 0; i < (y); i++) {
            for (int j = 0; j < (x); j++) {
                shit[j][i] = b.getSubimage(j*tilew, i*tileh, tilew, tileh);
            }
        }
        Cook c = new Cook(out,states,dirs,frames,shit, tilew,tileh);
        c.bake();
    }
    public static GameObj generateObjNodeTree(String s) {
        return generateObjNodeTree(s,root,0);
    }
    private static GameObj generateObjNodeTree(String s,GameObj g, int depth) {

        String[] poop = s.substring(1).split("/");
        StringBuilder j = new StringBuilder();
        if (depth<poop.length){
            for (int i = 0; i < depth+1; i++) {
                j.append("/").append(poop[i]);
            }

        }else{
            GameObj f = new GameObj(s);
            g.addChildren(f);
            return f;

        }
        GameObj f = new GameObj(j.toString());
        if (g.hasChild()){
            int x = g.children.size();
            for (int i = 0; i < x; i++) {
                String sh = g.children.get(i).getPath();
                if (sh.equals(j.toString())){
                    return generateObjNodeTree(s,g.children.get(i),depth+1);
                }
            }
        }
        g.addChildren(f);
        return generateObjNodeTree(s,f,depth+1);
    }
    public static GameObj searchTree(String s){
        return searchTree(s,root,0);
    }
    private static GameObj searchTree(String s,GameObj g,int depth){
        String[] poop = s.substring(1).split("/");
        StringBuilder j = new StringBuilder();
        if (depth<poop.length) {
            for (int i = 0; i < depth + 1; i++) {
                j.append("/").append(poop[i]);
            }
        }else {
            return g;
        }
        if (g.hasChild()){
            int x = g.children.size();
            for (int i = 0; i < x; i++) {
                String sh = g.children.get(i).getPath();
                if (sh.equals(j.toString())){
                    return searchTree(s,g.children.get(i),depth+1);
                }
            }
        }
        return null;
    }
}
