package org.apache.commons.math.estimation;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class EstimatedParameter implements java.io.Serializable {
    private static final long serialVersionUID = -555440800213416949L;
    private boolean bound;
    protected double estimate;
    private final java.lang.String name;

    public EstimatedParameter(java.lang.String str, double d) {
        this.name = str;
        this.estimate = d;
        this.bound = false;
    }

    public EstimatedParameter(java.lang.String str, double d, boolean z) {
        this.name = str;
        this.estimate = d;
        this.bound = z;
    }

    public EstimatedParameter(org.apache.commons.math.estimation.EstimatedParameter estimatedParameter) {
        this.name = estimatedParameter.name;
        this.estimate = estimatedParameter.estimate;
        this.bound = estimatedParameter.bound;
    }

    public void setEstimate(double d) {
        this.estimate = d;
    }

    public double getEstimate() {
        return this.estimate;
    }

    public java.lang.String getName() {
        return this.name;
    }

    public void setBound(boolean z) {
        this.bound = z;
    }

    public boolean isBound() {
        return this.bound;
    }
}
