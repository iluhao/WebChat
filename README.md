# WebChat
SSM执行过程(登录为例)
- 启动TOMCAT容器,读取tomcat容器下的所有项目中的WEB.XML文件
- Web.xml文件中只做一件事情，加载applicationContext.xml，spring容器会创建数据源对象，sqlSessionFactory工厂对象，最重要的创建了所有的Mapper代理对象

- 访问LOING.JSP页面，并填写登录ID和密码，点击登录按钮(chatUser/login.do)
- 表单中的控件元素名称:userid,password
- chatUser/login.do 路径会被SpringMVC的控制器DispatcherServlet截取，(在第一次执行的时候，加载spring_mvc.xml文件 会创建的对象是:biz对象以及controller对象  ,保存请求路径和controller的对应关系
- chatUser/login     ChatUserController.login()
- chatUser/add     ChatUserController.add()
- chatUser/delete     ChatUserController.delete()
- 利用AutoWired注解建立对象之间的依赖关系，依赖关系关系的建立默认按照对象的ID名称相同的方式建立依赖
- )

- 控制器会根据（路径映射chatUser/login.do）找到对应的控制器的方法
- 参数的传递: 控制器DispatcherServlet会根据表单参数填充到方法中的形参中

- 控制器接收（登录）请求，并获取了登录的参数
- 控制器依赖了BIZ对象实现某一个功能
- BIZ调用了MAPPER对象的方法，并返回结果给Controller
- 最后控制器的方法（ChatUserController.login()）方法返回了一个字符串(login,index)
- 控制器返回的字符串(login,index) 交回了控制器DispatcherServlet
- 控制器DispatcherServlet会根据视图解析器(在spring_mvc.xml中配置)对象为字符串结构添加前缀与后缀，形成结果路径(/login.jsp,/index.jsp)返回给客户端
