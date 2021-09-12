package com.lmxdawn.admin.util;

import com.lmxdawn.admin.config.PublicFileUrlConfig;

/**
 * 公共文件管理工具
 */
public class PublicFileUtils {

    public static String createUploadUrl(String filePath) {

        if (filePath == null || filePath.isEmpty()) {
            return "";
        }

        if (filePath.indexOf("http") == 0 || filePath.indexOf("/") == 0) {
            return filePath;
        }

        return PublicFileUrlConfig.getDomain() + "/" + filePath;
    }

}
