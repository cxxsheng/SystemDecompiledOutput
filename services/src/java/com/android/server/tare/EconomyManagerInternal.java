package com.android.server.tare;

/* loaded from: classes2.dex */
public interface EconomyManagerInternal {

    public interface AffordabilityChangeListener {
        void onAffordabilityChanged(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull com.android.server.tare.EconomyManagerInternal.ActionBill actionBill, boolean z);
    }

    public interface TareStateChangeListener {
        void onTareEnabledModeChanged(int i);
    }

    boolean canPayFor(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull com.android.server.tare.EconomyManagerInternal.ActionBill actionBill);

    int getEnabledMode();

    int getEnabledMode(int i);

    long getMaxDurationMs(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull com.android.server.tare.EconomyManagerInternal.ActionBill actionBill);

    void noteInstantaneousEvent(int i, @android.annotation.NonNull java.lang.String str, int i2, @android.annotation.Nullable java.lang.String str2);

    void noteOngoingEventStarted(int i, @android.annotation.NonNull java.lang.String str, int i2, @android.annotation.Nullable java.lang.String str2);

    void noteOngoingEventStopped(int i, @android.annotation.NonNull java.lang.String str, int i2, @android.annotation.Nullable java.lang.String str2);

    void registerAffordabilityChangeListener(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull com.android.server.tare.EconomyManagerInternal.AffordabilityChangeListener affordabilityChangeListener, @android.annotation.NonNull com.android.server.tare.EconomyManagerInternal.ActionBill actionBill);

    void registerTareStateChangeListener(@android.annotation.NonNull com.android.server.tare.EconomyManagerInternal.TareStateChangeListener tareStateChangeListener, int i);

    void unregisterAffordabilityChangeListener(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull com.android.server.tare.EconomyManagerInternal.AffordabilityChangeListener affordabilityChangeListener, @android.annotation.NonNull com.android.server.tare.EconomyManagerInternal.ActionBill actionBill);

    void unregisterTareStateChangeListener(@android.annotation.NonNull com.android.server.tare.EconomyManagerInternal.TareStateChangeListener tareStateChangeListener);

    public static final class AnticipatedAction {
        public final int actionId;
        private final int mHashCode;
        public final int numInstantaneousCalls;
        public final long ongoingDurationMs;

        public AnticipatedAction(int i, int i2, long j) {
            this.actionId = i;
            this.numInstantaneousCalls = i2;
            this.ongoingDurationMs = j;
            this.mHashCode = ((((0 + i) * 31) + i2) * 31) + ((int) ((j >>> 32) ^ j));
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || com.android.server.tare.EconomyManagerInternal.AnticipatedAction.class != obj.getClass()) {
                return false;
            }
            com.android.server.tare.EconomyManagerInternal.AnticipatedAction anticipatedAction = (com.android.server.tare.EconomyManagerInternal.AnticipatedAction) obj;
            if (this.actionId == anticipatedAction.actionId && this.numInstantaneousCalls == anticipatedAction.numInstantaneousCalls && this.ongoingDurationMs == anticipatedAction.ongoingDurationMs) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return this.mHashCode;
        }
    }

    public static final class ActionBill {
        private static final java.util.Comparator<com.android.server.tare.EconomyManagerInternal.AnticipatedAction> sAnticipatedActionComparator = java.util.Comparator.comparingInt(new java.util.function.ToIntFunction() { // from class: com.android.server.tare.EconomyManagerInternal$ActionBill$$ExternalSyntheticLambda0
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(java.lang.Object obj) {
                int i;
                i = ((com.android.server.tare.EconomyManagerInternal.AnticipatedAction) obj).actionId;
                return i;
            }
        });
        private final java.util.List<com.android.server.tare.EconomyManagerInternal.AnticipatedAction> mAnticipatedActions;
        private final int mHashCode;

        public ActionBill(@android.annotation.NonNull java.util.List<com.android.server.tare.EconomyManagerInternal.AnticipatedAction> list) {
            java.util.ArrayList arrayList = new java.util.ArrayList(list);
            arrayList.sort(sAnticipatedActionComparator);
            this.mAnticipatedActions = java.util.Collections.unmodifiableList(arrayList);
            int i = 0;
            for (int i2 = 0; i2 < this.mAnticipatedActions.size(); i2++) {
                i = (i * 31) + this.mAnticipatedActions.get(i2).hashCode();
            }
            this.mHashCode = i;
        }

        java.util.List<com.android.server.tare.EconomyManagerInternal.AnticipatedAction> getAnticipatedActions() {
            return this.mAnticipatedActions;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || com.android.server.tare.EconomyManagerInternal.ActionBill.class != obj.getClass()) {
                return false;
            }
            return this.mAnticipatedActions.equals(((com.android.server.tare.EconomyManagerInternal.ActionBill) obj).mAnticipatedActions);
        }

        public int hashCode() {
            return this.mHashCode;
        }
    }
}
