package multithreading.fork_join;


import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * RecursiveTask demo
 */
public class RecursiveTaskDemo {

    private class SumTask extends RecursiveTask<Integer> {
        /** 每个任务的计算的大小标准值 */
        private static final int STANDARD = 10;
        /** 待计算的数组 */
        private int arr[];
        /** 计算的数组的开始下标 */
        private int start;
        /** 计算的数组的结束下标 */
        private int end;

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
        protected Integer compute() {
            if ((end -start) <= STANDARD) {
                return subTotal();
            } else {
                int middle = (start + end) / 2;
                SumTask left = new SumTask(arr, start, middle);
                SumTask right = new SumTask(arr, middle, end);
                left.fork();
                right.fork();

                return left.join() + right.join();
            }
        }
    }

    public static void main(String[] args) {
        int size = 10000;
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = i + 1;
        }

        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Integer> res = pool.submit(new RecursiveTaskDemo().new SumTask(arr, 0, arr.length));
        System.out.println("final result: " + res.invoke());
        pool.shutdown();
    }
}
