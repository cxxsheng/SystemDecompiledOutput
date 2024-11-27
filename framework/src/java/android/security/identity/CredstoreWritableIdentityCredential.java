package android.security.identity;

/* loaded from: classes3.dex */
class CredstoreWritableIdentityCredential extends android.security.identity.WritableIdentityCredential {
    private static final java.lang.String TAG = "CredstoreWritableIdentityCredential";
    private android.security.identity.IWritableCredential mBinder;
    private android.content.Context mContext;
    private java.lang.String mCredentialName;
    private java.lang.String mDocType;

    CredstoreWritableIdentityCredential(android.content.Context context, java.lang.String str, java.lang.String str2, android.security.identity.IWritableCredential iWritableCredential) {
        this.mContext = context;
        this.mDocType = str2;
        this.mCredentialName = str;
        this.mBinder = iWritableCredential;
    }

    @Override // android.security.identity.WritableIdentityCredential
    public java.util.Collection<java.security.cert.X509Certificate> getCredentialKeyCertificateChain(byte[] bArr) {
        try {
            try {
                java.util.Collection<? extends java.security.cert.Certificate> generateCertificates = java.security.cert.CertificateFactory.getInstance("X.509").generateCertificates(new java.io.ByteArrayInputStream(this.mBinder.getCredentialKeyCertificateChain(bArr)));
                java.util.ArrayList arrayList = new java.util.ArrayList();
                java.util.Iterator<? extends java.security.cert.Certificate> it = generateCertificates.iterator();
                while (it.hasNext()) {
                    arrayList.add((java.security.cert.X509Certificate) it.next());
                }
                return arrayList;
            } catch (java.security.cert.CertificateException e) {
                throw new java.lang.RuntimeException("Error decoding certificates", e);
            }
        } catch (android.os.RemoteException e2) {
            throw new java.lang.RuntimeException("Unexpected RemoteException ", e2);
        } catch (android.os.ServiceSpecificException e3) {
            throw new java.lang.RuntimeException("Unexpected ServiceSpecificException with code " + e3.errorCode, e3);
        }
    }

    @Override // android.security.identity.WritableIdentityCredential
    public byte[] personalize(android.security.identity.PersonalizationData personalizationData) {
        return personalize(this.mBinder, personalizationData);
    }

    static byte[] personalize(android.security.identity.IWritableCredential iWritableCredential, android.security.identity.PersonalizationData personalizationData) {
        long j;
        java.util.Collection<android.security.identity.AccessControlProfile> accessControlProfiles = personalizationData.getAccessControlProfiles();
        android.security.identity.AccessControlProfileParcel[] accessControlProfileParcelArr = new android.security.identity.AccessControlProfileParcel[accessControlProfiles.size()];
        int i = 0;
        boolean z = false;
        for (android.security.identity.AccessControlProfile accessControlProfile : accessControlProfiles) {
            accessControlProfileParcelArr[i] = new android.security.identity.AccessControlProfileParcel();
            accessControlProfileParcelArr[i].id = accessControlProfile.getAccessControlProfileId().getId();
            java.security.cert.X509Certificate readerCertificate = accessControlProfile.getReaderCertificate();
            if (readerCertificate != null) {
                try {
                    accessControlProfileParcelArr[i].readerCertificate = readerCertificate.getEncoded();
                } catch (java.security.cert.CertificateException e) {
                    throw new java.lang.RuntimeException("Error encoding reader certificate", e);
                }
            } else {
                accessControlProfileParcelArr[i].readerCertificate = new byte[0];
            }
            accessControlProfileParcelArr[i].userAuthenticationRequired = accessControlProfile.isUserAuthenticationRequired();
            accessControlProfileParcelArr[i].userAuthenticationTimeoutMillis = accessControlProfile.getUserAuthenticationTimeout();
            if (accessControlProfile.isUserAuthenticationRequired()) {
                z = true;
            }
            i++;
        }
        java.util.Collection<java.lang.String> namespaces = personalizationData.getNamespaces();
        android.security.identity.EntryNamespaceParcel[] entryNamespaceParcelArr = new android.security.identity.EntryNamespaceParcel[namespaces.size()];
        int i2 = 0;
        for (java.lang.String str : namespaces) {
            android.security.identity.PersonalizationData.NamespaceData namespaceData = personalizationData.getNamespaceData(str);
            entryNamespaceParcelArr[i2] = new android.security.identity.EntryNamespaceParcel();
            entryNamespaceParcelArr[i2].namespaceName = str;
            java.util.Collection<java.lang.String> entryNames = namespaceData.getEntryNames();
            android.security.identity.EntryParcel[] entryParcelArr = new android.security.identity.EntryParcel[entryNames.size()];
            int i3 = 0;
            for (java.lang.String str2 : entryNames) {
                entryParcelArr[i3] = new android.security.identity.EntryParcel();
                entryParcelArr[i3].name = str2;
                entryParcelArr[i3].value = namespaceData.getEntryValue(str2);
                java.util.Collection<android.security.identity.AccessControlProfileId> accessControlProfileIds = namespaceData.getAccessControlProfileIds(str2);
                entryParcelArr[i3].accessControlProfileIds = new int[accessControlProfileIds.size()];
                java.util.Iterator<android.security.identity.AccessControlProfileId> it = accessControlProfileIds.iterator();
                int i4 = 0;
                while (it.hasNext()) {
                    entryParcelArr[i3].accessControlProfileIds[i4] = it.next().getId();
                    i4++;
                }
                i3++;
            }
            entryNamespaceParcelArr[i2].entries = entryParcelArr;
            i2++;
        }
        if (!z) {
            j = 0;
        } else {
            j = getRootSid();
        }
        try {
            return iWritableCredential.personalize(accessControlProfileParcelArr, entryNamespaceParcelArr, j);
        } catch (android.os.RemoteException e2) {
            throw new java.lang.RuntimeException("Unexpected RemoteException ", e2);
        } catch (android.os.ServiceSpecificException e3) {
            throw new java.lang.RuntimeException("Unexpected ServiceSpecificException with code " + e3.errorCode, e3);
        }
    }

    private static long getRootSid() {
        long secureUserId = android.security.GateKeeper.getSecureUserId();
        if (secureUserId == 0) {
            throw new java.lang.IllegalStateException("Secure lock screen must be enabled to create credentials requiring user authentication");
        }
        return secureUserId;
    }
}
