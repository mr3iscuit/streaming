package org.streaming.Audio;

import jakarta.persistence.EntityExistsException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AudioService {
    private AudioRepository audioRepo;
    private FileRepository fileRepo;

    private AudioGetDTO buildAudioGetDTO(AudioEntity audio) {
        FileEntity file = audio.getFile();
        FileGetDTO fileGetDTO = null;

        if (file != null) {
            fileGetDTO =
                    FileGetDTO.builder()
                            .id(file.getId())
                            .uploadDate(file.getUploadDate())
                            .fileType(file.getFileType())
                            .fileSize(file.getFileSize())
                            .chunks(file.getChunks() != null ?
                                    file.getChunks().stream().map(FileChunk::getId).collect(Collectors.toList()) :
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

        AudioEntity newAudio = AudioEntity.builder()
                .title(poReq.getTitle())
                .artist(poReq.getArtist())
                .album(poReq.getAlbum())
                .releaseYear(poReq.getReleaseYear())
                .genre(poReq.getGenre())
                .trackNumber(poReq.getTrackNumber())
                .build();

        LocalDateTime currentDateTime = LocalDateTime.now();

        newAudio.setUploadDate(currentDateTime);
        newAudio.setLastModified(currentDateTime);

        return audioRepo.save(newAudio);
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

        return audioRepo.save(audio);
    }
}
