package android.provider;

/* loaded from: classes3.dex */
public class BrowserContract {
    public static final java.lang.String AUTHORITY = "com.android.browser";
    public static final android.net.Uri AUTHORITY_URI = android.net.Uri.parse("content://com.android.browser");
    public static final java.lang.String CALLER_IS_SYNCADAPTER = "caller_is_syncadapter";
    public static final java.lang.String PARAM_LIMIT = "limit";

    public static final class Accounts {
        public static final java.lang.String ACCOUNT_NAME = "account_name";
        public static final java.lang.String ACCOUNT_TYPE = "account_type";
        public static final android.net.Uri CONTENT_URI = android.provider.BrowserContract.AUTHORITY_URI.buildUpon().appendPath(android.accounts.AccountManager.KEY_ACCOUNTS).build();
        public static final java.lang.String ROOT_ID = "root_id";
    }

    interface BaseSyncColumns {
        public static final java.lang.String SYNC1 = "sync1";
        public static final java.lang.String SYNC2 = "sync2";
        public static final java.lang.String SYNC3 = "sync3";
        public static final java.lang.String SYNC4 = "sync4";
        public static final java.lang.String SYNC5 = "sync5";
    }

    interface CommonColumns {
        public static final java.lang.String DATE_CREATED = "created";
        public static final java.lang.String TITLE = "title";
        public static final java.lang.String URL = "url";
        public static final java.lang.String _ID = "_id";
    }

    interface HistoryColumns {
        public static final java.lang.String DATE_LAST_VISITED = "date";
        public static final java.lang.String USER_ENTERED = "user_entered";
        public static final java.lang.String VISITS = "visits";
    }

    interface ImageColumns {
        public static final java.lang.String FAVICON = "favicon";
        public static final java.lang.String THUMBNAIL = "thumbnail";
        public static final java.lang.String TOUCH_ICON = "touch_icon";
    }

    interface ImageMappingColumns {
        public static final java.lang.String IMAGE_ID = "image_id";
        public static final java.lang.String URL = "url";
    }

    interface SyncColumns extends android.provider.BrowserContract.BaseSyncColumns {
        public static final java.lang.String ACCOUNT_NAME = "account_name";
        public static final java.lang.String ACCOUNT_TYPE = "account_type";
        public static final java.lang.String DATE_MODIFIED = "modified";
        public static final java.lang.String DIRTY = "dirty";
        public static final java.lang.String SOURCE_ID = "sourceid";
        public static final java.lang.String VERSION = "version";
    }

    public static final class ChromeSyncColumns {
        public static final java.lang.String CLIENT_UNIQUE = "sync4";
        public static final java.lang.String FOLDER_NAME_BOOKMARKS = "google_chrome_bookmarks";
        public static final java.lang.String FOLDER_NAME_BOOKMARKS_BAR = "bookmark_bar";
        public static final java.lang.String FOLDER_NAME_OTHER_BOOKMARKS = "other_bookmarks";
        public static final java.lang.String FOLDER_NAME_ROOT = "google_chrome";
        public static final java.lang.String SERVER_UNIQUE = "sync3";

        private ChromeSyncColumns() {
        }
    }

    public static final class Bookmarks implements android.provider.BrowserContract.CommonColumns, android.provider.BrowserContract.ImageColumns, android.provider.BrowserContract.SyncColumns {
        public static final int BOOKMARK_TYPE_BOOKMARK = 1;
        public static final int BOOKMARK_TYPE_BOOKMARK_BAR_FOLDER = 3;
        public static final int BOOKMARK_TYPE_FOLDER = 2;
        public static final int BOOKMARK_TYPE_MOBILE_FOLDER = 5;
        public static final int BOOKMARK_TYPE_OTHER_FOLDER = 4;
        public static final java.lang.String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/bookmark";
        public static final java.lang.String CONTENT_TYPE = "vnd.android.cursor.dir/bookmark";
        public static final android.net.Uri CONTENT_URI = android.net.Uri.withAppendedPath(android.provider.BrowserContract.AUTHORITY_URI, "bookmarks");
        public static final android.net.Uri CONTENT_URI_DEFAULT_FOLDER = android.net.Uri.withAppendedPath(CONTENT_URI, "folder");
        public static final java.lang.String INSERT_AFTER = "insert_after";
        public static final java.lang.String INSERT_AFTER_SOURCE_ID = "insert_after_source";
        public static final java.lang.String IS_DELETED = "deleted";
        public static final java.lang.String IS_FOLDER = "folder";
        public static final java.lang.String PARAM_ACCOUNT_NAME = "acct_name";
        public static final java.lang.String PARAM_ACCOUNT_TYPE = "acct_type";
        public static final java.lang.String PARENT = "parent";
        public static final java.lang.String PARENT_SOURCE_ID = "parent_source";
        public static final java.lang.String POSITION = "position";
        public static final java.lang.String QUERY_PARAMETER_SHOW_DELETED = "show_deleted";
        public static final java.lang.String TYPE = "type";

        private Bookmarks() {
        }

        public static final android.net.Uri buildFolderUri(long j) {
            return android.content.ContentUris.withAppendedId(CONTENT_URI_DEFAULT_FOLDER, j);
        }
    }

    public static final class History implements android.provider.BrowserContract.CommonColumns, android.provider.BrowserContract.HistoryColumns, android.provider.BrowserContract.ImageColumns {
        public static final java.lang.String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/browser-history";
        public static final java.lang.String CONTENT_TYPE = "vnd.android.cursor.dir/browser-history";
        public static final android.net.Uri CONTENT_URI = android.net.Uri.withAppendedPath(android.provider.BrowserContract.AUTHORITY_URI, "history");

        private History() {
        }
    }

    public static final class Searches {
        public static final java.lang.String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/searches";
        public static final java.lang.String CONTENT_TYPE = "vnd.android.cursor.dir/searches";
        public static final android.net.Uri CONTENT_URI = android.net.Uri.withAppendedPath(android.provider.BrowserContract.AUTHORITY_URI, "searches");
        public static final java.lang.String DATE = "date";
        public static final java.lang.String SEARCH = "search";
        public static final java.lang.String _ID = "_id";

        private Searches() {
        }
    }

    public static final class SyncState implements android.provider.SyncStateContract.Columns {
        public static final java.lang.String CONTENT_DIRECTORY = "syncstate";
        public static final android.net.Uri CONTENT_URI = android.net.Uri.withAppendedPath(android.provider.BrowserContract.AUTHORITY_URI, "syncstate");

        private SyncState() {
        }

        public static byte[] get(android.content.ContentProviderClient contentProviderClient, android.accounts.Account account) throws android.os.RemoteException {
            return android.provider.SyncStateContract.Helpers.get(contentProviderClient, CONTENT_URI, account);
        }

        public static android.util.Pair<android.net.Uri, byte[]> getWithUri(android.content.ContentProviderClient contentProviderClient, android.accounts.Account account) throws android.os.RemoteException {
            return android.provider.SyncStateContract.Helpers.getWithUri(contentProviderClient, CONTENT_URI, account);
        }

        public static void set(android.content.ContentProviderClient contentProviderClient, android.accounts.Account account, byte[] bArr) throws android.os.RemoteException {
            android.provider.SyncStateContract.Helpers.set(contentProviderClient, CONTENT_URI, account, bArr);
        }

        public static android.content.ContentProviderOperation newSetOperation(android.accounts.Account account, byte[] bArr) {
            return android.provider.SyncStateContract.Helpers.newSetOperation(CONTENT_URI, account, bArr);
        }
    }

    public static final class Images implements android.provider.BrowserContract.ImageColumns {
        public static final java.lang.String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/images";
        public static final java.lang.String CONTENT_TYPE = "vnd.android.cursor.dir/images";
        public static final android.net.Uri CONTENT_URI = android.net.Uri.withAppendedPath(android.provider.BrowserContract.AUTHORITY_URI, "images");
        public static final java.lang.String DATA = "data";
        public static final int IMAGE_TYPE_FAVICON = 1;
        public static final int IMAGE_TYPE_PRECOMPOSED_TOUCH_ICON = 2;
        public static final int IMAGE_TYPE_TOUCH_ICON = 4;
        public static final java.lang.String TYPE = "type";
        public static final java.lang.String URL = "url_key";

        private Images() {
        }
    }

    public static final class ImageMappings implements android.provider.BrowserContract.ImageMappingColumns {
        public static final java.lang.String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/image_mappings";
        public static final java.lang.String CONTENT_TYPE = "vnd.android.cursor.dir/image_mappings";
        public static final android.net.Uri CONTENT_URI = android.net.Uri.withAppendedPath(android.provider.BrowserContract.AUTHORITY_URI, "image_mappings");

        private ImageMappings() {
        }
    }

    public static final class Combined implements android.provider.BrowserContract.CommonColumns, android.provider.BrowserContract.HistoryColumns, android.provider.BrowserContract.ImageColumns {
        public static final android.net.Uri CONTENT_URI = android.net.Uri.withAppendedPath(android.provider.BrowserContract.AUTHORITY_URI, "combined");
        public static final java.lang.String IS_BOOKMARK = "bookmark";

        private Combined() {
        }
    }

    public static final class Settings {
        public static final android.net.Uri CONTENT_URI = android.net.Uri.withAppendedPath(android.provider.BrowserContract.AUTHORITY_URI, "settings");
        public static final java.lang.String KEY = "key";
        public static final java.lang.String KEY_SYNC_ENABLED = "sync_enabled";
        public static final java.lang.String VALUE = "value";

        private Settings() {
        }

        /* JADX WARN: Code restructure failed: missing block: B:17:0x0037, code lost:
        
            r0.close();
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public static boolean isSyncEnabled(android.content.Context context) {
            android.database.Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(CONTENT_URI, new java.lang.String[]{"value"}, "key=?", new java.lang.String[]{KEY_SYNC_ENABLED}, null);
                if (cursor != null && cursor.moveToFirst()) {
                    return cursor.getInt(0) != 0;
                }
                return false;
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }

        public static void setSyncEnabled(android.content.Context context, boolean z) {
            android.content.ContentValues contentValues = new android.content.ContentValues();
            contentValues.put("key", KEY_SYNC_ENABLED);
            contentValues.put("value", java.lang.Integer.valueOf(z ? 1 : 0));
            context.getContentResolver().insert(CONTENT_URI, contentValues);
        }
    }
}
