package com.google.android.mms.pdu;

/* loaded from: classes5.dex */
public class RetrieveConf extends com.google.android.mms.pdu.MultimediaMessagePdu {
    public RetrieveConf() throws com.google.android.mms.InvalidHeaderValueException {
        setMessageType(132);
    }

    RetrieveConf(com.google.android.mms.pdu.PduHeaders pduHeaders) {
        super(pduHeaders);
    }

    RetrieveConf(com.google.android.mms.pdu.PduHeaders pduHeaders, com.google.android.mms.pdu.PduBody pduBody) {
        super(pduHeaders, pduBody);
    }

    public com.google.android.mms.pdu.EncodedStringValue[] getCc() {
        return this.mPduHeaders.getEncodedStringValues(130);
    }

    public void addCc(com.google.android.mms.pdu.EncodedStringValue encodedStringValue) {
        this.mPduHeaders.appendEncodedStringValue(encodedStringValue, 130);
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

    @Override // com.google.android.mms.pdu.GenericPdu
    public com.google.android.mms.pdu.EncodedStringValue getFrom() {
        return this.mPduHeaders.getEncodedStringValue(137);
    }

    @Override // com.google.android.mms.pdu.GenericPdu
    public void setFrom(com.google.android.mms.pdu.EncodedStringValue encodedStringValue) {
        this.mPduHeaders.setEncodedStringValue(encodedStringValue, 137);
    }

    public byte[] getMessageClass() {
        return this.mPduHeaders.getTextString(138);
    }

    public void setMessageClass(byte[] bArr) {
        this.mPduHeaders.setTextString(bArr, 138);
    }

    public byte[] getMessageId() {
        return this.mPduHeaders.getTextString(139);
    }

    public void setMessageId(byte[] bArr) {
        this.mPduHeaders.setTextString(bArr, 139);
    }

    public int getReadReport() {
        return this.mPduHeaders.getOctet(144);
    }

    public void setReadReport(int i) throws com.google.android.mms.InvalidHeaderValueException {
        this.mPduHeaders.setOctet(i, 144);
    }

    public int getRetrieveStatus() {
        return this.mPduHeaders.getOctet(153);
    }

    public void setRetrieveStatus(int i) throws com.google.android.mms.InvalidHeaderValueException {
        this.mPduHeaders.setOctet(i, 153);
    }

    public com.google.android.mms.pdu.EncodedStringValue getRetrieveText() {
        return this.mPduHeaders.getEncodedStringValue(154);
    }

    public void setRetrieveText(com.google.android.mms.pdu.EncodedStringValue encodedStringValue) {
        this.mPduHeaders.setEncodedStringValue(encodedStringValue, 154);
    }

    public byte[] getTransactionId() {
        return this.mPduHeaders.getTextString(152);
    }

    public void setTransactionId(byte[] bArr) {
        this.mPduHeaders.setTextString(bArr, 152);
    }
}
