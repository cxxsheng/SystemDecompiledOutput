package android.os;

/* loaded from: classes3.dex */
public final class DdmSyncState {
    private static int sCurrentStageIndex = 0;

    public enum Stage {
        Boot("BOOT"),
        Attach("ATCH"),
        Bind("BIND"),
        Named("NAMD"),
        Debugger("DEBG"),
        Running("A_GO");

        final java.lang.String mLabel;

        Stage(java.lang.String str) {
            if (str.length() != 4) {
                throw new java.lang.IllegalStateException("Bad stage id '" + str + "'. Must be four letters");
            }
            this.mLabel = str;
        }

        public int toInt() {
            int i = 0;
            for (int i2 = 0; i2 < 4; i2++) {
                i = (i << 8) | (this.mLabel.charAt(i2) & 255);
            }
            return i;
        }
    }

    public static synchronized android.os.DdmSyncState.Stage getStage() {
        android.os.DdmSyncState.Stage stage;
        synchronized (android.os.DdmSyncState.class) {
            stage = android.os.DdmSyncState.Stage.values()[sCurrentStageIndex];
        }
        return stage;
    }

    public static void reset() {
        sCurrentStageIndex = 0;
    }

    public static synchronized void next(android.os.DdmSyncState.Stage stage) {
        synchronized (android.os.DdmSyncState.class) {
            android.os.DdmSyncState.Stage[] values = android.os.DdmSyncState.Stage.values();
            int i = sCurrentStageIndex;
            while (i < values.length && values[i] != stage) {
                i++;
            }
            if (i == values.length || values[i] != stage) {
                throw new java.lang.IllegalStateException("Cannot go to " + stage + " from:" + getInternalState());
            }
            sCurrentStageIndex = i;
        }
    }

    private static java.lang.String getInternalState() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("\n");
        sb.append("level = ").append(sCurrentStageIndex);
        sb.append("\n");
        sb.append("stages = ");
        sb.append(java.util.Arrays.toString(java.util.Arrays.stream(android.os.DdmSyncState.Stage.values()).map(new java.util.function.Function() { // from class: android.os.DdmSyncState$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return ((android.os.DdmSyncState.Stage) obj).name();
            }
        }).toArray()));
        sb.append("\n");
        return sb.toString();
    }
}
