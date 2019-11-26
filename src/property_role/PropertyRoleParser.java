package property_role;

import java.util.Map;

/**
 * 属性规则解析器
 */
public interface PropertyRoleParser {

    /**
     * 解析方法
     * @param role 指定的规则
     * @param data 待匹配的数据
     * @return 结果
     */
    Object resolve(String role, Map<String, Object> data);

}
