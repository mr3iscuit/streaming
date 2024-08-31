package org.streaming.filechunk;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FileChunkRepository extends JpaRepository<FileChunk, Long> {
    Optional<FileChunk> findByFile_IdAndChunkIndex(Long fileId, int chunkIndex);

}
