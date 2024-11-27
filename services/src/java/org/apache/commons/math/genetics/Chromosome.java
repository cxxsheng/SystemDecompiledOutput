package org.apache.commons.math.genetics;

/* loaded from: classes3.dex */
public abstract class Chromosome implements java.lang.Comparable<org.apache.commons.math.genetics.Chromosome>, org.apache.commons.math.genetics.Fitness {
    private double fitness = Double.MIN_VALUE;

    public double getFitness() {
        if (this.fitness == Double.MIN_VALUE) {
            this.fitness = fitness();
        }
        return this.fitness;
    }

    @Override // java.lang.Comparable
    public int compareTo(org.apache.commons.math.genetics.Chromosome chromosome) {
        return java.lang.Double.valueOf(getFitness()).compareTo(java.lang.Double.valueOf(chromosome.getFitness()));
    }

    protected boolean isSame(org.apache.commons.math.genetics.Chromosome chromosome) {
        return false;
    }

    protected org.apache.commons.math.genetics.Chromosome findSameChromosome(org.apache.commons.math.genetics.Population population) {
        for (org.apache.commons.math.genetics.Chromosome chromosome : population) {
            if (isSame(chromosome)) {
                return chromosome;
            }
        }
        return null;
    }

    public void searchForFitnessUpdate(org.apache.commons.math.genetics.Population population) {
        org.apache.commons.math.genetics.Chromosome findSameChromosome = findSameChromosome(population);
        if (findSameChromosome != null) {
            this.fitness = findSameChromosome.getFitness();
        }
    }
}
