/**
 * @Author shiyuquan
 * @Date 2018/10/25 10:51
 * @Description TODO
 */
public class Garbage {
    public static void main(String[] args) {
        // As long as the flag hasn't been set,
        // make Chairs and Strings:
        while(!Chair.f) {
            new Chair();
            new String("To take up space");
        }
        System.out.println(
                "After all Chairs have been created:\n" +
                        "total created = " + Chair.created +
                        ", total finalized = " + Chair.finalized);
        // Optional arguments force garbage
        // collection & finalization:
        if(args.length > 0) {
            if(args[0].equals("gc") ||
                    args[0].equals("all")) {
                System.out.println("gc():");
                System.gc();
            }
            if(args[0].equals("finalize") ||
                    args[0].equals("all")) {
                System.out.println("runFinalization():");
                System.runFinalization();
            }
        }
        System.out.println("bye!");
    }
}
