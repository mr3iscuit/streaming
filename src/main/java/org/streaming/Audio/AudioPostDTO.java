package org.streaming.Audio;
import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
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
