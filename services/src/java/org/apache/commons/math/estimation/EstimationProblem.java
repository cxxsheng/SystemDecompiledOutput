package org.apache.commons.math.estimation;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public interface EstimationProblem {
    org.apache.commons.math.estimation.EstimatedParameter[] getAllParameters();

    org.apache.commons.math.estimation.WeightedMeasurement[] getMeasurements();

    org.apache.commons.math.estimation.EstimatedParameter[] getUnboundParameters();
}
