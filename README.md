# rpcframework
# rpc framework for nio
## 基于spring、netty、guava开源框架实现
## netty
### server端基于netty的boss、work模式，服务端和客户端都使用线程池，boss负责接受新客户端链接的链接和任务分发，work负责channel内部pipeline和对应handler的绑定
### handler代码中handler按类型主要分为三种，接受客户端请求消息的反序列化(ChannelInboundHandlerAdapter)、业务处理、发送响应消息的序列化(ChannelOutboundHandlerAdapter)，考虑支持主流的多种序列化协议
### 业务处理handler目前使用单独业务线程池来处理，不占用boss和work线程池，提高nio吞吐，业务处理完成后异步通知channel回写处理结果给客户端
## springxml标签增强
### 扩展spring的NamespaceHandlerSupport以及BeanDefinitionParser，自定义了一套简单的xml标签自定义配置
## 注册
### RpcRegistery类
#### 实现spring的InitializingBean和DisposableBean，重写afterPropertiesSet方法，启动服务端主处理类
## 服务端
### RpcService类
#### 实现ApplicationContextAware和ApplicationListener，基于spring自定义标签，完成bean装配后，通过事件通知，将业务接口的全路径类名(interfaceName)和spring中的实现类对象绑定，存于单例模式的服务端执行器的map中保存
## 客户端 TODO
