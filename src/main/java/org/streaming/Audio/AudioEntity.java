package org.streaming.Audio;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.streaming.filechunk.FileChunk;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(
            nullable = false,
            updatable = false
    )
    private LocalDateTime uploadDate;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModified;

    private String fileName;
    private String fileType;
    private Long fileSize;

    private String title;
    private String artist;
    private String album;
    private String release_year;
    private String genre;
    private String trackNumber;

    @OneToMany(mappedBy = "AudioEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FileChunk> chunks;
}