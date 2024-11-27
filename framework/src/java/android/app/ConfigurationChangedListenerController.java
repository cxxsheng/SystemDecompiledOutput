package android.app;

/* loaded from: classes.dex */
class ConfigurationChangedListenerController {
    private final java.lang.Object mLock = new java.lang.Object();
    private final java.util.List<android.app.ConfigurationChangedListenerController.ListenerContainer> mListenerContainers = new java.util.ArrayList();

    ConfigurationChangedListenerController() {
    }

    void addListener(java.util.concurrent.Executor executor, java.util.function.Consumer<android.os.IBinder> consumer) {
        synchronized (this.mLock) {
            if (indexOf(consumer) > -1) {
                return;
            }
            this.mListenerContainers.add(new android.app.ConfigurationChangedListenerController.ListenerContainer(executor, consumer));
        }
    }

    void removeListener(java.util.function.Consumer<android.os.IBinder> consumer) {
        synchronized (this.mLock) {
            int indexOf = indexOf(consumer);
            if (indexOf > -1) {
                this.mListenerContainers.remove(indexOf);
            }
        }
    }

    void dispatchOnConfigurationChanged(android.os.IBinder iBinder) {
        java.util.ArrayList arrayList;
        synchronized (this.mLock) {
            arrayList = new java.util.ArrayList(this.mListenerContainers);
        }
        for (int i = 0; i < arrayList.size(); i++) {
            ((android.app.ConfigurationChangedListenerController.ListenerContainer) arrayList.get(i)).accept(iBinder);
        }
    }

    private int indexOf(java.util.function.Consumer<android.os.IBinder> consumer) {
        for (int i = 0; i < this.mListenerContainers.size(); i++) {
            if (this.mListenerContainers.get(i).isMatch(consumer)) {
                return i;
            }
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class ListenerContainer {
        private final java.util.function.Consumer<android.os.IBinder> mConsumer;
        private final java.util.concurrent.Executor mExecutor;

        ListenerContainer(java.util.concurrent.Executor executor, java.util.function.Consumer<android.os.IBinder> consumer) {
            this.mExecutor = executor;
            this.mConsumer = consumer;
        }

        public boolean isMatch(java.util.function.Consumer<android.os.IBinder> consumer) {
            return this.mConsumer.equals(consumer);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$accept$0(android.os.IBinder iBinder) {
            this.mConsumer.accept(iBinder);
        }

        public void accept(final android.os.IBinder iBinder) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.app.ConfigurationChangedListenerController$ListenerContainer$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.app.ConfigurationChangedListenerController.ListenerContainer.this.lambda$accept$0(iBinder);
                }
            });
        }
    }
}
