package com.github.mrmks.mc.lmf.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class TransformHelper {

    public static final String PROPERTY = "mrmks.lmf.saveDump";

    static Logger log = LogManager.getLogger("LMF_TH");

    private static File file = null;
    private static boolean willSave = false;
    private static boolean loaded = false;

    public static byte[] saveDump(String name, byte[] bytes) {
        if (!loaded) {
            loaded = true;
            String fileName = System.getProperty(PROPERTY, null);
            willSave = fileName != null;
            if (willSave) {
                fileName = fileName.trim();
                willSave = !fileName.equals("");
                if (willSave) {
                    fileName = fileName.replace('/','\\');
                    if (fileName.endsWith(".class")) fileName = fileName.substring(0, fileName.lastIndexOf('\\'));
                    file = new File(fileName);
                    if (!file.exists()) {
                        willSave = file.mkdirs();
                        if (!willSave) log.debug("Can't create dictionary \"" + fileName + "\", will not dump classes");
                    } else {
                        willSave = !file.isFile();
                        if (!willSave) log.debug("Can't create directory \"dump\" since a \"dump\" FILE already exists, will not dump classes");
                    }
                } else {
                    log.debug("Property \"" + PROPERTY + "\" is empty, will not dump classes");
                }
            }
            if (willSave) log.debug("Property \"" + PROPERTY + "\" defined, will dump classes to \"" + file + "\"");
            else file = null;
        }
        if (willSave) {
            String full = name.replace('.', '\\');
            String path = full.substring(0, full.lastIndexOf('\\'));
            name = full.substring(full.lastIndexOf('\\') + 1);

            File dic = new File(file, path);
            if (dic.exists() || dic.mkdirs()) {
                dic = new File(dic, name.concat(".class"));
                log.debug("Saving transformed {} to \"{}\"", name, dic);
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(dic);
                } catch (IOException e) {
                    log.debug("Can't dump {} to \"{}\"", name, dic);
                    log.debug(e);
                }
                if (fos != null) {
                    try {
                        fos.write(bytes);
                    } catch (IOException e) {
                        log.debug("Can't dump {} to \"{}\"", name, dic);
                        log.debug(e);
                    } finally {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            log.debug(e);
                        }
                    }
                }
            }
        }
        return bytes;
    }

    static byte[] transformedSave(String name, byte[] bytes) {
        transformed(name);
        return saveDump(name, bytes);
    }

    static void transformed(String name) {
        log.warn("Transformed: ".concat(name));
    }
}
