package utils;

import mapping.MappedStatement;
import mapping.SqlType;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import session.Configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 */
public final class XmlUtil {

    /**
     * 解析 mapper.xml 并为 configuration 设置SQLType 和相关成员变量
     *
     * @param file
     * @param configuration
     */
    public static void readMapperXml(File file, Configuration configuration) {
        try {
            SAXReader saxReader = new SAXReader();
            saxReader.setEncoding("utf-8");

            Document document = saxReader.read(file);

            Element root = document.getRootElement();

            if (!Constants.XML_ROOT_LABEL.equals(root.getName())) {
                System.err.println(" xml flag error");
                return;
            }
            String namespace = root.attributeValue(Constants.XML_SELECT_NAMESPACE);

            List<MappedStatement> stmts = new ArrayList<>();
            for (Iterator it = root.elementIterator(); it.hasNext(); ) {
                Element element = (Element) it.next();
                String eleName = element.getName();

                MappedStatement mappedStatement = new MappedStatement();
                if (SqlType.SELECT.value().equals(eleName)) {
                    String resultType = element.attributeValue(Constants.XML_SELECT_RESULTTYPE);
                    mappedStatement.setResultType(resultType);
                    mappedStatement.setSqlType(SqlType.SELECT);
                } else if (SqlType.UPDATE.value().equals(eleName)) {
                    mappedStatement.setSqlType(SqlType.UPDATE);
                } else if (SqlType.INSERT.value().equals(eleName)) {
                    mappedStatement.setSqlType(SqlType.INSERT);
                } else if (SqlType.DELETE.value().equals(eleName)) {
                    mappedStatement.setSqlType(SqlType.DELETE);
                } else {
                    System.err.println(" 暂不支持 ： " + eleName);
                    mappedStatement.setSqlType(SqlType.DEFAULT);
                }

                String expID = element.attributeValue(Constants.XML_ELEMENT_ID);
                String sqlId = namespace + "." + expID;
                mappedStatement.setSqlId(sqlId);
                mappedStatement.setNamespace(namespace);
                String trimSQL = element.getStringValue().trim();
                mappedStatement.setSql(trimSQL);
                mappedStatement.setSql(trimSQL);
                stmts.add(mappedStatement);

                configuration.addMappedStatement(sqlId, mappedStatement);
                configuration.addMappedStatement(expID, mappedStatement);
                configuration.addMapper(Class.forName(namespace));
            }

        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }
}
