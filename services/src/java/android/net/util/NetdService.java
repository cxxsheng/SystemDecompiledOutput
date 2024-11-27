package android.net.util;

/* loaded from: classes.dex */
public class NetdService {
    private static final long BASE_TIMEOUT_MS = 100;
    private static final long MAX_TIMEOUT_MS = 1000;
    private static final java.lang.String TAG = android.net.util.NetdService.class.getSimpleName();

    public interface NetdCommand {
        void run(android.net.INetd iNetd) throws android.os.RemoteException;
    }

    public static android.net.INetd getInstance() {
        android.net.INetd asInterface = android.net.INetd.Stub.asInterface(android.os.ServiceManager.getService("netd"));
        if (asInterface == null) {
            android.util.Log.w(TAG, "WARNING: returning null INetd instance.");
        }
        return asInterface;
    }

    public static android.net.INetd get(long j) {
        long j2;
        if (j == 0) {
            return getInstance();
        }
        if (j > 0) {
            j2 = android.os.SystemClock.elapsedRealtime() + j;
        } else {
            j2 = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
        }
        long j3 = 0;
        while (true) {
            android.net.INetd netdService = getInstance();
            if (netdService != null) {
                return netdService;
            }
            long elapsedRealtime = j2 - android.os.SystemClock.elapsedRealtime();
            if (elapsedRealtime > 0) {
                j3 = java.lang.Math.min(java.lang.Math.min(j3 + BASE_TIMEOUT_MS, 1000L), elapsedRealtime);
                try {
                    java.lang.Thread.sleep(j3);
                } catch (java.lang.InterruptedException e) {
                }
            } else {
                return null;
            }
        }
    }

    public static android.net.INetd get() {
        return get(-1L);
    }

    public static void run(android.net.util.NetdService.NetdCommand netdCommand) {
        while (true) {
            try {
                netdCommand.run(get());
                return;
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "error communicating with netd: " + e);
            }
        }
    }
}
