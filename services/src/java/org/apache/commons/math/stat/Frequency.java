package org.apache.commons.math.stat;

/* loaded from: classes3.dex */
public class Frequency implements java.io.Serializable {
    private static final long serialVersionUID = -3845586908418844111L;
    private final java.util.TreeMap<java.lang.Comparable<?>, java.lang.Long> freqTable;

    public Frequency() {
        this.freqTable = new java.util.TreeMap<>();
    }

    public Frequency(java.util.Comparator<?> comparator) {
        this.freqTable = new java.util.TreeMap<>(comparator);
    }

    public java.lang.String toString() {
        java.text.NumberFormat percentInstance = java.text.NumberFormat.getPercentInstance();
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("Value \t Freq. \t Pct. \t Cum Pct. \n");
        for (java.lang.Comparable<?> comparable : this.freqTable.keySet()) {
            sb.append(comparable);
            sb.append('\t');
            sb.append(getCount(comparable));
            sb.append('\t');
            sb.append(percentInstance.format(getPct(comparable)));
            sb.append('\t');
            sb.append(percentInstance.format(getCumPct(comparable)));
            sb.append('\n');
        }
        return sb.toString();
    }

    @java.lang.Deprecated
    public void addValue(java.lang.Object obj) {
        if (obj instanceof java.lang.Comparable) {
            addValue((java.lang.Comparable<?>) obj);
            return;
        }
        throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.CLASS_DOESNT_IMPLEMENT_COMPARABLE, obj.getClass().getName());
    }

    public void addValue(java.lang.Comparable<?> comparable) {
        java.lang.Comparable<?> comparable2;
        if (!(comparable instanceof java.lang.Integer)) {
            comparable2 = comparable;
        } else {
            comparable2 = java.lang.Long.valueOf(((java.lang.Integer) comparable).longValue());
        }
        try {
            java.lang.Long l = this.freqTable.get(comparable2);
            if (l == null) {
                this.freqTable.put(comparable2, 1L);
            } else {
                this.freqTable.put(comparable2, java.lang.Long.valueOf(l.longValue() + 1));
            }
        } catch (java.lang.ClassCastException e) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.INSTANCES_NOT_COMPARABLE_TO_EXISTING_VALUES, comparable.getClass().getName());
        }
    }

    public void addValue(int i) {
        addValue((java.lang.Comparable<?>) java.lang.Long.valueOf(i));
    }

    @java.lang.Deprecated
    public void addValue(java.lang.Integer num) {
        addValue((java.lang.Comparable<?>) java.lang.Long.valueOf(num.longValue()));
    }

    public void addValue(long j) {
        addValue((java.lang.Comparable<?>) java.lang.Long.valueOf(j));
    }

    public void addValue(char c) {
        addValue((java.lang.Comparable<?>) java.lang.Character.valueOf(c));
    }

    public void clear() {
        this.freqTable.clear();
    }

    public java.util.Iterator<java.lang.Comparable<?>> valuesIterator() {
        return this.freqTable.keySet().iterator();
    }

    public long getSumFreq() {
        java.util.Iterator<java.lang.Long> it = this.freqTable.values().iterator();
        long j = 0;
        while (it.hasNext()) {
            j += it.next().longValue();
        }
        return j;
    }

    @java.lang.Deprecated
    public long getCount(java.lang.Object obj) {
        return getCount((java.lang.Comparable<?>) obj);
    }

    public long getCount(java.lang.Comparable<?> comparable) {
        if (comparable instanceof java.lang.Integer) {
            return getCount(((java.lang.Integer) comparable).longValue());
        }
        try {
            java.lang.Long l = this.freqTable.get(comparable);
            if (l == null) {
                return 0L;
            }
            return l.longValue();
        } catch (java.lang.ClassCastException e) {
            return 0L;
        }
    }

    public long getCount(int i) {
        return getCount((java.lang.Comparable<?>) java.lang.Long.valueOf(i));
    }

    public long getCount(long j) {
        return getCount((java.lang.Comparable<?>) java.lang.Long.valueOf(j));
    }

    public long getCount(char c) {
        return getCount((java.lang.Comparable<?>) java.lang.Character.valueOf(c));
    }

    public int getUniqueCount() {
        return this.freqTable.keySet().size();
    }

    @java.lang.Deprecated
    public double getPct(java.lang.Object obj) {
        return getPct((java.lang.Comparable<?>) obj);
    }

    public double getPct(java.lang.Comparable<?> comparable) {
        long sumFreq = getSumFreq();
        if (sumFreq == 0) {
            return Double.NaN;
        }
        return getCount(comparable) / sumFreq;
    }

    public double getPct(int i) {
        return getPct((java.lang.Comparable<?>) java.lang.Long.valueOf(i));
    }

    public double getPct(long j) {
        return getPct((java.lang.Comparable<?>) java.lang.Long.valueOf(j));
    }

    public double getPct(char c) {
        return getPct((java.lang.Comparable<?>) java.lang.Character.valueOf(c));
    }

    @java.lang.Deprecated
    public long getCumFreq(java.lang.Object obj) {
        return getCumFreq((java.lang.Comparable<?>) obj);
    }

    public long getCumFreq(java.lang.Comparable<?> comparable) {
        long j;
        if (getSumFreq() == 0) {
            return 0L;
        }
        if (comparable instanceof java.lang.Integer) {
            return getCumFreq(((java.lang.Integer) comparable).longValue());
        }
        java.util.Comparator<? super java.lang.Comparable<?>> comparator = this.freqTable.comparator();
        if (comparator == null) {
            comparator = new org.apache.commons.math.stat.Frequency.NaturalComparator<>();
        }
        try {
            java.lang.Long l = this.freqTable.get(comparable);
            if (l == null) {
                j = 0;
            } else {
                j = l.longValue();
            }
            if (comparator.compare(comparable, this.freqTable.firstKey()) < 0) {
                return 0L;
            }
            if (comparator.compare(comparable, this.freqTable.lastKey()) >= 0) {
                return getSumFreq();
            }
            java.util.Iterator<java.lang.Comparable<?>> valuesIterator = valuesIterator();
            while (valuesIterator.hasNext()) {
                java.lang.Comparable<?> next = valuesIterator.next();
                if (comparator.compare(comparable, next) > 0) {
                    j += getCount(next);
                } else {
                    return j;
                }
            }
            return j;
        } catch (java.lang.ClassCastException e) {
            return 0L;
        }
    }

    public long getCumFreq(int i) {
        return getCumFreq((java.lang.Comparable<?>) java.lang.Long.valueOf(i));
    }

    public long getCumFreq(long j) {
        return getCumFreq((java.lang.Comparable<?>) java.lang.Long.valueOf(j));
    }

    public long getCumFreq(char c) {
        return getCumFreq((java.lang.Comparable<?>) java.lang.Character.valueOf(c));
    }

    @java.lang.Deprecated
    public double getCumPct(java.lang.Object obj) {
        return getCumPct((java.lang.Comparable<?>) obj);
    }

    public double getCumPct(java.lang.Comparable<?> comparable) {
        long sumFreq = getSumFreq();
        if (sumFreq == 0) {
            return Double.NaN;
        }
        return getCumFreq(comparable) / sumFreq;
    }

    public double getCumPct(int i) {
        return getCumPct((java.lang.Comparable<?>) java.lang.Long.valueOf(i));
    }

    public double getCumPct(long j) {
        return getCumPct((java.lang.Comparable<?>) java.lang.Long.valueOf(j));
    }

    public double getCumPct(char c) {
        return getCumPct((java.lang.Comparable<?>) java.lang.Character.valueOf(c));
    }

    private static class NaturalComparator<T extends java.lang.Comparable<T>> implements java.util.Comparator<java.lang.Comparable<T>>, java.io.Serializable {
        private static final long serialVersionUID = -3852193713161395148L;

        private NaturalComparator() {
        }

        @Override // java.util.Comparator
        public int compare(java.lang.Comparable<T> comparable, java.lang.Comparable<T> comparable2) {
            return comparable.compareTo(comparable2);
        }
    }

    public int hashCode() {
        return 31 + (this.freqTable == null ? 0 : this.freqTable.hashCode());
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof org.apache.commons.math.stat.Frequency)) {
            return false;
        }
        org.apache.commons.math.stat.Frequency frequency = (org.apache.commons.math.stat.Frequency) obj;
        if (this.freqTable == null) {
            if (frequency.freqTable != null) {
                return false;
            }
        } else if (!this.freqTable.equals(frequency.freqTable)) {
            return false;
        }
        return true;
    }
}
