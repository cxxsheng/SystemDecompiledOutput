package org.apache.commons.math.genetics;

/* loaded from: classes3.dex */
public abstract class AbstractListChromosome<T> extends org.apache.commons.math.genetics.Chromosome {
    private final java.util.List<T> representation;

    protected abstract void checkValidity(java.util.List<T> list) throws org.apache.commons.math.genetics.InvalidRepresentationException;

    public abstract org.apache.commons.math.genetics.AbstractListChromosome<T> newFixedLengthChromosome(java.util.List<T> list);

    public AbstractListChromosome(java.util.List<T> list) {
        try {
            checkValidity(list);
            this.representation = java.util.Collections.unmodifiableList(new java.util.ArrayList(list));
        } catch (org.apache.commons.math.genetics.InvalidRepresentationException e) {
            throw new java.lang.IllegalArgumentException(java.lang.String.format("Invalid representation for %s", getClass().getSimpleName()), e);
        }
    }

    public AbstractListChromosome(T[] tArr) {
        this(java.util.Arrays.asList(tArr));
    }

    protected java.util.List<T> getRepresentation() {
        return this.representation;
    }

    public int getLength() {
        return getRepresentation().size();
    }

    public java.lang.String toString() {
        return java.lang.String.format("(f=%s %s)", java.lang.Double.valueOf(getFitness()), getRepresentation());
    }
}
