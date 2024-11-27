package android.os;

/* loaded from: classes3.dex */
public abstract class TokenWatcher {
    private android.os.Handler mHandler;
    private java.lang.String mTag;
    private java.lang.Runnable mNotificationTask = new java.lang.Runnable() { // from class: android.os.TokenWatcher.1
        @Override // java.lang.Runnable
        public void run() {
            int i;
            synchronized (android.os.TokenWatcher.this.mTokens) {
                i = android.os.TokenWatcher.this.mNotificationQueue;
                android.os.TokenWatcher.this.mNotificationQueue = -1;
            }
            if (i == 1) {
                android.os.TokenWatcher.this.acquired();
            } else if (i == 0) {
                android.os.TokenWatcher.this.released();
            }
        }
    };
    private java.util.WeakHashMap<android.os.IBinder, android.os.TokenWatcher.Death> mTokens = new java.util.WeakHashMap<>();
    private int mNotificationQueue = -1;
    private volatile boolean mAcquired = false;

    public abstract void acquired();

    public abstract void released();

    public TokenWatcher(android.os.Handler handler, java.lang.String str) {
        this.mHandler = handler;
        this.mTag = str == null ? "TokenWatcher" : str;
    }

    public void acquire(android.os.IBinder iBinder, java.lang.String str) {
        synchronized (this.mTokens) {
            if (this.mTokens.containsKey(iBinder)) {
                return;
            }
            int size = this.mTokens.size();
            android.os.TokenWatcher.Death death = new android.os.TokenWatcher.Death(iBinder, str);
            try {
                iBinder.linkToDeath(death, 0);
                this.mTokens.put(iBinder, death);
                if (size == 0 && !this.mAcquired) {
                    sendNotificationLocked(true);
                    this.mAcquired = true;
                }
            } catch (android.os.RemoteException e) {
            }
        }
    }

    public void cleanup(android.os.IBinder iBinder, boolean z) {
        synchronized (this.mTokens) {
            android.os.TokenWatcher.Death remove = this.mTokens.remove(iBinder);
            if (z && remove != null) {
                remove.token.unlinkToDeath(remove, 0);
                remove.token = null;
            }
            if (this.mTokens.size() == 0 && this.mAcquired) {
                sendNotificationLocked(false);
                this.mAcquired = false;
            }
        }
    }

    public void release(android.os.IBinder iBinder) {
        cleanup(iBinder, true);
    }

    public boolean isAcquired() {
        boolean z;
        synchronized (this.mTokens) {
            z = this.mAcquired;
        }
        return z;
    }

    public void dump() {
        java.util.Iterator<java.lang.String> it = dumpInternal().iterator();
        while (it.hasNext()) {
            android.util.Log.i(this.mTag, it.next());
        }
    }

    public void dump(java.io.PrintWriter printWriter) {
        java.util.Iterator<java.lang.String> it = dumpInternal().iterator();
        while (it.hasNext()) {
            printWriter.println(it.next());
        }
    }

    private java.util.ArrayList<java.lang.String> dumpInternal() {
        java.util.ArrayList<java.lang.String> arrayList = new java.util.ArrayList<>();
        synchronized (this.mTokens) {
            java.util.Set<android.os.IBinder> keySet = this.mTokens.keySet();
            arrayList.add("Token count: " + this.mTokens.size());
            int i = 0;
            for (android.os.IBinder iBinder : keySet) {
                arrayList.add(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START + i + "] " + this.mTokens.get(iBinder).tag + " - " + iBinder);
                i++;
            }
        }
        return arrayList;
    }

    private void sendNotificationLocked(boolean z) {
        if (this.mNotificationQueue == -1) {
            this.mNotificationQueue = z ? 1 : 0;
            this.mHandler.post(this.mNotificationTask);
        } else if (this.mNotificationQueue != z) {
            this.mNotificationQueue = -1;
            this.mHandler.removeCallbacks(this.mNotificationTask);
        }
    }

    private class Death implements android.os.IBinder.DeathRecipient {
        java.lang.String tag;
        android.os.IBinder token;

        Death(android.os.IBinder iBinder, java.lang.String str) {
            this.token = iBinder;
            this.tag = str;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            android.os.TokenWatcher.this.cleanup(this.token, false);
        }

        protected void finalize() throws java.lang.Throwable {
            try {
                if (this.token != null) {
                    android.util.Log.w(android.os.TokenWatcher.this.mTag, "cleaning up leaked reference: " + this.tag);
                    android.os.TokenWatcher.this.release(this.token);
                }
            } finally {
                super.finalize();
            }
        }
    }
}
