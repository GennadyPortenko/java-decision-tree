package gpk.ml.decisiontree.dataload;

import gpk.ml.decisiontree.dataload.dataset.Dataset;
import gpk.ml.decisiontree.dataload.dataset.Sample;
import gpk.ml.decisiontree.dataload.dataset.feature.AbstractFeature;
import gpk.ml.decisiontree.dataload.dataset.feature.BooleanFeature;
import gpk.ml.decisiontree.dataload.dataset.feature.EnumFeature;
import gpk.ml.decisiontree.dataload.dataset.feature.NumericFeature;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class CSVLoaderImpl implements CSVLoader {
    @Override
    public Dataset loadDataset(String filepath,
                               List<AbstractFeature> features,
                               EnumFeature label,
                               List<String> labelValues) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filepath);
        if (inputStream == null) {
            throw new IllegalArgumentException("Error - no such file: `" + filepath + "`!");
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        Dataset dataset = new Dataset();
        dataset.setLabel(label);
        dataset.setLabelValues(labelValues);

        // read headers (feature names) into the dataset object
        String headersLine = reader.readLine();
        String[] csvHeaders = headersLine.split(",");

        // check positions
        features.forEach(feature -> {
            if (!csvHeaders[feature.getPos()].equals(feature.getName())) {
                throw new Error("Wrong features definition!");
            }
        });

        dataset.setFeatures(features);

        // read samples from file into the dataset object
        String line;
        int count = 0;
        while ((line = reader.readLine()) != null) {

            // split headers with `,` outside of "" delimiter
            String[] sampleStringValues = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

            // create sample
            Sample sample = new Sample();
            for (AbstractFeature feature : features) {
                try {
                    final String sampleStringValue = sampleStringValues[feature.getPos()];
                    if (feature instanceof BooleanFeature) {
                        sample.add(this.booleanValueOf(sampleStringValue));
                    } else if (feature instanceof NumericFeature){
                        sample.add(Float.parseFloat(sampleStringValue));
                    } else {
                        sample.add(sampleStringValue);
                    }
                } catch (IndexOutOfBoundsException | NumberFormatException e) {
                    e.printStackTrace();
                    System.out.println("Wrong sample! (" + count + ")");
                }
            }
            // read label value
            String labelValue = sampleStringValues[dataset.getLabel().getPos()];
            if (dataset.getLabelValues().contains(labelValue)) {
                sample.setLabelValue(labelValue);
            }

            dataset.addSample(sample);
            count++;
        }

        return dataset;
    }

    private boolean booleanValueOf(String val) {
        if (val.trim().equals("1")) {
            return true;
        } else if (val.trim().isEmpty()) {
            return false;
        } else {
            return Boolean.parseBoolean(val);
        }
    }


}
