package com.android.modules.expresslog;

/* loaded from: classes5.dex */
public final class MetricIds {
    public static final int METRIC_TYPE_COUNTER = 1;
    public static final int METRIC_TYPE_COUNTER_WITH_UID = 3;
    public static final int METRIC_TYPE_HISTOGRAM = 2;
    public static final int METRIC_TYPE_HISTOGRAM_WITH_UID = 4;
    public static final int METRIC_TYPE_UNKNOWN = 0;
    private static android.util.ArrayMap<java.lang.String, com.android.modules.expresslog.MetricIds.MetricInfo> metricIds = new android.util.ArrayMap<>();

    private static final class MetricInfo {
        public long mHash;
        public int mType;

        MetricInfo(long j, int i) {
            this.mHash = j;
            this.mType = i;
        }
    }

    static {
        metricIds.put("accessibility.value_fab_shortcut_action_dismiss", new com.android.modules.expresslog.MetricIds.MetricInfo(2124594874651631799L, 3));
        metricIds.put("accessibility.value_fab_shortcut_action_edit", new com.android.modules.expresslog.MetricIds.MetricInfo(-8870361605722580335L, 3));
        metricIds.put("automotive_os.value_concurrent_sync_operations", new com.android.modules.expresslog.MetricIds.MetricInfo(-4400160310807362695L, 2));
        metricIds.put("automotive_os.value_get_async_end_to_end_latency", new com.android.modules.expresslog.MetricIds.MetricInfo(-8272866773037756707L, 2));
        metricIds.put("automotive_os.value_get_async_latency", new com.android.modules.expresslog.MetricIds.MetricInfo(-8894500157990623512L, 2));
        metricIds.put("automotive_os.value_set_async_end_to_end_latency", new com.android.modules.expresslog.MetricIds.MetricInfo(156946757820491208L, 2));
        metricIds.put("automotive_os.value_set_async_latency", new com.android.modules.expresslog.MetricIds.MetricInfo(-4331235063088608305L, 2));
        metricIds.put("automotive_os.value_subscription_update_rate", new com.android.modules.expresslog.MetricIds.MetricInfo(-4229550097093086307L, 2));
        metricIds.put("automotive_os.value_sync_get_property_latency", new com.android.modules.expresslog.MetricIds.MetricInfo(-5307712280607833154L, 2));
        metricIds.put("automotive_os.value_sync_hal_get_property_latency", new com.android.modules.expresslog.MetricIds.MetricInfo(4878890158497689193L, 2));
        metricIds.put("automotive_os.value_sync_hal_set_property_latency", new com.android.modules.expresslog.MetricIds.MetricInfo(-772366165941106467L, 2));
        metricIds.put("automotive_os.value_sync_set_property_latency", new com.android.modules.expresslog.MetricIds.MetricInfo(5217707327207935689L, 2));
        metricIds.put("battery.value_app_added_to_power_allowlist", new com.android.modules.expresslog.MetricIds.MetricInfo(4707695847309057012L, 1));
        metricIds.put("battery.value_app_background_restricted", new com.android.modules.expresslog.MetricIds.MetricInfo(2048093366999806818L, 3));
        metricIds.put("battery.value_app_removed_from_power_allowlist", new com.android.modules.expresslog.MetricIds.MetricInfo(-4988440224544870744L, 1));
        metricIds.put("binary_transparency.value_digest_all_packages_latency_uniform", new com.android.modules.expresslog.MetricIds.MetricInfo(-1663388052249034620L, 2));
        metricIds.put("biometric.value_scheduler_watchdog_triggered_count", new com.android.modules.expresslog.MetricIds.MetricInfo(-8949614549819407840L, 1));
        metricIds.put("bluetooth.value_aptx_codec_usage_over_hfp", new com.android.modules.expresslog.MetricIds.MetricInfo(7003564244080163944L, 1));
        metricIds.put("bluetooth.value_auto_on_disabled", new com.android.modules.expresslog.MetricIds.MetricInfo(38262175154558217L, 1));
        metricIds.put("bluetooth.value_auto_on_enabled", new com.android.modules.expresslog.MetricIds.MetricInfo(4153375556285078717L, 1));
        metricIds.put("bluetooth.value_auto_on_hidden_usage", new com.android.modules.expresslog.MetricIds.MetricInfo(4395019980508280633L, 1));
        metricIds.put("bluetooth.value_auto_on_supported", new com.android.modules.expresslog.MetricIds.MetricInfo(-3270875478117966326L, 1));
        metricIds.put("bluetooth.value_auto_on_triggered", new com.android.modules.expresslog.MetricIds.MetricInfo(2657249641697133856L, 1));
        metricIds.put("bluetooth.value_close_profile_proxy_adapter_mismatch", new com.android.modules.expresslog.MetricIds.MetricInfo(5174922474613897731L, 3));
        metricIds.put("bluetooth.value_cvsd_codec_usage_over_hfp", new com.android.modules.expresslog.MetricIds.MetricInfo(2237290434176331321L, 1));
        metricIds.put("bluetooth.value_lc3_codec_usage_over_hfp", new com.android.modules.expresslog.MetricIds.MetricInfo(8778544908497736848L, 1));
        metricIds.put("bluetooth.value_msbc_codec_usage_over_hfp", new com.android.modules.expresslog.MetricIds.MetricInfo(-3563813040444045278L, 1));
        metricIds.put("content_capture.value_content_capture_wrong_thread_count", new com.android.modules.expresslog.MetricIds.MetricInfo(-2722617273353133449L, 1));
        metricIds.put("cpu.value_aggregated_thread_tracking_start_failure_count", new com.android.modules.expresslog.MetricIds.MetricInfo(-6863812985036080576L, 1));
        metricIds.put("cpu.value_process_tracking_start_failure_count", new com.android.modules.expresslog.MetricIds.MetricInfo(7879492844327619097L, 1));
        metricIds.put("device_lock.value_resets_unsuccessful_provisioning_deferred", new com.android.modules.expresslog.MetricIds.MetricInfo(1115524515723454059L, 1));
        metricIds.put("device_lock.value_resets_unsuccessful_provisioning_mandatory", new com.android.modules.expresslog.MetricIds.MetricInfo(241691752144394174L, 1));
        metricIds.put("device_lock.value_successful_check_in_response_count", new com.android.modules.expresslog.MetricIds.MetricInfo(4953020919956346487L, 1));
        metricIds.put("device_lock.value_successful_locking_count", new com.android.modules.expresslog.MetricIds.MetricInfo(8449007043296996467L, 1));
        metricIds.put("device_lock.value_successful_provisioning_count", new com.android.modules.expresslog.MetricIds.MetricInfo(2831700733284693731L, 1));
        metricIds.put("device_lock.value_successful_unlocking_count", new com.android.modules.expresslog.MetricIds.MetricInfo(-2324248776576908586L, 1));
        metricIds.put("health_services.value_hal_crash_counter", new com.android.modules.expresslog.MetricIds.MetricInfo(1163143892454419526L, 1));
        metricIds.put("input.value_app_handled_stem_primary_key_gestures_count", new com.android.modules.expresslog.MetricIds.MetricInfo(1523110716521952632L, 3));
        metricIds.put("job_scheduler.value_cntr_w_uid_estimated_network_bytes_updated", new com.android.modules.expresslog.MetricIds.MetricInfo(-2040690094286153484L, 3));
        metricIds.put("job_scheduler.value_cntr_w_uid_estimated_network_download_bytes_decreased", new com.android.modules.expresslog.MetricIds.MetricInfo(7559830738664114070L, 3));
        metricIds.put("job_scheduler.value_cntr_w_uid_estimated_network_download_bytes_increased", new com.android.modules.expresslog.MetricIds.MetricInfo(4307374164472954573L, 3));
        metricIds.put("job_scheduler.value_cntr_w_uid_estimated_network_upload_bytes_decreased", new com.android.modules.expresslog.MetricIds.MetricInfo(7746759860871419241L, 3));
        metricIds.put("job_scheduler.value_cntr_w_uid_estimated_network_upload_bytes_increased", new com.android.modules.expresslog.MetricIds.MetricInfo(1203926515126858145L, 3));
        metricIds.put("job_scheduler.value_cntr_w_uid_initial_set_notification_call_optional", new com.android.modules.expresslog.MetricIds.MetricInfo(6572724005091391785L, 3));
        metricIds.put("job_scheduler.value_cntr_w_uid_initial_set_notification_call_required", new com.android.modules.expresslog.MetricIds.MetricInfo(4062401757479629526L, 3));
        metricIds.put("job_scheduler.value_cntr_w_uid_job_work_items_enqueued", new com.android.modules.expresslog.MetricIds.MetricInfo(-7467641086093308410L, 3));
        metricIds.put("job_scheduler.value_cntr_w_uid_max_scheduling_limit_hit", new com.android.modules.expresslog.MetricIds.MetricInfo(-9136864684089221786L, 3));
        metricIds.put("job_scheduler.value_cntr_w_uid_schedule_failure_app_start_mode_disabled", new com.android.modules.expresslog.MetricIds.MetricInfo(7917680363610375490L, 3));
        metricIds.put("job_scheduler.value_cntr_w_uid_schedule_failure_ej_out_of_quota", new com.android.modules.expresslog.MetricIds.MetricInfo(-7117224395995325645L, 3));
        metricIds.put("job_scheduler.value_cntr_w_uid_schedule_failure_uij_invalid_state", new com.android.modules.expresslog.MetricIds.MetricInfo(-5320416248652252935L, 3));
        metricIds.put("job_scheduler.value_cntr_w_uid_schedule_failure_uij_no_permission", new com.android.modules.expresslog.MetricIds.MetricInfo(-6831752108807384315L, 3));
        metricIds.put("job_scheduler.value_cntr_w_uid_set_notification_changed_notification_ids", new com.android.modules.expresslog.MetricIds.MetricInfo(-5987874878060478878L, 3));
        metricIds.put("job_scheduler.value_cntr_w_uid_slow_app_response_binding", new com.android.modules.expresslog.MetricIds.MetricInfo(5259558322414450046L, 3));
        metricIds.put("job_scheduler.value_cntr_w_uid_slow_app_response_on_start_job", new com.android.modules.expresslog.MetricIds.MetricInfo(-3883908790761732574L, 3));
        metricIds.put("job_scheduler.value_cntr_w_uid_slow_app_response_on_stop_job", new com.android.modules.expresslog.MetricIds.MetricInfo(6916219943787278753L, 3));
        metricIds.put("job_scheduler.value_cntr_w_uid_slow_app_response_set_notification", new com.android.modules.expresslog.MetricIds.MetricInfo(-3311018727502583721L, 3));
        metricIds.put("job_scheduler.value_cntr_w_uid_subsequent_set_notification_call_optional", new com.android.modules.expresslog.MetricIds.MetricInfo(9145630948768787150L, 3));
        metricIds.put("job_scheduler.value_cntr_w_uid_subsequent_set_notification_call_required", new com.android.modules.expresslog.MetricIds.MetricInfo(3663977039218515109L, 3));
        metricIds.put("job_scheduler.value_cntr_w_uid_transferred_network_bytes_updated", new com.android.modules.expresslog.MetricIds.MetricInfo(-3822161589539396120L, 3));
        metricIds.put("job_scheduler.value_cntr_w_uid_transferred_network_download_bytes_decreased", new com.android.modules.expresslog.MetricIds.MetricInfo(-5904338831025314582L, 3));
        metricIds.put("job_scheduler.value_cntr_w_uid_transferred_network_download_bytes_increased", new com.android.modules.expresslog.MetricIds.MetricInfo(-2671783057200009454L, 3));
        metricIds.put("job_scheduler.value_cntr_w_uid_transferred_network_upload_bytes_decreased", new com.android.modules.expresslog.MetricIds.MetricInfo(6063356280586548670L, 3));
        metricIds.put("job_scheduler.value_cntr_w_uid_transferred_network_upload_bytes_increased", new com.android.modules.expresslog.MetricIds.MetricInfo(-7671154881427569796L, 3));
        metricIds.put("job_scheduler.value_cntr_w_uid_unexpected_service_disconnects", new com.android.modules.expresslog.MetricIds.MetricInfo(-1299427322302314244L, 3));
        metricIds.put("job_scheduler.value_hist_initial_job_estimated_network_download_kilobytes", new com.android.modules.expresslog.MetricIds.MetricInfo(-785066281506175156L, 2));
        metricIds.put("job_scheduler.value_hist_initial_job_estimated_network_upload_kilobytes", new com.android.modules.expresslog.MetricIds.MetricInfo(-5363480020640374054L, 2));
        metricIds.put("job_scheduler.value_hist_initial_jwi_estimated_network_download_kilobytes", new com.android.modules.expresslog.MetricIds.MetricInfo(3599293282843442428L, 2));
        metricIds.put("job_scheduler.value_hist_initial_jwi_estimated_network_upload_kilobytes", new com.android.modules.expresslog.MetricIds.MetricInfo(1703249122821912364L, 2));
        metricIds.put("job_scheduler.value_hist_job_concurrency", new com.android.modules.expresslog.MetricIds.MetricInfo(7022862059858276673L, 2));
        metricIds.put("job_scheduler.value_hist_scheduled_job_30_min_high_water_mark", new com.android.modules.expresslog.MetricIds.MetricInfo(-6580875291944158750L, 2));
        metricIds.put("job_scheduler.value_hist_transferred_network_download_kilobytes_high_water_mark", new com.android.modules.expresslog.MetricIds.MetricInfo(-8626132665475984961L, 2));
        metricIds.put("job_scheduler.value_hist_transferred_network_upload_kilobytes_high_water_mark", new com.android.modules.expresslog.MetricIds.MetricInfo(-6725069608641059563L, 2));
        metricIds.put("job_scheduler.value_hist_updated_estimated_network_download_kilobytes", new com.android.modules.expresslog.MetricIds.MetricInfo(-1643579595435861571L, 2));
        metricIds.put("job_scheduler.value_hist_updated_estimated_network_upload_kilobytes", new com.android.modules.expresslog.MetricIds.MetricInfo(-7398595476439352536L, 2));
        metricIds.put("job_scheduler.value_hist_w_uid_enqueued_work_items_at_job_start", new com.android.modules.expresslog.MetricIds.MetricInfo(-6217913081941281407L, 4));
        metricIds.put("job_scheduler.value_hist_w_uid_enqueued_work_items_high_water_mark", new com.android.modules.expresslog.MetricIds.MetricInfo(6347971958352818350L, 4));
        metricIds.put("job_scheduler.value_hist_w_uid_job_minimum_chunk_kilobytes", new com.android.modules.expresslog.MetricIds.MetricInfo(924428326049431555L, 4));
        metricIds.put("job_scheduler.value_hist_w_uid_jwi_minimum_chunk_kilobytes", new com.android.modules.expresslog.MetricIds.MetricInfo(3314117374945417107L, 4));
        metricIds.put("job_scheduler.value_job_quota_reduced_due_to_buggy_uid", new com.android.modules.expresslog.MetricIds.MetricInfo(2090030468725226029L, 3));
        metricIds.put("job_scheduler.value_job_scheduler_job_deadline_expired_counter", new com.android.modules.expresslog.MetricIds.MetricInfo(-4904009711020652546L, 1));
        metricIds.put("screen.value_dim_to_screen_off", new com.android.modules.expresslog.MetricIds.MetricInfo(-8437355397311467341L, 1));
        metricIds.put("screen.value_undim", new com.android.modules.expresslog.MetricIds.MetricInfo(673292744997347922L, 1));
        metricIds.put("speech_recognition.value_exceed_service_connections_count", new com.android.modules.expresslog.MetricIds.MetricInfo(4137901124994142032L, 3));
        metricIds.put("speech_recognition.value_exceed_session_count", new com.android.modules.expresslog.MetricIds.MetricInfo(-2798805470646689619L, 3));
        metricIds.put("stability_anr.value_skipped_anrs", new com.android.modules.expresslog.MetricIds.MetricInfo(-1686340313630649786L, 1));
        metricIds.put("stability_anr.value_total_anrs", new com.android.modules.expresslog.MetricIds.MetricInfo(-4448413402479670208L, 1));
        metricIds.put("stability_errors.value_dropbox_buffer_expired_count", new com.android.modules.expresslog.MetricIds.MetricInfo(-6509578091451690387L, 1));
        metricIds.put("statsd_errors.value_report_vendor_atom_errors_count", new com.android.modules.expresslog.MetricIds.MetricInfo(6758996126291769555L, 1));
        metricIds.put("tex_test.value_telemetry_express_fixed_range_histogram", new com.android.modules.expresslog.MetricIds.MetricInfo(-8323169799906496731L, 2));
        metricIds.put("tex_test.value_telemetry_express_fixed_range_histogram_with_uid", new com.android.modules.expresslog.MetricIds.MetricInfo(6023834309724625512L, 4));
        metricIds.put("tex_test.value_telemetry_express_scaled_factor_histogram", new com.android.modules.expresslog.MetricIds.MetricInfo(3864259057208837246L, 2));
        metricIds.put("tex_test.value_telemetry_express_scaled_range_histogram_with_uid", new com.android.modules.expresslog.MetricIds.MetricInfo(7629488784689754667L, 4));
        metricIds.put("tex_test.value_telemetry_express_test_counter", new com.android.modules.expresslog.MetricIds.MetricInfo(-2003432401640472195L, 1));
        metricIds.put("tex_test.value_telemetry_express_test_counter_with_uid", new com.android.modules.expresslog.MetricIds.MetricInfo(4877884967786456322L, 3));
        metricIds.put("vibrator.value_perform_haptic_feedback_keyboard", new com.android.modules.expresslog.MetricIds.MetricInfo(-6578167211361419381L, 3));
        metricIds.put("virtual_devices.value_activity_blocked_count", new com.android.modules.expresslog.MetricIds.MetricInfo(-7079715898906024559L, 3));
        metricIds.put("virtual_devices.value_secure_window_blocked_count", new com.android.modules.expresslog.MetricIds.MetricInfo(381600291587716893L, 3));
        metricIds.put("virtual_devices.value_virtual_audio_created_count", new com.android.modules.expresslog.MetricIds.MetricInfo(216469278413724750L, 3));
        metricIds.put("virtual_devices.value_virtual_camera_created_count", new com.android.modules.expresslog.MetricIds.MetricInfo(2669713370281136655L, 3));
        metricIds.put("virtual_devices.value_virtual_devices_created_count", new com.android.modules.expresslog.MetricIds.MetricInfo(849190108476587824L, 1));
        metricIds.put("virtual_devices.value_virtual_devices_created_with_uid_count", new com.android.modules.expresslog.MetricIds.MetricInfo(-8043783159667542734L, 3));
        metricIds.put("virtual_devices.value_virtual_display_created_count", new com.android.modules.expresslog.MetricIds.MetricInfo(-8328274662193080522L, 3));
        metricIds.put("virtual_devices.value_virtual_dpad_created_count", new com.android.modules.expresslog.MetricIds.MetricInfo(-7554867509149945123L, 3));
        metricIds.put("virtual_devices.value_virtual_keyboard_created_count", new com.android.modules.expresslog.MetricIds.MetricInfo(-4281936962231424349L, 3));
        metricIds.put("virtual_devices.value_virtual_mouse_created_count", new com.android.modules.expresslog.MetricIds.MetricInfo(1498259102425665167L, 3));
        metricIds.put("virtual_devices.value_virtual_navigationtouchpad_created_count", new com.android.modules.expresslog.MetricIds.MetricInfo(-8314108585791227748L, 3));
        metricIds.put("virtual_devices.value_virtual_sensors_created_count", new com.android.modules.expresslog.MetricIds.MetricInfo(6487611638632272886L, 3));
        metricIds.put("virtual_devices.value_virtual_stylus_created_count", new com.android.modules.expresslog.MetricIds.MetricInfo(-7217071694244830849L, 3));
        metricIds.put("virtual_devices.value_virtual_touchscreen_created_count", new com.android.modules.expresslog.MetricIds.MetricInfo(8525699099567515279L, 3));
    }

    static long getMetricIdHash(java.lang.String str, int i) {
        com.android.modules.expresslog.MetricIds.MetricInfo metricInfo = metricIds.get(str);
        if (metricInfo == null) {
            throw new java.lang.IllegalArgumentException("Metric is undefined " + str);
        }
        if (metricInfo.mType != i) {
            throw new java.util.InputMismatchException("Metric type is not " + i);
        }
        return metricInfo.mHash;
    }
}
