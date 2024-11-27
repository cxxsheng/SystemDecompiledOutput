package com.google.security.cryptauth.lib.securegcm.ukey2;

/* loaded from: classes3.dex */
public class D2DHandshakeContext {
    private final long contextPtr;

    public enum Role {
        INITIATOR,
        RESPONDER
    }

    private static native long create_context(boolean z);

    private static native byte[] get_next_handshake_message(long j) throws com.google.security.cryptauth.lib.securegcm.ukey2.BadHandleException;

    private static native byte[] get_verification_string(long j, int i) throws com.google.security.cryptauth.lib.securegcm.ukey2.BadHandleException, com.google.security.cryptauth.lib.securegcm.ukey2.HandshakeException;

    private static native boolean is_handshake_complete(long j) throws com.google.security.cryptauth.lib.securegcm.ukey2.BadHandleException;

    private static native void parse_handshake_message(long j, byte[] bArr) throws com.google.security.cryptauth.lib.securegcm.ukey2.BadHandleException, com.google.security.cryptauth.lib.securegcm.ukey2.HandshakeException;

    private static native long to_connection_context(long j) throws com.google.security.cryptauth.lib.securegcm.ukey2.HandshakeException;

    static {
        java.lang.System.loadLibrary("ukey2_jni");
    }

    public D2DHandshakeContext(@javax.annotation.Nonnull com.google.security.cryptauth.lib.securegcm.ukey2.D2DHandshakeContext.Role role) {
        this.contextPtr = create_context(role == com.google.security.cryptauth.lib.securegcm.ukey2.D2DHandshakeContext.Role.INITIATOR);
    }

    public static com.google.security.cryptauth.lib.securegcm.ukey2.D2DHandshakeContext forInitiator() {
        return new com.google.security.cryptauth.lib.securegcm.ukey2.D2DHandshakeContext(com.google.security.cryptauth.lib.securegcm.ukey2.D2DHandshakeContext.Role.INITIATOR);
    }

    public static com.google.security.cryptauth.lib.securegcm.ukey2.D2DHandshakeContext forResponder() {
        return new com.google.security.cryptauth.lib.securegcm.ukey2.D2DHandshakeContext(com.google.security.cryptauth.lib.securegcm.ukey2.D2DHandshakeContext.Role.RESPONDER);
    }

    public boolean isHandshakeComplete() throws com.google.security.cryptauth.lib.securegcm.ukey2.BadHandleException {
        return is_handshake_complete(this.contextPtr);
    }

    @javax.annotation.Nonnull
    public byte[] getNextHandshakeMessage() throws com.google.security.cryptauth.lib.securegcm.ukey2.BadHandleException {
        return get_next_handshake_message(this.contextPtr);
    }

    @javax.annotation.Nonnull
    public void parseHandshakeMessage(@javax.annotation.Nonnull byte[] bArr) throws com.google.security.cryptauth.lib.securegcm.ukey2.BadHandleException, com.google.security.cryptauth.lib.securegcm.ukey2.HandshakeException {
        parse_handshake_message(this.contextPtr, bArr);
    }

    @javax.annotation.Nonnull
    public byte[] getVerificationString(int i) throws com.google.security.cryptauth.lib.securegcm.ukey2.BadHandleException, com.google.security.cryptauth.lib.securegcm.ukey2.HandshakeException {
        return get_verification_string(this.contextPtr, i);
    }

    public com.google.security.cryptauth.lib.securegcm.ukey2.D2DConnectionContextV1 toConnectionContext() throws com.google.security.cryptauth.lib.securegcm.ukey2.HandshakeException {
        return new com.google.security.cryptauth.lib.securegcm.ukey2.D2DConnectionContextV1(to_connection_context(this.contextPtr));
    }
}
