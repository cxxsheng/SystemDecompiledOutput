package android.os;

/* loaded from: classes3.dex */
public class SystemService {
    private static java.util.HashMap<java.lang.String, android.os.SystemService.State> sStates = com.google.android.collect.Maps.newHashMap();
    private static java.lang.Object sPropertyLock = new java.lang.Object();

    static {
        android.os.SystemProperties.addChangeCallback(new java.lang.Runnable() { // from class: android.os.SystemService.1
            @Override // java.lang.Runnable
            public void run() {
                synchronized (android.os.SystemService.sPropertyLock) {
                    android.os.SystemService.sPropertyLock.notifyAll();
                }
            }
        });
    }

    public enum State {
        RUNNING("running"),
        STOPPING("stopping"),
        STOPPED("stopped"),
        RESTARTING("restarting");

        State(java.lang.String str) {
            android.os.SystemService.sStates.put(str, this);
        }
    }

    public static void start(java.lang.String str) {
        android.os.SystemProperties.set("ctl.start", str);
    }

    public static void stop(java.lang.String str) {
        android.os.SystemProperties.set("ctl.stop", str);
    }

    public static void restart(java.lang.String str) {
        android.os.SystemProperties.set("ctl.restart", str);
    }

    public static android.os.SystemService.State getState(java.lang.String str) {
        android.os.SystemService.State state = sStates.get(android.os.SystemProperties.get("init.svc." + str));
        if (state != null) {
            return state;
        }
        return android.os.SystemService.State.STOPPED;
    }

    public static boolean isStopped(java.lang.String str) {
        return android.os.SystemService.State.STOPPED.equals(getState(str));
    }

    public static boolean isRunning(java.lang.String str) {
        return android.os.SystemService.State.RUNNING.equals(getState(str));
    }

    public static void waitForState(java.lang.String str, android.os.SystemService.State state, long j) throws java.util.concurrent.TimeoutException {
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime() + j;
        while (true) {
            synchronized (sPropertyLock) {
                android.os.SystemService.State state2 = getState(str);
                if (state.equals(state2)) {
                    return;
                }
                if (android.os.SystemClock.elapsedRealtime() >= elapsedRealtime) {
                    throw new java.util.concurrent.TimeoutException("Service " + str + " currently " + state2 + "; waited " + j + "ms for " + state);
                }
                try {
                    sPropertyLock.wait(j);
                } catch (java.lang.InterruptedException e) {
                }
            }
        }
    }

    public static void waitForAnyStopped(java.lang.String... strArr) {
        while (true) {
            synchronized (sPropertyLock) {
                for (java.lang.String str : strArr) {
                    if (android.os.SystemService.State.STOPPED.equals(getState(str))) {
                        return;
                    }
                }
                try {
                    sPropertyLock.wait();
                } catch (java.lang.InterruptedException e) {
                }
            }
        }
    }
}
