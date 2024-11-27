package com.android.internal.org.bouncycastle.cms;

/* loaded from: classes4.dex */
public class SimpleAttributeTableGenerator implements com.android.internal.org.bouncycastle.cms.CMSAttributeTableGenerator {
    private final com.android.internal.org.bouncycastle.asn1.cms.AttributeTable attributes;

    public SimpleAttributeTableGenerator(com.android.internal.org.bouncycastle.asn1.cms.AttributeTable attributeTable) {
        this.attributes = attributeTable;
    }

    @Override // com.android.internal.org.bouncycastle.cms.CMSAttributeTableGenerator
    public com.android.internal.org.bouncycastle.asn1.cms.AttributeTable getAttributes(java.util.Map map) {
        return this.attributes;
    }
}
