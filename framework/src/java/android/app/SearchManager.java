package android.app;

/* loaded from: classes.dex */
public class SearchManager implements android.content.DialogInterface.OnDismissListener, android.content.DialogInterface.OnCancelListener {
    public static final java.lang.String ACTION_KEY = "action_key";
    public static final java.lang.String ACTION_MSG = "action_msg";
    public static final java.lang.String APP_DATA = "app_data";
    public static final java.lang.String CONTEXT_IS_VOICE = "android.search.CONTEXT_IS_VOICE";
    public static final java.lang.String CURSOR_EXTRA_KEY_IN_PROGRESS = "in_progress";
    private static final boolean DBG = false;
    public static final java.lang.String DISABLE_VOICE_SEARCH = "android.search.DISABLE_VOICE_SEARCH";
    public static final java.lang.String EXTRA_DATA_KEY = "intent_extra_data_key";
    public static final java.lang.String EXTRA_NEW_SEARCH = "new_search";
    public static final java.lang.String EXTRA_SELECT_QUERY = "select_query";
    public static final java.lang.String EXTRA_WEB_SEARCH_PENDINGINTENT = "web_search_pendingintent";
    public static final int FLAG_QUERY_REFINEMENT = 1;
    public static final java.lang.String INTENT_ACTION_GLOBAL_SEARCH = "android.search.action.GLOBAL_SEARCH";
    public static final java.lang.String INTENT_ACTION_SEARCHABLES_CHANGED = "android.search.action.SEARCHABLES_CHANGED";
    public static final java.lang.String INTENT_ACTION_SEARCH_SETTINGS = "android.search.action.SEARCH_SETTINGS";
    public static final java.lang.String INTENT_ACTION_SEARCH_SETTINGS_CHANGED = "android.search.action.SETTINGS_CHANGED";
    public static final java.lang.String INTENT_ACTION_WEB_SEARCH_SETTINGS = "android.search.action.WEB_SEARCH_SETTINGS";
    public static final java.lang.String INTENT_GLOBAL_SEARCH_ACTIVITY_CHANGED = "android.search.action.GLOBAL_SEARCH_ACTIVITY_CHANGED";
    public static final char MENU_KEY = 's';
    public static final int MENU_KEYCODE = 47;
    public static final java.lang.String QUERY = "query";
    public static final java.lang.String SEARCH_MODE = "search_mode";
    public static final java.lang.String SHORTCUT_MIME_TYPE = "vnd.android.cursor.item/vnd.android.search.suggest";
    public static final java.lang.String SUGGEST_COLUMN_AUDIO_CHANNEL_CONFIG = "suggest_audio_channel_config";
    public static final java.lang.String SUGGEST_COLUMN_CONTENT_TYPE = "suggest_content_type";
    public static final java.lang.String SUGGEST_COLUMN_DURATION = "suggest_duration";
    public static final java.lang.String SUGGEST_COLUMN_FLAGS = "suggest_flags";
    public static final java.lang.String SUGGEST_COLUMN_FORMAT = "suggest_format";
    public static final java.lang.String SUGGEST_COLUMN_ICON_1 = "suggest_icon_1";
    public static final java.lang.String SUGGEST_COLUMN_ICON_2 = "suggest_icon_2";
    public static final java.lang.String SUGGEST_COLUMN_INTENT_ACTION = "suggest_intent_action";
    public static final java.lang.String SUGGEST_COLUMN_INTENT_DATA = "suggest_intent_data";
    public static final java.lang.String SUGGEST_COLUMN_INTENT_DATA_ID = "suggest_intent_data_id";
    public static final java.lang.String SUGGEST_COLUMN_INTENT_EXTRA_DATA = "suggest_intent_extra_data";
    public static final java.lang.String SUGGEST_COLUMN_IS_LIVE = "suggest_is_live";
    public static final java.lang.String SUGGEST_COLUMN_LAST_ACCESS_HINT = "suggest_last_access_hint";
    public static final java.lang.String SUGGEST_COLUMN_PRODUCTION_YEAR = "suggest_production_year";
    public static final java.lang.String SUGGEST_COLUMN_PURCHASE_PRICE = "suggest_purchase_price";
    public static final java.lang.String SUGGEST_COLUMN_QUERY = "suggest_intent_query";
    public static final java.lang.String SUGGEST_COLUMN_RATING_SCORE = "suggest_rating_score";
    public static final java.lang.String SUGGEST_COLUMN_RATING_STYLE = "suggest_rating_style";
    public static final java.lang.String SUGGEST_COLUMN_RENTAL_PRICE = "suggest_rental_price";
    public static final java.lang.String SUGGEST_COLUMN_RESULT_CARD_IMAGE = "suggest_result_card_image";
    public static final java.lang.String SUGGEST_COLUMN_SHORTCUT_ID = "suggest_shortcut_id";
    public static final java.lang.String SUGGEST_COLUMN_SPINNER_WHILE_REFRESHING = "suggest_spinner_while_refreshing";
    public static final java.lang.String SUGGEST_COLUMN_TEXT_1 = "suggest_text_1";
    public static final java.lang.String SUGGEST_COLUMN_TEXT_2 = "suggest_text_2";
    public static final java.lang.String SUGGEST_COLUMN_TEXT_2_URL = "suggest_text_2_url";
    public static final java.lang.String SUGGEST_COLUMN_VIDEO_HEIGHT = "suggest_video_height";
    public static final java.lang.String SUGGEST_COLUMN_VIDEO_WIDTH = "suggest_video_width";
    public static final java.lang.String SUGGEST_MIME_TYPE = "vnd.android.cursor.dir/vnd.android.search.suggest";
    public static final java.lang.String SUGGEST_NEVER_MAKE_SHORTCUT = "_-1";
    public static final java.lang.String SUGGEST_PARAMETER_LIMIT = "limit";
    public static final java.lang.String SUGGEST_URI_PATH_QUERY = "search_suggest_query";
    public static final java.lang.String SUGGEST_URI_PATH_SHORTCUT = "search_suggest_shortcut";
    private static final java.lang.String TAG = "SearchManager";
    public static final java.lang.String USER_QUERY = "user_query";
    private final android.content.Context mContext;
    final android.os.Handler mHandler;
    private android.app.SearchDialog mSearchDialog;
    android.app.SearchManager.OnDismissListener mDismissListener = null;
    android.app.SearchManager.OnCancelListener mCancelListener = null;
    private final android.app.ISearchManager mService = android.app.ISearchManager.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow("search"));

    public interface OnCancelListener {
        void onCancel();
    }

    public interface OnDismissListener {
        void onDismiss();
    }

    SearchManager(android.content.Context context, android.os.Handler handler) throws android.os.ServiceManager.ServiceNotFoundException {
        this.mContext = context;
        this.mHandler = handler;
    }

    public void startSearch(java.lang.String str, boolean z, android.content.ComponentName componentName, android.os.Bundle bundle, boolean z2) {
        startSearch(str, z, componentName, bundle, z2, null);
    }

    public void startSearch(java.lang.String str, boolean z, android.content.ComponentName componentName, android.os.Bundle bundle, boolean z2, android.graphics.Rect rect) {
        if (z2) {
            startGlobalSearch(str, z, bundle, rect);
        } else if (((android.app.UiModeManager) this.mContext.getSystemService(android.app.UiModeManager.class)).getCurrentModeType() != 4) {
            ensureSearchDialog();
            this.mSearchDialog.show(str, z, componentName, bundle);
        }
    }

    private void ensureSearchDialog() {
        if (this.mSearchDialog == null) {
            this.mSearchDialog = new android.app.SearchDialog(this.mContext, this);
            this.mSearchDialog.setOnCancelListener(this);
            this.mSearchDialog.setOnDismissListener(this);
        }
    }

    void startGlobalSearch(java.lang.String str, boolean z, android.os.Bundle bundle, android.graphics.Rect rect) {
        android.os.Bundle bundle2;
        android.content.ComponentName globalSearchActivity = getGlobalSearchActivity();
        if (globalSearchActivity == null) {
            android.util.Log.w(TAG, "No global search activity found.");
            return;
        }
        android.content.Intent intent = new android.content.Intent(INTENT_ACTION_GLOBAL_SEARCH);
        intent.addFlags(268435456);
        intent.setComponent(globalSearchActivity);
        if (bundle == null) {
            bundle2 = new android.os.Bundle();
        } else {
            bundle2 = new android.os.Bundle(bundle);
        }
        if (!bundle2.containsKey(android.app.slice.Slice.SUBTYPE_SOURCE)) {
            bundle2.putString(android.app.slice.Slice.SUBTYPE_SOURCE, this.mContext.getPackageName());
        }
        intent.putExtra(APP_DATA, bundle2);
        if (!android.text.TextUtils.isEmpty(str)) {
            intent.putExtra("query", str);
        }
        if (z) {
            intent.putExtra(EXTRA_SELECT_QUERY, z);
        }
        intent.setSourceBounds(rect);
        try {
            this.mContext.startActivity(intent);
        } catch (android.content.ActivityNotFoundException e) {
            android.util.Log.e(TAG, "Global search activity not found: " + globalSearchActivity);
        }
    }

    public java.util.List<android.content.pm.ResolveInfo> getGlobalSearchActivities() {
        try {
            return this.mService.getGlobalSearchActivities();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.content.ComponentName getGlobalSearchActivity() {
        try {
            return this.mService.getGlobalSearchActivity();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.content.ComponentName getWebSearchActivity() {
        try {
            return this.mService.getWebSearchActivity();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void triggerSearch(java.lang.String str, android.content.ComponentName componentName, android.os.Bundle bundle) {
        if (str == null || android.text.TextUtils.getTrimmedLength(str) == 0) {
            android.util.Log.w(TAG, "triggerSearch called with empty query, ignoring.");
        } else {
            startSearch(str, false, componentName, bundle, false);
            this.mSearchDialog.launchQuerySearch();
        }
    }

    public void stopSearch() {
        if (this.mSearchDialog != null) {
            this.mSearchDialog.cancel();
        }
    }

    public boolean isVisible() {
        if (this.mSearchDialog == null) {
            return false;
        }
        return this.mSearchDialog.isShowing();
    }

    public void setOnDismissListener(android.app.SearchManager.OnDismissListener onDismissListener) {
        this.mDismissListener = onDismissListener;
    }

    public void setOnCancelListener(android.app.SearchManager.OnCancelListener onCancelListener) {
        this.mCancelListener = onCancelListener;
    }

    @Override // android.content.DialogInterface.OnCancelListener
    @java.lang.Deprecated
    public void onCancel(android.content.DialogInterface dialogInterface) {
        if (this.mCancelListener != null) {
            this.mCancelListener.onCancel();
        }
    }

    @Override // android.content.DialogInterface.OnDismissListener
    @java.lang.Deprecated
    public void onDismiss(android.content.DialogInterface dialogInterface) {
        if (this.mDismissListener != null) {
            this.mDismissListener.onDismiss();
        }
    }

    public android.app.SearchableInfo getSearchableInfo(android.content.ComponentName componentName) {
        try {
            return this.mService.getSearchableInfo(componentName);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.database.Cursor getSuggestions(android.app.SearchableInfo searchableInfo, java.lang.String str) {
        return getSuggestions(searchableInfo, str, -1);
    }

    public android.database.Cursor getSuggestions(android.app.SearchableInfo searchableInfo, java.lang.String str, int i) {
        java.lang.String suggestAuthority;
        java.lang.String[] strArr;
        if (searchableInfo == null || (suggestAuthority = searchableInfo.getSuggestAuthority()) == null) {
            return null;
        }
        android.net.Uri.Builder fragment = new android.net.Uri.Builder().scheme("content").authority(suggestAuthority).query("").fragment("");
        java.lang.String suggestPath = searchableInfo.getSuggestPath();
        if (suggestPath != null) {
            fragment.appendEncodedPath(suggestPath);
        }
        fragment.appendPath(SUGGEST_URI_PATH_QUERY);
        java.lang.String suggestSelection = searchableInfo.getSuggestSelection();
        if (suggestSelection != null) {
            strArr = new java.lang.String[]{str};
        } else {
            fragment.appendPath(str);
            strArr = null;
        }
        if (i > 0) {
            fragment.appendQueryParameter("limit", java.lang.String.valueOf(i));
        }
        return this.mContext.getContentResolver().query(fragment.build(), null, suggestSelection, strArr, null);
    }

    public java.util.List<android.app.SearchableInfo> getSearchablesInGlobalSearch() {
        try {
            return this.mService.getSearchablesInGlobalSearch();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.content.Intent getAssistIntent(boolean z) {
        android.os.Bundle assistContextExtras;
        try {
            android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_ASSIST);
            if (z && (assistContextExtras = android.app.ActivityTaskManager.getService().getAssistContextExtras(0)) != null) {
                intent.replaceExtras(assistContextExtras);
            }
            return intent;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void launchAssist(android.os.Bundle bundle) {
        try {
            if (this.mService == null) {
                return;
            }
            this.mService.launchAssist(this.mContext.getUserId(), bundle);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
