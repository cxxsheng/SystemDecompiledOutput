package com.android.server.backup.transport;

/* loaded from: classes.dex */
public class TransportUtils {
    private static final java.lang.String TAG = "TransportUtils";

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface Priority {
        public static final int DEBUG = 3;
        public static final int ERROR = 6;
        public static final int INFO = 4;
        public static final int VERBOSE = 2;
        public static final int WARN = 5;
        public static final int WTF = -1;
    }

    private TransportUtils() {
    }

    public static com.android.internal.backup.IBackupTransport checkTransportNotNull(@android.annotation.Nullable com.android.internal.backup.IBackupTransport iBackupTransport) throws com.android.server.backup.transport.TransportNotAvailableException {
        if (iBackupTransport == null) {
            log(6, TAG, "Transport not available");
            throw new com.android.server.backup.transport.TransportNotAvailableException();
        }
        return iBackupTransport;
    }

    static void log(int i, java.lang.String str, java.lang.String str2) {
        if (i == -1) {
            android.util.Slog.wtf(str, str2);
        } else if (android.util.Log.isLoggable(str, i)) {
            android.util.Slog.println(i, str, str2);
        }
    }

    static java.lang.String formatMessage(@android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String str2, java.lang.String str3) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        if (str != null) {
            sb.append(str);
            sb.append(" ");
        }
        if (str2 != null) {
            sb.append("[");
            sb.append(str2);
            sb.append("] ");
        }
        sb.append(str3);
        return sb.toString();
    }
}
