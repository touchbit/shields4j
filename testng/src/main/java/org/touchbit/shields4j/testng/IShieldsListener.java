/*
 * MIT License
 *
 * Copyright © 2019 TouchBIT.
 * Copyright © 2019 Oleg Shaburov.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.touchbit.shields4j.testng;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IExecutionListener;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.touchbit.shields4j.client.ShieldsIO;
import org.touchbit.shields4j.client.http.model.Color;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * A listener for test running.
 *
 * Created by Oleg Shaburov on 15.04.2019
 * shaburov.o.a@gmail.com
 */
@SuppressWarnings("WeakerAccess")
public class IShieldsListener implements ITestListener, IExecutionListener {

    private final Logger log = LoggerFactory.getLogger(IShieldsListener.class);

    private final AtomicInteger successTests = new AtomicInteger(0);
    private final AtomicInteger failureTests = new AtomicInteger(0);
    private final AtomicInteger skippedTests = new AtomicInteger(0);
    private final ShieldsIO shieldsIO;
    // By default implied "Integration tests".
    private String labelPrefix = "iTests";
    private String filePrefix = "TestNG";
    private String path = "./";

    public IShieldsListener() {
        this(new ShieldsIO());
    }

    /**
     * @param shieldsIO - ShieldsIO client
     */
    public IShieldsListener(final ShieldsIO shieldsIO) {
        this.shieldsIO = shieldsIO;
    }

    /**
     * @param labelPrefix prefix for result badge label. iTests by default.
     * @return updated {@link IShieldsListener} object
     */
    public IShieldsListener withLabelPefix(final String labelPrefix) {
        this.labelPrefix = labelPrefix;
        return this;
    }

    /**
     * @param filePrefix prefix for file name. TestNG by default.
     * @return updated {@link IShieldsListener} object
     */
    public IShieldsListener withFilePefix(final String filePrefix) {
        this.filePrefix = filePrefix;
        return this;
    }

    /**
     * @param path - badges directory (for save)
     * @return updated {@link IShieldsListener} object
     */
    public IShieldsListener withPath(final String path) {
        this.path = path;
        return this;
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        if (result.getMethod().isTest()) {
            successTests.incrementAndGet();
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        if (result.getMethod().isTest()) {
            failureTests.incrementAndGet();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        if (result.getMethod().isTest()) {
            skippedTests.incrementAndGet();
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        if (result.getMethod().isTest()) {
            failureTests.incrementAndGet();
        }
    }

    @Override
    public void onExecutionFinish() {
        writeTotal();
        writeSuccessPercent();
    }

    /**
     * Create a badge with tests total count
     */
    public void writeTotal() {
        try {
            shieldsIO
                    .withLabel(labelPrefix)
                    .withMessage(String.valueOf(getTotal()))
                    .withMessageColor(Color.GREEN)
                    .writeShieldToFile(path + filePrefix + "-" + labelPrefix.replace(" ", "-") + "-total.svg");
        } catch (Exception e) {
            log.error("Error getting a shield for total tests", e);
        }
    }

    /**
     * Create a badge with a tests success percentage
     */
    public void writeSuccessPercent() {
        int successPercent = 100;
        if (getTotal() != 0) {
            successPercent = successTests.get() * 100 / (getTotal() - skippedTests.get());
        }
        try {
            shieldsIO
                    .withLabel(labelPrefix + " success")
                    .withMessage(successPercent + "%")
                    .withMessageColor(successPercent == 100 ? Color.GREEN : Color.RED)
                    .writeShieldToFile(path + filePrefix + "-" +
                            labelPrefix.replace(" ", "-") + "-success-percent.svg");
        } catch (Exception e) {
            log.error("Error getting a shield for success tests percent", e);
        }
    }

    public int getSuccessTests() {
        return successTests.get();
    }

    public int getFailureTests() {
        return failureTests.get();
    }

    public int getSkippedTests() {
        return skippedTests.get();
    }

    public int getTotal() {
        return successTests.get() + failureTests.get() + skippedTests.get();
    }

    public String getLabelPrefix() {
        return labelPrefix;
    }

    public String getFilePrefix() {
        return filePrefix;
    }

    public String getPath() {
        return path;
    }

}
