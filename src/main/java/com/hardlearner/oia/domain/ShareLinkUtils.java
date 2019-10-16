package com.hardlearner.oia.domain;

import org.springframework.security.crypto.encrypt.Encryptors;

import java.io.IOException;
import java.util.Properties;

public class ShareLinkUtils {
    private static String URL_PARAMETER_SPLITTER = "?key=";

    public static String generate(String shareRequestUrl) {
        // /api/articles/id/share -> /articles/id/share?key=[key]
        String editedShareRequestUrl = shareRequestUrl.substring(4);
        String key = getKey(editedShareRequestUrl);
        return editedShareRequestUrl + URL_PARAMETER_SPLITTER + key;
    }

    private static String getKey(String url) {
        // 같은 입력값에 대한 같은 출력값을 제공한다
        // password, salt는 외부에 노출되면 안 되는 값이다
        Properties properties = null;
        try {
            // project폴더 내에 encrypt.properties 파일이 있어야 한다
            properties = PropertiesLoader.loadProperties("encrypt.properties");
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return Encryptors.queryableText(properties.getProperty("password"), properties.getProperty("salt")).encrypt(url);
    }

    public static boolean authorize(String shareRequestUrl, String key) {
        if (getKey(shareRequestUrl).equals(key)) {
            return true;
        }
        return false;
    }
}
