package com.android.server.integrity.model;

/* loaded from: classes2.dex */
public class RuleMetadata {
    private final java.lang.String mRuleProvider;
    private final java.lang.String mVersion;

    public RuleMetadata(java.lang.String str, java.lang.String str2) {
        this.mRuleProvider = str;
        this.mVersion = str2;
    }

    @android.annotation.Nullable
    public java.lang.String getRuleProvider() {
        return this.mRuleProvider;
    }

    @android.annotation.Nullable
    public java.lang.String getVersion() {
        return this.mVersion;
    }
}
