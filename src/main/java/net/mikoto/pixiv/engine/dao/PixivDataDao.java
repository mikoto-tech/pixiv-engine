package net.mikoto.pixiv.engine.dao;

import net.mikoto.dao.BaseDao;
import net.mikoto.pixiv.api.pojo.PixivData;
import net.mikoto.pixiv.engine.pojo.Config;
import net.mikoto.pixiv.engine.pojo.PixivDataResult;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


/**
 * @author mikoto
 * @date 2021/12/11 15:22
 */
public class PixivDataDao extends BaseDao {
    /**
     * Constants.
     */
    private static final String RESULT = "result";

    /**
     * Init method.
     *
     * @param config Config object.
     */
    public PixivDataDao(@NotNull Config config) {
        super(config.getJpbcUrl(), config.getUserName(), config.getUserPassword());
    }

    /**
     * Query pixiv data from database.
     *
     * @param sql Sql statement
     * @return A pixiv data object.
     * @throws SQLException A sql error.
     */
    public PixivDataResult queryPixivData(String sql) throws SQLException {
        ResultSet resultSet = executeQuery(sql);
        PixivDataResult pixivDataResult = new PixivDataResult();

        while (resultSet.next()) {
            PixivData pixivData = new PixivData();
            pixivData.setArtworkId(resultSet.getInt("pk_artwork_id"));
            pixivData.setArtworkTitle(resultSet.getString("artwork_title"));
            pixivData.setAuthorId(resultSet.getInt("author_id"));
            pixivData.setAuthorName(resultSet.getString("author_name"));
            pixivData.setDescription(resultSet.getString("description"));
            pixivData.setPageCount(resultSet.getInt("page_count"));
            pixivData.setBookmarkCount(resultSet.getInt("bookmark_count"));
            pixivData.setLikeCount(resultSet.getInt("like_count"));
            pixivData.setViewCount(resultSet.getInt("view_count"));
            pixivData.setGrading(resultSet.getInt("grading"));
            pixivData.setCrawlDate(resultSet.getDate("crawl_date").toString());
            pixivData.setCreateDate(resultSet.getDate("create_date").toString());
            pixivData.setUpdateDate(resultSet.getDate("update_date").toString());
            pixivData.setTags(resultSet.getString("tags").split(";"));
            Map<String, String> illustUrls = new HashMap<>(5);
            illustUrls.put("mini", resultSet.getString("illust_url_mini"));
            illustUrls.put("thumb", resultSet.getString("illust_url_thumb"));
            illustUrls.put("small", resultSet.getString("illust_url_small"));
            illustUrls.put("regular", resultSet.getString("illust_url_regular"));
            illustUrls.put("original", resultSet.getString("illust_url_original"));
            pixivData.setIllustUrls(illustUrls);
            pixivDataResult.add(pixivData);
        }

        return pixivDataResult;
    }

    /**
     * Insert pixiv data
     *
     * @param pixivData Pixiv data.
     * @throws SQLException ERROR.
     */
    public void insertPixivData(@NotNull PixivData pixivData) throws SQLException {
        Connection connection = getConnection();

        String sql = "select count(*) as result from " + getPixivDataTable(pixivData.getArtworkId()) + " where pk_artwork_id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, pixivData.getArtworkId());
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        if (resultSet.getInt(RESULT) != 0) {
            sql = "DELETE FROM " + getPixivDataTable(pixivData.getBookmarkCount()) + " WHERE pk_artwork_id=?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, pixivData.getArtworkId());
            preparedStatement.executeUpdate();
        }

        sql = "INSERT INTO pixiv_data." + getPixivDataTable(pixivData.getArtworkId()) + " (pk_artwork_id, artwork_title, author_id, author_name, description, tags, illust_url_mini, illust_url_thumb, illust_url_small, illust_url_regular, illust_url_original, page_count, bookmark_count, like_count, view_count, grading, update_date, create_date, crawl_date)  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, pixivData.getArtworkId());
        preparedStatement.setString(2, pixivData.getArtworkTitle());
        preparedStatement.setInt(3, pixivData.getAuthorId());
        preparedStatement.setString(4, pixivData.getAuthorName());
        preparedStatement.setString(5, pixivData.getDescription());
        StringBuilder tags = new StringBuilder();
        for (int i = 0; i < pixivData.getTags().length; i++) {
            tags.append(pixivData.getTags()[i]);
            if (i != tags.length() - 1) {
                tags.append(";");
            }
        }
        preparedStatement.setString(6, tags.toString());
        preparedStatement.setString(7, pixivData.getIllustUrls().get("mini"));
        preparedStatement.setString(8, pixivData.getIllustUrls().get("thumb"));
        preparedStatement.setString(9, pixivData.getIllustUrls().get("small"));
        preparedStatement.setString(10, pixivData.getIllustUrls().get("regular"));
        preparedStatement.setString(11, pixivData.getIllustUrls().get("original"));
        preparedStatement.setInt(12, pixivData.getPageCount());
        preparedStatement.setInt(13, pixivData.getBookmarkCount());
        preparedStatement.setInt(14, pixivData.getLikeCount());
        preparedStatement.setInt(15, pixivData.getViewCount());
        preparedStatement.setInt(16, pixivData.getGrading());
        preparedStatement.setString(17, pixivData.getUpdateDate());
        preparedStatement.setString(18, pixivData.getCreateDate());
        preparedStatement.setString(19, pixivData.getCrawlDate());
        preparedStatement.executeUpdate();

        preparedStatement.close();
    }

    /**
     * Get table.
     *
     * @param artworkId The id of this artwork.
     * @return A table name.
     */
    public String getPixivDataTable(@NotNull Integer artworkId) {
        return "artwork_table_" + (int) Math.ceil(Double.valueOf(artworkId) / 5000000.0);
    }
}
