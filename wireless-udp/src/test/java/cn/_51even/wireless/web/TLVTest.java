package cn._51even.wireless.web;

import com.payneteasy.tlv.*;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runner.RunWith;

import java.io.UnsupportedEncodingException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TLVTest {

    @Test
    public void decode(){
        String hexString = "0105efbfbdefbfbdcdabefbfbd644fefbfbd190000efbfbdefbfbd00000000000000000001020304050000";
        //将hex码转成byte字节
        byte[] bytes2 = HexUtil.parseHex(hexString);
        BerTlvParser parser = new BerTlvParser();
        BerTlvs tlvs = parser.parse(bytes2, 0, bytes2.length);

        //如果value的数据类型都一样的花可以通过getList来获取然后便利输出
        List<BerTlv> list = tlvs.getList();
        for (BerTlv berTlv : list) {
            byte[] bytesValue1 = berTlv.getBytesValue();
            String s = null;
            try {
                s = new String(bytesValue1,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            System.out.println(s);
        }

        //也可以指定Tag来获得数据
        BerTlv berTlv = tlvs.find(new BerTag(0x3));
        System.out.println(berTlv.getHexValue());

    }
}
