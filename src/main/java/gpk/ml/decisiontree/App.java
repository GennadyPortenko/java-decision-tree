package gpk.ml.decisiontree;

import gpk.ml.decisiontree.dataload.CSVLoaderImpl;
import gpk.ml.decisiontree.dataload.dataset.Dataset;
import gpk.ml.decisiontree.dataload.dataset.feature.AbstractFeature;
import gpk.ml.decisiontree.dataload.dataset.feature.BooleanFeature;
import gpk.ml.decisiontree.dataload.dataset.feature.EnumFeature;
import gpk.ml.decisiontree.dataload.dataset.feature.NumericFeature;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App {

    public static void main(String... args) {

        Dataset dataset;
        CSVLoaderImpl csvLoader = new CSVLoaderImpl();
        try {
            List<AbstractFeature> features = Arrays.asList(
                new NumericFeature("Pclass", 2),
                new EnumFeature("Sex", 4),
                new NumericFeature("Age", 5),
                new NumericFeature("SibSp", 6),
                new NumericFeature("Parch", 7),
                new BooleanFeature("Cabin", 10),
                new EnumFeature("Embarked", 11)
            );

            /*
            List<AbstractFeature> features = Arrays.asList(
                    new NumericFeature("SepalLengthCm", 1),
                    new EnumFeature("SepalWidthCm", 2),
                    new NumericFeature("PetalLengthCm", 3),
                    new NumericFeature("PetalWidthCm", 4)
            );
            */

            dataset = csvLoader.loadDataset("dataset/titanic/train.csv",
                                             features,
                                             new EnumFeature("Survived", 1),
                                             Arrays.asList("0", "1"));
            dataset.print();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
