package org.streaming.file;

import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.streaming.Audio.AudioEntity;
import org.streaming.Audio.AudioRepository;
import org.streaming.filechunk.FileChunk;
import org.streaming.filechunk.FileChunkRepository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FileService {

    private FileRepository fileRepository;
    private AudioRepository audioRepository;
    private FileChunkRepository fileChunkRepository;

    FileService(
            FileRepository fr,
            AudioRepository ar,
            FileChunkRepository fcr
    ) {
        this.fileRepository = fr;
        this.audioRepository = ar;
        this.fileChunkRepository = fcr;
    }

    public void uploadChunk(MultipartFile file, int chunkIndex, Long audioId) throws IOException {
        AudioEntity audioEntity = audioRepository.findById(audioId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid audio ID"));

        if (audioEntity.getFile() != null) {
            throw new EntityExistsException("Audio already exsists by id: " + audioEntity.getFile().getId());
        }

        FileEntity fileEntity = audioEntity.getFile();

        if (fileEntity == null) {
            fileEntity = new FileEntity();
            fileEntity.setFileType(file.getContentType());
            fileEntity.setFileSize(file.getSize());
            fileEntity.setUploadDate(LocalDateTime.now());
            fileRepository.save(fileEntity);
        }

        FileChunk fileChunk = new FileChunk();
        fileChunk.setData(file.getBytes());
        fileChunk.setChunkIndex(chunkIndex);
        fileChunk.setFile(fileEntity);

        audioEntity.setFile(fileEntity);

        fileChunkRepository.save(fileChunk);
    }

    public Optional<FileChunk> downloadChunk(Long fileId, int chunkIndex) {
        return fileChunkRepository.findByFile_IdAndChunkIndex(fileId, chunkIndex);
    }
}
