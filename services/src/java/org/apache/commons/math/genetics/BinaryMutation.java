package org.apache.commons.math.genetics;

/* loaded from: classes3.dex */
public class BinaryMutation implements org.apache.commons.math.genetics.MutationPolicy {
    @Override // org.apache.commons.math.genetics.MutationPolicy
    public org.apache.commons.math.genetics.Chromosome mutate(org.apache.commons.math.genetics.Chromosome chromosome) {
        if (!(chromosome instanceof org.apache.commons.math.genetics.BinaryChromosome)) {
            throw new java.lang.IllegalArgumentException("Binary mutation works on BinaryChromosome only.");
        }
        org.apache.commons.math.genetics.BinaryChromosome binaryChromosome = (org.apache.commons.math.genetics.BinaryChromosome) chromosome;
        java.util.ArrayList arrayList = new java.util.ArrayList(binaryChromosome.getRepresentation());
        int nextInt = org.apache.commons.math.genetics.GeneticAlgorithm.getRandomGenerator().nextInt(binaryChromosome.getLength());
        arrayList.set(nextInt, java.lang.Integer.valueOf(binaryChromosome.getRepresentation().get(nextInt).intValue() == 0 ? 1 : 0));
        return binaryChromosome.newFixedLengthChromosome(arrayList);
    }
}
