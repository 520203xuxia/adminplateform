package cn.edu.szu.bigdata.entity;

import java.io.Serializable;

/**
 * Created by longhao on 2017/8/29.
 * Email: longhao1@email.szu.edu.cn
 */
public class SegmentEntity implements Serializable {
    /**
     * 段落编号
     */
    float id;

    /**
     * 段落名称
     */
    String segment_name;

    /**
     * 段落填写简介
     */
    String segment_desc;

    public float getId() {
        return id;
    }

    public void setId(float id) {
        this.id = id;
    }

    public String getSegment_name() {
        return segment_name;
    }

    public void setSegment_name(String segment_name) {
        this.segment_name = segment_name;
    }

    public String getSegment_desc() {
        return segment_desc;
    }

    public void setSegment_desc(String segment_desc) {
        this.segment_desc = segment_desc;
    }
}
