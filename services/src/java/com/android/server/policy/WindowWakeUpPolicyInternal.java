package com.android.server.policy;

@com.android.internal.annotations.Keep
/* loaded from: classes2.dex */
public interface WindowWakeUpPolicyInternal {

    @com.android.internal.annotations.Keep
    public interface InputWakeUpDelegate {
        boolean wakeUpFromKey(long j, int i, boolean z);

        boolean wakeUpFromMotion(long j, int i, boolean z);
    }

    @com.android.internal.annotations.Keep
    void setInputWakeUpDelegate(@android.annotation.Nullable com.android.server.policy.WindowWakeUpPolicyInternal.InputWakeUpDelegate inputWakeUpDelegate);
}
