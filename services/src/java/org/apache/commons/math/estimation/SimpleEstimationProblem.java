package org.apache.commons.math.estimation;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class SimpleEstimationProblem implements org.apache.commons.math.estimation.EstimationProblem {
    private final java.util.List<org.apache.commons.math.estimation.EstimatedParameter> parameters = new java.util.ArrayList();
    private final java.util.List<org.apache.commons.math.estimation.WeightedMeasurement> measurements = new java.util.ArrayList();

    @Override // org.apache.commons.math.estimation.EstimationProblem
    public org.apache.commons.math.estimation.EstimatedParameter[] getAllParameters() {
        return (org.apache.commons.math.estimation.EstimatedParameter[]) this.parameters.toArray(new org.apache.commons.math.estimation.EstimatedParameter[this.parameters.size()]);
    }

    @Override // org.apache.commons.math.estimation.EstimationProblem
    public org.apache.commons.math.estimation.EstimatedParameter[] getUnboundParameters() {
        java.util.ArrayList arrayList = new java.util.ArrayList(this.parameters.size());
        for (org.apache.commons.math.estimation.EstimatedParameter estimatedParameter : this.parameters) {
            if (!estimatedParameter.isBound()) {
                arrayList.add(estimatedParameter);
            }
        }
        return (org.apache.commons.math.estimation.EstimatedParameter[]) arrayList.toArray(new org.apache.commons.math.estimation.EstimatedParameter[arrayList.size()]);
    }

    @Override // org.apache.commons.math.estimation.EstimationProblem
    public org.apache.commons.math.estimation.WeightedMeasurement[] getMeasurements() {
        return (org.apache.commons.math.estimation.WeightedMeasurement[]) this.measurements.toArray(new org.apache.commons.math.estimation.WeightedMeasurement[this.measurements.size()]);
    }

    protected void addParameter(org.apache.commons.math.estimation.EstimatedParameter estimatedParameter) {
        this.parameters.add(estimatedParameter);
    }

    protected void addMeasurement(org.apache.commons.math.estimation.WeightedMeasurement weightedMeasurement) {
        this.measurements.add(weightedMeasurement);
    }
}
