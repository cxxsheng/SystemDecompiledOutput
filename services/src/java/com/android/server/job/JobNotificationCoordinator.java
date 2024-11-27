package com.android.server.job;

/* loaded from: classes2.dex */
class JobNotificationCoordinator {
    private static final java.lang.String TAG = "JobNotificationCoordinator";
    private final java.lang.Object mUijLock = new java.lang.Object();
    private final android.util.ArrayMap<android.content.pm.UserPackage, android.util.SparseSetArray<com.android.server.job.JobServiceContext>> mCurrentAssociations = new android.util.ArrayMap<>();
    private final android.util.ArrayMap<com.android.server.job.JobServiceContext, com.android.server.job.JobNotificationCoordinator.NotificationDetails> mNotificationDetails = new android.util.ArrayMap<>();

    @com.android.internal.annotations.GuardedBy({"mUijLock"})
    private final android.util.SparseArrayMap<java.lang.String, android.util.IntArray> mUijNotifications = new android.util.SparseArrayMap<>();

    @com.android.internal.annotations.GuardedBy({"mUijLock"})
    private final android.util.SparseArrayMap<java.lang.String, android.util.ArraySet<java.lang.String>> mUijNotificationChannels = new android.util.SparseArrayMap<>();
    private final com.android.server.notification.NotificationManagerInternal mNotificationManagerInternal = (com.android.server.notification.NotificationManagerInternal) com.android.server.LocalServices.getService(com.android.server.notification.NotificationManagerInternal.class);

    private static final class NotificationDetails {
        public final int appPid;
        public final int appUid;
        public final int jobEndNotificationPolicy;
        public final java.lang.String notificationChannel;
        public final int notificationId;

        @android.annotation.NonNull
        public final android.content.pm.UserPackage userPackage;

        NotificationDetails(@android.annotation.NonNull android.content.pm.UserPackage userPackage, int i, int i2, int i3, java.lang.String str, int i4) {
            this.userPackage = userPackage;
            this.notificationId = i3;
            this.notificationChannel = str;
            this.appPid = i;
            this.appUid = i2;
            this.jobEndNotificationPolicy = i4;
        }
    }

    JobNotificationCoordinator() {
    }

    void enqueueNotification(@android.annotation.NonNull com.android.server.job.JobServiceContext jobServiceContext, @android.annotation.NonNull java.lang.String str, int i, int i2, int i3, @android.annotation.NonNull android.app.Notification notification, int i4) {
        validateNotification(str, i2, notification, i4);
        com.android.server.job.controllers.JobStatus runningJobLocked = jobServiceContext.getRunningJobLocked();
        if (runningJobLocked == null) {
            android.util.Slog.wtfStack(TAG, "enqueueNotification called with no running job");
            return;
        }
        com.android.server.job.JobNotificationCoordinator.NotificationDetails notificationDetails = this.mNotificationDetails.get(jobServiceContext);
        if (notificationDetails == null) {
            if (runningJobLocked.startedAsUserInitiatedJob) {
                com.android.modules.expresslog.Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_initial_set_notification_call_required", runningJobLocked.getUid());
            } else {
                com.android.modules.expresslog.Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_initial_set_notification_call_optional", runningJobLocked.getUid());
            }
        } else {
            if (runningJobLocked.startedAsUserInitiatedJob) {
                com.android.modules.expresslog.Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_subsequent_set_notification_call_required", runningJobLocked.getUid());
            } else {
                com.android.modules.expresslog.Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_subsequent_set_notification_call_optional", runningJobLocked.getUid());
            }
            if (notificationDetails.notificationId != i3) {
                removeNotificationAssociation(jobServiceContext, 0, runningJobLocked);
                com.android.modules.expresslog.Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_set_notification_changed_notification_ids", runningJobLocked.getUid());
            }
        }
        int userId = android.os.UserHandle.getUserId(i2);
        if (runningJobLocked.startedAsUserInitiatedJob) {
            notification.flags |= 32768;
            synchronized (this.mUijLock) {
                try {
                    maybeCreateUijNotificationSetsLocked(userId, str);
                    android.util.IntArray intArray = (android.util.IntArray) this.mUijNotifications.get(userId, str);
                    if (intArray.indexOf(i3) == -1) {
                        intArray.add(i3);
                    }
                    ((android.util.ArraySet) this.mUijNotificationChannels.get(userId, str)).add(notification.getChannelId());
                } finally {
                }
            }
        }
        android.content.pm.UserPackage of = android.content.pm.UserPackage.of(userId, str);
        com.android.server.job.JobNotificationCoordinator.NotificationDetails notificationDetails2 = new com.android.server.job.JobNotificationCoordinator.NotificationDetails(of, i, i2, i3, notification.getChannelId(), i4);
        android.util.SparseSetArray<com.android.server.job.JobServiceContext> sparseSetArray = this.mCurrentAssociations.get(of);
        if (sparseSetArray == null) {
            sparseSetArray = new android.util.SparseSetArray<>();
            this.mCurrentAssociations.put(of, sparseSetArray);
        }
        sparseSetArray.add(i3, jobServiceContext);
        this.mNotificationDetails.put(jobServiceContext, notificationDetails2);
        this.mNotificationManagerInternal.enqueueNotification(str, str, i2, i, null, i3, notification, userId);
    }

    void removeNotificationAssociation(@android.annotation.NonNull com.android.server.job.JobServiceContext jobServiceContext, int i, com.android.server.job.controllers.JobStatus jobStatus) {
        com.android.server.job.JobNotificationCoordinator.NotificationDetails remove = this.mNotificationDetails.remove(jobServiceContext);
        if (remove == null) {
            return;
        }
        android.util.SparseSetArray<com.android.server.job.JobServiceContext> sparseSetArray = this.mCurrentAssociations.get(remove.userPackage);
        if (sparseSetArray == null || !sparseSetArray.remove(remove.notificationId, jobServiceContext)) {
            android.util.Slog.wtf(TAG, "Association data structures not in sync");
            return;
        }
        int userId = android.os.UserHandle.getUserId(remove.appUid);
        java.lang.String str = remove.userPackage.packageName;
        int i2 = remove.notificationId;
        android.util.ArraySet arraySet = sparseSetArray.get(i2);
        boolean z = true;
        if (arraySet == null || arraySet.isEmpty()) {
            if (remove.jobEndNotificationPolicy == 1 || i == 11 || i == 13) {
                this.mNotificationManagerInternal.cancelNotification(str, str, remove.appUid, remove.appPid, null, i2, userId);
                z = false;
            }
        } else {
            z = true ^ isNotificationUsedForAnyUij(userId, str, i2);
        }
        if (z) {
            this.mNotificationManagerInternal.removeUserInitiatedJobFlagFromNotification(str, i2, userId);
        }
        if (jobStatus != null && jobStatus.startedAsUserInitiatedJob) {
            maybeDeleteNotificationIdAssociation(userId, str, i2);
            maybeDeleteNotificationChannelAssociation(userId, str, remove.notificationChannel);
        }
    }

    boolean isNotificationAssociatedWithAnyUserInitiatedJobs(int i, int i2, @android.annotation.NonNull java.lang.String str) {
        synchronized (this.mUijLock) {
            try {
                android.util.IntArray intArray = (android.util.IntArray) this.mUijNotifications.get(i2, str);
                if (intArray == null) {
                    return false;
                }
                return intArray.indexOf(i) != -1;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    boolean isNotificationChannelAssociatedWithAnyUserInitiatedJobs(@android.annotation.NonNull java.lang.String str, int i, @android.annotation.NonNull java.lang.String str2) {
        synchronized (this.mUijLock) {
            try {
                android.util.ArraySet arraySet = (android.util.ArraySet) this.mUijNotificationChannels.get(i, str2);
                if (arraySet == null) {
                    return false;
                }
                return arraySet.contains(str);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private boolean isNotificationUsedForAnyUij(int i, java.lang.String str, int i2) {
        android.util.ArraySet arraySet;
        android.util.SparseSetArray<com.android.server.job.JobServiceContext> sparseSetArray = this.mCurrentAssociations.get(android.content.pm.UserPackage.of(i, str));
        if (sparseSetArray == null || (arraySet = sparseSetArray.get(i2)) == null) {
            return false;
        }
        for (int size = arraySet.size() - 1; size >= 0; size--) {
            com.android.server.job.controllers.JobStatus runningJobLocked = ((com.android.server.job.JobServiceContext) arraySet.valueAt(size)).getRunningJobLocked();
            if (runningJobLocked != null && runningJobLocked.startedAsUserInitiatedJob) {
                return true;
            }
        }
        return false;
    }

    private void maybeDeleteNotificationIdAssociation(int i, java.lang.String str, int i2) {
        if (isNotificationUsedForAnyUij(i, str, i2)) {
            return;
        }
        synchronized (this.mUijLock) {
            try {
                android.util.IntArray intArray = (android.util.IntArray) this.mUijNotifications.get(i, str);
                if (intArray != null) {
                    intArray.remove(intArray.indexOf(i2));
                    if (intArray.size() == 0) {
                        this.mUijNotifications.delete(i, str);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void maybeDeleteNotificationChannelAssociation(int i, java.lang.String str, java.lang.String str2) {
        com.android.server.job.controllers.JobStatus runningJobLocked;
        for (int size = this.mNotificationDetails.size() - 1; size >= 0; size--) {
            com.android.server.job.JobServiceContext keyAt = this.mNotificationDetails.keyAt(size);
            com.android.server.job.JobNotificationCoordinator.NotificationDetails notificationDetails = this.mNotificationDetails.get(keyAt);
            if (notificationDetails != null && android.os.UserHandle.getUserId(notificationDetails.appUid) == i && notificationDetails.userPackage.packageName.equals(str) && notificationDetails.notificationChannel.equals(str2) && (runningJobLocked = keyAt.getRunningJobLocked()) != null && runningJobLocked.startedAsUserInitiatedJob) {
                return;
            }
        }
        synchronized (this.mUijLock) {
            try {
                android.util.ArraySet arraySet = (android.util.ArraySet) this.mUijNotificationChannels.get(i, str);
                if (arraySet != null) {
                    arraySet.remove(str2);
                    if (arraySet.isEmpty()) {
                        this.mUijNotificationChannels.delete(i, str);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mUijLock"})
    private void maybeCreateUijNotificationSetsLocked(int i, java.lang.String str) {
        if (this.mUijNotifications.get(i, str) == null) {
            this.mUijNotifications.add(i, str, new android.util.IntArray());
        }
        if (this.mUijNotificationChannels.get(i, str) == null) {
            this.mUijNotificationChannels.add(i, str, new android.util.ArraySet());
        }
    }

    private void validateNotification(@android.annotation.NonNull java.lang.String str, int i, @android.annotation.NonNull android.app.Notification notification, int i2) {
        if (notification == null) {
            throw new java.lang.NullPointerException("notification");
        }
        if (notification.getSmallIcon() == null) {
            throw new java.lang.IllegalArgumentException("small icon required");
        }
        if (this.mNotificationManagerInternal.getNotificationChannel(str, i, notification.getChannelId()) == null) {
            throw new java.lang.IllegalArgumentException("invalid notification channel");
        }
        if (i2 != 0 && i2 != 1) {
            throw new java.lang.IllegalArgumentException("invalid job end notification policy");
        }
    }
}
