package org.apache.commons.math.util;

/* loaded from: classes3.dex */
public interface DoubleArray {
    void addElement(double d);

    double addElementRolling(double d);

    void clear();

    double getElement(int i);

    double[] getElements();

    int getNumElements();

    void setElement(int i, double d);
}
