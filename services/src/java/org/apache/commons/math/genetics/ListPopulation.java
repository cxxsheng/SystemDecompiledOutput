package org.apache.commons.math.genetics;

/* loaded from: classes3.dex */
public abstract class ListPopulation implements org.apache.commons.math.genetics.Population {
    private java.util.List<org.apache.commons.math.genetics.Chromosome> chromosomes;
    private int populationLimit;

    public ListPopulation(java.util.List<org.apache.commons.math.genetics.Chromosome> list, int i) {
        if (list.size() > i) {
            throw new org.apache.commons.math.exception.NumberIsTooLargeException(org.apache.commons.math.exception.util.LocalizedFormats.LIST_OF_CHROMOSOMES_BIGGER_THAN_POPULATION_SIZE, java.lang.Integer.valueOf(list.size()), java.lang.Integer.valueOf(i), false);
        }
        if (i < 0) {
            throw new org.apache.commons.math.exception.NotPositiveException(org.apache.commons.math.exception.util.LocalizedFormats.POPULATION_LIMIT_NOT_POSITIVE, java.lang.Integer.valueOf(i));
        }
        this.chromosomes = list;
        this.populationLimit = i;
    }

    public ListPopulation(int i) {
        if (i < 0) {
            throw new org.apache.commons.math.exception.NotPositiveException(org.apache.commons.math.exception.util.LocalizedFormats.POPULATION_LIMIT_NOT_POSITIVE, java.lang.Integer.valueOf(i));
        }
        this.populationLimit = i;
        this.chromosomes = new java.util.ArrayList(i);
    }

    public void setChromosomes(java.util.List<org.apache.commons.math.genetics.Chromosome> list) {
        this.chromosomes = list;
    }

    public java.util.List<org.apache.commons.math.genetics.Chromosome> getChromosomes() {
        return this.chromosomes;
    }

    @Override // org.apache.commons.math.genetics.Population
    public void addChromosome(org.apache.commons.math.genetics.Chromosome chromosome) {
        this.chromosomes.add(chromosome);
    }

    @Override // org.apache.commons.math.genetics.Population
    public org.apache.commons.math.genetics.Chromosome getFittestChromosome() {
        org.apache.commons.math.genetics.Chromosome chromosome = this.chromosomes.get(0);
        for (org.apache.commons.math.genetics.Chromosome chromosome2 : this.chromosomes) {
            if (chromosome2.compareTo(chromosome) > 0) {
                chromosome = chromosome2;
            }
        }
        return chromosome;
    }

    @Override // org.apache.commons.math.genetics.Population
    public int getPopulationLimit() {
        return this.populationLimit;
    }

    public void setPopulationLimit(int i) {
        this.populationLimit = i;
    }

    @Override // org.apache.commons.math.genetics.Population
    public int getPopulationSize() {
        return this.chromosomes.size();
    }

    public java.lang.String toString() {
        return this.chromosomes.toString();
    }

    @Override // java.lang.Iterable
    public java.util.Iterator<org.apache.commons.math.genetics.Chromosome> iterator() {
        return this.chromosomes.iterator();
    }
}
