package com.android.server.os;

/* loaded from: classes2.dex */
public class SchedulingPolicyService extends android.os.ISchedulingPolicyService.Stub {
    private static final java.lang.String[] MEDIA_PROCESS_NAMES = {"media.swcodec"};
    private static final int PRIORITY_MAX = 3;
    private static final int PRIORITY_MIN = 1;
    private static final java.lang.String TAG = "SchedulingPolicyService";
    private android.os.IBinder mClient;
    private final android.os.IBinder.DeathRecipient mDeathRecipient = new android.os.IBinder.DeathRecipient() { // from class: com.android.server.os.SchedulingPolicyService.1
        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            com.android.server.os.SchedulingPolicyService.this.requestCpusetBoost(false, null);
        }
    };
    private int mBoostedPid = -1;

    public SchedulingPolicyService() {
        com.android.server.SystemServerInitThreadPool.submit(new java.lang.Runnable() { // from class: com.android.server.os.SchedulingPolicyService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.os.SchedulingPolicyService.this.lambda$new$0();
            }
        }, "SchedulingPolicyService.<init>");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        int[] pidsForCommands;
        synchronized (this.mDeathRecipient) {
            try {
                if (this.mBoostedPid == -1 && (pidsForCommands = android.os.Process.getPidsForCommands(MEDIA_PROCESS_NAMES)) != null && pidsForCommands.length == 1) {
                    this.mBoostedPid = pidsForCommands[0];
                    disableCpusetBoost(pidsForCommands[0]);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public int requestPriority(int i, int i2, int i3, boolean z) {
        if (!isPermitted() || i3 < 1 || i3 > 3 || android.os.Process.getThreadGroupLeader(i2) != i) {
            return -1;
        }
        if (android.os.Binder.getCallingUid() == 1041 && !z && android.os.Process.getUidForPid(i2) != 1041) {
            return -1;
        }
        if (android.os.Binder.getCallingUid() != 1002) {
            try {
                android.os.Process.setThreadGroup(i2, !z ? 4 : 6);
            } catch (java.lang.RuntimeException e) {
                android.util.Log.e(TAG, "Failed setThreadGroup: " + e);
                return -1;
            }
        }
        try {
            android.os.Process.setThreadScheduler(i2, com.android.server.policy.WindowManagerPolicy.COLOR_FADE_LAYER, i3);
            return 0;
        } catch (java.lang.RuntimeException e2) {
            android.util.Log.e(TAG, "Failed setThreadScheduler: " + e2);
            return -1;
        }
    }

    public int requestCpusetBoost(boolean z, android.os.IBinder iBinder) {
        if (android.os.Binder.getCallingPid() != android.os.Process.myPid() && android.os.Binder.getCallingUid() != 1013) {
            return -1;
        }
        int[] pidsForCommands = android.os.Process.getPidsForCommands(MEDIA_PROCESS_NAMES);
        if (pidsForCommands == null || pidsForCommands.length != 1) {
            android.util.Log.e(TAG, "requestCpusetBoost: can't find media.codec process");
            return -1;
        }
        synchronized (this.mDeathRecipient) {
            try {
                if (z) {
                    return enableCpusetBoost(pidsForCommands[0], iBinder);
                }
                return disableCpusetBoost(pidsForCommands[0]);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private int enableCpusetBoost(int i, android.os.IBinder iBinder) {
        if (this.mBoostedPid == i) {
            return 0;
        }
        this.mBoostedPid = -1;
        if (this.mClient != null) {
            try {
                this.mClient.unlinkToDeath(this.mDeathRecipient, 0);
            } catch (java.lang.Exception e) {
            } catch (java.lang.Throwable th) {
                this.mClient = null;
                throw th;
            }
            this.mClient = null;
        }
        try {
            iBinder.linkToDeath(this.mDeathRecipient, 0);
            android.util.Log.i(TAG, "Moving " + i + " to group 5");
            android.os.Process.setProcessGroup(i, 5);
            this.mBoostedPid = i;
            this.mClient = iBinder;
            return 0;
        } catch (java.lang.Exception e2) {
            android.util.Log.e(TAG, "Failed enableCpusetBoost: " + e2);
            try {
                iBinder.unlinkToDeath(this.mDeathRecipient, 0);
            } catch (java.lang.Exception e3) {
            }
            return -1;
        }
    }

    private int disableCpusetBoost(int i) {
        int i2 = this.mBoostedPid;
        this.mBoostedPid = -1;
        if (this.mClient != null) {
            try {
                this.mClient.unlinkToDeath(this.mDeathRecipient, 0);
            } catch (java.lang.Exception e) {
            } catch (java.lang.Throwable th) {
                this.mClient = null;
                throw th;
            }
            this.mClient = null;
        }
        if (i2 == i) {
            try {
                android.util.Log.i(TAG, "Moving " + i + " back to group default");
                android.os.Process.setProcessGroup(i, -1);
            } catch (java.lang.Exception e2) {
                android.util.Log.w(TAG, "Couldn't move pid " + i + " back to group default");
            }
        }
        return 0;
    }

    private boolean isPermitted() {
        if (android.os.Binder.getCallingPid() == android.os.Process.myPid()) {
            return true;
        }
        switch (android.os.Binder.getCallingUid()) {
            case 1001:
            case 1002:
            case 1041:
            case 1047:
                break;
        }
        return true;
    }
}
