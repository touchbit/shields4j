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

import org.touchbit.shields4j.client.http.model.Parameter;

import java.util.EnumMap;
import java.util.Map;

/**
 * Request parameters collection
 * <p>
 * Created by Oleg Shaburov on 15.04.2019
 * shaburov.o.a@gmail.com
 */
public class ParametersImpl implements Parameters {

    private Map<Parameter, String> urlParameters = new EnumMap<>(Parameter.class);

    /**
     * @return Map with request parameters
     */
    @Override
    public Map<Parameter, String> get() {
        return urlParameters;
    }

    /**
     * Add request parameter to Map
     * @param parameter request parameter key represents {@link Parameter} object
     * @param value request parameter {@link String} value
     */
    @Override
    public void put(Parameter parameter, String value) {
        urlParameters.put(parameter, value);
    }

    /**
     * Validate request parameters
     * @exception IllegalArgumentException if parameters has invalid condition
     */
    @Override
    public void check() {
        if (urlParameters.get(Parameter.MESSAGE) == null || urlParameters.get(Parameter.MESSAGE).isEmpty()) {
            throw new IllegalArgumentException("Parameter " + Parameter.MESSAGE + " is required.");
        }
        if (urlParameters.get(Parameter.LOGO) != null && urlParameters.get(Parameter.LOGO_BASE64) != null) {
            throw new IllegalArgumentException("Parameter logo should be presented in the singular.");
        }
    }

}
