package com.android.server.stats.bootstrap;

/* loaded from: classes2.dex */
public class StatsBootstrapAtomService extends android.os.IStatsBootstrapAtomService.Stub {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "StatsBootstrapAtomService";

    public void reportBootstrapAtom(android.os.StatsBootstrapAtom statsBootstrapAtom) {
        if (statsBootstrapAtom.atomId < 1 || statsBootstrapAtom.atomId >= 10000) {
            android.util.Slog.e(TAG, "Atom ID " + statsBootstrapAtom.atomId + " is not a valid atom ID");
            return;
        }
        android.util.StatsEvent.Builder atomId = android.util.StatsEvent.newBuilder().setAtomId(statsBootstrapAtom.atomId);
        for (android.os.StatsBootstrapAtomValue statsBootstrapAtomValue : statsBootstrapAtom.values) {
            switch (statsBootstrapAtomValue.getTag()) {
                case 0:
                    atomId.writeBoolean(statsBootstrapAtomValue.getBoolValue());
                    break;
                case 1:
                    atomId.writeInt(statsBootstrapAtomValue.getIntValue());
                    break;
                case 2:
                    atomId.writeLong(statsBootstrapAtomValue.getLongValue());
                    break;
                case 3:
                    atomId.writeFloat(statsBootstrapAtomValue.getFloatValue());
                    break;
                case 4:
                    atomId.writeString(statsBootstrapAtomValue.getStringValue());
                    break;
                case 5:
                    atomId.writeByteArray(statsBootstrapAtomValue.getBytesValue());
                    break;
                default:
                    android.util.Slog.e(TAG, "Unexpected value type " + statsBootstrapAtomValue.getTag() + " when logging atom " + statsBootstrapAtom.atomId);
                    return;
            }
        }
        android.util.StatsLog.write(atomId.usePooledBuffer().build());
    }

    public static final class Lifecycle extends com.android.server.SystemService {
        private com.android.server.stats.bootstrap.StatsBootstrapAtomService mStatsBootstrapAtomService;

        public Lifecycle(android.content.Context context) {
            super(context);
        }

        @Override // com.android.server.SystemService
        public void onStart() {
            this.mStatsBootstrapAtomService = new com.android.server.stats.bootstrap.StatsBootstrapAtomService();
            try {
                publishBinderService("statsbootstrap", this.mStatsBootstrapAtomService);
            } catch (java.lang.Exception e) {
                android.util.Slog.e(com.android.server.stats.bootstrap.StatsBootstrapAtomService.TAG, "Failed to publishBinderService", e);
            }
        }
    }
}
