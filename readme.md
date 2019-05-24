Quartz版本：2.3.0 目前稳定版

SpringBoot版本：2.0.4.RELEASE
## 项目结构
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190524235426118.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM0ODQ1Mzk0,size_16,color_FFFFFF,t_70)

结构很简单就不多说了，有一点需要注意，因为此项目使用的是Druid连接池，需要额外的一些配置，具体看：[Quartz学习笔记(五) quartz扩展druid连接池](https://www.cnblogs.com/zouhao510/p/5313600.html)

建议直接把最下面的源码克隆下来运行，源码里面注释很清晰，然后结合下面的几篇文章看，就可以很快理解了。

## 效果

![在这里插入图片描述](https://img-blog.csdnimg.cn/20190524234739300.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM0ODQ1Mzk0,size_16,color_FFFFFF,t_70)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190524234748207.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM0ODQ1Mzk0,size_16,color_FFFFFF,t_70)
## 入门
- [Quartz 入门详解](http://www.importnew.com/22890.html)
- [Quartz使用总结](https://www.cnblogs.com/drift-ice/p/3817269.html)

## 参考
- [Spring Boot集成持久化Quartz定时任务管理和界面展示](https://www.cnblogs.com/dekevin/p/8716596.html)
- [解决spring bean注入Job的问题](https://www.imooc.com/article/25585)