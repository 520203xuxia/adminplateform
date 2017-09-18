package cn.edu.szu.bigdata.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by longhao on 2017/9/4.
 * Email: longhao1@email.szu.edu.cn
 */

@Setter
@Getter
@NoArgsConstructor
@ToString
public class FragmentEntity {
    String projectName;
    String segmentName;
    String segmentContent;

}
