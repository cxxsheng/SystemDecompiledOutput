package com.android.internal.org.bouncycastle.asn1.cms;

/* loaded from: classes4.dex */
public class AttributeTable {
    private java.util.Hashtable attributes;

    public AttributeTable(java.util.Hashtable hashtable) {
        this.attributes = new java.util.Hashtable();
        this.attributes = copyTable(hashtable);
    }

    public AttributeTable(com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector) {
        this.attributes = new java.util.Hashtable();
        for (int i = 0; i != aSN1EncodableVector.size(); i++) {
            com.android.internal.org.bouncycastle.asn1.cms.Attribute attribute = com.android.internal.org.bouncycastle.asn1.cms.Attribute.getInstance(aSN1EncodableVector.get(i));
            addAttribute(attribute.getAttrType(), attribute);
        }
    }

    public AttributeTable(com.android.internal.org.bouncycastle.asn1.ASN1Set aSN1Set) {
        this.attributes = new java.util.Hashtable();
        for (int i = 0; i != aSN1Set.size(); i++) {
            com.android.internal.org.bouncycastle.asn1.cms.Attribute attribute = com.android.internal.org.bouncycastle.asn1.cms.Attribute.getInstance(aSN1Set.getObjectAt(i));
            addAttribute(attribute.getAttrType(), attribute);
        }
    }

    public AttributeTable(com.android.internal.org.bouncycastle.asn1.cms.Attribute attribute) {
        this.attributes = new java.util.Hashtable();
        addAttribute(attribute.getAttrType(), attribute);
    }

    public AttributeTable(com.android.internal.org.bouncycastle.asn1.cms.Attributes attributes) {
        this(com.android.internal.org.bouncycastle.asn1.ASN1Set.getInstance(attributes.toASN1Primitive()));
    }

    private void addAttribute(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, com.android.internal.org.bouncycastle.asn1.cms.Attribute attribute) {
        java.util.Vector vector;
        java.lang.Object obj = this.attributes.get(aSN1ObjectIdentifier);
        if (obj == null) {
            this.attributes.put(aSN1ObjectIdentifier, attribute);
            return;
        }
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.cms.Attribute) {
            vector = new java.util.Vector();
            vector.addElement(obj);
            vector.addElement(attribute);
        } else {
            vector = (java.util.Vector) obj;
            vector.addElement(attribute);
        }
        this.attributes.put(aSN1ObjectIdentifier, vector);
    }

    public com.android.internal.org.bouncycastle.asn1.cms.Attribute get(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        java.lang.Object obj = this.attributes.get(aSN1ObjectIdentifier);
        if (obj instanceof java.util.Vector) {
            return (com.android.internal.org.bouncycastle.asn1.cms.Attribute) ((java.util.Vector) obj).elementAt(0);
        }
        return (com.android.internal.org.bouncycastle.asn1.cms.Attribute) obj;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector getAll(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
        java.lang.Object obj = this.attributes.get(aSN1ObjectIdentifier);
        if (obj instanceof java.util.Vector) {
            java.util.Enumeration elements = ((java.util.Vector) obj).elements();
            while (elements.hasMoreElements()) {
                aSN1EncodableVector.add((com.android.internal.org.bouncycastle.asn1.cms.Attribute) elements.nextElement());
            }
        } else if (obj != null) {
            aSN1EncodableVector.add((com.android.internal.org.bouncycastle.asn1.cms.Attribute) obj);
        }
        return aSN1EncodableVector;
    }

    public int size() {
        java.util.Enumeration elements = this.attributes.elements();
        int i = 0;
        while (elements.hasMoreElements()) {
            java.lang.Object nextElement = elements.nextElement();
            if (nextElement instanceof java.util.Vector) {
                i += ((java.util.Vector) nextElement).size();
            } else {
                i++;
            }
        }
        return i;
    }

    public java.util.Hashtable toHashtable() {
        return copyTable(this.attributes);
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector toASN1EncodableVector() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
        java.util.Enumeration elements = this.attributes.elements();
        while (elements.hasMoreElements()) {
            java.lang.Object nextElement = elements.nextElement();
            if (nextElement instanceof java.util.Vector) {
                java.util.Enumeration elements2 = ((java.util.Vector) nextElement).elements();
                while (elements2.hasMoreElements()) {
                    aSN1EncodableVector.add(com.android.internal.org.bouncycastle.asn1.cms.Attribute.getInstance(elements2.nextElement()));
                }
            } else {
                aSN1EncodableVector.add(com.android.internal.org.bouncycastle.asn1.cms.Attribute.getInstance(nextElement));
            }
        }
        return aSN1EncodableVector;
    }

    public com.android.internal.org.bouncycastle.asn1.cms.Attributes toASN1Structure() {
        return new com.android.internal.org.bouncycastle.asn1.cms.Attributes(toASN1EncodableVector());
    }

    private java.util.Hashtable copyTable(java.util.Hashtable hashtable) {
        java.util.Hashtable hashtable2 = new java.util.Hashtable();
        java.util.Enumeration keys = hashtable.keys();
        while (keys.hasMoreElements()) {
            java.lang.Object nextElement = keys.nextElement();
            hashtable2.put(nextElement, hashtable.get(nextElement));
        }
        return hashtable2;
    }

    public com.android.internal.org.bouncycastle.asn1.cms.AttributeTable add(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) {
        com.android.internal.org.bouncycastle.asn1.cms.AttributeTable attributeTable = new com.android.internal.org.bouncycastle.asn1.cms.AttributeTable(this.attributes);
        attributeTable.addAttribute(aSN1ObjectIdentifier, new com.android.internal.org.bouncycastle.asn1.cms.Attribute(aSN1ObjectIdentifier, new com.android.internal.org.bouncycastle.asn1.DERSet(aSN1Encodable)));
        return attributeTable;
    }

    public com.android.internal.org.bouncycastle.asn1.cms.AttributeTable remove(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        com.android.internal.org.bouncycastle.asn1.cms.AttributeTable attributeTable = new com.android.internal.org.bouncycastle.asn1.cms.AttributeTable(this.attributes);
        attributeTable.attributes.remove(aSN1ObjectIdentifier);
        return attributeTable;
    }
}
