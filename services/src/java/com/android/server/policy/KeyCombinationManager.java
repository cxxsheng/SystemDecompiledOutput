package com.android.server.policy;

/* loaded from: classes2.dex */
public class KeyCombinationManager {
    private static final long COMBINE_KEY_DELAY_MILLIS = 150;
    private static final java.lang.String TAG = "KeyCombinationManager";
    private final android.os.Handler mHandler;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule mTriggeredRule;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseLongArray mDownTimes = new android.util.SparseLongArray(2);
    private final java.util.ArrayList<com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule> mRules = new java.util.ArrayList<>();
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.ArrayList<com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule> mActiveRules = new java.util.ArrayList<>();

    static abstract class TwoKeysCombinationRule {
        private int mKeyCode1;
        private int mKeyCode2;

        /* JADX INFO: Access modifiers changed from: package-private */
        public abstract void cancel();

        abstract void execute();

        TwoKeysCombinationRule(int i, int i2) {
            this.mKeyCode1 = i;
            this.mKeyCode2 = i2;
        }

        boolean preCondition() {
            return true;
        }

        boolean shouldInterceptKey(int i) {
            return preCondition() && (i == this.mKeyCode1 || i == this.mKeyCode2);
        }

        boolean shouldInterceptKeys(android.util.SparseLongArray sparseLongArray) {
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            if (sparseLongArray.get(this.mKeyCode1) > 0 && sparseLongArray.get(this.mKeyCode2) > 0 && uptimeMillis <= sparseLongArray.get(this.mKeyCode1) + 150 && uptimeMillis <= sparseLongArray.get(this.mKeyCode2) + 150) {
                return true;
            }
            return false;
        }

        long getKeyInterceptDelayMs() {
            return 150L;
        }

        public java.lang.String toString() {
            return android.view.KeyEvent.keyCodeToString(this.mKeyCode1) + " + " + android.view.KeyEvent.keyCodeToString(this.mKeyCode2);
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule)) {
                return false;
            }
            com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule twoKeysCombinationRule = (com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule) obj;
            if (this.mKeyCode1 == twoKeysCombinationRule.mKeyCode1 && this.mKeyCode2 == twoKeysCombinationRule.mKeyCode2) {
                return true;
            }
            return this.mKeyCode1 == twoKeysCombinationRule.mKeyCode2 && this.mKeyCode2 == twoKeysCombinationRule.mKeyCode1;
        }

        public int hashCode() {
            return (this.mKeyCode1 * 31) + this.mKeyCode2;
        }
    }

    KeyCombinationManager(android.os.Handler handler) {
        this.mHandler = handler;
    }

    void addRule(com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule twoKeysCombinationRule) {
        if (this.mRules.contains(twoKeysCombinationRule)) {
            throw new java.lang.IllegalArgumentException("Rule : " + twoKeysCombinationRule + " already exists.");
        }
        this.mRules.add(twoKeysCombinationRule);
    }

    void removeRule(com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule twoKeysCombinationRule) {
        this.mRules.remove(twoKeysCombinationRule);
    }

    boolean interceptKey(android.view.KeyEvent keyEvent, boolean z) {
        boolean interceptKeyLocked;
        synchronized (this.mLock) {
            interceptKeyLocked = interceptKeyLocked(keyEvent, z);
        }
        return interceptKeyLocked;
    }

    private boolean interceptKeyLocked(android.view.KeyEvent keyEvent, boolean z) {
        boolean z2 = keyEvent.getAction() == 0;
        final int keyCode = keyEvent.getKeyCode();
        int size = this.mActiveRules.size();
        long eventTime = keyEvent.getEventTime();
        if (z && z2) {
            if (this.mDownTimes.size() > 0) {
                if (size > 0 && eventTime > this.mDownTimes.valueAt(0) + 150) {
                    forAllRules(this.mActiveRules, new java.util.function.Consumer() { // from class: com.android.server.policy.KeyCombinationManager$$ExternalSyntheticLambda2
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            ((com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule) obj).cancel();
                        }
                    });
                    this.mActiveRules.clear();
                    return false;
                }
                if (size == 0) {
                    return false;
                }
            }
            if (this.mDownTimes.get(keyCode) != 0) {
                return false;
            }
            this.mDownTimes.put(keyCode, eventTime);
            if (this.mDownTimes.size() == 1) {
                this.mTriggeredRule = null;
                forAllRules(this.mRules, new java.util.function.Consumer() { // from class: com.android.server.policy.KeyCombinationManager$$ExternalSyntheticLambda3
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.policy.KeyCombinationManager.this.lambda$interceptKeyLocked$1(keyCode, (com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule) obj);
                    }
                });
            } else {
                if (this.mTriggeredRule != null) {
                    return true;
                }
                forAllActiveRules(new com.android.internal.util.ToBooleanFunction() { // from class: com.android.server.policy.KeyCombinationManager$$ExternalSyntheticLambda4
                    public final boolean apply(java.lang.Object obj) {
                        boolean lambda$interceptKeyLocked$2;
                        lambda$interceptKeyLocked$2 = com.android.server.policy.KeyCombinationManager.this.lambda$interceptKeyLocked$2((com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule) obj);
                        return lambda$interceptKeyLocked$2;
                    }
                });
                this.mActiveRules.clear();
                if (this.mTriggeredRule != null) {
                    this.mActiveRules.add(this.mTriggeredRule);
                    return true;
                }
            }
        } else {
            this.mDownTimes.delete(keyCode);
            for (int i = size - 1; i >= 0; i--) {
                final com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule twoKeysCombinationRule = this.mActiveRules.get(i);
                if (twoKeysCombinationRule.shouldInterceptKey(keyCode)) {
                    android.os.Handler handler = this.mHandler;
                    java.util.Objects.requireNonNull(twoKeysCombinationRule);
                    handler.post(new java.lang.Runnable() { // from class: com.android.server.policy.KeyCombinationManager$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule.this.cancel();
                        }
                    });
                    this.mActiveRules.remove(i);
                }
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$interceptKeyLocked$1(int i, com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule twoKeysCombinationRule) {
        if (twoKeysCombinationRule.shouldInterceptKey(i)) {
            this.mActiveRules.add(twoKeysCombinationRule);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$interceptKeyLocked$2(final com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule twoKeysCombinationRule) {
        if (!twoKeysCombinationRule.shouldInterceptKeys(this.mDownTimes)) {
            return false;
        }
        android.util.Log.v(TAG, "Performing combination rule : " + twoKeysCombinationRule);
        android.os.Handler handler = this.mHandler;
        java.util.Objects.requireNonNull(twoKeysCombinationRule);
        handler.post(new java.lang.Runnable() { // from class: com.android.server.policy.KeyCombinationManager$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule.this.execute();
            }
        });
        this.mTriggeredRule = twoKeysCombinationRule;
        return true;
    }

    long getKeyInterceptTimeout(int i) {
        synchronized (this.mLock) {
            try {
                long j = 0;
                if (this.mDownTimes.get(i) == 0) {
                    return 0L;
                }
                java.util.Iterator<com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule> it = this.mActiveRules.iterator();
                while (it.hasNext()) {
                    com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule next = it.next();
                    if (next.shouldInterceptKey(i)) {
                        j = java.lang.Math.max(j, next.getKeyInterceptDelayMs());
                    }
                }
                return this.mDownTimes.get(i) + java.lang.Math.min(j, 150L);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    boolean isKeyConsumed(android.view.KeyEvent keyEvent) {
        synchronized (this.mLock) {
            try {
                boolean z = false;
                if ((keyEvent.getFlags() & 1024) != 0) {
                    return false;
                }
                if (this.mTriggeredRule != null && this.mTriggeredRule.shouldInterceptKey(keyEvent.getKeyCode())) {
                    z = true;
                }
                return z;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    boolean isPowerKeyIntercepted() {
        synchronized (this.mLock) {
            try {
                if (forAllActiveRules(new com.android.internal.util.ToBooleanFunction() { // from class: com.android.server.policy.KeyCombinationManager$$ExternalSyntheticLambda0
                    public final boolean apply(java.lang.Object obj) {
                        boolean lambda$isPowerKeyIntercepted$3;
                        lambda$isPowerKeyIntercepted$3 = com.android.server.policy.KeyCombinationManager.lambda$isPowerKeyIntercepted$3((com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule) obj);
                        return lambda$isPowerKeyIntercepted$3;
                    }
                })) {
                    return this.mDownTimes.size() > 1 || this.mDownTimes.get(26) == 0;
                }
                return false;
            } finally {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$isPowerKeyIntercepted$3(com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule twoKeysCombinationRule) {
        return twoKeysCombinationRule.shouldInterceptKey(26);
    }

    private void forAllRules(java.util.ArrayList<com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule> arrayList, java.util.function.Consumer<com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule> consumer) {
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            consumer.accept(arrayList.get(i));
        }
    }

    private boolean forAllActiveRules(com.android.internal.util.ToBooleanFunction<com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule> toBooleanFunction) {
        int size = this.mActiveRules.size();
        for (int i = 0; i < size; i++) {
            if (toBooleanFunction.apply(this.mActiveRules.get(i))) {
                return true;
            }
        }
        return false;
    }

    void dump(final java.lang.String str, final java.io.PrintWriter printWriter) {
        printWriter.println(str + "KeyCombination rules:");
        forAllRules(this.mRules, new java.util.function.Consumer() { // from class: com.android.server.policy.KeyCombinationManager$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.policy.KeyCombinationManager.lambda$dump$4(printWriter, str, (com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dump$4(java.io.PrintWriter printWriter, java.lang.String str, com.android.server.policy.KeyCombinationManager.TwoKeysCombinationRule twoKeysCombinationRule) {
        printWriter.println(str + "  " + twoKeysCombinationRule);
    }
}
