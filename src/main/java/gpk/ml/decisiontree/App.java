package gpk.ml.decisiontree;

import gpk.ml.decisiontree.dataload.CSVLoaderImpl;
import gpk.ml.decisiontree.tree.Dataset;

import java.io.IOException;

public class App {

    public static void main(String... args) {

        Dataset dataset;
        CSVLoaderImpl csvLoader = new CSVLoaderImpl();
        try {
            dataset = csvLoader.loadDataset("dataset/titanic/train.csv");
            dataset.print();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
