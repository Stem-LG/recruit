package tn.louay.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import tn.louay.enums.ApplicationStatus;

@Data
@AllArgsConstructor
@Builder
public class GetApplication {
    private Integer id;
    private Date createdAt;
    private String offerTitle;
    private ApplicationStatus status;
    private String name;
    private String email;
    private String motivation;
    private String resumeSummary;
}
