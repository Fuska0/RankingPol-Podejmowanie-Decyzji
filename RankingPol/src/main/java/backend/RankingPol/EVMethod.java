package backend.RankingPol;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import java.util.Arrays;

public class EVMethod {

    // Skale wag
    private static final double[] PRIMITIVE_SCALE = {15,14,13,12,11,10,9,8,7, 6, 5, 4, 3, 2, 1};
    private static final double[] POWER_SCALE = {1, 4, 9, 16, 25, 36, 49, 64, 81};
    private static final double[] BALANCED_SCALE = {0.11, 0.176, 0.25, 0.333, 0.428,
            0.538, 0.66, 0.818, 1, 1.22, 1.5, 1.857, 2.333, 3, 4, 5.66, 9};
    private static final double[] GEOMETRIC_SCALE = {1, Math.sqrt(2), 2, Math.sqrt(3), 3,
            Math.sqrt(5), 5, Math.sqrt(7), 7, Math.sqrt(11), 11, Math.sqrt(13), 13, Math.sqrt(17), 17, Math.sqrt(19), 19};

    // Metoda obliczająca wagi za pomocą metody wartości własnych
    public double[] calculateEvmWeights(double[][] matrix, String scaleType) {
        double[] scale;
        for(int i = 0; i < matrix.length; i ++){
            System.out.println(Arrays.toString(matrix[0]));
        }
        // Wybór skali na podstawie rodzaju skali przekazanego jako argument
        switch (scaleType) {
            case "Power":
                scale = POWER_SCALE;
                break;
            case "Balanced":
                scale = BALANCED_SCALE;
                break;
            case "Geometric":
                scale = GEOMETRIC_SCALE;
                break;
            case "Primitive":
                scale = PRIMITIVE_SCALE;
                break;
            default:
                throw new IllegalArgumentException("Nieznany rodzaj skali: " + scaleType);
        }

        // Przekształcenie macierzy porównań par kryteriów zgodnie z wybraną skalą
        RealMatrix realMatrix = new Array2DRowRealMatrix(matrix);
        applyScale(realMatrix, scale);

        // Obliczenie wartości własnych i wektorów własnych macierzy
        EigenDecomposition eigenDecomposition = new EigenDecomposition(realMatrix);
        RealVector principalEigenvector = eigenDecomposition.getEigenvector(getMaxEigenvalueIndex(eigenDecomposition));

        // Normalizacja wektora własnego
        double[] tmp = normalizeVector(principalEigenvector.toArray());
        int n = tmp.length;

        // Zaokrąglanie wartości wektora własnego do dwóch miejsc po przecinku
        for(int i = 0; i< n; i++){
            BigDecimal bigDecimal = new BigDecimal(tmp[i]);
            bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
            tmp[i] = bigDecimal.doubleValue();
        }

        // Zwracanie obliczonych wag
        return tmp;
    }

    // Przekształcanie macierzy porównań par kryteriów zgodnie z wybraną skalą
    private static void applyScale(RealMatrix matrix, double[] scale) {
        int rowCount = matrix.getRowDimension();
        int colCount = matrix.getColumnDimension();

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                // Mnożenie elementu macierzy przez wartość skalującą
                matrix.multiplyEntry(i, j, Math.pow(scale[i] / scale[j], 1.0 / 2));
            }
        }
    }

    // Znajdowanie indeksu największej wartości własnej w dekompozycji
    private static int getMaxEigenvalueIndex(EigenDecomposition eigenDecomposition) {
        double[] eigenvalues = eigenDecomposition.getRealEigenvalues();
        int maxIndex = 0;
        double maxValue = eigenvalues[0];

        // Znajdowanie największej wartości własnej
        for (int i = 1; i < eigenvalues.length; i++) {
            if (eigenvalues[i] > maxValue) {
                maxIndex = i;
                maxValue = eigenvalues[i];
            }
        }

        // Zwracanie indeksu największej wartości własnej
        return maxIndex;
    }

    // Normalizacja wektora
    private static double[] normalizeVector(double[] vector) {
        double sum = Arrays.stream(vector).sum();

        // Dzielenie każdej wartości wektora przez sumę wszystkich wartości
        for (int i = 0; i < vector.length; i++) {
            vector[i] /= sum;
        }

        // Zwracanie znormalizowanego wektora
        return vector;
    }
}
