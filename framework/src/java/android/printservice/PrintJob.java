package android.printservice;

/* loaded from: classes3.dex */
public final class PrintJob {
    private static final java.lang.String LOG_TAG = "PrintJob";
    private android.print.PrintJobInfo mCachedInfo;
    private final android.content.Context mContext;
    private final android.printservice.PrintDocument mDocument;
    private final android.printservice.IPrintServiceClient mPrintServiceClient;

    PrintJob(android.content.Context context, android.print.PrintJobInfo printJobInfo, android.printservice.IPrintServiceClient iPrintServiceClient) {
        this.mContext = context;
        this.mCachedInfo = printJobInfo;
        this.mPrintServiceClient = iPrintServiceClient;
        this.mDocument = new android.printservice.PrintDocument(this.mCachedInfo.getId(), iPrintServiceClient, printJobInfo.getDocumentInfo());
    }

    public android.print.PrintJobId getId() {
        android.printservice.PrintService.throwIfNotCalledOnMainThread();
        return this.mCachedInfo.getId();
    }

    public android.print.PrintJobInfo getInfo() {
        android.print.PrintJobInfo printJobInfo;
        android.printservice.PrintService.throwIfNotCalledOnMainThread();
        if (isInImmutableState()) {
            return this.mCachedInfo;
        }
        try {
            printJobInfo = this.mPrintServiceClient.getPrintJobInfo(this.mCachedInfo.getId());
        } catch (android.os.RemoteException e) {
            android.util.Log.e(LOG_TAG, "Couldn't get info for job: " + this.mCachedInfo.getId(), e);
            printJobInfo = null;
        }
        if (printJobInfo != null) {
            this.mCachedInfo = printJobInfo;
        }
        return this.mCachedInfo;
    }

    public android.printservice.PrintDocument getDocument() {
        android.printservice.PrintService.throwIfNotCalledOnMainThread();
        return this.mDocument;
    }

    public boolean isQueued() {
        android.printservice.PrintService.throwIfNotCalledOnMainThread();
        return getInfo().getState() == 2;
    }

    public boolean isStarted() {
        android.printservice.PrintService.throwIfNotCalledOnMainThread();
        return getInfo().getState() == 3;
    }

    public boolean isBlocked() {
        android.printservice.PrintService.throwIfNotCalledOnMainThread();
        return getInfo().getState() == 4;
    }

    public boolean isCompleted() {
        android.printservice.PrintService.throwIfNotCalledOnMainThread();
        return getInfo().getState() == 5;
    }

    public boolean isFailed() {
        android.printservice.PrintService.throwIfNotCalledOnMainThread();
        return getInfo().getState() == 6;
    }

    public boolean isCancelled() {
        android.printservice.PrintService.throwIfNotCalledOnMainThread();
        return getInfo().getState() == 7;
    }

    public boolean start() {
        android.printservice.PrintService.throwIfNotCalledOnMainThread();
        int state = getInfo().getState();
        if (state == 2 || state == 4) {
            return setState(3, null);
        }
        return false;
    }

    public boolean block(java.lang.String str) {
        android.printservice.PrintService.throwIfNotCalledOnMainThread();
        int state = getInfo().getState();
        if (state == 3 || state == 4) {
            return setState(4, str);
        }
        return false;
    }

    public boolean complete() {
        android.printservice.PrintService.throwIfNotCalledOnMainThread();
        if (isStarted()) {
            return setState(5, null);
        }
        return false;
    }

    public boolean fail(java.lang.String str) {
        android.printservice.PrintService.throwIfNotCalledOnMainThread();
        if (!isInImmutableState()) {
            return setState(6, str);
        }
        return false;
    }

    public boolean cancel() {
        android.printservice.PrintService.throwIfNotCalledOnMainThread();
        if (!isInImmutableState()) {
            return setState(7, null);
        }
        return false;
    }

    public void setProgress(float f) {
        android.printservice.PrintService.throwIfNotCalledOnMainThread();
        try {
            this.mPrintServiceClient.setProgress(this.mCachedInfo.getId(), f);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(LOG_TAG, "Error setting progress for job: " + this.mCachedInfo.getId(), e);
        }
    }

    public void setStatus(java.lang.CharSequence charSequence) {
        android.printservice.PrintService.throwIfNotCalledOnMainThread();
        try {
            this.mPrintServiceClient.setStatus(this.mCachedInfo.getId(), charSequence);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(LOG_TAG, "Error setting status for job: " + this.mCachedInfo.getId(), e);
        }
    }

    public void setStatus(int i) {
        android.printservice.PrintService.throwIfNotCalledOnMainThread();
        try {
            this.mPrintServiceClient.setStatusRes(this.mCachedInfo.getId(), i, this.mContext.getPackageName());
        } catch (android.os.RemoteException e) {
            android.util.Log.e(LOG_TAG, "Error setting status for job: " + this.mCachedInfo.getId(), e);
        }
    }

    public boolean setTag(java.lang.String str) {
        android.printservice.PrintService.throwIfNotCalledOnMainThread();
        if (isInImmutableState()) {
            return false;
        }
        try {
            return this.mPrintServiceClient.setPrintJobTag(this.mCachedInfo.getId(), str);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(LOG_TAG, "Error setting tag for job: " + this.mCachedInfo.getId(), e);
            return false;
        }
    }

    public java.lang.String getTag() {
        android.printservice.PrintService.throwIfNotCalledOnMainThread();
        return getInfo().getTag();
    }

    public java.lang.String getAdvancedStringOption(java.lang.String str) {
        android.printservice.PrintService.throwIfNotCalledOnMainThread();
        return getInfo().getAdvancedStringOption(str);
    }

    public boolean hasAdvancedOption(java.lang.String str) {
        android.printservice.PrintService.throwIfNotCalledOnMainThread();
        return getInfo().hasAdvancedOption(str);
    }

    public int getAdvancedIntOption(java.lang.String str) {
        android.printservice.PrintService.throwIfNotCalledOnMainThread();
        return getInfo().getAdvancedIntOption(str);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.mCachedInfo.getId().equals(((android.printservice.PrintJob) obj).mCachedInfo.getId());
    }

    public int hashCode() {
        return this.mCachedInfo.getId().hashCode();
    }

    private boolean isInImmutableState() {
        int state = this.mCachedInfo.getState();
        return state == 5 || state == 7 || state == 6;
    }

    private boolean setState(int i, java.lang.String str) {
        try {
            if (this.mPrintServiceClient.setPrintJobState(this.mCachedInfo.getId(), i, str)) {
                this.mCachedInfo.setState(i);
                this.mCachedInfo.setStatus(str);
                return true;
            }
            return false;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(LOG_TAG, "Error setting the state of job: " + this.mCachedInfo.getId(), e);
            return false;
        }
    }
}
