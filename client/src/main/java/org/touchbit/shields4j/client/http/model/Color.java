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

package org.touchbit.shields4j.client.http.model;

/**
 * ShieldsIO base colors
 *
 * Created by Oleg Shaburov on 15.04.2019
 * shaburov.o.a@gmail.com
 */
public enum Color {

    BRIGHTGREEN("brightgreen"),
    GREEN("green"),
    YELLOWGREEN("yellowgreen"),
    YELLOW("yellow"),
    ORANGE("orange"),
    RED("red"),
    LIGHTGREY("lightgrey"),
    BLUE("blue"),
    BLUEVIOLET("blueviolet"),

    SUCCESS("brightgreen"),
    IMPORTANT("orange"),
    CRITICAL("red"),
    INFORMATIONAL("blue"),
    INACTIVE("lightgrey"),
    ;

    private String name;

    Color(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
