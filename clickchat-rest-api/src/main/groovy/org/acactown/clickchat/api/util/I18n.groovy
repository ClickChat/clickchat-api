package org.acactown.clickchat.api.util

import java.text.MessageFormat

/**
 * @author Andrés Amado
 * @since 2015-06-14
 */
enum I18n {

    BUNDLE

    private static final ResourceBundle bundle = ResourceBundle.getBundle("i18n/messages");

    String i18n(String key, String... params) {
        if (params == null) {
            return bundle.getString(key);
        } else {
            return MessageFormat.format(bundle.getString(key), params);
        }
    }

}
