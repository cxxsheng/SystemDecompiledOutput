package android.widget;

/* loaded from: classes4.dex */
public class RemoteViewsAdapter extends android.widget.BaseAdapter implements android.os.Handler.Callback {
    private static final int CACHE_RESET_CONFIG_FLAGS = -1073737216;
    private static final int DEFAULT_CACHE_SIZE = 40;
    private static final int DEFAULT_LOADING_VIEW_HEIGHT = 50;
    static final int MSG_LOAD_NEXT_ITEM = 3;
    private static final int MSG_MAIN_HANDLER_COMMIT_METADATA = 1;
    private static final int MSG_MAIN_HANDLER_REMOTE_ADAPTER_CONNECTED = 3;
    private static final int MSG_MAIN_HANDLER_REMOTE_ADAPTER_DISCONNECTED = 4;
    private static final int MSG_MAIN_HANDLER_REMOTE_VIEWS_LOADED = 5;
    private static final int MSG_MAIN_HANDLER_SUPER_NOTIFY_DATA_SET_CHANGED = 2;
    static final int MSG_NOTIFY_DATA_SET_CHANGED = 2;
    static final int MSG_REQUEST_BIND = 1;
    static final int MSG_UNBIND_SERVICE = 4;
    private static final int REMOTE_VIEWS_CACHE_DURATION = 5000;
    private static final java.lang.String TAG = "RemoteViewsAdapter";
    private static final int UNBIND_SERVICE_DELAY = 5000;
    private static android.os.Handler sCacheRemovalQueue;
    private static android.os.HandlerThread sCacheRemovalThread;
    private static final java.util.HashMap<android.widget.RemoteViewsAdapter.RemoteViewsCacheKey, android.widget.RemoteViewsAdapter.FixedSizeRemoteViewsCache> sCachedRemoteViewsCaches = new java.util.HashMap<>();
    private static final java.util.HashMap<android.widget.RemoteViewsAdapter.RemoteViewsCacheKey, java.lang.Runnable> sRemoteViewsCacheRemoveRunnables = new java.util.HashMap<>();
    private final int mAppWidgetId;
    private final java.util.concurrent.Executor mAsyncViewLoadExecutor;
    private final android.widget.RemoteViewsAdapter.FixedSizeRemoteViewsCache mCache;
    private final android.widget.RemoteViewsAdapter.RemoteAdapterConnectionCallback mCallback;
    private final android.content.Context mContext;
    private boolean mDataReady;
    private final android.content.Intent mIntent;
    private android.content.pm.ApplicationInfo mLastRemoteViewAppInfo;
    private final android.os.Handler mMainHandler;
    private final boolean mOnLightBackground;
    private android.widget.RemoteViews.InteractionHandler mRemoteViewsInteractionHandler;
    private android.widget.RemoteViewsAdapter.RemoteViewsFrameLayoutRefSet mRequestedViews;
    private final android.widget.RemoteViewsAdapter.RemoteServiceHandler mServiceHandler;
    private int mVisibleWindowLowerBound;
    private int mVisibleWindowUpperBound;
    private final android.os.HandlerThread mWorkerThread;

    public interface RemoteAdapterConnectionCallback {
        void deferNotifyDataSetChanged();

        boolean onRemoteAdapterConnected();

        void onRemoteAdapterDisconnected();

        void setRemoteViewsAdapter(android.content.Intent intent, boolean z);
    }

    public static class AsyncRemoteAdapterAction implements java.lang.Runnable {
        private final android.widget.RemoteViewsAdapter.RemoteAdapterConnectionCallback mCallback;
        private final android.content.Intent mIntent;

        public AsyncRemoteAdapterAction(android.widget.RemoteViewsAdapter.RemoteAdapterConnectionCallback remoteAdapterConnectionCallback, android.content.Intent intent) {
            this.mCallback = remoteAdapterConnectionCallback;
            this.mIntent = intent;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.mCallback.setRemoteViewsAdapter(this.mIntent, true);
        }
    }

    private static class RemoteServiceHandler extends android.os.Handler implements android.content.ServiceConnection {
        private final java.lang.ref.WeakReference<android.widget.RemoteViewsAdapter> mAdapter;
        private boolean mBindRequested;
        private final android.content.Context mContext;
        private boolean mNotifyDataSetChangedPending;
        private com.android.internal.widget.IRemoteViewsFactory mRemoteViewsFactory;

        RemoteServiceHandler(android.os.Looper looper, android.widget.RemoteViewsAdapter remoteViewsAdapter, android.content.Context context) {
            super(looper);
            this.mNotifyDataSetChangedPending = false;
            this.mBindRequested = false;
            this.mAdapter = new java.lang.ref.WeakReference<>(remoteViewsAdapter);
            this.mContext = context;
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
            this.mRemoteViewsFactory = com.android.internal.widget.IRemoteViewsFactory.Stub.asInterface(iBinder);
            enqueueDeferredUnbindServiceMessage();
            android.widget.RemoteViewsAdapter remoteViewsAdapter = this.mAdapter.get();
            if (remoteViewsAdapter == null) {
                return;
            }
            if (this.mNotifyDataSetChangedPending) {
                this.mNotifyDataSetChangedPending = false;
                android.os.Message obtain = android.os.Message.obtain(this, 2);
                handleMessage(obtain);
                obtain.recycle();
                return;
            }
            if (!sendNotifyDataSetChange(false)) {
                return;
            }
            remoteViewsAdapter.updateTemporaryMetaData(this.mRemoteViewsFactory);
            remoteViewsAdapter.mMainHandler.sendEmptyMessage(1);
            remoteViewsAdapter.mMainHandler.sendEmptyMessage(3);
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(android.content.ComponentName componentName) {
            this.mRemoteViewsFactory = null;
            android.widget.RemoteViewsAdapter remoteViewsAdapter = this.mAdapter.get();
            if (remoteViewsAdapter != null) {
                remoteViewsAdapter.mMainHandler.sendEmptyMessage(4);
            }
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            int i;
            int[] visibleWindow;
            android.widget.RemoteViewsAdapter remoteViewsAdapter = this.mAdapter.get();
            switch (message.what) {
                case 1:
                    if (remoteViewsAdapter == null || this.mRemoteViewsFactory != null) {
                        enqueueDeferredUnbindServiceMessage();
                    }
                    if (this.mBindRequested) {
                        return;
                    }
                    try {
                        this.mBindRequested = android.appwidget.AppWidgetManager.getInstance(this.mContext).bindRemoteViewsService(this.mContext, message.arg1, (android.content.Intent) message.obj, this.mContext.getServiceDispatcher(this, this, android.view.InputDevice.SOURCE_HDMI), android.view.InputDevice.SOURCE_HDMI);
                        return;
                    } catch (java.lang.Exception e) {
                        android.util.Log.e(android.widget.RemoteViewsAdapter.TAG, "Failed to bind remoteViewsService: " + e.getMessage());
                        return;
                    }
                case 2:
                    enqueueDeferredUnbindServiceMessage();
                    if (remoteViewsAdapter == null) {
                        return;
                    }
                    if (this.mRemoteViewsFactory == null) {
                        this.mNotifyDataSetChangedPending = true;
                        remoteViewsAdapter.requestBindService();
                        return;
                    }
                    if (!sendNotifyDataSetChange(true)) {
                        return;
                    }
                    synchronized (remoteViewsAdapter.mCache) {
                        remoteViewsAdapter.mCache.reset();
                    }
                    remoteViewsAdapter.updateTemporaryMetaData(this.mRemoteViewsFactory);
                    synchronized (remoteViewsAdapter.mCache.getTemporaryMetaData()) {
                        i = remoteViewsAdapter.mCache.getTemporaryMetaData().count;
                        visibleWindow = remoteViewsAdapter.getVisibleWindow(i);
                    }
                    for (int i2 : visibleWindow) {
                        if (i2 < i) {
                            remoteViewsAdapter.updateRemoteViews(this.mRemoteViewsFactory, i2, false);
                        }
                    }
                    remoteViewsAdapter.mMainHandler.sendEmptyMessage(1);
                    remoteViewsAdapter.mMainHandler.sendEmptyMessage(2);
                    return;
                case 3:
                    if (remoteViewsAdapter == null || this.mRemoteViewsFactory == null) {
                        return;
                    }
                    removeMessages(4);
                    int nextIndexToLoad = remoteViewsAdapter.mCache.getNextIndexToLoad();
                    if (nextIndexToLoad > -1) {
                        remoteViewsAdapter.updateRemoteViews(this.mRemoteViewsFactory, nextIndexToLoad, true);
                        sendEmptyMessage(3);
                        return;
                    } else {
                        enqueueDeferredUnbindServiceMessage();
                        return;
                    }
                case 4:
                    unbindNow();
                    return;
                default:
                    return;
            }
        }

        protected void unbindNow() {
            if (this.mBindRequested) {
                this.mBindRequested = false;
                this.mContext.unbindService(this);
            }
            this.mRemoteViewsFactory = null;
        }

        private boolean sendNotifyDataSetChange(boolean z) {
            if (!z) {
                try {
                    if (this.mRemoteViewsFactory.isCreated()) {
                        return true;
                    }
                } catch (android.os.RemoteException | java.lang.RuntimeException e) {
                    android.util.Log.e(android.widget.RemoteViewsAdapter.TAG, "Error in updateNotifyDataSetChanged(): " + e.getMessage());
                    return false;
                }
            }
            this.mRemoteViewsFactory.onDataSetChanged();
            return true;
        }

        private void enqueueDeferredUnbindServiceMessage() {
            removeMessages(4);
            sendEmptyMessageDelayed(4, 5000L);
        }
    }

    static class RemoteViewsFrameLayout extends android.appwidget.AppWidgetHostView.AdapterChildHostView {
        public int cacheIndex;
        private final android.widget.RemoteViewsAdapter.FixedSizeRemoteViewsCache mCache;

        public RemoteViewsFrameLayout(android.content.Context context, android.widget.RemoteViewsAdapter.FixedSizeRemoteViewsCache fixedSizeRemoteViewsCache) {
            super(context);
            this.cacheIndex = -1;
            this.mCache = fixedSizeRemoteViewsCache;
        }

        public void onRemoteViewsLoaded(android.widget.RemoteViews remoteViews, android.widget.RemoteViews.InteractionHandler interactionHandler, boolean z) {
            setInteractionHandler(interactionHandler);
            applyRemoteViews(remoteViews, z || (remoteViews != null && remoteViews.prefersAsyncApply()));
        }

        @Override // android.appwidget.AppWidgetHostView
        protected android.view.View getDefaultView() {
            int i = this.mCache.getMetaData().getLoadingTemplate(getContext()).defaultHeight;
            android.widget.TextView textView = (android.widget.TextView) android.view.LayoutInflater.from(getContext()).inflate(com.android.internal.R.layout.remote_views_adapter_default_loading_view, (android.view.ViewGroup) this, false);
            textView.setHeight(i);
            return textView;
        }

        @Override // android.appwidget.AppWidgetHostView
        protected android.view.View getErrorView() {
            return getDefaultView();
        }
    }

    private class RemoteViewsFrameLayoutRefSet extends android.util.SparseArray<java.util.ArrayList<android.widget.RemoteViewsAdapter.RemoteViewsFrameLayout>> {
        private RemoteViewsFrameLayoutRefSet() {
        }

        public void add(int i, android.widget.RemoteViewsAdapter.RemoteViewsFrameLayout remoteViewsFrameLayout) {
            java.util.ArrayList<android.widget.RemoteViewsAdapter.RemoteViewsFrameLayout> arrayList = get(i);
            if (arrayList == null) {
                arrayList = new java.util.ArrayList<>();
                put(i, arrayList);
            }
            remoteViewsFrameLayout.cacheIndex = i;
            arrayList.add(remoteViewsFrameLayout);
        }

        public void notifyOnRemoteViewsLoaded(int i, android.widget.RemoteViews remoteViews) {
            java.util.ArrayList<android.widget.RemoteViewsAdapter.RemoteViewsFrameLayout> removeReturnOld;
            if (remoteViews != null && (removeReturnOld = removeReturnOld(i)) != null) {
                java.util.Iterator<android.widget.RemoteViewsAdapter.RemoteViewsFrameLayout> it = removeReturnOld.iterator();
                while (it.hasNext()) {
                    it.next().onRemoteViewsLoaded(remoteViews, android.widget.RemoteViewsAdapter.this.mRemoteViewsInteractionHandler, true);
                }
            }
        }

        public void removeView(android.widget.RemoteViewsAdapter.RemoteViewsFrameLayout remoteViewsFrameLayout) {
            if (remoteViewsFrameLayout.cacheIndex < 0) {
                return;
            }
            java.util.ArrayList<android.widget.RemoteViewsAdapter.RemoteViewsFrameLayout> arrayList = get(remoteViewsFrameLayout.cacheIndex);
            if (arrayList != null) {
                arrayList.remove(remoteViewsFrameLayout);
            }
            remoteViewsFrameLayout.cacheIndex = -1;
        }
    }

    private static class RemoteViewsMetaData {
        int count;
        boolean hasStableIds;
        android.widget.RemoteViewsAdapter.LoadingViewTemplate loadingTemplate;
        private final android.util.SparseIntArray mTypeIdIndexMap = new android.util.SparseIntArray();
        int viewTypeCount;

        public RemoteViewsMetaData() {
            reset();
        }

        public void set(android.widget.RemoteViewsAdapter.RemoteViewsMetaData remoteViewsMetaData) {
            synchronized (remoteViewsMetaData) {
                this.count = remoteViewsMetaData.count;
                this.viewTypeCount = remoteViewsMetaData.viewTypeCount;
                this.hasStableIds = remoteViewsMetaData.hasStableIds;
                this.loadingTemplate = remoteViewsMetaData.loadingTemplate;
            }
        }

        public void reset() {
            this.count = 0;
            this.viewTypeCount = 1;
            this.hasStableIds = true;
            this.loadingTemplate = null;
            this.mTypeIdIndexMap.clear();
        }

        public int getMappedViewType(int i) {
            int i2 = this.mTypeIdIndexMap.get(i, -1);
            if (i2 == -1) {
                int size = this.mTypeIdIndexMap.size() + 1;
                this.mTypeIdIndexMap.put(i, size);
                return size;
            }
            return i2;
        }

        public boolean isViewTypeInRange(int i) {
            return getMappedViewType(i) < this.viewTypeCount;
        }

        public synchronized android.widget.RemoteViewsAdapter.LoadingViewTemplate getLoadingTemplate(android.content.Context context) {
            if (this.loadingTemplate == null) {
                this.loadingTemplate = new android.widget.RemoteViewsAdapter.LoadingViewTemplate(null, context);
            }
            return this.loadingTemplate;
        }
    }

    private static class RemoteViewsIndexMetaData {
        long itemId;
        int typeId;

        public RemoteViewsIndexMetaData(android.widget.RemoteViews remoteViews, long j) {
            set(remoteViews, j);
        }

        public void set(android.widget.RemoteViews remoteViews, long j) {
            this.itemId = j;
            if (remoteViews != null) {
                this.typeId = remoteViews.getLayoutId();
            } else {
                this.typeId = 0;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class FixedSizeRemoteViewsCache {
        private static final float sMaxCountSlackPercent = 0.75f;
        private static final int sMaxMemoryLimitInBytes = 2097152;
        private final android.content.res.Configuration mConfiguration;
        private final int mMaxCount;
        private final int mMaxCountSlack;
        private final android.widget.RemoteViewsAdapter.RemoteViewsMetaData mMetaData = new android.widget.RemoteViewsAdapter.RemoteViewsMetaData();
        private final android.widget.RemoteViewsAdapter.RemoteViewsMetaData mTemporaryMetaData = new android.widget.RemoteViewsAdapter.RemoteViewsMetaData();
        private final android.util.SparseArray<android.widget.RemoteViewsAdapter.RemoteViewsIndexMetaData> mIndexMetaData = new android.util.SparseArray<>();
        private final android.util.SparseArray<android.widget.RemoteViews> mIndexRemoteViews = new android.util.SparseArray<>();
        private final android.util.SparseBooleanArray mIndicesToLoad = new android.util.SparseBooleanArray();
        private int mPreloadLowerBound = 0;
        private int mPreloadUpperBound = -1;
        private int mLastRequestedIndex = -1;

        FixedSizeRemoteViewsCache(int i, android.content.res.Configuration configuration) {
            this.mMaxCount = i;
            this.mMaxCountSlack = java.lang.Math.round((this.mMaxCount / 2) * 0.75f);
            this.mConfiguration = new android.content.res.Configuration(configuration);
        }

        public void insert(int i, android.widget.RemoteViews remoteViews, long j, int[] iArr) {
            int farthestPositionFrom;
            if (this.mIndexRemoteViews.size() >= this.mMaxCount) {
                this.mIndexRemoteViews.remove(getFarthestPositionFrom(i, iArr));
            }
            int i2 = this.mLastRequestedIndex > -1 ? this.mLastRequestedIndex : i;
            while (getRemoteViewsBitmapMemoryUsage() >= 2097152 && (farthestPositionFrom = getFarthestPositionFrom(i2, iArr)) >= 0) {
                this.mIndexRemoteViews.remove(farthestPositionFrom);
            }
            android.widget.RemoteViewsAdapter.RemoteViewsIndexMetaData remoteViewsIndexMetaData = this.mIndexMetaData.get(i);
            if (remoteViewsIndexMetaData != null) {
                remoteViewsIndexMetaData.set(remoteViews, j);
            } else {
                this.mIndexMetaData.put(i, new android.widget.RemoteViewsAdapter.RemoteViewsIndexMetaData(remoteViews, j));
            }
            this.mIndexRemoteViews.put(i, remoteViews);
        }

        public android.widget.RemoteViewsAdapter.RemoteViewsMetaData getMetaData() {
            return this.mMetaData;
        }

        public android.widget.RemoteViewsAdapter.RemoteViewsMetaData getTemporaryMetaData() {
            return this.mTemporaryMetaData;
        }

        public android.widget.RemoteViews getRemoteViewsAt(int i) {
            return this.mIndexRemoteViews.get(i);
        }

        public android.widget.RemoteViewsAdapter.RemoteViewsIndexMetaData getMetaDataAt(int i) {
            return this.mIndexMetaData.get(i);
        }

        public void commitTemporaryMetaData() {
            synchronized (this.mTemporaryMetaData) {
                synchronized (this.mMetaData) {
                    this.mMetaData.set(this.mTemporaryMetaData);
                }
            }
        }

        private int getRemoteViewsBitmapMemoryUsage() {
            int i = 0;
            for (int size = this.mIndexRemoteViews.size() - 1; size >= 0; size--) {
                android.widget.RemoteViews valueAt = this.mIndexRemoteViews.valueAt(size);
                if (valueAt != null) {
                    i += valueAt.estimateMemoryUsage();
                }
            }
            return i;
        }

        private int getFarthestPositionFrom(int i, int[] iArr) {
            int i2 = 0;
            int i3 = 0;
            int i4 = -1;
            int i5 = -1;
            for (int size = this.mIndexRemoteViews.size() - 1; size >= 0; size--) {
                int keyAt = this.mIndexRemoteViews.keyAt(size);
                int abs = java.lang.Math.abs(keyAt - i);
                if (abs > i2 && java.util.Arrays.binarySearch(iArr, keyAt) < 0) {
                    i4 = keyAt;
                    i2 = abs;
                }
                if (abs >= i3) {
                    i5 = keyAt;
                    i3 = abs;
                }
            }
            if (i4 > -1) {
                return i4;
            }
            return i5;
        }

        public void queueRequestedPositionToLoad(int i) {
            this.mLastRequestedIndex = i;
            synchronized (this.mIndicesToLoad) {
                this.mIndicesToLoad.put(i, true);
            }
        }

        public boolean queuePositionsToBePreloadedFromRequestedPosition(int i) {
            int i2;
            if (this.mPreloadLowerBound <= i && i <= this.mPreloadUpperBound && java.lang.Math.abs(i - ((this.mPreloadUpperBound + this.mPreloadLowerBound) / 2)) < this.mMaxCountSlack) {
                return false;
            }
            synchronized (this.mMetaData) {
                i2 = this.mMetaData.count;
            }
            synchronized (this.mIndicesToLoad) {
                for (int size = this.mIndicesToLoad.size() - 1; size >= 0; size--) {
                    if (!this.mIndicesToLoad.valueAt(size)) {
                        this.mIndicesToLoad.removeAt(size);
                    }
                }
                int i3 = this.mMaxCount / 2;
                this.mPreloadLowerBound = i - i3;
                this.mPreloadUpperBound = i + i3;
                int min = java.lang.Math.min(this.mPreloadUpperBound, i2 - 1);
                for (int max = java.lang.Math.max(0, this.mPreloadLowerBound); max <= min; max++) {
                    if (this.mIndexRemoteViews.indexOfKey(max) < 0 && !this.mIndicesToLoad.get(max)) {
                        this.mIndicesToLoad.put(max, false);
                    }
                }
            }
            return true;
        }

        public int getNextIndexToLoad() {
            synchronized (this.mIndicesToLoad) {
                int indexOfValue = this.mIndicesToLoad.indexOfValue(true);
                if (indexOfValue < 0) {
                    indexOfValue = this.mIndicesToLoad.indexOfValue(false);
                }
                if (indexOfValue < 0) {
                    return -1;
                }
                int keyAt = this.mIndicesToLoad.keyAt(indexOfValue);
                this.mIndicesToLoad.removeAt(indexOfValue);
                return keyAt;
            }
        }

        public boolean containsRemoteViewAt(int i) {
            return this.mIndexRemoteViews.indexOfKey(i) >= 0;
        }

        public boolean containsMetaDataAt(int i) {
            return this.mIndexMetaData.indexOfKey(i) >= 0;
        }

        public void reset() {
            this.mPreloadLowerBound = 0;
            this.mPreloadUpperBound = -1;
            this.mLastRequestedIndex = -1;
            this.mIndexRemoteViews.clear();
            this.mIndexMetaData.clear();
            synchronized (this.mIndicesToLoad) {
                this.mIndicesToLoad.clear();
            }
        }
    }

    static class RemoteViewsCacheKey {
        final android.content.Intent.FilterComparison filter;
        final int widgetId;

        RemoteViewsCacheKey(android.content.Intent.FilterComparison filterComparison, int i) {
            this.filter = filterComparison;
            this.widgetId = i;
        }

        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof android.widget.RemoteViewsAdapter.RemoteViewsCacheKey)) {
                return false;
            }
            android.widget.RemoteViewsAdapter.RemoteViewsCacheKey remoteViewsCacheKey = (android.widget.RemoteViewsAdapter.RemoteViewsCacheKey) obj;
            return remoteViewsCacheKey.filter.equals(this.filter) && remoteViewsCacheKey.widgetId == this.widgetId;
        }

        public int hashCode() {
            return (this.filter == null ? 0 : this.filter.hashCode()) ^ (this.widgetId << 2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x00ed A[Catch: all -> 0x00f2, TryCatch #0 {, blocks: (B:12:0x009c, B:14:0x00ae, B:17:0x00bd, B:18:0x00cd, B:25:0x00e9, B:27:0x00ed, B:28:0x00f0, B:34:0x00df, B:35:0x00e0, B:20:0x00ce, B:22:0x00d8, B:23:0x00db), top: B:11:0x009c, inners: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public RemoteViewsAdapter(android.content.Context context, android.content.Intent intent, android.widget.RemoteViewsAdapter.RemoteAdapterConnectionCallback remoteAdapterConnectionCallback, boolean z) {
        this.mDataReady = false;
        this.mContext = context;
        this.mIntent = intent;
        if (this.mIntent == null) {
            throw new java.lang.IllegalArgumentException("Non-null Intent must be specified.");
        }
        this.mAppWidgetId = intent.getIntExtra("remoteAdapterAppWidgetId", -1);
        this.mRequestedViews = new android.widget.RemoteViewsAdapter.RemoteViewsFrameLayoutRefSet();
        this.mOnLightBackground = intent.getBooleanExtra("remoteAdapterOnLightBackground", false);
        intent.removeExtra("remoteAdapterAppWidgetId");
        intent.removeExtra("remoteAdapterOnLightBackground");
        this.mWorkerThread = new android.os.HandlerThread("RemoteViewsCache-loader");
        this.mWorkerThread.start();
        this.mMainHandler = new android.os.Handler(android.os.Looper.myLooper(), this);
        this.mServiceHandler = new android.widget.RemoteViewsAdapter.RemoteServiceHandler(this.mWorkerThread.getLooper(), this, context.getApplicationContext());
        this.mAsyncViewLoadExecutor = z ? new android.widget.RemoteViewsAdapter.HandlerThreadExecutor(this.mWorkerThread) : null;
        this.mCallback = remoteAdapterConnectionCallback;
        if (sCacheRemovalThread == null) {
            sCacheRemovalThread = new android.os.HandlerThread("RemoteViewsAdapter-cachePruner");
            sCacheRemovalThread.start();
            sCacheRemovalQueue = new android.os.Handler(sCacheRemovalThread.getLooper());
        }
        android.widget.RemoteViewsAdapter.RemoteViewsCacheKey remoteViewsCacheKey = new android.widget.RemoteViewsAdapter.RemoteViewsCacheKey(new android.content.Intent.FilterComparison(this.mIntent), this.mAppWidgetId);
        synchronized (sCachedRemoteViewsCaches) {
            android.widget.RemoteViewsAdapter.FixedSizeRemoteViewsCache fixedSizeRemoteViewsCache = sCachedRemoteViewsCaches.get(remoteViewsCacheKey);
            android.content.res.Configuration configuration = context.getResources().getConfiguration();
            if (fixedSizeRemoteViewsCache != null && (fixedSizeRemoteViewsCache.mConfiguration.diff(configuration) & CACHE_RESET_CONFIG_FLAGS) == 0) {
                this.mCache = sCachedRemoteViewsCaches.get(remoteViewsCacheKey);
                synchronized (this.mCache.mMetaData) {
                    if (this.mCache.mMetaData.count > 0) {
                        this.mDataReady = true;
                    }
                }
                if (!this.mDataReady) {
                    requestBindService();
                }
            }
            this.mCache = new android.widget.RemoteViewsAdapter.FixedSizeRemoteViewsCache(40, configuration);
            if (!this.mDataReady) {
            }
        }
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            this.mServiceHandler.unbindNow();
            this.mWorkerThread.quit();
        } finally {
            super.finalize();
        }
    }

    public boolean isDataReady() {
        return this.mDataReady;
    }

    public void setRemoteViewsInteractionHandler(android.widget.RemoteViews.InteractionHandler interactionHandler) {
        this.mRemoteViewsInteractionHandler = interactionHandler;
    }

    public void saveRemoteViewsCache() {
        int i;
        int size;
        final android.widget.RemoteViewsAdapter.RemoteViewsCacheKey remoteViewsCacheKey = new android.widget.RemoteViewsAdapter.RemoteViewsCacheKey(new android.content.Intent.FilterComparison(this.mIntent), this.mAppWidgetId);
        synchronized (sCachedRemoteViewsCaches) {
            if (sRemoteViewsCacheRemoveRunnables.containsKey(remoteViewsCacheKey)) {
                sCacheRemovalQueue.removeCallbacks(sRemoteViewsCacheRemoveRunnables.get(remoteViewsCacheKey));
                sRemoteViewsCacheRemoveRunnables.remove(remoteViewsCacheKey);
            }
            synchronized (this.mCache.mMetaData) {
                i = this.mCache.mMetaData.count;
            }
            synchronized (this.mCache) {
                size = this.mCache.mIndexRemoteViews.size();
            }
            if (i > 0 && size > 0) {
                sCachedRemoteViewsCaches.put(remoteViewsCacheKey, this.mCache);
            }
            java.lang.Runnable runnable = new java.lang.Runnable() { // from class: android.widget.RemoteViewsAdapter$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.widget.RemoteViewsAdapter.lambda$saveRemoteViewsCache$0(android.widget.RemoteViewsAdapter.RemoteViewsCacheKey.this);
                }
            };
            sRemoteViewsCacheRemoveRunnables.put(remoteViewsCacheKey, runnable);
            sCacheRemovalQueue.postDelayed(runnable, 5000L);
        }
    }

    static /* synthetic */ void lambda$saveRemoteViewsCache$0(android.widget.RemoteViewsAdapter.RemoteViewsCacheKey remoteViewsCacheKey) {
        synchronized (sCachedRemoteViewsCaches) {
            sCachedRemoteViewsCaches.remove(remoteViewsCacheKey);
            sRemoteViewsCacheRemoveRunnables.remove(remoteViewsCacheKey);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTemporaryMetaData(com.android.internal.widget.IRemoteViewsFactory iRemoteViewsFactory) {
        android.widget.RemoteViews viewAt;
        try {
            boolean hasStableIds = iRemoteViewsFactory.hasStableIds();
            int viewTypeCount = iRemoteViewsFactory.getViewTypeCount();
            int count = iRemoteViewsFactory.getCount();
            android.widget.RemoteViewsAdapter.LoadingViewTemplate loadingViewTemplate = new android.widget.RemoteViewsAdapter.LoadingViewTemplate(iRemoteViewsFactory.getLoadingView(), this.mContext);
            if (count > 0 && loadingViewTemplate.remoteViews == null && (viewAt = iRemoteViewsFactory.getViewAt(0)) != null) {
                loadingViewTemplate.loadFirstViewHeight(viewAt, this.mContext, new android.widget.RemoteViewsAdapter.HandlerThreadExecutor(this.mWorkerThread));
            }
            android.widget.RemoteViewsAdapter.RemoteViewsMetaData temporaryMetaData = this.mCache.getTemporaryMetaData();
            synchronized (temporaryMetaData) {
                temporaryMetaData.hasStableIds = hasStableIds;
                temporaryMetaData.viewTypeCount = viewTypeCount + 1;
                temporaryMetaData.count = count;
                temporaryMetaData.loadingTemplate = loadingViewTemplate;
            }
        } catch (android.os.RemoteException | java.lang.RuntimeException e) {
            android.util.Log.e(TAG, "Error in updateMetaData: " + e.getMessage());
            synchronized (this.mCache.getMetaData()) {
                this.mCache.getMetaData().reset();
                synchronized (this.mCache) {
                    this.mCache.reset();
                    this.mMainHandler.sendEmptyMessage(2);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateRemoteViews(com.android.internal.widget.IRemoteViewsFactory iRemoteViewsFactory, int i, boolean z) {
        boolean isViewTypeInRange;
        int i2;
        try {
            android.widget.RemoteViews viewAt = iRemoteViewsFactory.getViewAt(i);
            long itemId = iRemoteViewsFactory.getItemId(i);
            if (viewAt == null) {
                throw new java.lang.RuntimeException("Null remoteViews");
            }
            if (viewAt.mApplication != null) {
                if (this.mLastRemoteViewAppInfo != null && viewAt.hasSameAppInfo(this.mLastRemoteViewAppInfo)) {
                    viewAt.mApplication = this.mLastRemoteViewAppInfo;
                } else {
                    this.mLastRemoteViewAppInfo = viewAt.mApplication;
                }
            }
            int layoutId = viewAt.getLayoutId();
            android.widget.RemoteViewsAdapter.RemoteViewsMetaData metaData = this.mCache.getMetaData();
            synchronized (metaData) {
                isViewTypeInRange = metaData.isViewTypeInRange(layoutId);
                i2 = this.mCache.mMetaData.count;
            }
            synchronized (this.mCache) {
                if (isViewTypeInRange) {
                    this.mCache.insert(i, viewAt, itemId, getVisibleWindow(i2));
                    if (z) {
                        android.os.Message.obtain(this.mMainHandler, 5, i, 0, viewAt).sendToTarget();
                    }
                } else {
                    android.util.Log.e(TAG, "Error: widget's RemoteViewsFactory returns more view types than  indicated by getViewTypeCount() ");
                }
            }
        } catch (android.os.RemoteException | java.lang.RuntimeException e) {
            android.util.Log.e(TAG, "Error in updateRemoteViews(" + i + "): " + e.getMessage());
        }
    }

    public android.content.Intent getRemoteViewsServiceIntent() {
        return this.mIntent;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        int i;
        android.widget.RemoteViewsAdapter.RemoteViewsMetaData metaData = this.mCache.getMetaData();
        synchronized (metaData) {
            i = metaData.count;
        }
        return i;
    }

    @Override // android.widget.Adapter
    public java.lang.Object getItem(int i) {
        return null;
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        synchronized (this.mCache) {
            if (!this.mCache.containsMetaDataAt(i)) {
                return 0L;
            }
            return this.mCache.getMetaDataAt(i).itemId;
        }
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getItemViewType(int i) {
        int mappedViewType;
        synchronized (this.mCache) {
            if (!this.mCache.containsMetaDataAt(i)) {
                return 0;
            }
            int i2 = this.mCache.getMetaDataAt(i).typeId;
            android.widget.RemoteViewsAdapter.RemoteViewsMetaData metaData = this.mCache.getMetaData();
            synchronized (metaData) {
                mappedViewType = metaData.getMappedViewType(i2);
            }
            return mappedViewType;
        }
    }

    public void setVisibleRangeHint(int i, int i2) {
        this.mVisibleWindowLowerBound = i;
        this.mVisibleWindowUpperBound = i2;
    }

    @Override // android.widget.Adapter
    public android.view.View getView(int i, android.view.View view, android.view.ViewGroup viewGroup) {
        boolean queuePositionsToBePreloadedFromRequestedPosition;
        android.widget.RemoteViewsAdapter.RemoteViewsFrameLayout remoteViewsFrameLayout;
        synchronized (this.mCache) {
            android.widget.RemoteViews remoteViewsAt = this.mCache.getRemoteViewsAt(i);
            boolean z = remoteViewsAt != null;
            if (view != null && (view instanceof android.widget.RemoteViewsAdapter.RemoteViewsFrameLayout)) {
                this.mRequestedViews.removeView((android.widget.RemoteViewsAdapter.RemoteViewsFrameLayout) view);
            }
            if (!z) {
                requestBindService();
                queuePositionsToBePreloadedFromRequestedPosition = false;
            } else {
                queuePositionsToBePreloadedFromRequestedPosition = this.mCache.queuePositionsToBePreloadedFromRequestedPosition(i);
            }
            if (view instanceof android.widget.RemoteViewsAdapter.RemoteViewsFrameLayout) {
                remoteViewsFrameLayout = (android.widget.RemoteViewsAdapter.RemoteViewsFrameLayout) view;
            } else {
                remoteViewsFrameLayout = new android.widget.RemoteViewsAdapter.RemoteViewsFrameLayout(viewGroup.getContext(), this.mCache);
                remoteViewsFrameLayout.setExecutor(this.mAsyncViewLoadExecutor);
                remoteViewsFrameLayout.setOnLightBackground(this.mOnLightBackground);
            }
            if (z) {
                remoteViewsFrameLayout.onRemoteViewsLoaded(remoteViewsAt, this.mRemoteViewsInteractionHandler, false);
                if (queuePositionsToBePreloadedFromRequestedPosition) {
                    this.mServiceHandler.sendEmptyMessage(3);
                }
            } else {
                remoteViewsFrameLayout.onRemoteViewsLoaded(this.mCache.getMetaData().getLoadingTemplate(this.mContext).remoteViews, this.mRemoteViewsInteractionHandler, false);
                this.mRequestedViews.add(i, remoteViewsFrameLayout);
                this.mCache.queueRequestedPositionToLoad(i);
                this.mServiceHandler.sendEmptyMessage(3);
            }
        }
        return remoteViewsFrameLayout;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getViewTypeCount() {
        int i;
        android.widget.RemoteViewsAdapter.RemoteViewsMetaData metaData = this.mCache.getMetaData();
        synchronized (metaData) {
            i = metaData.viewTypeCount;
        }
        return i;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public boolean hasStableIds() {
        boolean z;
        android.widget.RemoteViewsAdapter.RemoteViewsMetaData metaData = this.mCache.getMetaData();
        synchronized (metaData) {
            z = metaData.hasStableIds;
        }
        return z;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public boolean isEmpty() {
        return getCount() <= 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int[] getVisibleWindow(int i) {
        int i2 = this.mVisibleWindowLowerBound;
        int i3 = this.mVisibleWindowUpperBound;
        int i4 = 0;
        if ((i2 == 0 && i3 == 0) || i2 < 0 || i3 < 0) {
            return new int[0];
        }
        if (i2 <= i3) {
            int[] iArr = new int[(i3 + 1) - i2];
            while (i2 <= i3) {
                iArr[i4] = i2;
                i2++;
                i4++;
            }
            return iArr;
        }
        int max = java.lang.Math.max(i, i2);
        int[] iArr2 = new int[(max - i2) + i3 + 1];
        int i5 = 0;
        while (i4 <= i3) {
            iArr2[i5] = i4;
            i4++;
            i5++;
        }
        while (i2 < max) {
            iArr2[i5] = i2;
            i2++;
            i5++;
        }
        return iArr2;
    }

    @Override // android.widget.BaseAdapter
    public void notifyDataSetChanged() {
        this.mServiceHandler.removeMessages(4);
        this.mServiceHandler.sendEmptyMessage(2);
    }

    void superNotifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(android.os.Message message) {
        switch (message.what) {
            case 1:
                this.mCache.commitTemporaryMetaData();
                break;
            case 2:
                superNotifyDataSetChanged();
                break;
            case 3:
                if (this.mCallback != null) {
                    this.mCallback.onRemoteAdapterConnected();
                    break;
                }
                break;
            case 4:
                if (this.mCallback != null) {
                    this.mCallback.onRemoteAdapterDisconnected();
                    break;
                }
                break;
            case 5:
                this.mRequestedViews.notifyOnRemoteViewsLoaded(message.arg1, (android.widget.RemoteViews) message.obj);
                break;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestBindService() {
        this.mServiceHandler.removeMessages(4);
        android.os.Message.obtain(this.mServiceHandler, 1, this.mAppWidgetId, 0, this.mIntent).sendToTarget();
    }

    private static class HandlerThreadExecutor implements java.util.concurrent.Executor {
        private final android.os.HandlerThread mThread;

        HandlerThreadExecutor(android.os.HandlerThread handlerThread) {
            this.mThread = handlerThread;
        }

        @Override // java.util.concurrent.Executor
        public void execute(java.lang.Runnable runnable) {
            if (java.lang.Thread.currentThread().getId() == this.mThread.getId()) {
                runnable.run();
            } else {
                new android.os.Handler(this.mThread.getLooper()).post(runnable);
            }
        }
    }

    private static class LoadingViewTemplate {
        public int defaultHeight;
        public final android.widget.RemoteViews remoteViews;

        LoadingViewTemplate(android.widget.RemoteViews remoteViews, android.content.Context context) {
            this.remoteViews = remoteViews;
            this.defaultHeight = java.lang.Math.round(context.getResources().getDisplayMetrics().density * 50.0f);
        }

        public void loadFirstViewHeight(android.widget.RemoteViews remoteViews, android.content.Context context, java.util.concurrent.Executor executor) {
            remoteViews.applyAsync(context, new android.widget.RemoteViewsAdapter.RemoteViewsFrameLayout(context, null), executor, new android.widget.RemoteViews.OnViewAppliedListener() { // from class: android.widget.RemoteViewsAdapter.LoadingViewTemplate.1
                @Override // android.widget.RemoteViews.OnViewAppliedListener
                public void onViewApplied(android.view.View view) {
                    try {
                        view.measure(android.view.View.MeasureSpec.makeMeasureSpec(0, 0), android.view.View.MeasureSpec.makeMeasureSpec(0, 0));
                        android.widget.RemoteViewsAdapter.LoadingViewTemplate.this.defaultHeight = view.getMeasuredHeight();
                    } catch (java.lang.Exception e) {
                        onError(e);
                    }
                }

                @Override // android.widget.RemoteViews.OnViewAppliedListener
                public void onError(java.lang.Exception exc) {
                    android.util.Log.w(android.widget.RemoteViewsAdapter.TAG, "Error inflating first RemoteViews", exc);
                }
            });
        }
    }
}
