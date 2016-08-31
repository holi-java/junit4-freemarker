package com.holi.junit.scanner;

import com.holi.junit.Action;
import java.io.IOException;
import java.io.Reader;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;
import static org.junit.internal.matchers.ThrowableMessageMatcher.hasMessage;

/**
 * Created by selonj on 16-9-1.
 */
public class ScriptFileTest {
  @Rule
  public final TemporaryFolder folder = new TemporaryFolder();

  @Test public void closeScriptAutomaticallyWhenFinished() throws Exception {
    ScriptFile file = new ScriptFile(folder.getRoot(), folder.newFile());

    Reader reader = file.open(returnScriptReader());

    try {
      reader.read();
      fail("should failed");
    } catch (IOException expected) {
      assertThat(expected, hasMessage(containsString("closed")));
    }
  }

  private Action<Reader, Reader> returnScriptReader() {
    return new Action<Reader, Reader>() {
      @Override public Reader call(Reader it) throws IOException {
        return it;
      }
    };
  }
}