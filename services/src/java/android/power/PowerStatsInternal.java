package android.power;

/* loaded from: classes.dex */
public abstract class PowerStatsInternal {
    @android.annotation.NonNull
    public abstract java.util.concurrent.CompletableFuture<android.hardware.power.stats.EnergyConsumerResult[]> getEnergyConsumedAsync(int[] iArr);

    @android.annotation.Nullable
    public abstract android.hardware.power.stats.EnergyConsumer[] getEnergyConsumerInfo();

    @android.annotation.Nullable
    public abstract android.hardware.power.stats.Channel[] getEnergyMeterInfo();

    @android.annotation.Nullable
    public abstract android.hardware.power.stats.PowerEntity[] getPowerEntityInfo();

    @android.annotation.NonNull
    public abstract java.util.concurrent.CompletableFuture<android.hardware.power.stats.StateResidencyResult[]> getStateResidencyAsync(int[] iArr);

    @android.annotation.NonNull
    public abstract java.util.concurrent.CompletableFuture<android.hardware.power.stats.EnergyMeasurement[]> readEnergyMeterAsync(int[] iArr);
}
