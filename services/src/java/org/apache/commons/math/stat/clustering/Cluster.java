package org.apache.commons.math.stat.clustering;

/* loaded from: classes3.dex */
public class Cluster<T extends org.apache.commons.math.stat.clustering.Clusterable<T>> implements java.io.Serializable {
    private static final long serialVersionUID = -3442297081515880464L;
    private final T center;
    private final java.util.List<T> points = new java.util.ArrayList();

    public Cluster(T t) {
        this.center = t;
    }

    public void addPoint(T t) {
        this.points.add(t);
    }

    public java.util.List<T> getPoints() {
        return this.points;
    }

    public T getCenter() {
        return this.center;
    }
}
