package CJ.CJ.service;

import java.lang.reflect.Field;
import java.sql.Blob;
import java.util.HashMap;

public class StringToByteArray {
    public static int[] blotToShortArray(HashMap<String, Object> params) throws Exception
    {
        Object blobObject = params.get("heartRateByteStream");
        String tempString = blobObject.toString();

        int blobLength = tempString.length();
        int[] unsignedByteArray = new int[blobLength];
        for (int i = 0; i < blobLength; i++)
        {
            unsignedByteArray[i] = tempString.charAt(i) & 0xFF; // 부호 없는 8비트 정수로 변환
        }
        return unsignedByteArray;
    }
}
