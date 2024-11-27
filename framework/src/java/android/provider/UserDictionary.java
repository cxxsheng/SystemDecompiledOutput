package android.provider;

/* loaded from: classes3.dex */
public class UserDictionary {
    public static final java.lang.String AUTHORITY = "user_dictionary";
    public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://user_dictionary");
    private static final int FREQUENCY_MAX = 255;
    private static final int FREQUENCY_MIN = 0;

    public static class Words implements android.provider.BaseColumns {
        public static final java.lang.String APP_ID = "appid";
        public static final java.lang.String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.google.userword";
        public static final java.lang.String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.google.userword";
        public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://user_dictionary/words");
        public static final java.lang.String DEFAULT_SORT_ORDER = "frequency DESC";
        public static final java.lang.String FREQUENCY = "frequency";
        public static final java.lang.String LOCALE = "locale";

        @java.lang.Deprecated
        public static final int LOCALE_TYPE_ALL = 0;

        @java.lang.Deprecated
        public static final int LOCALE_TYPE_CURRENT = 1;
        public static final java.lang.String SHORTCUT = "shortcut";
        public static final java.lang.String WORD = "word";
        public static final java.lang.String _ID = "_id";

        @java.lang.Deprecated
        public static void addWord(android.content.Context context, java.lang.String str, int i, int i2) {
            java.util.Locale locale;
            if (i2 == 0 || i2 == 1) {
                if (i2 == 1) {
                    locale = java.util.Locale.getDefault();
                } else {
                    locale = null;
                }
                addWord(context, str, i, null, locale);
            }
        }

        public static void addWord(android.content.Context context, java.lang.String str, int i, java.lang.String str2, java.util.Locale locale) {
            android.content.ContentResolver contentResolver = context.getContentResolver();
            if (android.text.TextUtils.isEmpty(str)) {
                return;
            }
            if (i < 0) {
                i = 0;
            }
            if (i > 255) {
                i = 255;
            }
            android.content.ContentValues contentValues = new android.content.ContentValues(5);
            contentValues.put(WORD, str);
            contentValues.put(FREQUENCY, java.lang.Integer.valueOf(i));
            contentValues.put("locale", locale == null ? null : locale.toString());
            contentValues.put(APP_ID, (java.lang.Integer) 0);
            contentValues.put("shortcut", str2);
            contentResolver.insert(CONTENT_URI, contentValues);
        }
    }
}
