package android.widget;

/* loaded from: classes4.dex */
public abstract class RemoteViewsService extends android.app.Service {
    private static final java.lang.String LOG_TAG = "RemoteViewsService";
    private static final int MAX_NUM_ENTRY = 10;
    private static final java.util.HashMap<android.content.Intent.FilterComparison, android.widget.RemoteViewsService.RemoteViewsFactory> sRemoteViewFactories = new java.util.HashMap<>();
    private static final java.lang.Object sLock = new java.lang.Object();

    public interface RemoteViewsFactory {
        int getCount();

        long getItemId(int i);

        android.widget.RemoteViews getLoadingView();

        android.widget.RemoteViews getViewAt(int i);

        int getViewTypeCount();

        boolean hasStableIds();

        void onCreate();

        void onDataSetChanged();

        void onDestroy();
    }

    public abstract android.widget.RemoteViewsService.RemoteViewsFactory onGetViewFactory(android.content.Intent intent);

    private static class RemoteViewsFactoryAdapter extends com.android.internal.widget.IRemoteViewsFactory.Stub {
        private android.widget.RemoteViewsService.RemoteViewsFactory mFactory;
        private boolean mIsCreated;

        public RemoteViewsFactoryAdapter(android.widget.RemoteViewsService.RemoteViewsFactory remoteViewsFactory, boolean z) {
            this.mFactory = remoteViewsFactory;
            this.mIsCreated = z;
        }

        @Override // com.android.internal.widget.IRemoteViewsFactory
        public synchronized boolean isCreated() {
            return this.mIsCreated;
        }

        @Override // com.android.internal.widget.IRemoteViewsFactory
        public synchronized void onDataSetChanged() {
            try {
                this.mFactory.onDataSetChanged();
            } catch (java.lang.Exception e) {
                java.lang.Thread.getDefaultUncaughtExceptionHandler().uncaughtException(java.lang.Thread.currentThread(), e);
            }
        }

        @Override // com.android.internal.widget.IRemoteViewsFactory
        public synchronized void onDataSetChangedAsync() {
            onDataSetChanged();
        }

        @Override // com.android.internal.widget.IRemoteViewsFactory
        public synchronized int getCount() {
            int i;
            try {
                i = this.mFactory.getCount();
            } catch (java.lang.Exception e) {
                java.lang.Thread.getDefaultUncaughtExceptionHandler().uncaughtException(java.lang.Thread.currentThread(), e);
                i = 0;
            }
            return i;
        }

        @Override // com.android.internal.widget.IRemoteViewsFactory
        public synchronized android.widget.RemoteViews getViewAt(int i) {
            android.widget.RemoteViews remoteViews;
            remoteViews = null;
            try {
                remoteViews = this.mFactory.getViewAt(i);
                if (remoteViews != null) {
                    remoteViews.addFlags(2);
                }
            } catch (java.lang.Exception e) {
                java.lang.Thread.getDefaultUncaughtExceptionHandler().uncaughtException(java.lang.Thread.currentThread(), e);
            }
            return remoteViews;
        }

        @Override // com.android.internal.widget.IRemoteViewsFactory
        public synchronized android.widget.RemoteViews getLoadingView() {
            android.widget.RemoteViews remoteViews;
            try {
                remoteViews = this.mFactory.getLoadingView();
            } catch (java.lang.Exception e) {
                java.lang.Thread.getDefaultUncaughtExceptionHandler().uncaughtException(java.lang.Thread.currentThread(), e);
                remoteViews = null;
            }
            return remoteViews;
        }

        @Override // com.android.internal.widget.IRemoteViewsFactory
        public synchronized int getViewTypeCount() {
            int i;
            try {
                i = this.mFactory.getViewTypeCount();
            } catch (java.lang.Exception e) {
                java.lang.Thread.getDefaultUncaughtExceptionHandler().uncaughtException(java.lang.Thread.currentThread(), e);
                i = 0;
            }
            return i;
        }

        @Override // com.android.internal.widget.IRemoteViewsFactory
        public synchronized long getItemId(int i) {
            long j;
            try {
                j = this.mFactory.getItemId(i);
            } catch (java.lang.Exception e) {
                java.lang.Thread.getDefaultUncaughtExceptionHandler().uncaughtException(java.lang.Thread.currentThread(), e);
                j = 0;
            }
            return j;
        }

        @Override // com.android.internal.widget.IRemoteViewsFactory
        public synchronized boolean hasStableIds() {
            boolean z;
            try {
                z = this.mFactory.hasStableIds();
            } catch (java.lang.Exception e) {
                java.lang.Thread.getDefaultUncaughtExceptionHandler().uncaughtException(java.lang.Thread.currentThread(), e);
                z = false;
            }
            return z;
        }

        @Override // com.android.internal.widget.IRemoteViewsFactory
        public void onDestroy(android.content.Intent intent) {
            synchronized (android.widget.RemoteViewsService.sLock) {
                android.content.Intent.FilterComparison filterComparison = new android.content.Intent.FilterComparison(intent);
                if (android.widget.RemoteViewsService.sRemoteViewFactories.containsKey(filterComparison)) {
                    try {
                        ((android.widget.RemoteViewsService.RemoteViewsFactory) android.widget.RemoteViewsService.sRemoteViewFactories.get(filterComparison)).onDestroy();
                    } catch (java.lang.Exception e) {
                        java.lang.Thread.getDefaultUncaughtExceptionHandler().uncaughtException(java.lang.Thread.currentThread(), e);
                    }
                    android.widget.RemoteViewsService.sRemoteViewFactories.remove(filterComparison);
                }
            }
        }

        @Override // com.android.internal.widget.IRemoteViewsFactory
        public android.widget.RemoteViews.RemoteCollectionItems getRemoteCollectionItems() {
            android.widget.RemoteViews.RemoteCollectionItems build = new android.widget.RemoteViews.RemoteCollectionItems.Builder().build();
            try {
                android.widget.RemoteViews.RemoteCollectionItems.Builder builder = new android.widget.RemoteViews.RemoteCollectionItems.Builder();
                this.mFactory.onDataSetChanged();
                builder.setHasStableIds(this.mFactory.hasStableIds());
                int min = java.lang.Math.min(this.mFactory.getCount(), 10);
                for (int i = 0; i < min; i++) {
                    builder.addItem(this.mFactory.getItemId(i), this.mFactory.getViewAt(i));
                }
                return builder.build();
            } catch (java.lang.Exception e) {
                java.lang.Thread.getDefaultUncaughtExceptionHandler().uncaughtException(java.lang.Thread.currentThread(), e);
                return build;
            }
        }
    }

    @Override // android.app.Service
    public android.os.IBinder onBind(android.content.Intent intent) {
        android.widget.RemoteViewsService.RemoteViewsFactory remoteViewsFactory;
        boolean z;
        android.widget.RemoteViewsService.RemoteViewsFactoryAdapter remoteViewsFactoryAdapter;
        synchronized (sLock) {
            android.content.Intent.FilterComparison filterComparison = new android.content.Intent.FilterComparison(intent);
            if (!sRemoteViewFactories.containsKey(filterComparison)) {
                remoteViewsFactory = onGetViewFactory(intent);
                sRemoteViewFactories.put(filterComparison, remoteViewsFactory);
                remoteViewsFactory.onCreate();
                z = false;
            } else {
                remoteViewsFactory = sRemoteViewFactories.get(filterComparison);
                z = true;
            }
            remoteViewsFactoryAdapter = new android.widget.RemoteViewsService.RemoteViewsFactoryAdapter(remoteViewsFactory, z);
        }
        return remoteViewsFactoryAdapter;
    }
}
