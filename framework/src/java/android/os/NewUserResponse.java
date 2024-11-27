package android.os;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class NewUserResponse {
    private final int mOperationResult;
    private final android.os.UserHandle mUser;

    public NewUserResponse(android.os.UserHandle userHandle, int i) {
        this.mUser = userHandle;
        this.mOperationResult = i;
    }

    public boolean isSuccessful() {
        return this.mUser != null;
    }

    public android.os.UserHandle getUser() {
        return this.mUser;
    }

    public int getOperationResult() {
        return this.mOperationResult;
    }

    public java.lang.String toString() {
        return "NewUserResponse{mUser=" + this.mUser + ", mOperationResult=" + this.mOperationResult + '}';
    }
}
