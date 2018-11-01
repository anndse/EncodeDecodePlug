package top.plusy;

public class util {
    public static int ByteArrayToInt(byte[] bArr, int offset) {
        int r = 0;
        if(bArr.length-offset < 4){
            return -1;
        }
        r = ((((bArr[offset] & 0xff) << 24)| ((bArr[offset+1] & 0xff) << 16)
                | ((bArr[offset+2] & 0xff) << 8) | ((bArr[offset+3] & 0xff))));
        return r;
    }

    public static byte[] IntToByteArray(int v)
    {
        byte []binary = new byte[4];

        binary[0] = (byte)(v >> 24);
        binary[1] = (byte)(v >> 16);
        binary[2] = (byte)(v >> 8);
        binary[3] = (byte)(v);

        return binary;
    }
}

