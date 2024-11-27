package org.apache.commons.math.stat.ranking;

/* loaded from: classes3.dex */
public class NaturalRanking implements org.apache.commons.math.stat.ranking.RankingAlgorithm {
    public static final org.apache.commons.math.stat.ranking.NaNStrategy DEFAULT_NAN_STRATEGY = org.apache.commons.math.stat.ranking.NaNStrategy.MAXIMAL;
    public static final org.apache.commons.math.stat.ranking.TiesStrategy DEFAULT_TIES_STRATEGY = org.apache.commons.math.stat.ranking.TiesStrategy.AVERAGE;
    private final org.apache.commons.math.stat.ranking.NaNStrategy nanStrategy;
    private final org.apache.commons.math.random.RandomData randomData;
    private final org.apache.commons.math.stat.ranking.TiesStrategy tiesStrategy;

    public NaturalRanking() {
        this.tiesStrategy = DEFAULT_TIES_STRATEGY;
        this.nanStrategy = DEFAULT_NAN_STRATEGY;
        this.randomData = null;
    }

    public NaturalRanking(org.apache.commons.math.stat.ranking.TiesStrategy tiesStrategy) {
        this.tiesStrategy = tiesStrategy;
        this.nanStrategy = DEFAULT_NAN_STRATEGY;
        this.randomData = new org.apache.commons.math.random.RandomDataImpl();
    }

    public NaturalRanking(org.apache.commons.math.stat.ranking.NaNStrategy naNStrategy) {
        this.nanStrategy = naNStrategy;
        this.tiesStrategy = DEFAULT_TIES_STRATEGY;
        this.randomData = null;
    }

    public NaturalRanking(org.apache.commons.math.stat.ranking.NaNStrategy naNStrategy, org.apache.commons.math.stat.ranking.TiesStrategy tiesStrategy) {
        this.nanStrategy = naNStrategy;
        this.tiesStrategy = tiesStrategy;
        this.randomData = new org.apache.commons.math.random.RandomDataImpl();
    }

    public NaturalRanking(org.apache.commons.math.random.RandomGenerator randomGenerator) {
        this.tiesStrategy = org.apache.commons.math.stat.ranking.TiesStrategy.RANDOM;
        this.nanStrategy = DEFAULT_NAN_STRATEGY;
        this.randomData = new org.apache.commons.math.random.RandomDataImpl(randomGenerator);
    }

    public NaturalRanking(org.apache.commons.math.stat.ranking.NaNStrategy naNStrategy, org.apache.commons.math.random.RandomGenerator randomGenerator) {
        this.nanStrategy = naNStrategy;
        this.tiesStrategy = org.apache.commons.math.stat.ranking.TiesStrategy.RANDOM;
        this.randomData = new org.apache.commons.math.random.RandomDataImpl(randomGenerator);
    }

    public org.apache.commons.math.stat.ranking.NaNStrategy getNanStrategy() {
        return this.nanStrategy;
    }

    public org.apache.commons.math.stat.ranking.TiesStrategy getTiesStrategy() {
        return this.tiesStrategy;
    }

    @Override // org.apache.commons.math.stat.ranking.RankingAlgorithm
    public double[] rank(double[] dArr) {
        org.apache.commons.math.stat.ranking.NaturalRanking.IntDoublePair[] intDoublePairArr = new org.apache.commons.math.stat.ranking.NaturalRanking.IntDoublePair[dArr.length];
        for (int i = 0; i < dArr.length; i++) {
            intDoublePairArr[i] = new org.apache.commons.math.stat.ranking.NaturalRanking.IntDoublePair(dArr[i], i);
        }
        java.util.List<java.lang.Integer> list = null;
        switch (org.apache.commons.math.stat.ranking.NaturalRanking.AnonymousClass1.$SwitchMap$org$apache$commons$math$stat$ranking$NaNStrategy[this.nanStrategy.ordinal()]) {
            case 1:
                recodeNaNs(intDoublePairArr, Double.POSITIVE_INFINITY);
                break;
            case 2:
                recodeNaNs(intDoublePairArr, Double.NEGATIVE_INFINITY);
                break;
            case 3:
                intDoublePairArr = removeNaNs(intDoublePairArr);
                break;
            case 4:
                list = getNanPositions(intDoublePairArr);
                break;
            default:
                throw new org.apache.commons.math.exception.MathInternalError();
        }
        java.util.Arrays.sort(intDoublePairArr);
        double[] dArr2 = new double[intDoublePairArr.length];
        dArr2[intDoublePairArr[0].getPosition()] = 1;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add(java.lang.Integer.valueOf(intDoublePairArr[0].getPosition()));
        int i2 = 1;
        for (int i3 = 1; i3 < intDoublePairArr.length; i3++) {
            if (java.lang.Double.compare(intDoublePairArr[i3].getValue(), intDoublePairArr[i3 - 1].getValue()) > 0) {
                i2 = i3 + 1;
                if (arrayList.size() > 1) {
                    resolveTie(dArr2, arrayList);
                }
                arrayList = new java.util.ArrayList();
                arrayList.add(java.lang.Integer.valueOf(intDoublePairArr[i3].getPosition()));
            } else {
                arrayList.add(java.lang.Integer.valueOf(intDoublePairArr[i3].getPosition()));
            }
            dArr2[intDoublePairArr[i3].getPosition()] = i2;
        }
        if (arrayList.size() > 1) {
            resolveTie(dArr2, arrayList);
        }
        if (this.nanStrategy == org.apache.commons.math.stat.ranking.NaNStrategy.FIXED) {
            restoreNaNs(dArr2, list);
        }
        return dArr2;
    }

    private org.apache.commons.math.stat.ranking.NaturalRanking.IntDoublePair[] removeNaNs(org.apache.commons.math.stat.ranking.NaturalRanking.IntDoublePair[] intDoublePairArr) {
        if (!containsNaNs(intDoublePairArr)) {
            return intDoublePairArr;
        }
        org.apache.commons.math.stat.ranking.NaturalRanking.IntDoublePair[] intDoublePairArr2 = new org.apache.commons.math.stat.ranking.NaturalRanking.IntDoublePair[intDoublePairArr.length];
        int i = 0;
        for (int i2 = 0; i2 < intDoublePairArr.length; i2++) {
            if (java.lang.Double.isNaN(intDoublePairArr[i2].getValue())) {
                for (int i3 = i2 + 1; i3 < intDoublePairArr.length; i3++) {
                    intDoublePairArr[i3] = new org.apache.commons.math.stat.ranking.NaturalRanking.IntDoublePair(intDoublePairArr[i3].getValue(), intDoublePairArr[i3].getPosition() - 1);
                }
            } else {
                intDoublePairArr2[i] = new org.apache.commons.math.stat.ranking.NaturalRanking.IntDoublePair(intDoublePairArr[i2].getValue(), intDoublePairArr[i2].getPosition());
                i++;
            }
        }
        org.apache.commons.math.stat.ranking.NaturalRanking.IntDoublePair[] intDoublePairArr3 = new org.apache.commons.math.stat.ranking.NaturalRanking.IntDoublePair[i];
        java.lang.System.arraycopy(intDoublePairArr2, 0, intDoublePairArr3, 0, i);
        return intDoublePairArr3;
    }

    private void recodeNaNs(org.apache.commons.math.stat.ranking.NaturalRanking.IntDoublePair[] intDoublePairArr, double d) {
        for (int i = 0; i < intDoublePairArr.length; i++) {
            if (java.lang.Double.isNaN(intDoublePairArr[i].getValue())) {
                intDoublePairArr[i] = new org.apache.commons.math.stat.ranking.NaturalRanking.IntDoublePair(d, intDoublePairArr[i].getPosition());
            }
        }
    }

    private boolean containsNaNs(org.apache.commons.math.stat.ranking.NaturalRanking.IntDoublePair[] intDoublePairArr) {
        for (org.apache.commons.math.stat.ranking.NaturalRanking.IntDoublePair intDoublePair : intDoublePairArr) {
            if (java.lang.Double.isNaN(intDoublePair.getValue())) {
                return true;
            }
        }
        return false;
    }

    private void resolveTie(double[] dArr, java.util.List<java.lang.Integer> list) {
        int i = 0;
        double d = dArr[list.get(0).intValue()];
        int size = list.size();
        switch (org.apache.commons.math.stat.ranking.NaturalRanking.AnonymousClass1.$SwitchMap$org$apache$commons$math$stat$ranking$TiesStrategy[this.tiesStrategy.ordinal()]) {
            case 1:
                fill(dArr, list, (((d * 2.0d) + size) - 1.0d) / 2.0d);
                return;
            case 2:
                fill(dArr, list, (d + size) - 1.0d);
                return;
            case 3:
                fill(dArr, list, d);
                return;
            case 4:
                java.util.Iterator<java.lang.Integer> it = list.iterator();
                long round = org.apache.commons.math.util.FastMath.round(d);
                while (it.hasNext()) {
                    dArr[it.next().intValue()] = this.randomData.nextLong(round, (size + round) - 1);
                }
                return;
            case 5:
                java.util.Iterator<java.lang.Integer> it2 = list.iterator();
                long round2 = org.apache.commons.math.util.FastMath.round(d);
                while (it2.hasNext()) {
                    dArr[it2.next().intValue()] = i + round2;
                    i++;
                }
                return;
            default:
                throw new org.apache.commons.math.exception.MathInternalError();
        }
    }

    /* renamed from: org.apache.commons.math.stat.ranking.NaturalRanking$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$apache$commons$math$stat$ranking$NaNStrategy;
        static final /* synthetic */ int[] $SwitchMap$org$apache$commons$math$stat$ranking$TiesStrategy = new int[org.apache.commons.math.stat.ranking.TiesStrategy.values().length];

        static {
            try {
                $SwitchMap$org$apache$commons$math$stat$ranking$TiesStrategy[org.apache.commons.math.stat.ranking.TiesStrategy.AVERAGE.ordinal()] = 1;
            } catch (java.lang.NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$apache$commons$math$stat$ranking$TiesStrategy[org.apache.commons.math.stat.ranking.TiesStrategy.MAXIMUM.ordinal()] = 2;
            } catch (java.lang.NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$org$apache$commons$math$stat$ranking$TiesStrategy[org.apache.commons.math.stat.ranking.TiesStrategy.MINIMUM.ordinal()] = 3;
            } catch (java.lang.NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$org$apache$commons$math$stat$ranking$TiesStrategy[org.apache.commons.math.stat.ranking.TiesStrategy.RANDOM.ordinal()] = 4;
            } catch (java.lang.NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$org$apache$commons$math$stat$ranking$TiesStrategy[org.apache.commons.math.stat.ranking.TiesStrategy.SEQUENTIAL.ordinal()] = 5;
            } catch (java.lang.NoSuchFieldError e5) {
            }
            $SwitchMap$org$apache$commons$math$stat$ranking$NaNStrategy = new int[org.apache.commons.math.stat.ranking.NaNStrategy.values().length];
            try {
                $SwitchMap$org$apache$commons$math$stat$ranking$NaNStrategy[org.apache.commons.math.stat.ranking.NaNStrategy.MAXIMAL.ordinal()] = 1;
            } catch (java.lang.NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$org$apache$commons$math$stat$ranking$NaNStrategy[org.apache.commons.math.stat.ranking.NaNStrategy.MINIMAL.ordinal()] = 2;
            } catch (java.lang.NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$org$apache$commons$math$stat$ranking$NaNStrategy[org.apache.commons.math.stat.ranking.NaNStrategy.REMOVED.ordinal()] = 3;
            } catch (java.lang.NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$org$apache$commons$math$stat$ranking$NaNStrategy[org.apache.commons.math.stat.ranking.NaNStrategy.FIXED.ordinal()] = 4;
            } catch (java.lang.NoSuchFieldError e9) {
            }
        }
    }

    private void fill(double[] dArr, java.util.List<java.lang.Integer> list, double d) {
        java.util.Iterator<java.lang.Integer> it = list.iterator();
        while (it.hasNext()) {
            dArr[it.next().intValue()] = d;
        }
    }

    private void restoreNaNs(double[] dArr, java.util.List<java.lang.Integer> list) {
        if (list.size() == 0) {
            return;
        }
        java.util.Iterator<java.lang.Integer> it = list.iterator();
        while (it.hasNext()) {
            dArr[it.next().intValue()] = Double.NaN;
        }
    }

    private java.util.List<java.lang.Integer> getNanPositions(org.apache.commons.math.stat.ranking.NaturalRanking.IntDoublePair[] intDoublePairArr) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i = 0; i < intDoublePairArr.length; i++) {
            if (java.lang.Double.isNaN(intDoublePairArr[i].getValue())) {
                arrayList.add(java.lang.Integer.valueOf(i));
            }
        }
        return arrayList;
    }

    private static class IntDoublePair implements java.lang.Comparable<org.apache.commons.math.stat.ranking.NaturalRanking.IntDoublePair> {
        private final int position;
        private final double value;

        public IntDoublePair(double d, int i) {
            this.value = d;
            this.position = i;
        }

        @Override // java.lang.Comparable
        public int compareTo(org.apache.commons.math.stat.ranking.NaturalRanking.IntDoublePair intDoublePair) {
            return java.lang.Double.compare(this.value, intDoublePair.value);
        }

        public double getValue() {
            return this.value;
        }

        public int getPosition() {
            return this.position;
        }
    }
}
