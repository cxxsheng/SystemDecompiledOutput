package com.android.server.biometrics.sensors.face;

/* loaded from: classes.dex */
public class UsageStats {
    private static final int EVENT_LOG_SIZE = 100;
    private int mAcceptCount;
    private long mAcceptLatency;
    private int mAuthAttemptCount;
    private android.content.Context mContext;
    private int mErrorCount;
    private long mErrorLatency;
    private int mRejectCount;
    private long mRejectLatency;
    private java.util.ArrayDeque<com.android.server.biometrics.sensors.face.UsageStats.AuthenticationEvent> mAuthenticationEvents = new java.util.ArrayDeque<>();
    private android.util.SparseIntArray mErrorFrequencyMap = new android.util.SparseIntArray();
    private android.util.SparseLongArray mErrorLatencyMap = new android.util.SparseLongArray();

    public static final class AuthenticationEvent {
        private boolean mAuthenticated;
        private int mError;
        private long mLatency;
        private long mStartTime;
        private int mUser;
        private int mVendorError;

        public AuthenticationEvent(long j, long j2, boolean z, int i, int i2, int i3) {
            this.mStartTime = j;
            this.mLatency = j2;
            this.mAuthenticated = z;
            this.mError = i;
            this.mVendorError = i2;
            this.mUser = i3;
        }

        public java.lang.String toString(android.content.Context context) {
            return "Start: " + this.mStartTime + "\tLatency: " + this.mLatency + "\tAuthenticated: " + this.mAuthenticated + "\tError: " + this.mError + "\tVendorCode: " + this.mVendorError + "\tUser: " + this.mUser + "\t" + android.hardware.face.FaceManager.getErrorString(context, this.mError, this.mVendorError);
        }
    }

    public UsageStats(android.content.Context context) {
        this.mContext = context;
    }

    public void addEvent(com.android.server.biometrics.sensors.face.UsageStats.AuthenticationEvent authenticationEvent) {
        this.mAuthAttemptCount++;
        if (this.mAuthenticationEvents.size() >= 100) {
            this.mAuthenticationEvents.removeFirst();
        }
        this.mAuthenticationEvents.add(authenticationEvent);
        if (authenticationEvent.mAuthenticated) {
            this.mAcceptCount++;
            this.mAcceptLatency += authenticationEvent.mLatency;
        } else if (authenticationEvent.mError == 0) {
            this.mRejectCount++;
            this.mRejectLatency += authenticationEvent.mLatency;
        } else {
            this.mErrorCount++;
            this.mErrorLatency += authenticationEvent.mLatency;
            this.mErrorFrequencyMap.put(authenticationEvent.mError, this.mErrorFrequencyMap.get(authenticationEvent.mError, 0) + 1);
            this.mErrorLatencyMap.put(authenticationEvent.mError, this.mErrorLatencyMap.get(authenticationEvent.mError, 0L) + authenticationEvent.mLatency);
        }
    }

    public void print(java.io.PrintWriter printWriter) {
        printWriter.println("Printing most recent events since last reboot(" + this.mAuthenticationEvents.size() + " events)");
        java.util.Iterator<com.android.server.biometrics.sensors.face.UsageStats.AuthenticationEvent> it = this.mAuthenticationEvents.iterator();
        while (it.hasNext()) {
            printWriter.println(it.next().toString(this.mContext));
        }
        printWriter.println("");
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("Accept Count: ");
        sb.append(this.mAcceptCount);
        sb.append("\tLatency: ");
        sb.append(this.mAcceptLatency);
        sb.append("\tAverage: ");
        sb.append(this.mAcceptCount > 0 ? this.mAcceptLatency / this.mAcceptCount : 0L);
        printWriter.println(sb.toString());
        java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
        sb2.append("Reject Count: ");
        sb2.append(this.mRejectCount);
        sb2.append("\tLatency: ");
        sb2.append(this.mRejectLatency);
        sb2.append("\tAverage: ");
        sb2.append(this.mRejectCount > 0 ? this.mRejectLatency / this.mRejectCount : 0L);
        printWriter.println(sb2.toString());
        java.lang.StringBuilder sb3 = new java.lang.StringBuilder();
        sb3.append("Total Error Count: ");
        sb3.append(this.mErrorCount);
        sb3.append("\tLatency: ");
        sb3.append(this.mErrorLatency);
        sb3.append("\tAverage: ");
        sb3.append(this.mErrorCount > 0 ? this.mErrorLatency / this.mErrorCount : 0L);
        printWriter.println(sb3.toString());
        printWriter.println("Total Attempts: " + this.mAuthAttemptCount);
        printWriter.println("");
        for (int i = 0; i < this.mErrorFrequencyMap.size(); i++) {
            int keyAt = this.mErrorFrequencyMap.keyAt(i);
            int i2 = this.mErrorFrequencyMap.get(keyAt);
            java.lang.StringBuilder sb4 = new java.lang.StringBuilder();
            sb4.append("Error");
            sb4.append(keyAt);
            sb4.append("\tCount: ");
            sb4.append(i2);
            sb4.append("\tLatency: ");
            sb4.append(this.mErrorLatencyMap.get(keyAt, 0L));
            sb4.append("\tAverage: ");
            sb4.append(i2 > 0 ? this.mErrorLatencyMap.get(keyAt, 0L) / i2 : 0L);
            sb4.append("\t");
            sb4.append(android.hardware.face.FaceManager.getErrorString(this.mContext, keyAt, 0));
            printWriter.println(sb4.toString());
        }
    }
}
