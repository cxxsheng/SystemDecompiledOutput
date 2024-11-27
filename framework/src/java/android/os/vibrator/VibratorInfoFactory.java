package android.os.vibrator;

/* loaded from: classes3.dex */
public final class VibratorInfoFactory {
    public static android.os.VibratorInfo create(int i, android.os.VibratorInfo[] vibratorInfoArr) {
        if (vibratorInfoArr.length == 0) {
            return new android.os.VibratorInfo.Builder(i).build();
        }
        if (vibratorInfoArr.length == 1) {
            return new android.os.VibratorInfo(i, vibratorInfoArr[0]);
        }
        return new android.os.vibrator.MultiVibratorInfo(i, vibratorInfoArr);
    }

    private VibratorInfoFactory() {
    }
}
