package android.os;

/* loaded from: classes3.dex */
public class ConditionVariable {
    private volatile boolean mCondition;

    public ConditionVariable() {
        this.mCondition = false;
    }

    public ConditionVariable(boolean z) {
        this.mCondition = z;
    }

    public void open() {
        synchronized (this) {
            boolean z = this.mCondition;
            this.mCondition = true;
            if (!z) {
                notifyAll();
            }
        }
    }

    public void close() {
        synchronized (this) {
            this.mCondition = false;
        }
    }

    public void block() {
        synchronized (this) {
            while (!this.mCondition) {
                try {
                    wait();
                } catch (java.lang.InterruptedException e) {
                }
            }
        }
    }

    public boolean block(long j) {
        boolean z;
        if (j != 0) {
            synchronized (this) {
                long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
                long j2 = j + elapsedRealtime;
                while (!this.mCondition && elapsedRealtime < j2) {
                    try {
                        wait(j2 - elapsedRealtime);
                    } catch (java.lang.InterruptedException e) {
                    }
                    elapsedRealtime = android.os.SystemClock.elapsedRealtime();
                }
                z = this.mCondition;
            }
            return z;
        }
        block();
        return true;
    }
}
