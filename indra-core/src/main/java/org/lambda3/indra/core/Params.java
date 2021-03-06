package org.lambda3.indra.core;

/*-
 * ==========================License-Start=============================
 * Indra Core Module
 * --------------------------------------------------------------------
 * Copyright (C) 2016 - 2017 Lambda^3
 * --------------------------------------------------------------------
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * ==========================License-End===============================
 */

import org.lambda3.indra.client.AbstractBasicRequest;
import org.lambda3.indra.client.RelatednessRequest;
import org.lambda3.indra.client.ScoreFunction;
import org.lambda3.indra.client.VectorRequest;
import org.lambda3.indra.core.composition.VectorComposition;

import java.util.Objects;


public final class Params {
    public final String corpusName;
    public final ScoreFunction func;
    public final String language;
    public final String model;
    public final boolean translate;

    public final Boolean applyStopWords;
    public final int minWordLength;

    public final String translateTargetLanguage = "EN";

    public final VectorComposition termComposition;
    public final VectorComposition translationComposition;

    public Params(RelatednessRequest request, boolean toTranslate,
                  VectorComposition termComposition, VectorComposition translationComposition) {
        this(request, request.getScoreFunction(), toTranslate, termComposition, translationComposition);
    }

    public Params(VectorRequest request, boolean toTranslate,
                  VectorComposition termComposition, VectorComposition translationComposition) {
        this(request, null, toTranslate, termComposition, translationComposition);
    }

    private Params(AbstractBasicRequest request, ScoreFunction func, boolean toTranslate,
                   VectorComposition termComposition, VectorComposition translationComposition) {
        if (request == null || termComposition == null || translationComposition == null) {
            throw new IllegalArgumentException("All arguments are mandatory!");
        }

        this.corpusName = request.getCorpus();
        this.func = func;
        this.language = request.getLanguage();
        this.model = request.getModel();
        this.translate = toTranslate;
        this.applyStopWords = request.getApplyStopWords();
        this.minWordLength = request.getMinWordLength();
        this.termComposition = termComposition;
        this.translationComposition = translationComposition;
    }

    public Params(String corpusName, ScoreFunction func, String language, String model, boolean translate,
                  Boolean applyStopWords, int minWordLength,
                  VectorComposition termComposition, VectorComposition translationComposition) {

        if (corpusName == null || func == null || language == null || model == null || termComposition == null
                || translationComposition == null) {
            throw new IllegalArgumentException("Except applyStopWords all arguments are mandatory!");
        }

        this.corpusName = corpusName;
        this.func = func;
        this.language = language;
        this.model = model;
        this.translate = translate;
        this.applyStopWords = applyStopWords;
        this.minWordLength = minWordLength;
        this.termComposition = termComposition;
        this.translationComposition = translationComposition;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Params params = (Params) o;
        return translate == params.translate &&
                minWordLength == params.minWordLength &&
                Objects.equals(corpusName, params.corpusName) &&
                func == params.func &&
                Objects.equals(language, params.language) &&
                Objects.equals(model, params.model) &&
                Objects.equals(applyStopWords, params.applyStopWords) &&
                Objects.equals(translateTargetLanguage, params.translateTargetLanguage) &&
                termComposition == params.termComposition &&
                translationComposition == params.translationComposition;
    }

    @Override
    public int hashCode() {
        return Objects.hash(corpusName, func, language, model, translate, applyStopWords, minWordLength,
                translateTargetLanguage, termComposition, translationComposition);
    }
}
