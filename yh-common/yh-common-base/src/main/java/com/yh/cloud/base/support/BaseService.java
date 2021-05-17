package com.yh.cloud.base.support;

/**
 * @author yanghan
 * @date 2019/6/24
 */
public class BaseService {

    /**
     * 数字类型主键ID
     * @return
     */
    protected long nextIDLong() {
        return IdGenerator.nextId();
    }

    /**
     * 字符类型主键ID
     * @return
     */
    protected String nextIDString() {
        return 'f' + String.valueOf(IdGenerator.nextId());
    }

    public static void main(String[] args) {
        BaseService baseService = new BaseService();
//        System.out.println(baseService.nextIDLong());
//        System.out.println(new SnowflakeIdWorker(1,1).nextId());

        String name = "312149";
        long tiem = 1563267945000L;
        long id = 641792122860605440L;
        String url = "6eb/0/c/8e81a8901401000/4740d4480a0";
        System.out.println(Long.toHexString(id));
        System.out.println(Long.toOctalString(id));
        System.out.println(Long.toBinaryString(id));
        System.out.println(Long.toUnsignedString(id));
        System.out.println(Long.lowestOneBit(id));
        System.out.println(Long.toUnsignedString(id,32));
        System.out.println(id >> 32);

    }
}
