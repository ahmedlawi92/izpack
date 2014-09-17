package com.izforge.izpack.installer.util;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ahmed Abu Lawi on 17/09/14.
 * ini file reader
 */


public class IniFileReader {

    private static Pattern SECTION_PATTERN = Pattern.compile("\\s*\\[(.*?)\\]");

    /**
     * Returns whether a the current line being read is a section heading.
     *
     * @param line     the line in the ini file
     * @return true if it is a section heading, false otherwise
     */
    private static boolean isSection(String line){
        Matcher matcher = SECTION_PATTERN.matcher(line);
        if (matcher.find()){
            return true;
        } else {
            return false;
        }
    }


    /**
     * Returns the section heading with no leading or trailing whitespace.
     *
     * @param line     the line in the ini file
     * @return the section
     */
    private static String getSectionName(String line){
        Matcher matcher = SECTION_PATTERN.matcher(line);
        String section = "";
        if (matcher.matches()) {
            section = matcher.group(1).trim();
        }
        return section;
    }

    /**
     * Returns the section heading with no leading or trailing whitespace.
     *
     * @param file     String with a path to the ini file
     * @return hashmap with key-value pairs
     */
    public static Map<String, String> readIniFile(File file){

        Map<String, String> fileVariables = new HashMap<String, String>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            String section = "";
            while (true){
                line = br.readLine();

                if (line == null){
                    break;
                }

                if (isSection(line)){
                    section = getSectionName(line);
                    continue;
                } else if (line.trim().isEmpty()){
                    section = "";
                }
                else{
                    String [] keyPair = line.split("=", 2);
                    if (keyPair.length == 2) {
                        if (! section.isEmpty()){
                            keyPair[0] = section.concat(".").concat(keyPair[0]);
                        }
                        fileVariables.put(keyPair[0].trim(), keyPair[1].trim());
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileVariables;
    }
}



