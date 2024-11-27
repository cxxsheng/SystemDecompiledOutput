package com.android.server.credentials;

/* loaded from: classes.dex */
public class NonCredentialProviderCallerException extends java.lang.RuntimeException {
    private static final java.lang.String MESSAGE = " is not an existing Credential Provider.";

    public NonCredentialProviderCallerException(java.lang.String str) {
        super(str + MESSAGE);
    }
}
