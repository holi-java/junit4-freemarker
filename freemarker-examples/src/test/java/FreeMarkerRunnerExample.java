import com.holi.junit.Scanner;
import com.holi.junit.ScriptRunner;
import com.holi.junit.ScriptScanner;
import com.holi.junit.scanner.FileSystemScriptScanner;
import org.junit.runner.RunWith;

import static com.holi.junit.scanner.ScriptMatchers.allOf;
import static com.holi.junit.scanner.ScriptMatchers.endsWith;
import static com.holi.junit.scanner.ScriptMatchers.not;
import static com.holi.junit.scanner.ScriptMatchers.startsWith;
import static com.holi.junit.utils.Files.file;

/**
 * Created by selonj on 16-8-31.
 */
@RunWith(ScriptRunner.class)
public class FreeMarkerRunnerExample {

  public static @Scanner ScriptScanner scanner() {
    return new FileSystemScriptScanner(file("src/test/resources"),
        allOf(not(startsWith("_")), endsWith(".ftl")));
  }
}
