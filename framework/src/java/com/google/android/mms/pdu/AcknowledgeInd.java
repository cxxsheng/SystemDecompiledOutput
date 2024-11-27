package com.google.android.mms.pdu;

/* loaded from: classes5.dex */
public class AcknowledgeInd extends com.google.android.mms.pdu.GenericPdu {
    public AcknowledgeInd(int i, byte[] bArr) throws com.google.android.mms.InvalidHeaderValueException {
        setMessageType(133);
        setMmsVersion(i);
        setTransactionId(bArr);
    }

    AcknowledgeInd(com.google.android.mms.pdu.PduHeaders pduHeaders) {
        super(pduHeaders);
    }

    public int getReportAllowed() {
        return this.mPduHeaders.getOctet(145);
    }

    public void setReportAllowed(int i) throws com.google.android.mms.InvalidHeaderValueException {
        this.mPduHeaders.setOctet(i, 145);
    }

    public byte[] getTransactionId() {
        return this.mPduHeaders.getTextString(152);
    }

    public void setTransactionId(byte[] bArr) {
        this.mPduHeaders.setTextString(bArr, 152);
    }
}
