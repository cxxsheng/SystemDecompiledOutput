package com.android.internal.org.bouncycastle.operator.bc;

/* loaded from: classes4.dex */
public class BcDefaultDigestProvider implements com.android.internal.org.bouncycastle.operator.bc.BcDigestProvider {
    private static final java.util.Map lookup = createTable();
    public static final com.android.internal.org.bouncycastle.operator.bc.BcDigestProvider INSTANCE = new com.android.internal.org.bouncycastle.operator.bc.BcDefaultDigestProvider();

    private static java.util.Map createTable() {
        java.util.HashMap hashMap = new java.util.HashMap();
        hashMap.put(com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.idSHA1, new com.android.internal.org.bouncycastle.operator.bc.BcDigestProvider() { // from class: com.android.internal.org.bouncycastle.operator.bc.BcDefaultDigestProvider.1
            @Override // com.android.internal.org.bouncycastle.operator.bc.BcDigestProvider
            public com.android.internal.org.bouncycastle.crypto.ExtendedDigest get(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier) {
                return new com.android.internal.org.bouncycastle.crypto.digests.SHA1Digest();
            }
        });
        hashMap.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha224, new com.android.internal.org.bouncycastle.operator.bc.BcDigestProvider() { // from class: com.android.internal.org.bouncycastle.operator.bc.BcDefaultDigestProvider.2
            @Override // com.android.internal.org.bouncycastle.operator.bc.BcDigestProvider
            public com.android.internal.org.bouncycastle.crypto.ExtendedDigest get(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier) {
                return new com.android.internal.org.bouncycastle.crypto.digests.SHA224Digest();
            }
        });
        hashMap.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha256, new com.android.internal.org.bouncycastle.operator.bc.BcDigestProvider() { // from class: com.android.internal.org.bouncycastle.operator.bc.BcDefaultDigestProvider.3
            @Override // com.android.internal.org.bouncycastle.operator.bc.BcDigestProvider
            public com.android.internal.org.bouncycastle.crypto.ExtendedDigest get(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier) {
                return new com.android.internal.org.bouncycastle.crypto.digests.SHA256Digest();
            }
        });
        hashMap.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha384, new com.android.internal.org.bouncycastle.operator.bc.BcDigestProvider() { // from class: com.android.internal.org.bouncycastle.operator.bc.BcDefaultDigestProvider.4
            @Override // com.android.internal.org.bouncycastle.operator.bc.BcDigestProvider
            public com.android.internal.org.bouncycastle.crypto.ExtendedDigest get(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier) {
                return new com.android.internal.org.bouncycastle.crypto.digests.SHA384Digest();
            }
        });
        hashMap.put(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha512, new com.android.internal.org.bouncycastle.operator.bc.BcDigestProvider() { // from class: com.android.internal.org.bouncycastle.operator.bc.BcDefaultDigestProvider.5
            @Override // com.android.internal.org.bouncycastle.operator.bc.BcDigestProvider
            public com.android.internal.org.bouncycastle.crypto.ExtendedDigest get(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier) {
                return new com.android.internal.org.bouncycastle.crypto.digests.SHA512Digest();
            }
        });
        return java.util.Collections.unmodifiableMap(hashMap);
    }

    private BcDefaultDigestProvider() {
    }

    @Override // com.android.internal.org.bouncycastle.operator.bc.BcDigestProvider
    public com.android.internal.org.bouncycastle.crypto.ExtendedDigest get(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException {
        com.android.internal.org.bouncycastle.operator.bc.BcDigestProvider bcDigestProvider = (com.android.internal.org.bouncycastle.operator.bc.BcDigestProvider) lookup.get(algorithmIdentifier.getAlgorithm());
        if (bcDigestProvider == null) {
            throw new com.android.internal.org.bouncycastle.operator.OperatorCreationException("cannot recognise digest");
        }
        return bcDigestProvider.get(algorithmIdentifier);
    }
}
