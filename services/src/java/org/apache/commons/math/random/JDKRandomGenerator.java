package org.apache.commons.math.random;

/* loaded from: classes3.dex */
public class JDKRandomGenerator extends java.util.Random implements org.apache.commons.math.random.RandomGenerator {
    private static final long serialVersionUID = -7745277476784028798L;

    @Override // org.apache.commons.math.random.RandomGenerator
    public void setSeed(int i) {
        setSeed(i);
    }

    @Override // org.apache.commons.math.random.RandomGenerator
    public void setSeed(int[] iArr) {
        long j = 0;
        for (int i : iArr) {
            j = (j * 4294967291L) + i;
        }
        setSeed(j);
    }
}
