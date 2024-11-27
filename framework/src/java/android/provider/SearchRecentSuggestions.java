package android.provider;

/* loaded from: classes3.dex */
public class SearchRecentSuggestions {
    private static final java.lang.String LOG_TAG = "SearchSuggestions";
    private static final int MAX_HISTORY_COUNT = 250;
    public static final int QUERIES_PROJECTION_DATE_INDEX = 1;
    public static final int QUERIES_PROJECTION_DISPLAY1_INDEX = 3;
    public static final int QUERIES_PROJECTION_DISPLAY2_INDEX = 4;
    public static final int QUERIES_PROJECTION_QUERY_INDEX = 2;
    private final java.lang.String mAuthority;
    private final android.content.Context mContext;
    private final android.net.Uri mSuggestionsUri;
    private final boolean mTwoLineDisplay;
    public static final java.lang.String[] QUERIES_PROJECTION_1LINE = {"_id", "date", "query", android.provider.SearchRecentSuggestions.SuggestionColumns.DISPLAY1};
    public static final java.lang.String[] QUERIES_PROJECTION_2LINE = {"_id", "date", "query", android.provider.SearchRecentSuggestions.SuggestionColumns.DISPLAY1, android.provider.SearchRecentSuggestions.SuggestionColumns.DISPLAY2};
    private static final java.util.concurrent.Semaphore sWritesInProgress = new java.util.concurrent.Semaphore(0);

    private static class SuggestionColumns implements android.provider.BaseColumns {
        public static final java.lang.String DATE = "date";
        public static final java.lang.String DISPLAY1 = "display1";
        public static final java.lang.String DISPLAY2 = "display2";
        public static final java.lang.String QUERY = "query";

        private SuggestionColumns() {
        }
    }

    public SearchRecentSuggestions(android.content.Context context, java.lang.String str, int i) {
        if (android.text.TextUtils.isEmpty(str) || (i & 1) == 0) {
            throw new java.lang.IllegalArgumentException();
        }
        this.mTwoLineDisplay = (i & 2) != 0;
        this.mContext = context;
        this.mAuthority = new java.lang.String(str);
        this.mSuggestionsUri = android.net.Uri.parse("content://" + this.mAuthority + "/suggestions");
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [android.provider.SearchRecentSuggestions$1] */
    public void saveRecentQuery(final java.lang.String str, final java.lang.String str2) {
        if (android.text.TextUtils.isEmpty(str)) {
            return;
        }
        if (!this.mTwoLineDisplay && !android.text.TextUtils.isEmpty(str2)) {
            throw new java.lang.IllegalArgumentException();
        }
        new java.lang.Thread("saveRecentQuery") { // from class: android.provider.SearchRecentSuggestions.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                android.provider.SearchRecentSuggestions.this.saveRecentQueryBlocking(str, str2);
                android.provider.SearchRecentSuggestions.sWritesInProgress.release();
            }
        }.start();
    }

    void waitForSave() {
        do {
            sWritesInProgress.acquireUninterruptibly();
        } while (sWritesInProgress.availablePermits() > 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveRecentQueryBlocking(java.lang.String str, java.lang.String str2) {
        android.content.ContentResolver contentResolver = this.mContext.getContentResolver();
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        try {
            android.content.ContentValues contentValues = new android.content.ContentValues();
            contentValues.put(android.provider.SearchRecentSuggestions.SuggestionColumns.DISPLAY1, str);
            if (this.mTwoLineDisplay) {
                contentValues.put(android.provider.SearchRecentSuggestions.SuggestionColumns.DISPLAY2, str2);
            }
            contentValues.put("query", str);
            contentValues.put("date", java.lang.Long.valueOf(currentTimeMillis));
            contentResolver.insert(this.mSuggestionsUri, contentValues);
        } catch (java.lang.RuntimeException e) {
            android.util.Log.e(LOG_TAG, "saveRecentQuery", e);
        }
        truncateHistory(contentResolver, 250);
    }

    public void clearHistory() {
        truncateHistory(this.mContext.getContentResolver(), 0);
    }

    protected void truncateHistory(android.content.ContentResolver contentResolver, int i) {
        java.lang.String str;
        if (i < 0) {
            throw new java.lang.IllegalArgumentException();
        }
        if (i <= 0) {
            str = null;
        } else {
            try {
                str = "_id IN (SELECT _id FROM suggestions ORDER BY date DESC LIMIT -1 OFFSET " + java.lang.String.valueOf(i) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            } catch (java.lang.RuntimeException e) {
                android.util.Log.e(LOG_TAG, "truncateHistory", e);
                return;
            }
        }
        contentResolver.delete(this.mSuggestionsUri, str, null);
    }
}
