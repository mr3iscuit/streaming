package org.streaming.Audio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.streaming.Audio.FileChunk;

import java.util.Optional;

public interface FileChunkRepository extends JpaRepository<FileChunk, Long> {
    Optional<FileChunk> findByFile_IdAndChunkIndex(Long fileId, int chunkIndex);

}
