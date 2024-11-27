package com.google.security.cryptauth.lib.securegcm.ukey2;

/* loaded from: classes3.dex */
public class D2DConnectionContextV1 {
    private final long contextPtr;

    private static native byte[] decode_message_from_peer(long j, byte[] bArr, byte[] bArr2) throws com.google.security.cryptauth.lib.securegcm.ukey2.CryptoException;

    private static native byte[] encode_message_to_peer(long j, byte[] bArr, byte[] bArr2) throws com.google.security.cryptauth.lib.securegcm.ukey2.BadHandleException;

    private static native long from_saved_session(byte[] bArr);

    private static native int get_sequence_number_for_decoding(long j) throws com.google.security.cryptauth.lib.securegcm.ukey2.BadHandleException;

    private static native int get_sequence_number_for_encoding(long j) throws com.google.security.cryptauth.lib.securegcm.ukey2.BadHandleException;

    private static native byte[] get_session_unique(long j) throws com.google.security.cryptauth.lib.securegcm.ukey2.BadHandleException;

    private static native byte[] save_session(long j) throws com.google.security.cryptauth.lib.securegcm.ukey2.BadHandleException;

    static {
        java.lang.String property = java.lang.System.getProperty("java.library.path");
        if (property == null) {
            throw new java.lang.RuntimeException("Path isn't set.");
        }
        java.util.List of = java.util.List.of((java.lang.Object[]) property.split(";"));
        final java.io.PrintStream printStream = java.lang.System.out;
        java.util.Objects.requireNonNull(printStream);
        of.forEach(new java.util.function.Consumer() { // from class: com.google.security.cryptauth.lib.securegcm.ukey2.D2DConnectionContextV1$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                printStream.println((java.lang.String) obj);
            }
        });
        java.lang.System.loadLibrary("ukey2_jni");
    }

    D2DConnectionContextV1(@javax.annotation.Nonnull long j) {
        this.contextPtr = j;
    }

    @javax.annotation.Nonnull
    public byte[] encodeMessageToPeer(@javax.annotation.Nonnull byte[] bArr, @javax.annotation.Nullable byte[] bArr2) throws com.google.security.cryptauth.lib.securegcm.ukey2.BadHandleException {
        return encode_message_to_peer(this.contextPtr, bArr, bArr2);
    }

    @javax.annotation.Nonnull
    public byte[] decodeMessageFromPeer(@javax.annotation.Nonnull byte[] bArr, @javax.annotation.Nullable byte[] bArr2) throws com.google.security.cryptauth.lib.securegcm.ukey2.CryptoException {
        return decode_message_from_peer(this.contextPtr, bArr, bArr2);
    }

    @javax.annotation.Nonnull
    public byte[] getSessionUnique() throws com.google.security.cryptauth.lib.securegcm.ukey2.BadHandleException {
        return get_session_unique(this.contextPtr);
    }

    public int getSequenceNumberForEncoding() throws com.google.security.cryptauth.lib.securegcm.ukey2.BadHandleException {
        return get_sequence_number_for_encoding(this.contextPtr);
    }

    public int getSequenceNumberForDecoding() throws com.google.security.cryptauth.lib.securegcm.ukey2.BadHandleException {
        return get_sequence_number_for_decoding(this.contextPtr);
    }

    @javax.annotation.Nonnull
    public byte[] saveSession() throws com.google.security.cryptauth.lib.securegcm.ukey2.BadHandleException {
        return save_session(this.contextPtr);
    }

    public static com.google.security.cryptauth.lib.securegcm.ukey2.D2DConnectionContextV1 fromSavedSession(@javax.annotation.Nonnull byte[] bArr) {
        return new com.google.security.cryptauth.lib.securegcm.ukey2.D2DConnectionContextV1(from_saved_session(bArr));
    }
}
