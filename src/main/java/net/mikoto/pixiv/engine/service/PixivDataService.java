package net.mikoto.pixiv.engine.service;


import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import net.mikoto.pixiv.api.pojo.PixivData;
import net.mikoto.pixiv.engine.dao.PixivDataDao;
import net.mikoto.pixiv.engine.pojo.Config;
import net.mikoto.pixiv.engine.pojo.PixivDataResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

import static net.mikoto.pixiv.engine.util.HttpUtil.httpGet;

/**
 * @author mikoto
 * @date 2021/12/11 22:26
 */
public class PixivDataService {
    private static final String ERROR_CODE = "error";
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final ArrayList<String> pixivDataForwardServer;
    private final String key;
    private final ArrayList<String> tableName = new ArrayList<>();
    private Integer lastServer = 0;

    /**
     * Init service.
     */
    public PixivDataService(@NotNull Config config) {
        this.key = config.getKey();
        this.pixivDataForwardServer = config.getPixivDataForwardServer();
        tableName.add("bookmark_0_1000");
        tableName.add("bookmark_1000_5000");
        tableName.add("bookmark_5000_10000");
        tableName.add("bookmark_10000_15000");
        tableName.add("bookmark_15000_20000");
        tableName.add("bookmark_20000_25000");
        tableName.add("bookmark_25000_30000");
        tableName.add("bookmark_30000_40000");
        tableName.add("bookmark_40000_50000");
        tableName.add("bookmark_50000_100000");
        tableName.add("bookmark_100000");
    }

    /**
     * Crawl pixiv data by id
     *
     * @param server    Pixiv forward server
     * @param artworkId Artwork id
     * @return pixiv data
     */
    @Nullable
    private PixivData crawlPixivDataById(@NotNull String server, @NotNull Integer artworkId) {
        PixivData pixivData = null;
        try {
            JSONObject rawJson = JSONObject.parseObject(httpGet("https://" + server + "/getArtworkInformation?key=" + key + "&artworkId=" + artworkId));
            try {
                if (!rawJson.getBoolean(ERROR_CODE)) {
                    rawJson.put("crawlDate", SIMPLE_DATE_FORMAT.format(new Date()));
                    pixivData = new PixivData();
                    pixivData.loadJson(rawJson);
                } else {
                    return null;
                }
            } catch (JSONException e) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                crawlPixivDataById(server, artworkId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return pixivData;
    }

    /**
     * Get forward server.
     *
     * @return server address.
     */
    public synchronized String getPixivDataForwardServer() {
        if (lastServer >= pixivDataForwardServer.size()) {
            lastServer = 0;
        }
        String server = pixivDataForwardServer.get(lastServer);
        lastServer++;
        return server;
    }

    /**
     * Get a pixiv data by id and store it in database.
     *
     * @param artworkId    The id of this artwork.
     * @param pixivDataDao Dao object of database.
     * @return A pixiv data.
     * @throws SQLException An error.
     */
    public PixivData getPixivDataById(@NotNull String server, @NotNull Integer artworkId, @NotNull PixivDataDao pixivDataDao) throws SQLException {
        PixivData pixivData = crawlPixivDataById(server, artworkId);
        if (pixivData != null) {
            pixivDataDao.insertPixivData(pixivData);
        } else {
            throw new NullPointerException();
        }

        return pixivData;
    }

    /**
     * Get pixiv data by tag.
     *
     * @param tag          Tag name.
     * @param pixivDataDao Dao object of database.
     * @return A pixiv data.
     * @throws SQLException An error.
     */
    public PixivData getPixivDataByTag(@NotNull String tag, @NotNull PixivDataDao pixivDataDao) throws SQLException {
        ArrayList<Integer> pageArray = getPageArray();

        for (Integer page :
                pageArray) {
            String sql = "SELECT * FROM `" + tableName.get(page) + "` WHERE `tags` LIKE '%" + tag + "%' order by rand() limit 1";

            PixivDataResult pixivDataResult = pixivDataDao.queryPixivData(sql);

            if (pixivDataResult != null) {
                if (pixivDataResult.getPixivDataCount() == 1) {
                    return pixivDataResult.getIterator().next();
                }
            }
        }
        return null;
    }

    /**
     * Get pixiv data by tag.
     *
     * @param tag          Tag name.
     * @param pixivDataDao Dao object of database.
     * @param column       Sort by.
     * @param type         Sorting method.
     * @param count        The number of pixiv data.
     * @return A pixiv data.
     * @throws SQLException An error.
     */
    public Set<PixivData> getMultiPixivDataByTag(@NotNull String tag, @NotNull PixivDataDao pixivDataDao, Column column, Type type, int count) throws SQLException {
        ArrayList<String> pageArray = getPageArray(type);
        ArrayList<PixivData> outputPixivDataArrayList = new ArrayList<>();
        int outputPixivDataCount = 0;

        for (String page :
                pageArray) {
            String sql = "SELECT * FROM `" + page + "` WHERE `tags` LIKE '%" + tag + "%' order by " + column + " " + type + " limit " + count;

            PixivDataResult pixivDataResult = pixivDataDao.queryPixivData(sql);

            outputPixivDataCount += pixivDataResult.getPixivDataCount();

            outputPixivDataArrayList.addAll(pixivDataResult.getPixivDataSet());

            if (outputPixivDataCount >= count) {
                for (int i = 0; i < outputPixivDataCount - count; i++) {
                    outputPixivDataArrayList.remove(outputPixivDataArrayList.size() - 1);
                }
                return new HashSet<>(outputPixivDataArrayList);
            }
        }
        return null;
    }

    /**
     * Get pixiv data by tag.
     *
     * @param authorName   Tag name.
     * @param pixivDataDao Dao object of database.
     * @return A pixiv data.
     * @throws SQLException An error.
     */
    public PixivData getPixivDataByAuthorName(@NotNull String authorName, @NotNull PixivDataDao pixivDataDao) throws SQLException {
        ArrayList<Integer> pageArray = getPageArray();

        for (Integer page :
                pageArray) {
            String sql = "SELECT * FROM `" + tableName.get(page) + "` WHERE `author_name` LIKE '%" + authorName + "%' order by rand() limit 1";

            PixivDataResult pixivDataResult = pixivDataDao.queryPixivData(sql);

            if (pixivDataResult != null) {
                if (pixivDataResult.getPixivDataCount() == 1) {
                    return pixivDataResult.getIterator().next();
                }
            }
        }
        return null;
    }

    /**
     * Get random page array.
     *
     * @return An array list.
     */
    @NotNull
    private ArrayList<Integer> getPageArray() {
        Random rand = new Random();
        ArrayList<Integer> pageArray = new ArrayList<>();
        for (int i = 0; i < tableName.size(); i++) {
            int page = rand.nextInt(10);
            while (pageArray.contains(page)) {
                page = rand.nextInt(10);
            }
            pageArray.add(page);
        }
        return pageArray;
    }

    /**
     * Get random page array.
     *
     * @return An array list.
     */
    @NotNull
    private ArrayList<String> getPageArray(@NotNull Type type) {
        if (type.equals(Type.desc)) {
            Collections.reverse(tableName);
        }
        ArrayList<String> pageArray = new ArrayList<>(tableName);
        Collections.reverse(tableName);
        return pageArray;
    }
}
