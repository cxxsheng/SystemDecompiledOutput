package android.window;

/* loaded from: classes4.dex */
public class ProxyOnBackInvokedDispatcher implements android.window.OnBackInvokedDispatcher {
    private final android.window.WindowOnBackInvokedDispatcher.Checker mChecker;
    private android.window.ImeOnBackInvokedDispatcher mImeDispatcher;
    private final java.util.List<android.util.Pair<android.window.OnBackInvokedCallback, java.lang.Integer>> mCallbacks = new java.util.ArrayList();
    private final java.lang.Object mLock = new java.lang.Object();
    private android.window.OnBackInvokedDispatcher mActualDispatcher = null;

    public ProxyOnBackInvokedDispatcher(android.content.Context context) {
        this.mChecker = new android.window.WindowOnBackInvokedDispatcher.Checker(context);
    }

    @Override // android.window.OnBackInvokedDispatcher
    public void registerOnBackInvokedCallback(int i, android.window.OnBackInvokedCallback onBackInvokedCallback) {
        if (this.mChecker.checkApplicationCallbackRegistration(i, onBackInvokedCallback)) {
            registerOnBackInvokedCallbackUnchecked(onBackInvokedCallback, i);
        }
    }

    @Override // android.window.OnBackInvokedDispatcher
    public void registerSystemOnBackInvokedCallback(android.window.OnBackInvokedCallback onBackInvokedCallback) {
        registerOnBackInvokedCallbackUnchecked(onBackInvokedCallback, -1);
    }

    @Override // android.window.OnBackInvokedDispatcher
    public void unregisterOnBackInvokedCallback(final android.window.OnBackInvokedCallback onBackInvokedCallback) {
        synchronized (this.mLock) {
            this.mCallbacks.removeIf(new java.util.function.Predicate() { // from class: android.window.ProxyOnBackInvokedDispatcher$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean equals;
                    equals = ((android.window.OnBackInvokedCallback) ((android.util.Pair) obj).first).equals(android.window.OnBackInvokedCallback.this);
                    return equals;
                }
            });
            if (this.mActualDispatcher != null) {
                this.mActualDispatcher.unregisterOnBackInvokedCallback(onBackInvokedCallback);
            }
        }
    }

    private void registerOnBackInvokedCallbackUnchecked(android.window.OnBackInvokedCallback onBackInvokedCallback, int i) {
        synchronized (this.mLock) {
            this.mCallbacks.add(android.util.Pair.create(onBackInvokedCallback, java.lang.Integer.valueOf(i)));
            if (this.mActualDispatcher != null) {
                if (i <= -1) {
                    this.mActualDispatcher.registerSystemOnBackInvokedCallback(onBackInvokedCallback);
                } else {
                    this.mActualDispatcher.registerOnBackInvokedCallback(i, onBackInvokedCallback);
                }
            }
        }
    }

    private void transferCallbacksToDispatcher() {
        if (this.mActualDispatcher == null) {
            return;
        }
        if (this.mImeDispatcher != null) {
            this.mActualDispatcher.setImeOnBackInvokedDispatcher(this.mImeDispatcher);
        }
        for (android.util.Pair<android.window.OnBackInvokedCallback, java.lang.Integer> pair : this.mCallbacks) {
            int intValue = pair.second.intValue();
            if (intValue >= 0) {
                this.mActualDispatcher.registerOnBackInvokedCallback(intValue, pair.first);
            } else {
                this.mActualDispatcher.registerSystemOnBackInvokedCallback(pair.first);
            }
        }
        this.mCallbacks.clear();
        this.mImeDispatcher = null;
    }

    private void clearCallbacksOnDispatcher() {
        if (this.mActualDispatcher == null) {
            return;
        }
        java.util.Iterator<android.util.Pair<android.window.OnBackInvokedCallback, java.lang.Integer>> it = this.mCallbacks.iterator();
        while (it.hasNext()) {
            this.mActualDispatcher.unregisterOnBackInvokedCallback(it.next().first);
        }
    }

    public void reset() {
        synchronized (this.mLock) {
            this.mCallbacks.clear();
            this.mImeDispatcher = null;
        }
    }

    public void setActualDispatcher(android.window.OnBackInvokedDispatcher onBackInvokedDispatcher) {
        synchronized (this.mLock) {
            if (onBackInvokedDispatcher == this.mActualDispatcher) {
                return;
            }
            clearCallbacksOnDispatcher();
            this.mActualDispatcher = onBackInvokedDispatcher;
            transferCallbacksToDispatcher();
        }
    }

    @Override // android.window.OnBackInvokedDispatcher
    public void setImeOnBackInvokedDispatcher(android.window.ImeOnBackInvokedDispatcher imeOnBackInvokedDispatcher) {
        if (this.mActualDispatcher != null) {
            this.mActualDispatcher.setImeOnBackInvokedDispatcher(imeOnBackInvokedDispatcher);
        } else {
            this.mImeDispatcher = imeOnBackInvokedDispatcher;
        }
    }
}
