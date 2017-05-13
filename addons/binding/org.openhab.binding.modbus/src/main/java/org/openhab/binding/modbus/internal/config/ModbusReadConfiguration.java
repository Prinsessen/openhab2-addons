package org.openhab.binding.modbus.internal.config;

public class ModbusReadConfiguration {
    private int start;
    private String trigger;
    private String transform;
    private String valueType;
    private boolean postReadErrorsAsUndefined;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public String getTrigger() {
        return trigger;
    }

    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }

    public String getTransform() {
        return transform;
    }

    public void setTransform(String transform) {
        this.transform = transform;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public boolean getPostReadErrorsAsUndefined() {
        return postReadErrorsAsUndefined;
    }

    public void setPostReadErrorsAsUndefined(boolean postReadErrorsAsUndefined) {
        this.postReadErrorsAsUndefined = postReadErrorsAsUndefined;
    }

}
