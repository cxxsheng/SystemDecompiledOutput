package android.print;

/* loaded from: classes3.dex */
public final class PrintJob {
    private android.print.PrintJobInfo mCachedInfo;
    private final android.print.PrintManager mPrintManager;

    PrintJob(android.print.PrintJobInfo printJobInfo, android.print.PrintManager printManager) {
        this.mCachedInfo = printJobInfo;
        this.mPrintManager = printManager;
    }

    public android.print.PrintJobId getId() {
        return this.mCachedInfo.getId();
    }

    public android.print.PrintJobInfo getInfo() {
        if (isInImmutableState()) {
            return this.mCachedInfo;
        }
        android.print.PrintJobInfo printJobInfo = this.mPrintManager.getPrintJobInfo(this.mCachedInfo.getId());
        if (printJobInfo != null) {
            this.mCachedInfo = printJobInfo;
        }
        return this.mCachedInfo;
    }

    public void cancel() {
        int state = getInfo().getState();
        if (state == 2 || state == 3 || state == 4 || state == 6) {
            this.mPrintManager.cancelPrintJob(this.mCachedInfo.getId());
        }
    }

    public void restart() {
        if (isFailed()) {
            this.mPrintManager.restartPrintJob(this.mCachedInfo.getId());
        }
    }

    public boolean isQueued() {
        return getInfo().getState() == 2;
    }

    public boolean isStarted() {
        return getInfo().getState() == 3;
    }

    public boolean isBlocked() {
        return getInfo().getState() == 4;
    }

    public boolean isCompleted() {
        return getInfo().getState() == 5;
    }

    public boolean isFailed() {
        return getInfo().getState() == 6;
    }

    public boolean isCancelled() {
        return getInfo().getState() == 7;
    }

    private boolean isInImmutableState() {
        int state = this.mCachedInfo.getState();
        return state == 5 || state == 7;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return java.util.Objects.equals(this.mCachedInfo.getId(), ((android.print.PrintJob) obj).mCachedInfo.getId());
    }

    public int hashCode() {
        android.print.PrintJobId id = this.mCachedInfo.getId();
        if (id == null) {
            return 0;
        }
        return id.hashCode();
    }
}
