工作中经常遇到需要处理压缩包的场景，有事会遇到压缩包中包含压缩包的情况。一个一个解压比较麻烦，写一个程序来处理这种问题。讲压缩包中的所有文件按路径解压出来。

直接使用ZipInputStream解压报错了  
搜索了一下说是和文件的编码有关系  
https://www.cnblogs.com/wang-meng/p/6886455.html

怎么判断文件的编码？  
https://bugs.openjdk.java.net/browse/JDK-7062777  
openjdk关于这个的解释

