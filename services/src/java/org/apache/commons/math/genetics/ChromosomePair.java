package org.apache.commons.math.genetics;

/* loaded from: classes3.dex */
public class ChromosomePair {
    private final org.apache.commons.math.genetics.Chromosome first;
    private final org.apache.commons.math.genetics.Chromosome second;

    public ChromosomePair(org.apache.commons.math.genetics.Chromosome chromosome, org.apache.commons.math.genetics.Chromosome chromosome2) {
        this.first = chromosome;
        this.second = chromosome2;
    }

    public org.apache.commons.math.genetics.Chromosome getFirst() {
        return this.first;
    }

    public org.apache.commons.math.genetics.Chromosome getSecond() {
        return this.second;
    }

    public java.lang.String toString() {
        return java.lang.String.format("(%s,%s)", getFirst(), getSecond());
    }
}
