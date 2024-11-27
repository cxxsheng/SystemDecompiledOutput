package com.android.server.incident;

/* loaded from: classes2.dex */
class RequestQueue {
    private final android.os.Handler mHandler;
    private boolean mStarted;
    private java.util.ArrayList<com.android.server.incident.RequestQueue.Rec> mPending = new java.util.ArrayList<>();
    private final java.lang.Runnable mWorker = new java.lang.Runnable() { // from class: com.android.server.incident.RequestQueue.1
        @Override // java.lang.Runnable
        public void run() {
            java.util.ArrayList arrayList;
            synchronized (com.android.server.incident.RequestQueue.this.mPending) {
                try {
                    if (com.android.server.incident.RequestQueue.this.mPending.size() <= 0) {
                        arrayList = null;
                    } else {
                        arrayList = new java.util.ArrayList(com.android.server.incident.RequestQueue.this.mPending);
                        com.android.server.incident.RequestQueue.this.mPending.clear();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            if (arrayList != null) {
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    ((com.android.server.incident.RequestQueue.Rec) arrayList.get(i)).runnable.run();
                }
            }
        }
    };

    private class Rec {
        public final android.os.IBinder key;
        public final java.lang.Runnable runnable;
        public final boolean value;

        Rec(android.os.IBinder iBinder, boolean z, java.lang.Runnable runnable) {
            this.key = iBinder;
            this.value = z;
            this.runnable = runnable;
        }
    }

    RequestQueue(android.os.Handler handler) {
        this.mHandler = handler;
    }

    public void start() {
        synchronized (this.mPending) {
            try {
                if (!this.mStarted) {
                    if (this.mPending.size() > 0) {
                        this.mHandler.post(this.mWorker);
                    }
                    this.mStarted = true;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void enqueue(android.os.IBinder iBinder, boolean z, java.lang.Runnable runnable) {
        boolean z2;
        synchronized (this.mPending) {
            if (!z) {
                try {
                    z2 = true;
                    for (int size = this.mPending.size() - 1; size >= 0; size--) {
                        com.android.server.incident.RequestQueue.Rec rec = this.mPending.get(size);
                        if (rec.key == iBinder && rec.value) {
                            this.mPending.remove(size);
                            break;
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            z2 = false;
            if (!z2) {
                this.mPending.add(new com.android.server.incident.RequestQueue.Rec(iBinder, z, runnable));
            }
            if (this.mStarted) {
                this.mHandler.post(this.mWorker);
            }
        }
    }
}
