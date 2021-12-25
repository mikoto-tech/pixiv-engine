package net.mikoto.pixiv.engine;

import net.mikoto.pixiv.engine.logger.impl.ConsoleTimeFormatLogger;
import net.mikoto.pixiv.engine.pojo.Config;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author mikoto
 * @date 2021/12/26 0:51
 */
public class PixivEngineTest {
    @Test
    void test() {
        Config config = new Config();
        config.setLogger(new ConsoleTimeFormatLogger());
        config.setJpbcUrl("jdbc:mysql://203.135.96.74:51108/pixiv_data");
        config.setUserName("pixiv_data");
        config.setUserPassword("YOUR DATABASE PASSWORD");
        config.setKey("1fc499f4ef758ad328505f6747d39198c9373cb1dfe893f21300f0eeb7a3f4c4");
        ArrayList<String> pixivDataForwardServer = new ArrayList<>();
        pixivDataForwardServer.add("pixiv-forward-1.mikoto-tech.cc");
        pixivDataForwardServer.add("pixiv-forward-2.mikoto-tech.cc");
        pixivDataForwardServer.add("pixiv-forward-3.mikoto-tech.cc");
        config.setPixivDataForwardServer(pixivDataForwardServer);

        PixivEngine pixivEngine = new PixivEngine(config);

        try {
            config.getLogger().log(pixivEngine.getPixivDataService().getPixivDataByTag("–|·½", pixivEngine.getPixivDataDao()).toJsonObject().toJSONString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
