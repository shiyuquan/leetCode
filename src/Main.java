import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private int counter=0;
    private void recur(){
        counter++;
        recur();//递归
    }
    public void getStackDepth(){
        try {
            recur();
        }catch (Throwable t){
            System.out.println("栈最大深度："+counter);
            t.printStackTrace();
        }

    }

    public static void main(String[] args){
        Main stack=new Main();
        stack.getStackDepth();

    }
}