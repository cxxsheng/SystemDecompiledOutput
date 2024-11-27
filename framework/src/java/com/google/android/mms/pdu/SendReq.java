package com.google.android.mms.pdu;

/* loaded from: classes5.dex */
public class SendReq extends com.google.android.mms.pdu.MultimediaMessagePdu {
    private static final java.lang.String TAG = "SendReq";

    public SendReq() {
        try {
            setMessageType(128);
            setMmsVersion(18);
            setContentType(com.google.android.mms.ContentType.MULTIPART_RELATED.getBytes());
            setFrom(new com.google.android.mms.pdu.EncodedStringValue(com.google.android.mms.pdu.PduHeaders.FROM_INSERT_ADDRESS_TOKEN_STR.getBytes()));
            setTransactionId(generateTransactionId());
        } catch (com.google.android.mms.InvalidHeaderValueException e) {
            android.util.Log.e(TAG, "Unexpected InvalidHeaderValueException.", e);
            throw new java.lang.RuntimeException(e);
        }
    }

    private byte[] generateTransactionId() {
        return ("T" + java.lang.Long.toHexString(java.lang.System.currentTimeMillis())).getBytes();
    }

    public SendReq(byte[] bArr, com.google.android.mms.pdu.EncodedStringValue encodedStringValue, int i, byte[] bArr2) throws com.google.android.mms.InvalidHeaderValueException {
        setMessageType(128);
        setContentType(bArr);
        setFrom(encodedStringValue);
        setMmsVersion(i);
        setTransactionId(bArr2);
    }

    SendReq(com.google.android.mms.pdu.PduHeaders pduHeaders) {
        super(pduHeaders);
    }

    SendReq(com.google.android.mms.pdu.PduHeaders pduHeaders, com.google.android.mms.pdu.PduBody pduBody) {
        super(pduHeaders, pduBody);
    }

    public com.google.android.mms.pdu.EncodedStringValue[] getBcc() {
        return this.mPduHeaders.getEncodedStringValues(129);
    }

    public void addBcc(com.google.android.mms.pdu.EncodedStringValue encodedStringValue) {
        this.mPduHeaders.appendEncodedStringValue(encodedStringValue, 129);
    }

    public void setBcc(com.google.android.mms.pdu.EncodedStringValue[] encodedStringValueArr) {
        this.mPduHeaders.setEncodedStringValues(encodedStringValueArr, 129);
    }

    public com.google.android.mms.pdu.EncodedStringValue[] getCc() {
        return this.mPduHeaders.getEncodedStringValues(130);
    }

    public void addCc(com.google.android.mms.pdu.EncodedStringValue encodedStringValue) {
        this.mPduHeaders.appendEncodedStringValue(encodedStringValue, 130);
    }

    public void setCc(com.google.android.mms.pdu.EncodedStringValue[] encodedStringValueArr) {
        this.mPduHeaders.setEncodedStringValues(encodedStringValueArr, 130);
    }

    public byte[] getContentType() {
        return this.mPduHeaders.getTextString(132);
    }

    public void setContentType(byte[] bArr) {
        this.mPduHeaders.setTextString(bArr, 132);
    }

    public int getDeliveryReport() {
        return this.mPduHeaders.getOctet(134);
    }

    public void setDeliveryReport(int i) throws com.google.android.mms.InvalidHeaderValueException {
        this.mPduHeaders.setOctet(i, 134);
    }

    public long getExpiry() {
        return this.mPduHeaders.getLongInteger(136);
    }

    public void setExpiry(long j) {
        this.mPduHeaders.setLongInteger(j, 136);
    }

    public long getMessageSize() {
        return this.mPduHeaders.getLongInteger(142);
    }

    public void setMessageSize(long j) {
        this.mPduHeaders.setLongInteger(j, 142);
    }

    public byte[] getMessageClass() {
        return this.mPduHeaders.getTextString(138);
    }

    public void setMessageClass(byte[] bArr) {
        this.mPduHeaders.setTextString(bArr, 138);
    }

    public int getReadReport() {
        return this.mPduHeaders.getOctet(144);
    }

    public void setReadReport(int i) throws com.google.android.mms.InvalidHeaderValueException {
        this.mPduHeaders.setOctet(i, 144);
    }

    public void setTo(com.google.android.mms.pdu.EncodedStringValue[] encodedStringValueArr) {
        this.mPduHeaders.setEncodedStringValues(encodedStringValueArr, 151);
    }

    public byte[] getTransactionId() {
        return this.mPduHeaders.getTextString(152);
    }

    public void setTransactionId(byte[] bArr) {
        this.mPduHeaders.setTextString(bArr, 152);
    }
}
