package tn.louay.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class PostOffer {
    private String title;
    private String company;
    private String skills;
    private String description;
}
