package com.android.server.display.brightness;

/* loaded from: classes.dex */
public class DisplayBrightnessStrategySelector {
    private static final java.lang.String TAG = "DisplayBrightnessStrategySelector";
    private final boolean mAllowAutoBrightnessWhileDozingConfig;
    private final com.android.server.display.brightness.strategy.AutomaticBrightnessStrategy mAutomaticBrightnessStrategy;
    private final com.android.server.display.brightness.strategy.BoostBrightnessStrategy mBoostBrightnessStrategy;
    private final com.android.server.display.brightness.strategy.DisplayBrightnessStrategy[] mDisplayBrightnessStrategies;
    private final int mDisplayId;
    private final com.android.server.display.brightness.strategy.DozeBrightnessStrategy mDozeBrightnessStrategy;
    private final com.android.server.display.brightness.strategy.FollowerBrightnessStrategy mFollowerBrightnessStrategy;
    private final com.android.server.display.brightness.strategy.InvalidBrightnessStrategy mInvalidBrightnessStrategy;

    @android.annotation.Nullable
    private final com.android.server.display.brightness.strategy.OffloadBrightnessStrategy mOffloadBrightnessStrategy;
    private java.lang.String mOldBrightnessStrategyName;
    private final com.android.server.display.brightness.strategy.OverrideBrightnessStrategy mOverrideBrightnessStrategy;
    private final com.android.server.display.brightness.strategy.ScreenOffBrightnessStrategy mScreenOffBrightnessStrategy;
    private final com.android.server.display.brightness.strategy.TemporaryBrightnessStrategy mTemporaryBrightnessStrategy;

    public DisplayBrightnessStrategySelector(android.content.Context context, com.android.server.display.brightness.DisplayBrightnessStrategySelector.Injector injector, int i, com.android.server.display.feature.DisplayManagerFlags displayManagerFlags) {
        injector = injector == null ? new com.android.server.display.brightness.DisplayBrightnessStrategySelector.Injector() : injector;
        this.mDisplayId = i;
        this.mDozeBrightnessStrategy = injector.getDozeBrightnessStrategy();
        this.mScreenOffBrightnessStrategy = injector.getScreenOffBrightnessStrategy();
        this.mOverrideBrightnessStrategy = injector.getOverrideBrightnessStrategy();
        this.mTemporaryBrightnessStrategy = injector.getTemporaryBrightnessStrategy();
        this.mBoostBrightnessStrategy = injector.getBoostBrightnessStrategy();
        this.mFollowerBrightnessStrategy = injector.getFollowerBrightnessStrategy(i);
        this.mInvalidBrightnessStrategy = injector.getInvalidBrightnessStrategy();
        this.mAutomaticBrightnessStrategy = injector.getAutomaticBrightnessStrategy(context, i);
        if (displayManagerFlags.isDisplayOffloadEnabled()) {
            this.mOffloadBrightnessStrategy = injector.getOffloadBrightnessStrategy();
        } else {
            this.mOffloadBrightnessStrategy = null;
        }
        this.mDisplayBrightnessStrategies = new com.android.server.display.brightness.strategy.DisplayBrightnessStrategy[]{this.mInvalidBrightnessStrategy, this.mScreenOffBrightnessStrategy, this.mDozeBrightnessStrategy, this.mFollowerBrightnessStrategy, this.mBoostBrightnessStrategy, this.mOverrideBrightnessStrategy, this.mTemporaryBrightnessStrategy, this.mOffloadBrightnessStrategy};
        this.mAllowAutoBrightnessWhileDozingConfig = context.getResources().getBoolean(android.R.bool.config_allowAutoBrightnessWhileDozing);
        this.mOldBrightnessStrategyName = this.mInvalidBrightnessStrategy.getName();
    }

    @android.annotation.NonNull
    public com.android.server.display.brightness.strategy.DisplayBrightnessStrategy selectStrategy(android.hardware.display.DisplayManagerInternal.DisplayPowerRequest displayPowerRequest, int i) {
        com.android.server.display.brightness.strategy.DisplayBrightnessStrategy displayBrightnessStrategy = this.mInvalidBrightnessStrategy;
        if (i == 1) {
            displayBrightnessStrategy = this.mScreenOffBrightnessStrategy;
        } else if (shouldUseDozeBrightnessStrategy(displayPowerRequest)) {
            displayBrightnessStrategy = this.mDozeBrightnessStrategy;
        } else if (com.android.server.display.brightness.BrightnessUtils.isValidBrightnessValue(this.mFollowerBrightnessStrategy.getBrightnessToFollow())) {
            displayBrightnessStrategy = this.mFollowerBrightnessStrategy;
        } else if (displayPowerRequest.boostScreenBrightness) {
            displayBrightnessStrategy = this.mBoostBrightnessStrategy;
        } else if (com.android.server.display.brightness.BrightnessUtils.isValidBrightnessValue(displayPowerRequest.screenBrightnessOverride)) {
            displayBrightnessStrategy = this.mOverrideBrightnessStrategy;
        } else if (com.android.server.display.brightness.BrightnessUtils.isValidBrightnessValue(this.mTemporaryBrightnessStrategy.getTemporaryScreenBrightness())) {
            displayBrightnessStrategy = this.mTemporaryBrightnessStrategy;
        } else if (this.mOffloadBrightnessStrategy != null && com.android.server.display.brightness.BrightnessUtils.isValidBrightnessValue(this.mOffloadBrightnessStrategy.getOffloadScreenBrightness())) {
            displayBrightnessStrategy = this.mOffloadBrightnessStrategy;
        }
        if (!this.mOldBrightnessStrategyName.equals(displayBrightnessStrategy.getName())) {
            android.util.Slog.i(TAG, "Changing the DisplayBrightnessStrategy from " + this.mOldBrightnessStrategyName + " to " + displayBrightnessStrategy.getName() + " for display " + this.mDisplayId);
            this.mOldBrightnessStrategyName = displayBrightnessStrategy.getName();
        }
        return displayBrightnessStrategy;
    }

    public com.android.server.display.brightness.strategy.TemporaryBrightnessStrategy getTemporaryDisplayBrightnessStrategy() {
        return this.mTemporaryBrightnessStrategy;
    }

    public com.android.server.display.brightness.strategy.FollowerBrightnessStrategy getFollowerDisplayBrightnessStrategy() {
        return this.mFollowerBrightnessStrategy;
    }

    public com.android.server.display.brightness.strategy.AutomaticBrightnessStrategy getAutomaticBrightnessStrategy() {
        return this.mAutomaticBrightnessStrategy;
    }

    @android.annotation.Nullable
    public com.android.server.display.brightness.strategy.OffloadBrightnessStrategy getOffloadBrightnessStrategy() {
        return this.mOffloadBrightnessStrategy;
    }

    public boolean isAllowAutoBrightnessWhileDozingConfig() {
        return this.mAllowAutoBrightnessWhileDozingConfig;
    }

    public void dump(java.io.PrintWriter printWriter) {
        printWriter.println();
        printWriter.println("DisplayBrightnessStrategySelector:");
        printWriter.println("  mDisplayId= " + this.mDisplayId);
        printWriter.println("  mOldBrightnessStrategyName= " + this.mOldBrightnessStrategyName);
        printWriter.println("  mAllowAutoBrightnessWhileDozingConfig= " + this.mAllowAutoBrightnessWhileDozingConfig);
        java.io.PrintWriter indentingPrintWriter = new android.util.IndentingPrintWriter(printWriter, " ");
        for (com.android.server.display.brightness.strategy.DisplayBrightnessStrategy displayBrightnessStrategy : this.mDisplayBrightnessStrategies) {
            if (displayBrightnessStrategy != null) {
                displayBrightnessStrategy.dump(indentingPrintWriter);
            }
        }
    }

    private boolean shouldUseDozeBrightnessStrategy(android.hardware.display.DisplayManagerInternal.DisplayPowerRequest displayPowerRequest) {
        return displayPowerRequest.policy == 1 && !this.mAllowAutoBrightnessWhileDozingConfig && com.android.server.display.brightness.BrightnessUtils.isValidBrightnessValue(displayPowerRequest.dozeScreenBrightness);
    }

    @com.android.internal.annotations.VisibleForTesting
    static class Injector {
        Injector() {
        }

        com.android.server.display.brightness.strategy.ScreenOffBrightnessStrategy getScreenOffBrightnessStrategy() {
            return new com.android.server.display.brightness.strategy.ScreenOffBrightnessStrategy();
        }

        com.android.server.display.brightness.strategy.DozeBrightnessStrategy getDozeBrightnessStrategy() {
            return new com.android.server.display.brightness.strategy.DozeBrightnessStrategy();
        }

        com.android.server.display.brightness.strategy.OverrideBrightnessStrategy getOverrideBrightnessStrategy() {
            return new com.android.server.display.brightness.strategy.OverrideBrightnessStrategy();
        }

        com.android.server.display.brightness.strategy.TemporaryBrightnessStrategy getTemporaryBrightnessStrategy() {
            return new com.android.server.display.brightness.strategy.TemporaryBrightnessStrategy();
        }

        com.android.server.display.brightness.strategy.BoostBrightnessStrategy getBoostBrightnessStrategy() {
            return new com.android.server.display.brightness.strategy.BoostBrightnessStrategy();
        }

        com.android.server.display.brightness.strategy.FollowerBrightnessStrategy getFollowerBrightnessStrategy(int i) {
            return new com.android.server.display.brightness.strategy.FollowerBrightnessStrategy(i);
        }

        com.android.server.display.brightness.strategy.InvalidBrightnessStrategy getInvalidBrightnessStrategy() {
            return new com.android.server.display.brightness.strategy.InvalidBrightnessStrategy();
        }

        com.android.server.display.brightness.strategy.AutomaticBrightnessStrategy getAutomaticBrightnessStrategy(android.content.Context context, int i) {
            return new com.android.server.display.brightness.strategy.AutomaticBrightnessStrategy(context, i);
        }

        com.android.server.display.brightness.strategy.OffloadBrightnessStrategy getOffloadBrightnessStrategy() {
            return new com.android.server.display.brightness.strategy.OffloadBrightnessStrategy();
        }
    }
}
