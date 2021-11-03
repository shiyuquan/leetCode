package data_structure.bloom_filter;

/**
 * https://llimllib.github.io/bloomfilter-tutorial/zh_CN/
 * https://hur.st/bloomfilter/?n=4000&p=1.0E-3&m=&k=
 *
 * 基本概念: 布隆过滤器（Bloom Filter）是由布隆（Burton Howard Bloom）在1970年提出的。是由一个很长的二进制向量（位向量）
 *      和一系列随机映射(hash)函数组成
 *
 * 作用: 用于检索一个元素是否在一个集合中 (可以判断某个元素一定不在集合中，不能判断一个元素一定在集合中，只能说可能在集合中，
 *      或者大概率在集合中)
 *
 * 优点: 间效率和查询时间都远远超过一般的算法
 * 缺点: 存在误识别率（错误率 False positives，即Bloom Filter判断某一元素存在于某集合中，但是实际上该元素并不在集合中）
 *
 * 重要参数:
 * 位数组（Bit Array）长度: m
 * 哈希函数(Hash Function) 个数: k
 * 期望的数据量: n
 * 误判率: p
 *
 * 公式：
 * 误判率
 *      p = (1 - e^(-(kn/m)))^k
 *
 * 布隆过滤器大小(m) 可以通过 期望的误判率(p) 和 预计的数量大小(n) 计算得出
 *      m = - (n * ln P) / (ln2)^2
 *
 * 最小哈希函数个数
 *      k = (m/n)ln(2)
 *
 * @author shiyuquan
 * @since 2021/8/25 5:54 下午
 */
public class BloomFilter {}
