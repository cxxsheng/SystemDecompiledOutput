package com.android.internal.os;

/* loaded from: classes4.dex */
public abstract class Clock {
    public static final com.android.internal.os.Clock SYSTEM_CLOCK = new com.android.internal.os.Clock() { // from class: com.android.internal.os.Clock.1
        @Override // com.android.internal.os.Clock
        public long elapsedRealtime() {
            return android.os.SystemClock.elapsedRealtime();
        }

        @Override // com.android.internal.os.Clock
        public long uptimeMillis() {
            return android.os.SystemClock.uptimeMillis();
        }

        @Override // com.android.internal.os.Clock
        public long currentTimeMillis() {
            return java.lang.System.currentTimeMillis();
        }
    };

    public long elapsedRealtime() {
        throw new java.lang.UnsupportedOperationException();
    }

    public long uptimeMillis() {
        throw new java.lang.UnsupportedOperationException();
    }

    public long currentTimeMillis() {
        throw new java.lang.UnsupportedOperationException();
    }
}
