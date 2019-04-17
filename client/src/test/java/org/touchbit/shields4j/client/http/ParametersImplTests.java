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

package org.touchbit.shields4j.client.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.touchbit.shields4j.client.http.model.Parameter;
import org.touchbit.shields4j.jupiter.BaseUnitTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Oleg Shaburov on 17.04.2019
 * shaburov.o.a@gmail.com
 */
@DisplayName("ParametersImpl class tests")
class ParametersImplTests extends BaseUnitTest {

    @Test
    @DisplayName("Check put and get")
    void unitTest_20190417210121() {
        ParametersImpl parameters = new ParametersImpl();
        parameters.put(Parameter.LOGO, "logo");
        assertThat(parameters.get()).hasSize(1);
        assertThat(parameters.get().get(Parameter.LOGO)).isEqualTo("logo");
    }

    @Test
    @DisplayName("Check message parameter")
    void unitTest_20190417210450() {
        ParametersImpl parameters = new ParametersImpl();
        parameters.put(Parameter.MESSAGE, "");
        Executable executable = parameters::check;
        Throwable t = executeThrowable(executable, IllegalArgumentException.class);
        assertThat(t.getMessage()).isEqualTo("Parameter message is required.");
        parameters.put(Parameter.MESSAGE, null);
        executable = parameters::check;
        t = executeThrowable(executable, IllegalArgumentException.class);
        assertThat(t.getMessage()).isEqualTo("Parameter message is required.");
    }

    @Test
    @DisplayName("Check logo parameter")
    void unitTest_20190417210519() {
        ParametersImpl parameters = new ParametersImpl();
        parameters.put(Parameter.MESSAGE, "MESSAGE");
        parameters.put(Parameter.LOGO, "");
        parameters.put(Parameter.LOGO_BASE64, "");
        Executable executable = parameters::check;
        Throwable t = executeThrowable(executable, IllegalArgumentException.class);
        assertThat(t.getMessage()).isEqualTo("Parameter logo should be presented in the singular.");
    }

}
