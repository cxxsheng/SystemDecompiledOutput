package org.apache.commons.math.genetics;

/* loaded from: classes3.dex */
public class RandomKeyMutation implements org.apache.commons.math.genetics.MutationPolicy {
    @Override // org.apache.commons.math.genetics.MutationPolicy
    public org.apache.commons.math.genetics.Chromosome mutate(org.apache.commons.math.genetics.Chromosome chromosome) {
        if (!(chromosome instanceof org.apache.commons.math.genetics.RandomKey)) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.RANDOMKEY_MUTATION_WRONG_CLASS, chromosome.getClass().getSimpleName());
        }
        org.apache.commons.math.genetics.RandomKey randomKey = (org.apache.commons.math.genetics.RandomKey) chromosome;
        java.util.List<java.lang.Double> representation = randomKey.getRepresentation();
        int nextInt = org.apache.commons.math.genetics.GeneticAlgorithm.getRandomGenerator().nextInt(representation.size());
        java.util.ArrayList arrayList = new java.util.ArrayList(representation);
        arrayList.set(nextInt, java.lang.Double.valueOf(org.apache.commons.math.genetics.GeneticAlgorithm.getRandomGenerator().nextDouble()));
        return randomKey.newFixedLengthChromosome(arrayList);
    }
}
