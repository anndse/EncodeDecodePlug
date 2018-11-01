package top.plusy;

import net.sf.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import static java.util.Base64.Decoder;
import static java.util.Base64.getDecoder;

public class EncodeDecodePlug {

    private static String encodeFromJson(JSONObject jsonObject)
    {
        int offset = 0;
        String base64data = null;
        int msgId = jsonObject.getInt(Const.msgId);

        switch (msgId)
        {
            case Const.MSGID_BELL_REQ:
            {
                int orderId = jsonObject.getInt(Const.orderId);
                int count = jsonObject.getInt(Const.ringCount);
                byte[] binary = new byte[6];
                binary[offset++] = (byte)msgId;
                System.arraycopy(util.IntToByteArray(orderId), 0, binary, offset, 4);
                offset += 4;
                binary[offset] = (byte)count;
                base64data = Base64.getEncoder().encodeToString(binary);
                break;
            }
            case Const.MSGID_PARAM_REQ:
            {
                int orderId = jsonObject.getInt(Const.orderId);
                int rideInterval = jsonObject.getInt(Const.rideInterval);
                byte[] binary = new byte[9];
                binary[offset++] = (byte)msgId;
                System.arraycopy(util.IntToByteArray(orderId), 0, binary, offset, 4);
                offset += 4;
                System.arraycopy(util.IntToByteArray(rideInterval), 0, binary, offset, 4);
                base64data = Base64.getEncoder().encodeToString(binary);
                break;
            }
            case Const.MSGID_GET_PARAMS:
            case Const.MSGID_CLEAR_FEETS:
            case Const.MSGID_FOTA_REQ:
            {
                int orderId = jsonObject.getInt(Const.orderId);
                byte[] binary = new byte[5];
                binary[offset++] = (byte)msgId;
                System.arraycopy(util.IntToByteArray(orderId), 0, binary, offset, 4);
                base64data = Base64.getEncoder().encodeToString(binary);
                break;
            }
            case Const.MSGID_TIME_REPLY:
            {
                ////msgid-1,orderId-4,rOrderId-4, timestamp-4, timezone-1
                byte[] binary = new byte[14];
                int orderId = jsonObject.getInt(Const.orderId);
                int rOrderId = jsonObject.getInt(Const.rOrderId);
                int timestamp = jsonObject.getInt(Const.TimeStamp);
                int TZ = jsonObject.getInt(Const.timezone);
                binary[offset++] = (byte)msgId;
                System.arraycopy(util.IntToByteArray(orderId), 0, binary, offset, 4);
                offset += 4;
                System.arraycopy(util.IntToByteArray(rOrderId), 0, binary, offset, 4);
                offset += 4;
                System.arraycopy(util.IntToByteArray(timestamp), 0, binary, offset, 4);
                offset += 4;
                binary[offset] = (byte)TZ;
                base64data = Base64.getEncoder().encodeToString(binary);
                break;
            }
            case Const.MSGID_SET_HOST:
            {
                //msgId-1,orderId-4, domainType-1, port-2, domainLen-1, domain-x
                int orderId = jsonObject.getInt(Const.orderId);
                int domainType = jsonObject.getInt(Const.domainType);
                int port = jsonObject.getInt(Const.port);
                int domainLen = jsonObject.getInt(Const.domainLen);
                String domain = jsonObject.getString(Const.domain);
                byte[] binary = new byte[9+domainLen];
                binary[offset++] = (byte)msgId;
                System.arraycopy(util.IntToByteArray(orderId), 0, binary, offset, 4);
                offset += 4;
                binary[offset++] = (byte)domainType;
                binary[offset++] = (byte)(port>>8);
                binary[offset++] = (byte)port;
                binary[offset++] = (byte)domainLen;
                System.arraycopy(domain.getBytes(), 0, binary, offset, domain.getBytes().length);
                base64data = Base64.getEncoder().encodeToString(binary);
                break;
            }
            case Const.MSGID_AGPS:
            {
                //msgId-1, rOrderId-4, dataLen-2, data-x
                int rOrderId = jsonObject.getInt(Const.rOrderId);
                int dataLen = jsonObject.getInt(Const.dataLen);
                String data = jsonObject.getString(Const.data);
                byte[] binary = new byte[9];
                binary[offset++] = (byte)msgId;
                System.arraycopy(util.IntToByteArray(rOrderId), 0, binary, offset, 4);
                offset += 4;
                binary[offset++] = (byte)(dataLen>>8);
                binary[offset] = (byte)dataLen;
                base64data = Base64.getEncoder().encodeToString(binary);
                base64data = base64data.concat(data);
                break;
            }
            default:
            {
                System.out.println("DO NOT SUPPORT CMD!");
                break;
            }
        }
        return base64data;
    }

    public static String encodeFromJsonString(String jsonString)
    {
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        return encodeFromJson(jsonObject);
    }

    private static JSONObject JsonFromHeader(msgHeader header)
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate(Const.msgId, header.msgId);
        jsonObject.accumulate(Const.orderId, header.orderId);
        if(header.msgId > 10) {
            jsonObject.accumulate(Const.DevId, header.devId);
            jsonObject.accumulate(Const.TimeStamp, header.timestamp);
        }

        return  jsonObject;
    }

    private static JSONObject decodeToJsonFromBinary(byte[] binary, msgHeader header)
    {
        int offset = header.length;
        JSONObject jsonObject = JsonFromHeader(header);

        switch (header.msgId)
        {
            case Const.MSGID_SWVER:
            {
                int verLen = binary[offset++];
                String ver = new String(binary, offset, verLen);
                jsonObject.accumulate(Const.swver, ver);
                break;
            }
            case Const.MSGID_GET_AGPS:
            {
                int args = util.ByteArrayToInt(binary, offset);
                offset += 4;
                jsonObject.accumulate(Const.args, args);
                if(binary.length > offset) {
                    String ver = new String(binary, offset, 4);
                    jsonObject.accumulate(Const.ver, ver);
                }
                break;
            }
            case Const.MSGID_FEETS:
            {
                int steps = util.ByteArrayToInt(binary, offset);
                jsonObject.accumulate(Const.feets, steps);
                break;
            }
            case Const.MSGID_LOC:
            {
                int latitude = util.ByteArrayToInt(binary, offset);
                offset += 4;
                int longitude = util.ByteArrayToInt(binary, offset);
                offset += 4;
                int feets = util.ByteArrayToInt(binary, offset);
                offset += 4;
                int percentage = binary[offset++];
                int csq = binary[offset++];
                int gps_count = binary[offset++];
                int alarmType = binary[offset++];
                jsonObject.accumulate(Const.latitude, latitude);
                jsonObject.accumulate(Const.longitude, longitude);
                jsonObject.accumulate(Const.feets, feets);
                jsonObject.accumulate(Const.battery, percentage);
                jsonObject.accumulate(Const.csq, csq);
                jsonObject.accumulate(Const.satellites, gps_count);
                jsonObject.accumulate(Const.alarmType, alarmType);
                if(binary.length > offset) {
                    int GPSOnTs = util.ByteArrayToInt(binary, offset);
                    offset += 4;
                    int GPSOffTs = util.ByteArrayToInt(binary, offset);
                    offset += 4;
                    int GPSSearch = binary[offset++];
                    StringBuilder CNS = new StringBuilder();
                    int Count = Math.min(GPSSearch, 40);
                    for (int i = 0; i < Count; i++) {
                        CNS.append(String.format("%d,", binary[offset++]));
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    jsonObject.accumulate("GPSOnTs", sdf.format(new Date(GPSOnTs * 1000L)));
                    jsonObject.accumulate("GPSOffTs", sdf.format(new Date(GPSOffTs * 1000L)));
                    jsonObject.accumulate("GPSSearch", GPSSearch);
                    jsonObject.accumulate("CNS", CNS.toString());
                }
                break;
            }
            case Const.MSGID_REPLY_PARAMS:
            {
                int rideInterval = util.ByteArrayToInt(binary, offset);
                jsonObject.accumulate(Const.rideInterval, rideInterval);
                break;
            }
            case Const.MSGID_GET_TIME:
            {
                // no body
                break;
            }
            case Const.MSGID_BELL_REQ:
            {
                int ringCount = binary[offset];
                jsonObject.accumulate(Const.ringCount, ringCount);
                break;
            }
            case Const.MSGID_PARAM_REQ:
            {
                int rideInterval = util.ByteArrayToInt(binary, offset);
                jsonObject.accumulate(Const.rideInterval, rideInterval);
                break;
            }
            case Const.MSGID_GET_PARAMS:
            case Const.MSGID_CLEAR_FEETS:
            case Const.MSGID_FOTA_REQ:
            {
                //nobody
                break;
            }
            case Const.MSGID_TIME_REPLY:
            {
                int rOrderId = util.ByteArrayToInt(binary, offset);
                offset += 4;
                int timestamp = util.ByteArrayToInt(binary, offset);
                offset += 4;
                int TZ = binary[offset];

                jsonObject.accumulate(Const.rOrderId, rOrderId);
                jsonObject.accumulate(Const.TimeStamp, timestamp);
                jsonObject.accumulate(Const.timezone, TZ);
                break;
            }
            case Const.MSGID_SET_HOST:
            {
                int domainType = binary[offset++];
                int port = (binary[offset++]<<8) + binary[offset++];
                int dlen = binary[offset++];
                String domain = new String(binary, offset, dlen);
                jsonObject.accumulate(Const.domain, domain);
                jsonObject.accumulate(Const.port, port);
                jsonObject.accumulate(Const.domainLen, dlen);
                jsonObject.accumulate(Const.domainType, domainType);
                break;
            }
            case Const.MSGID_AGPS:
            {
                int dataLen = (binary[offset] << 8) + binary[offset++];
                String base64data = new String(binary, offset, dataLen);
                jsonObject.accumulate(Const.dataLen, dataLen);
                jsonObject.accumulate(Const.data, base64data);
                break;
            }
        }
        return jsonObject;
    }

    public static JSONObject decodeFromBase64(String base64)
    {
        int offset = 0;

        Decoder decoder;
        msgHeader header;
        decoder = getDecoder();
        byte[] binary = decoder.decode(base64);

        int msgId = binary[offset++];
        if(msgId < 10)//dl
        {
            int orderId = util.ByteArrayToInt(binary, offset);
            header = new msgHeader(msgId, orderId);
        }
        else { //up
            String devId = new String(binary, offset, 15);
            offset += 15;
            int timestamp = util.ByteArrayToInt(binary, offset);
            offset += 4;
            int orderId = util.ByteArrayToInt(binary, offset);
            header = new msgHeader(devId, timestamp, msgId, orderId);
        }

        return decodeToJsonFromBinary(binary, header);
    }
}
