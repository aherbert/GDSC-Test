package uk.ac.sussex.gdsc.test.rng;

import java.util.function.Function;

import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.core.source64.SplitMix64;
import org.apache.commons.rng.simple.RandomSource;

import uk.ac.sussex.gdsc.test.utils.DataCache;
import uk.ac.sussex.gdsc.test.utils.TestSettings;

/**
 * A factory for creation of {@link UniformRandomProvider}.
 */
public class RNGFactory {

    /**
     * Class for generating full length seeds.
     */
    private static class SeedGenerator implements Function<Long, int[]> {
        @Override
        public int[] apply(Long source) {
            // This has been copied from org.apache.commons.rng.simple.internal.SeedFactory

            // Generate a full length seed using another RNG
            final SplitMix64 rng = new SplitMix64(source);
            final int n = 257; // Size of the state array of "MultiplyWithCarry256".
            final int[] array = new int[n];
            for (int i = 0; i < n; i++) {
                array[i] = rng.nextInt();
            }
            return array;
        }
    }

    /**
     * Do not allow public construction.
     */
    private RNGFactory() {
    }

    /** Store the seeds for the UniformRandomProvider. */
    private static final DataCache<Long, int[]> seedCache = new DataCache<>();

    /** The seed generator. */
    private static SeedGenerator SEED_GENERATOR = new SeedGenerator();

    /**
     * Gets the uniform random provider.
     * <p>
     * If the {@code seed} is {@code 0} then a random seed will be used.
     *
     * @param seed the seed
     * @return the uniform random provider
     */
    public static UniformRandomProvider create(long seed) {
        if (seed == 0)
            return RandomSource.create(RandomSource.MWC_256);
        final int[] fullSeed = seedCache.getOrComputeIfAbsent(seed, SEED_GENERATOR);
        return RandomSource.create(RandomSource.MWC_256, fullSeed);
    }

    /**
     * Gets a uniform random provider with a fixed seed set using the system
     * property {@value TestSettings#PROPERTY_RANDOM_SEED}.
     * <p>
     * Note: To obtain a randomly seeded provider use
     * {@link #create(long)} using zero as the seed.
     *
     * @return the uniform random provider
     */
    public static UniformRandomProvider create() {
        return create(TestSettings.getSeed());
    }
}
