package org.apache.commons.math.estimation;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public interface Estimator {
    void estimate(org.apache.commons.math.estimation.EstimationProblem estimationProblem) throws org.apache.commons.math.estimation.EstimationException;

    double[][] getCovariances(org.apache.commons.math.estimation.EstimationProblem estimationProblem) throws org.apache.commons.math.estimation.EstimationException;

    double getRMS(org.apache.commons.math.estimation.EstimationProblem estimationProblem);

    double[] guessParametersErrors(org.apache.commons.math.estimation.EstimationProblem estimationProblem) throws org.apache.commons.math.estimation.EstimationException;
}
