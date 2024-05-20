package tn.louay.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetOffer {
    private Integer id;
    private String title;
    private Date createdAt;
    private String company;
    private String skills;
    private String description;
}
