package org.apache.commons.math.genetics;

/* loaded from: classes3.dex */
public abstract class BinaryChromosome extends org.apache.commons.math.genetics.AbstractListChromosome<java.lang.Integer> {
    public BinaryChromosome(java.util.List<java.lang.Integer> list) {
        super(list);
    }

    public BinaryChromosome(java.lang.Integer[] numArr) {
        super(numArr);
    }

    @Override // org.apache.commons.math.genetics.AbstractListChromosome
    protected void checkValidity(java.util.List<java.lang.Integer> list) throws org.apache.commons.math.genetics.InvalidRepresentationException {
        java.util.Iterator<java.lang.Integer> it = list.iterator();
        while (it.hasNext()) {
            int intValue = it.next().intValue();
            if (intValue < 0 || intValue > 1) {
                throw new org.apache.commons.math.genetics.InvalidRepresentationException("Elements can be only 0 or 1.");
            }
        }
    }

    public static java.util.List<java.lang.Integer> randomBinaryRepresentation(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList(i);
        for (int i2 = 0; i2 < i; i2++) {
            arrayList.add(java.lang.Integer.valueOf(org.apache.commons.math.genetics.GeneticAlgorithm.getRandomGenerator().nextInt(2)));
        }
        return arrayList;
    }

    @Override // org.apache.commons.math.genetics.Chromosome
    protected boolean isSame(org.apache.commons.math.genetics.Chromosome chromosome) {
        if (!(chromosome instanceof org.apache.commons.math.genetics.BinaryChromosome)) {
            return false;
        }
        org.apache.commons.math.genetics.BinaryChromosome binaryChromosome = (org.apache.commons.math.genetics.BinaryChromosome) chromosome;
        if (getLength() != binaryChromosome.getLength()) {
            return false;
        }
        for (int i = 0; i < getRepresentation().size(); i++) {
            if (!getRepresentation().get(i).equals(binaryChromosome.getRepresentation().get(i))) {
                return false;
            }
        }
        return true;
    }
}
