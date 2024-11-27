package com.google.android.mms.pdu;

/* loaded from: classes5.dex */
public class ReadRecInd extends com.google.android.mms.pdu.GenericPdu {
    public ReadRecInd(com.google.android.mms.pdu.EncodedStringValue encodedStringValue, byte[] bArr, int i, int i2, com.google.android.mms.pdu.EncodedStringValue[] encodedStringValueArr) throws com.google.android.mms.InvalidHeaderValueException {
        setMessageType(135);
        setFrom(encodedStringValue);
        setMessageId(bArr);
        setMmsVersion(i);
        setTo(encodedStringValueArr);
        setReadStatus(i2);
    }

    ReadRecInd(com.google.android.mms.pdu.PduHeaders pduHeaders) {
        super(pduHeaders);
    }

    public long getDate() {
        return this.mPduHeaders.getLongInteger(133);
    }

    public void setDate(long j) {
        this.mPduHeaders.setLongInteger(j, 133);
    }

    public byte[] getMessageId() {
        return this.mPduHeaders.getTextString(139);
    }

    public void setMessageId(byte[] bArr) {
        this.mPduHeaders.setTextString(bArr, 139);
    }

    public com.google.android.mms.pdu.EncodedStringValue[] getTo() {
        return this.mPduHeaders.getEncodedStringValues(151);
    }

    public void setTo(com.google.android.mms.pdu.EncodedStringValue[] encodedStringValueArr) {
        this.mPduHeaders.setEncodedStringValues(encodedStringValueArr, 151);
    }

    public int getReadStatus() {
        return this.mPduHeaders.getOctet(155);
    }

    public void setReadStatus(int i) throws com.google.android.mms.InvalidHeaderValueException {
        this.mPduHeaders.setOctet(i, 155);
    }
}
