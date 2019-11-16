# simple-mybatis

> 一个简单的orm框架 ---- 麻雀虽小 五脏俱全.

## Configuration ( 解析配置)
 
Configuration Mybatis的配置核心,  ( Mybatis的Configuration配置项目很多,本项目是一个非常简化的版本)

通过 SqlSessionFactory的build()方法创建,储存数据库连接的参数(如:driver,url,username,password),并且储存 *Mapper.xml所对应的 *Mapper.java 以及的 MappedStatement
  
## SqlSession

SqlSession通过getMapper,获得一个代理对象MapperProxy从而对DB进行CRUD操作，并且根据配置文件中的配置进行返回。

## MappedStatement

MappedStatement 对应没一个Mapper文件,里面保存了 sqlType : (insert update delete select 等) ; originSql 及mapper中没有参数化过的sql语句 (包含#{...}之类的) ; sql 及参数化后替换为 参数为 ? 以供PrepareStatement prepare


## MapperProxy

MapperProxy 是一个JDK动态代理的实现类

Override invoke() 方法后,所有Mapper的方法调用时,都会调用这个invoke方法

如果是通用方法 [toString() hashCode() ] 则无需代理
if (Object.class.equals(method.getDeclaringClass())) {
                return method.invoke(this, args);
    }

由于代理的是 Mapper接口,因此具体方法需要实现,因此invoke()方法返回 

execute(method, args)
通过ID 从Configuration的getMappedStatement()方法获取对应的 MappedStatement 

再通过 SqlSession 执行对应的增删改查操作

## MapperProxyFactory

工厂模式
通过 `(T) Proxy.newProxyInstance(mapperInterface.getClassLoader(),new Class[]{mapperInterface},mapperProxy)` 生成mapper接口对应的代理类实例

## SqlSession

SqlSession是MyBatis的关键对象,是执行持久化操作类.它是应用程序与持久层之间执行交互操作的一个单线程对象,也是MyBatis执行持久化操作的关键对象.SqlSession对象完全包含以数据库为背景的所有执行SQL操作的方法,它的底层封装了JDBC连接,可以用SqlSession实例来直接执行被映射的SQL语句.

SqlSession 支持的操作有

- insert
- update
- select
- delete
- commit 
- rollback

(selectMap 暂未实现)
(在Mybatis中Session中有缓存机制,本项目也暂未实现)


## SqlSessionFactory

SqlSessionFactory是MyBatis的关键对象,工厂方法模式的实现,它是个单个数据库映射关系经过编译后的内存镜像.SqlSessionFactory对象的实例可以通过SqlSessionFactoryBuilder对象类获得,而SqlSessionFactoryBuilder则可以从XML配置文件或一个预先定制的Configuration的实例构建出SqlSessionFactory的实例

## Executor

Executor即执行器, 每一条sql都通过Executor打开一个PrepareStatement execute() 
在Executor 首先prepare()完成sql语言的预编译,防止SQL注入
然后将sql语句参数化

例如: INSERT INTO table_1 VALUES (#{id},#{username},#{password}) 
1. 通过 mapper.insert(obj); 进行调用
2. 将 #{id},#{username},#{password}  替换为 PrepareStatement  所需要的 ? 即  INSERT INTO table_1 VALUES (?,?,?) 
3. 通过反射将obj获取对应field (id,username,password)的值以及对应的类型

```java
Method declaredMethod = obj.getClass().getDeclaredMethod("get" + toUpperFristChar(field));
String name = declaredMethod.getReturnType().getName();
Object value = declaredMethod.invoke(obj);
```
4. 通过 PrepareStatement的 
    - setString(int parameterIndex, String x)
    - setInt(int parameterIndex, int x)
    - setBoolean(int parameterIndex, boolean x)
   等方法将对应的值set
   如 :
   - stmt.setInt(1,${id})
   - stmt.setString(2,${username})
   - stmt.setString(3,${password})


## StatementHandler

真正调用JDBC execute() 方法的地方,将jdbc的返回结果 通过 ResultSetsHandler处理后返回Mapper中声明的类型

##  ResultSetsHandler

通过获取JDBC返回值结果

运行反射生成所需对象并返回

```java
 Field[] declaredFields = entity.getClass().getDeclaredFields();// 通过反射获取对象的所有属性

//----

 f = bean.getClass().getDeclaredField(propName);// 获得对象指定的属性
 f.setAccessible(true);// 将字段设置为可通过反射进行访问 比如当修饰词为private时
 f.set(bean, value);// 为属性设值
```

## DataSource

一个JDBC的简单封装,主要是 initDriver() 即经典语句 `Class.forName("com.mysql.cj.jdbc.Driver");` 并注册到 `DriverManager`
和 `DriverManager.getConnection(url, props);` 拿到 Connection

## Transaction

JDBC事务的简单封装

主要支持 
- `getConnection()` : 获取JDBC Connection
- `commit()`  : 提交执行结果
- `rollback()` : 回滚操作

---
> 实现的比较简陋 但是实现过程过通过debug mybatis源码学到了很多知识,纯属练手


### todo

GenericTokenParser 
ParameterHandler
ResultHandler
TypeHandler
KeyGenerator
重写 StatementHandler
缓存
隔离级别

