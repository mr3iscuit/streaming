package org.streaming.Audio;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;
@Builder
@Data
@Getter
public class FilePostDTO {
    private Long fileSize;
    private String fileType;
    private Long sampleRate;
    private Integer chunkNumber;
    private Integer partialChunkSize;
}
