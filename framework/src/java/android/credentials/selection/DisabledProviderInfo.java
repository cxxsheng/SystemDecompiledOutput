package android.credentials.selection;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class DisabledProviderInfo {
    private final java.lang.String mProviderName;

    public DisabledProviderInfo(java.lang.String str) {
        this.mProviderName = (java.lang.String) com.android.internal.util.Preconditions.checkStringNotEmpty(str);
    }

    public java.lang.String getProviderName() {
        return this.mProviderName;
    }
}
