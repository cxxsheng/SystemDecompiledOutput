package android.os;

/* loaded from: classes3.dex */
public abstract class UEventObserver {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "UEventObserver";
    private static android.os.UEventObserver.UEventThread sThread;

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAddMatch(java.lang.String str);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeRemoveMatch(java.lang.String str);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetup();

    /* JADX INFO: Access modifiers changed from: private */
    public static native java.lang.String nativeWaitForNextEvent();

    public abstract void onUEvent(android.os.UEventObserver.UEvent uEvent);

    protected void finalize() throws java.lang.Throwable {
        try {
            stopObserving();
        } finally {
            super.finalize();
        }
    }

    private static android.os.UEventObserver.UEventThread getThread() {
        android.os.UEventObserver.UEventThread uEventThread;
        synchronized (android.os.UEventObserver.class) {
            if (sThread == null) {
                sThread = new android.os.UEventObserver.UEventThread();
                sThread.start();
            }
            uEventThread = sThread;
        }
        return uEventThread;
    }

    private static android.os.UEventObserver.UEventThread peekThread() {
        android.os.UEventObserver.UEventThread uEventThread;
        synchronized (android.os.UEventObserver.class) {
            uEventThread = sThread;
        }
        return uEventThread;
    }

    public final void startObserving(java.lang.String str) {
        if (str == null || str.isEmpty()) {
            throw new java.lang.IllegalArgumentException("match substring must be non-empty");
        }
        getThread().addObserver(str, this);
    }

    public final void stopObserving() {
        android.os.UEventObserver.UEventThread peekThread = peekThread();
        if (peekThread != null) {
            peekThread.removeObserver(this);
        }
    }

    public static final class UEvent {
        private final java.util.HashMap<java.lang.String, java.lang.String> mMap = new java.util.HashMap<>();

        public UEvent(java.lang.String str) {
            int length = str.length();
            int i = 0;
            while (i < length) {
                int indexOf = str.indexOf(61, i);
                int indexOf2 = str.indexOf(0, i);
                if (indexOf2 >= 0) {
                    if (indexOf > i && indexOf < indexOf2) {
                        this.mMap.put(str.substring(i, indexOf), str.substring(indexOf + 1, indexOf2));
                    }
                    i = indexOf2 + 1;
                } else {
                    return;
                }
            }
        }

        public java.lang.String get(java.lang.String str) {
            return this.mMap.get(str);
        }

        public java.lang.String get(java.lang.String str, java.lang.String str2) {
            java.lang.String str3 = this.mMap.get(str);
            return str3 == null ? str2 : str3;
        }

        public java.lang.String toString() {
            return this.mMap.toString();
        }
    }

    private static final class UEventThread extends java.lang.Thread {
        private final java.util.ArrayList<java.lang.Object> mKeysAndObservers;
        private final java.util.ArrayList<android.os.UEventObserver> mTempObserversToSignal;

        public UEventThread() {
            super(android.os.UEventObserver.TAG);
            this.mKeysAndObservers = new java.util.ArrayList<>();
            this.mTempObserversToSignal = new java.util.ArrayList<>();
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            android.os.UEventObserver.nativeSetup();
            while (true) {
                java.lang.String nativeWaitForNextEvent = android.os.UEventObserver.nativeWaitForNextEvent();
                if (nativeWaitForNextEvent != null) {
                    sendEvent(nativeWaitForNextEvent);
                }
            }
        }

        private void sendEvent(java.lang.String str) {
            int i;
            synchronized (this.mKeysAndObservers) {
                int size = this.mKeysAndObservers.size();
                for (int i2 = 0; i2 < size; i2 += 2) {
                    if (str.contains((java.lang.String) this.mKeysAndObservers.get(i2))) {
                        this.mTempObserversToSignal.add((android.os.UEventObserver) this.mKeysAndObservers.get(i2 + 1));
                    }
                }
            }
            if (!this.mTempObserversToSignal.isEmpty()) {
                android.os.UEventObserver.UEvent uEvent = new android.os.UEventObserver.UEvent(str);
                int size2 = this.mTempObserversToSignal.size();
                for (i = 0; i < size2; i++) {
                    this.mTempObserversToSignal.get(i).onUEvent(uEvent);
                }
                this.mTempObserversToSignal.clear();
            }
        }

        public void addObserver(java.lang.String str, android.os.UEventObserver uEventObserver) {
            synchronized (this.mKeysAndObservers) {
                this.mKeysAndObservers.add(str);
                this.mKeysAndObservers.add(uEventObserver);
                android.os.UEventObserver.nativeAddMatch(str);
            }
        }

        public void removeObserver(android.os.UEventObserver uEventObserver) {
            synchronized (this.mKeysAndObservers) {
                int i = 0;
                while (i < this.mKeysAndObservers.size()) {
                    int i2 = i + 1;
                    if (this.mKeysAndObservers.get(i2) == uEventObserver) {
                        this.mKeysAndObservers.remove(i2);
                        android.os.UEventObserver.nativeRemoveMatch((java.lang.String) this.mKeysAndObservers.remove(i));
                    } else {
                        i += 2;
                    }
                }
            }
        }
    }
}
