package android.widget;

/* loaded from: classes4.dex */
class SuggestionsAdapter extends android.widget.ResourceCursorAdapter implements android.view.View.OnClickListener {
    private static final boolean DBG = false;
    private static final long DELETE_KEY_POST_DELAY = 500;
    static final int INVALID_INDEX = -1;
    private static final java.lang.String LOG_TAG = "SuggestionsAdapter";
    private static final int QUERY_LIMIT = 50;
    static final int REFINE_ALL = 2;
    static final int REFINE_BY_ENTRY = 1;
    static final int REFINE_NONE = 0;
    private boolean mClosed;
    private final int mCommitIconResId;
    private int mFlagsCol;
    private int mIconName1Col;
    private int mIconName2Col;
    private final java.util.WeakHashMap<java.lang.String, android.graphics.drawable.Drawable.ConstantState> mOutsideDrawablesCache;
    private final android.content.Context mProviderContext;
    private int mQueryRefinement;
    private final android.app.SearchManager mSearchManager;
    private final android.widget.SearchView mSearchView;
    private final android.app.SearchableInfo mSearchable;
    private int mText1Col;
    private int mText2Col;
    private int mText2UrlCol;
    private android.content.res.ColorStateList mUrlColor;

    public SuggestionsAdapter(android.content.Context context, android.widget.SearchView searchView, android.app.SearchableInfo searchableInfo, java.util.WeakHashMap<java.lang.String, android.graphics.drawable.Drawable.ConstantState> weakHashMap) {
        super(context, searchView.getSuggestionRowLayout(), (android.database.Cursor) null, true);
        this.mClosed = false;
        this.mQueryRefinement = 1;
        this.mText1Col = -1;
        this.mText2Col = -1;
        this.mText2UrlCol = -1;
        this.mIconName1Col = -1;
        this.mIconName2Col = -1;
        this.mFlagsCol = -1;
        this.mSearchManager = (android.app.SearchManager) this.mContext.getSystemService("search");
        this.mSearchView = searchView;
        this.mSearchable = searchableInfo;
        this.mCommitIconResId = searchView.getSuggestionCommitIconResId();
        this.mProviderContext = this.mSearchable.getProviderContext(this.mContext, this.mSearchable.getActivityContext(this.mContext));
        this.mOutsideDrawablesCache = weakHashMap;
        getFilter().setDelayer(new android.widget.Filter.Delayer() { // from class: android.widget.SuggestionsAdapter.1
            private int mPreviousLength = 0;

            @Override // android.widget.Filter.Delayer
            public long getPostingDelay(java.lang.CharSequence charSequence) {
                if (charSequence == null) {
                    return 0L;
                }
                long j = charSequence.length() < this.mPreviousLength ? android.widget.SuggestionsAdapter.DELETE_KEY_POST_DELAY : 0L;
                this.mPreviousLength = charSequence.length();
                return j;
            }
        });
    }

    public void setQueryRefinement(int i) {
        this.mQueryRefinement = i;
    }

    public int getQueryRefinement() {
        return this.mQueryRefinement;
    }

    @Override // android.widget.CursorAdapter, android.widget.BaseAdapter, android.widget.Adapter
    public boolean hasStableIds() {
        return false;
    }

    @Override // android.widget.CursorAdapter, android.widget.CursorFilter.CursorFilterClient
    public android.database.Cursor runQueryOnBackgroundThread(java.lang.CharSequence charSequence) {
        java.lang.String charSequence2 = charSequence == null ? "" : charSequence.toString();
        if (this.mSearchView.getVisibility() != 0 || this.mSearchView.getWindowVisibility() != 0) {
            return null;
        }
        try {
            android.database.Cursor suggestions = this.mSearchManager.getSuggestions(this.mSearchable, charSequence2, 50);
            if (suggestions != null) {
                suggestions.getCount();
                return suggestions;
            }
        } catch (java.lang.RuntimeException e) {
            android.util.Log.w(LOG_TAG, "Search suggestions query threw an exception.", e);
        }
        return null;
    }

    public void close() {
        changeCursor(null);
        this.mClosed = true;
    }

    @Override // android.widget.BaseAdapter
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        updateSpinnerState(getCursor());
    }

    @Override // android.widget.BaseAdapter
    public void notifyDataSetInvalidated() {
        super.notifyDataSetInvalidated();
        updateSpinnerState(getCursor());
    }

    private void updateSpinnerState(android.database.Cursor cursor) {
        android.os.Bundle extras = cursor != null ? cursor.getExtras() : null;
        if (extras != null) {
            extras.getBoolean(android.app.SearchManager.CURSOR_EXTRA_KEY_IN_PROGRESS);
        }
    }

    @Override // android.widget.CursorAdapter, android.widget.CursorFilter.CursorFilterClient
    public void changeCursor(android.database.Cursor cursor) {
        if (this.mClosed) {
            android.util.Log.w(LOG_TAG, "Tried to change cursor after adapter was closed.");
            if (cursor != null) {
                cursor.close();
                return;
            }
            return;
        }
        try {
            super.changeCursor(cursor);
            if (cursor != null) {
                this.mText1Col = cursor.getColumnIndex(android.app.SearchManager.SUGGEST_COLUMN_TEXT_1);
                this.mText2Col = cursor.getColumnIndex(android.app.SearchManager.SUGGEST_COLUMN_TEXT_2);
                this.mText2UrlCol = cursor.getColumnIndex(android.app.SearchManager.SUGGEST_COLUMN_TEXT_2_URL);
                this.mIconName1Col = cursor.getColumnIndex(android.app.SearchManager.SUGGEST_COLUMN_ICON_1);
                this.mIconName2Col = cursor.getColumnIndex(android.app.SearchManager.SUGGEST_COLUMN_ICON_2);
                this.mFlagsCol = cursor.getColumnIndex(android.app.SearchManager.SUGGEST_COLUMN_FLAGS);
            }
        } catch (java.lang.Exception e) {
            android.util.Log.e(LOG_TAG, "error changing cursor and caching columns", e);
        }
    }

    @Override // android.widget.ResourceCursorAdapter, android.widget.CursorAdapter
    public android.view.View newView(android.content.Context context, android.database.Cursor cursor, android.view.ViewGroup viewGroup) {
        android.view.View newView = super.newView(context, cursor, viewGroup);
        newView.setTag(new android.widget.SuggestionsAdapter.ChildViewCache(newView));
        ((android.widget.ImageView) newView.findViewById(com.android.internal.R.id.edit_query)).setImageResource(this.mCommitIconResId);
        return newView;
    }

    private static final class ChildViewCache {
        public final android.widget.ImageView mIcon1;
        public final android.widget.ImageView mIcon2;
        public final android.widget.ImageView mIconRefine;
        public final android.widget.TextView mText1;
        public final android.widget.TextView mText2;

        public ChildViewCache(android.view.View view) {
            this.mText1 = (android.widget.TextView) view.findViewById(16908308);
            this.mText2 = (android.widget.TextView) view.findViewById(16908309);
            this.mIcon1 = (android.widget.ImageView) view.findViewById(16908295);
            this.mIcon2 = (android.widget.ImageView) view.findViewById(16908296);
            this.mIconRefine = (android.widget.ImageView) view.findViewById(com.android.internal.R.id.edit_query);
        }
    }

    @Override // android.widget.CursorAdapter
    public void bindView(android.view.View view, android.content.Context context, android.database.Cursor cursor) {
        int i;
        java.lang.CharSequence stringOrNull;
        android.widget.SuggestionsAdapter.ChildViewCache childViewCache = (android.widget.SuggestionsAdapter.ChildViewCache) view.getTag();
        if (this.mFlagsCol == -1) {
            i = 0;
        } else {
            i = cursor.getInt(this.mFlagsCol);
        }
        if (childViewCache.mText1 != null) {
            setViewText(childViewCache.mText1, getStringOrNull(cursor, this.mText1Col));
        }
        if (childViewCache.mText2 != null) {
            java.lang.String stringOrNull2 = getStringOrNull(cursor, this.mText2UrlCol);
            if (stringOrNull2 != null) {
                stringOrNull = formatUrl(context, stringOrNull2);
            } else {
                stringOrNull = getStringOrNull(cursor, this.mText2Col);
            }
            if (android.text.TextUtils.isEmpty(stringOrNull)) {
                if (childViewCache.mText1 != null) {
                    childViewCache.mText1.setSingleLine(false);
                    childViewCache.mText1.setMaxLines(2);
                }
            } else if (childViewCache.mText1 != null) {
                childViewCache.mText1.setSingleLine(true);
                childViewCache.mText1.setMaxLines(1);
            }
            setViewText(childViewCache.mText2, stringOrNull);
        }
        if (childViewCache.mIcon1 != null) {
            setViewDrawable(childViewCache.mIcon1, getIcon1(cursor), 4);
        }
        if (childViewCache.mIcon2 != null) {
            setViewDrawable(childViewCache.mIcon2, getIcon2(cursor), 8);
        }
        if (this.mQueryRefinement == 2 || (this.mQueryRefinement == 1 && (i & 1) != 0)) {
            childViewCache.mIconRefine.setVisibility(0);
            childViewCache.mIconRefine.setTag(childViewCache.mText1.getText());
            childViewCache.mIconRefine.setOnClickListener(this);
            return;
        }
        childViewCache.mIconRefine.setVisibility(8);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(android.view.View view) {
        java.lang.Object tag = view.getTag();
        if (tag instanceof java.lang.CharSequence) {
            this.mSearchView.onQueryRefine((java.lang.CharSequence) tag);
        }
    }

    private java.lang.CharSequence formatUrl(android.content.Context context, java.lang.CharSequence charSequence) {
        if (this.mUrlColor == null) {
            android.util.TypedValue typedValue = new android.util.TypedValue();
            context.getTheme().resolveAttribute(com.android.internal.R.attr.textColorSearchUrl, typedValue, true);
            this.mUrlColor = context.getColorStateList(typedValue.resourceId);
        }
        android.text.SpannableString spannableString = new android.text.SpannableString(charSequence);
        spannableString.setSpan(new android.text.style.TextAppearanceSpan(null, 0, 0, this.mUrlColor, null), 0, charSequence.length(), 33);
        return spannableString;
    }

    private void setViewText(android.widget.TextView textView, java.lang.CharSequence charSequence) {
        textView.lambda$setTextAsync$0(charSequence);
        if (android.text.TextUtils.isEmpty(charSequence)) {
            textView.setVisibility(8);
        } else {
            textView.setVisibility(0);
        }
    }

    private android.graphics.drawable.Drawable getIcon1(android.database.Cursor cursor) {
        if (this.mIconName1Col == -1) {
            return null;
        }
        android.graphics.drawable.Drawable drawableFromResourceValue = getDrawableFromResourceValue(cursor.getString(this.mIconName1Col));
        if (drawableFromResourceValue != null) {
            return drawableFromResourceValue;
        }
        return getDefaultIcon1(cursor);
    }

    private android.graphics.drawable.Drawable getIcon2(android.database.Cursor cursor) {
        if (this.mIconName2Col == -1) {
            return null;
        }
        return getDrawableFromResourceValue(cursor.getString(this.mIconName2Col));
    }

    private void setViewDrawable(android.widget.ImageView imageView, android.graphics.drawable.Drawable drawable, int i) {
        imageView.lambda$setImageURIAsync$2(drawable);
        if (drawable == null) {
            imageView.setVisibility(i);
            return;
        }
        imageView.setVisibility(0);
        drawable.setVisible(false, false);
        drawable.setVisible(true, false);
    }

    @Override // android.widget.CursorAdapter, android.widget.CursorFilter.CursorFilterClient
    public java.lang.CharSequence convertToString(android.database.Cursor cursor) {
        java.lang.String columnString;
        java.lang.String columnString2;
        if (cursor == null) {
            return null;
        }
        java.lang.String columnString3 = getColumnString(cursor, android.app.SearchManager.SUGGEST_COLUMN_QUERY);
        if (columnString3 != null) {
            return columnString3;
        }
        if (this.mSearchable.shouldRewriteQueryFromData() && (columnString2 = getColumnString(cursor, android.app.SearchManager.SUGGEST_COLUMN_INTENT_DATA)) != null) {
            return columnString2;
        }
        if (!this.mSearchable.shouldRewriteQueryFromText() || (columnString = getColumnString(cursor, android.app.SearchManager.SUGGEST_COLUMN_TEXT_1)) == null) {
            return null;
        }
        return columnString;
    }

    @Override // android.widget.CursorAdapter, android.widget.Adapter
    public android.view.View getView(int i, android.view.View view, android.view.ViewGroup viewGroup) {
        try {
            return super.getView(i, view, viewGroup);
        } catch (java.lang.RuntimeException e) {
            android.util.Log.w(LOG_TAG, "Search suggestions cursor threw exception.", e);
            android.view.View newView = newView(this.mContext, this.mCursor, viewGroup);
            if (newView != null) {
                ((android.widget.SuggestionsAdapter.ChildViewCache) newView.getTag()).mText1.lambda$setTextAsync$0(e.toString());
            }
            return newView;
        }
    }

    @Override // android.widget.CursorAdapter, android.widget.BaseAdapter, android.widget.SpinnerAdapter
    public android.view.View getDropDownView(int i, android.view.View view, android.view.ViewGroup viewGroup) {
        try {
            return super.getDropDownView(i, view, viewGroup);
        } catch (java.lang.RuntimeException e) {
            android.util.Log.w(LOG_TAG, "Search suggestions cursor threw exception.", e);
            android.view.View newDropDownView = newDropDownView(this.mDropDownContext == null ? this.mContext : this.mDropDownContext, this.mCursor, viewGroup);
            if (newDropDownView != null) {
                ((android.widget.SuggestionsAdapter.ChildViewCache) newDropDownView.getTag()).mText1.lambda$setTextAsync$0(e.toString());
            }
            return newDropDownView;
        }
    }

    private android.graphics.drawable.Drawable getDrawableFromResourceValue(java.lang.String str) {
        if (str == null || str.length() == 0 || android.media.AudioSystem.LEGACY_REMOTE_SUBMIX_ADDRESS.equals(str)) {
            return null;
        }
        try {
            int parseInt = java.lang.Integer.parseInt(str);
            java.lang.String str2 = "android.resource://" + this.mProviderContext.getPackageName() + "/" + parseInt;
            android.graphics.drawable.Drawable checkIconCache = checkIconCache(str2);
            if (checkIconCache != null) {
                return checkIconCache;
            }
            android.graphics.drawable.Drawable drawable = this.mProviderContext.getDrawable(parseInt);
            storeInIconCache(str2, drawable);
            return drawable;
        } catch (android.content.res.Resources.NotFoundException e) {
            android.util.Log.w(LOG_TAG, "Icon resource not found: " + str);
            return null;
        } catch (java.lang.NumberFormatException e2) {
            android.graphics.drawable.Drawable checkIconCache2 = checkIconCache(str);
            if (checkIconCache2 != null) {
                return checkIconCache2;
            }
            android.graphics.drawable.Drawable drawable2 = getDrawable(android.net.Uri.parse(str));
            storeInIconCache(str, drawable2);
            return drawable2;
        }
    }

    private android.graphics.drawable.Drawable getDrawable(android.net.Uri uri) {
        try {
            if (android.content.ContentResolver.SCHEME_ANDROID_RESOURCE.equals(uri.getScheme())) {
                android.content.ContentResolver.OpenResourceIdResult resourceId = this.mProviderContext.getContentResolver().getResourceId(uri);
                try {
                    return resourceId.r.getDrawable(resourceId.id, this.mProviderContext.getTheme());
                } catch (android.content.res.Resources.NotFoundException e) {
                    throw new java.io.FileNotFoundException("Resource does not exist: " + uri);
                }
            }
            java.io.InputStream openInputStream = this.mProviderContext.getContentResolver().openInputStream(uri);
            if (openInputStream == null) {
                throw new java.io.FileNotFoundException("Failed to open " + uri);
            }
            try {
                return android.graphics.drawable.Drawable.createFromStream(openInputStream, null);
            } finally {
                try {
                    openInputStream.close();
                } catch (java.io.IOException e2) {
                    android.util.Log.e(LOG_TAG, "Error closing icon stream for " + uri, e2);
                }
            }
        } catch (java.io.FileNotFoundException e3) {
            android.util.Log.w(LOG_TAG, "Icon not found: " + uri + ", " + e3.getMessage());
            return null;
        }
        android.util.Log.w(LOG_TAG, "Icon not found: " + uri + ", " + e3.getMessage());
        return null;
    }

    private android.graphics.drawable.Drawable checkIconCache(java.lang.String str) {
        android.graphics.drawable.Drawable.ConstantState constantState = this.mOutsideDrawablesCache.get(str);
        if (constantState == null) {
            return null;
        }
        return constantState.newDrawable();
    }

    private void storeInIconCache(java.lang.String str, android.graphics.drawable.Drawable drawable) {
        if (drawable != null) {
            this.mOutsideDrawablesCache.put(str, drawable.getConstantState());
        }
    }

    private android.graphics.drawable.Drawable getDefaultIcon1(android.database.Cursor cursor) {
        android.graphics.drawable.Drawable activityIconWithCache = getActivityIconWithCache(this.mSearchable.getSearchActivity());
        if (activityIconWithCache != null) {
            return activityIconWithCache;
        }
        return this.mContext.getPackageManager().getDefaultActivityIcon();
    }

    private android.graphics.drawable.Drawable getActivityIconWithCache(android.content.ComponentName componentName) {
        java.lang.String flattenToShortString = componentName.flattenToShortString();
        if (this.mOutsideDrawablesCache.containsKey(flattenToShortString)) {
            android.graphics.drawable.Drawable.ConstantState constantState = this.mOutsideDrawablesCache.get(flattenToShortString);
            if (constantState == null) {
                return null;
            }
            return constantState.newDrawable(this.mProviderContext.getResources());
        }
        android.graphics.drawable.Drawable activityIcon = getActivityIcon(componentName);
        this.mOutsideDrawablesCache.put(flattenToShortString, activityIcon != null ? activityIcon.getConstantState() : null);
        return activityIcon;
    }

    private android.graphics.drawable.Drawable getActivityIcon(android.content.ComponentName componentName) {
        android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
        try {
            android.content.pm.ActivityInfo activityInfo = packageManager.getActivityInfo(componentName, 128);
            int iconResource = activityInfo.getIconResource();
            if (iconResource == 0) {
                return null;
            }
            android.graphics.drawable.Drawable drawable = packageManager.getDrawable(componentName.getPackageName(), iconResource, activityInfo.applicationInfo);
            if (drawable == null) {
                android.util.Log.w(LOG_TAG, "Invalid icon resource " + iconResource + " for " + componentName.flattenToShortString());
                return null;
            }
            return drawable;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Log.w(LOG_TAG, e.toString());
            return null;
        }
    }

    public static java.lang.String getColumnString(android.database.Cursor cursor, java.lang.String str) {
        return getStringOrNull(cursor, cursor.getColumnIndex(str));
    }

    private static java.lang.String getStringOrNull(android.database.Cursor cursor, int i) {
        if (i == -1) {
            return null;
        }
        try {
            return cursor.getString(i);
        } catch (java.lang.Exception e) {
            android.util.Log.e(LOG_TAG, "unexpected error retrieving valid column from cursor, did the remote process die?", e);
            return null;
        }
    }
}
