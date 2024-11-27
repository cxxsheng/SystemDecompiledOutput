package com.android.server.biometrics;

/* loaded from: classes.dex */
public class SensorConfig {
    public final int id;
    final int modality;
    public final int strength;

    public SensorConfig(java.lang.String str) {
        java.lang.String[] split = str.split(":");
        this.id = java.lang.Integer.parseInt(split[0]);
        this.modality = java.lang.Integer.parseInt(split[1]);
        this.strength = java.lang.Integer.parseInt(split[2]);
    }
}
