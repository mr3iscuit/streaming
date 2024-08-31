package org.streaming.Audio;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// @Table(name = "file_chunks")
public class FileChunk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(length = 524288, nullable = false)
    private byte[] data;

    private int chunkIndex;

    @ManyToOne
    @JoinColumn(name = "file_id", nullable = false)
    private FileEntity file;
}
