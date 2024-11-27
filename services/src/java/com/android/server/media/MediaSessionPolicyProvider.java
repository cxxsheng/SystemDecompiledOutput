package com.android.server.media;

/* loaded from: classes2.dex */
public abstract class MediaSessionPolicyProvider {
    static final int SESSION_POLICY_IGNORE_BUTTON_RECEIVER = 1;
    static final int SESSION_POLICY_IGNORE_BUTTON_SESSION = 2;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface SessionPolicy {
    }

    public MediaSessionPolicyProvider(android.content.Context context) {
    }

    int getSessionPoliciesForApplication(int i, @android.annotation.NonNull java.lang.String str) {
        return 0;
    }
}
