package android.app;

/* loaded from: classes.dex */
public abstract class AppOpsManagerInternal {
    public abstract int getOpRestrictionCount(int i, android.os.UserHandle userHandle, java.lang.String str, java.lang.String str2);

    public abstract void setDeviceAndProfileOwners(android.util.SparseIntArray sparseIntArray);

    public abstract void setGlobalRestriction(int i, boolean z, android.os.IBinder iBinder);

    public abstract void setModeFromPermissionPolicy(int i, int i2, java.lang.String str, int i3, com.android.internal.app.IAppOpsCallback iAppOpsCallback);

    public abstract void setUidModeFromPermissionPolicy(int i, int i2, int i3, com.android.internal.app.IAppOpsCallback iAppOpsCallback);

    public abstract void updateAppWidgetVisibility(android.util.SparseArray<java.lang.String> sparseArray, boolean z);

    public interface CheckOpsDelegate {
        int checkAudioOperation(int i, int i2, int i3, java.lang.String str, com.android.internal.util.function.QuadFunction<java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.Integer> quadFunction);

        int checkOperation(int i, int i2, java.lang.String str, java.lang.String str2, int i3, boolean z, com.android.internal.util.function.HexFunction<java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Boolean, java.lang.Integer> hexFunction);

        void finishProxyOperation(android.os.IBinder iBinder, int i, android.content.AttributionSource attributionSource, boolean z, com.android.internal.util.function.QuadFunction<android.os.IBinder, java.lang.Integer, android.content.AttributionSource, java.lang.Boolean, java.lang.Void> quadFunction);

        android.app.SyncNotedAppOp noteOperation(int i, int i2, java.lang.String str, java.lang.String str2, int i3, boolean z, java.lang.String str3, boolean z2, com.android.internal.util.function.OctFunction<java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Boolean, java.lang.String, java.lang.Boolean, android.app.SyncNotedAppOp> octFunction);

        android.app.SyncNotedAppOp noteProxyOperation(int i, android.content.AttributionSource attributionSource, boolean z, java.lang.String str, boolean z2, boolean z3, com.android.internal.util.function.HexFunction<java.lang.Integer, android.content.AttributionSource, java.lang.Boolean, java.lang.String, java.lang.Boolean, java.lang.Boolean, android.app.SyncNotedAppOp> hexFunction);

        android.app.SyncNotedAppOp startOperation(android.os.IBinder iBinder, int i, int i2, java.lang.String str, java.lang.String str2, int i3, boolean z, boolean z2, java.lang.String str3, boolean z3, int i4, int i5, com.android.internal.util.function.DodecFunction<android.os.IBinder, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Boolean, java.lang.Boolean, java.lang.String, java.lang.Boolean, java.lang.Integer, java.lang.Integer, android.app.SyncNotedAppOp> dodecFunction);

        android.app.SyncNotedAppOp startProxyOperation(android.os.IBinder iBinder, int i, android.content.AttributionSource attributionSource, boolean z, boolean z2, java.lang.String str, boolean z3, boolean z4, int i2, int i3, int i4, com.android.internal.util.function.UndecFunction<android.os.IBinder, java.lang.Integer, android.content.AttributionSource, java.lang.Boolean, java.lang.Boolean, java.lang.String, java.lang.Boolean, java.lang.Boolean, java.lang.Integer, java.lang.Integer, java.lang.Integer, android.app.SyncNotedAppOp> undecFunction);

        default void finishOperation(android.os.IBinder iBinder, int i, int i2, java.lang.String str, java.lang.String str2, int i3, com.android.internal.util.function.HexConsumer<android.os.IBinder, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String, java.lang.Integer> hexConsumer) {
            hexConsumer.accept(iBinder, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), str, str2, java.lang.Integer.valueOf(i3));
        }
    }
}
