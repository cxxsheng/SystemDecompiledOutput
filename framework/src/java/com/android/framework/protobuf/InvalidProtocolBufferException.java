package com.android.framework.protobuf;

/* loaded from: classes4.dex */
public class InvalidProtocolBufferException extends java.io.IOException {
    private static final long serialVersionUID = -1616151763072450476L;
    private com.android.framework.protobuf.MessageLite unfinishedMessage;
    private boolean wasThrownFromInputStream;

    public InvalidProtocolBufferException(java.lang.String str) {
        super(str);
        this.unfinishedMessage = null;
    }

    public InvalidProtocolBufferException(java.lang.Exception exc) {
        super(exc.getMessage(), exc);
        this.unfinishedMessage = null;
    }

    public InvalidProtocolBufferException(java.lang.String str, java.lang.Exception exc) {
        super(str, exc);
        this.unfinishedMessage = null;
    }

    public InvalidProtocolBufferException(java.io.IOException iOException) {
        super(iOException.getMessage(), iOException);
        this.unfinishedMessage = null;
    }

    public InvalidProtocolBufferException(java.lang.String str, java.io.IOException iOException) {
        super(str, iOException);
        this.unfinishedMessage = null;
    }

    public com.android.framework.protobuf.InvalidProtocolBufferException setUnfinishedMessage(com.android.framework.protobuf.MessageLite messageLite) {
        this.unfinishedMessage = messageLite;
        return this;
    }

    public com.android.framework.protobuf.MessageLite getUnfinishedMessage() {
        return this.unfinishedMessage;
    }

    void setThrownFromInputStream() {
        this.wasThrownFromInputStream = true;
    }

    boolean getThrownFromInputStream() {
        return this.wasThrownFromInputStream;
    }

    public java.io.IOException unwrapIOException() {
        return getCause() instanceof java.io.IOException ? (java.io.IOException) getCause() : this;
    }

    static com.android.framework.protobuf.InvalidProtocolBufferException truncatedMessage() {
        return new com.android.framework.protobuf.InvalidProtocolBufferException("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either that the input has been truncated or that an embedded message misreported its own length.");
    }

    static com.android.framework.protobuf.InvalidProtocolBufferException negativeSize() {
        return new com.android.framework.protobuf.InvalidProtocolBufferException("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
    }

    static com.android.framework.protobuf.InvalidProtocolBufferException malformedVarint() {
        return new com.android.framework.protobuf.InvalidProtocolBufferException("CodedInputStream encountered a malformed varint.");
    }

    static com.android.framework.protobuf.InvalidProtocolBufferException invalidTag() {
        return new com.android.framework.protobuf.InvalidProtocolBufferException("Protocol message contained an invalid tag (zero).");
    }

    static com.android.framework.protobuf.InvalidProtocolBufferException invalidEndTag() {
        return new com.android.framework.protobuf.InvalidProtocolBufferException("Protocol message end-group tag did not match expected tag.");
    }

    static com.android.framework.protobuf.InvalidProtocolBufferException.InvalidWireTypeException invalidWireType() {
        return new com.android.framework.protobuf.InvalidProtocolBufferException.InvalidWireTypeException("Protocol message tag had invalid wire type.");
    }

    public static class InvalidWireTypeException extends com.android.framework.protobuf.InvalidProtocolBufferException {
        private static final long serialVersionUID = 3283890091615336259L;

        public InvalidWireTypeException(java.lang.String str) {
            super(str);
        }
    }

    static com.android.framework.protobuf.InvalidProtocolBufferException recursionLimitExceeded() {
        return new com.android.framework.protobuf.InvalidProtocolBufferException("Protocol message had too many levels of nesting.  May be malicious.  Use CodedInputStream.setRecursionLimit() to increase the depth limit.");
    }

    static com.android.framework.protobuf.InvalidProtocolBufferException sizeLimitExceeded() {
        return new com.android.framework.protobuf.InvalidProtocolBufferException("Protocol message was too large.  May be malicious.  Use CodedInputStream.setSizeLimit() to increase the size limit.");
    }

    static com.android.framework.protobuf.InvalidProtocolBufferException parseFailure() {
        return new com.android.framework.protobuf.InvalidProtocolBufferException("Failed to parse the message.");
    }

    static com.android.framework.protobuf.InvalidProtocolBufferException invalidUtf8() {
        return new com.android.framework.protobuf.InvalidProtocolBufferException("Protocol message had invalid UTF-8.");
    }
}
