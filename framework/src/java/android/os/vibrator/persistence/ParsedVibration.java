package android.os.vibrator.persistence;

/* loaded from: classes3.dex */
public class ParsedVibration {
    private final java.util.List<android.os.VibrationEffect> mEffects;

    public ParsedVibration(java.util.List<android.os.VibrationEffect> list) {
        this.mEffects = list;
    }

    public ParsedVibration(android.os.VibrationEffect vibrationEffect) {
        this.mEffects = java.util.List.of(vibrationEffect);
    }

    public android.os.VibrationEffect resolve(android.os.Vibrator vibrator) {
        return resolve(vibrator.getInfo());
    }

    public java.util.List<android.os.VibrationEffect> getVibrationEffects() {
        return java.util.Collections.unmodifiableList(this.mEffects);
    }

    public final android.os.VibrationEffect resolve(android.os.VibratorInfo vibratorInfo) {
        for (int i = 0; i < this.mEffects.size(); i++) {
            android.os.VibrationEffect vibrationEffect = this.mEffects.get(i);
            if (vibratorInfo.areVibrationFeaturesSupported(vibrationEffect)) {
                return vibrationEffect;
            }
        }
        return null;
    }
}
