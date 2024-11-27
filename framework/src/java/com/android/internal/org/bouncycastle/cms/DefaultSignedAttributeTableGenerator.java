package com.android.internal.org.bouncycastle.cms;

/* loaded from: classes4.dex */
public class DefaultSignedAttributeTableGenerator implements com.android.internal.org.bouncycastle.cms.CMSAttributeTableGenerator {
    private final java.util.Hashtable table;

    public DefaultSignedAttributeTableGenerator() {
        this.table = new java.util.Hashtable();
    }

    public DefaultSignedAttributeTableGenerator(com.android.internal.org.bouncycastle.asn1.cms.AttributeTable attributeTable) {
        if (attributeTable != null) {
            this.table = attributeTable.toHashtable();
        } else {
            this.table = new java.util.Hashtable();
        }
    }

    protected java.util.Hashtable createStandardAttributeTable(java.util.Map map) {
        com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier;
        java.util.Hashtable copyHashTable = copyHashTable(this.table);
        if (!copyHashTable.containsKey(com.android.internal.org.bouncycastle.asn1.cms.CMSAttributes.contentType) && (aSN1ObjectIdentifier = com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier.getInstance(map.get(com.android.internal.org.bouncycastle.cms.CMSAttributeTableGenerator.CONTENT_TYPE))) != null) {
            com.android.internal.org.bouncycastle.asn1.cms.Attribute attribute = new com.android.internal.org.bouncycastle.asn1.cms.Attribute(com.android.internal.org.bouncycastle.asn1.cms.CMSAttributes.contentType, new com.android.internal.org.bouncycastle.asn1.DERSet(aSN1ObjectIdentifier));
            copyHashTable.put(attribute.getAttrType(), attribute);
        }
        if (!copyHashTable.containsKey(com.android.internal.org.bouncycastle.asn1.cms.CMSAttributes.signingTime)) {
            com.android.internal.org.bouncycastle.asn1.cms.Attribute attribute2 = new com.android.internal.org.bouncycastle.asn1.cms.Attribute(com.android.internal.org.bouncycastle.asn1.cms.CMSAttributes.signingTime, new com.android.internal.org.bouncycastle.asn1.DERSet(new com.android.internal.org.bouncycastle.asn1.cms.Time(new java.util.Date())));
            copyHashTable.put(attribute2.getAttrType(), attribute2);
        }
        if (!copyHashTable.containsKey(com.android.internal.org.bouncycastle.asn1.cms.CMSAttributes.messageDigest)) {
            com.android.internal.org.bouncycastle.asn1.cms.Attribute attribute3 = new com.android.internal.org.bouncycastle.asn1.cms.Attribute(com.android.internal.org.bouncycastle.asn1.cms.CMSAttributes.messageDigest, new com.android.internal.org.bouncycastle.asn1.DERSet(new com.android.internal.org.bouncycastle.asn1.DEROctetString((byte[]) map.get(com.android.internal.org.bouncycastle.cms.CMSAttributeTableGenerator.DIGEST))));
            copyHashTable.put(attribute3.getAttrType(), attribute3);
        }
        if (!copyHashTable.contains(com.android.internal.org.bouncycastle.asn1.cms.CMSAttributes.cmsAlgorithmProtect)) {
            com.android.internal.org.bouncycastle.asn1.cms.Attribute attribute4 = new com.android.internal.org.bouncycastle.asn1.cms.Attribute(com.android.internal.org.bouncycastle.asn1.cms.CMSAttributes.cmsAlgorithmProtect, new com.android.internal.org.bouncycastle.asn1.DERSet(new com.android.internal.org.bouncycastle.asn1.cms.CMSAlgorithmProtection((com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier) map.get(com.android.internal.org.bouncycastle.cms.CMSAttributeTableGenerator.DIGEST_ALGORITHM_IDENTIFIER), 1, (com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier) map.get(com.android.internal.org.bouncycastle.cms.CMSAttributeTableGenerator.SIGNATURE_ALGORITHM_IDENTIFIER))));
            copyHashTable.put(attribute4.getAttrType(), attribute4);
        }
        return copyHashTable;
    }

    @Override // com.android.internal.org.bouncycastle.cms.CMSAttributeTableGenerator
    public com.android.internal.org.bouncycastle.asn1.cms.AttributeTable getAttributes(java.util.Map map) {
        return new com.android.internal.org.bouncycastle.asn1.cms.AttributeTable(createStandardAttributeTable(map));
    }

    private static java.util.Hashtable copyHashTable(java.util.Hashtable hashtable) {
        java.util.Hashtable hashtable2 = new java.util.Hashtable();
        java.util.Enumeration keys = hashtable.keys();
        while (keys.hasMoreElements()) {
            java.lang.Object nextElement = keys.nextElement();
            hashtable2.put(nextElement, hashtable.get(nextElement));
        }
        return hashtable2;
    }
}
