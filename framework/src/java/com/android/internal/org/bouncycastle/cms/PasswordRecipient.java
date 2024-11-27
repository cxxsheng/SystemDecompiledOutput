package com.android.internal.org.bouncycastle.cms;

/* loaded from: classes4.dex */
public interface PasswordRecipient extends com.android.internal.org.bouncycastle.cms.Recipient {
    public static final int PKCS5_SCHEME2 = 0;
    public static final int PKCS5_SCHEME2_UTF8 = 1;

    byte[] calculateDerivedKey(int i, com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier, int i2) throws com.android.internal.org.bouncycastle.cms.CMSException;

    char[] getPassword();

    int getPasswordConversionScheme();

    com.android.internal.org.bouncycastle.cms.RecipientOperator getRecipientOperator(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier, com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier2, byte[] bArr, byte[] bArr2) throws com.android.internal.org.bouncycastle.cms.CMSException;

    public static final class PRF {
        public static final com.android.internal.org.bouncycastle.cms.PasswordRecipient.PRF HMacSHA1 = new com.android.internal.org.bouncycastle.cms.PasswordRecipient.PRF("HMacSHA1", new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_hmacWithSHA1, com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE));
        public static final com.android.internal.org.bouncycastle.cms.PasswordRecipient.PRF HMacSHA224 = new com.android.internal.org.bouncycastle.cms.PasswordRecipient.PRF("HMacSHA224", new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_hmacWithSHA224, com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE));
        public static final com.android.internal.org.bouncycastle.cms.PasswordRecipient.PRF HMacSHA256 = new com.android.internal.org.bouncycastle.cms.PasswordRecipient.PRF("HMacSHA256", new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_hmacWithSHA256, com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE));
        public static final com.android.internal.org.bouncycastle.cms.PasswordRecipient.PRF HMacSHA384 = new com.android.internal.org.bouncycastle.cms.PasswordRecipient.PRF("HMacSHA384", new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_hmacWithSHA384, com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE));
        public static final com.android.internal.org.bouncycastle.cms.PasswordRecipient.PRF HMacSHA512 = new com.android.internal.org.bouncycastle.cms.PasswordRecipient.PRF("HMacSHA512", new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_hmacWithSHA512, com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE));
        private final java.lang.String hmac;
        final com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier prfAlgID;

        private PRF(java.lang.String str, com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier) {
            this.hmac = str;
            this.prfAlgID = algorithmIdentifier;
        }

        public java.lang.String getName() {
            return this.hmac;
        }

        public com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier getAlgorithmID() {
            return this.prfAlgID;
        }
    }
}
