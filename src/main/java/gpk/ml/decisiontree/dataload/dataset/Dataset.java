package gpk.ml.decisiontree.dataload.dataset;

import gpk.ml.decisiontree.dataload.dataset.feature.AbstractFeature;
import gpk.ml.decisiontree.dataload.dataset.feature.EnumFeature;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Dataset {
    private List<Sample> samples = new ArrayList<>();

    @Getter
    @Setter
    private List<AbstractFeature> features;

    @Getter
    @Setter
    private EnumFeature label;

    @Getter
    @Setter
    List<String> labelValues;

    public void addSample(Sample sample) {
        samples.add(sample);
    }

    public void print() {
        samples.forEach(System.out::println);

        System.out.println("features description:");
        features.forEach(System.out::println);

        System.out.println("label:");
        System.out.println(label);

        System.out.print("label values: ");
        for (String val : labelValues) {
            System.out.print(val + "  ");
        }
        System.out.println();
    }
}
