package seamcarving;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Dynamic programming implementation of the {@link SeamFinder} interface.
 *
 * @see SeamFinder
 * @see SeamCarver
 */
public class DynamicProgrammingSeamFinder implements SeamFinder {



    @Override
    public List<Integer> findHorizontalSeam(double[][] energies) {
        double[] row = energies[0];
        int rowLength = row.length;
        int colHeight = energies.length;
        double[][] dynEnergies = new double[rowLength][colHeight];

        for (int i = 0; i < energies.length; i++) {
            double[] currRow = energies[i];
            for (int j = 0; j < currRow.length; j++) {
                dynEnergies[j][i] = energies[i][j];
            }
        }

        for (int i = 1; i < dynEnergies[0].length; i++) {
            for (int j = 0; j < dynEnergies.length; j++) {
                List<Double> options = new LinkedList<>();
                if (j > 0) {
                    options.add(dynEnergies[j - 1][i - 1]);
                }
                if (j < dynEnergies.length - 1) {
                    options.add(dynEnergies[j + 1][i - 1]);
                }
                options.add(dynEnergies[j][i - 1]);

                double bestOption = Double.MAX_VALUE;
                for (double option : options) {
                    if (option < bestOption) {
                        bestOption = option;
                    }
                }

                dynEnergies[j][i] = dynEnergies[j][i] + bestOption;
            }
        }

        double startEnergy = dynEnergies[0][dynEnergies[0].length - 1];
        int startYPosition = 0;


        for (int i = 0; i < dynEnergies.length; i++) {
            System.out.println("Current Start position: [" + startYPosition + ", " +
                (dynEnergies[0].length - 1) + "] " + dynEnergies[startYPosition][dynEnergies[0].length - 1]);
            System.out.println("Potential Start position: [" + i + ", " +
                (dynEnergies[0].length - 1) + "] " + dynEnergies[i][dynEnergies[0].length - 1]);
            if (dynEnergies[i][dynEnergies[0].length - 1] < startEnergy) {
                System.out.println("Updating start position to: [" + i + ", " +
                    (dynEnergies[0].length - 1) + "] " + dynEnergies[i][dynEnergies[0].length - 1]);
                startEnergy = dynEnergies[i][dynEnergies[0].length - 1];
                startYPosition = i;
            } else {
                System.out.println("We will not change the start position!");
            }
            System.out.println();
        }

        LinkedList<Integer> yPositions = new LinkedList<>();
        int[] yPositionsArray = new int[dynEnergies[0].length];
        yPositionsArray[yPositionsArray.length - 1] = startYPosition;
        int count = 0;
        //yPositions.add(startYPosition);
        for (int i = dynEnergies[0].length - 1; i > 0; i--) {
            Map<Integer, Double> options = new HashMap<>();
            if (startYPosition > 0) {
                options.put((startYPosition - 1), dynEnergies[startYPosition - 1][i - 1]);
            } if (startYPosition < dynEnergies.length - 1) {
                options.put((startYPosition + 1), dynEnergies[startYPosition + 1][i - 1]);
            }
            options.put((startYPosition), dynEnergies[startYPosition][i - 1]);

            double bestOption = Double.MAX_VALUE;
            int bestOptionYValue = 10;
            for (int option : options.keySet()) {
                double currOption = options.get(option);
                System.out.println("Option: ");
                System.out.println("    Y Position: " + option);
                System.out.println("    Energy: " + options.get(option));
                if (currOption < bestOption) {
                    bestOption = currOption;
                    bestOptionYValue = option;
                }
            }
            System.out.println();
            System.out.println("The best option has a Y value of " + bestOptionYValue +
                                " and an energy of " + bestOption);
            System.out.println();
            yPositionsArray[i - 1] = bestOptionYValue;
            count++;
            startYPosition = bestOptionYValue;
        }

        

        System.out.println("STOP");

        for (int i = 0; i < yPositionsArray.length; i++) {
            yPositions.add(yPositionsArray[i]);
        }

        return yPositions;
    }

    @Override
    public List<Integer> findVerticalSeam(double[][] energies) {
        int rowCount = energies.length;
        int colCount = energies[0].length;

        double[][] newEnergies = new double[colCount][rowCount];

        for (int i = 0; i < colCount; i++) {
            for (int j = 0; j < rowCount; j++) {
                newEnergies[i][j] = energies[j][i];
            }
        }

        return findHorizontalSeam(newEnergies);
        /*
        double[] row = newEnergies[0];
        int rowLength = row.length;
        int colHeight = newEnergies.length;
        double[][] dynEnergies = new double[rowLength][colHeight];

        for (int i = 0; i < newEnergies.length; i++) {
            double[] currRow = newEnergies[i];
            for (int j = 0; j < currRow.length; j++) {
                dynEnergies[j][i] = newEnergies[i][j];
            }
        }

        for (int i = 1; i < dynEnergies[0].length; i++) {
            double[] currRow = dynEnergies[i - 1];
            for (int j = 0; j <= dynEnergies.length; j++) {
                List<Double> options = new LinkedList<>();
                if (j > 0) {
                    options.add(dynEnergies[j - 1][i - 1]);
                }
                if (j < dynEnergies.length - 1) {
                    options.add(dynEnergies[j + 1][i - 1]);
                }
                options.add(dynEnergies[j][i - 1]);

                double bestOption = Double.MAX_VALUE;
                for (double option : options) {
                    if (option < bestOption) {
                        bestOption = option;
                    }
                }

                dynEnergies[j][i] = dynEnergies[j][i] + bestOption;
            }
        }

        double startEnergy = dynEnergies[0][dynEnergies[0].length - 1];
        int startYPosition = 0;


        for (int i = 0; i < dynEnergies.length; i++) {
            if (dynEnergies[i][dynEnergies[0].length - 1] < startEnergy) {
                startEnergy = dynEnergies[i][dynEnergies[0].length - 1];
                startYPosition = i;
            }
        }

        LinkedList<Integer> yPositions = new LinkedList<>();
        int[] yPositionsArray = new int[dynEnergies[0].length];
        int count = 0;
        //yPositions.add(startYPosition);
        for (int i = dynEnergies[0].length; i > 0; i--) {
            Map<Integer, Double> options = new HashMap<>();
            if (startYPosition > 0) {
                options.put((startYPosition - 1), dynEnergies[startYPosition - 1][i - 1]);
            } if (startYPosition < dynEnergies.length - 1) {
                options.put((startYPosition + 1), dynEnergies[startYPosition + 1][i - 1]);
            }
            options.put((startYPosition), dynEnergies[startYPosition][i - 1]);

            double bestOption = Double.MAX_VALUE;
            int bestOptionYValue = 10;
            for (int option : options.keySet()) {
                double currOption = options.get(option);
                System.out.println("Option: ");
                System.out.println("    Y Position: " + option);
                System.out.println("    Energy: " + options.get(option));
                if (currOption < bestOption) {
                    bestOption = currOption;
                    bestOptionYValue = option;
                }
            }
            System.out.println();
            System.out.println("The best option has a Y value of " + bestOptionYValue +
                " and an energy of " + bestOption);
            System.out.println();
            yPositionsArray[i - 1] = bestOptionYValue;
            count++;
            startYPosition = bestOptionYValue;
        }



        System.out.println("STOP");

        for (int i = 0; i < yPositionsArray.length; i++) {
            yPositions.add(yPositionsArray[i]);
        }

        return yPositions;

         */
    }
}
