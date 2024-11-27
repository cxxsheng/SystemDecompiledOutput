package org.apache.commons.math.stat.inference;

/* loaded from: classes3.dex */
public interface OneWayAnova {
    double anovaFValue(java.util.Collection<double[]> collection) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException;

    double anovaPValue(java.util.Collection<double[]> collection) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException;

    boolean anovaTest(java.util.Collection<double[]> collection, double d) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException;
}
