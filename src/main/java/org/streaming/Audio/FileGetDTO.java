package org.streaming.Audio;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Data
@Getter
public class FileGetDTO {
    private Long fileSize;
    private Integer partialChunkSize;
    private Long id;
    private String fileType;
    private List<Long> chunksID;
    private Integer chunkNumber;

    public static FileGetDTO buildFromFileEntity(FileEntity file) {
        return FileGetDTO.builder()
                .id(file.getId())
                .fileType(file.getFileType())
                .fileSize(file.getFileSize())
                .chunksID(
                        file.getChunksID() != null
                        ? file.getChunksID().stream().map(FileChunk::getId).collect(Collectors.toList())
                        : new ArrayList<>())
                .chunkNumber(file.getChunksID() == null ? 0 : file.getChunksID().size())
                .partialChunkSize(file.getPartialChunkSize())
                .build();
    }
}
