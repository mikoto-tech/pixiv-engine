package net.mikoto.pixiv.engine.pojo;

import net.mikoto.log.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * @author mikoto
 * @date 2021/12/26 0:26
 */
public class Config {
    private String jpbcUrl;
    private String userName;
    private String userPassword;
    private Logger logger;
    private ArrayList<String> pixivDataForwardServer;
    private String key;

    public String getJpbcUrl() {
        return jpbcUrl;
    }

    public void setJpbcUrl(@NotNull String jpbcUrl) {
        this.jpbcUrl = jpbcUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(@NotNull String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(@NotNull String userPassword) {
        this.userPassword = userPassword;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(@NotNull Logger logger) {
        this.logger = logger;
    }

    public ArrayList<String> getPixivDataForwardServer() {
        return pixivDataForwardServer;
    }

    public void setPixivDataForwardServer(@NotNull ArrayList<String> pixivDataForwardServer) {
        this.pixivDataForwardServer = pixivDataForwardServer;
    }

    public String getKey() {
        return key;
    }

    public void setKey(@NotNull String key) {
        this.key = key;
    }
}
