package org.streaming.Audio;

import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Setter
public class AudioGetDTO {
    private Long id;
    private LocalDateTime uploadDate;
    private LocalDateTime lastModified;
    private String title;
    private String artist;
    private String album;
    private String releaseYear;
    private String genre;
    private String trackNumber;
    private FileGetDTO file;

    public static AudioGetDTO buildFromAudioEntity(@NotNull AudioEntity audioEntity) {

        FileGetDTO fileGetDTO = null;
        FileEntity file = audioEntity.getFile();

        if (file != null) {
            fileGetDTO = FileGetDTO.buildFromFileEntity(file);
        }

        return AudioGetDTO.builder()
                .id(audioEntity.getId())
                .uploadDate(audioEntity.getUploadDate())
                .lastModified(audioEntity.getLastModified())

                .title(audioEntity.getTitle())
                .artist(audioEntity.getArtist())
                .album(audioEntity.getAlbum())
                .releaseYear((audioEntity.getReleaseYear()))
                .genre(audioEntity.getGenre())
                .trackNumber(audioEntity.getTrackNumber())
                .file(fileGetDTO)
                .build();
    }
}