package com.nix.zhylina.database;

import com.nix.zhylina.exception.DataProcessingException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * <p><h2>PropertiesUtil</h2>
 * <b>The class in which the property file is read</b> <br>
 *
 * @author Zhilina Svetlana
 * @version 1.0
 * @since 26.01.2022 </p>
 */
public class PropertiesUtil {
    /**
     * This method reads a property file.
     * If the file with the name {@code propertyFileName} is not found,
     * is called or if the properties cannot be read from
     * property filef, an exception should be thrown
     *
     * @param propertyFileName is the name of the file to be read and resources
     * @return loaded properties from property file
     */
    public static Properties getProperty(String propertyFileName) {
        InputStream fileReading = PropertiesUtil.class.getClassLoader()
                .getResourceAsStream(propertyFileName);
        Properties uploadedPropertyFile = new Properties();
        try {
            if (fileReading == null) {
                throw new DataProcessingException("Property file not found!");
            }
            uploadedPropertyFile.load(fileReading);
        } catch (IOException e) {
            throw new DataProcessingException("Error reading list properties!");
        }
        return uploadedPropertyFile;
    }
}