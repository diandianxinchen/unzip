工作中经常遇到需要处理压缩包的场景，有事会遇到压缩包中包含压缩包的情况。一个一个解压比较麻烦，写一个程序来处理这种问题。讲压缩包中的所有文件按路径解压出来。

直接使用ZipInputStream解压报错了  
搜索了一下说是和文件的编码有关系  
https://www.cnblogs.com/wang-meng/p/6886455.html

怎么判断文件的编码？  
https://bugs.openjdk.java.net/browse/JDK-7062777  
openjdk关于这个的解释

```xml
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-compress</artifactId>
    <version>1.8.1</version>
</dependency>
```
这个工具包有处理这种情况，有空看一下实现

2021-07-27 增加了commons-compress的例子  
参考文章 https://zhuanlan.zhihu.com/p/139700568  
7z的解压依赖居然是可选的... 不引入依赖的话只有接口没有实现会报错
