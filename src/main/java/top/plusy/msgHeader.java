package top.plusy;

public class msgHeader {
    public String devId;
    public int timestamp;
    public int msgId;
    public int orderId;
    public int length;

    msgHeader(String devId, int timestamp, int msgId, int orderId)
    {
        this.devId = devId; //15
        this.timestamp = timestamp; //4
        this.msgId = msgId; //1
        this.orderId = orderId; //4
        this.length = 24;
    }

    msgHeader(int msgId, int orderId)
    {
        this.msgId = msgId; //1
        this.orderId = orderId; //4
        this.length = 5;
    }
}
