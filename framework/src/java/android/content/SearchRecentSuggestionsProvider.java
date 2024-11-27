package android.content;

/* loaded from: classes.dex */
public class SearchRecentSuggestionsProvider extends android.content.ContentProvider {
    public static final int DATABASE_MODE_2LINES = 2;
    public static final int DATABASE_MODE_QUERIES = 1;
    private static final int DATABASE_VERSION = 512;
    private static final java.lang.String NULL_COLUMN = "query";
    private static final java.lang.String ORDER_BY = "date DESC";
    private static final java.lang.String TAG = "SuggestionsProvider";
    private static final int URI_MATCH_SUGGEST = 1;
    private static final java.lang.String sDatabaseName = "suggestions.db";
    private static final java.lang.String sSuggestions = "suggestions";
    private java.lang.String mAuthority;
    private int mMode;
    private android.database.sqlite.SQLiteOpenHelper mOpenHelper;
    private java.lang.String mSuggestSuggestionClause;
    private java.lang.String[] mSuggestionProjection;
    private android.net.Uri mSuggestionsUri;
    private boolean mTwoLineDisplay;
    private android.content.UriMatcher mUriMatcher;

    private static class DatabaseHelper extends android.database.sqlite.SQLiteOpenHelper {
        private int mNewVersion;

        public DatabaseHelper(android.content.Context context, int i) {
            super(context, android.content.SearchRecentSuggestionsProvider.sDatabaseName, (android.database.sqlite.SQLiteDatabase.CursorFactory) null, i);
            this.mNewVersion = i;
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(android.database.sqlite.SQLiteDatabase sQLiteDatabase) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("CREATE TABLE suggestions (_id INTEGER PRIMARY KEY,display1 TEXT UNIQUE ON CONFLICT REPLACE");
            if ((this.mNewVersion & 2) != 0) {
                sb.append(",display2 TEXT");
            }
            sb.append(",query TEXT,date LONG);");
            sQLiteDatabase.execSQL(sb.toString());
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(android.database.sqlite.SQLiteDatabase sQLiteDatabase, int i, int i2) {
            android.util.Log.w(android.content.SearchRecentSuggestionsProvider.TAG, "Upgrading database from version " + i + " to " + i2 + ", which will destroy all old data");
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS suggestions");
            onCreate(sQLiteDatabase);
        }
    }

    protected void setupSuggestions(java.lang.String str, int i) {
        if (android.text.TextUtils.isEmpty(str) || (i & 1) == 0) {
            throw new java.lang.IllegalArgumentException();
        }
        this.mTwoLineDisplay = (i & 2) != 0;
        this.mAuthority = new java.lang.String(str);
        this.mMode = i;
        this.mSuggestionsUri = android.net.Uri.parse("content://" + this.mAuthority + "/suggestions");
        this.mUriMatcher = new android.content.UriMatcher(-1);
        this.mUriMatcher.addURI(this.mAuthority, android.app.SearchManager.SUGGEST_URI_PATH_QUERY, 1);
        if (this.mTwoLineDisplay) {
            this.mSuggestSuggestionClause = "display1 LIKE ? OR display2 LIKE ?";
            this.mSuggestionProjection = new java.lang.String[]{"0 AS suggest_format", "'android.resource://system/17301578' AS suggest_icon_1", "display1 AS suggest_text_1", "display2 AS suggest_text_2", "query AS suggest_intent_query", "_id"};
        } else {
            this.mSuggestSuggestionClause = "display1 LIKE ?";
            this.mSuggestionProjection = new java.lang.String[]{"0 AS suggest_format", "'android.resource://system/17301578' AS suggest_icon_1", "display1 AS suggest_text_1", "query AS suggest_intent_query", "_id"};
        }
    }

    @Override // android.content.ContentProvider
    public int delete(android.net.Uri uri, java.lang.String str, java.lang.String[] strArr) {
        android.database.sqlite.SQLiteDatabase writableDatabase = this.mOpenHelper.getWritableDatabase();
        if (uri.getPathSegments().size() != 1) {
            throw new java.lang.IllegalArgumentException("Unknown Uri");
        }
        if (uri.getPathSegments().get(0).equals("suggestions")) {
            int delete = writableDatabase.delete("suggestions", str, strArr);
            getContext().getContentResolver().notifyChange(uri, null);
            return delete;
        }
        throw new java.lang.IllegalArgumentException("Unknown Uri");
    }

    @Override // android.content.ContentProvider, android.content.ContentInterface
    public java.lang.String getType(android.net.Uri uri) {
        if (this.mUriMatcher.match(uri) == 1) {
            return android.app.SearchManager.SUGGEST_MIME_TYPE;
        }
        int size = uri.getPathSegments().size();
        if (size >= 1 && uri.getPathSegments().get(0).equals("suggestions")) {
            if (size == 1) {
                return "vnd.android.cursor.dir/suggestion";
            }
            if (size == 2) {
                return "vnd.android.cursor.item/suggestion";
            }
        }
        throw new java.lang.IllegalArgumentException("Unknown Uri");
    }

    @Override // android.content.ContentProvider
    public android.net.Uri insert(android.net.Uri uri, android.content.ContentValues contentValues) {
        long j;
        android.net.Uri uri2;
        android.database.sqlite.SQLiteDatabase writableDatabase = this.mOpenHelper.getWritableDatabase();
        int size = uri.getPathSegments().size();
        if (size < 1) {
            throw new java.lang.IllegalArgumentException("Unknown Uri");
        }
        if (uri.getPathSegments().get(0).equals("suggestions") && size == 1) {
            j = writableDatabase.insert("suggestions", "query", contentValues);
            if (j <= 0) {
                uri2 = null;
            } else {
                uri2 = android.net.Uri.withAppendedPath(this.mSuggestionsUri, java.lang.String.valueOf(j));
            }
        } else {
            j = -1;
            uri2 = null;
        }
        if (j < 0) {
            throw new java.lang.IllegalArgumentException("Unknown Uri");
        }
        getContext().getContentResolver().notifyChange(uri2, null);
        return uri2;
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        if (this.mAuthority == null || this.mMode == 0) {
            throw new java.lang.IllegalArgumentException("Provider not configured");
        }
        this.mOpenHelper = new android.content.SearchRecentSuggestionsProvider.DatabaseHelper(getContext(), this.mMode + 512);
        return true;
    }

    @Override // android.content.ContentProvider
    public android.database.Cursor query(android.net.Uri uri, java.lang.String[] strArr, java.lang.String str, java.lang.String[] strArr2, java.lang.String str2) {
        java.lang.String[] strArr3;
        java.lang.String[] strArr4;
        java.lang.String str3;
        java.lang.String[] strArr5;
        android.database.sqlite.SQLiteDatabase readableDatabase = this.mOpenHelper.getReadableDatabase();
        if (this.mUriMatcher.match(uri) == 1) {
            if (android.text.TextUtils.isEmpty(strArr2[0])) {
                str3 = null;
                strArr5 = null;
            } else {
                java.lang.String str4 = "%" + strArr2[0] + "%";
                if (this.mTwoLineDisplay) {
                    strArr4 = new java.lang.String[]{str4, str4};
                } else {
                    strArr4 = new java.lang.String[]{str4};
                }
                str3 = this.mSuggestSuggestionClause;
                strArr5 = strArr4;
            }
            android.database.Cursor query = readableDatabase.query("suggestions", this.mSuggestionProjection, str3, strArr5, null, null, "date DESC", null);
            query.setNotificationUri(getContext().getContentResolver(), uri);
            return query;
        }
        int size = uri.getPathSegments().size();
        if (size != 1 && size != 2) {
            throw new java.lang.IllegalArgumentException("Unknown Uri");
        }
        java.lang.String str5 = uri.getPathSegments().get(0);
        if (!str5.equals("suggestions")) {
            throw new java.lang.IllegalArgumentException("Unknown Uri");
        }
        if (strArr != null && strArr.length > 0) {
            java.lang.String[] strArr6 = new java.lang.String[strArr.length + 1];
            java.lang.System.arraycopy(strArr, 0, strArr6, 0, strArr.length);
            strArr6[strArr.length] = "_id AS _id";
            strArr3 = strArr6;
        } else {
            strArr3 = null;
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder(256);
        if (size == 2) {
            sb.append("(_id = ").append(uri.getPathSegments().get(1)).append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        if (str != null && str.length() > 0) {
            if (sb.length() > 0) {
                sb.append(" AND ");
            }
            sb.append('(');
            sb.append(str);
            sb.append(')');
        }
        android.database.Cursor query2 = readableDatabase.query(str5, strArr3, sb.toString(), strArr2, null, null, str2, null);
        query2.setNotificationUri(getContext().getContentResolver(), uri);
        return query2;
    }

    @Override // android.content.ContentProvider
    public int update(android.net.Uri uri, android.content.ContentValues contentValues, java.lang.String str, java.lang.String[] strArr) {
        throw new java.lang.UnsupportedOperationException("Not implemented");
    }
}
