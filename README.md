# Mikoto-Pixiv-Engine

Mikoto-Pixiv-Engine 是提供基础Pixiv服务的基础库

从属于**Mikoto-Pixiv**项目之下

## 如何使用?

### Step.1

配置Config对象

例:

```java
public class TestClass {
    public void TestMethod() {
        Config config = new Config();
        config.setLogger(LOGGER);
        config.setKey(PROPERTIES.getProperty("KEY"));
        config.setUserPassword(PROPERTIES.getProperty("PASSWORD"));
        config.setUserName(PROPERTIES.getProperty("USERNAME"));
        config.setJpbcUrl(PROPERTIES.getProperty("URL"));
        config.setPixivDataForwardServer(new ArrayList<>(Arrays.asList(PROPERTIES.getProperty("DATA_FORWARD_SERVER").split(";"))));
    }
}
```

### Step.2

新建Pixiv-Engine对象

```java
public class TestClass {
    public void TestMethod() {
        PIXIV_ENGINE = new PixivEngine(config);
    }
}
```

大功告成! 您可以使用Pixiv-Engine提供的服务了