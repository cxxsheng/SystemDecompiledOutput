package org.apache.commons.math.genetics;

/* loaded from: classes3.dex */
public class OnePointCrossover<T> implements org.apache.commons.math.genetics.CrossoverPolicy {
    @Override // org.apache.commons.math.genetics.CrossoverPolicy
    public org.apache.commons.math.genetics.ChromosomePair crossover(org.apache.commons.math.genetics.Chromosome chromosome, org.apache.commons.math.genetics.Chromosome chromosome2) {
        if (!(chromosome instanceof org.apache.commons.math.genetics.AbstractListChromosome) || !(chromosome2 instanceof org.apache.commons.math.genetics.AbstractListChromosome)) {
            throw new java.lang.IllegalArgumentException("One point crossover works on FixedLengthChromosomes only.");
        }
        return crossover((org.apache.commons.math.genetics.AbstractListChromosome) chromosome, (org.apache.commons.math.genetics.AbstractListChromosome) chromosome2);
    }

    private org.apache.commons.math.genetics.ChromosomePair crossover(org.apache.commons.math.genetics.AbstractListChromosome<T> abstractListChromosome, org.apache.commons.math.genetics.AbstractListChromosome<T> abstractListChromosome2) {
        int length = abstractListChromosome.getLength();
        if (length != abstractListChromosome2.getLength()) {
            throw new java.lang.IllegalArgumentException("Both chromosomes must have same lengths.");
        }
        java.util.List<T> representation = abstractListChromosome.getRepresentation();
        java.util.List<T> representation2 = abstractListChromosome2.getRepresentation();
        java.util.ArrayList arrayList = new java.util.ArrayList(abstractListChromosome.getLength());
        java.util.ArrayList arrayList2 = new java.util.ArrayList(abstractListChromosome2.getLength());
        int nextInt = org.apache.commons.math.genetics.GeneticAlgorithm.getRandomGenerator().nextInt(length - 2) + 1;
        for (int i = 0; i < nextInt; i++) {
            arrayList.add(representation.get(i));
            arrayList2.add(representation2.get(i));
        }
        while (nextInt < length) {
            arrayList.add(representation2.get(nextInt));
            arrayList2.add(representation.get(nextInt));
            nextInt++;
        }
        return new org.apache.commons.math.genetics.ChromosomePair(abstractListChromosome.newFixedLengthChromosome(arrayList), abstractListChromosome2.newFixedLengthChromosome(arrayList2));
    }
}
