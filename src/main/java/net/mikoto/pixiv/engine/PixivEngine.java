package net.mikoto.pixiv.engine;

import net.mikoto.pixiv.engine.dao.PixivDataDao;
import net.mikoto.pixiv.engine.pojo.Config;
import net.mikoto.pixiv.engine.service.PixivDataService;
import org.jetbrains.annotations.NotNull;

/**
 * @author mikoto
 * @date 2021/12/26 0:38
 */
public class PixivEngine {
    private final PixivDataDao pixivDataDao;
    private final PixivDataService pixivDataService;

    public PixivEngine(@NotNull Config config) {
        if (config.getJpbcUrl() == null
                || config.getLogger() == null
                || config.getUserName() == null
                || config.getUserPassword() == null
                || config.getPixivDataForwardServer() == null
                || config.getKey() == null) {
            throw new NullPointerException();
        }
        this.pixivDataDao = new PixivDataDao(config);
        this.pixivDataService = new PixivDataService(config);
    }

    public PixivDataDao getPixivDataDao() {
        return pixivDataDao;
    }

    public PixivDataService getPixivDataService() {
        return pixivDataService;
    }
}
