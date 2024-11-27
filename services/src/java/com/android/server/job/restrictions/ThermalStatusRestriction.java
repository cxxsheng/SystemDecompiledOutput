package com.android.server.job.restrictions;

/* loaded from: classes2.dex */
public class ThermalStatusRestriction extends com.android.server.job.restrictions.JobRestriction {
    private static final int HIGHER_PRIORITY_THRESHOLD = 2;
    private static final int LOWER_THRESHOLD = 1;
    private static final int LOW_PRIORITY_THRESHOLD = 1;
    private static final java.lang.String TAG = "ThermalStatusRestriction";
    private static final int UPPER_THRESHOLD = 3;
    private volatile int mThermalStatus;

    public ThermalStatusRestriction(com.android.server.job.JobSchedulerService jobSchedulerService) {
        super(jobSchedulerService, 4, 12, 5);
        this.mThermalStatus = 0;
    }

    @Override // com.android.server.job.restrictions.JobRestriction
    public void onSystemServicesReady() {
        ((android.os.PowerManager) this.mService.getTestableContext().getSystemService(android.os.PowerManager.class)).addThermalStatusListener(new android.os.PowerManager.OnThermalStatusChangedListener() { // from class: com.android.server.job.restrictions.ThermalStatusRestriction.1
            @Override // android.os.PowerManager.OnThermalStatusChangedListener
            public void onThermalStatusChanged(int i) {
                boolean z = (i >= 1 && i <= 3) || (com.android.server.job.restrictions.ThermalStatusRestriction.this.mThermalStatus >= 1 && i < 1) || (com.android.server.job.restrictions.ThermalStatusRestriction.this.mThermalStatus < 3 && i > 3);
                boolean z2 = com.android.server.job.restrictions.ThermalStatusRestriction.this.mThermalStatus < i;
                com.android.server.job.restrictions.ThermalStatusRestriction.this.mThermalStatus = i;
                if (z) {
                    com.android.server.job.restrictions.ThermalStatusRestriction.this.mService.onRestrictionStateChanged(com.android.server.job.restrictions.ThermalStatusRestriction.this, z2);
                }
            }
        });
    }

    @Override // com.android.server.job.restrictions.JobRestriction
    public boolean isJobRestricted(com.android.server.job.controllers.JobStatus jobStatus) {
        if (this.mThermalStatus >= 3) {
            return true;
        }
        int effectivePriority = jobStatus.getEffectivePriority();
        if (this.mThermalStatus >= 2) {
            if (jobStatus.shouldTreatAsUserInitiatedJob()) {
                return false;
            }
            if (!jobStatus.shouldTreatAsExpeditedJob()) {
                return (effectivePriority == 400 && this.mService.isCurrentlyRunningLocked(jobStatus) && !this.mService.isJobInOvertimeLocked(jobStatus)) ? false : true;
            }
            if (jobStatus.getNumPreviousAttempts() <= 0) {
                return this.mService.isCurrentlyRunningLocked(jobStatus) && this.mService.isJobInOvertimeLocked(jobStatus);
            }
            return true;
        }
        if (this.mThermalStatus < 1) {
            return false;
        }
        if (effectivePriority != 100) {
            return effectivePriority == 200 && (!this.mService.isCurrentlyRunningLocked(jobStatus) || this.mService.isJobInOvertimeLocked(jobStatus));
        }
        return true;
    }

    @com.android.internal.annotations.VisibleForTesting
    int getThermalStatus() {
        return this.mThermalStatus;
    }

    @Override // com.android.server.job.restrictions.JobRestriction
    public void dumpConstants(android.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.print("Thermal status: ");
        indentingPrintWriter.println(this.mThermalStatus);
    }
}
