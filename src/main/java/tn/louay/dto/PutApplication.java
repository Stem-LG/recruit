package tn.louay.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import tn.louay.enums.ApplicationAction;

@Data
@AllArgsConstructor
@Builder
public class PutApplication {
    ApplicationAction action;
}
