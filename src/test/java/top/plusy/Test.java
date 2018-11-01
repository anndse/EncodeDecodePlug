package top.plusy;

import net.sf.json.JSONObject;

public class Test {
    public static void main(String args[])
    {
        String testPubVer = "ODYxNjM4MDM0MTc0NzUwW639bg4AAAAAEVRLMDVfU1dfVjIuMC1CMDEy";
        String testGetAgps = "ODYxNjM4MDM0MTc0NzUwW67s/AsAAAACAA==";
        String testGetFeets = "ODYxNjM4MDM0MTc0NzUwW65QAA0AAAABAAAAAA==";
        String testLoc = "ODYxNjM4MDM0MTc0NzUwW676zRAAAAAEAAAAAAAAAAAAAAAAQQcABQ==";

        String testBell = "{\"msgId\":3, \"orderId\":12,\"ringCount\":1}";
        String testConfig = "{\"msgId\":4, \"orderId\":12,\"rideInterval\":300}";
        String test258 = "{\"msgId\":5, \"orderId\":12}";
        String testSyncTime = "{\"msgId\":7, \"orderId\":23,\"rOrderId\":12,\"timestamp\":1524821789,\"timezone\":20}";
        String testSetDomain="{\"msgId\":9, \"orderId\":23, \"domain\": \"192.168.1.18\", \"domainLen\":12, \"domainType\":1, \"port\":80}";
        String testAgpsData="{ \"msgId\": 1, \"rOrderId\": 1, \"packs\": 11, \"index\": 11, \"dataLen\": 480, \"data\": \"AAAnAAABKl0AAH3WFKVbeSkBGieIn/klGP5IyJTVlBVAH7C0//95ES4oKez//2kdAABOTAAA5fT//4kAAAC+AAAAAAAAAINptWITA1gAAQAMAAABAAAQVwAAI5RQACpdAAAaAAACKl0AAI3gFKXpIEsB2mvMm9Umuv4fac/6sxbUHhO0//9OFiIo8Oz//3MdAACKSwAAUPT//1cAAACiAAAAAAAAAOedtWITA1gAAQANAAAAAABLq///EeDa/ypdAACc/wABKl0AAF2v7cp3w7sBk0OrgWsLiv6urIvFFbCLMyTp//9p/egnYpT//8QZAACkKQAAwp3//0r////a/f//AAAAAGFPtWITA1gAAQAOAAAAAADcUgEAD/c2ACpdAABAAAABKl0AAJ/sFKWHVfYA5zQ5qikqZwOnWr54+HXhc962//9tvQ0n1/z//8JGAAA5LwAAG/7//zAAAACo////AAAAAB02\" }";

        JSONObject jsonObject = EncodeDecodePlug.decodeFromBase64(testPubVer);
        System.out.println("INPUT: " + testPubVer);
        System.out.println("OUTPUT:" + jsonObject.toString());

        jsonObject = EncodeDecodePlug.decodeFromBase64(testGetAgps);
        System.out.println("INPUT: " + testGetAgps);
        System.out.println("OUTPUT:" + jsonObject.toString());

        jsonObject = EncodeDecodePlug.decodeFromBase64(testGetFeets);
        System.out.println("INPUT: " + testGetAgps);
        System.out.println("OUTPUT:" + jsonObject.toString());

        jsonObject = EncodeDecodePlug.decodeFromBase64(testLoc);
        System.out.println("INPUT: " + testLoc);
        System.out.println("OUTPUT:" + jsonObject.toString());

        String base64date = EncodeDecodePlug.encodeFromJsonString(testBell);
        System.out.println("INPUT: " + testBell);
        System.out.println("OUTPUT:" + base64date);

        base64date = EncodeDecodePlug.encodeFromJsonString(testConfig);
        System.out.println("INPUT: " + testConfig);
        System.out.println("OUTPUT:" + base64date);

        base64date = EncodeDecodePlug.encodeFromJsonString(test258);
        System.out.println("INPUT: " + test258);
        System.out.println("OUTPUT:" + base64date);

        base64date = EncodeDecodePlug.encodeFromJsonString(testSyncTime);
        System.out.println("INPUT: " + testSyncTime);
        System.out.println("OUTPUT:" + base64date);

        base64date = EncodeDecodePlug.encodeFromJsonString(testSetDomain);
        System.out.println("INPUT: " + testSetDomain);
        System.out.println("OUTPUT:" + base64date);

        base64date = EncodeDecodePlug.encodeFromJsonString(testAgpsData);
        System.out.println("INPUT: " + testAgpsData);
        System.out.println("OUTPUT:" + base64date);
    }
}
