package org.apache.commons.math.estimation;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public abstract class WeightedMeasurement implements java.io.Serializable {
    private static final long serialVersionUID = 4360046376796901941L;
    private boolean ignored;
    private final double measuredValue;
    private final double weight;

    public abstract double getPartial(org.apache.commons.math.estimation.EstimatedParameter estimatedParameter);

    public abstract double getTheoreticalValue();

    public WeightedMeasurement(double d, double d2) {
        this.weight = d;
        this.measuredValue = d2;
        this.ignored = false;
    }

    public WeightedMeasurement(double d, double d2, boolean z) {
        this.weight = d;
        this.measuredValue = d2;
        this.ignored = z;
    }

    public double getWeight() {
        return this.weight;
    }

    public double getMeasuredValue() {
        return this.measuredValue;
    }

    public double getResidual() {
        return this.measuredValue - getTheoreticalValue();
    }

    public void setIgnored(boolean z) {
        this.ignored = z;
    }

    public boolean isIgnored() {
        return this.ignored;
    }
}
