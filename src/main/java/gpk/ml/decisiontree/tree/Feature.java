package gpk.ml.decisiontree.tree;

import lombok.Data;

@Data
public class Feature {
    final private String name;
    final private Class<?> class_;  // Integer, Float, Boolean, String
    final private int pos;  // position in sample
}
