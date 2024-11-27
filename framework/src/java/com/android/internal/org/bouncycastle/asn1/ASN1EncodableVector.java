package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
public class ASN1EncodableVector {
    private static final int DEFAULT_CAPACITY = 10;
    static final com.android.internal.org.bouncycastle.asn1.ASN1Encodable[] EMPTY_ELEMENTS = new com.android.internal.org.bouncycastle.asn1.ASN1Encodable[0];
    private boolean copyOnWrite;
    private int elementCount;
    private com.android.internal.org.bouncycastle.asn1.ASN1Encodable[] elements;

    public ASN1EncodableVector() {
        this(10);
    }

    public ASN1EncodableVector(int i) {
        if (i < 0) {
            throw new java.lang.IllegalArgumentException("'initialCapacity' must not be negative");
        }
        this.elements = i == 0 ? EMPTY_ELEMENTS : new com.android.internal.org.bouncycastle.asn1.ASN1Encodable[i];
        this.elementCount = 0;
        this.copyOnWrite = false;
    }

    public void add(com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) {
        if (aSN1Encodable == null) {
            throw new java.lang.NullPointerException("'element' cannot be null");
        }
        int length = this.elements.length;
        int i = this.elementCount + 1;
        if (this.copyOnWrite | (i > length)) {
            reallocate(i);
        }
        this.elements[this.elementCount] = aSN1Encodable;
        this.elementCount = i;
    }

    public void addAll(com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector) {
        if (aSN1EncodableVector == null) {
            throw new java.lang.NullPointerException("'other' cannot be null");
        }
        int size = aSN1EncodableVector.size();
        boolean z = true;
        if (size < 1) {
            return;
        }
        int length = this.elements.length;
        int i = this.elementCount + size;
        int i2 = 0;
        if (i <= length) {
            z = false;
        }
        if (z | this.copyOnWrite) {
            reallocate(i);
        }
        do {
            com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable = aSN1EncodableVector.get(i2);
            if (aSN1Encodable == null) {
                throw new java.lang.NullPointerException("'other' elements cannot be null");
            }
            this.elements[this.elementCount + i2] = aSN1Encodable;
            i2++;
        } while (i2 < size);
        this.elementCount = i;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Encodable get(int i) {
        if (i >= this.elementCount) {
            throw new java.lang.ArrayIndexOutOfBoundsException(i + " >= " + this.elementCount);
        }
        return this.elements[i];
    }

    public int size() {
        return this.elementCount;
    }

    com.android.internal.org.bouncycastle.asn1.ASN1Encodable[] copyElements() {
        if (this.elementCount == 0) {
            return EMPTY_ELEMENTS;
        }
        com.android.internal.org.bouncycastle.asn1.ASN1Encodable[] aSN1EncodableArr = new com.android.internal.org.bouncycastle.asn1.ASN1Encodable[this.elementCount];
        java.lang.System.arraycopy(this.elements, 0, aSN1EncodableArr, 0, this.elementCount);
        return aSN1EncodableArr;
    }

    com.android.internal.org.bouncycastle.asn1.ASN1Encodable[] takeElements() {
        if (this.elementCount == 0) {
            return EMPTY_ELEMENTS;
        }
        if (this.elements.length == this.elementCount) {
            this.copyOnWrite = true;
            return this.elements;
        }
        com.android.internal.org.bouncycastle.asn1.ASN1Encodable[] aSN1EncodableArr = new com.android.internal.org.bouncycastle.asn1.ASN1Encodable[this.elementCount];
        java.lang.System.arraycopy(this.elements, 0, aSN1EncodableArr, 0, this.elementCount);
        return aSN1EncodableArr;
    }

    private void reallocate(int i) {
        com.android.internal.org.bouncycastle.asn1.ASN1Encodable[] aSN1EncodableArr = new com.android.internal.org.bouncycastle.asn1.ASN1Encodable[java.lang.Math.max(this.elements.length, i + (i >> 1))];
        java.lang.System.arraycopy(this.elements, 0, aSN1EncodableArr, 0, this.elementCount);
        this.elements = aSN1EncodableArr;
        this.copyOnWrite = false;
    }

    static com.android.internal.org.bouncycastle.asn1.ASN1Encodable[] cloneElements(com.android.internal.org.bouncycastle.asn1.ASN1Encodable[] aSN1EncodableArr) {
        return aSN1EncodableArr.length < 1 ? EMPTY_ELEMENTS : (com.android.internal.org.bouncycastle.asn1.ASN1Encodable[]) aSN1EncodableArr.clone();
    }
}
