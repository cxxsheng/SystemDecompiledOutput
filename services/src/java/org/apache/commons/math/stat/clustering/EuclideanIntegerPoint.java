package org.apache.commons.math.stat.clustering;

/* loaded from: classes3.dex */
public class EuclideanIntegerPoint implements org.apache.commons.math.stat.clustering.Clusterable<org.apache.commons.math.stat.clustering.EuclideanIntegerPoint>, java.io.Serializable {
    private static final long serialVersionUID = 3946024775784901369L;
    private final int[] point;

    public EuclideanIntegerPoint(int[] iArr) {
        this.point = iArr;
    }

    public int[] getPoint() {
        return this.point;
    }

    @Override // org.apache.commons.math.stat.clustering.Clusterable
    public double distanceFrom(org.apache.commons.math.stat.clustering.EuclideanIntegerPoint euclideanIntegerPoint) {
        return org.apache.commons.math.util.MathUtils.distance(this.point, euclideanIntegerPoint.getPoint());
    }

    @Override // org.apache.commons.math.stat.clustering.Clusterable
    public org.apache.commons.math.stat.clustering.EuclideanIntegerPoint centroidOf(java.util.Collection<org.apache.commons.math.stat.clustering.EuclideanIntegerPoint> collection) {
        int i;
        int length = getPoint().length;
        int[] iArr = new int[length];
        java.util.Iterator<org.apache.commons.math.stat.clustering.EuclideanIntegerPoint> it = collection.iterator();
        while (true) {
            i = 0;
            if (!it.hasNext()) {
                break;
            }
            org.apache.commons.math.stat.clustering.EuclideanIntegerPoint next = it.next();
            while (i < length) {
                iArr[i] = iArr[i] + next.getPoint()[i];
                i++;
            }
        }
        while (i < length) {
            iArr[i] = iArr[i] / collection.size();
            i++;
        }
        return new org.apache.commons.math.stat.clustering.EuclideanIntegerPoint(iArr);
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof org.apache.commons.math.stat.clustering.EuclideanIntegerPoint)) {
            return false;
        }
        int[] point = ((org.apache.commons.math.stat.clustering.EuclideanIntegerPoint) obj).getPoint();
        if (this.point.length != point.length) {
            return false;
        }
        for (int i = 0; i < this.point.length; i++) {
            if (this.point[i] != point[i]) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int i = 0;
        for (int i2 : this.point) {
            i += (java.lang.Integer.valueOf(i2).hashCode() * 13) + 7;
        }
        return i;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("(");
        int[] point = getPoint();
        for (int i = 0; i < point.length; i++) {
            sb.append(point[i]);
            if (i < point.length - 1) {
                sb.append(",");
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
