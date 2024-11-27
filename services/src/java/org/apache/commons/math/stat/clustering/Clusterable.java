package org.apache.commons.math.stat.clustering;

/* loaded from: classes3.dex */
public interface Clusterable<T> {
    T centroidOf(java.util.Collection<T> collection);

    double distanceFrom(T t);
}
