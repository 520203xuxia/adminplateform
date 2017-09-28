package cn.edu.szu.bigdata.entity;

import com.alibaba.druid.filter.AutoLoad;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by longhao on 2017/9/27.
 * Email: longhao1@email.szu.edu.cn
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SegmentEntity implements Serializable {
    int id;
    int project_id;
    String segment_name;
    String segment_content;
    String project_name;
}
