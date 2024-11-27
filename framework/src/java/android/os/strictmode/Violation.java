package android.os.strictmode;

/* loaded from: classes3.dex */
public abstract class Violation extends java.lang.Throwable {
    private int mHashCode;
    private boolean mHashCodeValid;

    Violation(java.lang.String str) {
        super(str);
    }

    public int hashCode() {
        synchronized (this) {
            if (this.mHashCodeValid) {
                return this.mHashCode;
            }
            java.lang.Object message = getMessage();
            java.lang.Throwable cause = getCause();
            if (message == null) {
                message = getClass();
            }
            int hashCode = (((message.hashCode() * 37) + calcStackTraceHashCode(getStackTrace())) * 37) + (cause != null ? cause.toString().hashCode() : 0);
            this.mHashCodeValid = true;
            this.mHashCode = hashCode;
            return hashCode;
        }
    }

    @Override // java.lang.Throwable
    public synchronized java.lang.Throwable initCause(java.lang.Throwable th) {
        this.mHashCodeValid = false;
        return super.initCause(th);
    }

    @Override // java.lang.Throwable
    public void setStackTrace(java.lang.StackTraceElement[] stackTraceElementArr) {
        super.setStackTrace(stackTraceElementArr);
        synchronized (this) {
            this.mHashCodeValid = false;
        }
    }

    @Override // java.lang.Throwable
    public synchronized java.lang.Throwable fillInStackTrace() {
        this.mHashCodeValid = false;
        return super.fillInStackTrace();
    }

    private static int calcStackTraceHashCode(java.lang.StackTraceElement[] stackTraceElementArr) {
        int i = 17;
        if (stackTraceElementArr != null) {
            for (int i2 = 0; i2 < stackTraceElementArr.length; i2++) {
                if (stackTraceElementArr[i2] != null) {
                    i = (i * 37) + stackTraceElementArr[i2].hashCode();
                }
            }
        }
        return i;
    }
}
