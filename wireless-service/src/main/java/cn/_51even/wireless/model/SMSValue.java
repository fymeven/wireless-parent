package cn._51even.wireless.model;

import java.util.Date;

public class SMSValue {

    /**
     * 类型：0:电瓶车 1：汽车
     */
    private int type;

    /**
     * 信号频率
     */
    private int signalFrequency;

    /**
     * 信号类型： 0:ASK 1:FSK
     */
    private int signalType;

    /**
     * 信号强度
     */
    private int signalStrength;

    /**
     * 时间（时间戳）
     */
    private Date recordTime;

    /**
     * 信号长度
     */
    private int signalLength;

    /**
     * 信号数据
     */
    private String signalData;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSignalFrequency() {
        return signalFrequency;
    }

    public void setSignalFrequency(int signalFrequency) {
        this.signalFrequency = signalFrequency;
    }

    public int getSignalType() {
        return signalType;
    }

    public void setSignalType(int signalType) {
        this.signalType = signalType;
    }

    public int getSignalStrength() {
        return signalStrength;
    }

    public void setSignalStrength(int signalStrength) {
        this.signalStrength = signalStrength;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public int getSignalLength() {
        return signalLength;
    }

    public void setSignalLength(int signalLength) {
        this.signalLength = signalLength;
    }

    public String getSignalData() {
        return signalData;
    }

    public void setSignalData(String signalData) {
        this.signalData = signalData;
    }
}
