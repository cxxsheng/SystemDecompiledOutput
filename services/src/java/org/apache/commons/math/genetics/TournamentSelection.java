package org.apache.commons.math.genetics;

/* loaded from: classes3.dex */
public class TournamentSelection implements org.apache.commons.math.genetics.SelectionPolicy {
    private int arity;

    public TournamentSelection(int i) {
        this.arity = i;
    }

    @Override // org.apache.commons.math.genetics.SelectionPolicy
    public org.apache.commons.math.genetics.ChromosomePair select(org.apache.commons.math.genetics.Population population) {
        org.apache.commons.math.genetics.ListPopulation listPopulation = (org.apache.commons.math.genetics.ListPopulation) population;
        return new org.apache.commons.math.genetics.ChromosomePair(tournament(listPopulation), tournament(listPopulation));
    }

    private org.apache.commons.math.genetics.Chromosome tournament(org.apache.commons.math.genetics.ListPopulation listPopulation) {
        if (listPopulation.getPopulationSize() < this.arity) {
            throw new java.lang.IllegalArgumentException("Tournament arity cannot be bigger than population size.");
        }
        org.apache.commons.math.genetics.ListPopulation listPopulation2 = new org.apache.commons.math.genetics.ListPopulation(this.arity) { // from class: org.apache.commons.math.genetics.TournamentSelection.1
            @Override // org.apache.commons.math.genetics.Population
            public org.apache.commons.math.genetics.Population nextGeneration() {
                return null;
            }
        };
        java.util.ArrayList arrayList = new java.util.ArrayList(listPopulation.getChromosomes());
        for (int i = 0; i < this.arity; i++) {
            int nextInt = org.apache.commons.math.genetics.GeneticAlgorithm.getRandomGenerator().nextInt(arrayList.size());
            listPopulation2.addChromosome((org.apache.commons.math.genetics.Chromosome) arrayList.get(nextInt));
            arrayList.remove(nextInt);
        }
        return listPopulation2.getFittestChromosome();
    }

    public int getArity() {
        return this.arity;
    }

    public void setArity(int i) {
        this.arity = i;
    }
}
