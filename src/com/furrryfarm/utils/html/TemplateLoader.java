package com.furrryfarm.utils.html;
import java.io.*;

public class TemplateLoader {
    public static String read(String templateName) throws IOException {
        try {
            File file = new File("templates/" + templateName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

            String line;
            StringBuilder fileContent = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) fileContent.append(line);

            bufferedReader.close();
            return fileContent.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
