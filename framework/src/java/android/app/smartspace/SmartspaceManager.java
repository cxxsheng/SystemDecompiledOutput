package android.app.smartspace;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class SmartspaceManager {
    private final android.content.Context mContext;

    public SmartspaceManager(android.content.Context context) {
        this.mContext = (android.content.Context) java.util.Objects.requireNonNull(context);
    }

    public android.app.smartspace.SmartspaceSession createSmartspaceSession(android.app.smartspace.SmartspaceConfig smartspaceConfig) {
        return new android.app.smartspace.SmartspaceSession(this.mContext, smartspaceConfig);
    }
}
