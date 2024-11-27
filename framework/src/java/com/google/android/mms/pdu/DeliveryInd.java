package com.google.android.mms.pdu;

/* loaded from: classes5.dex */
public class DeliveryInd extends com.google.android.mms.pdu.GenericPdu {
    public DeliveryInd() throws com.google.android.mms.InvalidHeaderValueException {
        setMessageType(134);
    }

    DeliveryInd(com.google.android.mms.pdu.PduHeaders pduHeaders) {
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

    public int getStatus() {
        return this.mPduHeaders.getOctet(149);
    }

    public void setStatus(int i) throws com.google.android.mms.InvalidHeaderValueException {
        this.mPduHeaders.setOctet(i, 149);
    }

    public com.google.android.mms.pdu.EncodedStringValue[] getTo() {
        return this.mPduHeaders.getEncodedStringValues(151);
    }

    public void setTo(com.google.android.mms.pdu.EncodedStringValue[] encodedStringValueArr) {
        this.mPduHeaders.setEncodedStringValues(encodedStringValueArr, 151);
    }
}
