package multithreading.fork_join;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

public class RecursiveActionDemo {

    private class SumTask extends RecursiveAction {
        /** 每个任务的计算的大小标准值 */
        private static final int STANDARD = 10;
        /** 待计算的数组 */
        private int arr[];
        /** 计算的数组的开始下标 */
        private int start;
        /** 计算的数组的结束下标 */
        private int end;

        private int sum;

        public SumTask(int[] arr, int start, int end) {
            this.arr = arr;
            this.start = start;
            this.end = end;
        }

        private Integer subTotal() {
            Integer sum = 0;
            for (int i = start; i < end; i++) {
                sum += arr[i];
            }
//            System.err.println(Thread.currentThread().getName() + "caculate (" + start +" ~ " + end + ") = " + sum);
            return sum;
        }

        @Override
        protected void compute() {
            if ((end -start) <= STANDARD) {
                sum = subTotal();
            } else {
                int middle = (start + end) >>> 1;
                SumTask left = new SumTask(arr, start, middle);
                SumTask right = new SumTask(arr, middle, end);
                invokeAll(left, right);
                sum = left.sum + right.sum;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int size = 10000;
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = i + 1;
        }

        ForkJoinPool pool = new ForkJoinPool();
        SumTask task = new RecursiveActionDemo().new SumTask(arr, 0, arr.length);
        pool.invoke(task);
        pool.awaitTermination(2, TimeUnit.SECONDS);
        System.out.println(task.sum);
        pool.shutdown();
    }
}
