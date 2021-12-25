package net.mikoto.pixiv.engine.logger.impl;

import net.mikoto.pixiv.engine.logger.AbstractTimeFormatLogger;

/**
 * @author mikoto
 * @date 2021/12/11 21:56
 */
public class ConsoleTimeFormatLogger extends AbstractTimeFormatLogger {
    /**
     * Print data.
     *
     * @param data Input data.
     */
    @Override
    protected void print(String data) {
        System.out.println(data);
    }
}
