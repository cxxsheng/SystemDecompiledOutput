package com.android.internal.org.bouncycastle.math.field;

/* loaded from: classes4.dex */
class GenericPolynomialExtensionField implements com.android.internal.org.bouncycastle.math.field.PolynomialExtensionField {
    protected final com.android.internal.org.bouncycastle.math.field.Polynomial minimalPolynomial;
    protected final com.android.internal.org.bouncycastle.math.field.FiniteField subfield;

    GenericPolynomialExtensionField(com.android.internal.org.bouncycastle.math.field.FiniteField finiteField, com.android.internal.org.bouncycastle.math.field.Polynomial polynomial) {
        this.subfield = finiteField;
        this.minimalPolynomial = polynomial;
    }

    @Override // com.android.internal.org.bouncycastle.math.field.FiniteField
    public java.math.BigInteger getCharacteristic() {
        return this.subfield.getCharacteristic();
    }

    @Override // com.android.internal.org.bouncycastle.math.field.FiniteField
    public int getDimension() {
        return this.subfield.getDimension() * this.minimalPolynomial.getDegree();
    }

    @Override // com.android.internal.org.bouncycastle.math.field.ExtensionField
    public com.android.internal.org.bouncycastle.math.field.FiniteField getSubfield() {
        return this.subfield;
    }

    @Override // com.android.internal.org.bouncycastle.math.field.ExtensionField
    public int getDegree() {
        return this.minimalPolynomial.getDegree();
    }

    @Override // com.android.internal.org.bouncycastle.math.field.PolynomialExtensionField
    public com.android.internal.org.bouncycastle.math.field.Polynomial getMinimalPolynomial() {
        return this.minimalPolynomial;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof com.android.internal.org.bouncycastle.math.field.GenericPolynomialExtensionField)) {
            return false;
        }
        com.android.internal.org.bouncycastle.math.field.GenericPolynomialExtensionField genericPolynomialExtensionField = (com.android.internal.org.bouncycastle.math.field.GenericPolynomialExtensionField) obj;
        return this.subfield.equals(genericPolynomialExtensionField.subfield) && this.minimalPolynomial.equals(genericPolynomialExtensionField.minimalPolynomial);
    }

    public int hashCode() {
        return this.subfield.hashCode() ^ com.android.internal.org.bouncycastle.util.Integers.rotateLeft(this.minimalPolynomial.hashCode(), 16);
    }
}
