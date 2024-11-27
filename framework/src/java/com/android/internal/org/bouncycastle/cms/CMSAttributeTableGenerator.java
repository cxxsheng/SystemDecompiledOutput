package com.android.internal.org.bouncycastle.cms;

/* loaded from: classes4.dex */
public interface CMSAttributeTableGenerator {
    public static final java.lang.String CONTENT_TYPE = "contentType";
    public static final java.lang.String DIGEST = "digest";
    public static final java.lang.String DIGEST_ALGORITHM_IDENTIFIER = "digestAlgID";
    public static final java.lang.String MAC_ALGORITHM_IDENTIFIER = "macAlgID";
    public static final java.lang.String SIGNATURE = "encryptedDigest";
    public static final java.lang.String SIGNATURE_ALGORITHM_IDENTIFIER = "signatureAlgID";

    com.android.internal.org.bouncycastle.asn1.cms.AttributeTable getAttributes(java.util.Map map) throws com.android.internal.org.bouncycastle.cms.CMSAttributeTableGenerationException;
}
