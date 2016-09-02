package com.holi.junit.scanner;

import com.holi.junit.Action;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.junit.internal.matchers.ThrowableMessageMatcher.hasMessage;

/**
 * Created by selonj on 16-9-1.
 */
public class ScriptFileTest {
  @Rule
  public final TemporaryFolder folder = new TemporaryFolder();

  @Test public void closeScriptAutomaticallyWhenFinished() throws Exception {
    ScriptFile file = script(folder.newFile());

    Reader reader = file.open(returnScriptReader());

    try {
      reader.read();
      fail("should failed");
    } catch (IOException expected) {
      assertThat(expected, hasMessage(containsString("closed")));
    }
  }

  @Test public void twoScriptsAreEqualsIfTheirScriptFileAreTheSame() throws Exception {
    File file1 = folder.newFile("test.ftl");
    File file2 = folder.newFile("test2.ftl");

    assertThat(script(file1), equalTo(script(file1)));
    assertThat(script(file1), not(equalTo(script(file2))));
    assertThat(script(file1), not(equalTo(null)));
  }

  @Test public void scriptHashCodeEqualToFileHashCode() throws Exception {
    File file = folder.newFile("test.ftl");

    assertThat(script(file).hashCode(), equalTo(file.hashCode()));
  }

  private ScriptFile script(File file) {
    return new ScriptFile(folder.getRoot(), file);
  }

  private Action<Reader, Reader> returnScriptReader() {
    return new Action<Reader, Reader>() {
      @Override public Reader call(Reader it) throws IOException {
        return it;
      }
    };
  }
}