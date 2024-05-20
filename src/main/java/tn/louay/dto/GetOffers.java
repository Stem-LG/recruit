package tn.louay.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetOffers {
    private Integer id;
    private String title;
    private Date createdAt;
}
