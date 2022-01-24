package net.mikoto.pixiv.engine;

import com.alibaba.fastjson.JSON;
import net.mikoto.pixiv.api.pojo.PixivData;
import net.mikoto.pixiv.engine.logger.impl.ConsoleTimeFormatLogger;
import net.mikoto.pixiv.engine.pojo.Config;
import net.mikoto.pixiv.engine.service.Column;
import net.mikoto.pixiv.engine.service.Type;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static net.mikoto.pixiv.engine.util.HttpUtil.httpGet;

/**
 * @author mikoto
 * @date 2021/12/26 0:51
 */
public class PixivEngineTest {
    @Test
    void test() {
        Config config = new Config();
        config.setLogger(new ConsoleTimeFormatLogger());
        config.setJpbcUrl("jdbc:mysql://192.168.10.110:3306/pixiv_data");
        config.setUserName("pixiv_data");
        config.setUserPassword("YOUR DATABASE PASSWORD");
        config.setKey("1fc499f4ef758ad328505f6747d39198c9373cb1dfe893f21300f0eeb7a3f4c4");
        ArrayList<String> pixivDataForwardServer = new ArrayList<>();
        pixivDataForwardServer.add("pixiv-forward-1.mikoto-tech.cc");
        pixivDataForwardServer.add("pixiv-forward-3.mikoto-tech.cc");
        config.setPixivDataForwardServer(pixivDataForwardServer);

        PixivEngine pixivEngine = new PixivEngine(config);

        try {
            ArrayList<PixivData> pixivDataSet = pixivEngine.getPixivDataService().getMultiPixivDataByTag("≥ı“Ù", pixivEngine.getPixivDataDao(), Column.bookmark_count, Type.desc, 5);
            for (PixivData pixivData :
                    pixivDataSet) {
                config.getLogger().log(pixivData.toJsonObject().toJSONString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void daoTest() {
        Config config = new Config();
        config.setLogger(new ConsoleTimeFormatLogger());
        config.setJpbcUrl("jdbc:mysql://192.168.10.110:3306/pixiv_data");
        config.setUserName("pixiv_data");
        config.setUserPassword("YOUR DATABASE PASSWORD");
        config.setKey("1fc499f4ef758ad328505f6747d39198c9373cb1dfe893f21300f0eeb7a3f4c4");
        ArrayList<String> pixivDataForwardServer = new ArrayList<>();
        pixivDataForwardServer.add("pixiv-forward-1.mikoto-tech.cc");
        pixivDataForwardServer.add("pixiv-forward-3.mikoto-tech.cc");
        config.setPixivDataForwardServer(pixivDataForwardServer);

        PixivEngine pixivEngine = new PixivEngine(config);

        config.getLogger().log(pixivEngine.getPixivDataDao().getPixivDataTable(94236849));
    }

    @Test
    void datTest2() {
        Config config = new Config();
        config.setLogger(new ConsoleTimeFormatLogger());
        config.setJpbcUrl("jdbc:mysql://192.168.10.110:3306/pixiv_data");
        config.setUserName("pixiv_data");
        config.setUserPassword("YOUR DATABASE PASSWORD");
        config.setKey("1fc499f4ef758ad328505f6747d39198c9373cb1dfe893f21300f0eeb7a3f4c4");
        ArrayList<String> pixivDataForwardServer = new ArrayList<>();
        pixivDataForwardServer.add("pixiv-forward-1.mikoto-tech.cc");
        pixivDataForwardServer.add("pixiv-forward-3.mikoto-tech.cc");
        config.setPixivDataForwardServer(pixivDataForwardServer);

        PixivEngine pixivEngine = new PixivEngine(config);

        try {
            pixivEngine.getPixivDataDao().insertPixivData(new PixivData().loadJson(JSON.parseObject(httpGet("https://pixiv-forward-1.mikoto-tech.cc/getArtworkInformation?key=1fc499f4ef758ad328505f6747d39198c9373cb1dfe893f21300f0eeb7a3f4c4&artworkId=94236849"))));
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
