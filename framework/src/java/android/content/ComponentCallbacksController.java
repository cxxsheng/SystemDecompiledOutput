package android.content;

/* loaded from: classes.dex */
public class ComponentCallbacksController {
    private java.util.List<android.content.ComponentCallbacks> mComponentCallbacks;
    private final java.lang.Object mLock = new java.lang.Object();

    public void registerCallbacks(android.content.ComponentCallbacks componentCallbacks) {
        synchronized (this.mLock) {
            if (this.mComponentCallbacks == null) {
                this.mComponentCallbacks = new java.util.ArrayList();
            }
            this.mComponentCallbacks.add(componentCallbacks);
        }
    }

    public void unregisterCallbacks(android.content.ComponentCallbacks componentCallbacks) {
        synchronized (this.mLock) {
            if (this.mComponentCallbacks != null && !this.mComponentCallbacks.isEmpty()) {
                this.mComponentCallbacks.remove(componentCallbacks);
            }
        }
    }

    public void clearCallbacks() {
        synchronized (this.mLock) {
            if (this.mComponentCallbacks != null) {
                this.mComponentCallbacks.clear();
            }
        }
    }

    public void dispatchConfigurationChanged(final android.content.res.Configuration configuration) {
        forAllComponentCallbacks(new java.util.function.Consumer() { // from class: android.content.ComponentCallbacksController$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((android.content.ComponentCallbacks) obj).onConfigurationChanged(android.content.res.Configuration.this);
            }
        });
    }

    public void dispatchLowMemory() {
        forAllComponentCallbacks(new java.util.function.Consumer() { // from class: android.content.ComponentCallbacksController$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((android.content.ComponentCallbacks) obj).onLowMemory();
            }
        });
    }

    public void dispatchTrimMemory(final int i) {
        forAllComponentCallbacks(new java.util.function.Consumer() { // from class: android.content.ComponentCallbacksController$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                android.content.ComponentCallbacksController.lambda$dispatchTrimMemory$1(i, (android.content.ComponentCallbacks) obj);
            }
        });
    }

    static /* synthetic */ void lambda$dispatchTrimMemory$1(int i, android.content.ComponentCallbacks componentCallbacks) {
        if (componentCallbacks instanceof android.content.ComponentCallbacks2) {
            ((android.content.ComponentCallbacks2) componentCallbacks).onTrimMemory(i);
        }
    }

    private void forAllComponentCallbacks(java.util.function.Consumer<android.content.ComponentCallbacks> consumer) {
        synchronized (this.mLock) {
            if (this.mComponentCallbacks != null && !this.mComponentCallbacks.isEmpty()) {
                int size = this.mComponentCallbacks.size();
                android.content.ComponentCallbacks[] componentCallbacksArr = new android.content.ComponentCallbacks[size];
                this.mComponentCallbacks.toArray(componentCallbacksArr);
                for (int i = 0; i < size; i++) {
                    consumer.accept(componentCallbacksArr[i]);
                }
            }
        }
    }
}
