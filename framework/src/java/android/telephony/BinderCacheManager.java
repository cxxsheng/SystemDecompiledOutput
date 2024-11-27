package android.telephony;

/* loaded from: classes3.dex */
public class BinderCacheManager<T extends android.os.IInterface> {
    private final android.telephony.BinderCacheManager.BinderInterfaceFactory<T> mBinderInterfaceFactory;
    private final java.util.concurrent.atomic.AtomicReference<android.telephony.BinderCacheManager<T>.BinderDeathTracker> mCachedConnection = new java.util.concurrent.atomic.AtomicReference<>();

    public interface BinderInterfaceFactory<T> {
        T create();
    }

    /* JADX INFO: Access modifiers changed from: private */
    class BinderDeathTracker implements android.os.IBinder.DeathRecipient {
        private final T mConnection;
        private final java.util.HashMap<java.lang.Object, java.lang.Runnable> mListeners = new java.util.HashMap<>();

        BinderDeathTracker(T t) {
            this.mConnection = t;
            try {
                this.mConnection.asBinder().linkToDeath(this, 0);
            } catch (android.os.RemoteException e) {
            }
        }

        public boolean addListener(java.lang.Object obj, java.lang.Runnable runnable) {
            synchronized (this.mListeners) {
                if (!isAlive()) {
                    return false;
                }
                this.mListeners.put(obj, runnable);
                return true;
            }
        }

        public void removeListener(java.lang.Object obj) {
            synchronized (this.mListeners) {
                this.mListeners.remove(obj);
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            java.util.ArrayList arrayList;
            synchronized (this.mListeners) {
                arrayList = new java.util.ArrayList(this.mListeners.values());
                this.mListeners.clear();
                try {
                    this.mConnection.asBinder().unlinkToDeath(this, 0);
                } catch (java.util.NoSuchElementException e) {
                }
            }
            arrayList.forEach(new android.telephony.BinderCacheManager$BinderDeathTracker$$ExternalSyntheticLambda0());
        }

        public T getConnection() {
            return this.mConnection;
        }

        public boolean isAlive() {
            return this.mConnection.asBinder().isBinderAlive();
        }
    }

    public BinderCacheManager(android.telephony.BinderCacheManager.BinderInterfaceFactory<T> binderInterfaceFactory) {
        this.mBinderInterfaceFactory = binderInterfaceFactory;
    }

    public T listenOnBinder(java.lang.Object obj, java.lang.Runnable runnable) {
        android.telephony.BinderCacheManager<T>.BinderDeathTracker tracker;
        if (obj == null || runnable == null || (tracker = getTracker()) == null || !tracker.addListener(obj, runnable)) {
            return null;
        }
        return (T) tracker.getConnection();
    }

    public T getBinder() {
        android.telephony.BinderCacheManager<T>.BinderDeathTracker tracker = getTracker();
        if (tracker != null) {
            return (T) tracker.getConnection();
        }
        return null;
    }

    public T removeRunnable(java.lang.Object obj) {
        android.telephony.BinderCacheManager<T>.BinderDeathTracker tracker;
        if (obj == null || (tracker = getTracker()) == null) {
            return null;
        }
        tracker.removeListener(obj);
        return (T) tracker.getConnection();
    }

    private android.telephony.BinderCacheManager<T>.BinderDeathTracker getTracker() {
        return this.mCachedConnection.updateAndGet(new java.util.function.UnaryOperator() { // from class: android.telephony.BinderCacheManager$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                android.telephony.BinderCacheManager.BinderDeathTracker lambda$getTracker$0;
                lambda$getTracker$0 = android.telephony.BinderCacheManager.this.lambda$getTracker$0((android.telephony.BinderCacheManager.BinderDeathTracker) obj);
                return lambda$getTracker$0;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.telephony.BinderCacheManager.BinderDeathTracker lambda$getTracker$0(android.telephony.BinderCacheManager.BinderDeathTracker binderDeathTracker) {
        if (binderDeathTracker == null || !binderDeathTracker.isAlive()) {
            T create = this.mBinderInterfaceFactory.create();
            binderDeathTracker = create != null ? new android.telephony.BinderCacheManager.BinderDeathTracker(create) : null;
        }
        if (binderDeathTracker == null || !binderDeathTracker.isAlive()) {
            return null;
        }
        return binderDeathTracker;
    }
}
