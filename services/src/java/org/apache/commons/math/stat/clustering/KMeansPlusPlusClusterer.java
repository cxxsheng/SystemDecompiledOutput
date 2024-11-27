package org.apache.commons.math.stat.clustering;

/* loaded from: classes3.dex */
public class KMeansPlusPlusClusterer<T extends org.apache.commons.math.stat.clustering.Clusterable<T>> {
    private final org.apache.commons.math.stat.clustering.KMeansPlusPlusClusterer.EmptyClusterStrategy emptyStrategy;
    private final java.util.Random random;

    public enum EmptyClusterStrategy {
        LARGEST_VARIANCE,
        LARGEST_POINTS_NUMBER,
        FARTHEST_POINT,
        ERROR
    }

    public KMeansPlusPlusClusterer(java.util.Random random) {
        this(random, org.apache.commons.math.stat.clustering.KMeansPlusPlusClusterer.EmptyClusterStrategy.LARGEST_VARIANCE);
    }

    public KMeansPlusPlusClusterer(java.util.Random random, org.apache.commons.math.stat.clustering.KMeansPlusPlusClusterer.EmptyClusterStrategy emptyClusterStrategy) {
        this.random = random;
        this.emptyStrategy = emptyClusterStrategy;
    }

    public java.util.List<org.apache.commons.math.stat.clustering.Cluster<T>> cluster(java.util.Collection<T> collection, int i, int i2) {
        org.apache.commons.math.stat.clustering.Clusterable clusterable;
        java.util.List<org.apache.commons.math.stat.clustering.Cluster<T>> chooseInitialCenters = chooseInitialCenters(collection, i, this.random);
        assignPointsToClusters(chooseInitialCenters, collection);
        if (i2 < 0) {
            i2 = Integer.MAX_VALUE;
        }
        int i3 = 0;
        while (i3 < i2) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            boolean z = false;
            for (org.apache.commons.math.stat.clustering.Cluster<T> cluster : chooseInitialCenters) {
                boolean z2 = true;
                if (cluster.getPoints().isEmpty()) {
                    switch (org.apache.commons.math.stat.clustering.KMeansPlusPlusClusterer.AnonymousClass1.$SwitchMap$org$apache$commons$math$stat$clustering$KMeansPlusPlusClusterer$EmptyClusterStrategy[this.emptyStrategy.ordinal()]) {
                        case 1:
                            clusterable = getPointFromLargestVarianceCluster(chooseInitialCenters);
                            break;
                        case 2:
                            clusterable = getPointFromLargestNumberCluster(chooseInitialCenters);
                            break;
                        case 3:
                            clusterable = getFarthestPoint(chooseInitialCenters);
                            break;
                        default:
                            throw new org.apache.commons.math.exception.ConvergenceException(org.apache.commons.math.exception.util.LocalizedFormats.EMPTY_CLUSTER_IN_K_MEANS);
                    }
                } else {
                    org.apache.commons.math.stat.clustering.Clusterable clusterable2 = (org.apache.commons.math.stat.clustering.Clusterable) cluster.getCenter().centroidOf(cluster.getPoints());
                    if (clusterable2.equals(cluster.getCenter())) {
                        z2 = z;
                        clusterable = clusterable2;
                    } else {
                        clusterable = clusterable2;
                    }
                }
                arrayList.add(new org.apache.commons.math.stat.clustering.Cluster<>(clusterable));
                z = z2;
            }
            if (!z) {
                return chooseInitialCenters;
            }
            assignPointsToClusters(arrayList, collection);
            i3++;
            chooseInitialCenters = arrayList;
        }
        return chooseInitialCenters;
    }

    /* renamed from: org.apache.commons.math.stat.clustering.KMeansPlusPlusClusterer$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$apache$commons$math$stat$clustering$KMeansPlusPlusClusterer$EmptyClusterStrategy = new int[org.apache.commons.math.stat.clustering.KMeansPlusPlusClusterer.EmptyClusterStrategy.values().length];

        static {
            try {
                $SwitchMap$org$apache$commons$math$stat$clustering$KMeansPlusPlusClusterer$EmptyClusterStrategy[org.apache.commons.math.stat.clustering.KMeansPlusPlusClusterer.EmptyClusterStrategy.LARGEST_VARIANCE.ordinal()] = 1;
            } catch (java.lang.NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$apache$commons$math$stat$clustering$KMeansPlusPlusClusterer$EmptyClusterStrategy[org.apache.commons.math.stat.clustering.KMeansPlusPlusClusterer.EmptyClusterStrategy.LARGEST_POINTS_NUMBER.ordinal()] = 2;
            } catch (java.lang.NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$org$apache$commons$math$stat$clustering$KMeansPlusPlusClusterer$EmptyClusterStrategy[org.apache.commons.math.stat.clustering.KMeansPlusPlusClusterer.EmptyClusterStrategy.FARTHEST_POINT.ordinal()] = 3;
            } catch (java.lang.NoSuchFieldError e3) {
            }
        }
    }

    private static <T extends org.apache.commons.math.stat.clustering.Clusterable<T>> void assignPointsToClusters(java.util.Collection<org.apache.commons.math.stat.clustering.Cluster<T>> collection, java.util.Collection<T> collection2) {
        for (T t : collection2) {
            getNearestCluster(collection, t).addPoint(t);
        }
    }

    private static <T extends org.apache.commons.math.stat.clustering.Clusterable<T>> java.util.List<org.apache.commons.math.stat.clustering.Cluster<T>> chooseInitialCenters(java.util.Collection<T> collection, int i, java.util.Random random) {
        java.util.ArrayList arrayList = new java.util.ArrayList(collection);
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        arrayList2.add(new org.apache.commons.math.stat.clustering.Cluster((org.apache.commons.math.stat.clustering.Clusterable) arrayList.remove(random.nextInt(arrayList.size()))));
        int size = arrayList.size();
        double[] dArr = new double[size];
        while (arrayList2.size() < i) {
            int i2 = 0;
            int i3 = 0;
            for (int i4 = 0; i4 < arrayList.size(); i4++) {
                org.apache.commons.math.stat.clustering.Clusterable clusterable = (org.apache.commons.math.stat.clustering.Clusterable) arrayList.get(i4);
                double distanceFrom = clusterable.distanceFrom(getNearestCluster(arrayList2, clusterable).getCenter());
                i3 = (int) (i3 + (distanceFrom * distanceFrom));
                dArr[i4] = i3;
            }
            double nextDouble = random.nextDouble() * i3;
            while (true) {
                if (i2 >= size) {
                    break;
                }
                if (dArr[i2] < nextDouble) {
                    i2++;
                } else {
                    arrayList2.add(new org.apache.commons.math.stat.clustering.Cluster((org.apache.commons.math.stat.clustering.Clusterable) arrayList.remove(i2)));
                    break;
                }
            }
        }
        return arrayList2;
    }

    private T getPointFromLargestVarianceCluster(java.util.Collection<org.apache.commons.math.stat.clustering.Cluster<T>> collection) {
        double d = Double.NEGATIVE_INFINITY;
        org.apache.commons.math.stat.clustering.Cluster<T> cluster = null;
        for (org.apache.commons.math.stat.clustering.Cluster<T> cluster2 : collection) {
            if (!cluster2.getPoints().isEmpty()) {
                T center = cluster2.getCenter();
                org.apache.commons.math.stat.descriptive.moment.Variance variance = new org.apache.commons.math.stat.descriptive.moment.Variance();
                java.util.Iterator<T> it = cluster2.getPoints().iterator();
                while (it.hasNext()) {
                    variance.increment(it.next().distanceFrom(center));
                }
                double result = variance.getResult();
                if (result > d) {
                    cluster = cluster2;
                    d = result;
                }
            }
        }
        if (cluster == null) {
            throw new org.apache.commons.math.exception.ConvergenceException(org.apache.commons.math.exception.util.LocalizedFormats.EMPTY_CLUSTER_IN_K_MEANS);
        }
        java.util.List<T> points = cluster.getPoints();
        return points.remove(this.random.nextInt(points.size()));
    }

    private T getPointFromLargestNumberCluster(java.util.Collection<org.apache.commons.math.stat.clustering.Cluster<T>> collection) {
        int i = 0;
        org.apache.commons.math.stat.clustering.Cluster<T> cluster = null;
        for (org.apache.commons.math.stat.clustering.Cluster<T> cluster2 : collection) {
            int size = cluster2.getPoints().size();
            if (size > i) {
                cluster = cluster2;
                i = size;
            }
        }
        if (cluster == null) {
            throw new org.apache.commons.math.exception.ConvergenceException(org.apache.commons.math.exception.util.LocalizedFormats.EMPTY_CLUSTER_IN_K_MEANS);
        }
        java.util.List<T> points = cluster.getPoints();
        return points.remove(this.random.nextInt(points.size()));
    }

    private T getFarthestPoint(java.util.Collection<org.apache.commons.math.stat.clustering.Cluster<T>> collection) {
        double d = Double.NEGATIVE_INFINITY;
        org.apache.commons.math.stat.clustering.Cluster<T> cluster = null;
        int i = -1;
        for (org.apache.commons.math.stat.clustering.Cluster<T> cluster2 : collection) {
            T center = cluster2.getCenter();
            java.util.List<T> points = cluster2.getPoints();
            for (int i2 = 0; i2 < points.size(); i2++) {
                double distanceFrom = points.get(i2).distanceFrom(center);
                if (distanceFrom > d) {
                    cluster = cluster2;
                    i = i2;
                    d = distanceFrom;
                }
            }
        }
        if (cluster == null) {
            throw new org.apache.commons.math.exception.ConvergenceException(org.apache.commons.math.exception.util.LocalizedFormats.EMPTY_CLUSTER_IN_K_MEANS);
        }
        return cluster.getPoints().remove(i);
    }

    private static <T extends org.apache.commons.math.stat.clustering.Clusterable<T>> org.apache.commons.math.stat.clustering.Cluster<T> getNearestCluster(java.util.Collection<org.apache.commons.math.stat.clustering.Cluster<T>> collection, T t) {
        double d = Double.MAX_VALUE;
        org.apache.commons.math.stat.clustering.Cluster<T> cluster = null;
        for (org.apache.commons.math.stat.clustering.Cluster<T> cluster2 : collection) {
            double distanceFrom = t.distanceFrom(cluster2.getCenter());
            if (distanceFrom < d) {
                cluster = cluster2;
                d = distanceFrom;
            }
        }
        return cluster;
    }
}
