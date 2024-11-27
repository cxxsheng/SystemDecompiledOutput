package android.content;

/* loaded from: classes.dex */
public class OperationApplicationException extends java.lang.Exception {
    private final int mNumSuccessfulYieldPoints;

    public OperationApplicationException() {
        this.mNumSuccessfulYieldPoints = 0;
    }

    public OperationApplicationException(java.lang.String str) {
        super(str);
        this.mNumSuccessfulYieldPoints = 0;
    }

    public OperationApplicationException(java.lang.String str, java.lang.Throwable th) {
        super(str, th);
        this.mNumSuccessfulYieldPoints = 0;
    }

    public OperationApplicationException(java.lang.Throwable th) {
        super(th);
        this.mNumSuccessfulYieldPoints = 0;
    }

    public OperationApplicationException(int i) {
        this.mNumSuccessfulYieldPoints = i;
    }

    public OperationApplicationException(java.lang.String str, int i) {
        super(str);
        this.mNumSuccessfulYieldPoints = i;
    }

    public int getNumSuccessfulYieldPoints() {
        return this.mNumSuccessfulYieldPoints;
    }
}
