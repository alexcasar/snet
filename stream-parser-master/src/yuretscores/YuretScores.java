/**
* Simple score class
* */
package yuretscores;

import org.ojalgo.matrix.store.SparseStore;

public class YuretScores {

    SparseStore<Double> yuretScores = SparseStore.PRIMITIVE64.make(12, 12);

    public YuretScores(){
        yuretScores.set(0, 1, Math.exp(1.18));
        yuretScores.set(0, 2, Math.exp(0.55));
        yuretScores.set(0, 3, Math.exp(1.78));
        yuretScores.set(0, 6, Math.exp(0.53));
        yuretScores.set(1, 2, Math.exp(3.48));
        yuretScores.set(2, 3, Math.exp(-1.64));
        yuretScores.set(2, 4, Math.exp(3.15));
        yuretScores.set(2, 5, Math.exp(1.26));
        yuretScores.set(3, 4, Math.exp(1.43));
        yuretScores.set(4, 7, Math.exp(2.09));
        yuretScores.set(5, 7, Math.exp(4.01));
        yuretScores.set(6, 7, Math.exp(0.43));
        yuretScores.set(7, 8, Math.exp(2.61));
        yuretScores.set(7, 9, Math.exp(3.92));
        yuretScores.set(8, 9, Math.exp(2.58));
        yuretScores.set(9, 10, Math.exp(1.07));
        yuretScores.set(10, 11, Math.exp(4.51));
    }

    public SparseStore<Double> ReturnScores() {
        return yuretScores;
    }
}
