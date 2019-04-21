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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.touchbit.shields4j.client.http.Client;
import org.touchbit.shields4j.client.http.ClientImpl;
import org.touchbit.shields4j.client.http.Parameters;
import org.touchbit.shields4j.client.http.ParametersImpl;
import org.touchbit.shields4j.client.http.model.Color;
import org.touchbit.shields4j.client.http.model.Logo;
import org.touchbit.shields4j.client.http.model.Parameter;
import org.touchbit.shields4j.client.http.model.Style;
import org.touchbit.shields4j.client.utils.URLUtils;

import java.io.*;
import java.util.Base64;
import java.util.Map;
import java.util.StringJoiner;

/**
 * Created by Oleg Shaburov on 15.04.2019
 * shaburov.o.a@gmail.com
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class ShieldsIO {

    private final Logger log = LoggerFactory.getLogger(ShieldsIO.class);
    private final String url;
    private Client client = new ClientImpl();
    private Parameters parameters = new ParametersImpl();
    private boolean ignoreExceptions = false;

    public ShieldsIO() {
        this("https://img.shields.io/static/v1.svg");
    }

    /**
     * @param url shields.io URL
     * @exception IllegalArgumentException url can not be empty
     */
    public ShieldsIO(final String url) {
        if (url != null && !url.isEmpty()) {
            this.url = url;
        } else {
            throw new IllegalArgumentException("URL can not be empty.");
        }
    }

    /**
     * HTTP client
     * @param client HTTP client
     * @exception IllegalArgumentException client can not be null
     * @return updated {@link ShieldsIO} object
     */
    public ShieldsIO withClient(Client client) {
        if (client == null) {
            throw new IllegalArgumentException("Client can not be null.");
        }
        this.client = client;
        return this;
    }

    /**
     * @param parameters HTTP request parameters map
     * @exception IllegalArgumentException parameters can not be null
     * @return updated {@link ShieldsIO} object
     */
    public ShieldsIO withParameters(Parameters parameters) {
        if (parameters == null) {
            throw new IllegalArgumentException("Parameters can not be null.");
        }
        this.parameters = parameters;
        return this;
    }

    /**
     * @param label Label text
     * @return updated {@link ShieldsIO} object
     */
    public ShieldsIO withLabel(String label) {
        parameters.put(Parameter.LABEL, label);
        return this;
    }

    /**
     * @param color Label background color
     * @return updated {@link ShieldsIO} object
     */
    public ShieldsIO withLabelColor(Color color) {
        return withLabelColor(color.toString());
    }

    /**
     * @param color Label background color
     * @return updated {@link ShieldsIO} object
     */
    public ShieldsIO withLabelColor(String color) {
        parameters.put(Parameter.LABEL_COLOR, color);
        return this;
    }

    /**
     * @param message Message text
     * @return updated {@link ShieldsIO} object
     */
    public ShieldsIO withMessage(String message) {
        parameters.put(Parameter.MESSAGE, message);
        return this;
    }

    /**
     * @param color Message background color
     * @return updated {@link ShieldsIO} object
     */
    public ShieldsIO withMessageColor(Color color) {
        return withMessageColor(color.toString());
    }

    /**
     * @param color Message background color
     * @return updated {@link ShieldsIO} object
     */
    public ShieldsIO withMessageColor(String color) {
        parameters.put(Parameter.COLOR, color);
        return this;
    }

    /**
     * @param style shield style (Flat is the default).
     * @return updated {@link ShieldsIO} object
     */
    public ShieldsIO withStyle(Style style) {
        parameters.put(Parameter.STYLE, style.toString());
        return this;
    }

    /**
     * Insert one of the named logos from (bitcoin, dependabot, discord, gitlab, npm, paypal, serverfault,
     * stackexchange, superuser, telegram, travis) or simple-icons. Simple-icons are referenced using names as
     * they appear on the simple-icons site.
     * @param logo shield logo
     * @return updated {@link ShieldsIO} object
     */
    public ShieldsIO withLogo(Logo logo) {
        return withLogo(logo.toString());
    }

    /**
     * Insert one of the named logos from (bitcoin, dependabot, discord, gitlab, npm, paypal, serverfault,
     * stackexchange, superuser, telegram, travis) or simple-icons. Simple-icons are referenced using names as
     * they appear on the simple-icons site.
     * @param logo shield logo name
     * @return updated {@link ShieldsIO} object
     */
    public ShieldsIO withLogo(String logo) {
        parameters.put(Parameter.LOGO, logo);
        return this;
    }

    /**
     * @param color Set the color of the logo (hex, rgb, rgba, hsl, hsla and css named colors supported)
     * @return updated {@link ShieldsIO} object
     */
    public ShieldsIO withLogoColor(Color color) {
        return withLogoColor(color.toString());
    }

    /**
     * @param color Set the color of the logo (hex, rgb, rgba, hsl, hsla and css named colors supported)
     * @return updated {@link ShieldsIO} object
     */
    public ShieldsIO withLogoColor(String color) {
        parameters.put(Parameter.LOGO_COLOR, color);
        return this;
    }

    /**
     * @param width Set the horizontal space to give to the logo
     * @return updated {@link ShieldsIO} object
     */
    public ShieldsIO withLogoWidth(int width) {
        parameters.put(Parameter.LOGO_WIDTH, String.valueOf(width));
        return this;
    }

    /**
     * @param link Specify what clicking on the left/right of a badge should do (esp. for social badge style)
     * @return updated {@link ShieldsIO} object
     */
    public ShieldsIO withLogoLink(String link) {
        parameters.put(Parameter.LINK, link);
        return this;
    }

    /**
     * @param base64 Insert custom svg+xml logo image (≥ 14px high)
     * @return updated {@link ShieldsIO} object
     */
    public ShieldsIO withBase64Logo(String base64) {
        parameters.put(Parameter.LOGO_BASE64, "data:image/svg+xml;base64," + base64);
        return this;
    }

    /**
     * @param cache Set the HTTP cache lifetime (rules are applied to infer a default value on a per-badge basis,
     * any values specified below the default will be ignored).
     * @return updated {@link ShieldsIO} object
     */
    public ShieldsIO withCacheSeconds(int cache) {
        parameters.put(Parameter.CACHE_SECONDS, String.valueOf(cache));
        return this;
    }

    /**
     * @param file Insert custom logo image file (≥ 14px high)
     * @exception IOException that occur when error read from file
     * @return updated {@link ShieldsIO} object
     */
    public ShieldsIO withBase64Logo(File file) throws IOException {
        try (FileInputStream reader = new FileInputStream(file)) {
            int fileLen = (int) file.length();
            byte[] bytes = new byte[fileLen];
            if (reader.read(bytes) != fileLen) {
                throw new IOException("File modified while reading");
            }
            String encodedFile = Base64.getEncoder().encodeToString(bytes);
            return withBase64Logo(encodedFile);
        } catch (Exception e) {
            log.error("Error reading from file", e);
            throw e;
        }
    }

    /**
     * @param ignoreExceptions Ignore exceptions that occur when making requests to shields.io or write to file
     * (if an error occurs watch log)
     * @return updated {@link ShieldsIO} object
     */
    public ShieldsIO withIgnoreExceptions(boolean ignoreExceptions) {
        this.ignoreExceptions = ignoreExceptions;
        return this;
    }

    /**
     * Ignore exceptions that occur when making requests to shields.io or write to file
     * (if an error occurs watch log)
     * @return updated {@link ShieldsIO} object
     */
    public ShieldsIO withIgnoreExceptions() {
        return withIgnoreExceptions(true);
    }

    /**
     * @return URL to the svg shield
     */
    public String getShieldUrl() {
        parameters.check();
        Map<Parameter, String> parameterMap = parameters.get();
        String shieldUrl = url;
        if (parameterMap != null && !parameterMap.isEmpty()) {
            StringJoiner sj = new StringJoiner("&", "?", "");
            parameterMap.forEach((k, v) -> sj.add(k + "=" + URLUtils.encode(v)));
            shieldUrl += sj.toString();
        }
        return shieldUrl;
    }

    /**
     * @return shield body (xml)
     * @exception IOException that occur when error call HTTP request
     */
    public String getShield() throws IOException {
        try {
            return client.get(getShieldUrl());
        } catch (Exception e) {
            log.error("Error getting image by URL: " + getShieldUrl(), e);
            if (ignoreExceptions) {
                return e.toString();
            }
            throw e;
        } finally {
            parameters.get().clear();
        }
    }

    /**
     * @param path write shield to specify file
     * @exception IOException that occur when error call HTTP request or write shield to file
     */
    public void writeShieldToFile(String path) throws IOException {
        File file = new File(path);
        mkdirs(file);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            String shield = getShield();
            writer.write(shield);
        } catch (Exception e) {
            log.error("Error writing to file: " + path, e);
            if (ignoreExceptions) {
                return;
            }
            throw e;
        }
    }

    public void mkdirs(File file) {
        if (file.getParentFile()!= null && !file.getParentFile().exists()) {
            //noinspection ResultOfMethodCallIgnored
            file.getParentFile().mkdirs();
        }
    }

}
