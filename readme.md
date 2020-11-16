## java实现一个简单的web服务器和浏览器(支持中文编程)





### 项目介绍

`webserver`一个纯属娱乐的项目，简单实现一个web服务器和一个浏览器客户端。本项目奔着极少的代码实现极简的功能。



本项目适合人群：

> 小白，对HTTP协议理解不深的
>
> 为了让小白能容易理解，代码几乎没有任何面向对象的设计，只为让小白舒适。


#### 涉及技术
 - IO
 - 集合
 - awt
 - 多线程
 - URL编码
 - HTTP协议头解析

### 服务器端

#### 相关功能
 - **支持可视化控制服务器启动关闭(类似IIS服务器)**

 - **支持中文编程**

 - 支持404响应

 - 支持网页icon图标

 - 支持html文本

 - 支持css文本

 - 支持js文本

 - 支持图片数据

 - 支持音频数据

 - 支持视频数据


### 客户端

 - 支持

 - HTTP协议解析

 - 网页渲染

客户端

![客户端](https://gitee.com/dahuoyzs/res/raw/master/img/browser.jpg)







服务器端

![服务器端](https://gitee.com/dahuoyzs/res/raw/master/img/server.jpg)



## 原理图，看不清，请把图片保存到桌面上仔细看。-_-

> 详细的说明了，URL回车后到页面渲染完成的详细流程，(本图内不涉及DNS解析的具体内容)

![浏览器_服务器模型_原理图](https://gitee.com/dahuoyzs/res/raw/master/img/浏览器_服务器模型_原理图.jpg)

