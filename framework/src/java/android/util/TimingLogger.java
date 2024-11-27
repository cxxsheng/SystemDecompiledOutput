package android.util;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class TimingLogger {
    private boolean mDisabled;
    private java.lang.String mLabel;
    java.util.ArrayList<java.lang.String> mSplitLabels;
    java.util.ArrayList<java.lang.Long> mSplits;
    private java.lang.String mTag;

    public TimingLogger(java.lang.String str, java.lang.String str2) {
        reset(str, str2);
    }

    public void reset(java.lang.String str, java.lang.String str2) {
        this.mTag = str;
        this.mLabel = str2;
        reset();
    }

    public void reset() {
        this.mDisabled = !android.util.Log.isLoggable(this.mTag, 2);
        if (this.mDisabled) {
            return;
        }
        if (this.mSplits == null) {
            this.mSplits = new java.util.ArrayList<>();
            this.mSplitLabels = new java.util.ArrayList<>();
        } else {
            this.mSplits.clear();
            this.mSplitLabels.clear();
        }
        addSplit(null);
    }

    public void addSplit(java.lang.String str) {
        if (this.mDisabled) {
            return;
        }
        this.mSplits.add(java.lang.Long.valueOf(android.os.SystemClock.elapsedRealtime()));
        this.mSplitLabels.add(str);
    }

    public void dumpToLog() {
        if (this.mDisabled) {
            return;
        }
        android.util.Log.d(this.mTag, this.mLabel + ": begin");
        long longValue = this.mSplits.get(0).longValue();
        long j = longValue;
        for (int i = 1; i < this.mSplits.size(); i++) {
            j = this.mSplits.get(i).longValue();
            android.util.Log.d(this.mTag, this.mLabel + ":      " + (j - this.mSplits.get(i - 1).longValue()) + " ms, " + this.mSplitLabels.get(i));
        }
        android.util.Log.d(this.mTag, this.mLabel + ": end, " + (j - longValue) + " ms");
    }
}
