package domain;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;
import java.io.IOException;

public class SourceReaderTest {

    @Test
    public void getFileDirectoryCount() throws IOException {
        asserThat(SourceReader.getFileDirectoryCount()).isEqual(100);
    }
}
