package org.streaming.Audio;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Builder
@Data
@Getter
public class FileGetDTO {
    private Long fileSize;
    private Long id;
    private String fileType;
    private List<Long> chunksID;
}
