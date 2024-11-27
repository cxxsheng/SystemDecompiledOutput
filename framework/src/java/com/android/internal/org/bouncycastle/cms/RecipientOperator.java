package com.android.internal.org.bouncycastle.cms;

/* loaded from: classes4.dex */
public class RecipientOperator {
    private final java.lang.Object operator;

    public RecipientOperator(com.android.internal.org.bouncycastle.operator.InputDecryptor inputDecryptor) {
        this.operator = inputDecryptor;
    }

    public RecipientOperator(com.android.internal.org.bouncycastle.operator.MacCalculator macCalculator) {
        this.operator = macCalculator;
    }

    public java.io.InputStream getInputStream(java.io.InputStream inputStream) {
        if (this.operator instanceof com.android.internal.org.bouncycastle.operator.InputDecryptor) {
            return ((com.android.internal.org.bouncycastle.operator.InputDecryptor) this.operator).getInputStream(inputStream);
        }
        return new com.android.internal.org.bouncycastle.util.io.TeeInputStream(inputStream, ((com.android.internal.org.bouncycastle.operator.MacCalculator) this.operator).getOutputStream());
    }

    public boolean isMacBased() {
        return this.operator instanceof com.android.internal.org.bouncycastle.operator.MacCalculator;
    }

    public byte[] getMac() {
        return ((com.android.internal.org.bouncycastle.operator.MacCalculator) this.operator).getMac();
    }
}
