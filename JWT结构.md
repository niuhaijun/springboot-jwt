# JWT（json web token）
JWT(JSON Web Token)是一个开放标准（RFC 7519），它定义了一种紧凑且独立的方式，可以在各个系统之间用JSON作为对象安全地传输信息，并且可以保证所传输的信息不会被篡改。
## jwt的3个组成部分
jwt由三部分组成，用.分隔开
### 1、header 头部
* 第一部分为头部，通常由两部分组成，令牌的类型（jwt），以及使用的加密算法
```
{
 "alg": "HS256", // 加密算法
 "typ": "JWT"    // 令牌类型
}
```
Base64加密后，就变成了:
```
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9
```
### 2、payload 载荷
* 第二部分为载荷，用于存储信息，如token过期时间，发型人等
```
{
 "sub": "1234567890",
 "name": "John Doe",
 "iat": 1516239022
}
```
Base64加密后，就变成了:
```
eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ
```
### 3、signature 签名
第三部分是签名，生成签名需要以下3部分的信息
* 1、header（base64后）
* 2、payload（base64后）
* 3、secret 秘钥
* 注意
  * 接受到JWT后，利用相同的信息再计算一次签名，然年与JWT中的签名对比，如果不相同则说明JWT中的内容被篡改。
  * secret是保存在服务器端的，jwt的签发生成也是在服务器端的，secret就是用来进行jwt的签发和jwt的验证，因此切勿泄露秘钥
  * JWT的内容并不是加密的，只是简单的Base64编码。
---

## jwt的优点
* 1、安全性高，可防止token被伪造和篡改
* 2、可防护CSRF攻击
* 3、构成简单，体积小，便于传输
* 4、不需要在服务端保存会话信息, 所以它易于应用的扩展。
* 5、跨语言，因为json是通用的
* 6、可携带信息，payload可以存储一些业务必要信息
---

## jwt存在的问题以及解决方案
### 问题1：续签
session默认有效期是30分钟，30分钟内有访问，会自动续签。
* 解决方案1：每次请求，服务端都重新生成一个token
* 解决方案2：客户端定时刷新token
* 解决方案3：token即将过期时，服务端为客户端重新生成一个token
* 解决方案4：token不设置过期时间。以token为key，在Redis中设置过期时间，每次访问时校验、续签。
### 问题2：注销
session存储在服务端，注销时只要清除session即可。token是无状态的，服务端只校验token的有效性。
* 解决方案1：服务端将token存储到Redis中，注销的时候，删除即可
* 解决方案2：
  * 客户端删除存储token的cookie即可
  * 客户端调用注销token接口，服务端将token加入一个黑名单。黑名单保存的是未过期，但是想要使其无效的token，不用保存已过期的token。
### 问题3：token泄露
* http被窃听
* 客户端的cookie、storage被窃取
* 解决方案：https
---

## 应用场景
* 分布式系统，在网关服务设置安全校验逻辑
* 无状态的 RESTful API
* SSO 单点登录
---

## 参考链接
* [jwt相关介绍](https://www.guonanjun.com/220.html)
* [jwt结构介绍](https://www.toutiao.com/i6687888468342735367/)
* [jwt详解](https://learnku.com/articles/17883?order_by=vote_count&)
* [解码token](https://jwt.io/)




