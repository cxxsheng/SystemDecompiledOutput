package org.apache.commons.math.genetics;

/* loaded from: classes3.dex */
public abstract class RandomKey<T> extends org.apache.commons.math.genetics.AbstractListChromosome<java.lang.Double> implements org.apache.commons.math.genetics.PermutationChromosome<T> {
    private final java.util.List<java.lang.Integer> baseSeqPermutation;
    private final java.util.List<java.lang.Double> sortedRepresentation;

    public RandomKey(java.util.List<java.lang.Double> list) {
        super(list);
        java.util.ArrayList arrayList = new java.util.ArrayList(getRepresentation());
        java.util.Collections.sort(arrayList);
        this.sortedRepresentation = java.util.Collections.unmodifiableList(arrayList);
        this.baseSeqPermutation = java.util.Collections.unmodifiableList(decodeGeneric(baseSequence(getLength()), getRepresentation(), this.sortedRepresentation));
    }

    public RandomKey(java.lang.Double[] dArr) {
        this((java.util.List<java.lang.Double>) java.util.Arrays.asList(dArr));
    }

    @Override // org.apache.commons.math.genetics.PermutationChromosome
    public java.util.List<T> decode(java.util.List<T> list) {
        return decodeGeneric(list, getRepresentation(), this.sortedRepresentation);
    }

    private static <S> java.util.List<S> decodeGeneric(java.util.List<S> list, java.util.List<java.lang.Double> list2, java.util.List<java.lang.Double> list3) {
        int size = list.size();
        if (list2.size() != size) {
            throw new java.lang.IllegalArgumentException(java.lang.String.format("Length of sequence for decoding (%s) has to be equal to the length of the RandomKey (%s)", java.lang.Integer.valueOf(size), java.lang.Integer.valueOf(list2.size())));
        }
        if (list2.size() != list3.size()) {
            throw new java.lang.IllegalArgumentException(java.lang.String.format("Representation and sortedRepr must have same sizes, %d != %d", java.lang.Integer.valueOf(list2.size()), java.lang.Integer.valueOf(list3.size())));
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(list2);
        java.util.ArrayList arrayList2 = new java.util.ArrayList(size);
        for (int i = 0; i < size; i++) {
            int indexOf = arrayList.indexOf(list3.get(i));
            arrayList2.add(list.get(indexOf));
            arrayList.set(indexOf, null);
        }
        return arrayList2;
    }

    @Override // org.apache.commons.math.genetics.Chromosome
    protected boolean isSame(org.apache.commons.math.genetics.Chromosome chromosome) {
        if (!(chromosome instanceof org.apache.commons.math.genetics.RandomKey)) {
            return false;
        }
        org.apache.commons.math.genetics.RandomKey randomKey = (org.apache.commons.math.genetics.RandomKey) chromosome;
        if (getLength() != randomKey.getLength()) {
            return false;
        }
        java.util.List<java.lang.Integer> list = this.baseSeqPermutation;
        java.util.List<java.lang.Integer> list2 = randomKey.baseSeqPermutation;
        for (int i = 0; i < getLength(); i++) {
            if (list.get(i) != list2.get(i)) {
                return false;
            }
        }
        return true;
    }

    @Override // org.apache.commons.math.genetics.AbstractListChromosome
    protected void checkValidity(java.util.List<java.lang.Double> list) throws org.apache.commons.math.genetics.InvalidRepresentationException {
        java.util.Iterator<java.lang.Double> it = list.iterator();
        while (it.hasNext()) {
            double doubleValue = it.next().doubleValue();
            if (doubleValue < 0.0d || doubleValue > 1.0d) {
                throw new org.apache.commons.math.genetics.InvalidRepresentationException("Values of representation must be in [0,1] interval");
            }
        }
    }

    public static final java.util.List<java.lang.Double> randomPermutation(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList(i);
        for (int i2 = 0; i2 < i; i2++) {
            arrayList.add(java.lang.Double.valueOf(org.apache.commons.math.genetics.GeneticAlgorithm.getRandomGenerator().nextDouble()));
        }
        return arrayList;
    }

    public static final java.util.List<java.lang.Double> identityPermutation(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList(i);
        for (int i2 = 0; i2 < i; i2++) {
            arrayList.add(java.lang.Double.valueOf(i2 / i));
        }
        return arrayList;
    }

    public static <S> java.util.List<java.lang.Double> comparatorPermutation(java.util.List<S> list, java.util.Comparator<S> comparator) {
        java.util.ArrayList arrayList = new java.util.ArrayList(list);
        java.util.Collections.sort(arrayList, comparator);
        return inducedPermutation(list, arrayList);
    }

    public static <S> java.util.List<java.lang.Double> inducedPermutation(java.util.List<S> list, java.util.List<S> list2) throws java.lang.IllegalArgumentException {
        if (list.size() != list2.size()) {
            throw new java.lang.IllegalArgumentException("originalData and permutedData must have same length");
        }
        int size = list.size();
        java.util.ArrayList arrayList = new java.util.ArrayList(list);
        java.lang.Double[] dArr = new java.lang.Double[size];
        for (int i = 0; i < size; i++) {
            int indexOf = arrayList.indexOf(list2.get(i));
            if (indexOf == -1) {
                throw new java.lang.IllegalArgumentException("originalData and permutedData must contain the same objects.");
            }
            dArr[indexOf] = java.lang.Double.valueOf(i / size);
            arrayList.set(indexOf, null);
        }
        return java.util.Arrays.asList(dArr);
    }

    @Override // org.apache.commons.math.genetics.AbstractListChromosome
    public java.lang.String toString() {
        return java.lang.String.format("(f=%s pi=(%s))", java.lang.Double.valueOf(getFitness()), this.baseSeqPermutation);
    }

    private static java.util.List<java.lang.Integer> baseSequence(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList(i);
        for (int i2 = 0; i2 < i; i2++) {
            arrayList.add(java.lang.Integer.valueOf(i2));
        }
        return arrayList;
    }
}
