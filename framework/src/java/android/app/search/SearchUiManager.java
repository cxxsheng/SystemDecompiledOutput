package android.app.search;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class SearchUiManager {
    private final android.content.Context mContext;

    public SearchUiManager(android.content.Context context) {
        this.mContext = (android.content.Context) java.util.Objects.requireNonNull(context);
    }

    public android.app.search.SearchSession createSearchSession(android.app.search.SearchContext searchContext) {
        return new android.app.search.SearchSession(this.mContext, searchContext);
    }
}
