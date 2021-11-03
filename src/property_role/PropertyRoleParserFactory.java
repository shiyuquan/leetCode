package property_role;

/**
 * 属性解析器工厂
 */
public class PropertyRoleParserFactory {

    /**
     * @param ope 参数
     * @return property_role.PropertyRoleParser
     */
    public static PropertyRoleParser getParser(String ope) {
        PropertyRoleParser propertyRoleParser = null;
        switch (ope) {
            case "DEFAULT":
                propertyRoleParser = new DefaultPropertyRoleParser();
                break;
            default:
                break;
        }
        return propertyRoleParser;

    }
}
