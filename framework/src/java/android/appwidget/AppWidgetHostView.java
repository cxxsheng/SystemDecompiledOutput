package android.appwidget;

/* loaded from: classes.dex */
public class AppWidgetHostView extends android.widget.FrameLayout implements android.appwidget.AppWidgetHost.AppWidgetHostListener {
    private static final int FIRST_RESOURCE_COLOR_ID = 17170461;
    private static final android.view.LayoutInflater.Filter INFLATER_FILTER = new android.view.LayoutInflater.Filter() { // from class: android.appwidget.AppWidgetHostView$$ExternalSyntheticLambda3
        @Override // android.view.LayoutInflater.Filter
        public final boolean onLoadClass(java.lang.Class cls) {
            boolean isAnnotationPresent;
            isAnnotationPresent = cls.isAnnotationPresent(android.widget.RemoteViews.RemoteView.class);
            return isAnnotationPresent;
        }
    };
    private static final java.lang.String KEY_INFLATION_ID = "inflation_id";
    private static final java.lang.String KEY_JAILED_ARRAY = "jail";
    private static final int LAST_RESOURCE_COLOR_ID = 17170525;
    static final boolean LOGD = false;
    static final java.lang.String TAG = "AppWidgetHostView";
    static final int VIEW_MODE_CONTENT = 1;
    static final int VIEW_MODE_DEFAULT = 3;
    static final int VIEW_MODE_ERROR = 2;
    static final int VIEW_MODE_NOINIT = 0;
    int mAppWidgetId;
    private java.util.concurrent.Executor mAsyncExecutor;
    boolean mColorMappingChanged;
    private android.widget.RemoteViews.ColorResources mColorResources;
    android.content.Context mContext;
    private android.util.SizeF mCurrentSize;
    private long mDelayedRestoredInflationId;
    private android.util.SparseArray<android.os.Parcelable> mDelayedRestoredState;
    android.appwidget.AppWidgetProviderInfo mInfo;
    private android.widget.RemoteViews.InteractionHandler mInteractionHandler;
    private android.os.CancellationSignal mLastExecutionSignal;
    private android.widget.RemoteViews mLastInflatedRemoteViews;
    private long mLastInflatedRemoteViewsId;
    private boolean mOnLightBackground;
    android.content.Context mRemoteContext;
    android.view.View mView;
    int mViewMode;

    public AppWidgetHostView(android.content.Context context) {
        this(context, 17432576, 17432577);
    }

    public AppWidgetHostView(android.content.Context context, android.widget.RemoteViews.InteractionHandler interactionHandler) {
        this(context, 17432576, 17432577);
        this.mInteractionHandler = getHandler(interactionHandler);
    }

    public AppWidgetHostView(android.content.Context context, int i, int i2) {
        super(context);
        this.mViewMode = 0;
        this.mColorMappingChanged = false;
        this.mCurrentSize = null;
        this.mColorResources = null;
        this.mLastInflatedRemoteViews = null;
        this.mLastInflatedRemoteViewsId = -1L;
        this.mContext = context;
        setIsRootNamespace(true);
    }

    public void setInteractionHandler(android.widget.RemoteViews.InteractionHandler interactionHandler) {
        this.mInteractionHandler = getHandler(interactionHandler);
    }

    public static class AdapterChildHostView extends android.appwidget.AppWidgetHostView {
        public AdapterChildHostView(android.content.Context context) {
            super(context);
        }

        @Override // android.appwidget.AppWidgetHostView
        public android.content.Context getRemoteContextEnsuringCorrectCachedApkPath() {
            return null;
        }
    }

    public void setAppWidget(int i, android.appwidget.AppWidgetProviderInfo appWidgetProviderInfo) {
        this.mAppWidgetId = i;
        this.mInfo = appWidgetProviderInfo;
        android.graphics.Rect defaultPadding = getDefaultPadding();
        setPadding(defaultPadding.left, defaultPadding.top, defaultPadding.right, defaultPadding.bottom);
        if (appWidgetProviderInfo != null) {
            java.lang.String loadLabel = appWidgetProviderInfo.loadLabel(getContext().getPackageManager());
            if ((appWidgetProviderInfo.providerInfo.applicationInfo.flags & 1073741824) != 0) {
                loadLabel = android.content.res.Resources.getSystem().getString(com.android.internal.R.string.suspended_widget_accessibility, loadLabel);
            }
            setContentDescription(loadLabel);
        }
    }

    public static android.graphics.Rect getDefaultPaddingForWidget(android.content.Context context, android.content.ComponentName componentName, android.graphics.Rect rect) {
        return getDefaultPaddingForWidget(context, rect);
    }

    private static android.graphics.Rect getDefaultPaddingForWidget(android.content.Context context, android.graphics.Rect rect) {
        if (rect == null) {
            rect = new android.graphics.Rect(0, 0, 0, 0);
        } else {
            rect.set(0, 0, 0, 0);
        }
        android.content.res.Resources resources = context.getResources();
        rect.left = resources.getDimensionPixelSize(com.android.internal.R.dimen.default_app_widget_padding_left);
        rect.right = resources.getDimensionPixelSize(com.android.internal.R.dimen.default_app_widget_padding_right);
        rect.top = resources.getDimensionPixelSize(com.android.internal.R.dimen.default_app_widget_padding_top);
        rect.bottom = resources.getDimensionPixelSize(com.android.internal.R.dimen.default_app_widget_padding_bottom);
        return rect;
    }

    private android.graphics.Rect getDefaultPadding() {
        return getDefaultPaddingForWidget(this.mContext, null);
    }

    public int getAppWidgetId() {
        return this.mAppWidgetId;
    }

    public android.appwidget.AppWidgetProviderInfo getAppWidgetInfo() {
        return this.mInfo;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchSaveInstanceState(android.util.SparseArray<android.os.Parcelable> sparseArray) {
        android.util.SparseArray<android.os.Parcelable> sparseArray2 = new android.util.SparseArray<>();
        super.dispatchSaveInstanceState(sparseArray2);
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putSparseParcelableArray(KEY_JAILED_ARRAY, sparseArray2);
        bundle.putLong(KEY_INFLATION_ID, this.mLastInflatedRemoteViewsId);
        sparseArray.put(generateId(), bundle);
        sparseArray.put(generateId(), bundle);
    }

    private int generateId() {
        int id = getId();
        return id == -1 ? this.mAppWidgetId : id;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchRestoreInstanceState(android.util.SparseArray<android.os.Parcelable> sparseArray) {
        android.util.SparseArray<android.os.Parcelable> sparseArray2;
        android.os.Parcelable parcelable = sparseArray.get(generateId());
        long j = -1;
        if (!(parcelable instanceof android.os.Bundle)) {
            sparseArray2 = null;
        } else {
            android.os.Bundle bundle = (android.os.Bundle) parcelable;
            sparseArray2 = bundle.getSparseParcelableArray(KEY_JAILED_ARRAY);
            j = bundle.getLong(KEY_INFLATION_ID, -1L);
        }
        if (sparseArray2 == null) {
            sparseArray2 = new android.util.SparseArray<>();
        }
        this.mDelayedRestoredState = sparseArray2;
        this.mDelayedRestoredInflationId = j;
        restoreInstanceState();
    }

    void restoreInstanceState() {
        long j = this.mDelayedRestoredInflationId;
        android.util.SparseArray<android.os.Parcelable> sparseArray = this.mDelayedRestoredState;
        if (j == -1 || j != this.mLastInflatedRemoteViewsId) {
            return;
        }
        this.mDelayedRestoredInflationId = -1L;
        this.mDelayedRestoredState = null;
        try {
            super.dispatchRestoreInstanceState(sparseArray);
        } catch (java.lang.Exception e) {
            android.util.Log.e(TAG, "failed to restoreInstanceState for widget id: " + this.mAppWidgetId + ", " + (this.mInfo == null ? "null" : this.mInfo.provider), e);
        }
    }

    private android.util.SizeF computeSizeFromLayout(int i, int i2, int i3, int i4) {
        float f = getResources().getDisplayMetrics().density;
        return new android.util.SizeF((((i3 - i) - getPaddingLeft()) - getPaddingRight()) / f, (((i4 - i2) - getPaddingTop()) - getPaddingBottom()) / f);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        android.widget.RemoteViews remoteViewsToApplyIfDifferent;
        try {
            android.util.SizeF sizeF = this.mCurrentSize;
            android.util.SizeF computeSizeFromLayout = computeSizeFromLayout(i, i2, i3, i4);
            this.mCurrentSize = computeSizeFromLayout;
            if (this.mLastInflatedRemoteViews != null && (remoteViewsToApplyIfDifferent = this.mLastInflatedRemoteViews.getRemoteViewsToApplyIfDifferent(sizeF, computeSizeFromLayout)) != null) {
                applyRemoteViews(remoteViewsToApplyIfDifferent, false);
                measureChildWithMargins(this.mView, android.view.View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824), 0, android.view.View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824), 0);
            }
            super.onLayout(z, i, i2, i3, i4);
        } catch (java.lang.RuntimeException e) {
            android.util.Log.e(TAG, "Remote provider threw runtime exception, using error view instead.", e);
            handleViewError();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleViewError() {
        removeViewInLayout(this.mView);
        android.view.View errorView = getErrorView();
        prepareView(errorView);
        addViewInLayout(errorView, 0, errorView.getLayoutParams());
        measureChild(errorView, android.view.View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824), android.view.View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824));
        errorView.layout(0, 0, errorView.getMeasuredWidth() + this.mPaddingLeft + this.mPaddingRight, errorView.getMeasuredHeight() + this.mPaddingTop + this.mPaddingBottom);
        this.mView = errorView;
        this.mViewMode = 2;
    }

    @java.lang.Deprecated
    public void updateAppWidgetSize(android.os.Bundle bundle, int i, int i2, int i3, int i4) {
        updateAppWidgetSize(bundle, i, i2, i3, i4, false);
    }

    public void updateAppWidgetSize(android.os.Bundle bundle, java.util.List<android.util.SizeF> list) {
        android.appwidget.AppWidgetManager appWidgetManager = android.appwidget.AppWidgetManager.getInstance(this.mContext);
        android.graphics.Rect defaultPadding = getDefaultPadding();
        float f = getResources().getDisplayMetrics().density;
        float f2 = (defaultPadding.left + defaultPadding.right) / f;
        float f3 = (defaultPadding.top + defaultPadding.bottom) / f;
        java.util.ArrayList<? extends android.os.Parcelable> arrayList = new java.util.ArrayList<>(list.size());
        float f4 = Float.MAX_VALUE;
        float f5 = 0.0f;
        float f6 = 0.0f;
        float f7 = Float.MAX_VALUE;
        for (int i = 0; i < list.size(); i++) {
            android.util.SizeF sizeF = list.get(i);
            android.util.SizeF sizeF2 = new android.util.SizeF(java.lang.Math.max(0.0f, sizeF.getWidth() - f2), java.lang.Math.max(0.0f, sizeF.getHeight() - f3));
            arrayList.add(sizeF2);
            f4 = java.lang.Math.min(f4, sizeF2.getWidth());
            f5 = java.lang.Math.max(f5, sizeF2.getWidth());
            f7 = java.lang.Math.min(f7, sizeF2.getHeight());
            f6 = java.lang.Math.max(f6, sizeF2.getHeight());
        }
        if (arrayList.equals(appWidgetManager.getAppWidgetOptions(this.mAppWidgetId).getParcelableArrayList(android.appwidget.AppWidgetManager.OPTION_APPWIDGET_SIZES))) {
            return;
        }
        android.os.Bundle deepCopy = bundle.deepCopy();
        deepCopy.putInt(android.appwidget.AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH, (int) f4);
        deepCopy.putInt(android.appwidget.AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT, (int) f7);
        deepCopy.putInt(android.appwidget.AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH, (int) f5);
        deepCopy.putInt(android.appwidget.AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT, (int) f6);
        deepCopy.putParcelableArrayList(android.appwidget.AppWidgetManager.OPTION_APPWIDGET_SIZES, arrayList);
        updateAppWidgetOptions(deepCopy);
    }

    public void updateAppWidgetSize(android.os.Bundle bundle, int i, int i2, int i3, int i4, boolean z) {
        if (bundle == null) {
            bundle = new android.os.Bundle();
        }
        android.graphics.Rect defaultPadding = getDefaultPadding();
        float f = getResources().getDisplayMetrics().density;
        int i5 = (int) ((defaultPadding.left + defaultPadding.right) / f);
        int i6 = (int) ((defaultPadding.top + defaultPadding.bottom) / f);
        boolean z2 = false;
        int i7 = i - (z ? 0 : i5);
        int i8 = i2 - (z ? 0 : i6);
        if (z) {
            i5 = 0;
        }
        int i9 = i3 - i5;
        if (z) {
            i6 = 0;
        }
        int i10 = i4 - i6;
        android.os.Bundle appWidgetOptions = android.appwidget.AppWidgetManager.getInstance(this.mContext).getAppWidgetOptions(this.mAppWidgetId);
        if (i7 != appWidgetOptions.getInt(android.appwidget.AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH) || i8 != appWidgetOptions.getInt(android.appwidget.AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT) || i9 != appWidgetOptions.getInt(android.appwidget.AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH) || i10 != appWidgetOptions.getInt(android.appwidget.AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT)) {
            z2 = true;
        }
        if (z2) {
            bundle.putInt(android.appwidget.AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH, i7);
            bundle.putInt(android.appwidget.AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT, i8);
            bundle.putInt(android.appwidget.AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH, i9);
            bundle.putInt(android.appwidget.AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT, i10);
            bundle.putParcelableArrayList(android.appwidget.AppWidgetManager.OPTION_APPWIDGET_SIZES, new java.util.ArrayList<>());
            updateAppWidgetOptions(bundle);
        }
    }

    public void updateAppWidgetOptions(android.os.Bundle bundle) {
        android.appwidget.AppWidgetManager.getInstance(this.mContext).updateAppWidgetOptions(this.mAppWidgetId, bundle);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public android.widget.FrameLayout.LayoutParams generateLayoutParams(android.util.AttributeSet attributeSet) {
        return new android.widget.FrameLayout.LayoutParams(this.mRemoteContext != null ? this.mRemoteContext : this.mContext, attributeSet);
    }

    public void setExecutor(java.util.concurrent.Executor executor) {
        if (this.mLastExecutionSignal != null) {
            this.mLastExecutionSignal.cancel();
            this.mLastExecutionSignal = null;
        }
        this.mAsyncExecutor = executor;
    }

    public void setOnLightBackground(boolean z) {
        this.mOnLightBackground = z;
    }

    @Override // android.appwidget.AppWidgetHost.AppWidgetHostListener
    public void onUpdateProviderInfo(android.appwidget.AppWidgetProviderInfo appWidgetProviderInfo) {
        setAppWidget(this.mAppWidgetId, appWidgetProviderInfo);
        this.mViewMode = 0;
        updateAppWidget(null);
    }

    @Override // android.appwidget.AppWidgetHost.AppWidgetHostListener
    public void updateAppWidget(android.widget.RemoteViews remoteViews) {
        this.mLastInflatedRemoteViews = remoteViews;
        applyRemoteViews(remoteViews, true);
    }

    private void reapplyLastRemoteViews() {
        android.util.SparseArray<android.os.Parcelable> sparseArray = new android.util.SparseArray<>();
        saveHierarchyState(sparseArray);
        applyRemoteViews(this.mLastInflatedRemoteViews, true);
        restoreHierarchyState(sparseArray);
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x007a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected void applyRemoteViews(android.widget.RemoteViews remoteViews, boolean z) {
        java.lang.RuntimeException runtimeException;
        android.view.View view;
        java.lang.RuntimeException e;
        android.view.View view2;
        this.mLastInflatedRemoteViewsId = -1L;
        android.view.View view3 = null;
        java.lang.RuntimeException runtimeException2 = null;
        if (this.mLastExecutionSignal != null) {
            this.mLastExecutionSignal.cancel();
            this.mLastExecutionSignal = null;
        }
        boolean z2 = false;
        if (remoteViews == null) {
            if (this.mViewMode == 3) {
                return;
            }
            view = getDefaultView();
            this.mViewMode = 3;
        } else {
            android.widget.RemoteViews remoteViewsToApply = remoteViews.getRemoteViewsToApply(this.mContext, this.mCurrentSize);
            if (this.mOnLightBackground) {
                remoteViewsToApply = remoteViewsToApply.getDarkTextViews();
            }
            if (this.mAsyncExecutor != null && z) {
                inflateAsync(remoteViewsToApply);
                return;
            }
            this.mRemoteContext = getRemoteContextEnsuringCorrectCachedApkPath();
            if (!this.mColorMappingChanged && remoteViewsToApply.canRecycleView(this.mView)) {
                try {
                    remoteViewsToApply.reapply(this.mContext, this.mView, this.mInteractionHandler, this.mCurrentSize, this.mColorResources);
                    view2 = this.mView;
                } catch (java.lang.RuntimeException e2) {
                    e = e2;
                    view2 = null;
                }
                try {
                    this.mLastInflatedRemoteViewsId = remoteViewsToApply.computeUniqueId(remoteViews);
                    z2 = true;
                    view3 = view2;
                    runtimeException = null;
                } catch (java.lang.RuntimeException e3) {
                    e = e3;
                    java.lang.RuntimeException runtimeException3 = e;
                    view3 = view2;
                    runtimeException = runtimeException3;
                    if (view3 == null) {
                    }
                    this.mViewMode = 1;
                    applyContent(view, z2, runtimeException2);
                }
            } else {
                runtimeException = null;
            }
            if (view3 == null) {
                view = view3;
                runtimeException2 = runtimeException;
            } else {
                try {
                    view3 = remoteViewsToApply.apply(this.mContext, this, this.mInteractionHandler, this.mCurrentSize, this.mColorResources);
                    this.mLastInflatedRemoteViewsId = remoteViewsToApply.computeUniqueId(remoteViews);
                    view = view3;
                    runtimeException2 = runtimeException;
                } catch (java.lang.RuntimeException e4) {
                    android.view.View view4 = view3;
                    runtimeException2 = e4;
                    view = view4;
                }
            }
            this.mViewMode = 1;
        }
        applyContent(view, z2, runtimeException2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applyContent(android.view.View view, boolean z, java.lang.Exception exc) {
        this.mColorMappingChanged = false;
        if (view == null) {
            if (this.mViewMode == 2) {
                return;
            }
            if (exc != null) {
                android.util.Log.w(TAG, "Error inflating RemoteViews", exc);
            }
            view = getErrorView();
            this.mViewMode = 2;
        }
        if (!z) {
            prepareView(view);
            addView(view);
        }
        if (this.mView != view) {
            removeView(this.mView);
            this.mView = view;
        }
    }

    private void inflateAsync(android.widget.RemoteViews remoteViews) {
        this.mRemoteContext = getRemoteContextEnsuringCorrectCachedApkPath();
        int layoutId = remoteViews.getLayoutId();
        if (this.mLastExecutionSignal != null) {
            this.mLastExecutionSignal.cancel();
        }
        if (!this.mColorMappingChanged && remoteViews.canRecycleView(this.mView)) {
            try {
                this.mLastExecutionSignal = remoteViews.reapplyAsync(this.mContext, this.mView, this.mAsyncExecutor, new android.appwidget.AppWidgetHostView.ViewApplyListener(remoteViews, layoutId, true), this.mInteractionHandler, this.mCurrentSize, this.mColorResources);
            } catch (java.lang.Exception e) {
            }
        }
        if (this.mLastExecutionSignal == null) {
            this.mLastExecutionSignal = remoteViews.applyAsync(this.mContext, this, this.mAsyncExecutor, new android.appwidget.AppWidgetHostView.ViewApplyListener(remoteViews, layoutId, false), this.mInteractionHandler, this.mCurrentSize, this.mColorResources);
        }
    }

    private class ViewApplyListener implements android.widget.RemoteViews.OnViewAppliedListener {
        private final boolean mIsReapply;
        private final int mLayoutId;
        private final android.widget.RemoteViews mViews;

        ViewApplyListener(android.widget.RemoteViews remoteViews, int i, boolean z) {
            this.mViews = remoteViews;
            this.mLayoutId = i;
            this.mIsReapply = z;
        }

        @Override // android.widget.RemoteViews.OnViewAppliedListener
        public void onViewApplied(android.view.View view) {
            android.appwidget.AppWidgetHostView.this.mViewMode = 1;
            android.appwidget.AppWidgetHostView.this.applyContent(view, this.mIsReapply, null);
            android.appwidget.AppWidgetHostView.this.mLastInflatedRemoteViewsId = this.mViews.computeUniqueId(android.appwidget.AppWidgetHostView.this.mLastInflatedRemoteViews);
            android.appwidget.AppWidgetHostView.this.restoreInstanceState();
            android.appwidget.AppWidgetHostView.this.mLastExecutionSignal = null;
        }

        @Override // android.widget.RemoteViews.OnViewAppliedListener
        public void onError(java.lang.Exception exc) {
            if (this.mIsReapply) {
                android.appwidget.AppWidgetHostView.this.mLastExecutionSignal = this.mViews.applyAsync(android.appwidget.AppWidgetHostView.this.mContext, android.appwidget.AppWidgetHostView.this, android.appwidget.AppWidgetHostView.this.mAsyncExecutor, android.appwidget.AppWidgetHostView.this.new ViewApplyListener(this.mViews, this.mLayoutId, false), android.appwidget.AppWidgetHostView.this.mInteractionHandler, android.appwidget.AppWidgetHostView.this.mCurrentSize);
            } else {
                android.appwidget.AppWidgetHostView.this.applyContent(null, false, exc);
            }
            android.appwidget.AppWidgetHostView.this.mLastExecutionSignal = null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.appwidget.AppWidgetHost.AppWidgetHostListener
    public void onViewDataChanged(int i) {
        android.view.View findViewById = findViewById(i);
        if (findViewById != null && (findViewById instanceof android.widget.AdapterView)) {
            android.widget.AdapterView adapterView = (android.widget.AdapterView) findViewById;
            android.widget.Adapter adapter = adapterView.getAdapter();
            if (adapter instanceof android.widget.BaseAdapter) {
                ((android.widget.BaseAdapter) adapter).notifyDataSetChanged();
            } else if (adapter == null && (adapterView instanceof android.widget.RemoteViewsAdapter.RemoteAdapterConnectionCallback)) {
                ((android.widget.RemoteViewsAdapter.RemoteAdapterConnectionCallback) adapterView).deferNotifyDataSetChanged();
            }
        }
    }

    protected android.content.Context getRemoteContextEnsuringCorrectCachedApkPath() {
        try {
            android.app.LoadedApk.checkAndUpdateApkPaths(this.mInfo.providerInfo.applicationInfo);
            android.content.Context createApplicationContext = this.mContext.createApplicationContext(this.mInfo.providerInfo.applicationInfo, 4);
            if (this.mColorResources != null) {
                this.mColorResources.apply(createApplicationContext);
            }
            return createApplicationContext;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Log.e(TAG, "Package name " + this.mInfo.providerInfo.packageName + " not found");
            return this.mContext;
        } catch (java.lang.NullPointerException e2) {
            android.util.Log.e(TAG, "Error trying to create the remote context.", e2);
            return this.mContext;
        }
    }

    protected void prepareView(android.view.View view) {
        android.widget.FrameLayout.LayoutParams layoutParams = (android.widget.FrameLayout.LayoutParams) view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new android.widget.FrameLayout.LayoutParams(-1, -1);
        }
        layoutParams.gravity = 17;
        view.setLayoutParams(layoutParams);
    }

    protected android.view.View getDefaultView() {
        android.view.View view;
        int i;
        java.lang.RuntimeException e = null;
        try {
            if (this.mInfo != null) {
                android.content.Context remoteContextEnsuringCorrectCachedApkPath = getRemoteContextEnsuringCorrectCachedApkPath();
                this.mRemoteContext = remoteContextEnsuringCorrectCachedApkPath;
                android.view.LayoutInflater cloneInContext = ((android.view.LayoutInflater) remoteContextEnsuringCorrectCachedApkPath.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE)).cloneInContext(remoteContextEnsuringCorrectCachedApkPath);
                cloneInContext.setFilter(INFLATER_FILTER);
                android.os.Bundle appWidgetOptions = android.appwidget.AppWidgetManager.getInstance(this.mContext).getAppWidgetOptions(this.mAppWidgetId);
                int i2 = this.mInfo.initialLayout;
                if (appWidgetOptions.containsKey(android.appwidget.AppWidgetManager.OPTION_APPWIDGET_HOST_CATEGORY) && appWidgetOptions.getInt(android.appwidget.AppWidgetManager.OPTION_APPWIDGET_HOST_CATEGORY) == 2 && (i = this.mInfo.initialKeyguardLayout) != 0) {
                    i2 = i;
                }
                view = cloneInContext.inflate(i2, (android.view.ViewGroup) this, false);
                try {
                    if (!(view instanceof android.widget.AdapterView)) {
                        view.setOnClickListener(new android.view.View.OnClickListener() { // from class: android.appwidget.AppWidgetHostView$$ExternalSyntheticLambda2
                            @Override // android.view.View.OnClickListener
                            public final void onClick(android.view.View view2) {
                                android.appwidget.AppWidgetHostView.this.onDefaultViewClicked(view2);
                            }
                        });
                    }
                } catch (java.lang.RuntimeException e2) {
                    e = e2;
                }
            } else {
                android.util.Log.w(TAG, "can't inflate defaultView because mInfo is missing");
                view = null;
            }
        } catch (java.lang.RuntimeException e3) {
            e = e3;
            view = null;
        }
        if (e != null) {
            android.util.Log.w(TAG, "Error inflating AppWidget " + this.mInfo, e);
        }
        if (view == null) {
            return getErrorView();
        }
        return view;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onDefaultViewClicked(android.view.View view) {
        if (this.mInfo != null) {
            android.content.pm.LauncherApps launcherApps = (android.content.pm.LauncherApps) getContext().getSystemService(android.content.pm.LauncherApps.class);
            java.util.List<android.content.pm.LauncherActivityInfo> activityList = launcherApps.getActivityList(this.mInfo.provider.getPackageName(), this.mInfo.getProfile());
            if (!activityList.isEmpty()) {
                android.content.pm.LauncherActivityInfo launcherActivityInfo = activityList.get(0);
                launcherApps.startMainActivity(launcherActivityInfo.getComponentName(), launcherActivityInfo.getUser(), android.widget.RemoteViews.getSourceBounds(view), null);
            }
        }
    }

    protected android.view.View getErrorView() {
        android.widget.TextView textView = new android.widget.TextView(this.mContext);
        textView.setText(com.android.internal.R.string.gadget_host_error_inflating);
        textView.setBackgroundColor(android.graphics.Color.argb(127, 0, 0, 0));
        return textView;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onInitializeAccessibilityNodeInfoInternal(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(android.appwidget.AppWidgetHostView.class.getName());
    }

    public android.app.ActivityOptions createSharedElementActivityOptions(int[] iArr, java.lang.String[] strArr, android.content.Intent intent) {
        android.content.Context context = getContext();
        while ((context instanceof android.content.ContextWrapper) && !(context instanceof android.app.Activity)) {
            context = ((android.content.ContextWrapper) context).getBaseContext();
        }
        if (!(context instanceof android.app.Activity)) {
            return null;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        android.os.Bundle bundle = new android.os.Bundle();
        for (int i = 0; i < iArr.length; i++) {
            android.view.View findViewById = findViewById(iArr[i]);
            if (findViewById != null) {
                arrayList.add(android.util.Pair.create(findViewById, strArr[i]));
                bundle.putParcelable(strArr[i], android.widget.RemoteViews.getSourceBounds(findViewById));
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        intent.putExtra(android.widget.RemoteViews.EXTRA_SHARED_ELEMENT_BOUNDS, bundle);
        android.app.ActivityOptions makeSceneTransitionAnimation = android.app.ActivityOptions.makeSceneTransitionAnimation((android.app.Activity) context, (android.util.Pair[]) arrayList.toArray(new android.util.Pair[arrayList.size()]));
        makeSceneTransitionAnimation.setPendingIntentLaunchFlags(268435456);
        return makeSceneTransitionAnimation;
    }

    private android.widget.RemoteViews.InteractionHandler getHandler(final android.widget.RemoteViews.InteractionHandler interactionHandler) {
        return new android.widget.RemoteViews.InteractionHandler() { // from class: android.appwidget.AppWidgetHostView$$ExternalSyntheticLambda0
            @Override // android.widget.RemoteViews.InteractionHandler
            public final boolean onInteraction(android.view.View view, android.app.PendingIntent pendingIntent, android.widget.RemoteViews.RemoteResponse remoteResponse) {
                boolean lambda$getHandler$1;
                lambda$getHandler$1 = android.appwidget.AppWidgetHostView.this.lambda$getHandler$1(interactionHandler, view, pendingIntent, remoteResponse);
                return lambda$getHandler$1;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$getHandler$1(android.widget.RemoteViews.InteractionHandler interactionHandler, android.view.View view, android.app.PendingIntent pendingIntent, android.widget.RemoteViews.RemoteResponse remoteResponse) {
        android.appwidget.AppWidgetManager appWidgetManager = android.appwidget.AppWidgetManager.getInstance(this.mContext);
        if (appWidgetManager != null) {
            appWidgetManager.noteAppWidgetTapped(this.mAppWidgetId);
        }
        if (interactionHandler != null) {
            return interactionHandler.onInteraction(view, pendingIntent, remoteResponse);
        }
        return android.widget.RemoteViews.startPendingIntent(view, pendingIntent, remoteResponse.getLaunchOptions(view));
    }

    public void setColorResources(android.util.SparseIntArray sparseIntArray) {
        if (this.mColorResources != null && isSameColorMapping(this.mColorResources.getColorMapping(), sparseIntArray)) {
            return;
        }
        setColorResources(android.widget.RemoteViews.ColorResources.create(this.mContext, sparseIntArray));
    }

    private void setColorResourcesStates(android.widget.RemoteViews.ColorResources colorResources) {
        this.mColorResources = colorResources;
        this.mColorMappingChanged = true;
        this.mViewMode = 0;
    }

    public void setColorResources(android.widget.RemoteViews.ColorResources colorResources) {
        if (colorResources == this.mColorResources) {
            return;
        }
        setColorResourcesStates(colorResources);
        reapplyLastRemoteViews();
    }

    public void setColorResourcesNoReapply(android.widget.RemoteViews.ColorResources colorResources) {
        if (colorResources == this.mColorResources) {
            return;
        }
        setColorResourcesStates(colorResources);
    }

    private boolean isSameColorMapping(android.util.SparseIntArray sparseIntArray, android.util.SparseIntArray sparseIntArray2) {
        if (sparseIntArray.size() != sparseIntArray2.size()) {
            return false;
        }
        for (int i = 0; i < sparseIntArray.size(); i++) {
            if (sparseIntArray.keyAt(i) != sparseIntArray2.keyAt(i) || sparseIntArray.valueAt(i) != sparseIntArray2.valueAt(i)) {
                return false;
            }
        }
        return true;
    }

    public void resetColorResources() {
        if (this.mColorResources != null) {
            this.mColorResources = null;
            this.mColorMappingChanged = true;
            this.mViewMode = 0;
            reapplyLastRemoteViews();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(android.graphics.Canvas canvas) {
        try {
            super.dispatchDraw(canvas);
        } catch (java.lang.Exception e) {
            android.util.Log.e(TAG, "Drawing view failed: " + e);
            post(new java.lang.Runnable() { // from class: android.appwidget.AppWidgetHostView$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.appwidget.AppWidgetHostView.this.handleViewError();
                }
            });
        }
    }
}
