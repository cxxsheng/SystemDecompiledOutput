package com.android.server.backup.transport;

/* loaded from: classes.dex */
public class TransportStats {
    private final java.lang.Object mStatsLock = new java.lang.Object();
    private final java.util.Map<android.content.ComponentName, com.android.server.backup.transport.TransportStats.Stats> mTransportStats = new java.util.HashMap();

    void registerConnectionTime(android.content.ComponentName componentName, long j) {
        synchronized (this.mStatsLock) {
            try {
                com.android.server.backup.transport.TransportStats.Stats stats = this.mTransportStats.get(componentName);
                if (stats == null) {
                    stats = new com.android.server.backup.transport.TransportStats.Stats();
                    this.mTransportStats.put(componentName, stats);
                }
                stats.register(j);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.Nullable
    public com.android.server.backup.transport.TransportStats.Stats getStatsForTransport(android.content.ComponentName componentName) {
        synchronized (this.mStatsLock) {
            try {
                com.android.server.backup.transport.TransportStats.Stats stats = this.mTransportStats.get(componentName);
                if (stats == null) {
                    return null;
                }
                return new com.android.server.backup.transport.TransportStats.Stats(stats);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void dump(java.io.PrintWriter printWriter) {
        synchronized (this.mStatsLock) {
            try {
                java.util.Optional<com.android.server.backup.transport.TransportStats.Stats> reduce = this.mTransportStats.values().stream().reduce(new java.util.function.BinaryOperator() { // from class: com.android.server.backup.transport.TransportStats$$ExternalSyntheticLambda0
                    @Override // java.util.function.BiFunction
                    public final java.lang.Object apply(java.lang.Object obj, java.lang.Object obj2) {
                        return com.android.server.backup.transport.TransportStats.Stats.merge((com.android.server.backup.transport.TransportStats.Stats) obj, (com.android.server.backup.transport.TransportStats.Stats) obj2);
                    }
                });
                if (reduce.isPresent()) {
                    dumpStats(printWriter, "", reduce.get());
                }
                if (!this.mTransportStats.isEmpty()) {
                    printWriter.println("Per transport:");
                    for (android.content.ComponentName componentName : this.mTransportStats.keySet()) {
                        com.android.server.backup.transport.TransportStats.Stats stats = this.mTransportStats.get(componentName);
                        printWriter.println("    " + componentName.flattenToShortString());
                        dumpStats(printWriter, "        ", stats);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private static void dumpStats(java.io.PrintWriter printWriter, java.lang.String str, com.android.server.backup.transport.TransportStats.Stats stats) {
        printWriter.println(java.lang.String.format(java.util.Locale.US, "%sAverage connection time: %.2f ms", str, java.lang.Double.valueOf(stats.average)));
        printWriter.println(java.lang.String.format(java.util.Locale.US, "%sMax connection time: %d ms", str, java.lang.Long.valueOf(stats.max)));
        printWriter.println(java.lang.String.format(java.util.Locale.US, "%sMin connection time: %d ms", str, java.lang.Long.valueOf(stats.min)));
        printWriter.println(java.lang.String.format(java.util.Locale.US, "%sNumber of connections: %d ", str, java.lang.Integer.valueOf(stats.n)));
    }

    public static final class Stats {
        public double average;
        public long max;
        public long min;
        public int n;

        public static com.android.server.backup.transport.TransportStats.Stats merge(com.android.server.backup.transport.TransportStats.Stats stats, com.android.server.backup.transport.TransportStats.Stats stats2) {
            return new com.android.server.backup.transport.TransportStats.Stats(stats2.n + stats.n, ((stats.average * stats.n) + (stats2.average * stats2.n)) / (stats.n + stats2.n), java.lang.Math.max(stats.max, stats2.max), java.lang.Math.min(stats.min, stats2.min));
        }

        public Stats() {
            this.n = 0;
            this.average = 0.0d;
            this.max = 0L;
            this.min = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
        }

        private Stats(int i, double d, long j, long j2) {
            this.n = i;
            this.average = d;
            this.max = j;
            this.min = j2;
        }

        private Stats(com.android.server.backup.transport.TransportStats.Stats stats) {
            this(stats.n, stats.average, stats.max, stats.min);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void register(long j) {
            this.average = ((this.average * this.n) + j) / (this.n + 1);
            this.n++;
            this.max = java.lang.Math.max(this.max, j);
            this.min = java.lang.Math.min(this.min, j);
        }
    }
}
