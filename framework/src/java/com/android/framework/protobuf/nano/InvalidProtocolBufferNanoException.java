package com.android.framework.protobuf.nano;

/* loaded from: classes4.dex */
public class InvalidProtocolBufferNanoException extends java.io.IOException {
    private static final long serialVersionUID = -1616151763072450476L;

    public InvalidProtocolBufferNanoException(java.lang.String str) {
        super(str);
    }

    static com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException truncatedMessage() {
        return new com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either that the input has been truncated or that an embedded message misreported its own length.");
    }

    static com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException negativeSize() {
        return new com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
    }

    static com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException malformedVarint() {
        return new com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException("CodedInputStream encountered a malformed varint.");
    }

    static com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException invalidTag() {
        return new com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException("Protocol message contained an invalid tag (zero).");
    }

    static com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException invalidEndTag() {
        return new com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException("Protocol message end-group tag did not match expected tag.");
    }

    static com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException invalidWireType() {
        return new com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException("Protocol message tag had invalid wire type.");
    }

    static com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException recursionLimitExceeded() {
        return new com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException("Protocol message had too many levels of nesting.  May be malicious.  Use CodedInputStream.setRecursionLimit() to increase the depth limit.");
    }

    static com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException sizeLimitExceeded() {
        return new com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException("Protocol message was too large.  May be malicious.  Use CodedInputStream.setSizeLimit() to increase the size limit.");
    }
}
