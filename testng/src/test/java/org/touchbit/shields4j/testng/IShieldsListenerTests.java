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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.touchbit.shields4j.client.ShieldsIO;
import org.touchbit.shields4j.jupiter.BaseUnitTest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mock;

/**
 * Created by Oleg Shaburov on 17.04.2019
 * shaburov.o.a@gmail.com
 */
class IShieldsListenerTests extends BaseUnitTest {

    @Test
    @DisplayName("Default constructor")
    void unitTest_20190417235122() {
        new IShieldsListener();
    }

    @Test
    @DisplayName("Check configuration")
    void unitTest_20190418001241() {
        ITestNGMethod method = mock(ITestNGMethod.class);
        when(method.isTest()).thenReturn(true);
        ITestResult result = mock(ITestResult.class);
        when(result.getMethod()).thenReturn(method);
        wireMockServer.stubFor(get("/?message=unitTest_20190417235122")
                .willReturn(aResponse().withHeader("Content-Type", "text/plain")
                        .withStatus(200)
                        .withBody("unitTest_20190417235122")));
        ShieldsIO shieldsIO = new ShieldsIO("http://localhost:12345/");
        IShieldsListener listener = new IShieldsListener(shieldsIO);
        listener.onTestSuccess(result);
        listener.onTestSkipped(result);
        listener.onTestFailure(result);
        listener.onTestFailedButWithinSuccessPercentage(result);
        assertThat(listener.getSuccessTests()).isEqualTo(1);
        assertThat(listener.getSkippedTests()).isEqualTo(1);
        assertThat(listener.getFailureTests()).isEqualTo(2);
        assertThat(listener.getTotal()).isEqualTo(4);
        assertThat(listener.getLabelPrefix()).isEqualTo("iTests");
        assertThat(listener.getFilePrefix()).isEqualTo("TestNG");
        assertThat(listener.getPath()).isEqualTo("./");
    }

    @Test
    @DisplayName("Write to file with custom label and file prefix")
    void unitTest_20190418001320() throws IOException {
        wireMockServer.stubFor(get("/?label=Integration&message=0&color=green")
                .willReturn(aResponse().withHeader("Content-Type", "text/plain")
                        .withStatus(200)
                        .withBody("unitTest_20190418001320_Integration")));
        wireMockServer.stubFor(get("/?label=Integration+success&message=100%25&color=green")
                .willReturn(aResponse().withHeader("Content-Type", "text/plain")
                        .withStatus(200)
                        .withBody("unitTest_20190418001320_Integration_success")));
        ShieldsIO shieldsIO = new ShieldsIO("http://localhost:12345/");
        IShieldsListener listener = new IShieldsListener(shieldsIO)
                .withLabelPefix("Integration")
                .withFilePefix("testng")
                .withPath(BADGES_PATH);
        listener.onExecutionFinish();
        try (BufferedReader reader = new BufferedReader(
                new FileReader(BADGES_PATH + "testng-Integration-success-percent.svg"))) {
            StringBuilder sb = new StringBuilder();
            reader.lines().forEach(sb::append);
            assertThat(sb.toString()).isEqualTo("unitTest_20190418001320_Integration_success");
        }
        try (BufferedReader reader = new BufferedReader(
                new FileReader(BADGES_PATH + "testng-Integration-total.svg"))) {
            StringBuilder sb = new StringBuilder();
            reader.lines().forEach(sb::append);
            assertThat(sb.toString()).isEqualTo("unitTest_20190418001320_Integration");
        }
    }

    @Test
    @DisplayName("Division by zero")
    void unitTest_20190418003407() throws IOException {
        ITestNGMethod method = mock(ITestNGMethod.class);
        when(method.isTest()).thenReturn(true);
        ITestResult result = mock(ITestResult.class);
        when(result.getMethod()).thenReturn(method);
        wireMockServer.stubFor(get("/?label=iTests&message=1&color=green")
                .willReturn(aResponse().withHeader("Content-Type", "text/plain")
                        .withStatus(200)
                        .withBody("unitTest_20190418001320_iTests")));
        wireMockServer.stubFor(get("/?label=iTests+success&message=100%25&color=green")
                .willReturn(aResponse().withHeader("Content-Type", "text/plain")
                        .withStatus(200)
                        .withBody("unitTest_20190418001320_iTests_success")));
        ShieldsIO shieldsIO = new ShieldsIO("http://localhost:12345/");
        IShieldsListener listener = new IShieldsListener(shieldsIO)
                .withPath(BADGES_PATH)
                .withFilePefix("unitTest_20190418003407");
        listener.onTestSuccess(result);
        listener.onExecutionFinish();
        try (BufferedReader reader = new BufferedReader(
                new FileReader(BADGES_PATH + "unitTest_20190418003407-iTests-success-percent.svg"))) {
            StringBuilder sb = new StringBuilder();
            reader.lines().forEach(sb::append);
            assertThat(sb.toString()).isEqualTo("unitTest_20190418001320_iTests_success");
        }
        try (BufferedReader reader = new BufferedReader(
                new FileReader(BADGES_PATH + "unitTest_20190418003407-iTests-total.svg"))) {
            StringBuilder sb = new StringBuilder();
            reader.lines().forEach(sb::append);
            assertThat(sb.toString()).isEqualTo("unitTest_20190418001320_iTests");
        }
    }

    @Test
    @DisplayName("writeTotal() ignore exception")
    void unitTest_20190418004523() {
        ShieldsIO shieldsIO = new ShieldsIO("http://localhost");
        IShieldsListener listener = new IShieldsListener(shieldsIO)
                .withPath("/")
                .withFilePefix("unitTest_20190418003407");
        listener.writeTotal();
    }

    @Test
    @DisplayName("writeSuccessPercent() ignore exception")
    void unitTest_20190418004545() {
        ShieldsIO shieldsIO = new ShieldsIO("http://localhost");
        IShieldsListener listener = new IShieldsListener(shieldsIO)
                .withPath("/")
                .withFilePefix("unitTest_20190418003407");
        listener.writeSuccessPercent();
    }

}
