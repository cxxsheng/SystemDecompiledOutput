package android.provider;

/* loaded from: classes3.dex */
public class Browser {
    public static final java.lang.String EXTRA_APPLICATION_ID = "com.android.browser.application_id";
    public static final java.lang.String EXTRA_CREATE_NEW_TAB = "create_new_tab";
    public static final java.lang.String EXTRA_HEADERS = "com.android.browser.headers";
    public static final java.lang.String EXTRA_SHARE_FAVICON = "share_favicon";
    public static final java.lang.String EXTRA_SHARE_SCREENSHOT = "share_screenshot";
    public static final int HISTORY_PROJECTION_BOOKMARK_INDEX = 4;
    public static final int HISTORY_PROJECTION_DATE_INDEX = 3;
    public static final int HISTORY_PROJECTION_FAVICON_INDEX = 6;
    public static final int HISTORY_PROJECTION_ID_INDEX = 0;
    public static final int HISTORY_PROJECTION_THUMBNAIL_INDEX = 7;
    public static final int HISTORY_PROJECTION_TITLE_INDEX = 5;
    public static final int HISTORY_PROJECTION_TOUCH_ICON_INDEX = 8;
    public static final int HISTORY_PROJECTION_URL_INDEX = 1;
    public static final int HISTORY_PROJECTION_VISITS_INDEX = 2;
    public static final java.lang.String INITIAL_ZOOM_LEVEL = "browser.initialZoomLevel";
    private static final java.lang.String LOGTAG = "browser";
    private static final int MAX_HISTORY_COUNT = 250;
    public static final int SEARCHES_PROJECTION_DATE_INDEX = 2;
    public static final int SEARCHES_PROJECTION_SEARCH_INDEX = 1;
    public static final int TRUNCATE_HISTORY_PROJECTION_ID_INDEX = 0;
    public static final int TRUNCATE_N_OLDEST = 5;
    public static final android.net.Uri BOOKMARKS_URI = android.net.Uri.parse("content://browser/bookmarks");
    public static final java.lang.String[] HISTORY_PROJECTION = {"_id", "url", "visits", "date", "bookmark", "title", "favicon", "thumbnail", "touch_icon", "user_entered"};
    public static final java.lang.String[] TRUNCATE_HISTORY_PROJECTION = {"_id", "date"};
    public static final android.net.Uri SEARCHES_URI = android.net.Uri.parse("content://browser/searches");
    public static final java.lang.String[] SEARCHES_PROJECTION = {"_id", "search", "date"};

    public static class BookmarkColumns implements android.provider.BaseColumns {
        public static final java.lang.String BOOKMARK = "bookmark";
        public static final java.lang.String CREATED = "created";
        public static final java.lang.String DATE = "date";
        public static final java.lang.String FAVICON = "favicon";
        public static final java.lang.String THUMBNAIL = "thumbnail";
        public static final java.lang.String TITLE = "title";
        public static final java.lang.String TOUCH_ICON = "touch_icon";
        public static final java.lang.String URL = "url";
        public static final java.lang.String USER_ENTERED = "user_entered";
        public static final java.lang.String VISITS = "visits";
    }

    public static class SearchColumns implements android.provider.BaseColumns {
        public static final java.lang.String DATE = "date";
        public static final java.lang.String SEARCH = "search";

        @java.lang.Deprecated
        public static final java.lang.String URL = "url";
    }

    public static final void saveBookmark(android.content.Context context, java.lang.String str, java.lang.String str2) {
    }

    public static final void sendString(android.content.Context context, java.lang.String str) {
        sendString(context, str, context.getString(com.android.internal.R.string.sendText));
    }

    public static final void sendString(android.content.Context context, java.lang.String str, java.lang.String str2) {
        android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, str);
        try {
            android.content.Intent createChooser = android.content.Intent.createChooser(intent, str2);
            createChooser.setFlags(268435456);
            context.startActivity(createChooser);
        } catch (android.content.ActivityNotFoundException e) {
        }
    }

    public static final android.database.Cursor getAllBookmarks(android.content.ContentResolver contentResolver) throws java.lang.IllegalStateException {
        return new android.database.MatrixCursor(new java.lang.String[]{"url"}, 0);
    }

    public static final android.database.Cursor getAllVisitedUrls(android.content.ContentResolver contentResolver) throws java.lang.IllegalStateException {
        return new android.database.MatrixCursor(new java.lang.String[]{"url"}, 0);
    }

    private static final void addOrUrlEquals(java.lang.StringBuilder sb) {
        sb.append(" OR url = ");
    }

    private static final android.database.Cursor getVisitedLike(android.content.ContentResolver contentResolver, java.lang.String str) {
        java.lang.StringBuilder sb;
        boolean z = false;
        if (str.startsWith("http://")) {
            str = str.substring(7);
        } else if (str.startsWith("https://")) {
            str = str.substring(8);
            z = true;
        }
        if (str.startsWith("www.")) {
            str = str.substring(4);
        }
        if (z) {
            sb = new java.lang.StringBuilder("url = ");
            android.database.DatabaseUtils.appendEscapedSQLString(sb, "https://" + str);
            addOrUrlEquals(sb);
            android.database.DatabaseUtils.appendEscapedSQLString(sb, "https://www." + str);
        } else {
            java.lang.StringBuilder sb2 = new java.lang.StringBuilder("url = ");
            android.database.DatabaseUtils.appendEscapedSQLString(sb2, str);
            addOrUrlEquals(sb2);
            java.lang.String str2 = "www." + str;
            android.database.DatabaseUtils.appendEscapedSQLString(sb2, str2);
            addOrUrlEquals(sb2);
            android.database.DatabaseUtils.appendEscapedSQLString(sb2, "http://" + str);
            addOrUrlEquals(sb2);
            android.database.DatabaseUtils.appendEscapedSQLString(sb2, "http://" + str2);
            sb = sb2;
        }
        return contentResolver.query(android.provider.BrowserContract.History.CONTENT_URI, new java.lang.String[]{"_id", "visits"}, sb.toString(), null, null);
    }

    public static final void updateVisitedHistory(android.content.ContentResolver contentResolver, java.lang.String str, boolean z) {
    }

    @java.lang.Deprecated
    public static final java.lang.String[] getVisitedHistory(android.content.ContentResolver contentResolver) {
        return new java.lang.String[0];
    }

    public static final void truncateHistory(android.content.ContentResolver contentResolver) {
    }

    public static final boolean canClearHistory(android.content.ContentResolver contentResolver) {
        return false;
    }

    public static final void clearHistory(android.content.ContentResolver contentResolver) {
    }

    public static final void deleteHistoryTimeFrame(android.content.ContentResolver contentResolver, long j, long j2) {
    }

    public static final void deleteFromHistory(android.content.ContentResolver contentResolver, java.lang.String str) {
    }

    public static final void addSearchUrl(android.content.ContentResolver contentResolver, java.lang.String str) {
    }

    public static final void clearSearches(android.content.ContentResolver contentResolver) {
    }

    public static final void requestAllIcons(android.content.ContentResolver contentResolver, java.lang.String str, android.webkit.WebIconDatabase.IconListener iconListener) {
    }
}
