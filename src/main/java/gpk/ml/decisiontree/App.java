package gpk.ml.decisiontree;

import gpk.ml.decisiontree.dataload.CSVLoaderImpl;
import gpk.ml.decisiontree.dataload.dataset.Dataset;
import gpk.ml.decisiontree.dataload.dataset.Sample;
import gpk.ml.decisiontree.dataload.dataset.feature.AbstractFeature;
import gpk.ml.decisiontree.dataload.dataset.feature.EnumFeature;
import gpk.ml.decisiontree.dataload.dataset.feature.NumericFeature;
import gpk.ml.decisiontree.tree.DecisionTree;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class App {

    public static void main(String... args) {

        Dataset dataset;
        CSVLoaderImpl csvLoader = new CSVLoaderImpl();
        try {
            /*
            List<AbstractFeature> features = Arrays.asList(
                new NumericFeature("Pclass", 2),
                new EnumFeature("Sex", 4),
                new NumericFeature("Age", 5),
                new NumericFeature("SibSp", 6),
                new NumericFeature("Parch", 7),
                new BooleanFeature("Cabin", 10),
                new EnumFeature("Embarked", 11)
            );
            */

            List<AbstractFeature> features = Arrays.asList(
                    new NumericFeature("SepalLengthCm", 1),
                    new NumericFeature("SepalWidthCm", 2),
                    new NumericFeature("PetalLengthCm", 3),
                    new NumericFeature("PetalWidthCm", 4)
            );

            dataset = csvLoader.loadDataset("dataset/iris/train.csv",
                                             features,
                                             new EnumFeature("Species", 5),
                                             Arrays.asList("Iris-versicolor", "Iris-setosa", "Iris-virginica"));
            dataset.print();

            DecisionTree tree = new DecisionTree();
            tree.learn(dataset);


            Dataset testDataset = csvLoader.loadDataset("dataset/iris/test.csv",
                    features,
                    new EnumFeature("Species", 5),
                    Arrays.asList("Iris-versicolor", "Iris-setosa", "Iris-virginica"));

            float accuracy = test(tree, testDataset);

            System.out.println("accuracy : " + accuracy);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static float test(DecisionTree tree, Dataset dataset) {
        int count = 0;
        for (Sample sample : dataset.getSamples()) {
            String label = tree.classify(sample);
            if (label.equals(sample.getLabelValue())) {
                count++;
            }
        }
        return (float)count/dataset.getSamples().size();
    }
}
