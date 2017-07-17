package consumers;


import com.aliyun.openservices.shade.com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangziqing on 17/7/17.
 */
public class Test {

    public static void main(String[] args) {
        Map map = new HashMap();
        map.put("qq","wwww");
        map.put("ee","ddddd");

        long s = System.currentTimeMillis() ;
        for(int i=0;i<1;i++){
            byte[] raw = JSONObject.toJSONBytes(map);
            JSONObject.parseObject(raw,Map.class);
        }
        System.out.println(System.currentTimeMillis() - s);
    }
}
