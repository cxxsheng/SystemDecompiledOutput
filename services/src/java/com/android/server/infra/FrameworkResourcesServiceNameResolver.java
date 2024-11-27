package com.android.server.infra;

/* loaded from: classes2.dex */
public final class FrameworkResourcesServiceNameResolver extends com.android.server.infra.ServiceNameBaseResolver {
    private final int mArrayResourceId;
    private final int mStringResourceId;

    public FrameworkResourcesServiceNameResolver(@android.annotation.NonNull android.content.Context context, int i) {
        super(context, false);
        this.mStringResourceId = i;
        this.mArrayResourceId = -1;
    }

    public FrameworkResourcesServiceNameResolver(@android.annotation.NonNull android.content.Context context, int i, boolean z) {
        super(context, z);
        if (!z) {
            throw new java.lang.UnsupportedOperationException("Please use FrameworkResourcesServiceNameResolver(context, @StringRes int) constructor if single service mode is requested.");
        }
        this.mStringResourceId = -1;
        this.mArrayResourceId = i;
    }

    @Override // com.android.server.infra.ServiceNameBaseResolver
    public java.lang.String[] readServiceNameList(int i) {
        return this.mContext.getResources().getStringArray(this.mArrayResourceId);
    }

    @Override // com.android.server.infra.ServiceNameBaseResolver
    @android.annotation.Nullable
    public java.lang.String readServiceName(int i) {
        return this.mContext.getResources().getString(this.mStringResourceId);
    }

    @Override // com.android.server.infra.ServiceNameResolver
    public void dumpShort(@android.annotation.NonNull java.io.PrintWriter printWriter) {
        synchronized (this.mLock) {
            printWriter.print("FrameworkResourcesServiceNamer: resId=");
            printWriter.print(this.mStringResourceId);
            printWriter.print(", numberTemps=");
            printWriter.print(this.mTemporaryServiceNamesList.size());
            printWriter.print(", enabledDefaults=");
            printWriter.print(this.mDefaultServicesDisabled.size());
        }
    }
}
