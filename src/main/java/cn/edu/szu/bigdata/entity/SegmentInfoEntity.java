package cn.edu.szu.bigdata.entity;

import lombok.*;

import java.io.Serializable;

/**
 * Created by longhao on 2017/8/29.
 * Email: longhao1@email.szu.edu.cn
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SegmentInfoEntity implements Serializable {
    /**
     * 段落编号
     */
    int id;

    /**
     * 段落名称
     */
    String segment_name;

    /**
     * 段落填写简介
     */
    String segment_desc;

    /**
     * 父资源id
     */
    int parentid;

    /**
     * 类型
     */

    int type;

}
