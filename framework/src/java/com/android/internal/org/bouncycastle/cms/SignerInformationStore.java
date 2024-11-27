package com.android.internal.org.bouncycastle.cms;

/* loaded from: classes4.dex */
public class SignerInformationStore implements com.android.internal.org.bouncycastle.util.Iterable<com.android.internal.org.bouncycastle.cms.SignerInformation> {
    private java.util.List all;
    private java.util.Map table = new java.util.HashMap();

    public SignerInformationStore(com.android.internal.org.bouncycastle.cms.SignerInformation signerInformation) {
        this.all = new java.util.ArrayList();
        this.all = new java.util.ArrayList(1);
        this.all.add(signerInformation);
        this.table.put(signerInformation.getSID(), this.all);
    }

    public SignerInformationStore(java.util.Collection<com.android.internal.org.bouncycastle.cms.SignerInformation> collection) {
        this.all = new java.util.ArrayList();
        for (com.android.internal.org.bouncycastle.cms.SignerInformation signerInformation : collection) {
            com.android.internal.org.bouncycastle.cms.SignerId sid = signerInformation.getSID();
            java.util.ArrayList arrayList = (java.util.ArrayList) this.table.get(sid);
            if (arrayList == null) {
                arrayList = new java.util.ArrayList(1);
                this.table.put(sid, arrayList);
            }
            arrayList.add(signerInformation);
        }
        this.all = new java.util.ArrayList(collection);
    }

    public com.android.internal.org.bouncycastle.cms.SignerInformation get(com.android.internal.org.bouncycastle.cms.SignerId signerId) {
        java.util.Collection<com.android.internal.org.bouncycastle.cms.SignerInformation> signers = getSigners(signerId);
        if (signers.size() == 0) {
            return null;
        }
        return signers.iterator().next();
    }

    public int size() {
        return this.all.size();
    }

    public java.util.Collection<com.android.internal.org.bouncycastle.cms.SignerInformation> getSigners() {
        return new java.util.ArrayList(this.all);
    }

    public java.util.Collection<com.android.internal.org.bouncycastle.cms.SignerInformation> getSigners(com.android.internal.org.bouncycastle.cms.SignerId signerId) {
        if (signerId.getIssuer() != null && signerId.getSubjectKeyIdentifier() != null) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            java.util.Collection<com.android.internal.org.bouncycastle.cms.SignerInformation> signers = getSigners(new com.android.internal.org.bouncycastle.cms.SignerId(signerId.getIssuer(), signerId.getSerialNumber()));
            if (signers != null) {
                arrayList.addAll(signers);
            }
            java.util.Collection<com.android.internal.org.bouncycastle.cms.SignerInformation> signers2 = getSigners(new com.android.internal.org.bouncycastle.cms.SignerId(signerId.getSubjectKeyIdentifier()));
            if (signers2 != null) {
                arrayList.addAll(signers2);
            }
            return arrayList;
        }
        java.util.ArrayList arrayList2 = (java.util.ArrayList) this.table.get(signerId);
        return arrayList2 == null ? new java.util.ArrayList() : new java.util.ArrayList(arrayList2);
    }

    @Override // com.android.internal.org.bouncycastle.util.Iterable, java.lang.Iterable
    public java.util.Iterator<com.android.internal.org.bouncycastle.cms.SignerInformation> iterator() {
        return getSigners().iterator();
    }
}
