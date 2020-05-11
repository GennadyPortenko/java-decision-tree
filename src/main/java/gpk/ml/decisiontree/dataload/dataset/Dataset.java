package gpk.ml.decisiontree.dataload.dataset;

import gpk.ml.decisiontree.dataload.dataset.feature.AbstractFeature;
import gpk.ml.decisiontree.dataload.dataset.feature.EnumFeature;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@NoArgsConstructor
public class Dataset {
    @Getter
    private List<Sample> samples = new ArrayList<>();

    @Getter
    @Setter
    @NonNull
    private List<AbstractFeature> features;

    @Getter
    @Setter
    @NonNull
    private EnumFeature label;

    @Getter
    @Setter
    @NonNull
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
