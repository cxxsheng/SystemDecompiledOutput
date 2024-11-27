package org.apache.commons.math.genetics;

/* loaded from: classes3.dex */
public class GeneticAlgorithm {
    private static org.apache.commons.math.random.RandomGenerator randomGenerator = new org.apache.commons.math.random.JDKRandomGenerator();
    private final org.apache.commons.math.genetics.CrossoverPolicy crossoverPolicy;
    private final double crossoverRate;
    private int generationsEvolved = 0;
    private final org.apache.commons.math.genetics.MutationPolicy mutationPolicy;
    private final double mutationRate;
    private final org.apache.commons.math.genetics.SelectionPolicy selectionPolicy;

    public GeneticAlgorithm(org.apache.commons.math.genetics.CrossoverPolicy crossoverPolicy, double d, org.apache.commons.math.genetics.MutationPolicy mutationPolicy, double d2, org.apache.commons.math.genetics.SelectionPolicy selectionPolicy) {
        if (d < 0.0d || d > 1.0d) {
            throw new java.lang.IllegalArgumentException("crossoverRate must be between 0 and 1");
        }
        if (d2 < 0.0d || d2 > 1.0d) {
            throw new java.lang.IllegalArgumentException("mutationRate must be between 0 and 1");
        }
        this.crossoverPolicy = crossoverPolicy;
        this.crossoverRate = d;
        this.mutationPolicy = mutationPolicy;
        this.mutationRate = d2;
        this.selectionPolicy = selectionPolicy;
    }

    public static synchronized void setRandomGenerator(org.apache.commons.math.random.RandomGenerator randomGenerator2) {
        synchronized (org.apache.commons.math.genetics.GeneticAlgorithm.class) {
            randomGenerator = randomGenerator2;
        }
    }

    public static synchronized org.apache.commons.math.random.RandomGenerator getRandomGenerator() {
        org.apache.commons.math.random.RandomGenerator randomGenerator2;
        synchronized (org.apache.commons.math.genetics.GeneticAlgorithm.class) {
            randomGenerator2 = randomGenerator;
        }
        return randomGenerator2;
    }

    public org.apache.commons.math.genetics.Population evolve(org.apache.commons.math.genetics.Population population, org.apache.commons.math.genetics.StoppingCondition stoppingCondition) {
        this.generationsEvolved = 0;
        while (!stoppingCondition.isSatisfied(population)) {
            population = nextGeneration(population);
            this.generationsEvolved++;
        }
        return population;
    }

    public org.apache.commons.math.genetics.Population nextGeneration(org.apache.commons.math.genetics.Population population) {
        org.apache.commons.math.genetics.Population nextGeneration = population.nextGeneration();
        org.apache.commons.math.random.RandomGenerator randomGenerator2 = getRandomGenerator();
        while (nextGeneration.getPopulationSize() < nextGeneration.getPopulationLimit()) {
            org.apache.commons.math.genetics.ChromosomePair select = getSelectionPolicy().select(population);
            if (randomGenerator2.nextDouble() < getCrossoverRate()) {
                select = getCrossoverPolicy().crossover(select.getFirst(), select.getSecond());
            }
            if (randomGenerator2.nextDouble() < getMutationRate()) {
                select = new org.apache.commons.math.genetics.ChromosomePair(getMutationPolicy().mutate(select.getFirst()), getMutationPolicy().mutate(select.getSecond()));
            }
            nextGeneration.addChromosome(select.getFirst());
            if (nextGeneration.getPopulationSize() < nextGeneration.getPopulationLimit()) {
                nextGeneration.addChromosome(select.getSecond());
            }
        }
        return nextGeneration;
    }

    public org.apache.commons.math.genetics.CrossoverPolicy getCrossoverPolicy() {
        return this.crossoverPolicy;
    }

    public double getCrossoverRate() {
        return this.crossoverRate;
    }

    public org.apache.commons.math.genetics.MutationPolicy getMutationPolicy() {
        return this.mutationPolicy;
    }

    public double getMutationRate() {
        return this.mutationRate;
    }

    public org.apache.commons.math.genetics.SelectionPolicy getSelectionPolicy() {
        return this.selectionPolicy;
    }

    public int getGenerationsEvolved() {
        return this.generationsEvolved;
    }
}
