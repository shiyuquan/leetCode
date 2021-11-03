/**
 * @author shiyuquan
 */
public class Main {

    public final Main m = new Main();

    public static void main(String[] args) {
        // System.err.println("1");
        // new MThread().start();
        new Main().run();
    }

    public void run() {
        System.err.println(Thread.currentThread().getName() + " in");
    }

    // static class MThread extends Thread {
    //
    //     @Override
    //     public void run() {
    //         int a = 0;
    //         System.err.println(a);
    //         new Main().run();
    //     }
    //
    // }



}