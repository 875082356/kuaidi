package com.hzgzsoft.logisticsmanage;

/**
 * 作者    楼雄伟
 * 时间    16/2/1 上午12:18
 * 文件    LogisticsManage
 * 描述
 */
public class AddressMessage
{
    private int id;
    private String name;
    private String postNumber;
    private String adrs;

    public AddressMessage()
    {

    }
    public AddressMessage(int id,String name, String postNumber, String adrs)
    {
        this.id = id;
        this.name = name;
        this.postNumber = postNumber;
        this.adrs=adrs;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPostNumber() {
        return postNumber;
    }
    public void setPostNumber(String postNumber) {
        this.postNumber = postNumber;
    }
    public String getAdrs() {
        return adrs;
    }
    public void setAdrs(String adrs) {
        this.adrs = adrs;
    }
}
