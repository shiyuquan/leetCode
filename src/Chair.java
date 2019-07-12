/**
 * @Author shiyuquan
 * @Date 2018/10/25 10:52
 * @Description TODO
 */
public class Chair {
    static boolean gcrun = false;
    static boolean f = false;
    static int created = 0;
    static int finalized = 0;
    int i;
    Chair() {
        i = ++created;
        System.out.println(i);
        if(created == 47)
            System.out.println("Created 47");
    }
    public void finalize() {
        if(i == 47) {
            System.out.println(
                    "Finalizing Chair #47, " +
                            "Setting flag to stop Chair creation");
            f = true;
        }
        if(!gcrun) {
            // The first time finalize() is called:
            gcrun = true;
            System.out.println(
                    "Beginning to finalize after " +
                            created + " Chairs have been created");
        }

        finalized++;
        if(finalized >= created)
            System.out.println(
                    "All " + finalized + " finalized");
    }
    public String toString() {
        return "chair";
    }
}
