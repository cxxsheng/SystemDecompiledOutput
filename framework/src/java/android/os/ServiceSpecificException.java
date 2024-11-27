package android.os;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public class ServiceSpecificException extends java.lang.RuntimeException {
    public final int errorCode;

    public ServiceSpecificException(int i, java.lang.String str) {
        super(str);
        this.errorCode = i;
    }

    public ServiceSpecificException(int i) {
        this.errorCode = i;
    }

    @Override // java.lang.Throwable
    public java.lang.String toString() {
        return super.toString() + " (code " + this.errorCode + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
    }
}
