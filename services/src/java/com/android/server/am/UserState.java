package com.android.server.am;

/* loaded from: classes.dex */
public final class UserState {
    public static final int STATE_BOOTING = 0;
    public static final int STATE_NONE = -1;
    public static final int STATE_RUNNING_LOCKED = 1;
    public static final int STATE_RUNNING_UNLOCKED = 3;
    public static final int STATE_RUNNING_UNLOCKING = 2;
    public static final int STATE_SHUTDOWN = 5;
    public static final int STATE_STOPPING = 4;
    private static final java.lang.String TAG = "ActivityManager";
    public final android.os.UserHandle mHandle;
    public final com.android.internal.util.ProgressReporter mUnlockProgress;
    public boolean switching;
    public final java.util.ArrayList<android.app.IStopUserCallback> mStopCallbacks = new java.util.ArrayList<>();
    public final java.util.ArrayList<com.android.server.am.UserState.KeyEvictedCallback> mKeyEvictedCallbacks = new java.util.ArrayList<>();
    public int state = 0;
    public int lastState = 0;
    final android.util.ArrayMap<java.lang.String, java.lang.Long> mProviderLastReportedFg = new android.util.ArrayMap<>();

    public interface KeyEvictedCallback {
        void keyEvicted(int i);
    }

    public UserState(android.os.UserHandle userHandle) {
        this.mHandle = userHandle;
        this.mUnlockProgress = new com.android.internal.util.ProgressReporter(userHandle.getIdentifier());
    }

    public boolean setState(int i, int i2) {
        if (this.state == i) {
            setState(i2);
            return true;
        }
        android.util.Slog.w(TAG, "Expected user " + this.mHandle.getIdentifier() + " in state " + stateToString(i) + " but was in state " + stateToString(this.state));
        return false;
    }

    public void setState(int i) {
        if (i == this.state) {
            return;
        }
        int identifier = this.mHandle.getIdentifier();
        if (this.state != 0) {
            android.os.Trace.asyncTraceEnd(64L, stateToString(this.state) + " " + identifier, identifier);
        }
        if (i != 5) {
            android.os.Trace.asyncTraceBegin(64L, stateToString(i) + " " + identifier, identifier);
        }
        android.util.Slog.i(TAG, "User " + identifier + " state changed from " + stateToString(this.state) + " to " + stateToString(i));
        com.android.server.am.EventLogTags.writeAmUserStateChanged(identifier, i);
        this.lastState = this.state;
        this.state = i;
    }

    public static java.lang.String stateToString(int i) {
        switch (i) {
            case 0:
                return "BOOTING";
            case 1:
                return "RUNNING_LOCKED";
            case 2:
                return "RUNNING_UNLOCKING";
            case 3:
                return "RUNNING_UNLOCKED";
            case 4:
                return "STOPPING";
            case 5:
                return "SHUTDOWN";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    public static int stateToProtoEnum(int i) {
        switch (i) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 5;
            default:
                return i;
        }
    }

    void dump(java.lang.String str, java.io.PrintWriter printWriter) {
        printWriter.print(str);
        printWriter.print("state=");
        printWriter.print(stateToString(this.state));
        if (this.switching) {
            printWriter.print(" SWITCHING");
        }
        printWriter.println();
    }

    void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1159641169921L, stateToProtoEnum(this.state));
        protoOutputStream.write(1133871366146L, this.switching);
        protoOutputStream.end(start);
    }

    public java.lang.String toString() {
        return "[UserState: id=" + this.mHandle.getIdentifier() + ", state=" + stateToString(this.state) + ", lastState=" + stateToString(this.lastState) + ", switching=" + this.switching + "]";
    }
}
