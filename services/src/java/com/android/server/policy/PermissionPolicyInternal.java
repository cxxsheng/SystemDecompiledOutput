package com.android.server.policy;

/* loaded from: classes2.dex */
public abstract class PermissionPolicyInternal {

    public interface OnInitializedCallback {
        void onInitialized(int i);
    }

    public abstract boolean checkStartActivity(@android.annotation.NonNull android.content.Intent intent, int i, @android.annotation.Nullable java.lang.String str);

    public abstract boolean isInitialized(int i);

    public abstract boolean isIntentToPermissionDialog(@android.annotation.NonNull android.content.Intent intent);

    public abstract void setOnInitializedCallback(@android.annotation.NonNull com.android.server.policy.PermissionPolicyInternal.OnInitializedCallback onInitializedCallback);

    public abstract boolean shouldShowNotificationDialogForTask(@android.annotation.Nullable android.app.TaskInfo taskInfo, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String str2, @android.annotation.Nullable android.content.Intent intent, @android.annotation.NonNull java.lang.String str3);

    public abstract void showNotificationPromptIfNeeded(@android.annotation.NonNull java.lang.String str, int i, int i2);
}
