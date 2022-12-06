package DMMtoBlend;

public class LoadBar {
    public static void bar(int cur, int target, String msg){
        float a = (float) cur;
        float b = (float) target;
        float poop = a/b;
        String outString = "║░░░░░░░░░░░░░░░░░░░░░║ " + Math.floor(poop*100) + "% ||| " + msg;
        for (int i = 0; i < poop*20; i++) {
            outString = outString.replaceFirst("░","█");
        }
        System.out.println(outString);
    }
}
