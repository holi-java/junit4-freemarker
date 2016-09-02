package com.holi.junit.scanner;

import com.holi.junit.Script;
import java.io.File;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runners.model.TestClass;

import static com.holi.junit.freemarker.matchers.ScriptMatchers.scriptThatIs;
import static com.holi.junit.freemarker.matchers.ScriptMatchers.scriptWithBaseDir;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by selonj on 16-9-1.
 */
public class FileSystemScriptScannerTest {
  private static final TestClass UNUSED_TEST_CLASS = null;
  @Rule
  public final TemporaryFolder folder = new TemporaryFolder();

  private FileSystemScriptScanner scanner(File baseDir) {
    return new FileSystemScriptScanner(baseDir, ScriptMatchers.endsWith(".ftl"));
  }

  @Test public void scanAllMatchedScriptsRecursively() throws Exception {
    folder.newFile("test.ftl");
    folder.newFile("other.txt");
    folder.newFolder("sub");
    folder.newFile("sub/test.ftl");
    File baseDir = folder.getRoot();

    List<Script> scripts = scanner(baseDir).scan(UNUSED_TEST_CLASS);

    assertThat(scripts, hasSize(2));
    assertThat(scripts, everyItem(scriptWithBaseDir(baseDir)));
    assertThat(scripts, hasItem(scriptThatIs("test.ftl")));
    assertThat(scripts, hasItem(scriptThatIs("sub/test.ftl")));
  }

  @Test public void returnEmptyScriptsIfBaseDirNotExists() throws Exception {
    List<Script> scripts = scanner(new File(folder.getRoot(), "directory is not exists")).scan(UNUSED_TEST_CLASS);

    assertThat(scripts, is(empty()));
  }
}