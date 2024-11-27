package com.android.server.am;

/* loaded from: classes.dex */
public class AppTimeTracker {
    private final android.util.ArrayMap<java.lang.String, android.util.MutableLong> mPackageTimes = new android.util.ArrayMap<>();
    private final android.app.PendingIntent mReceiver;
    private java.lang.String mStartedPackage;
    private android.util.MutableLong mStartedPackageTime;
    private long mStartedTime;
    private long mTotalTime;

    public AppTimeTracker(android.app.PendingIntent pendingIntent) {
        this.mReceiver = pendingIntent;
    }

    public void start(java.lang.String str) {
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        if (this.mStartedTime == 0) {
            this.mStartedTime = elapsedRealtime;
        }
        if (!str.equals(this.mStartedPackage)) {
            if (this.mStartedPackageTime != null) {
                long j = elapsedRealtime - this.mStartedTime;
                this.mStartedPackageTime.value += j;
                this.mTotalTime += j;
            }
            this.mStartedPackage = str;
            this.mStartedPackageTime = this.mPackageTimes.get(str);
            if (this.mStartedPackageTime == null) {
                this.mStartedPackageTime = new android.util.MutableLong(0L);
                this.mPackageTimes.put(str, this.mStartedPackageTime);
            }
        }
    }

    public void stop() {
        if (this.mStartedTime != 0) {
            long elapsedRealtime = android.os.SystemClock.elapsedRealtime() - this.mStartedTime;
            this.mTotalTime += elapsedRealtime;
            if (this.mStartedPackageTime != null) {
                this.mStartedPackageTime.value += elapsedRealtime;
            }
            this.mStartedPackage = null;
            this.mStartedPackageTime = null;
        }
    }

    public void deliverResult(android.content.Context context) {
        stop();
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putLong("android.activity.usage_time", this.mTotalTime);
        android.os.Bundle bundle2 = new android.os.Bundle();
        for (int size = this.mPackageTimes.size() - 1; size >= 0; size--) {
            bundle2.putLong(this.mPackageTimes.keyAt(size), this.mPackageTimes.valueAt(size).value);
        }
        bundle.putBundle("android.usage_time_packages", bundle2);
        android.content.Intent intent = new android.content.Intent();
        intent.putExtras(bundle);
        try {
            this.mReceiver.send(context, 0, intent);
        } catch (android.app.PendingIntent.CanceledException e) {
        }
    }

    public void dumpWithHeader(java.io.PrintWriter printWriter, java.lang.String str, boolean z) {
        printWriter.print(str);
        printWriter.print("AppTimeTracker #");
        printWriter.print(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
        printWriter.println(":");
        dump(printWriter, str + "  ", z);
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str, boolean z) {
        printWriter.print(str);
        printWriter.print("mReceiver=");
        printWriter.println(this.mReceiver);
        printWriter.print(str);
        printWriter.print("mTotalTime=");
        android.util.TimeUtils.formatDuration(this.mTotalTime, printWriter);
        printWriter.println();
        for (int i = 0; i < this.mPackageTimes.size(); i++) {
            printWriter.print(str);
            printWriter.print("mPackageTime:");
            printWriter.print(this.mPackageTimes.keyAt(i));
            printWriter.print("=");
            android.util.TimeUtils.formatDuration(this.mPackageTimes.valueAt(i).value, printWriter);
            printWriter.println();
        }
        if (z && this.mStartedTime != 0) {
            printWriter.print(str);
            printWriter.print("mStartedTime=");
            android.util.TimeUtils.formatDuration(android.os.SystemClock.elapsedRealtime(), this.mStartedTime, printWriter);
            printWriter.println();
            printWriter.print(str);
            printWriter.print("mStartedPackage=");
            printWriter.println(this.mStartedPackage);
        }
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j, boolean z) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1138166333441L, this.mReceiver.toString());
        protoOutputStream.write(1112396529666L, this.mTotalTime);
        for (int i = 0; i < this.mPackageTimes.size(); i++) {
            long start2 = protoOutputStream.start(2246267895811L);
            protoOutputStream.write(1138166333441L, this.mPackageTimes.keyAt(i));
            protoOutputStream.write(1112396529666L, this.mPackageTimes.valueAt(i).value);
            protoOutputStream.end(start2);
        }
        if (z && this.mStartedTime != 0) {
            android.util.proto.ProtoUtils.toDuration(protoOutputStream, 1146756268036L, this.mStartedTime, android.os.SystemClock.elapsedRealtime());
            protoOutputStream.write(1138166333445L, this.mStartedPackage);
        }
        protoOutputStream.end(start);
    }
}
