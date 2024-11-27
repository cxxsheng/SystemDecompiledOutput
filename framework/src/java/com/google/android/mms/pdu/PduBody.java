package com.google.android.mms.pdu;

/* loaded from: classes5.dex */
public class PduBody {
    private java.util.Map<java.lang.String, com.google.android.mms.pdu.PduPart> mPartMapByContentId;
    private java.util.Map<java.lang.String, com.google.android.mms.pdu.PduPart> mPartMapByContentLocation;
    private java.util.Map<java.lang.String, com.google.android.mms.pdu.PduPart> mPartMapByFileName;
    private java.util.Map<java.lang.String, com.google.android.mms.pdu.PduPart> mPartMapByName;
    private java.util.Vector<com.google.android.mms.pdu.PduPart> mParts;

    public PduBody() {
        this.mParts = null;
        this.mPartMapByContentId = null;
        this.mPartMapByContentLocation = null;
        this.mPartMapByName = null;
        this.mPartMapByFileName = null;
        this.mParts = new java.util.Vector<>();
        this.mPartMapByContentId = new java.util.HashMap();
        this.mPartMapByContentLocation = new java.util.HashMap();
        this.mPartMapByName = new java.util.HashMap();
        this.mPartMapByFileName = new java.util.HashMap();
    }

    private void putPartToMaps(com.google.android.mms.pdu.PduPart pduPart) {
        byte[] contentId = pduPart.getContentId();
        if (contentId != null) {
            this.mPartMapByContentId.put(new java.lang.String(contentId), pduPart);
        }
        byte[] contentLocation = pduPart.getContentLocation();
        if (contentLocation != null) {
            this.mPartMapByContentLocation.put(new java.lang.String(contentLocation), pduPart);
        }
        byte[] name = pduPart.getName();
        if (name != null) {
            this.mPartMapByName.put(new java.lang.String(name), pduPart);
        }
        byte[] filename = pduPart.getFilename();
        if (filename != null) {
            this.mPartMapByFileName.put(new java.lang.String(filename), pduPart);
        }
    }

    public boolean addPart(com.google.android.mms.pdu.PduPart pduPart) {
        if (pduPart == null) {
            throw new java.lang.NullPointerException();
        }
        putPartToMaps(pduPart);
        return this.mParts.add(pduPart);
    }

    public void addPart(int i, com.google.android.mms.pdu.PduPart pduPart) {
        if (pduPart == null) {
            throw new java.lang.NullPointerException();
        }
        putPartToMaps(pduPart);
        this.mParts.add(i, pduPart);
    }

    public com.google.android.mms.pdu.PduPart removePart(int i) {
        return this.mParts.remove(i);
    }

    public void removeAll() {
        this.mParts.clear();
    }

    public com.google.android.mms.pdu.PduPart getPart(int i) {
        return this.mParts.get(i);
    }

    public int getPartIndex(com.google.android.mms.pdu.PduPart pduPart) {
        return this.mParts.indexOf(pduPart);
    }

    public int getPartsNum() {
        return this.mParts.size();
    }

    public com.google.android.mms.pdu.PduPart getPartByContentId(java.lang.String str) {
        return this.mPartMapByContentId.get(str);
    }

    public com.google.android.mms.pdu.PduPart getPartByContentLocation(java.lang.String str) {
        return this.mPartMapByContentLocation.get(str);
    }

    public com.google.android.mms.pdu.PduPart getPartByName(java.lang.String str) {
        return this.mPartMapByName.get(str);
    }

    public com.google.android.mms.pdu.PduPart getPartByFileName(java.lang.String str) {
        return this.mPartMapByFileName.get(str);
    }
}
