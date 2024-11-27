package com.android.server.tare;

/* loaded from: classes2.dex */
abstract class Modifier {
    static final int COST_MODIFIER_CHARGING = 0;
    static final int COST_MODIFIER_DEVICE_IDLE = 1;
    static final int COST_MODIFIER_POWER_SAVE_MODE = 2;
    static final int COST_MODIFIER_PROCESS_STATE = 3;
    static final int NUM_COST_MODIFIERS = 4;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CostModifier {
    }

    Modifier() {
    }

    abstract void dump(android.util.IndentingPrintWriter indentingPrintWriter);

    long getModifiedCostToProduce(long j) {
        return j;
    }

    long getModifiedPrice(long j) {
        return j;
    }

    void setup() {
    }

    void tearDown() {
    }
}
