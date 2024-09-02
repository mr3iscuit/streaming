package org.streaming.Audio;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AudioPostDTO {
    private Long id;
    private String title;
    private String artist;
    private String album;
    private String genre;
    private String releaseYear;
    private String trackNumber;

    private FilePostDTO file;
}
