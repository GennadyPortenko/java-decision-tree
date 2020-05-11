package gpk.ml.decisiontree.dataload;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DatasetUtilTest {
    /**
     * writes two files with train and test data
     * @throws IOException
     */
    @Test
    @Disabled
    public void splitDatasetTestAndTrain() throws IOException {
        String filepath = "dataset/iris/iris.csv";

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filepath);
        if (inputStream == null) {
            throw new IllegalArgumentException("Error - no such file: `" + filepath + "`!");
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String headersLine = reader.readLine();

        List<String> sourceLines = new ArrayList<>();
        List<String> trainLines = new ArrayList<>();

        String line;
        while ((line = reader.readLine()) != null) {
            sourceLines.add(line);
        }

        int sourceSize = sourceLines.size();
        System.out.println("source size : " + sourceSize);
        int trainSize = (int)Math.round(sourceSize * 0.8);

        for (int currentTrainSize = 0; currentTrainSize < trainSize; currentTrainSize++) {
            int randomIndex = (int)Math.round(Math.random() * (sourceLines.size() - 1));
            String randomLine = sourceLines.get(randomIndex);
            sourceLines.remove(randomIndex);
            trainLines.add(randomLine);

        }

        System.out.println("train size :");
        trainLines.forEach(System.out::println);
        System.out.println("test size : " + sourceLines.size());
        sourceLines.forEach(System.out::println);

        File trainFile = new File("target/train.csv");
        assertTrue(mkdirIfDoesNotExist(trainFile.getParentFile()));

        try( FileWriter fileWriter = new FileWriter(trainFile) ) {
            fileWriter.write(headersLine + "\n");
            for (String trainLine : trainLines) {
                fileWriter.write(trainLine + "\n");
            }
        }

        File testFile = new File("target/test.csv");
        assertTrue(mkdirIfDoesNotExist(testFile.getParentFile()));

        try( FileWriter fileWriter = new FileWriter(testFile) ) {
            fileWriter.write(headersLine + "\n");
            for (String testLine : sourceLines) {
                fileWriter.write(testLine + "\n");
            }
        }

    }

    private boolean mkdirIfDoesNotExist(File file) {
        System.out.println("creating directory `" + file + "`...");
        if (!Files.exists(file.toPath())) {
            return file.mkdir();
        }
        System.out.println("directory `" + file + "` already exists.");
        return true;
    }
}