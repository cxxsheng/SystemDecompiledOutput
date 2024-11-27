package com.android.server.inputmethod;

/* loaded from: classes2.dex */
public final class ImeTrackerService extends com.android.internal.inputmethod.IImeTracker.Stub {
    private static final java.lang.String TAG = "ImeTracker";
    private static final long TIMEOUT_MS = 10000;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.os.Handler mHandler;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final com.android.server.inputmethod.ImeTrackerService.History mHistory = new com.android.server.inputmethod.ImeTrackerService.History();
    private final java.lang.Object mLock = new java.lang.Object();

    ImeTrackerService(@android.annotation.NonNull android.os.Looper looper) {
        this.mHandler = new android.os.Handler(looper, null, true);
    }

    @android.annotation.NonNull
    public android.view.inputmethod.ImeTracker.Token onStart(@android.annotation.NonNull java.lang.String str, int i, int i2, int i3, int i4, boolean z) {
        android.os.Binder binder = new android.os.Binder();
        final android.view.inputmethod.ImeTracker.Token token = new android.view.inputmethod.ImeTracker.Token(binder, str);
        com.android.server.inputmethod.ImeTrackerService.History.Entry entry = new com.android.server.inputmethod.ImeTrackerService.History.Entry(str, i, i2, 1, i3, i4, z);
        synchronized (this.mLock) {
            this.mHistory.addEntry(binder, entry);
            this.mHandler.postDelayed(new java.lang.Runnable() { // from class: com.android.server.inputmethod.ImeTrackerService$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.inputmethod.ImeTrackerService.this.lambda$onStart$0(token);
                }
            }, 10000L);
        }
        return token;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onStart$0(android.view.inputmethod.ImeTracker.Token token) {
        synchronized (this.mLock) {
            this.mHistory.setFinished(token, 5, 0);
        }
    }

    public void onProgress(@android.annotation.NonNull android.os.IBinder iBinder, int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.inputmethod.ImeTrackerService.History.Entry entry = this.mHistory.getEntry(iBinder);
                if (entry == null) {
                    return;
                }
                entry.mPhase = i;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void onFailed(@android.annotation.NonNull android.view.inputmethod.ImeTracker.Token token, int i) {
        synchronized (this.mLock) {
            this.mHistory.setFinished(token, 3, i);
        }
    }

    public void onCancelled(@android.annotation.NonNull android.view.inputmethod.ImeTracker.Token token, int i) {
        synchronized (this.mLock) {
            this.mHistory.setFinished(token, 2, i);
        }
    }

    public void onShown(@android.annotation.NonNull android.view.inputmethod.ImeTracker.Token token) {
        synchronized (this.mLock) {
            this.mHistory.setFinished(token, 4, 0);
        }
    }

    public void onHidden(@android.annotation.NonNull android.view.inputmethod.ImeTracker.Token token) {
        synchronized (this.mLock) {
            this.mHistory.setFinished(token, 4, 0);
        }
    }

    public void onImmsUpdate(@android.annotation.NonNull android.view.inputmethod.ImeTracker.Token token, @android.annotation.NonNull java.lang.String str) {
        synchronized (this.mLock) {
            try {
                com.android.server.inputmethod.ImeTrackerService.History.Entry entry = this.mHistory.getEntry(token.getBinder());
                if (entry == null) {
                    return;
                }
                entry.mRequestWindowName = str;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void dump(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull java.lang.String str) {
        synchronized (this.mLock) {
            this.mHistory.dump(printWriter, str);
        }
    }

    @android.annotation.EnforcePermission("android.permission.TEST_INPUT_METHOD")
    public boolean hasPendingImeVisibilityRequests() {
        boolean z;
        super.hasPendingImeVisibilityRequests_enforcePermission();
        synchronized (this.mLock) {
            z = !this.mHistory.mLiveEntries.isEmpty();
        }
        return z;
    }

    private static final class History {
        private static final int CAPACITY = 100;
        private static final java.util.concurrent.atomic.AtomicInteger sSequenceNumber = new java.util.concurrent.atomic.AtomicInteger(0);

        @com.android.internal.annotations.GuardedBy({"ImeTrackerService.this"})
        private final java.util.ArrayDeque<com.android.server.inputmethod.ImeTrackerService.History.Entry> mEntries;

        @com.android.internal.annotations.GuardedBy({"ImeTrackerService.this"})
        private final java.util.WeakHashMap<android.os.IBinder, com.android.server.inputmethod.ImeTrackerService.History.Entry> mLiveEntries;

        private History() {
            this.mEntries = new java.util.ArrayDeque<>(100);
            this.mLiveEntries = new java.util.WeakHashMap<>();
        }

        /* JADX INFO: Access modifiers changed from: private */
        @com.android.internal.annotations.GuardedBy({"ImeTrackerService.this"})
        public void addEntry(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull com.android.server.inputmethod.ImeTrackerService.History.Entry entry) {
            this.mLiveEntries.put(iBinder, entry);
        }

        /* JADX INFO: Access modifiers changed from: private */
        @com.android.internal.annotations.GuardedBy({"ImeTrackerService.this"})
        @android.annotation.Nullable
        public com.android.server.inputmethod.ImeTrackerService.History.Entry getEntry(@android.annotation.NonNull android.os.IBinder iBinder) {
            return this.mLiveEntries.get(iBinder);
        }

        /* JADX INFO: Access modifiers changed from: private */
        @com.android.internal.annotations.GuardedBy({"ImeTrackerService.this"})
        public void setFinished(@android.annotation.NonNull android.view.inputmethod.ImeTracker.Token token, int i, int i2) {
            com.android.server.inputmethod.ImeTrackerService.History.Entry remove = this.mLiveEntries.remove(token.getBinder());
            if (remove == null) {
                if (i != 5) {
                    android.util.Log.i(com.android.server.inputmethod.ImeTrackerService.TAG, token.getTag() + ": setFinished on previously finished token at " + android.view.inputmethod.ImeTracker.Debug.phaseToString(i2) + " with " + android.view.inputmethod.ImeTracker.Debug.statusToString(i));
                    return;
                }
                return;
            }
            remove.mDuration = java.lang.System.currentTimeMillis() - remove.mStartTime;
            remove.mStatus = i;
            if (i2 != 0) {
                remove.mPhase = i2;
            }
            if (i == 5) {
                android.util.Log.i(com.android.server.inputmethod.ImeTrackerService.TAG, token.getTag() + ": setFinished at " + android.view.inputmethod.ImeTracker.Debug.phaseToString(remove.mPhase) + " with " + android.view.inputmethod.ImeTracker.Debug.statusToString(i));
            }
            while (this.mEntries.size() >= 100) {
                this.mEntries.remove();
            }
            this.mEntries.offer(remove);
            com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.IME_REQUEST_FINISHED, remove.mUid, remove.mDuration, remove.mType, remove.mStatus, remove.mReason, remove.mOrigin, remove.mPhase, remove.mFromUser);
        }

        /* JADX INFO: Access modifiers changed from: private */
        @com.android.internal.annotations.GuardedBy({"ImeTrackerService.this"})
        public void dump(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull java.lang.String str) {
            java.time.format.DateTimeFormatter withZone = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS", java.util.Locale.US).withZone(java.time.ZoneId.systemDefault());
            printWriter.print(str);
            printWriter.println("mLiveEntries: " + this.mLiveEntries.size() + " elements");
            java.util.Iterator<com.android.server.inputmethod.ImeTrackerService.History.Entry> it = this.mLiveEntries.values().iterator();
            while (it.hasNext()) {
                dumpEntry(it.next(), printWriter, str + "  ", withZone);
            }
            printWriter.print(str);
            printWriter.println("mEntries: " + this.mEntries.size() + " elements");
            java.util.Iterator<com.android.server.inputmethod.ImeTrackerService.History.Entry> it2 = this.mEntries.iterator();
            while (it2.hasNext()) {
                dumpEntry(it2.next(), printWriter, str + "  ", withZone);
            }
        }

        @com.android.internal.annotations.GuardedBy({"ImeTrackerService.this"})
        private void dumpEntry(@android.annotation.NonNull com.android.server.inputmethod.ImeTrackerService.History.Entry entry, @android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.time.format.DateTimeFormatter dateTimeFormatter) {
            printWriter.print(str);
            printWriter.print("#" + entry.mSequenceNumber);
            printWriter.print(" " + android.view.inputmethod.ImeTracker.Debug.typeToString(entry.mType));
            printWriter.print(" - " + android.view.inputmethod.ImeTracker.Debug.statusToString(entry.mStatus));
            printWriter.print(" - " + entry.mTag);
            printWriter.println(" (" + entry.mDuration + "ms):");
            printWriter.print(str);
            printWriter.print("  startTime=" + dateTimeFormatter.format(java.time.Instant.ofEpochMilli(entry.mStartTime)));
            printWriter.println(" " + android.view.inputmethod.ImeTracker.Debug.originToString(entry.mOrigin));
            printWriter.print(str);
            printWriter.print("  reason=" + com.android.internal.inputmethod.InputMethodDebug.softInputDisplayReasonToString(entry.mReason));
            printWriter.println(" " + android.view.inputmethod.ImeTracker.Debug.phaseToString(entry.mPhase));
            printWriter.print(str);
            printWriter.println("  requestWindowName=" + entry.mRequestWindowName);
        }

        private static final class Entry {
            private long mDuration;
            private final boolean mFromUser;
            private final int mOrigin;
            private int mPhase;
            private final int mReason;

            @android.annotation.NonNull
            private java.lang.String mRequestWindowName;
            private final int mSequenceNumber;
            private final long mStartTime;
            private int mStatus;

            @android.annotation.NonNull
            private final java.lang.String mTag;
            private final int mType;
            private final int mUid;

            private Entry(@android.annotation.NonNull java.lang.String str, int i, int i2, int i3, int i4, int i5, boolean z) {
                this.mSequenceNumber = com.android.server.inputmethod.ImeTrackerService.History.sSequenceNumber.getAndIncrement();
                this.mStartTime = java.lang.System.currentTimeMillis();
                this.mDuration = 0L;
                this.mPhase = 0;
                this.mRequestWindowName = "not set";
                this.mTag = str;
                this.mUid = i;
                this.mType = i2;
                this.mStatus = i3;
                this.mOrigin = i4;
                this.mReason = i5;
                this.mFromUser = z;
            }
        }
    }
}
