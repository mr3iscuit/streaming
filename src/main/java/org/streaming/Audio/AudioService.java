package org.streaming.Audio;

import lombok.AllArgsConstructor;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AudioService {
    private AudioRepository audioRepo;
    private FileRepository fileRepo;
    private FileChunkRepository fileChunkRepository;

    private AudioGetDTO buildAudioGetDTO(AudioEntity audio) {
        FileEntity file = audio.getFile();
        FileGetDTO fileGetDTO = null;

        if (file != null) {
            fileGetDTO =
                    FileGetDTO.builder()
                            .id(file.getId())
                            .fileType(file.getFileType())
                            .fileSize(file.getFileSize())
                            .chunksID(file.getChunksID() != null ?
                                    file.getChunksID().stream().map(FileChunk::getId).collect(Collectors.toList()) :
                                    new ArrayList<>())
                            .build();
        }

        AudioGetDTO res = AudioGetDTO.builder()
                .id(audio.getId())
                .uploadDate(audio.getUploadDate())
                .lastModified(audio.getLastModified())

                .title(audio.getTitle())
                .artist(audio.getArtist())
                .album(audio.getAlbum())
                .releaseYear((audio.getReleaseYear()))
                .genre(audio.getGenre())
                .trackNumber(audio.getTrackNumber())
                .file(fileGetDTO)
                .build();

        res.setFile(fileGetDTO);

        return res;
    }

    public AudioGetDTO getAudioByID(Long id) {
        AudioEntity audio = audioRepo.findById(id).orElseThrow(() -> new OpenApiResourceNotFoundException("No audio found by id: " + id));

        return buildAudioGetDTO(audio);
    }

    public List<AudioGetDTO> getAllAudio() {
        return audioRepo.findAll().stream().map(this::buildAudioGetDTO).collect(Collectors.toList());
    }

    public AudioEntity saveAudio(AudioPostDTO poReq) {

        FileEntity fileEntity = new FileEntity();

        AudioEntity audio = AudioEntity.builder()
                .title(poReq.getTitle())
                .artist(poReq.getArtist())
                .album(poReq.getAlbum())
                .releaseYear(poReq.getReleaseYear())
                .genre(poReq.getGenre())
                .trackNumber(poReq.getTrackNumber())
                .build();

        fileEntity = new FileEntity().builder()
                .sampleRate(poReq.getFile().getSampleRate())
                .fileType(poReq.getFile().getFileType())
                .fileSize(poReq.getFile().getFileSize())
                .build();

        audio.setFile( fileRepo.save(fileEntity) );

        LocalDateTime currentDateTime = LocalDateTime.now();

        audio.setUploadDate(currentDateTime);
        audio.setLastModified(currentDateTime);

        return audioRepo.save(audio);
    }

    public AudioEntity update(Long id, AudioPostDTO poReq) {
        AudioEntity audio = audioRepo.findById(id).orElseThrow(() -> new OpenApiResourceNotFoundException("No audio found by id: " + id));

        if (poReq.getTitle() != null) {
            audio.setTitle(poReq.getTitle());
        }

        if (poReq.getArtist() != null) {
            audio.setArtist(poReq.getArtist());
        }

        if (poReq.getAlbum() != null) {
            audio.setAlbum(poReq.getAlbum());
        }

        if (poReq.getGenre() != null) {
            audio.setGenre(poReq.getGenre());
        }

        audio.setLastModified(LocalDateTime.now());

        //TODO patch file from audioDTO
//        FileEntity fileEntity = audio.getFile();
//        audio.setFile(fileEntity);

        return audioRepo.save(audio);
    }

    public void uploadChunk(MultipartFile file, int chunkIndex, Long audioId) throws IOException {
        AudioEntity audioEntity = audioRepo.findById(audioId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid audio ID"));

        FileEntity fileEntity = audioEntity.getFile();

        FileChunk fileChunk = new FileChunk();
        fileChunk.setData(file.getBytes());
        fileChunk.setChunkIndex(chunkIndex);
        fileChunk.setFile(fileEntity);


        fileChunkRepository.save(fileChunk);
    }

    public Optional<FileChunk> downloadChunk(Long audioId, int chunkIndex) {
        AudioEntity audio = audioRepo.findById(audioId).orElseThrow(() -> new OpenApiResourceNotFoundException("No Audio found with id: " + audioId));

        FileEntity file = audio.getFile();

        return fileChunkRepository.findByFile_IdAndChunkIndex(file.getId(), chunkIndex);
    }
}
