package com.huangxunyi.datasource.mapper;

import mapping.MappedStatement;
import org.junit.Test;
import session.Configuration;
import utils.XmlUtil;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

public class MapperXMLReaderTest {

    @Test
    public void testXmlRead() {
        Configuration configuration = new Configuration();
        File f = new File("src/test/resources/TestMapper.xml");
        XmlUtil.readMapperXml(f, configuration);
        Map<String, MappedStatement> mappedStatements = configuration.getMappedStatements();
        Iterator<String> iterator = mappedStatements.keySet().iterator();
        while (iterator.hasNext()){
            String key = iterator.next();
            System.out.println("key  : "+ key+ " value : "+ mappedStatements.get(key));
        }
    }
}
