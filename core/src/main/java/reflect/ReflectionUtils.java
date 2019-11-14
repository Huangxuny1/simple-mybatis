package reflect;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReflectionUtils {
    public static void setPropToBean(Object bean, String propName, Object value) {
        Field f;
        try {
            f = bean.getClass().getDeclaredField(propName);// 获得对象指定的属性
            f.setAccessible(true);// 将字段设置为可通过反射进行访问 比如当修饰词为private时
            f.set(bean, value);// 为属性设值

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void setPropToBeanFromResultSet(Object entity, ResultSet rs) throws SQLException {
        Field[] declaredFields = entity.getClass().getDeclaredFields();// 通过反射获取对象的所有属性
        for (int i = 0; i < declaredFields.length; i++) {
            String fieldName = declaredFields[i].getName();
            if (String.class.equals(declaredFields[i].getType())) {
                setPropToBean(entity, fieldName, rs.getString(fieldName));
            } else if (Integer.class.equals(declaredFields[i].getType())) {
                setPropToBean(entity, fieldName, rs.getInt(fieldName));
            } else if (Boolean.class.equals(declaredFields[i].getType())) {
                setPropToBean(entity, fieldName, rs.getBoolean(fieldName));
            } else {
                throw new RuntimeException(" 暂不支持数据类型  : " + fieldName);
            }
        }
    }
}
