package org.apache.commons.math.random;

/* loaded from: classes3.dex */
public class GaussianRandomGenerator implements org.apache.commons.math.random.NormalizedRandomGenerator {
    private final org.apache.commons.math.random.RandomGenerator generator;

    public GaussianRandomGenerator(org.apache.commons.math.random.RandomGenerator randomGenerator) {
        this.generator = randomGenerator;
    }

    @Override // org.apache.commons.math.random.NormalizedRandomGenerator
    public double nextNormalizedDouble() {
        return this.generator.nextGaussian();
    }
}
