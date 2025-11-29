package model;

public class EVAttributes {
    private long lastChargeInMs = -1;
    private long totalChargingMinutes = 0;

    public synchronized void startChargingIfNeeded() {
        if (lastChargeInMs == -1) {
            lastChargeInMs = System.currentTimeMillis();
        }
    }

    public synchronized void stopChargingIfNeeded() {
        if (lastChargeInMs != -1) {
            totalChargingMinutes += (System.currentTimeMillis() - lastChargeInMs) / 60000L;
            lastChargeInMs = -1;
        }
    }

    public synchronized long getTotalChargingMinutes() {
        if (lastChargeInMs != -1) {
            totalChargingMinutes += (System.currentTimeMillis() - lastChargeInMs) / 60000L;
            lastChargeInMs = System.currentTimeMillis();
        }
        return totalChargingMinutes;
    }

    public synchronized void clear() {
        lastChargeInMs = -1;
        totalChargingMinutes = 0;
    }
}
