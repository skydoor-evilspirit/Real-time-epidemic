package xyz.egaz.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@AllArgsConstructor@NoArgsConstructor
public class GraphBean {

    private Long id;
    private String date;
    private double confirm;
    private double suspect;
}
