package util;

/**
 * @author shiyuquan
 * Create Time: 2018/11/12 11:08
 */
public class IdUtils {
    private long workerId;
    private long datacenterId;
    private long sequence = 0L;
    /** Thu, 04 Nov 2010 01:42:54 GMT */
    private long twepoch = 1288834974657L;
    /** 节点ID长度 */
    private long workerIdBits = 5L;
    /** 数据中心ID长度 */
    private long datacenterIdBits = 5L;
    /** 最大支持机器节点数0~31，一共32个 */
    private long maxWorkerId = -1L ^ (-1L << workerIdBits);
    /** 最大支持数据中心节点数0~31，一共32个 */
    private long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
    /** 序列号12位 */
    private long sequenceBits = 12L;
    /** 机器节点左移12位 */
    private long workerIdShift = sequenceBits;
    /** 数据中心节点左移17位 */
    private long datacenterIdShift = sequenceBits + workerIdBits;
    /** 时间毫秒数左移22位 */
    private long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    /** 4095 */
    private long sequenceMask = -1L ^ (-1L << sequenceBits);
    private long lastTimestamp = -1L;

    private static class IdGenHolder {
        private static final IdUtils instance = new IdUtils();
    }

    public static IdUtils get() {
        return IdGenHolder.instance;
    }

    public IdUtils() {
        this(0L, 0L);
    }

    public IdUtils(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    public synchronized long nextId() {
        //获取当前毫秒数
        long timestamp = timeGen();
        //如果服务器时间有问题(时钟后退) 报错。
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format(
                    "Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        //如果上次生成时间和当前时间相同,在同一毫秒内
        if (lastTimestamp == timestamp) {
            //sequence自增，因为sequence只有12bit，所以和sequenceMask相与一下，去掉高位
            sequence = (sequence + 1) & sequenceMask;
            //判断是否溢出,也就是每毫秒内超过4095，当为4096时，与sequenceMask相与，sequence就等于0
            if (sequence == 0) {
                //自旋等待到下一毫秒
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            //如果和上次生成时间不同,重置sequence，就是下一毫秒开始，sequence计数重新从0开始累加
            sequence = 0L;
        }
        lastTimestamp = timestamp;
        // 最后按照规则拼出ID。
        // 000000000000000000000000000000000000000000  00000            00000       000000000000
        // time                                       datacenterId   workerId    sequence
        return ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift)
                | (workerId << workerIdShift) | sequence;
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }
}
