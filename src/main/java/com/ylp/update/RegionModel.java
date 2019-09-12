/**
 * 保存数据的数据模型。
 */
package com.ylp.update;
public class RegionModel {
    //行政区划代码
    private String id;
   // 区域名称
    private String street_name;
    // 区域父节点区划代码（id）
    private String parent_id;

    public String getId() {
        return id;
    }

    public RegionModel(String id, String street_name, String parent_id) {
        this.id = id;
        this.street_name = street_name;
        this.parent_id = parent_id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStreet_name() {
        return street_name;
    }

    public void setStreet_name(String street_name) {
        this.street_name = street_name;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }
}
