## 一、Struts2的配置

#### 1.web.xml

> 需先配置struts的核心过滤器

```xml
<!-- Spring核心监听器 -->
<listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
</listener>

<!-- 加载Spring配置文件的路径，默认加载WEB-INF/applicationContext.xml -->
<context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>classpath:applicationContext.xml</param-value>
</context-param>

<!-- Struts2的核心过滤器 -->
<filter>
    <filter-name>struts2</filter-name>
    <filter-class>org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter</filter-class>
</filter>
 
<filter-mapping>
    <filter-name>struts2</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
```

#### 2.struts.xml

> 一开始配置好strut2的常量即可

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE struts PUBLIC
    "-//Apache Software Foundation//DTD Struts Configuration 2.3//EN"
    "http://struts.apache.org/dtds/struts-2.3.dtd">
<struts>

	<!-- 配置Struts2的常量 -->
	<constant name="struts.action.extension" value="action"></constant>
	
	<!-- 配置Action -->
	<package name="crm" extends="struts-default" namespace="/">
		<!-- 配置用户管理的Action -->
		<action name="xx_*" class="xxxAction" method="{1}">
			<result name="login">/login.jsp</result>
		</action>
	</package>
</struts>   
```

## 二、spring的配置

#### 1.jdbc.properties

```
jdbc.driverClass=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql:///crm
jdbc.username=root
jdbc.password=mysql
```

#### 2.applicationContext.xml

> 将hibernate的配置全部交给spring来管理，主要包括内容有：

* 外部属性配置文件
* 配置连接池
* 引入hibernate的配置的信息（连接池、相关属性、映射文件）
* 配置Action、Service、Dao
* 配置事务管理器
* 开启注解事务

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:aop="http://www.springframework.org/schema/aop"
	xmlns:tx="http://www.springframework.org/schema/tx"
	xsi:schemaLocation="http://www.springframework.org/schema/beans 
	http://www.springframework.org/schema/beans/spring-beans.xsd
	http://www.springframework.org/schema/context
	http://www.springframework.org/schema/context/spring-context.xsd
	http://www.springframework.org/schema/aop
	http://www.springframework.org/schema/aop/spring-aop.xsd
	http://www.springframework.org/schema/tx 
	http://www.springframework.org/schema/tx/spring-tx.xsd">

	<!-- 引入外部属性文件 -->
	<context:property-placeholder location="classpath:jdbc.properties" />
	 
	<!-- 配置c3p0连接池 -->
	<bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource" >
		<property name="driverClass" value="${jdbc.driverClass}" />
		<property name="jdbcUrl" value="${jdbc.url}" />
		<property name="user" value="${jdbc.username}" />
		<property name="password" value="${jdbc.password}" />
	</bean>
	
	<!-- Spring整合Hibernate -->
	<!-- 引入Hibernate的配置的信息=============== -->
	<bean id="sessionFactory" class="org.springframework.orm.hibernate5.LocalSessionFactoryBean">
	    <!-- 注入连接池 -->
	    <property name="dataSource" ref="dataSource"/>
	    <!-- 配置Hibernate的相关属性 -->
	    <property name="hibernateProperties">
	        <props>
	            <prop key="hibernate.dialect">org.hibernate.dialect.MySQLDialect</prop>
	            <prop key="hibernate.show_sql">true</prop>
	            <prop key="hibernate.format_sql">true</prop>
	            <prop key="hibernate.hbm2ddl.auto">update</prop>
	        </props>
	    </property>
	 
	    <!-- 设置映射文件 -->
	    <property name="mappingResources">
	        <list>
	            <value>com/xxx/xxx/domain/xxx.hbm.xml</value>
	        </list>
	    </property>
	</bean>
	
	<!-- 配置事务管理器 -->
	<bean id="transactionManager" class="org.springframework.orm.hibernate5.HibernateTransactionManager">
	    <property name="sessionFactory" ref="sessionFactory"/>
	</bean>
	
	<!-- 开启注解事务 -->
	<tx:annotation-driven transaction-manager="transactionManager"/>
</beans>

```

## 三、log4j配置

```xml
### direct log messages to stdout ###
log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.Target=System.err
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=%d{ABSOLUTE} %5p 
%c{1}:%L - %m%n

### direct messages to file mylog.log ###
log4j.appender.file=org.apache.log4j.FileAppender
log4j.appender.file.File=c\:mylog.log
log4j.appender.file.layout=org.apache.log4j.PatternLayout
log4j.appender.file.layout.ConversionPattern=%d{ABSOLUTE} %5p 
%c{1}:%L - %m%n

### set log levels - for more verbose logging change 'info' to 
'debug' ###
# error warn info debug trace
log4j.rootLogger= info, stdout
```