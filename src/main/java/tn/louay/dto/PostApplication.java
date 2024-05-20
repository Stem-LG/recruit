package tn.louay.dto;

import java.io.File;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class PostApplication implements Serializable {
    private String name;
    private String email;
    private File resume;
    private String motivation;
}
