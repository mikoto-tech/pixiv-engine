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

## 声明

### 一切开发旨在学习，请勿用于非法用途

- Mikoto-Pixiv 是完全免费且开放源代码的软件，仅供学习和娱乐用途使用
- Mikoto-Pixiv 不会通过任何方式强制收取费用，或对使用者提出物质条件
- Mikoto-Pixiv 由整个开源社区维护，并不是属于某个个体的作品，所有贡献者都享有其作品的著作权。

### 许可证

    Copyright (C) 2021-2021 Mikoto and contributors.

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation, either version 3 of the
    License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.

`mikoto-pixiv` 采用 `AGPLv3` 协议开源。为了整个社区的良性发展，我们**强烈建议**您做到以下几点：

- **间接接触（包括但不限于使用 `Http API` 或 跨进程技术）到 `mikoto-pixiv` 的软件使用 `AGPLv3` 开源**
- **不鼓励，不支持一切商业使用**

鉴于项目的特殊性，开发团队可能在任何时间**停止更新**或**删除项目**。