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
 * Shield style
 * <p>
 * Created by Oleg Shaburov on 15.04.2019
 * shaburov.o.a@gmail.com
 */
public enum Style {

    PLASTIC("plastic"),
    FLAT("flat"),
    FLAT_SQUARE("flat_square"),
    FOR_THE_BADGE("for_the_badge"),
    POPOUT("popout"),
    POPOUT_SQUARE("popout_square"),
    SOCIAL("social"),
    ;

    private String name;

    Style(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
