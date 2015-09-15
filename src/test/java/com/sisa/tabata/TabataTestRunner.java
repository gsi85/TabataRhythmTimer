package com.sisa.tabata;

import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.manifest.AndroidManifest;
import org.robolectric.res.Fs;

/**
 * Custom test runner using {@link RobolectricTestRunner}.
 *
 * @author Laszlo Sisa
 */
public class TabataTestRunner extends RobolectricTestRunner {

    /**
     * Creates a runner to run {@code testClass}. Looks in your working directory for your AndroidManifest.xml file
     * and res directory by default. Use the {@link Config} annotation to configure.
     *
     * @param testClass the test class to be run
     * @throws InitializationError if junit says so
     */
    public TabataTestRunner(final Class<?> testClass) throws InitializationError {
        super(testClass);
    }

    @Override
    protected AndroidManifest getAppManifest(Config config) {
        String myAppPath = TabataTestRunner.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String manifestPath = myAppPath + "../../../src/main/AndroidManifest.xml";
        String resPath = myAppPath + "../../../src/main/res";
        String assetPath = myAppPath + "../../../src/main/assets";
        return createAppManifest(Fs.fileFromPath(manifestPath), Fs.fileFromPath(resPath), Fs.fileFromPath(assetPath));
    }

}
