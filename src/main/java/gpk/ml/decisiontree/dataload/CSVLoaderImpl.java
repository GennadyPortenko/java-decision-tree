package gpk.ml.decisiontree.dataload;

import gpk.ml.decisiontree.tree.Dataset;
import gpk.ml.decisiontree.tree.Feature;
import gpk.ml.decisiontree.tree.Sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVLoaderImpl implements CSVLoader {
    @Override
    public Dataset loadDataset(String filepath) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filepath);
        if (inputStream == null) {
            throw new IllegalArgumentException("Error - no such file: `" + filepath + "`!");
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        Dataset dataset = new Dataset();

        // read headers (feature names) to dataset object
        String headersLine = reader.readLine();
        String[] csvHeaders = headersLine.split(",");
        List<Feature> features = new ArrayList<>();
        for (int i = 0; i < csvHeaders.length; i++) {
            Feature feature = parseHeader(csvHeaders[i], i);
            if (feature != null) {
                features.add(feature);
            }
        }
        dataset.setFeatures(features);

        // read samples to dataset object
        String line;
        int count = 0;
        while ((line = reader.readLine()) != null) {
            Sample sample = new Sample();


            String[] sampleValues = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

            for (Feature feature : features) {
                try {
                    sample.add(sampleValues[feature.getPos()]);
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                    System.out.println("sample number " + count);
                }
            }
            dataset.addSample(sample);
            count++;
        }

        return dataset;
    }

    private Feature parseHeader(String header, int pos) {
        if (!header.contains("#")) {
            return null;
        }
        String[] arr = header.split("#");
        switch(arr[1]) {
            case "int":
                return new Feature(arr[0], Integer.class, pos);
            case "float":
                return new Feature(arr[0], Float.class, pos);
            case "string":
                return new Feature(arr[0], String.class, pos);
            default:
                System.out.println("header : " + header);
                throw new Error("Wrong csv file headers!");
        }
    }

}
