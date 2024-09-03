package org.streaming.Audio;

import lombok.*;

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
}