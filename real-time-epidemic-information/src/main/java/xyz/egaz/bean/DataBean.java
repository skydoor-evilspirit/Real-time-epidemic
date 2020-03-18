package xyz.egaz.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data@AllArgsConstructor@NoArgsConstructor
@TableName("illness")
public class DataBean implements Serializable {

    private Long id;
    private String area;
    private int confirmAdd;
    private int confirm;
    private int suspect;
    private int dead;
    private int heal;
}
