package gpk.ml.decisiontree.dataload;

import gpk.ml.decisiontree.dataload.dataset.Dataset;
import gpk.ml.decisiontree.dataload.dataset.feature.AbstractFeature;
import gpk.ml.decisiontree.dataload.dataset.feature.EnumFeature;

import java.io.IOException;
import java.util.List;

public interface CSVLoader {
    Dataset loadDataset(String filepath,
                        List<AbstractFeature> features,
                        EnumFeature label,
                        List<String> labelValues /* classes */) throws IOException;
}
