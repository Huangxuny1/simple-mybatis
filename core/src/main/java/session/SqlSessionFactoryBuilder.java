package session;

import session.impl.SimpleSqlSessionFactory;

import java.io.IOException;
import java.io.InputStream;

public class SqlSessionFactoryBuilder {
    public SqlSessionFactory build(String fileName) {
        InputStream inputStream = SqlSessionFactoryBuilder.class.getClassLoader().getResourceAsStream(fileName);
        return build(inputStream);
    }

    public SqlSessionFactory build(InputStream is) {
        try {
            Configuration.PROPS.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new SimpleSqlSessionFactory(new Configuration());
    }
}
