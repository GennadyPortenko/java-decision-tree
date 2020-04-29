package gpk.ml.decisiontree.dataload;

import gpk.ml.decisiontree.tree.Dataset;

import java.io.IOException;

public interface CSVLoader {
    Dataset loadDataset(String filepath) throws IOException;
}
