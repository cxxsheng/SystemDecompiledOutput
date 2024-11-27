package com.google.android.mms.pdu;

/* loaded from: classes5.dex */
public class ReadOrigInd extends com.google.android.mms.pdu.GenericPdu {
    public ReadOrigInd() throws com.google.android.mms.InvalidHeaderValueException {
        setMessageType(136);
    }

    ReadOrigInd(com.google.android.mms.pdu.PduHeaders pduHeaders) {
        super(pduHeaders);
    }

    public long getDate() {
        return this.mPduHeaders.getLongInteger(133);
    }

    public void setDate(long j) {
        this.mPduHeaders.setLongInteger(j, 133);
    }

    @Override // com.google.android.mms.pdu.GenericPdu
    public com.google.android.mms.pdu.EncodedStringValue getFrom() {
        return this.mPduHeaders.getEncodedStringValue(137);
    }

    @Override // com.google.android.mms.pdu.GenericPdu
    public void setFrom(com.google.android.mms.pdu.EncodedStringValue encodedStringValue) {
        this.mPduHeaders.setEncodedStringValue(encodedStringValue, 137);
    }

    public byte[] getMessageId() {
        return this.mPduHeaders.getTextString(139);
    }

    public void setMessageId(byte[] bArr) {
        this.mPduHeaders.setTextString(bArr, 139);
    }

    public int getReadStatus() {
        return this.mPduHeaders.getOctet(155);
    }

    public void setReadStatus(int i) throws com.google.android.mms.InvalidHeaderValueException {
        this.mPduHeaders.setOctet(i, 155);
    }

    public com.google.android.mms.pdu.EncodedStringValue[] getTo() {
        return this.mPduHeaders.getEncodedStringValues(151);
    }

    public void setTo(com.google.android.mms.pdu.EncodedStringValue[] encodedStringValueArr) {
        this.mPduHeaders.setEncodedStringValues(encodedStringValueArr, 151);
    }
}
