package com.android.server.companion.securechannel;

/* loaded from: classes.dex */
public class SecureChannel {
    private static final boolean DEBUG = android.os.Build.IS_DEBUGGABLE;
    private static final int HEADER_LENGTH = 6;
    private static final java.lang.String TAG = "CDM_SecureChannel";
    private static final int VERSION = 1;
    private java.lang.String mAlias;
    private final com.android.server.companion.securechannel.SecureChannel.Callback mCallback;
    private byte[] mClientInit;
    private com.google.security.cryptauth.lib.securegcm.ukey2.D2DConnectionContextV1 mConnectionContext;
    private com.google.security.cryptauth.lib.securegcm.ukey2.D2DHandshakeContext mHandshakeContext;
    private volatile boolean mInProgress;
    private final java.io.InputStream mInput;
    private final java.io.OutputStream mOutput;
    private final byte[] mPreSharedKey;
    private boolean mPskVerified;
    private com.google.security.cryptauth.lib.securegcm.ukey2.D2DHandshakeContext.Role mRole;
    private volatile boolean mStopped;
    private int mVerificationResult;
    private final com.android.server.companion.securechannel.AttestationVerifier mVerifier;

    public interface Callback {
        void onError(java.lang.Throwable th);

        void onSecureConnection();

        void onSecureMessageReceived(byte[] bArr);
    }

    public SecureChannel(@android.annotation.NonNull java.io.InputStream inputStream, @android.annotation.NonNull java.io.OutputStream outputStream, @android.annotation.NonNull com.android.server.companion.securechannel.SecureChannel.Callback callback, @android.annotation.NonNull byte[] bArr) {
        this(inputStream, outputStream, callback, bArr, null);
    }

    public SecureChannel(@android.annotation.NonNull java.io.InputStream inputStream, @android.annotation.NonNull java.io.OutputStream outputStream, @android.annotation.NonNull com.android.server.companion.securechannel.SecureChannel.Callback callback, @android.annotation.NonNull android.content.Context context) {
        this(inputStream, outputStream, callback, null, new com.android.server.companion.securechannel.AttestationVerifier(context));
    }

    public SecureChannel(java.io.InputStream inputStream, java.io.OutputStream outputStream, com.android.server.companion.securechannel.SecureChannel.Callback callback, byte[] bArr, com.android.server.companion.securechannel.AttestationVerifier attestationVerifier) {
        this.mInput = inputStream;
        this.mOutput = outputStream;
        this.mCallback = callback;
        this.mPreSharedKey = bArr;
        this.mVerifier = attestationVerifier;
    }

    public void start() {
        if (DEBUG) {
            android.util.Slog.d(TAG, "Starting secure channel.");
        }
        this.mStopped = false;
        new java.lang.Thread(new java.lang.Runnable() { // from class: com.android.server.companion.securechannel.SecureChannel$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.companion.securechannel.SecureChannel.this.lambda$start$0();
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$start$0() {
        try {
            exchangeHandshake();
            exchangeAuthentication();
            this.mInProgress = false;
            this.mCallback.onSecureConnection();
            while (!this.mStopped) {
                receiveSecureMessage();
            }
        } catch (java.lang.Exception e) {
            if (this.mStopped) {
                return;
            }
            android.util.Slog.e(TAG, "Secure channel encountered an error.", e);
            close();
            this.mCallback.onError(e);
        }
    }

    public void stop() {
        if (DEBUG) {
            android.util.Slog.d(TAG, "Stopping secure channel.");
        }
        this.mStopped = true;
        this.mInProgress = false;
    }

    public void close() {
        stop();
        if (DEBUG) {
            android.util.Slog.d(TAG, "Closing secure channel.");
        }
        libcore.io.IoUtils.closeQuietly(this.mInput);
        libcore.io.IoUtils.closeQuietly(this.mOutput);
        com.android.server.companion.securechannel.KeyStoreUtils.cleanUp(this.mAlias);
    }

    public boolean isStopped() {
        return this.mStopped;
    }

    public void establishSecureConnection() throws java.io.IOException, com.android.server.companion.securechannel.SecureChannelException {
        if (isSecured()) {
            android.util.Slog.d(TAG, "Channel is already secure.");
            return;
        }
        if (this.mInProgress) {
            android.util.Slog.w(TAG, "Channel has already started establishing secure connection.");
            return;
        }
        try {
            this.mInProgress = true;
            initiateHandshake();
        } catch (com.google.security.cryptauth.lib.securegcm.ukey2.BadHandleException e) {
            throw new com.android.server.companion.securechannel.SecureChannelException("Failed to initiate handshake protocol.", e);
        }
    }

    public void sendSecureMessage(byte[] bArr) throws java.io.IOException {
        if (!isSecured()) {
            android.util.Slog.d(TAG, "Cannot send a message without a secure connection.");
            throw new java.lang.IllegalStateException("Channel is not secured yet.");
        }
        try {
            sendMessage(com.android.server.companion.securechannel.SecureChannel.MessageType.SECURE_MESSAGE, bArr);
        } catch (com.google.security.cryptauth.lib.securegcm.ukey2.BadHandleException e) {
            throw new com.android.server.companion.securechannel.SecureChannelException("Failed to encrypt data.", e);
        }
    }

    private void receiveSecureMessage() throws java.io.IOException, com.google.security.cryptauth.lib.securegcm.ukey2.CryptoException {
        if (!isSecured()) {
            android.util.Slog.d(TAG, "Received a message without a secure connection. Message will be ignored.");
            this.mCallback.onError(new java.lang.IllegalStateException("Connection is not secure."));
            return;
        }
        try {
            this.mCallback.onSecureMessageReceived(readMessage(com.android.server.companion.securechannel.SecureChannel.MessageType.SECURE_MESSAGE));
        } catch (com.android.server.companion.securechannel.SecureChannelException e) {
            android.util.Slog.w(TAG, "Ignoring received message.", e);
        }
    }

    private byte[] readMessage(com.android.server.companion.securechannel.SecureChannel.MessageType messageType) throws java.io.IOException, com.android.server.companion.securechannel.SecureChannelException, com.google.security.cryptauth.lib.securegcm.ukey2.CryptoException {
        if (DEBUG) {
            if (isSecured()) {
                android.util.Slog.d(TAG, "Waiting to receive next secure message.");
            } else {
                android.util.Slog.d(TAG, "Waiting to receive next " + messageType + " message.");
            }
        }
        synchronized (this.mInput) {
            try {
                byte[] bArr = new byte[6];
                libcore.io.Streams.readFully(this.mInput, bArr);
                java.nio.ByteBuffer wrap = java.nio.ByteBuffer.wrap(bArr);
                int i = wrap.getInt();
                short s = wrap.getShort();
                if (i != 1) {
                    libcore.io.Streams.skipByReading(this.mInput, com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME);
                    throw new com.android.server.companion.securechannel.SecureChannelException("Secure channel version mismatch. Currently on version 1. Skipping rest of data.");
                }
                if (s != messageType.mValue) {
                    libcore.io.Streams.skipByReading(this.mInput, com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME);
                    throw new com.android.server.companion.securechannel.SecureChannelException("Unexpected message type. Expected " + messageType.name() + "; Found " + com.android.server.companion.securechannel.SecureChannel.MessageType.from(s).name() + ". Skipping rest of data.");
                }
                byte[] bArr2 = new byte[4];
                libcore.io.Streams.readFully(this.mInput, bArr2);
                try {
                    byte[] bArr3 = new byte[java.nio.ByteBuffer.wrap(bArr2).getInt()];
                    libcore.io.Streams.readFully(this.mInput, bArr3);
                    if (!com.android.server.companion.securechannel.SecureChannel.MessageType.shouldEncrypt(messageType)) {
                        return bArr3;
                    }
                    return this.mConnectionContext.decodeMessageFromPeer(bArr3, bArr);
                } catch (java.lang.OutOfMemoryError e) {
                    libcore.io.Streams.skipByReading(this.mInput, com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME);
                    throw new com.android.server.companion.securechannel.SecureChannelException("Payload is too large.", e);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void sendMessage(com.android.server.companion.securechannel.SecureChannel.MessageType messageType, byte[] bArr) throws java.io.IOException, com.google.security.cryptauth.lib.securegcm.ukey2.BadHandleException {
        synchronized (this.mOutput) {
            try {
                byte[] array = java.nio.ByteBuffer.allocate(6).putInt(1).putShort(messageType.mValue).array();
                if (com.android.server.companion.securechannel.SecureChannel.MessageType.shouldEncrypt(messageType)) {
                    bArr = this.mConnectionContext.encodeMessageToPeer(bArr, array);
                }
                this.mOutput.write(array);
                this.mOutput.write(java.nio.ByteBuffer.allocate(4).putInt(bArr.length).array());
                this.mOutput.write(bArr);
                this.mOutput.flush();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void initiateHandshake() throws java.io.IOException, com.google.security.cryptauth.lib.securegcm.ukey2.BadHandleException {
        if (this.mConnectionContext != null) {
            android.util.Slog.d(TAG, "Ukey2 handshake is already completed.");
            return;
        }
        this.mRole = com.google.security.cryptauth.lib.securegcm.ukey2.D2DHandshakeContext.Role.INITIATOR;
        this.mHandshakeContext = com.google.security.cryptauth.lib.securegcm.ukey2.D2DHandshakeContext.forInitiator();
        this.mClientInit = this.mHandshakeContext.getNextHandshakeMessage();
        if (DEBUG) {
            android.util.Slog.d(TAG, "Sending Ukey2 Client Init message");
        }
        sendMessage(com.android.server.companion.securechannel.SecureChannel.MessageType.HANDSHAKE_INIT, constructHandshakeInitMessage(this.mClientInit));
    }

    private byte[] handleHandshakeCollision(byte[] bArr) throws java.io.IOException, com.google.security.cryptauth.lib.securegcm.ukey2.HandshakeException, com.google.security.cryptauth.lib.securegcm.ukey2.BadHandleException, com.google.security.cryptauth.lib.securegcm.ukey2.CryptoException {
        java.nio.ByteBuffer wrap = java.nio.ByteBuffer.wrap(bArr);
        boolean z = wrap.get() == 0;
        byte[] bArr2 = new byte[wrap.remaining()];
        wrap.get(bArr2);
        if (this.mHandshakeContext == null || !z) {
            return bArr2;
        }
        android.util.Slog.w(TAG, "Detected a Ukey2 handshake role collision. Negotiating a role.");
        if (compareByteArray(this.mClientInit, bArr2) < 0) {
            android.util.Slog.d(TAG, "Assigned: Responder");
            this.mHandshakeContext = null;
            return bArr2;
        }
        android.util.Slog.d(TAG, "Assigned: Initiator; Discarding received Client Init");
        java.nio.ByteBuffer wrap2 = java.nio.ByteBuffer.wrap(readMessage(com.android.server.companion.securechannel.SecureChannel.MessageType.HANDSHAKE_INIT));
        if (wrap2.get() == 0) {
            throw new com.google.security.cryptauth.lib.securegcm.ukey2.HandshakeException("Failed to resolve Ukey2 handshake role collision.");
        }
        byte[] bArr3 = new byte[wrap2.remaining()];
        wrap2.get(bArr3);
        return bArr3;
    }

    private void exchangeHandshake() throws java.io.IOException, com.google.security.cryptauth.lib.securegcm.ukey2.HandshakeException, com.google.security.cryptauth.lib.securegcm.ukey2.BadHandleException, com.google.security.cryptauth.lib.securegcm.ukey2.CryptoException {
        if (this.mConnectionContext != null) {
            android.util.Slog.d(TAG, "Ukey2 handshake is already completed.");
            return;
        }
        byte[] readMessage = readMessage(com.android.server.companion.securechannel.SecureChannel.MessageType.HANDSHAKE_INIT);
        this.mInProgress = true;
        byte[] handleHandshakeCollision = handleHandshakeCollision(readMessage);
        if (this.mHandshakeContext == null) {
            this.mRole = com.google.security.cryptauth.lib.securegcm.ukey2.D2DHandshakeContext.Role.RESPONDER;
            this.mHandshakeContext = com.google.security.cryptauth.lib.securegcm.ukey2.D2DHandshakeContext.forResponder();
            if (DEBUG) {
                android.util.Slog.d(TAG, "Receiving Ukey2 Client Init message");
            }
            this.mHandshakeContext.parseHandshakeMessage(handleHandshakeCollision);
            if (DEBUG) {
                android.util.Slog.d(TAG, "Sending Ukey2 Server Init message");
            }
            sendMessage(com.android.server.companion.securechannel.SecureChannel.MessageType.HANDSHAKE_INIT, constructHandshakeInitMessage(this.mHandshakeContext.getNextHandshakeMessage()));
            if (DEBUG) {
                android.util.Slog.d(TAG, "Receiving Ukey2 Client Finish message");
            }
            this.mHandshakeContext.parseHandshakeMessage(readMessage(com.android.server.companion.securechannel.SecureChannel.MessageType.HANDSHAKE_FINISH));
        } else {
            if (DEBUG) {
                android.util.Slog.d(TAG, "Receiving Ukey2 Server Init message");
            }
            this.mHandshakeContext.parseHandshakeMessage(handleHandshakeCollision);
            if (DEBUG) {
                android.util.Slog.d(TAG, "Sending Ukey2 Client Finish message");
            }
            sendMessage(com.android.server.companion.securechannel.SecureChannel.MessageType.HANDSHAKE_FINISH, this.mHandshakeContext.getNextHandshakeMessage());
        }
        if (this.mHandshakeContext.isHandshakeComplete()) {
            if (DEBUG) {
                android.util.Slog.d(TAG, "Ukey2 Handshake completed successfully");
            }
            this.mConnectionContext = this.mHandshakeContext.toConnectionContext();
            return;
        }
        android.util.Slog.e(TAG, "Failed to complete Ukey2 Handshake");
        throw new java.lang.IllegalStateException("Ukey2 Handshake did not complete as expected.");
    }

    private void exchangeAuthentication() throws java.io.IOException, java.security.GeneralSecurityException, com.google.security.cryptauth.lib.securegcm.ukey2.BadHandleException, com.google.security.cryptauth.lib.securegcm.ukey2.CryptoException {
        if (this.mPreSharedKey != null) {
            exchangePreSharedKey();
        }
        if (this.mVerifier != null) {
            exchangeAttestation();
        }
    }

    private void exchangePreSharedKey() throws java.io.IOException, java.security.GeneralSecurityException, com.google.security.cryptauth.lib.securegcm.ukey2.BadHandleException, com.google.security.cryptauth.lib.securegcm.ukey2.CryptoException {
        com.google.security.cryptauth.lib.securegcm.ukey2.D2DHandshakeContext.Role role;
        if (DEBUG) {
            android.util.Slog.d(TAG, "Exchanging pre-shared keys.");
        }
        sendMessage(com.android.server.companion.securechannel.SecureChannel.MessageType.PRE_SHARED_KEY, constructToken(this.mRole, this.mPreSharedKey));
        byte[] readMessage = readMessage(com.android.server.companion.securechannel.SecureChannel.MessageType.PRE_SHARED_KEY);
        if (this.mRole == com.google.security.cryptauth.lib.securegcm.ukey2.D2DHandshakeContext.Role.INITIATOR) {
            role = com.google.security.cryptauth.lib.securegcm.ukey2.D2DHandshakeContext.Role.RESPONDER;
        } else {
            role = com.google.security.cryptauth.lib.securegcm.ukey2.D2DHandshakeContext.Role.INITIATOR;
        }
        this.mPskVerified = java.util.Arrays.equals(readMessage, constructToken(role, this.mPreSharedKey));
        if (!this.mPskVerified) {
            throw new com.android.server.companion.securechannel.SecureChannelException("Failed to verify the hash of pre-shared key.");
        }
        if (DEBUG) {
            android.util.Slog.d(TAG, "The pre-shared key was successfully authenticated.");
        }
    }

    private void exchangeAttestation() throws java.io.IOException, java.security.GeneralSecurityException, com.google.security.cryptauth.lib.securegcm.ukey2.BadHandleException, com.google.security.cryptauth.lib.securegcm.ukey2.CryptoException {
        com.google.security.cryptauth.lib.securegcm.ukey2.D2DHandshakeContext.Role role;
        if (this.mVerificationResult == 1) {
            android.util.Slog.d(TAG, "Remote attestation was already verified.");
            return;
        }
        if (DEBUG) {
            android.util.Slog.d(TAG, "Exchanging device attestation.");
        }
        if (this.mAlias == null) {
            this.mAlias = generateAlias();
        }
        com.android.server.companion.securechannel.KeyStoreUtils.generateAttestationKeyPair(this.mAlias, constructToken(this.mRole, this.mConnectionContext.getSessionUnique()));
        sendMessage(com.android.server.companion.securechannel.SecureChannel.MessageType.ATTESTATION, com.android.server.companion.securechannel.KeyStoreUtils.getEncodedCertificateChain(this.mAlias));
        byte[] readMessage = readMessage(com.android.server.companion.securechannel.SecureChannel.MessageType.ATTESTATION);
        if (this.mRole == com.google.security.cryptauth.lib.securegcm.ukey2.D2DHandshakeContext.Role.INITIATOR) {
            role = com.google.security.cryptauth.lib.securegcm.ukey2.D2DHandshakeContext.Role.RESPONDER;
        } else {
            role = com.google.security.cryptauth.lib.securegcm.ukey2.D2DHandshakeContext.Role.INITIATOR;
        }
        this.mVerificationResult = this.mVerifier.verifyAttestation(readMessage, constructToken(role, this.mConnectionContext.getSessionUnique()));
        sendMessage(com.android.server.companion.securechannel.SecureChannel.MessageType.AVF_RESULT, java.nio.ByteBuffer.allocate(4).putInt(this.mVerificationResult).array());
        if (java.nio.ByteBuffer.wrap(readMessage(com.android.server.companion.securechannel.SecureChannel.MessageType.AVF_RESULT)).getInt() != 1) {
            throw new com.android.server.companion.securechannel.SecureChannelException("Remote device failed to verify local attestation.");
        }
        if (this.mVerificationResult != 1) {
            throw new com.android.server.companion.securechannel.SecureChannelException("Failed to verify remote attestation.");
        }
        if (DEBUG) {
            android.util.Slog.d(TAG, "Remote attestation was successfully verified.");
        }
    }

    private boolean isSecured() {
        if (this.mConnectionContext == null) {
            return false;
        }
        return this.mPskVerified || this.mVerificationResult == 1;
    }

    private byte[] constructHandshakeInitMessage(byte[] bArr) {
        return java.nio.ByteBuffer.allocate(bArr.length + 1).put((byte) (!com.google.security.cryptauth.lib.securegcm.ukey2.D2DHandshakeContext.Role.INITIATOR.equals(this.mRole) ? 1 : 0)).put(bArr).array();
    }

    private byte[] constructToken(com.google.security.cryptauth.lib.securegcm.ukey2.D2DHandshakeContext.Role role, byte[] bArr) throws java.security.GeneralSecurityException {
        java.security.MessageDigest messageDigest = java.security.MessageDigest.getInstance("SHA-256");
        byte[] bytes = (role == com.google.security.cryptauth.lib.securegcm.ukey2.D2DHandshakeContext.Role.INITIATOR ? "Initiator" : "Responder").getBytes(java.nio.charset.StandardCharsets.UTF_8);
        return messageDigest.digest(java.nio.ByteBuffer.allocate(bytes.length + bArr.length).put(bytes).put(bArr).array());
    }

    private int compareByteArray(byte[] bArr, byte[] bArr2) {
        if (bArr == bArr2) {
            return 0;
        }
        if (bArr.length != bArr2.length) {
            return bArr.length - bArr2.length;
        }
        for (int i = 0; i < bArr.length; i++) {
            if (bArr[i] != bArr2[i]) {
                return bArr[i] - bArr2[i];
            }
        }
        return 0;
    }

    private java.lang.String generateAlias() {
        java.lang.String str;
        do {
            str = "secure-channel-" + java.util.UUID.randomUUID();
        } while (com.android.server.companion.securechannel.KeyStoreUtils.aliasExists(str));
        return str;
    }

    private enum MessageType {
        HANDSHAKE_INIT(18505),
        HANDSHAKE_FINISH(18502),
        PRE_SHARED_KEY(20555),
        ATTESTATION(16724),
        AVF_RESULT(22098),
        SECURE_MESSAGE(21325),
        UNKNOWN(0);

        private final short mValue;

        MessageType(int i) {
            this.mValue = (short) i;
        }

        static com.android.server.companion.securechannel.SecureChannel.MessageType from(short s) {
            for (com.android.server.companion.securechannel.SecureChannel.MessageType messageType : values()) {
                if (s == messageType.mValue) {
                    return messageType;
                }
            }
            return UNKNOWN;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static boolean shouldEncrypt(com.android.server.companion.securechannel.SecureChannel.MessageType messageType) {
            return (messageType == HANDSHAKE_INIT || messageType == HANDSHAKE_FINISH) ? false : true;
        }
    }
}
