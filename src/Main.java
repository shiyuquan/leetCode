import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author shiyuquan
 */
public class Main {
    public static void main(String[] args) throws Exception {
        List<Integer> l = null;

        Optional.ofNullable(l).ifPresent(ll -> {
            ll.forEach(System.out::println);
        });
    }

}