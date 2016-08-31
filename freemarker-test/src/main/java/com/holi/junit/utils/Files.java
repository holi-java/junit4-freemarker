package com.holi.junit.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

/**
 * Created by selonj on 16-9-1.
 */
public class Files {
  public static Reader open(File file) throws FileNotFoundException {
    return new BufferedReader(new FileReader(file));
  }

  public static File file(String pathname) {
    return new File(pathname);
  }
}
