package com.holi.junit.scanner;

import com.holi.junit.Action;
import com.holi.junit.Script;
import com.holi.junit.utils.Files;
import java.io.File;
import java.io.IOException;
import java.io.Reader;

/**
 * Created by selonj on 16-9-1.
 */
public class ScriptFile implements Script {
  private File file;
  private File root;

  public ScriptFile(File baseDir, File script) {
    this.file = script;
    root = baseDir;
  }

  @Override public String getName() {
    return scriptName(file);
  }

  private String scriptName(File file) {
    return file.getPath().substring(baseDir().getPath().length() + 1);
  }

  @Override public File baseDir() {
    return root;
  }

  @Override public <R> R open(Action<Reader, R> action) throws IOException {
    try (Reader it = Files.open(file)) {
      return action.call(it);
    }
  }

  @Override public boolean equals(Object obj) {
    if (!(obj instanceof ScriptFile)) return false;
    ScriptFile that = (ScriptFile) obj;
    return file.equals(that.file);
  }

  @Override public int hashCode() {
    return file.hashCode();
  }
}
