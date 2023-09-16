package seamcarving;

import edu.princeton.cs.algs4.Picture;
import edu.washington.cse373.BaseTest;
import seamcarving.energy.DualGradientEnergyFunction;
import seamcarving.energy.EnergyFunction;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

public abstract class BasicSeamFinderTests extends BaseTest {
    protected abstract SeamFinder createSeamFinder();

    protected EnergyFunction createEnergyFunction() {
        return new DualGradientEnergyFunction();
    }

    protected Picture loadPicture(String name) {
        return new Picture(Path.of("data").resolve(name).toFile());
    }

    protected SeamFinderAssert assertThat(SeamFinder seamFinder) {
        return new SeamFinderAssert(seamFinder);
    }

    @Test
    public void findVerticalSeam_returnsCorrectSeam() {
        Picture p = loadPicture("6x5.png");
        EnergyFunction energyFunction = createEnergyFunction();
        double[][] energies = SeamCarver.computeEnergies(p, energyFunction);

        SeamFinder seamFinder = createSeamFinder();
        assertThat(seamFinder).verticalSeam(energies).hasSameEnergyAs(4, 4, 3, 2, 2);
    }

    @Test
    public void findHorizontalSeam_returnsCorrectSeam() {
        Picture p = loadPicture("6x5.png");
        EnergyFunction energyFunction = createEnergyFunction();
        double[][] energies = SeamCarver.computeEnergies(p, energyFunction);

        SeamFinder seamFinder = createSeamFinder();
        assertThat(seamFinder).horizontalSeam(energies).hasSameEnergyAs(2, 2, 1, 2, 1, 1);
    }
    @Test
    public void horizontalSeamTestGradescope() {
        double[][] energies = {
            {1253.33, 678.96, 927.95, 374.76, 802.33, 838.47, 240.23},
            {853.69, 244.60, 186.22, 203.66, 124.62, 891.40, 355.90},
            {535.23, 251.32, 290.55, 186.41, 345.83, 322.37, 192.48}
        };

        SeamFinder seamFinder = createSeamFinder();
        assertThat(seamFinder).horizontalSeam(energies).hasSize(3);
    }
}
