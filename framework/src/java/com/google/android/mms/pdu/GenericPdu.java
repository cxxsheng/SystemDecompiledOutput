package com.google.android.mms.pdu;

/* loaded from: classes5.dex */
public class GenericPdu {
    com.google.android.mms.pdu.PduHeaders mPduHeaders;

    public GenericPdu() {
        this.mPduHeaders = null;
        this.mPduHeaders = new com.google.android.mms.pdu.PduHeaders();
    }

    GenericPdu(com.google.android.mms.pdu.PduHeaders pduHeaders) {
        this.mPduHeaders = null;
        this.mPduHeaders = pduHeaders;
    }

    com.google.android.mms.pdu.PduHeaders getPduHeaders() {
        return this.mPduHeaders;
    }

    public int getMessageType() {
        return this.mPduHeaders.getOctet(140);
    }

    public void setMessageType(int i) throws com.google.android.mms.InvalidHeaderValueException {
        this.mPduHeaders.setOctet(i, 140);
    }

    public int getMmsVersion() {
        return this.mPduHeaders.getOctet(141);
    }

    public void setMmsVersion(int i) throws com.google.android.mms.InvalidHeaderValueException {
        this.mPduHeaders.setOctet(i, 141);
    }

    public com.google.android.mms.pdu.EncodedStringValue getFrom() {
        return this.mPduHeaders.getEncodedStringValue(137);
    }

    public void setFrom(com.google.android.mms.pdu.EncodedStringValue encodedStringValue) {
        this.mPduHeaders.setEncodedStringValue(encodedStringValue, 137);
    }
}
