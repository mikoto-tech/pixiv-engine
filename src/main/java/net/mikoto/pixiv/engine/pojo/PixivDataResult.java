package net.mikoto.pixiv.engine.pojo;

import net.mikoto.pixiv.api.pojo.PixivData;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author mikoto
 * @date 2022/1/24 5:15
 */
public class PixivDataResult {
    private final Set<PixivData> pixivDataSet = new HashSet<>();

    public int getPixivDataCount() {
        return pixivDataSet.size();
    }

    public void add(PixivData pixivData) {
        pixivDataSet.add(pixivData);
    }

    /**
     * Remove pixiv data by object.
     *
     * @param pixivData The pixiv data you need to remove.
     */
    public void remove(PixivData pixivData) {
        pixivDataSet.remove(pixivData);
    }

    /**
     * Get the iterator of the set.
     *
     * @return An iterator.
     */
    public Iterator<PixivData> getIterator() {
        return pixivDataSet.iterator();
    }

    /**
     * Get pixiv data set.
     *
     * @return The set of pixiv data.
     */
    public Set<PixivData> getPixivDataSet() {
        return pixivDataSet;
    }
}
