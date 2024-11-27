package org.apache.commons.math.genetics;

/* loaded from: classes3.dex */
public class ElitisticListPopulation extends org.apache.commons.math.genetics.ListPopulation {
    private double elitismRate;

    public ElitisticListPopulation(java.util.List<org.apache.commons.math.genetics.Chromosome> list, int i, double d) {
        super(list, i);
        this.elitismRate = 0.9d;
        this.elitismRate = d;
    }

    public ElitisticListPopulation(int i, double d) {
        super(i);
        this.elitismRate = 0.9d;
        this.elitismRate = d;
    }

    @Override // org.apache.commons.math.genetics.Population
    public org.apache.commons.math.genetics.Population nextGeneration() {
        org.apache.commons.math.genetics.ElitisticListPopulation elitisticListPopulation = new org.apache.commons.math.genetics.ElitisticListPopulation(getPopulationLimit(), getElitismRate());
        java.util.List<org.apache.commons.math.genetics.Chromosome> chromosomes = getChromosomes();
        java.util.Collections.sort(chromosomes);
        for (int ceil = (int) org.apache.commons.math.util.FastMath.ceil((1.0d - getElitismRate()) * chromosomes.size()); ceil < chromosomes.size(); ceil++) {
            elitisticListPopulation.addChromosome(chromosomes.get(ceil));
        }
        return elitisticListPopulation;
    }

    public void setElitismRate(double d) {
        if (d < 0.0d || d > 1.0d) {
            throw new java.lang.IllegalArgumentException("Elitism rate has to be in [0,1]");
        }
        this.elitismRate = d;
    }

    public double getElitismRate() {
        return this.elitismRate;
    }
}
