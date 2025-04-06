package worker;

import java.time.Instant;

public record FileRecord(String file_path, String extension, Long file_size, String content, String lines) {
}
