# 1.核心容器

# 2.整合

# 3.AOP

## 3.1 AOP的概念与作用

AOP(Aspect Oriented Programming)面向切面编程，一种编程范式，知道开发者如何组织程序结构

- 作用：**在不惊动原始设计的基础上，进行功能增强。**
- Sping理念：**无入侵式编程**

## 3.2 核心概念

连接点：程序执行过程中的任意位置，粒度为执行方法、抛出异常、设置变量

- 在spring中，理解为方法的执行

切入点：匹配连接点的式子

在springAop中，一个切入点可以只描述一个具体方法，也可以匹配多个方法

- 一个具体方法：某个包下的某个接口的无形参无返回值的save方法
- 匹配多个方法：所有的get开头的方法，所有以Dao结尾的方法，所有带有一个参数的方法

通知：在切入点处执行的操作，也就是共性功能

通知类：定义通知的类

切面：描述通知与切入点的对应关系

## 3.3 AOP入门案例

案例设定：测定接口的执行效率

简化设定：在接口执行前输出当前系统时间

开发模式：注解

思路分析：

1. 导入坐标（pom.xml）
2. 制作连接点方法（原始操作，Dao接口和实现类）
3. 制作共性功能（通知类和通知） **通知类定为@Compoment @Aspect 配置类 @EnableAspectAutoProxy** 
4. 定义切入点 **设定一个私有的无返回值无内容的方法@Pointcut（“enecution（）”）**
5. 绑定切入点与通知关系（切面）**@before（切入点）**

## 3.4 AOP工作流程

1. Spring容器启动
2. 读取所有切面配置中的切入点 即只读取有切面连接的切入点
3. 初始化Bean，判定bean对应的类中的方法是否匹配到任意切入点
   - 匹配失败，创建对象
   - 匹配成功，创建原始对象（目标对象）的**代理对象**
4. 获取bean执行方法
   - 获取bean，调用方法并执行，完成操作
   - 获取的bean是代理对象时，根据**代理对象的运行模式**运行原始方法与增强内容，完成操作

## 3.5 AOP切入点表达式

切入点表达式标准格式 ：

动作关键字execution（访问修饰符 返回值 包名.类/接口名.方法名（参数）异常名）

可以使用通配符快速描述：

*：单个独立的任意符号，可以独立实现，也可以作为前缀或者后缀的匹配符出现

..：多个连续的任意符号，常用于简化包名与参数的书写

+：专用于匹配子类类型

**书写技巧**

- 所有代码按照标准规范开发，否则以下技巧全部失效
- 描述切入点通常描述**接口**，而不描述实现类
- 访问控制修饰符针对接口开发均采用public（**可省略**）
- 返回值类型对于增删改类只用精准类型加速匹配，对于查询类使用*通配符快速匹配
- **包名书写尽量不使用..匹配**，效率过低，常用*做单个包描述匹配，或精准匹配
- **接口名**/类名书写名称与模块相关的采用*匹配，例如UserService书写成 *Service，绑定业务层接口名
- **方法名书写以动词进行精准匹配**，名词采用 * 匹配，例如getById书写成 getBy*，selectAll书写成selectAll
- 参数规则较复杂，根据业务规则灵活调整
- 通常**不使用异常**作为匹配规则

## 3.6 AOP通知类型

前置通知 Before 后置通知 After AfterReturning AfterThrowing 

环绕通知 Around 

```java
@Around("切入点")
public void around(ProceedingJoinPoint) throws Throwable{
    //前置逻辑
    pjp.proceed();
    //后置逻辑
}
//如果连接点方法有返回值
@Around("切入点")
public Object around(ProceedingJoinPoint) throws Throwable{
    //前置逻辑
    Object ret = pjp.proceed();
    //后置逻辑
    return ret;
}
```

环绕通知注意事项

- 必须依赖形参ProceedingJoinPoint才能实现对原始方法的调用
- 如果未使用ProceedingJoinPoint对原始方法调用将跳过对原始方法的执行，可用于**权限校验**
- 对原始方法的调用可以不接受返回值，通常设置成void，如果接收返回值，必须设定为Object
- 原始方法返回值如果是void，通知方法返回值可以设void，也可以Object
- 由于无法预知原始方法运行后是否会抛出异常，因此环绕通知方法必须抛出Throwable对象

## 3.7 AOP通知获取数据

- 获取切入点方法的参数
  - JoinPoint 前置 后置 返回后 异常后通知 **Object[] args = jp.getArgs();**
  - ProceedJoinPoint 环绕通知 **可以劫持数据 用于参数矫正**
- 获取切入点方法返回值
  - 返回后通知
  - 环绕通知 
- 获取切入点方法运行异常现象
  - 异常后通知
  - 环绕通知

# 4.Spring 事务

## 4.1 模拟案例

作用：在数据层或业务层保障一系列数据的数据库操作同成功同失败

模拟银行账户转账

- 在业务层接口上添加Spring事务管理 @Transactional
- 设定事务管理器 PlatformTransactionManager
- 开启注解式事务驱动 @Enable

## 4.2 Spring 事务角色

事务管理员：发起事务方，通常指代业务层开启事务的方法

事务协调员：加入事务方，通常指代数据层方法，也可以是业务层方法

## 4.3 Spring事务属性

运行时异常和ERROR才回滚，通过rollbackfor设置

# 5. SpringMVC

## 5.1MVC概述

springmvc是一种基于java实现的mvc模型的轻量级web框架

使用简单，开发便捷

灵活性强

## 5.2 入门案例

1. 先导入SpringMvc坐标和Servlet坐标
2. 创建SpringMVC控制器类（等同于Servlet功能）@RequestMapping("/路径") @ResponseBody
3. 初始化SpringMVC环境，设定配置类
4. 初始化servlet容器，加载springmvc环境

