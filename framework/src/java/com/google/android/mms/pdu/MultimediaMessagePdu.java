package com.google.android.mms.pdu;

/* loaded from: classes5.dex */
public class MultimediaMessagePdu extends com.google.android.mms.pdu.GenericPdu {
    private com.google.android.mms.pdu.PduBody mMessageBody;

    public MultimediaMessagePdu() {
    }

    public MultimediaMessagePdu(com.google.android.mms.pdu.PduHeaders pduHeaders, com.google.android.mms.pdu.PduBody pduBody) {
        super(pduHeaders);
        this.mMessageBody = pduBody;
    }

    MultimediaMessagePdu(com.google.android.mms.pdu.PduHeaders pduHeaders) {
        super(pduHeaders);
    }

    public com.google.android.mms.pdu.PduBody getBody() {
        return this.mMessageBody;
    }

    public void setBody(com.google.android.mms.pdu.PduBody pduBody) {
        this.mMessageBody = pduBody;
    }

    public com.google.android.mms.pdu.EncodedStringValue getSubject() {
        return this.mPduHeaders.getEncodedStringValue(150);
    }

    public void setSubject(com.google.android.mms.pdu.EncodedStringValue encodedStringValue) {
        this.mPduHeaders.setEncodedStringValue(encodedStringValue, 150);
    }

    public com.google.android.mms.pdu.EncodedStringValue[] getTo() {
        return this.mPduHeaders.getEncodedStringValues(151);
    }

    public void addTo(com.google.android.mms.pdu.EncodedStringValue encodedStringValue) {
        this.mPduHeaders.appendEncodedStringValue(encodedStringValue, 151);
    }

    public int getPriority() {
        return this.mPduHeaders.getOctet(143);
    }

    public void setPriority(int i) throws com.google.android.mms.InvalidHeaderValueException {
        this.mPduHeaders.setOctet(i, 143);
    }

    public long getDate() {
        return this.mPduHeaders.getLongInteger(133);
    }

    public void setDate(long j) {
        this.mPduHeaders.setLongInteger(j, 133);
    }
}
