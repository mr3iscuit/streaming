package org.streaming.Audio;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(
            nullable = false,
            updatable = false
    )

    private String fileType;
    private Long fileSize;
    private Long sampleRate;

    @OneToMany(mappedBy = "file", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FileChunk> chunksID;

    @OneToOne(mappedBy = "file")
    private AudioEntity audio;
}