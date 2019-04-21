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

package org.touchbit.shields4j.client;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.touchbit.shields4j.client.http.ClientImpl;
import org.touchbit.shields4j.client.http.ParametersImpl;
import org.touchbit.shields4j.client.http.model.Color;
import org.touchbit.shields4j.client.http.model.Logo;
import org.touchbit.shields4j.client.http.model.Parameter;
import org.touchbit.shields4j.client.http.model.Style;
import org.touchbit.shields4j.jupiter.BaseUnitTest;
import org.touchbit.shields4j.client.impl.TestParameters;

import java.io.*;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Oleg Shaburov on 15.04.2019
 * shaburov.o.a@gmail.com
 */
@DisplayName("ShieldsIO tests")
class ShieldsIOTests extends BaseUnitTest {

    @Test
    @DisplayName("Get badge body")
    void unitTest_20190415112710() throws Exception {
        wireMockServer.stubFor(get("/?message=20190415112710")
                .willReturn(aResponse().withHeader("Content-Type", "text/plain")
                        .withStatus(200)
                        .withBody("unitTest_20190415112710")));
        String result = new ShieldsIO("http://localhost:12345/")
                .withMessage("20190415112710")
                .getShield();
        assertThat(result).isEqualTo("unitTest_20190415112710");
    }

    @Test
    @DisplayName("Get badge body with exception")
    void unitTest_20190417164608() {
        wireMockServer.stubFor(get("/?message=unitTest_20190417164608")
                .willReturn(aResponse().withHeader("Content-Type", "text/plain")
                        .withStatus(400)));
        ShieldsIO shieldsIO = new ShieldsIO("http://localhost:12345/")
                .withMessage("unitTest_20190417164608");
        Executable executable = shieldsIO::getShield;
        Throwable t = executeThrowable(executable, IOException.class);
        assertThat(t.getMessage()).isEqualTo("Server returned HTTP response code: 400 for URL: " +
                "http://localhost:12345/?message=unitTest_20190417164608");
    }

    @Test
    @DisplayName("Get badge body with ignoreException")
    void unitTest_20190417165938() throws Exception {
        wireMockServer.stubFor(get("/?message=20190415112710")
                .willReturn(aResponse().withHeader("Content-Type", "text/plain")
                        .withStatus(404)
                        .withBody("File not found")));
        String result = new ShieldsIO("http://localhost:12345/")
                .withMessage("20190415112710")
                .withIgnoreExceptions(true)
                .getShield();
        assertThat(result).isEqualTo("java.io.FileNotFoundException: " +
                "http://localhost:12345/?message=20190415112710");
    }

    @Test
    @DisplayName("Write badge to file")
    void unitTest_20190417162209() throws Exception {
        File file = new File(BADGES_PATH + "unitTest_20190417162209.svg");
        wireMockServer.stubFor(get("/?message=unitTest_20190417162209")
                .willReturn(aResponse().withHeader("Content-Type", "text/plain")
                        .withStatus(200)
                        .withBody("unitTest_20190417162209")));
        new ShieldsIO("http://localhost:12345/")
                .withMessage("unitTest_20190417162209")
                .writeShieldToFile(file.getPath());
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder sb = new StringBuilder();
            reader.lines().forEach(sb::append);
            assertThat(sb.toString()).isEqualTo("unitTest_20190417162209");
        }
    }

    @Test
    @DisplayName("Write badge to file with Exception")
    void unitTest_20190417173014() {
        File file = new File(FAIL_PATH);
        wireMockServer.stubFor(get("/?message=unitTest_20190417162209")
                .willReturn(aResponse().withHeader("Content-Type", "text/plain")
                        .withStatus(200)
                        .withBody("unitTest_20190417162209")));

        ShieldsIO shieldsIO = new ShieldsIO("http://localhost:12345/")
                .withMessage("unitTest_20190417162209");
        Executable executable = () -> shieldsIO.writeShieldToFile(file.getPath());
        Throwable t = executeThrowable(executable, IOException.class);
        assertThat(t.getMessage()).isEqualTo(FAIL_PATH + " (Permission denied)");
    }

    @Test
    @DisplayName("Write badge to file with ignoreException")
    void unitTest_20190417174859() throws Exception {
        File file = new File(FAIL_PATH);
        wireMockServer.stubFor(get("/?message=unitTest_20190417162209")
                .willReturn(aResponse().withHeader("Content-Type", "text/plain")
                        .withStatus(200)
                        .withBody("unitTest_20190417162209")));
        new ShieldsIO("http://localhost:12345/")
                .withMessage("unitTest_20190417162209")
                .withIgnoreExceptions()
                .writeShieldToFile(file.getPath());
    }

    @Test
    @DisplayName("Create new ShieldsIO() object")
    void unitTest_20190417175347() {
        new ShieldsIO();
    }

    @Test
    @DisplayName("Create new ShieldsIO() object with specified URL")
    void unitTest_20190417185344() {
        new ShieldsIO("http://example.com");
    }

    @Test
    @DisplayName("Create new ShieldsIO() object with empty URL")
    void unitTest_20190417185431() {
        Executable executable = () -> new ShieldsIO("");
        Throwable t = executeThrowable(executable, IllegalArgumentException.class);
        assertThat(t.getMessage()).isEqualTo("URL can not be empty.");
        //noinspection ConstantConditions
        executable = () -> new ShieldsIO(null);
        t = executeThrowable(executable, IllegalArgumentException.class);
        assertThat(t.getMessage()).isEqualTo("URL can not be empty.");
    }

    @Test
    @DisplayName("Create new ShieldsIO() object with all parameters")
    void unitTest_20190417192430() {
        ShieldsIO shieldsIO = new ShieldsIO("http://localhost:12345/")
                .withClient(new ClientImpl())
                .withParameters(new ParametersImpl())
                .withLabel("withLabel")
                .withLabelColor(Color.BLUE)
                .withMessage("withMessage")
                .withMessageColor(Color.RED)
                .withStyle(Style.PLASTIC)
                .withLogo(Logo.GITLAB)
                .withLogoColor(Color.BLUEVIOLET)
                .withLogoWidth(1)
                .withLogoLink("withLogoLink")
                .withCacheSeconds(2)
                .withIgnoreExceptions();
        assertThat(shieldsIO.getShieldUrl()).contains("label=withLabel");
        assertThat(shieldsIO.getShieldUrl()).contains("logoColor=blueviolet");
        assertThat(shieldsIO.getShieldUrl()).contains("labelColor=blue");
        assertThat(shieldsIO.getShieldUrl()).contains("color=red");
        assertThat(shieldsIO.getShieldUrl()).contains("message=withMessage");
        assertThat(shieldsIO.getShieldUrl()).contains("logoWidth=1");
        assertThat(shieldsIO.getShieldUrl()).contains("cacheSeconds=2");
        assertThat(shieldsIO.getShieldUrl()).contains("style=plastic");
        assertThat(shieldsIO.getShieldUrl()).contains("link=withLogoLink");
        assertThat(shieldsIO.getShieldUrl()).contains("logo=GitLab");
    }

    @Test
    @DisplayName("Create new ShieldsIO() object with base64 logo")
    void unitTest_20190417202536() throws IOException {
        ShieldsIO shieldsIO = new ShieldsIO("http://localhost:12345/")
                .withMessage("withMessage")
                .withBase64Logo(new File("../.docs/img/status_success.svg"));
        assertThat(shieldsIO.getShieldUrl())
                .contains("logo=data%3Aimage%2Fsvg%2Bxml%3Bbase64%2CPHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCAxNCAxNCIgaWQ9InN0YXR1c19zdWNjZXNzIiB3aWR0aD0iMTAwJSIgaGVpZ2h0PSIxMDAlIj48ZyBmaWxsLXJ1bGU9ImV2ZW5vZGQiPjxwYXRoIGQ9Ik0wIDdhNyA3IDAgMSAxIDE0IDBBNyA3IDAgMCAxIDAgN3oiPjwvcGF0aD48cGF0aCBkPSJNMTMgN0E2IDYgMCAxIDAgMSA3YTYgNiAwIDAgMCAxMiAweiIgZmlsbD0iI0ZGRiI%2BPC9wYXRoPjxwYXRoIGQ9Ik02LjI3OCA3LjY5N0w1LjA0NSA2LjQ2NGEuMjk2LjI5NiAwIDAgMC0uNDItLjAwMmwtLjYxMy42MTRhLjI5OC4yOTggMCAwIDAgLjAwMi40MmwxLjkxIDEuOTA5YS41LjUgMCAwIDAgLjcwMy4wMDVsLjI2NS0uMjY1TDkuOTk3IDYuMDRhLjI5MS4yOTEgMCAwIDAtLjAwOS0uNDA4bC0uNjE0LS42MTRhLjI5LjI5IDAgMCAwLS40MDgtLjAwOUw2LjI3OCA3LjY5N3oiPjwvcGF0aD48L2c%2BPC9zdmc%2B");
    }

    @Test
    @DisplayName("Create new ShieldsIO() object with base64 logo Exception")
    void unitTest_20190417202718() {
        Executable executable = () -> new ShieldsIO().withBase64Logo(new File(FAIL_PATH));
        Throwable t = executeThrowable(executable, FileNotFoundException.class);
        assertThat(t.getMessage()).isEqualTo(FAIL_PATH + " (No such file or directory)");
    }

    @Test
    @DisplayName("Create new ShieldsIO() object with empty Client")
    void unitTest_20190417203203() {
        Executable executable = () -> new ShieldsIO().withClient(null);
        Throwable t = executeThrowable(executable, IllegalArgumentException.class);
        assertThat(t.getMessage()).isEqualTo("Client can not be null.");
    }

    @Test
    @DisplayName("Create new ShieldsIO() object with empty Parameters")
    void unitTest_20190417203320() {
        Executable executable = () -> new ShieldsIO().withParameters(null);
        Throwable t = executeThrowable(executable, IllegalArgumentException.class);
        assertThat(t.getMessage()).isEqualTo("Parameters can not be null.");
    }

    @Test
    @DisplayName("getShieldUrl with empty Parameters")
    void unitTest_20190417211414() {
        ShieldsIO shieldsIO = new ShieldsIO("http://localhost:12345/")
                .withParameters(new TestParameters());
        assertThat(shieldsIO.getShieldUrl()).isEqualTo("http://localhost:12345/");
        shieldsIO = new ShieldsIO("http://localhost:12345/")
                .withParameters(new TestParameters() {
                    @Override
                    public Map<Parameter, String> get() {
                        return null;
                    }
                });
        assertThat(shieldsIO.getShieldUrl()).isEqualTo("http://localhost:12345/");
    }

    @Test
    @DisplayName("mkdirs() without parent directory")
    void unitTest_20190420212944() {
        ShieldsIO shieldsIO = new ShieldsIO();
        shieldsIO.mkdirs(new File("test.tmp"));
    }

}
